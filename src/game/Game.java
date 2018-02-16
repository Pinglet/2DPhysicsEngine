package game;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import components.Mesh;
import gameobject.GameObject;
import utils.Utils;

public class Game {

	public boolean paused = false;
	public static final int PLAYER_SIZE = 32;
	
	public ArrayList<GameObject> currentObjects;
	public ArrayList<GameObject> objectsToDelete;
	public ArrayList<GameObject> objectsToAdd;

	public Renderer renderer;
	
	public Game() {
		currentObjects = new ArrayList<GameObject>();
		objectsToDelete = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
		renderer = new Renderer();
		
		GameObject player = new GameObject();
		player.currentTexture = Utils.quickLoad("entity/player/playerup");
		player.components.put("mesh", new Mesh((Display.getWidth()/2-PLAYER_SIZE/2), (Display.getHeight()/2-PLAYER_SIZE/2), 0, PLAYER_SIZE, PLAYER_SIZE));
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
