package pstoriz.desacore.entity.mob;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.entity.projectile.FireDProjectile;
import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.graphics.Sprite;

public abstract class Mob extends Entity {
	
	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;
	protected boolean harmful = false;
	protected boolean slow = false;
	protected boolean alive = true;
	protected boolean walking = false;
	protected boolean splash = false;
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction direction;
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	
	public int ammo, round, Tlvl, health, prehealth, range;
	public boolean noReload, alerted = false;
	protected int hbXmul, hbYmul;
	protected int hbXmod, hbYmod;
	
	//x and y variables need to change when it moves
	public void move(double xa, double ya) {
		//Separates out which way you're colliding so you can slide
		if (xa != 0 && ya != 0) {
			//Runs its twice so that it will sort out and go the right way
			move(xa, 0);
			move(0, ya);
			return;
		}
		//Decides which way the entity is facing
		if (xa > 0) {
			dir = RIGHT;
			direction = Direction.RIGHT;
		}
		if (xa < 0) {
			dir = LEFT;
			direction = Direction.LEFT;
		}
		if (ya > 0) {
			dir = DOWN;
			direction = Direction.DOWN;
		}
		if (ya < 0) {
			dir = UP;
			direction = Direction.UP;
		}
		
		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				// if xa is between 1 and 0
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				// if xa is between 1 and 0
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}
	
	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
		
	//Abstract means it doesn't have to implement anything
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected void playerShoot(double x, double y, int dir) {
		Projectile p = new FireDProjectile(x, y, this.dir);
		level.add(p);
	}
	
	private boolean collision(double xa, double ya) {
		boolean solid = false;
		//Collision detection for the corners
		for (int c = 0; c < 4; c++) {
			harmful = false;
			slow = false;
			splash = false;
			int xt = (int) ((x + xa) - c % hbXmul + hbXmod) / 16;
			int yt = (int) ((y + ya) - c / 2 * hbYmul + hbYmod) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix --;
			if (c / 2 == 0) iy --;
			if (level.getTile(ix, iy).solid()) solid = true;
			if (level.getTile(ix, iy).isHarmful()) harmful = true;
			if (level.getTile(ix, iy).isSlow()) slow = true;
			if (level.getTile(ix, iy).water()) splash = true;


		}
		return solid;
	}
	
}

