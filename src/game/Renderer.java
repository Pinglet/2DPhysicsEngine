package game;

import gameobject.GameObject;
import utils.Utils;
import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;
import components.Mesh;

public class Renderer {
	
	public Renderer() {
		
	}
	
	// Takes a collection of objects and draws them in the level
	public void renderObjects(ArrayList<GameObject> gameObjects) {
		
		Texture oTexture;
		float oXPos;
		float oYPos;
		float oZPos;
		int oWidth;
		int oHeight;		
		
		for (GameObject currentObject : gameObjects) {
			
			Mesh mesh = (Mesh) currentObject.components.get("mesh");
			
			if (mesh!=null) {
				oTexture = currentObject.currentTexture;
				oXPos = mesh.getX();
				oYPos = mesh.getY();
				oZPos = mesh.getZ();
				oWidth = mesh.getWidth();
				oHeight = mesh.getHeight();
				
				Utils.drawQuadTex(oTexture, oXPos, oYPos, oZPos, oWidth, oHeight);
				
			}			
		}
	}
	
}
