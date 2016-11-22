package gameeditor.controller.interfaces;

import java.util.Map;

public interface IGameObjectManager {
	void createGameObject(double xPos, double yPos, double width, double height, 
			String imageFileName, Map<String, String> properties);
}
