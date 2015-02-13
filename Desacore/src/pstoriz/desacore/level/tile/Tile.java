package pstoriz.desacore.level.tile;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;

public class Tile {

	public Sprite sprite;
		
	public static Tile stoneRan = new StoneRanTile(Sprite.stoneRan);
	public static Tile stone1 = new Stone1Tile(Sprite.stone1);
	public static Tile stone2 = new Stone2Tile(Sprite.stone2);
	public static Tile stone3 = new Stone3Tile(Sprite.stone3);
	public static Tile wall = new WallTile(Sprite.wall);
	public static Tile wallAbove = new WallAboveTile(Sprite.wallAbove);
	public static Tile topLeftCorner = new TopLeftCornerTile(Sprite.topLeftCorner);
	public static Tile topRightCorner = new TopRightCornerTile(Sprite.topRightCorner);
	public static Tile bottomLeftCorner = new BottomLeftCornerTile(Sprite.bottomLeftCorner);
	public static Tile bottomRightCorner = new BottomRightCornerTile(Sprite.bottomRightCorner);
	public static Tile topRightInv = new TopRightInvTile(Sprite.topRightInv);
	public static Tile topLeftInv = new TopLeftInvTile(Sprite.topLeftInv);
	public static Tile wallLeft = new WallLeftTile(Sprite.wallLeft);
	public static Tile wallRight = new WallRightTile(Sprite.wallRight);
	public static Tile grass = new Grass(Sprite.grass);
	public static Tile water = new Water(Sprite.water);
	public static Tile lava = new Lava(Sprite.lava);
	public static Tile wood = new Wood(Sprite.wood);
	
	public static Tile voidTile = new voidTile(Sprite.voidSprite);
	
	public static final int col_stone1 = 0xff9c9c9c;
	public static final int col_stone2 = 0xffa7a5a5;
	public static final int col_stone3 = 0xffb8b8b8;
	public static final int col_grass = 0xff1dcb00;
	public static final int col_wood = 0xff988945;
	public static final int col_water = 0xff00a8ff;
	public static final int col_lava = 0xffff7200;
	public static final int col_wall = 0xff000000;
	public static final int col_wallLeft= 0xff132114;
	public static final int col_wallRight = 0xff352137;
	public static final int col_wallAbove = 0xff4b4b4b;
	public static final int col_topLeftCorner = 0xff425b48;
	public static final int col_topRightCorner = 0xff424d5b;
	public static final int col_bottomLeftCorner = 0xff5b4242;
	public static final int col_bottomRightCorner = 0xff58425b;
	public static final int col_topRightInv = 0xff002b61;
	public static final int col_topLeftInv = 0xff1a5428;
			
	//You require a sprite for each tile you create
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	//Every tile renders itself
	//Even if you're returning a stoneTile, you're still returning a tile
	public void render(int x, int y, Screen screen) {
	
	}
	
	//If its not different its not necessary to change for the ___tile class
	public boolean solid() {
		return false;
	}
	
	public boolean isHarmful() {
		return false;
	}
	
	public boolean glow() {
		return false;
	}
	
	public boolean isAbove() {
		return false;
	}
	
	public boolean isSlow() {
		return false;
	}
}
