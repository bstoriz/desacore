package pstoriz.desacore.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//maps out every pixel of the spritesheet
public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 256);
	public static SpriteSheet projectiles = new SpriteSheet("/textures/projectiles/FireProjectile.png", 48);
	public static ImageLoader playerShadow = new ImageLoader("/textures/appearance/PlayerShadow.png", "/textures/appearance/PlayerShadow_alpha.png", 144, 144);
	
	public static SpriteSheet player = new SpriteSheet("/textures/player_sprite.png", 128, 96);
	public static SpriteSheet player_down = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 1, 0, 1, 3, 32);

	public static SpriteSheet wiseMan = new SpriteSheet("/textures/wise_man_sprite_1.png", 128, 96);
	public static SpriteSheet wiseMan_down = new SpriteSheet(wiseMan, 2, 0, 1, 3, 32);
	public static SpriteSheet wiseMan_up = new SpriteSheet(wiseMan, 0, 0, 1, 3, 32);
	public static SpriteSheet wiseMan_left = new SpriteSheet(wiseMan, 3, 0, 1, 3, 32);
	public static SpriteSheet wiseMan_right = new SpriteSheet(wiseMan, 1, 0, 1, 3, 32);
	
	public static SpriteSheet bandit = new SpriteSheet("/textures/bandit_spritesheet_1.png", 128, 96);
	public static SpriteSheet bandit_down = new SpriteSheet(bandit, 2, 0, 1, 3, 32);
	public static SpriteSheet bandit_up = new SpriteSheet(bandit, 0, 0, 1, 3, 32);
	public static SpriteSheet bandit_left = new SpriteSheet(bandit, 3, 0, 1, 3, 32);
	public static SpriteSheet bandit_right = new SpriteSheet(bandit, 1, 0, 1, 3, 32);
	
	public static SpriteSheet star = new SpriteSheet("/textures/bandit_spritesheet_2.png", 128, 96);
	public static SpriteSheet star_down = new SpriteSheet(star, 2, 0, 1, 3, 32);
	public static SpriteSheet star_up = new SpriteSheet(star, 0, 0, 1, 3, 32);
	public static SpriteSheet star_left = new SpriteSheet(star, 3, 0, 1, 3, 32);
	public static SpriteSheet star_right = new SpriteSheet(star, 1, 0, 1, 3, 32);
	
	public static SpriteSheet shooter = new SpriteSheet("/textures/shooter_spritesheet.png", 128, 96);
	public static SpriteSheet shooter_down = new SpriteSheet(shooter, 2, 0, 1, 3, 32);
	public static SpriteSheet shooter_up = new SpriteSheet(shooter, 0, 0, 1, 3, 32);
	public static SpriteSheet shooter_left = new SpriteSheet(shooter, 3, 0, 1, 3, 32);
	public static SpriteSheet shooter_right = new SpriteSheet(shooter, 1, 0, 1, 3, 32);
	
	
	private Sprite[] sprites;
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	//Constructor for each spritesheet
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			//translates the loaded image into pixels
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
