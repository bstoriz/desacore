package pstoriz.desacore.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.entity.particle.Particle;
import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.graphics.ImageLoader;
import pstoriz.desacore.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	// contains color values of every pixel in that level
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	
	public ImageLoader glow = new ImageLoader("/textures/appearance/Glow.png",
			"/textures/appearance/Glow_alpha.png", 144, 144);

	// public static Level spawn = new Level("/location");

	// Constructor for the level
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	@SuppressWarnings("unused")
	private void time() {

	}

	// x and y is position of Entity. size is
	// the size of object
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		// Collision detection for the corners
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	// Corner "pin" almost. Defines which area of the map we want to render
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		// renders all the tiles until it hits the end of the screen
		int x0 = xScroll >> 4; // Same as divided by 16 (Size of our tiles).
								// Pixel level not tile level
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4; // Define render region of
														// the screen
		for (int y = y0; y < y1; y++) { // Cycles through every pixel
			for (int x = x0; x < x1; x++) { // Rendering from top part of screen
											// from bottom part of the screen
				getTile(x, y).render(x, y, screen); // Tile precision
			}
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}

	// Seperate render method for the Above Tile
	public void renderAbove(int xScroll, int yScroll, Screen screen, Tile tile) {
		screen.setOffset(xScroll, yScroll);
		// renders all the tiles until it hits the end of the screen
		int x0 = xScroll >> 4; // Same as divided by 16 (Size of our tiles).
								// Pixel level not tile level
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4; // Define render region of
														// the screen
		for (int y = y0; y < y1; y++) { // Cycles through every pixel
			for (int x = x0; x < x1; x++) { // Rendering from top part of screen
											// from bottom part of the screen
				if (getTile(x, y).isAbove()) {
					getTile(x, y).render(x, y, screen); // Tile precision
				}
			}
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else {
			entities.add(e);			
		}
	}

	// Renders specific tiles
	public Tile getTile(int x, int y) {
		// If you get outside the map boundaries it returns a voidTile
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_stone1)
			return Tile.stone1;
		if (tiles[x + y * width] == Tile.col_stone2)
			return Tile.stone2;
		if (tiles[x + y * width] == Tile.col_stone3)
			return Tile.stone3;
		if (tiles[x + y * width] == Tile.col_grass)
			return Tile.grass;
		if (tiles[x + y * width] == Tile.col_wood)
			return Tile.wood;
		if (tiles[x + y * width] == Tile.col_water)
			return Tile.water;
		if (tiles[x + y * width] == Tile.col_lava)
			return Tile.lava;
		if (tiles[x + y * width] == Tile.col_wall)
			return Tile.wall;
		if (tiles[x + y * width] == Tile.col_wallAbove)
			return Tile.wallAbove;
		if (tiles[x + y * width] == Tile.col_wallLeft)
			return Tile.wallLeft;
		if (tiles[x + y * width] == Tile.col_wallRight)
			return Tile.wallRight;
		if (tiles[x + y * width] == Tile.col_topLeftCorner)
			return Tile.topLeftCorner;
		if (tiles[x + y * width] == Tile.col_topRightCorner)
			return Tile.topRightCorner;
		if (tiles[x + y * width] == Tile.col_bottomLeftCorner)
			return Tile.bottomLeftCorner;
		if (tiles[x + y * width] == Tile.col_bottomRightCorner)
			return Tile.bottomRightCorner;
		if (tiles[x + y * width] == Tile.col_topRightInv)
			return Tile.topRightInv;
		if (tiles[x + y * width] == Tile.col_topLeftInv)
			return Tile.topLeftInv;
		return Tile.voidTile;
	}

	public boolean getIsAbove(Tile tile) {
		return tile.isAbove();
	}

	public Tile chooseTile(Tile tile0, Tile tile1, Tile tile2) {
		Random r = new Random();
		int num = r.nextInt(2);
		if (num == 0)
			return tile0;
		if (num == 1)
			return tile1;
		return tile2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
