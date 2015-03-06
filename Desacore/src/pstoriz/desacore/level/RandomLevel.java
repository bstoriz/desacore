package pstoriz.desacore.level;

import java.util.Random;

public class RandomLevel extends Level{
	
	private static final Random random = new Random();
	

	//Parameters inputed here get inputed into the Level
	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	//whatever code is inserted here will run
	protected void generateLevel() {
		//These cycle through every index in that tile insuring we will it completely
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tilesInt[x + y * width] = random.nextInt(4);
			}
		}
	}

}
