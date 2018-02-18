package physics;

import java.util.ArrayList;
import java.util.Iterator;

import components.Mesh;
import components.RigidBody;
import gameobject.GameObject;

public class Explosion {

	private float xPos;
	private float yPos;
	private float force;
	private float explosionDissapation;
	
	private ArrayList<GameObject> affectedObjects;
	
	public Explosion(float newX, float newY, float newForce, ArrayList<GameObject> objects) {
		xPos = newX;
		yPos = newY;
		force = 1000*newForce;
		affectedObjects = objects;
		explosionDissapation = 0.01f;
		applyForcesToObjects();
	}
	
	private void applyForcesToObjects() {
		Iterator<GameObject> iter = affectedObjects.iterator();
		
		while (iter.hasNext()) {
			GameObject currObj = iter.next();
			Mesh currMesh = (Mesh) currObj.components.get("mesh");
			RigidBody currRB = (RigidBody) currObj.components.get("rigidbody");
			
			float xDiff = currMesh.getX()-xPos;
			float yDiff = currMesh.getY()-yPos;
			float euclidDiff = PhysicsUtils.calculateHypotenuse(xDiff, yDiff);
			
			if (euclidDiff>0) {
				float angleFromNorm = PhysicsUtils.angleFromX(yDiff, euclidDiff);
				float forceApplied = force/(euclidDiff*euclidDiff*explosionDissapation);
				
				float forceX = (float) Math.cos(angleFromNorm)*forceApplied;
				float forceY = (float) Math.sin(angleFromNorm)*forceApplied;
				
				if (xDiff<0) {
					forceX *= -1;
				}
				
				if(yDiff<0) {
					forceY *= -1;
				}
				
				currRB.addForce(new Force(forceX, forceY));
			}
		}
	}
	
}
