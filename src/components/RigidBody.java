package components;

import java.util.ArrayList;
import java.util.Iterator;

import game.Time;
import gameobject.GameObject;
import physics.*;

public class RigidBody extends Component {

	public GameObject object;
	private Mesh mesh;
	
	private ArrayList<Force> forceBox;
	
	private float xVelocity;
	private float yVelocity;
	private float xAccel;
	private float yAccel;
	private float mass;
	
	private Force resultant;
	
	public RigidBody(GameObject newObject, float newMass) {
		forceBox = new ArrayList<Force>();
		object = newObject;
		mass = newMass;
		resultant = new Force(0, 0);

	}
	
	public void update() {
		updateRForce();
		updateAcceleration();
		updateVelocity(1);
		moveMesh(1);
	}
	
	private void updateRForce() {
		resultant.setXForce(0);
		resultant.setYForce(0);
		
		Iterator<Force> iter = forceBox.iterator();
		
		while (iter.hasNext()) {
			Force f = iter.next();
			resultant.addXForce(f.getXForce());
			resultant.addYForce(f.getYForce());
			f.reduceLife(1*Time.getDelta());
			if (f.getLife()<=0) {
				iter.remove();
			}
		}		
	}
	
	private void updateAcceleration() {
		xAccel = resultant.getXForce()/mass;
		yAccel = resultant.getYForce()/mass;
	}
	
	private void updateVelocity(float time) {
		time *= Time.getDelta();
		xVelocity += xAccel*time;
		yVelocity += yAccel*time;
	}
	
	private void moveMesh(float time) {
		time *= Time.getDelta();
		mesh = (Mesh) object.components.get("mesh");
		mesh.addX(xVelocity*time);
		mesh.addY(yVelocity*time);
	}
	
	public void inputForceFB(Force force) {
		forceBox.add(force);	
	}
	
	public void addForce(float x, float y, int startTime) {
		Force force = new Force(x, y, startTime);
		forceBox.add(force);
	}	
	
	public void addForce(float x, float y) {
		Force force = new Force(x, y, 1);
		forceBox.add(force);
	}
	
	
}
