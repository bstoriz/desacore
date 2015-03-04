package pstoriz.desacore.entity.mob;

import pstoriz.desacore.Screen;
import pstoriz.desacore.graphics.Sprite;

public class Shooter extends Mob {
	
	private int time = 0;
	
	//INITIALIZATION
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.bandit;
	}

	//LOGIC BEHAVIOR
	public void update() {
		time++;
		Player p = level.getClientPlayer();
		double dx = p.getX() - x;
		double dy = p.getY() - y;
		double dir = Math.atan2(dy, dx);
		if (time % 60 == 0) shoot(x + 5, y + 10, dir);
		
	}

	//VISUAL
	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite);
	}

}
