package entity;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import engine.Main;
import game.Game;
import game.Time;
import gameobject.GameObject;
import utils.Utils;

public class Player extends GameObject {
	
	public static final int PLAYER_SIZE = 32;
	public String[] textureNameArray;
	public Texture[] textureArray;
	public int xMoveVector;
	public int yMoveVector;
	
//	public boolean attackFlag = false;
//	public boolean dashFlag = false;
//	public float dashTimer;
//	public float mouseAngle;

	public Player() {
		initPlayer((Display.getWidth()/2-PLAYER_SIZE/2), (Display.getHeight()/2-PLAYER_SIZE/2), PLAYER_SIZE, PLAYER_SIZE, "player/player");
	}
	
	public void initPlayer(float x, float y, int width, int height, String textureNameArrayPrefix) {
		setTextureNameArray(textureNameArrayPrefix);
		loadTextureArray(textureNameArray);
		initGameObject(x, y, 0, width, height, "entity/" + textureNameArray[4]);
	}
	
	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
			yMoveVector = 1;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			yMoveVector = -1;
		} else {
			yMoveVector = 0;
		}
		// Slow player movement speed.
//		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//			speedFactor = 0.5f;
//		} else {
//			speedFactor = 1.0f;
//		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xMoveVector = -1;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xMoveVector = 1;
		} else {
			xMoveVector = 0;
		}
//		while (Keyboard.next()) {
//			if (Keyboard.getEventKeyState()) {
//				// Dash Key
//				if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
//					dashFlag = true;
//				}
//				// Teleportation
//				if (Keyboard.getEventKey() == Keyboard.KEY_T) {
//					x += xMoveVector * 100;
//					y += yMoveVector * 100;
//				}
//				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
//					Main.game.paused = !Main.game.paused;
//				}
//				if (Keyboard.getEventKey() == Keyboard.KEY_F) {
//					int xSpawn = Utils.genRandomNumber(Display.getWidth() - 32);
//					int ySpawn = Utils.genRandomNumber(Display.getHeight() - 32);
//					Main.game.currentObjects.add(new Enemy(xSpawn, ySpawn, 32, true, 1.5f, true, 1000, false, Weapon.DAGGER, 100, armour.SUPER_ARMOUR));
//				}
//			}
//		}
//		while (Mouse.next()) {
//			if (Mouse.getEventButtonState()) {
//				if (Mouse.getEventButton() == 0) {
//					attackFlag = true;
//			}
//		}
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
		
	}
	
	private void act() {
		float moveAmount = 1 * Time.getDelta();
		float diagonalMoveAmount = moveAmount * (float)(Math.sqrt(2) / 2);
		if (xMoveVector != 0 && yMoveVector != 0) {
			x += diagonalMoveAmount * xMoveVector;
			y += diagonalMoveAmount * yMoveVector;
		} else {
			x += moveAmount * xMoveVector;
			y += moveAmount * yMoveVector;
		}
		
	}
	
	public void update() {
//		checkDeath();
//		updateMouseAngle();
		act();
//		handleAttack();
//		checkFlags();
		checkTexture();
	}
	

//	
//	private void updateMouseAngle() {
//		mouseAngle = Utils.calculateAngle(x + SIZE / 2, y + SIZE / 2, (float)Mouse.getX(), (float)Mouse.getY());
//	}
	

	
//	private void handleAttack() {
//		ArrayList<Entity> targetList = new ArrayList<Entity>();
//		for (GameObject go : Main.game.currentObjects) {
//			if (go != this && go instanceof Entity && Utils.isCollidingWithSector(go, this, mouseAngle)) {
//				targetList.add((Entity)go);
//			}
//		}
//		attAttack(attackFlag, true, targetList);
//	}
//	
//	private void checkFlags() {
//		attackFlag = false;
//		
//		// Dash functionality for the player
//		if (dashFlag) {
//			if (dashTimer >= 0.25f) {
//				speedFactor = 1.0f;
//				dashFlag = false;
//				dashTimer = 0.0f;
//			} else {
//				speedFactor = 4.0f;
//				dashTimer += (double)Time.getDifference() / 1000000000;
//			}
//		}
//	}

	
//	public void checkDeath() {
//		if (currentHealth <= 0) {
//			if (this instanceof Enemy) {
//				Main.game.objectsToDelete.add(this);
//			} else if (this instanceof Player) {
//				Main.game = new Game();
//			}
//		}
//	}
	
}