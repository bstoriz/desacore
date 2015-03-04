package pstoriz.desacore.entity.projectile;

import java.util.Random;

import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny; //change each update
	protected double distance;
	protected double speed, range, damage;
	protected final Random r = new Random();
	protected char lvl; //How powerful the projectile is
	
	public Projectile(double x, double y, int dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		
	}
}
