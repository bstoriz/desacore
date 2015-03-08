package pstoriz.desacore.entity.mob.player;

public class Skill {
	
	//How much xp you need to get to the next lvl
	private int lvl;
	private double xpNeeded;
	
	//The higher the curve the harder it makes it to level up
	public final static double POWER_CURVE = 1.75;
	public final static double ATTACK_CURVE = 1.1;
	public final static double DEFENSE_CURVE = 1.1;
	public final static double RELOAD_CURVE = 1.1;
	
	public double curve;
	
	//Constructor for the class
	public Skill(double xpNeeded, int lvl, double curve) {
		this.xpNeeded = xpNeeded;
		this.curve = curve;
		this.lvl = lvl;
	}
	
	public void setXPNeeded(double curve) {
		//Increases the difficulty of leveling up after each level
		xpNeeded = (curve * xpNeeded);
	}
	
	public void increaseSkillLVL() {
		lvl++;
	}
	
	public double getXPNeeded() {
		return xpNeeded;
	}
	
	public int getLVL() {
		return lvl;
	}
}
