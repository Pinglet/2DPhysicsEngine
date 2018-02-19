package components;

import java.util.ArrayList;
import java.util.Iterator;

import game.Time;
import gameobject.GameObject;
import physics.*;
import utils.Vector2;

public class RigidBody extends Component {

	public GameObject object;
	private Mesh mesh;
	
	private ArrayList<Force> forceBox;
	
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 acceleration = new Vector2(0, 0);
	
	private float mass;
	
	private float frictionCoef;
	private Force resultant = new Force(0, 0);
	private Force friction;
	
	public RigidBody(GameObject newObject, float newCoef, float newMass) {
		forceBox = new ArrayList<Force>();
		object = newObject;
		mass = newMass;
		frictionCoef = newCoef;
	}
	
	// Constructor with default mass of 1
	public RigidBody(GameObject newObject, float newCoef) {
		this(newObject, newCoef, 1);
	}
	
	public void update() {
		updateRForce();
		updateAcceleration();
		updateVelocity(Time.getDelta());
		moveMesh(Time.getDelta());
	}
	
	private void updateRForce() {
		friction = new Force(0, 0);
		resultant.setX(0);
		resultant.setY(0);
		
		Iterator<Force> iter = forceBox.iterator();
		
		while (iter.hasNext()) {
			Force f = iter.next();
			resultant.addX(f.getX());
			resultant.addY(f.getY());
			iter.remove();
		}

		// Calculates the angle of the friction force from the Normal
		float resultantVelocity = velocity.hypotonuse();
		
		if(resultantVelocity>0) {
			float angleFromNorm = PhysicsUtils.angleFromX(velocity.getY(), resultantVelocity);
			float frictionForce = mass*frictionCoef;
			
			float xFric = (float)Math.cos(angleFromNorm)*frictionForce;
			if (velocity.getX()>0) {
				xFric*=-1;
			}
			float yFric = (float)Math.sin(angleFromNorm)*frictionForce;
			if (velocity.getY()>0) {
				yFric*=-1;
			}
			
			friction.setX(xFric);
			friction.setY(yFric);
			
			resultant = resultant.sumForce(friction);
			
			System.out.println(resultant.getX());
			System.out.println(resultant.getY());
		}
	}
	
	private void updateAcceleration() {
		acceleration.setX(resultant.getX()/mass);
		acceleration.setY(resultant.getY()/mass);
	}
	
	private void updateVelocity(float time) {
		velocity.setX(velocity.getX()+acceleration.getX()*time);
		velocity.setY(velocity.getY()+acceleration.getY()*time);
		if (friction.equals(resultant)) {
			if (acceleration.getX()/velocity.getX()>0) {
				velocity.setX(0);
			}
			if (acceleration.getY()/velocity.getY()>0) {
				velocity.setY(0);
			}
		}
	}
	
	private void moveMesh(float time) {
		mesh = (Mesh) object.components.get("mesh");
		mesh.setX(mesh.getX()+velocity.getX()*time);
		mesh.setY(mesh.getY()+velocity.getY()*time);
	}
	
	public void addForce(Force force) {
		switch (force.getForceType()) {
			case force:
				break;
			case zeroVForce:
				velocity.setX(0);
				velocity.setY(0);
				break;
		}	
		forceBox.add(force);
	}
	
	public void setFriction(float newFriction) {
		frictionCoef = newFriction;
	}
	
	public float getXVelocity() {
		return velocity.getX();
	}
	
	public float getYVelocity() {
		return velocity.getX();
	}
}
