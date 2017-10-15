package entity;

public enum Armour {
	
	SUPER_ARMOUR(5, 100, 90, 90, 90),
	NOT_SUPER_ARMOUR(2, 0, 0, 0, 0);
	
	// Weight determines how quickly an entity can move with this armour as well how hard they are to knockback
	public int weight;
	// Block chance is the percentage for attack to be completely deflected off the armour
	public int blockChance;
	// Each protection protects against different types of weapon. Damage from these weapons are reduced by the 
	// value of the total respective protection value (WIP calculations)
	public int bluntProtection;
	public int pierceProtection;
	public int cutProtection;
	
	private Armour(int weight, int blockChance, int bluntProtection, int pierceProtection, int cutProtection) {
		this.weight = weight;
		this.blockChance = blockChance;
		this.bluntProtection = bluntProtection;
		this.pierceProtection = pierceProtection;
		this.cutProtection = cutProtection;
	}
}
