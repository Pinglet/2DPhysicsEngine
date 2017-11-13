package gameobject;

import org.newdawn.slick.opengl.Texture;

import utils.Utils;

public abstract class GameObject {
	
	public float x;
	public float y;
	public float z;
	public int width;
	public int height;
	public String textureName;
	public Texture texture;
	
	public void initGameObject(float x, float y, float z, int width, int height, String textureName) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.textureName = textureName;
		this.texture = Utils.quickLoad(textureName);
	}
	
	public void update() {
		
	}
	
}
