package entity;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import engine.Main;
import game.Time;
import gameobject.GameObject;
import utils.Utils;

public class Enemy extends Entity {
	
	private Player target;
	private boolean aggressive;
	private int detectRange;
	private boolean roaming;
	
	private final float ROAM_SPEED_FACTOR = 0.5f;
	private final int ROAM_MAX_PAUSE_TIME = 3;
	private float roamX;
	private float roamY;
	private float roamDelay;
	private double roamDelayTimer;
	
	
	public Enemy(float x, float y, int size, boolean solid, float moveSpeed, boolean aggressive,
				 int detectRange, boolean roaming, Weapon weapon, int maxHealth, Armour armour) {
		initEntity(x, y, size, size, "enemy/enemy", solid, moveSpeed, weapon, maxHealth, armour);
		this.aggressive = aggressive;
		this.detectRange = detectRange;
		roamX = x;
		roamY = y;
		roamDelay = 1f;
		attacking = false;
		attackDelayTimer = 50f;
	}
	
	public void update() {
		checkDeath();
		if (target == null) {
			if (Utils.calculateDistanceFromCentre(this, Main.game.player) < detectRange && aggressive) {
				target = Main.game.player;
//				chaseTarget();
			} else if (roaming) {
				roam();
			}
		}
		if (target != null && aggressive) {
//			chaseTarget();
//			handleAttack();
		}
		checkTexture();
	}
	
	public void render() {
		renderHealthBars(64, 4);
		renderTargetSector(Utils.calculateAngle(x + width / 2, y + height / 2, target.x + target.width / 2, target.y + target.height / 2));
		
		Utils.drawQuadTex(texture, x, y, z, width, height);
	}
	
//	private void handleAttack() {
//		boolean targetWithinRange = Utils.isCollidingWithSector(target, this,
//				Utils.calculateAngle(x + width / 2, y + height / 2, target.x + target.width / 2, target.y + target.height / 2));
//		ArrayList<Entity> targetAsList = new ArrayList<Entity>();
//		targetAsList.add(target);
//		attAttack(targetWithinRange, targetWithinRange, targetAsList);
//	}
	

	
	private void roam() {
		if (x == roamX && y == roamY) {
			if (roamDelayTimer >= roamDelay) {
				roamDelay = Utils.genRandomNumber(ROAM_MAX_PAUSE_TIME) + 1;
				roamX = createNewRoam(roamX, 32, Display.getWidth() - 32 - width);
				roamY = createNewRoam(roamY, 32, Display.getHeight() - 32 - height);
				roamDelayTimer = 0;
			} else {
				roamDelayTimer += (double)Time.getDifference() / 1000000000;
			}
		} else {
			float initialMoveSpeed = moveSpeed * speedFactor * Time.getDelta() * ROAM_SPEED_FACTOR;
			double velocityAngle = Math.atan(Math.abs((roamY - y)/(roamX - x)));
			xMoveVector = initialMoveSpeed * (float)Math.cos(velocityAngle);
			yMoveVector = initialMoveSpeed * (float)Math.sin(velocityAngle);
			if (roamX - x < 0) { // check = 0 condition for both
				xMoveVector = -xMoveVector;
			}
			if (roamY - y < 0) { // check = 0 condition for both
				yMoveVector = -yMoveVector;
			}
			x += xMoveVector;
			y += yMoveVector;
			if (xMoveVector < 0 && x < roamX) {
				x = roamX;
			} else if (xMoveVector > 0 && x > roamX) {
				x = roamX;
			}
			if (yMoveVector < 0 && y < roamY) {
				y = roamY;
			} else if (yMoveVector > 0 && y > roamY) {
				y = roamY;
			}
		}
	}
	
	private float createNewRoam(float currentPosition, float minimumBound, float maximumBound) {
		int changeInPosition = Utils.genRandomNumber(600) - 300;
		if (changeInPosition <= 150 && changeInPosition >= -150) {
			if (150 - changeInPosition > 150 + changeInPosition) {
				changeInPosition = -150;
			} else {
				changeInPosition = 150;
			}
		}
		float newPosition = currentPosition + changeInPosition;
		newPosition = Utils.correctOverlap(newPosition, minimumBound, maximumBound);
		return newPosition;
	}

	private void chase() {
		float initialMoveSpeed = moveSpeed * speedFactor * Time.getDelta();
		double velocityAngle = Math.atan(Math.abs((target.y - y)/(target.x - x)));
		xMoveVector = initialMoveSpeed * (float)Math.cos(velocityAngle);
		yMoveVector = initialMoveSpeed * (float)Math.sin(velocityAngle);
		if (target.x - x < 0) { // check = 0 condition for both
			xMoveVector = -xMoveVector;
		}
		if (target.y - y < 0) { // check = 0 condition for both
			yMoveVector = -yMoveVector;
		}
		x += xMoveVector;
		y += yMoveVector;
		if (xMoveVector < 0 && x < target.x) {
			x = target.x;
		} else if (xMoveVector > 0 && x > target.x) {
			x = target.x;
		}
		if (yMoveVector < 0 && y < target.y) {
			y = target.y;
		} else if (yMoveVector > 0 && y > target.y) {
			y = target.y;
		}
	}
	
	public void die() {
		Main.game.objectsToDelete.add(this);
	}
}