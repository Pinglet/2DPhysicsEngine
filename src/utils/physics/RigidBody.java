package utils.physics;

public class RigidBody<ObjectType> {
	
	private float mass;
	private final ObjectType object;
	
	// Constructor
	public RigidBody(float reqMass, ObjectType reqObject) {
		mass = reqMass;
		object = reqObject;
	}
	
	public void setMass(float reqMass) {
		mass = reqMass;
	}
	
	public float getMass(float reqMass) {
		return mass;
	}
	
}