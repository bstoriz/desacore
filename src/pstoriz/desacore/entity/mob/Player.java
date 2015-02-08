package pstoriz.desacore.entity.mob;

import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.projectile.FireDProjectile;
import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.input.Keyboard;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private int hanim = 0;
	private int count = 0;
	private int ranDmgWidth, ranDmgHeight;
	private boolean walking = false;
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
	private int xa, ya;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_up;
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
		if (fireRate > 0) fireRate--;
		if (fireRate > 0) fireRateLog--;
		xa = 0;
		ya = 0;

		if (anim < 7500)
			anim++;
		else
			anim = 0;

		// moves player
		if (input.up)
			ya--;
		if (input.down)
			ya++;
		if (input.left)
			xa--;
		if (input.right)
			xa++;

		// if shift is held the player moves faster
		if (input.up && input.shift)
			ya--;
		if (input.down && input.shift)
			ya++;
		if (input.left && input.shift)
			xa--;
		if (input.right && input.shift)
			xa++;

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
			playerShoot(x - 8, y, dir);
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
		int speed = 20;
		//Walking
		if (!input.shift) {
			animateRun(speed);
		}
		//Running
		if (input.shift) {
			animateRun(speed / 2);
		}
		screen.renderPlayer(x - 16, y - 16, sprite);
	}
	
	public void animateRun(int speed) {
		animate(0, Sprite.player_up, Sprite.player_up_1,
				Sprite.player_up_2, speed);
		animate(1, Sprite.player_right, Sprite.player_right_1,
				Sprite.player_right_2, speed);
		animate(2, Sprite.player_down, Sprite.player_down_1,
				Sprite.player_down_2, speed);
		animate(3, Sprite.player_left, Sprite.player_left_1,
				Sprite.player_left_2, speed);
	}

	public void animate(int direction, Sprite sprite0, Sprite sprite1,
			Sprite sprite2, int animSpeed) {
		if (dir == direction) {
			sprite = sprite0;
			if (walking) {
				if (anim % animSpeed > (animSpeed / 2)) {
					sprite = sprite1;
				} else {
					sprite = sprite2;
				}
			}
		}
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
	
	public boolean getHarmful() {
		return harmful;
	}
	
}
