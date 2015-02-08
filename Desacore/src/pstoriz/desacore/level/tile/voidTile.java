package pstoriz.desacore.level.tile;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;

//Blank tile with a particular color. Used for rendering tiles that don't exist
public class voidTile extends Tile {

	public voidTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
