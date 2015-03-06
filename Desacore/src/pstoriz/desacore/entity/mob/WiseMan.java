package pstoriz.desacore.entity.mob;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.AnimatedSprite;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;

public class WiseMan extends Mob {
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.wiseMan_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.wiseMan_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.wiseMan_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.wiseMan_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int xa = 1;
	private int ya = 0;
	
	public WiseMan(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		hbXmul = 13; hbXmod = 7; hbYmul = 15; hbYmod = 15;
		sprite = Sprite.wiseMan;
	}
	
	public void update() {
		time++;
		// time % 60 is once per second
		if (time % (random.nextInt(50) + 30) == 0) {
			//changes his direction
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			direction = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			direction = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			direction = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			direction = Direction.RIGHT;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite);
	}

}
