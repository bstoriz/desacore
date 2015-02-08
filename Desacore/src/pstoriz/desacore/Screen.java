package pstoriz.desacore;

import java.util.Random;

import pstoriz.desacore.entity.projectile.Projectile;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;
import pstoriz.desacore.level.tile.Tile;

public class Screen {

	// width and height of the Screen
	public int width, height;
	// array for the pixels
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;	
	public int xOffset, yOffset;	
	private boolean red = false;
	private boolean showing = true;
	// How big the map is
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];

	private Random random = new Random();

	// Constructor for the Screen class
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
			tiles[0] = 0;
		}
	}

	// Clears the pixels
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;		
		}
		//setting the color of pixels to the color of those sprites
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}
	
	//Motion tracking footage. Sticks to the land so that when we move the sprite moves with the map
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;		
		}
		//setting the color of pixels to the color of those sprites
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}

	// only renders the tiles you see
	public void renderTile(int xp, int yp, Tile tile) {
		//Adjusting the location by the offset
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0) xa = 0;
				// renders the tile onto the screen
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		//Adjusting the location by the offset
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0) xa = 0;
				// renders the tile onto the screen
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp;
			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * 32];
				if (red) col = hue(col, 'r');
				// renders the tile onto the screen
				if (col != 0xffff00ff && col != 0xff00ff && showing) pixels[xa + ya * width] = col;
			}
		}
	}
	
	//applies a red hue by converting from hex to dec and setting the red channel to 255
	public int hue(int color, char channel) {
		String col = Integer.toHexString(color);
		String r = col.substring(2, 4);
		String g = col.substring(4, 6);
		String b = col.substring(6);
		if (color != 0xffff00ff && channel == 'r') r = "ff";
		if (color != 0xffff00ff && channel == 'g') g = "ff";
		if (color != 0xffff00ff && channel == 'b') b = "ff";
		col = r + g + b;
		return Integer.parseInt(col, 16);
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void setShowing(boolean isShowing) {
		showing = isShowing;
	}
	
	public void setRed(boolean isRed) {
		red = isRed;
	}
	
	public boolean getRed() {
		return red;
	}

}
