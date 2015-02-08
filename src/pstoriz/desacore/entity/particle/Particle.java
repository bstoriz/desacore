package pstoriz.desacore.entity.particle;

import java.awt.Color;

import pstoriz.desacore.Screen;
import pstoriz.desacore.entity.Entity;
import pstoriz.desacore.graphics.Sprite;

public class Particle extends Entity {
	
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	//the amount of pixels in the x and y axises
	protected double xx, yy, zz;
	protected double xa, ya, za;
	
	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(60) - 30);
		sprite = Sprite.particle_normal;
		
		this.xa = random.nextGaussian(); //number between -1 and 1 (more likely 0 than anything)
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}

	public void update() {
		time++;
		if (time >= Integer.MAX_VALUE - 1) time = 0;
		if (time > life) remove();
		za -= 0.1;
		//particle animation. za is animated so that it acts like gravity. Imagine graphing equations on a calculator
		//bouncing animation
		if (zz < 0) {
			zz = 0;
			za *= -.6; //makes it bounce off the ground (changes height)
			xa *= .4; //every bounce slow them down in the x
			ya *= .3; //every bounce slow them down in the y
		}
		move(xx + xa, (yy + ya) + (zz + za));
	}
	
	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix --;
			if (c / 2 == 0) iy --;
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}


	public void render(Screen screen) {
		//zz controls its pull on the y axis. (animation)
		screen.renderSprite((int) xx, (int) yy - (int) zz, sprite, true);
	}
}
