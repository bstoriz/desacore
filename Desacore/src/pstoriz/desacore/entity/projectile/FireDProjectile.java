package pstoriz.desacore.entity.projectile;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.mob.Mob;
import pstoriz.desacore.entity.spawner.ParticleSpawner;
import pstoriz.desacore.entity.spawner.Spawner;
import pstoriz.desacore.graphics.Sprite;


//For the FireProjectile class
public class FireDProjectile extends Projectile {

	public static final double FIRE_RATE = 20; //Higher is slower
	public static final int AMMO_CAP = 10;
	public static final int ROUND_CAP = 10;
	public static final int RELOAD_TIME = 100;
	
	public FireDProjectile(int x, int y, int dir) {
		super(x, y, dir);
		range = (r.nextDouble() * 10) + 200;
		speed = (r.nextDouble() * 2) + 3;
		damage = 20;
		sprite = Sprite.projectile_fire_d;
		lvl = 'D';
		
		//shoots the projectile the same way the player is facing
		if (dir == Mob.UP) {
			nx = 0;
			ny = speed * -1;
		}
		
		if (dir == Mob.DOWN) {
			nx = 0;
			ny = speed;
		}
		
		if (dir == Mob.RIGHT) {
			nx = speed;
			ny = 0;
		}
		
		if (dir == Mob.LEFT) {
			nx = speed * -1;
			ny = 0;
		}
		
	}
	
	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 7, 5, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 60, 20, level, Sprite.particle_normal));
			remove();
		}
		move();
	}
	
	protected void move() {
		x += nx;
		y += ny;			
		if (distance() > range) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x)) + (yOrigin - y) * (yOrigin - y));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile(x, y, this);
		
	}

}
