//declares a package with a folder inside pstoriz called desacore
package pstoriz.desacore.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.mob.Player;
import pstoriz.desacore.entity.mob.player.Skill;
import pstoriz.desacore.input.Keyboard;
import pstoriz.desacore.input.Mouse;
import pstoriz.desacore.level.Level;
import pstoriz.desacore.level.SpawnLevel;
import pstoriz.desacore.level.TileCoordinate;
import pstoriz.desacore.level.tile.Tile;

//Initially creates the game
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// For this class the width will always be 300
	private static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;
	public static double fps = 60.0;
	public String title = "Desacore";

	// creating a new thread
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private Skill power, reloadSpeed;
	private boolean running = false;
	private Screen screen;

	// Colors
	public Color ammoRedCol = new Color(0xccFC6060, true);
	public Color ammoCol = new Color(0xccffffff, true);
	public Color ammoGreenCol = new Color(0xccBFFFDD, true);

	// Creates an image object
	private BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	public static ImageLoader shade = new ImageLoader(
			"/textures/appearance/Shade.png",
			"/textures/appearance/Shade_alpha.png", 900, 486);
	public static ImageLoader displayLVL = new ImageLoader(
			"/textures/appearance/LVLDisplay.png",
			"/textures/appearance/LVLDisplay_alpha.png", 200, 60);

	public Random r = new Random();

	private int wait, redCount;
	public boolean countdown;

	// Accesses the image
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	// Constructor for Game class
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = new SpawnLevel("/levels/level_test.png");
		TileCoordinate playerSpawn = new TileCoordinate(level.getWidth() / 2,
				level.getHeight() / 2 - 5);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);

		// timers
		wait = 180;
		redCount = 0;

		// Any time you add a new Skill
		power = new Skill(30, 1, Skill.POWER_CURVE);
		player.resetXP("powerXP");

		reloadSpeed = new Skill(6, 1, Skill.RELOAD_CURVE);
		player.resetXP("reloadXP");

		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	// synchronized prevents things from overlapping and getting complicated.
	// There's multiple Threads running
	public synchronized void start() {
		running = true;
		// The new thread contains this game class. Thread is attached to game
		// object
		thread = new Thread(this, "Display");
		thread.start();
	}

	// Shutting down the game with the stop method
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// while the game is running do these loops
	public void run() {
		// gets the actual computer time
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		// sets the box to focus allowing you to click right away
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			// calculates the initial time it takes to run the code and then
			// resets it
			lastTime = now;
			// delta will only be greater than 1 ever 1/60 seconds
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			// This happens once a second
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups," + frames + " fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames
						+ " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	// Updates the code only once ever so often
	public void update() {
		key.update();
		level.update();
		ammoUpdate();
		player.setPowerLVL(power.getLVL());
		player.setReloadSpeed(reloadSpeed.getLVL());
		player.setTLVL();
		XPUpdate(player.powerXP, power.getXPNeeded(), power, "powerXP",
				Skill.POWER_CURVE);
		XPUpdate(player.reloadXP, reloadSpeed.getXPNeeded(), reloadSpeed,
				"reloadXP", Skill.RELOAD_CURVE);
	}

	// Renders the code as fast as possible
	public void render() {
		BufferStrategy bs = getBufferStrategy();

		// if there is nothing created then it will create an object
		if (bs == null) {
			// The parameter inside is the amount of objects in storage
			createBufferStrategy(3);
			return;
		}

		// clears the screen and then renders the new image
		screen.clear();

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		// Horizontal and Vertical position of Player
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int) xScroll, (int) yScroll, screen);
		// screen.renderSprite(0, 0, Sprite.playerShadow, false);		
		renderAbove((int) xScroll, (int) yScroll);

		// Sprite sprite = new Sprite(16, 16, 0xff);
		Sprite wall = new Sprite(16, 1, 1, SpriteSheet.tiles);
		screen.renderSprite(16, 16, wall, true);

		// goes through every value and sets it equal to the pixels array
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.drawImage(shade.getImage(), 0, 0, shade.getWidth(),
				shade.getLength(), null);
		displayLVL(g);
		g.setColor(ammoCol);
		g.setFont(new Font("Minecraftia", 0, 20));
		checkColor(g);
		reloadText(g);
		playerDamage(g);

		// when the 'e' key is pressed it toggle the statistics
		if (key.etoggle)
			displayInfo(g);
		g.dispose();
		bs.show();
	}

	public void renderAbove(int xScroll, int yScroll) {
		level.renderAbove(xScroll, yScroll, screen, Tile.wallAbove);
		level.renderAbove(xScroll, yScroll, screen, Tile.topLeftInv);
		level.renderAbove(xScroll, yScroll, screen, Tile.topRightInv);
	}

	public void reloadText(Graphics g) {
		// Sets up the text at the bottom of the screen for reloading
		if (player.noReload) {
			g.setColor(ammoCol);
			g.setFont(new Font("Minecraftia", 0, 20));
			if (player.justReloaded > 0) {
				if (player.getFireRate() > 0) {
					String reloading = "Reloading: "
							+ (int) (100 - (player.fireRateLog / player.reloadTime) * 100)
							+ "%";
					g.drawString(reloading,
							getWidth() - 200 - reloading.length(),
							getHeight() - 60);
				} else {
					g.drawString("Reloaded", getWidth() - 170, getHeight() - 60);
				}
			} else if (player.round != 0) {
				g.drawString("Cannot reload yet", getWidth() - 190,
						getHeight() - 60);
			}
		}
	}

	public void checkColor(Graphics g) {
		// Sets the ammo bar to green or red when reloaded
		if (key.r) {
			if (player.round == 0) {
				g.setColor(ammoCol);
				g.setFont(new Font("Minecraftia", 0, 20));
				g.drawString("No more rounds", getWidth() - 210,
						getHeight() - 60);
			}
			if (player.justReloaded > 0) {
				if (player.justReloaded == 1) {
					setAmmoCol(g, ammoGreenCol);
				} else {
					setAmmoCol(g, ammoRedCol);
				}
			} else {
				setAmmoCol(g, ammoRedCol);
			}
		} else {
			setAmmoCol(g, ammoCol);
		}

	}

	// Sets the color of the ammo bar
	public void setAmmoCol(Graphics g, Color c) {
		g.setColor(c);
		g.setFont(new Font("Minecraftia", 0, 30));
		String ammoSettings = player.ammo + "A/" + player.round + "R";
		g.drawString(ammoSettings, getWidth() - 180, getHeight() - 20);
	}

	// Displays stats for the game such as player location while e is held
	public void displayInfo(Graphics g) {
		Color infoCol = new Color(0xddffffff, true);
		g.setColor(infoCol);
		g.setFont(new Font("Minecraftia", 0, 35));
		g.drawString("X: " + ((int) (player.getX() / 16) - (level.getWidth() / 2))
				+ ", Y: " + ((int) (player.getY() / 16) - (level.getHeight() / 2) + 1),
				20, 50);
		drawXPStats(g, 20, 90, 20);
	}

	// Adds little damage stats
	public void playerDamage(Graphics g) {
		// if the player is being harmed it will display the damage on the
		// screen for a certain amount of time (wait)
		countdown = false;
		if (player.isHarmful()) {
			countdown = true;
		}
		if (countdown) {
			redCount++;
			wait--;
			if (redCount % 60 > 0 && redCount % 60 < 20) {
				screen.setRed(true);
			} else {
				screen.setRed(false);
			}
			if ((redCount % 180 > 0 && redCount % 180 < 3)
					|| (redCount % 180 > 5 && redCount % 180 < 8)) {
				screen.setShowing(false);
			} else {
				screen.setShowing(true);
			}
			if (wait > 0) {
				String damage = "" + player.dmg;
				g.setColor(Color.BLACK);
				g.setFont(new Font("Minecraftia", 0, 31));
				g.drawString(damage,
						(getWindowWidth() / 2) + player.getDmgWidth(),
						(getWindowHeight() / 2) + player.getDmgHeight());
				g.setColor(Color.WHITE);
				g.setFont(new Font("Minecraftia", 0, 30));
				g.drawString(damage,
						(getWindowWidth() / 2) + player.getDmgWidth(),
						(getWindowHeight() / 2) + player.getDmgHeight());
			} else {
				countdown = false;
				wait = 180;
			}

		} else {
			redCount = 0;
			screen.setRed(false);
			screen.setShowing(true);
		}
	}

	// Displays the Character's Level in the top Right Corner
	public void displayLVL(Graphics g) {
		g.setFont(new Font("Minecraftia", 0, 27));
		Rectangle rect = new Rectangle();
		rect.setBounds((int) (getWidth() * .75), 20, 200, 60);
		g.drawImage(displayLVL.getImage(), rect.x, rect.y,
				displayLVL.getWidth(), displayLVL.getLength(), null);
		// Color rectCol = new Color(0xaa000000, true);
		// g.setColor(rectCol);
		// g.fillRect((int) rect.getX(), (int) rect.getY(), (int)
		// rect.getWidth(), (int) rect.getHeight());
		Rectangle health = new Rectangle();
		health.setBounds(
				(int) ((getWidth() * .75) + (rect.getWidth() - (rect.getWidth() * .5))),
				(int) (rect.getY() + rect.getHeight() - 18),
				(int) (rect.getWidth() * .6), 5);
		health.translate((int) (-.5 * (rect.getWidth() - health.getWidth())), 0);

		// healthWidth is between 0 and 1 and is the percent health player has
		double healthPercent = (double) player.health / player.hp;

		/*
		 * changes the hue from green to red by going through yellow. First it
		 * brings up the red until they are both at 217, then it brings down the
		 * green.
		 */

		int red, blue, green;
		if (healthPercent > .5) {
			red = (int) (217 * (1 - healthPercent) * 2);
			green = 217;
			blue = 0;
		} else {
			red = 217;
			green = (int) (217 * healthPercent * 2);
			blue = 0;
		}
		Color healthCol = new Color(red, green, blue);
		g.setColor(healthCol);
		g.fillRect((int) health.getX(), (int) health.getY(),
				(int) (health.getWidth() * healthPercent),
				(int) health.getHeight());
		Color white = new Color(0xffffffff, false);
		g.setColor(white);
		g.drawString("" + player.getTLVL(),
				(int) (rect.x + (rect.getWidth() * .7)),
				(int) (rect.getY() + (rect.getHeight() * .6)));
	}

	// x location, y location, and change in y
	public void drawXPStats(Graphics g, int x, int y, int dif) {
		g.setFont(new Font("Minecraftia", 0, 18));
		g.drawString("Power Level: " + power.getLVL(), x, y);
		g.drawString("Power XP: " + player.powerXP, x, y + dif);
		g.drawString("XP until next Level: "
				+ (int) (power.getXPNeeded() - player.powerXP), x, y + dif * 2);
		g.drawString("Reload Speed: " + reloadSpeed.getLVL(), x, y + dif * 3);
		g.drawString("Reload XP: " + player.reloadXP, x, y + dif * 4);
		g.drawString("XP until next Level: "
				+ (int) (reloadSpeed.getXPNeeded() - player.reloadXP), x, y
				+ dif * 5);
	}

	// Updates the ammo box by reloading it
	public void ammoUpdate() {
		if (key.r) {
			player.reload();
		}
	}

	// Updates the XP
	public void XPUpdate(int xp, double xpNeeded, Skill skill, String whichXP,
			double curve) {
		// increases the skill level for power every time the XP reaches the
		// XPNeeded
		if (xp >= xpNeeded) {
			skill.increaseSkillLVL();
			player.resetXP(whichXP);
			skill.setXPNeeded(curve);
			player.setTLVL();
		}
	}

	// Runs the whole program
	public static void main(String[] args) {
		// Sets up the game before it displays it
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
