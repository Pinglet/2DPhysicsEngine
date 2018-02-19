package physics;

import utils.Vector2;

public class Force {

//	private float xForce;
//	private float yForce;
	
	private Vector2 vector;
	
	public enum ForceType {
		force, zeroVForce
	}
	
	private final ForceType forceType;
	
	public Force(Vector2 vector, ForceType f) {
		this.vector = vector;
		forceType = f;
	}
	
	public Force(float x, float y, ForceType f) {
		this(new Vector2(x, y), f);
	}
	
	public Force(Vector2 vector) {
		this(vector, ForceType.force);
	}
	
	public Force(float x, float y) {
		this(new Vector2(x, y), ForceType.force);
	}
	
	public float getX() {
		return vector.getX();
	}
	public void setX(float x) {
		vector.setX(x);
	}
	public void addX(float x) {
		vector.setX(vector.getX()+x);
	}
	
	public float getY() {
		return vector.getY();
	}	
	public void setY(float y) {
		vector.setY(y);
	}
	public void addY(float y) {
		vector.setY(vector.getY()+y);
	}
	
	public Vector2 getVector() {
		return vector;
	}
	
	// Returns the type of force
	public ForceType getForceType() {
		return forceType;
	}
	
	// Adds the effects of 2 forces and stores result in the object calling the method
	public Force sumForce(Force otherForce) {
		return new Force(vector.sum(otherForce.getVector()));
	}
	
	// Returns the total force of two forces
	public float totalForce(Force other) {
		return (float)Math.sqrt(vector.dot(other.getVector()));
	}
	
	public boolean equals(Force otherForce) {
		if (vector.getX()==otherForce.getX() && vector.getY()==otherForce.getY()) {
			return true;
		} else {
			return false;
		}
	}
}
