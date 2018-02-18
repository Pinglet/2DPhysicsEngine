package physics;

public class PhysicsUtils {

	public static float calculateHypotenuse(float sideOne, float sideTwo) {
		return (float)Math.sqrt(sideOne*sideOne+sideTwo*sideTwo);
	}

	public static float angleFromX(float opp, float hyp) {
		return (float) Math.abs(Math.asin(opp/hyp));
	}
}
