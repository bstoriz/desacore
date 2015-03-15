package pstoriz.desacore.entity.mob;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.mob.Mob.Direction;
import pstoriz.desacore.graphics.AnimatedSprite;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.graphics.SpriteSheet;

public class Shooter extends Mob {
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.shooter_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.shooter_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.shooter_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.shooter_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	
	//INITIALIZATION
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.shooter;
	}

	private double xa = 0;
	private double ya = 0;
	private int timer = 0;
	private double speed = 1.1;
	
	
	//LOGIC BEHAVIOR
	public void update() {
		time++;
		Player p = level.getClientPlayer();
		if (p.getX() < 0 && p.getY() > 0 || p.getX() > 0 && p.getY() < 0) {
			shoot(p, 32, 0);
		}
		else if (p.getX() == 0 || p.getY() == 0) {
			shoot(p, 16, 16);
		}
		shoot(p, 0, 0);
	}
	
	//SHOOT FIREBALL
	public void shoot(Player target, int changeX, int changeY) {
		double dx = target.getX() - x;
		double dy = target.getY() - y;
		double dir = Math.atan2(dy, dx);
		if (time % 60 == 0 && target.getDistance(x, y) < 10) shoot(x - changeX, y - changeY , dir);
	}

	//VISUAL
	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite);
	}

}
