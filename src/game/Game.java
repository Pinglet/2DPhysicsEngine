package game;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import components.Mesh;
import components.RigidBody;
import gameobject.GameObject;
import physics.Explosion;
import physics.Force;
import physics.Force.ForceType;
import utils.Utils;

public class Game {

	public boolean paused = false;
	public static final int PLAYER_SIZE = 32;
	
	public ArrayList<GameObject> currentObjects;
	public ArrayList<GameObject> objectsToDelete;
	public ArrayList<GameObject> objectsToAdd;
	
	public int count = 0;
	
	public static float zoomFactor = 1;
	
	public GameObject player;

	public Renderer renderer;
	
	public Game() {
		currentObjects = new ArrayList<GameObject>();
		objectsToDelete = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
		renderer = new Renderer();
		
		player = new GameObject();
		player.currentTexture = Utils.quickLoad("entity/player/player");
		player.components.put("mesh", new Mesh(Display.getWidth()/2, Display.getHeight()/2, 1, PLAYER_SIZE, PLAYER_SIZE, player));
		player.components.put("rigidbody", new RigidBody(player, 0.6f, 5f));
		objectsToAdd.add(player);
	}
	
	
	public void update() {
		if (!paused) {
			for (GameObject go : currentObjects) {
				go.update();
			}
			deleteObjects();
			addObjects();
		}
	}
	
	public void getInput() {
		RigidBody playerRB = (RigidBody) player.components.get("rigidbody");
		Mesh playerMesh = (Mesh) player.components.get("mesh");
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && !Keyboard.isKeyDown(Keyboard.KEY_S)) {
			playerRB.addForce(new Force(0, 5));
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			playerRB.addForce(new Force(0, -5));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && !Keyboard.isKeyDown(Keyboard.KEY_D)) {
			playerRB.addForce(new Force(-5, 0));
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			playerRB.addForce(new Force(5, 0));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			playerRB.addForce(new Force(50, 0, ForceType.zeroVForce));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			new Explosion(Display.getWidth()/2, Display.getHeight()/2, 300, currentObjects);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
			System.out.printf("x: %f, y: %f Rotation: %f%n", playerMesh.getX(), playerMesh.getY(), playerMesh.getRotation());
		}	
		if (Mouse.isButtonDown(0)) {
			GameObject testyBoi = new GameObject();
			testyBoi.currentTexture = Utils.quickLoad("entity/enemy/enemy");
			testyBoi.components.put("mesh", new Mesh(Mouse.getX(), Mouse.getY(), 0, PLAYER_SIZE, PLAYER_SIZE, player));
			testyBoi.components.put("rigidbody", new RigidBody(testyBoi, 0.8f, 5f));
			objectsToAdd.add(testyBoi);
			count++;
			System.out.println("Count :" + count);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			System.out.printf("mouse x: %d, mouse y: %d%n", Mouse.getX(), Mouse.getY());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E) && !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			playerMesh.setRotation(playerMesh.getRotation()+1);
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_E) && Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			playerMesh.setRotation(playerMesh.getRotation()-1);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_I) && !Keyboard.isKeyDown(Keyboard.KEY_O)) {
			zoomFactor += 0.1;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_I) && Keyboard.isKeyDown(Keyboard.KEY_O)) {
			zoomFactor -= 0.1;
		}
	}
	
	public void render() {
		if (!paused) {
			renderer.renderObjects(currentObjects);
		}
	}
	

	private void deleteObjects() {
		if (!objectsToDelete.isEmpty()) {
			ArrayList<GameObject> newObjects = new ArrayList<>(currentObjects);
			ArrayList<GameObject> newObjectsToDelete = new ArrayList<>(objectsToDelete);
			for (GameObject go : objectsToDelete) {
				newObjects.remove(go);
				newObjectsToDelete.remove(go);
			}
			currentObjects = newObjects;
			objectsToDelete = newObjectsToDelete;
		}
	}
	
	private void addObjects() {
		if (!objectsToAdd.isEmpty()) {
			ArrayList<GameObject> newObjects = new ArrayList<>(currentObjects);
			ArrayList<GameObject> newObjectsToAdd = new ArrayList<>(objectsToDelete);
			for (GameObject go : objectsToAdd) {
				newObjects.add(go);
				newObjectsToAdd.remove(go);
			}
			currentObjects = newObjects;
			objectsToAdd = newObjectsToAdd;
		}
	}
}
