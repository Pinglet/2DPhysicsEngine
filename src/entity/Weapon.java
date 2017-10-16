package entity;

public enum Weapon {
	
	OP_PLAYER_SWORD(75f, 1f, 0.5f, 0.05f, 1, 50),
	SWORD(75f, 1f, 0.5f, 0.05f, 0, 40),
	DAGGER(45f, 3f, 0.1f, 0.05f, 0, 30),
	WARHAMMER(125f, 0.2f, 2f, 0.2f, 3, 100);
	
	public float attackRange;
	public float attackSpeed;
	public float windUpTime;
	public float attackTime;
	public int knockBack;
	public int damage;
	
	private Weapon(float attackRange, float attackSpeed, float windUpTime, float attackTime, int knockBack, int damage) {
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.windUpTime = windUpTime;
		this.attackTime = attackTime;
		this.knockBack = knockBack;
		this.damage = damage;
	}
}

