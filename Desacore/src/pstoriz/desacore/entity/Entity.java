package pstoriz.desacore.entity;

import java.util.Random;

import pstoriz.desacore.Screen;
import pstoriz.desacore.level.Level;

public abstract class Entity {
	
	//Controls location of an entity on the map
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
		
	}
	
	//Entities move, thats why they have their own x and y
	public void render(Screen screen) {
		
	}
	
	//Removes the entity from level
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	//Initializes level
	public void init(Level level) {
		this.level = level;
	}

}
