package pstoriz.desacore.level.tile;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;

public class BottomRightCornerTile extends Tile {
	
	public BottomRightCornerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this); //Moves from pixel to tile precision
	}
	
	public boolean solid() {
		return true;
	}

}
