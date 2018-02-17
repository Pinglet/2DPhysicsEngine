package gameobject;


import components.Component;
import utils.Utils;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;

public class GameObject {
	
	public HashMap<String, Component> components;
	public Texture currentTexture;
	
	public GameObject() {
		components = new HashMap<String, Component>();
	}
	
	public void addComponent(String key, Component newComponent) {
		Component componentCheck = components.get(key);
		if (componentCheck==null) {
			components.put(key, newComponent);
		} else {
			System.err.println("Component already exisits for this object");
		}
	}
	
	public void setTexture(String textureName) {
		currentTexture = Utils.quickLoad(textureName);
	}
	
	public void update() {
		for (Component component : components.values()) {
			component.update();
		}
	}
	
}
