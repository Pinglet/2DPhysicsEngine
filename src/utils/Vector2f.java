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
	
	// Normalises the vector (So it has norm 1)
	public void normalise() {
		x = x / this.norm();
		y = y / this.norm();
	}
	
	// Adding two vectors together;
	public static Vector2f add(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x + v2.x, v1.y + v2.y);
	}
	
	// The dot product of two vectors.
	public static Vector2f dot(Vector2f v1, Vector2f v2) {
		return new Vector2f(v1.x * v2.x, v1.y * v2.y);
	}
	
}