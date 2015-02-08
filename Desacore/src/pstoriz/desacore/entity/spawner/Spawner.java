package pstoriz.desacore.entity.spawner;

import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.level.Level;

public class Spawner extends Entity {
	
	//creates a custom variable called Type, the values of Type are equal to the following
	public enum Type {
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
