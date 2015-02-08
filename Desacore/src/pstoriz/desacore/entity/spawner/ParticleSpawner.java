package pstoriz.desacore.entity.spawner;

import pstoriz.desacore.entity.particle.Particle;
import pstoriz.desacore.graphics.Sprite;
import pstoriz.desacore.level.Level;

public class ParticleSpawner extends Spawner {
	
	private int life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life, sprite));
		}
	}

}
