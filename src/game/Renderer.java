package game;

import gameobject.GameObject;
import utils.Utils;
import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;

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
			
			oTexture = currentObject.texture;
			oXPos = currentObject.x;
			oYPos = currentObject.y;
			oZPos = currentObject.z;
			oWidth = currentObject.width;
			oHeight = currentObject.height;
			
			Utils.drawQuadTex(oTexture, oXPos, oYPos, oZPos, oWidth, oHeight);
			
		}
	}
	
}
