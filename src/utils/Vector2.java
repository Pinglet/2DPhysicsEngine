package utils;

public class Vector2 {
	
	private float x;
	private float y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public Vector2 sum(Vector2 other) {
		return new Vector2(x+other.getX(), y+other.getY());
	}
	
	// Returns dot product of 2 vectors
	public float dot(Vector2 other) {
		return x*other.getX() + y*other.getY();
	}
	
	// INCOMPLETE
	public float cross(Vector2 other) {
		return 1;
	}
	
	public float hypotonuse() {
		return (float)Math.sqrt(x*x+y*y);
	}
	
}
