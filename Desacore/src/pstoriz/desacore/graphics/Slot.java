package pstoriz.desacore.graphics;

import java.util.ArrayList;
import java.util.List;

import pstoriz.desacore.entity.item.Item;

public class Slot {
	
	private static int amountSlots = 0;
	private int quantity, capacity;
	private int location;
	private boolean selected;
	private Sprite sprite;
	private boolean occupied;
	private List<Item> items = new ArrayList<Item>();
	
	public Slot() {
		amountSlots++;
		location = amountSlots;
		selected = false;
		sprite = null;
		occupied = true;
		quantity = 0;
	}
	
	
	public void addItem(Item i) {
		items.add(i);
	}
	
	public void removeItem(Item i) {
		items.remove(i);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void fill() {
		quantity = capacity;
	}
	
	public void use() {
		if (quantity > 0) quantity--;
		else {
			occupied = false;
			sprite = null;
		}
		
	}

}
