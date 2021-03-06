package pstoriz.desacore.entity.mob;

import java.util.List;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.AnimatedSprite;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;
import pstoriz.desacore.level.Node;
import pstoriz.desacore.util.Vector2i;

public class Star extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.star_down, 32,
			32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.star_up, 32, 32,
			3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.star_left, 32,
			32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.star_right,
			32, 32, 3);

	private AnimatedSprite animSprite = down;

	private double xa = 0;
	private double ya = 0;
	private List<Node> path = null;
	private double speed = 1.1;
	private int time, timer = 0;
	Vector2i goal = new Vector2i(200, 200);

	public Star(int x, int y, int level) {
		this.x = x << 4;
		this.y = y << 4;
		hbXmul = 13; hbXmod = 7; hbYmul = 15; hbYmod = 15;
		Tlvl = level;
		sprite = Sprite.star;
		health = 100 + (int) (Tlvl * 1.1);
		range = 128;
	}

	private void move() {
		xa = 0;
		ya = 0;
		speed = .5;
		range = 128;
		alerted = false;
		List<Player> players = level.getPlayers(this, range);
		if (players.size() > 0) {
			alerted = true;
			range = 256;
			speed = 1.1;
			int px = (int) level.getPlayerAt(0).getX(); // player's location
			int py = (int) level.getPlayerAt(0).getY();
			Vector2i start = new Vector2i((int) getX() >> 4, (int) getY() >> 4);
			goal = new Vector2i(px >> 4, py >> 4);
			if (time % 3 == 0) path = level.findPath(start, goal);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if ((int) x < vec.getX() << 4)
						xa += speed; // if the location we need to move to is to the
								// right then move that way
					if ((int) x > vec.getX() << 4)
						xa -= speed;
					if ((int) y < vec.getY() << 4)
						ya += speed;
					if ((int) y > vec.getY() << 4)
						ya -= speed;
				}
			}
		} else {
			xa = 0;
			ya = 0;
			timer++;
			// time % 60 is once per second
			if (timer % (random.nextInt(50) + 30) == 0) {
				//changes his direction
				xa += (random.nextInt(3) - 1) * speed;
				ya += (random.nextInt(3) - 1) * speed;
				if (random.nextInt(1) == 0) {
					xa = 0;
					ya = 0;
				}
			}
			if (timer > 10000) timer = 0;
		}
		if (xa != 0 || ya != 0) {
			move(xa, 0);
			move(0, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		time++;
		
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
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
		move();
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite);
		
	}

}
