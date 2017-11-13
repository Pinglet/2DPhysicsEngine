package entity;

import gameobject.GameObject;
import items.Inventory;
import utils.components.ai.BasicAggroAI;

public class TestEnemy extends Npc {
	
	public TestEnemy() {
		ai = new BasicAggroAI(this);
		inventory = new Inventory();
	}
	
}