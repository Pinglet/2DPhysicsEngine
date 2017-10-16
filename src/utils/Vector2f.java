package utils;

public class Vector2f {
	
	float x;
	float y;
	
	// Creates a 2 dimensional vector.
	public Vector2f(float x, float y) {
		// x and y coordinates.
		this.x = x;
		this.y = y;
	}
	
	// Calculates the Euclidean norm of the vector.
	public float norm() {
		float value = (float)Math.sqrt(x * x + y * y);
		return value;
	}
	
	// Normalises the vector (So it has norm 1).
	public void normalise() {
		x = x / this.norm();
		y = y / this.norm();
	}
	
	// Calculating the angle between two vectors.
	public float calculateAngle(Vector2f v1, Vector2f v2) {
		float angle = (float)(Math.acos(dot(v1, v2) / (v1.norm()*v2.norm())));
		return angle;
	}
	
	// Rotates the vector counter clockwise.
	public void rotate(Vector2f v1, float angle) {
		x = x * (float)Math.cos(angle) - y * (float)Math.sin(angle);
		y = x * (float)Math.sin(angle) + y * (float)Math.cos(angle);
	}
	
	// The dot product of two vectors.
	public float dot(Vector2f v1, Vector2f v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	// Adding two vectors together.
	public static Vector2f add(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x + v2.x, v1.y + v2.y);
	}
}