package pstoriz.desacore.entity.item;

import pstoriz.desacore.entity.item.armor.Armor;
import pstoriz.desacore.entity.item.consumable.Consumable;
import pstoriz.desacore.entity.item.food.Food;
import pstoriz.desacore.entity.item.junk.Junk;
import pstoriz.desacore.entity.item.potion.Potion;
import pstoriz.desacore.entity.item.weapon.Weapon;

public class Item {
	
	//class data
	protected String name;
	protected int itemID;
	protected char symbol;
	protected double weight;
	protected double value;
	protected String discription;
	
	private static int id;
	
	//constructor
	public Item() {
		this.name = "";
		itemID = id;
		id++;
		symbol = ' ';
		weight = 0.0;
		value = 0.0;
	}
	
	public Item(Item i) {
		this.name = i.name;
	    this.symbol = i.symbol;
	    this.weight = i.weight;
	    this.value = i.value;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return itemID;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getValue() {
		return value;
	}
	
	public boolean isConsumable() {
		return this instanceof Consumable;
		
	}
	
	public boolean isFood() {
		return this instanceof Food;
		
	}
	
	public boolean isPotion() {
		return this instanceof Potion;
		
	}
	
	public boolean isWeapon() {
		return this instanceof Weapon;
		
	}
	
	public boolean isArmor() {
		return this instanceof Armor;
		
	}
	
	public boolean isJunk() {
		return this instanceof Junk;
		
	}

}
