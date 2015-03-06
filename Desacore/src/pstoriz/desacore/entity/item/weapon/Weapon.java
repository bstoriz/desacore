package pstoriz.desacore.entity.item.weapon;

import pstoriz.desacore.entity.item.Item;

public class Weapon extends Item {
	
	protected char rank;
	protected int damage;
	protected double durability;
	protected int amount;
	
	public Weapon(Weapon w) {
		super((Item) w);
		//weapon data here
	}

}
