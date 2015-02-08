package pstoriz.desacore.entity.mob;

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
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	
	public int ammo, round, Tlvl, health, prehealth;
	public boolean noReload = false;
	
	//x and y variables need to change when it moves
	public void move(int xa, int ya) {
		//Separates out which way you're colliding so you can slide
		if (xa != 0 && ya != 0) {
			//Runs its twice so that it will sort out and go the right way
			move(xa, 0);
			move(0, ya);
			return;
		}
		//Decides which way the entity is facing
		if (xa > 0) dir = RIGHT;
		if (xa < 0) dir = LEFT;
		if (ya > 0) dir = DOWN;
		if (ya < 0) dir = UP;
				
		//as long as its not colliding, it will move
		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}
	
	//Being overwritten by player
	public void update() {
		
	}
	
	protected void playerShoot(int x, int y, int dir) {
		Projectile p = new FireDProjectile(x, y, this.dir);
		level.add(p);
	}
	
	private boolean collision(int xa, int ya) {
		boolean solid = false;
		//Collision detection for the corners
		int w = 15;
		int h = - 8;
		for (int c = 0; c < 4; c++) {
			harmful = false;
			int xt = ((x + xa) + c % 2 * 27 - w) / 16;
			int yt = ((y + ya) + c / 2 * 12 + h) / 16;
			if (level.getTile(xt, yt).solid()) solid = true;
			int xh = ((x + xa) + c % 2) / 16;
			int yh = ((y + ya) + c / 2) / 16;
			if (level.getTile(xh, yh).isHarmful()) harmful = true;
			if (level.getTile(xh, yh).isSlow()) slow = true;

		}
		return solid;
	}
	
	public void render() {
		
	}

}

