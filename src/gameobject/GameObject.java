package gameobject;

import org.newdawn.slick.opengl.Texture;

import animation.Animation;
import utils.Utils;

public abstract class GameObject {
	
	public float x;
	public float y;
	public float z;
	public int width;
	public int height;
	public String textureName;
	public Texture texture;
	public Animation animation;
	public boolean solid;
	
	public void initGameObject(float x, float y, float z, int width, int height, String textureName, boolean solid, Animation animation) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.texture = Utils.quickLoad(textureName);
		this.animation = animation;
		this.solid = solid;
	}
	
	public void update() {
		
	}
	
	public void render() {
		if (animation == null || animation.getCurrentTexture() == null) {
			Utils.drawQuadTex(texture, x, y, z, width, height);
		} else {
			Utils.drawQuadTex(animation.getCurrentTexture(), x, y, z, width, height);
		}
	}
}
