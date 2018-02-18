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
	
	private float frictionCoef;
	private Force resultant;
	private Force friction;
	
	public RigidBody(GameObject newObject, float newMass) {
		forceBox = new ArrayList<Force>();
		object = newObject;
		mass = newMass;
		frictionCoef = 0.6f;
		resultant = new Force(0, 0);
	}
	
	public void update() {
		updateRForce();
		updateAcceleration();
		updateVelocity(Time.getDelta());
		moveMesh(Time.getDelta());
	}
	
	private void updateRForce() {
		friction = new Force(0, 0);
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

		// Calculates the angle of the friction force from the Normal
		
		
		float resultantVelocity = (float)Math.sqrt(xVelocity*xVelocity+yVelocity*yVelocity);
		
		if(resultantVelocity>0) {
			double angleFromNorm = Math.abs(Math.asin(yVelocity/resultantVelocity));
			System.out.println("Angle :"+angleFromNorm);
			float frictionForce = mass*frictionCoef;
			
			float xFric = (float)Math.cos(angleFromNorm)*frictionForce;
			if (xVelocity>0) {
				xFric*=-1;
			}
			float yFric = (float)Math.sin(angleFromNorm)*frictionForce;
			if (yVelocity>0) {
				yFric*=-1;
			}
			
			friction.setXForce(xFric);
			friction.setYForce(yFric);
			
			System.out.println("xFricF :"+xFric);
			System.out.println("yFricF :"+yFric);
			
			resultant.sumForces(friction);
			System.out.println("xResultant :"+resultant.getXForce());
			System.out.println("yResultant :"+resultant.getYForce());
		}
	}
	
	private void updateAcceleration() {
		xAccel = resultant.getXForce()/mass;
		yAccel = resultant.getYForce()/mass;
	}
	
	private void updateVelocity(float time) {
		xVelocity += xAccel*time;
		yVelocity += yAccel*time;
		if (friction.equals(resultant)) {
			if (xAccel/xVelocity>0) {
				xVelocity = 0;
			}
			if (yAccel/yVelocity>0) {
				yVelocity = 0;
			}
		}
	}
	
	private void moveMesh(float time) {
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
	
	public void setFriction(float newFriction) {
		frictionCoef = newFriction;
	}
	
	public float getXVelocity() {
		return xVelocity;
	}
	
	public float getYVelocity() {
		return yVelocity;
	}
}
