package pstoriz.desacore.graphics;

import java.awt.Color;
import java.util.Random;

public class Sprite {
	
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	private ImageLoader image;
	
	//creates the new sprites
	public static Sprite stone1 = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite stone2 = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite stone3 = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite stoneRan = ranSprite(stone1, stone2, stone3);
	public static Sprite wall = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite wallAbove = new Sprite(wall);
	public static Sprite topLeftCorner = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite topRightCorner = new Sprite(16, 2, 1, SpriteSheet.tiles);
	public static Sprite bottomLeftCorner = new Sprite(16, 0, 3, SpriteSheet.tiles);
	public static Sprite bottomRightCorner = new Sprite(16, 2, 3, SpriteSheet.tiles);
	public static Sprite topRightInv = new Sprite(16, 0, 4, SpriteSheet.tiles);
	public static Sprite topLeftInv = new Sprite(16, 1, 4, SpriteSheet.tiles);
	public static Sprite wallLeft = new Sprite(16, 0, 2, SpriteSheet.tiles);
	public static Sprite wallRight = new Sprite(16, 2, 2, SpriteSheet.tiles);
	public static Sprite grass = new Sprite(16, 3, 2, SpriteSheet.tiles);
	public static Sprite water = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite lava = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite wood = new Sprite(16, 3, 1, SpriteSheet.tiles);
	public static Sprite wool = new Sprite(16, 4, 1, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0);
	
	public static Sprite player_up = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_down = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_left = new Sprite(32, 3, 5, SpriteSheet.tiles);
	public static Sprite player_right = new Sprite(32, 1, 5, SpriteSheet.tiles);
	
	public static Sprite player_up_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_up_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);
	public static Sprite player_down_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_down_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);
	public static Sprite player_left_1 = new Sprite(32, 3, 6, SpriteSheet.tiles);
	public static Sprite player_left_2 = new Sprite(32, 3, 7, SpriteSheet.tiles);
	public static Sprite player_right_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_right_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);
	
	public static Sprite wiseMan = new Sprite(32, 0, 0, SpriteSheet.wiseMan_down);
	public static Sprite bandit = new Sprite(32, 0, 0, SpriteSheet.bandit_down);
	public static Sprite star = new Sprite(32, 0, 0, SpriteSheet.star_down);
	public static Sprite shooter = new Sprite(32, 0, 0, SpriteSheet.shooter_down);
	
	//Projectiles
	public static Sprite projectile_fire_d = new Sprite(16, 0, 0, SpriteSheet.projectiles);
	
	//Particls
	public static Color fire = new Color(0xFF6A00);
	public static Sprite particle_normal = new Sprite(3, fire.getRGB());
	public static Color blood = new Color(0xAD0000);
	public static Sprite particle_blood = new Sprite(2, blood.getRGB());
	public static Color waterCol = new Color(0x22A2F2);
	public static Sprite particle_water = new Sprite(2, waterCol.getRGB());
	
	public static Sprite playerShadow = new Sprite(144, SpriteSheet.playerShadow);
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	//Constructs a sprite with parameters
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int size, ImageLoader image) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.image = image;
	}
	
	public Sprite(Sprite other) {
		SIZE = other.SIZE;
		width = other.width;
		height = other.height;
		x = other.x;
		y = other.y;
		sheet = other.sheet;
		pixels = other.pixels;
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	private void setColor(int color) {
		//Covers every single pixel
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}
	
	//you don't want to be able to change width and height from the outside
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.HEIGHT];
			}
		}
	}
	
	//Random number generator
	public static int RandInt(int min, int max) {
		Random r = new Random();
		int randomNum = r.nextInt((max - min) + 1) + min;
		//System.out.println(randomNum);
		return randomNum;
	}
	
	//Randomly selects a sprite
	public static Sprite ranSprite(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
		int num = RandInt(0, 2);
		if (num == 0) {
			//System.out.println("Sprite1");
			return sprite1;
		}
		if (num == 1) {
			//System.out.println("Sprite2");
			return sprite2;
		}
		if (num == 2) {
			//System.out.println("Sprite3");			
			return sprite3;
		}
		return null;
	}
	
}
