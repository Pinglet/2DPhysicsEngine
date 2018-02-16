package game;

import java.util.ArrayList;
import gameobject.GameObject;

public class Game {

	public boolean paused = false;
	
	public ArrayList<GameObject> currentObjects;
	public ArrayList<GameObject> objectsToDelete;
	public ArrayList<GameObject> objectsToAdd;

	public Game() {
		currentObjects = new ArrayList<GameObject>();
		objectsToDelete = new ArrayList<GameObject>();
		objectsToAdd = new ArrayList<GameObject>();
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
