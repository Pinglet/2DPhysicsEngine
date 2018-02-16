package components;

import gameobject.GameObject;

/* This class is used to define the object's space and position in the world and is referred to be any other component which manipulates its
 * position*/

public class Mesh extends Component {

	public GameObject object;
	private int x;
	private int y;
	private int z;
	private int width;
	private int height;
	
	public Mesh(int newX, int newY, int newZ, int newWidth, int newHeight) {
		x = newX;
		y = newY;
		z = newZ;
		width = newWidth;
		height = newHeight;		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
