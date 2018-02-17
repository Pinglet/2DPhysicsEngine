package components;

import gameobject.GameObject;

/* This class is used to define the object's space and position in the world and is referred to be any other component which manipulates its
 * position*/

public class Mesh extends Component {

	public GameObject object;
	private float xPos;
	private float yPos;
	private float zPos;
	private int width;
	private int height;
	
	public Mesh(float newX, float newY, float newZ, int newWidth, int newHeight, GameObject newObject) {
		xPos = newX;
		yPos = newY;
		zPos = newZ;
		width = newWidth;
		height = newHeight;	
		object = newObject;
	}
	
	public float getX() {
		return xPos;
	}
	public void addX(float x) {
		xPos += x;
	}
	
	public float getY() {
		return yPos;
	}
	public void addY(float y) {
		yPos += y;
	}
	
	public float getZ() {
		return zPos;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
