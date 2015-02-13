package pstoriz.desacore.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import pstoriz.desacore.entity.mob.Bandit;
import pstoriz.desacore.entity.mob.WiseMan;

public class SpawnLevel extends Level {
	
	public SpawnLevel(String path) {
		super(path);
	}
	
	//Loading an image, calculating width and height, and then converting it into an array of pixels, then tells the color of each pixel
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[ w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		add(new Bandit(height / 2, width / 2, 1));
		add(new WiseMan(height / 2, width / 2));
	}

	
	//Converts array of pixels into tiles
	protected void generateLevel() {
	}
}
