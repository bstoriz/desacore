package pstoriz.desacore.entity.mob;

import java.util.List;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.AnimatedSprite;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;

public class Bandit extends Mob {
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.bandit_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.bandit_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.bandit_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.bandit_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private double xa = 0;
	private double ya = 0;
	private int timer = 0;
	private double speed = 1.1;

	public Bandit(int x, int y, int level) {
		this.x = x << 4;
		this.y = y << 4;
		hbXmul = 13; hbXmod = 7; hbYmul = 15; hbYmod = 15;
		Tlvl = level;
		sprite = Sprite.bandit;
		health = 100 + (int) (Tlvl * 1.1);
		range = 64;
	}
	
	private void move() {
		//Chases the player
		range = 64;
		List<Player> players = level.getPlayers(this, range);
		speed = .5;
		if (players.size() > 0) {
			range = 128;
			speed = 1.1;
			xa = 0;
			ya = 0;
			Player player = players.get(0);
			if (x < player.getX()) xa += speed;
			if (x > player.getX()) xa -= speed;
			if (y < player.getY()) ya += speed;
			if (y > player.getY()) ya -= speed;
		} else {
			timer++;
			// time % 60 is once per second
			if (timer % (random.nextInt(50) + 30) == 0) {
				//changes his direction
				xa += (random.nextInt(3) - 1) * speed;
				ya += (random.nextInt(3) - 1) * speed;
				if (random.nextInt(2) == 0) {
					xa = 0;
					ya = 0;
				}
			}
			
		}
		
		if (timer > 10000) timer = 0;
	}
	
	public void update() {
		move();
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
			move(xa, 0);
			move(0, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16),(int) (y - 16), sprite);
	}

}
