package entity;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import engine.Main;
import game.Game;
import game.Time;
import gameobject.GameObject;
import utils.Utils;

public abstract class Entity extends GameObject {
	
	public float xMoveVector;
	public float yMoveVector;
	public float speedFactor;
	
	public Weapon weapon;
	public Armour armour;
	public boolean attacking;
	public float attackDelayTimer;
	
	public int currentHealth;
	public int maxHealth;
	public float moveSpeed;
	public float attackRange;
	public String[] textureNameArray;
	public Texture[] textureArray;

	public void initEntity(float x, float y, int width, int height, String textureNameArrayPrefix,
						   boolean solid, float moveSpeed, Weapon weapon, int maxHealth, Armour armour) {
		setTextureNameArray(textureNameArrayPrefix);
		loadTextureArray(textureNameArray);
		initGameObject(x, y, 0f, width, height, "entity/" + textureNameArray[4], solid);
		this.moveSpeed = moveSpeed;
		this.weapon = weapon;
		this.armour = armour;
		this.maxHealth = maxHealth;
		currentHealth = maxHealth;
		speedFactor = 1;
	}
	
	public void attAttack(boolean triggerCondition, boolean damageCondition, ArrayList<Entity> damageRecipients) {
		if (attackDelayTimer >= 1f / weapon.attackSpeed && !attacking) {
			if (triggerCondition) {
				attacking = true;
				attackDelayTimer = 0;
			}
		} else {
			attackDelayTimer += (double)Time.getDifference() / 1000000000;
		}
		if (attacking && attackDelayTimer >= weapon.windUpTime + weapon.attackTime) { //damage registers when they finish their swing
			if (damageCondition) {
				for (Entity e : damageRecipients) {

					if (e.armour == null) {
						e.takeDamage(weapon.damage);
					}
					else if (e.armour.blockChance < Utils.genRandomNumber(100)) {
						e.takeDamage(weapon.damage);
					}

					// Knocking back entity
					getKnocked(weapon, e);

				}
			}
			attacking = false;
		}
	}
	
	// Entity getting knocked back when attacked.
	public void getKnocked(Weapon weapon, Entity ent2) {
		// Get vector from this entity to the other entity
		float xChange = ent2.x - x;
		float yChange = ent2.y - y;
		
		// Normalising
		float norm =  (float)Math.sqrt(xChange * xChange + yChange * yChange);
		xChange = xChange / norm;
		yChange = yChange / norm;
		
		// Applying knock back from weapon
		ent2.x += xChange * 50 * weapon.knockBack;
		ent2.y += yChange * 50 * weapon.knockBack;
	}
	
	public void renderTargetSector(float angle) {
		if (attacking) {
			if (attackDelayTimer >= weapon.windUpTime && attackDelayTimer <= weapon.windUpTime + weapon.attackTime) {
				glColor3f(1, 0, 0);
			}
			Utils.drawSector(x + width / 2, y + height / 2, -0.3f, weapon.attackRange, angle, 90f);
		}
		glColor3f(1, 1, 1);
	}
	
	public void renderHealthBars(int healthBarWidth, int healthBarHeight) {
		glColor3f(1, 0, 0);
		Utils.drawQuad(x + (width - healthBarWidth) / 2, y + height * 1.5f, -0.1f, healthBarWidth, healthBarHeight);
		
		glColor3f(0, 1, 0);
		float percentageHealth = (float)currentHealth / maxHealth;
		if (percentageHealth < 0) {
			percentageHealth = 0;
		}
		Utils.drawQuad(x + (width - healthBarWidth) / 2, y + height * 1.5f, -0.05f,
					   (int)(healthBarWidth * percentageHealth), healthBarHeight);
		
		glColor3f(1, 1, 1);
	}
	
	public void takeDamage(int damage) {
		currentHealth -= damage;			
	}
	
	public void checkDeath() {
		if (currentHealth <= 0) {
			if (this instanceof Enemy) {
				Main.game.objectsToDelete.add(this);
			} else if (this instanceof Player) {
				Main.game = new Game();
			}
		}
	}
	
	public void setTextureNameArray(String prefix) {
		String[] textureNameArray = new String[8];
		
		textureNameArray[0] = prefix + "up";
		textureNameArray[1] = prefix + "topright";
		textureNameArray[2] = prefix + "right";
		textureNameArray[3] = prefix + "bottomright";
		textureNameArray[4] = prefix + "down";
		textureNameArray[5] = prefix + "bottomleft";
		textureNameArray[6] = prefix + "left";
		textureNameArray[7] = prefix + "topleft";
		
		this.textureNameArray = textureNameArray;
	}
	
	public void loadTextureArray(String[] textureNameArray) {
		Texture[] textureArray = new Texture[8];
		
		for (int i = 0; i < 8; i++) {
			textureArray[i] = Utils.quickLoad("entity/" + textureNameArray[i]);
		}
		
		this.textureArray = textureArray;
	}
	
	public void checkTexture() {
		double cutoffAngle = Math.toRadians(15d); //degrees either side of horizontal/vertical
		double smallCutoffMagnitude = Math.sin(cutoffAngle);
		double largeCutoffMagnitude = Math.cos(cutoffAngle);
		if (yMoveVector >= largeCutoffMagnitude && xMoveVector <= smallCutoffMagnitude && xMoveVector >= -smallCutoffMagnitude) {
			texture = textureArray[0];
		} else if (yMoveVector >= smallCutoffMagnitude && xMoveVector >= smallCutoffMagnitude) {
			texture = textureArray[1];
		} else if (yMoveVector <= smallCutoffMagnitude && yMoveVector >= -smallCutoffMagnitude && xMoveVector >= largeCutoffMagnitude) {
			texture = textureArray[2];
		} else if (yMoveVector <= -smallCutoffMagnitude && xMoveVector >= smallCutoffMagnitude) {
			texture = textureArray[3];
		} else if (yMoveVector <= -largeCutoffMagnitude && xMoveVector <= smallCutoffMagnitude && xMoveVector >= -smallCutoffMagnitude) {
			texture = textureArray[4];
		} else if (yMoveVector <= -smallCutoffMagnitude && xMoveVector <= -smallCutoffMagnitude) {
			texture = textureArray[5];
		} else if (yMoveVector <= smallCutoffMagnitude && yMoveVector >= -smallCutoffMagnitude && xMoveVector <= -largeCutoffMagnitude) {
			texture = textureArray[6];
		} else if (yMoveVector >= smallCutoffMagnitude && xMoveVector <= -smallCutoffMagnitude) {
			texture = textureArray[7];
		}
		
		/*
		if (yMoveVector == 1 && xMoveVector == 0) {
			texture = textureArray[0];
		} else if (yMoveVector == 1 && xMoveVector == 1) {
			texture = textureArray[1];
		} else if (yMoveVector == 0 && xMoveVector == 1) {
			texture = textureArray[2];
		} else if (yMoveVector == -1 && xMoveVector == 1) {
			texture = textureArray[3];
		} else if (yMoveVector == -1 && xMoveVector == 0) {
			texture = textureArray[4];
		} else if (yMoveVector == -1 && xMoveVector == -1) {
			texture = textureArray[5];
		} else if (yMoveVector == 0 && xMoveVector == -1) {
			texture = textureArray[6];
		} else if (yMoveVector == 1 && xMoveVector == -1) {
			texture = textureArray[7];
		}
		 */
	}
	

}
