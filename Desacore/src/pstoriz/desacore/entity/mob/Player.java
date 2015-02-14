package pstoriz.desacore.entity.mob;

import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.projectile.FireDProjectile;
import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.entity.spawner.ParticleSpawner;
import pstoriz.desacore.graphics.AnimatedSprite;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;
import pstoriz.desacore.input.Keyboard;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int hanim = 0;
	private int count = 0;
	private int ranDmgWidth, ranDmgHeight;
	
	// -- adding animated sprites --
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;
	
	private Random r = new Random();
	
	Projectile p;
	private double fireRate;
	
	public int ammoCap = 0;
	public int roundCap = 0;
	public int ammo, round, powerXP, reloadXP, powerLVL, health, dmg, healthLVL, hp;
	public boolean noReload = false;
	public int justReloaded = 0;
	public int reloadTime, reloadSpeed;
	public double fireRateLog;
	private double xa, ya;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_up;
		animSprite = down;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		fireRate = FireDProjectile.FIRE_RATE;
		ammoCap = FireDProjectile.AMMO_CAP;
		ammo = ammoCap;
		roundCap = FireDProjectile.ROUND_CAP;
		round = roundCap;
		reloadTime = FireDProjectile.RELOAD_TIME;
		fireRateLog = reloadTime;
		powerXP = 0;
		reloadXP = 0;
		Tlvl = 1;
		reloadSpeed = 1;
		powerLVL = 1;
		healthLVL = 1;
		health = 100;
		dmg = 0;
		hp = health + healthLVL * 5;
	}

	public void update() {		
		if (walking) {
			if (input.shift) {
				animSprite.setFrameRate(4);
			} else {
				animSprite.setFrameRate(7);
			}
			animSprite.update();
		}
		else animSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		if (fireRate > 0) fireRateLog--;
		xa = 0;
		ya = 0;
		double speed = 1.5;
		if (slow) speed -= .6;
		if (splash) level.add(new ParticleSpawner((int) x, (int) y, 20, 2, level, Sprite.particle_water));
		if (input.shift) speed *= 1.5;
		// moves player
		// if shift is held the player moves faster
		if (input.up) {
			animSprite = up;
			ya -= speed;
		} else if (input.down) {
			animSprite = down;
			ya += speed;
		}
		if (input.left) {
			animSprite = left;
			xa -= speed;
		} else if (input.right) {
			animSprite = right;
			xa += speed;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		updateHealth();
		clear();
		updateShooting();
	}
	
	//updates the player's health 
	public void updateHealth() {
		int speed = 50;
		int regenSpeed = 30;
		prehealth = health;
		count++;
		if (harmful && health > 0) {
			if (hanim % speed == 0) {
				// Emits a blood particle for the damage
				level.add(new ParticleSpawner((int) x,(int) y, 40,
						 dmg * 20, level, Sprite.particle_blood));
				health -= r.nextInt(15) + 5;
				dmg = prehealth - health;
				ranDmgWidth = r.nextInt(100) - 50;
				ranDmgHeight = r.nextInt(100) - 50;
			}
			if (health < 0) {
				health = 0;
			}
			hanim++;
		} else {
			hanim = 0;
		}
		//slows down health regeneration by only regaining health every 10 ticks
		if (count % regenSpeed == 0 && health < hp && health > 0) {
			health++;
		}
		if (count > 10000) count = 0;
		
	}
	
	//sets a random location to place the damage splatter
	public int getDmgWidth() {
		return ranDmgWidth;
	}
		
	public int getDmgHeight() {
		return ranDmgHeight;
	}
	
	//Removes projectiles from the screen by taking them out of the array
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	public void updateShooting() {
		if (input.space && fireRate <= 0 && ammo > 0) {
			int dir = this.dir;
			playerShoot((int) (x - 8), (int) y, dir);
			powerXP = powerXP + r.nextInt(5) + 1;
			ammo--;
			justReloaded = 0;
			noReload = false;
			fireRate = FireDProjectile.FIRE_RATE / (1 + powerLVL / 10.0);
		}
	}
	
	//Resets the XP
	public void resetXP(String whichXP) {
		if (whichXP.equals("powerXP")) powerXP = 0;
		if (whichXP.equals("reloadXP")) reloadXP = 0;
	}
	
	public void reload() {
		if (ammo > (ammoCap / 2)) noReload = true;
		if (round > 0 && ammo < (ammoCap / 2) + 1) {
			ammo = ammoCap;
			round--;
			justReloaded++;
			reloadXP += r.nextInt(3) + 1;
			double speed = reloadTime / (1 + reloadSpeed / 20.0);
			fireRate = speed;
			fireRateLog = speed;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderPlayer((int) (x - 16), (int) (y - 16), sprite);
	}
	
	public double getFireRate() {
		return fireRate;
	}

	public void setReloadSpeed(int lvl) {
		reloadSpeed = lvl;
	}

	public void setPowerLVL(int lvl) {
		powerLVL = lvl;
	}
	
	public void setTLVL() {
		Tlvl = (int) ((powerLVL + reloadSpeed) / 2);
	}
	
	public int getTLVL() {
		return Tlvl;
	}
	
	public boolean isSlow() {
		return slow;
	}
	
	public boolean isHarmful() {
		return harmful;
	}
	
}
