package components;

import gameobject.GameObject;
import utils.Vector2;

/* This class is used to define the object's space and position in the world and is referred to be any other component which manipulates its
 * position*/

public class Mesh extends Component {

	public GameObject object;
	// The x and y co-ords determine the centre of the box
	private Vector2 posXY = new Vector2(0, 0);
	private float zPos;
	private float rotation = 0;
	private int width;
	private int height;
	
	public Mesh(float newX, float newY, float newZ, int newWidth, int newHeight, GameObject newObject) {
		posXY.setX(newX);
		posXY.setY(newY);
		zPos = newZ;
		width = newWidth;
		height = newHeight;	
		object = newObject;
	}
	
	public Mesh(Vector2 vector, float newZ, int newWidth, int newHeight, GameObject newObject) {
		this(vector.getX(), vector.getY(), newZ, newWidth, newHeight, newObject);
	}
	
	public float getX() {
		return posXY.getX();
	}
	public void setX(float x) {
		posXY.setX(x);
	}
	
	public float getY() {
		return posXY.getY();
	}
	public void setY(float y) {
		posXY.setY(y);
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
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getRotation() {
		return rotation;
	}
}
