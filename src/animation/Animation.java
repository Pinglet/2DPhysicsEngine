package animation;

import org.newdawn.slick.opengl.Texture;

import game.Timer;

public abstract class Animation {
	
	//Animations work using a tileset arranged in a grid 
	private Texture currentTexture;
	private Texture[] textureSequence;
	private float[] delaySequence;
	private Timer timer;
	
	private Animation() {
		
	}
	
	public void initAnimation(Texture[] textureSequence, float[] delaySequence, int buffer) {
		//textureSequence should be 1 longer than delaySequence. The final texture in the array is the new 
		//persistent texture of the object. Final texture should be null if original texture is returned.
		this.textureSequence = textureSequence;
		this.delaySequence = delaySequence;
		
		float totalDelay = 0;
		for (float f : delaySequence) {
			totalDelay += f;
		}
		timer = new Timer(totalDelay);
	}
	
	public void update() {
		if (timer.isRunning()) {
			for (int i = 0; i < delaySequence.length; i++) {
				if (timer.progress() <= delaySequence[i]) {
					currentTexture = textureSequence[i];
					break;
				}
			}
		}	
	}
	
	public void play() {
		timer.start();
	}
	
	public Texture getCurrentTexture() {
		return currentTexture;
	}
	
	public boolean isRunning() {
		return timer.isRunning();
	}
}
