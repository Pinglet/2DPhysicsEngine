package physics;

public class Force {

	private float xForce;
	private float yForce;
	private float life;
	
	public Force(float x, float y) {
		xForce = x;
		yForce = y;
		life = 1;
	}
	
	public Force(float x, float y, float startTime) {
		xForce = x;
		yForce = y;
		life = startTime;
	}
	
	public float getXForce() {
		return xForce;
	}
	public void setXForce(float x) {
		xForce = x;
	}
	public void addXForce(float x) {
		xForce += x;
	}
	
	public float getYForce() {
		return yForce;
	}	
	public void setYForce(float y) {
		yForce = y;
	}
	public void addYForce(float y) {
		yForce += y;
	}
	
	public float getLife() {
		return life;
	}
	public void reduceLife(float time) {
		life -= time;
	}
	
	// Adds the effects of 2 forces and stores result in the object calling the method
	public void sumForces(Force otherForce) {
		xForce += otherForce.getXForce();
		yForce += otherForce.getYForce();
	}
	
	// Returns the total force of both vectors
	public float totalForce() {
		return (float)Math.sqrt((xForce*xForce+yForce*yForce));
	}
	
	public boolean equals(Force otherForce) {
		if (xForce==otherForce.getXForce() && yForce==otherForce.getYForce()) {
			return true;
		} else {
			return false;
		}
	}
}
