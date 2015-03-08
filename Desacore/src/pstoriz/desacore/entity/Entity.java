package pstoriz.desacore.entity;

import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.level.Level;

public class Entity {
	
	//Controls location of an entity on the map
	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {
		
	}
	
	//Entities move, thats why they have their own x and y
	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int) x,(int) y, sprite, true);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	//Removes the entity from level
	public void remove() {
		removed = true;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	//Initializes level
	public void init(Level level) {
		this.level = level;
	}

}
