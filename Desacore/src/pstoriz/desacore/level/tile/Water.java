package pstoriz.desacore.level.tile;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;

public class Water extends Tile {
	
	public Water(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this); //Moves from pixel to tile precision
	}
	
	public boolean isSlow() {
		return true;
	}

}
