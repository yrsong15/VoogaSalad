package gameeditor.interfaces;

import java.util.Map;

/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface ICreateGameObject {
	void createGameObject(double xPos, double yPos, double width, double height, String imageFileName, Map<String, String> properties);
	Map<?, ?> createProperties();
	void addToProperties(String key, String value);
}


//void setPosition(String isRandom);
//void addXCorToProperty(String xcor);
//void addYCorToProperty(String ycor);
//void addSpacingToProperty(String spacing);
//void addMinHeightToProperty(String minHeight);
//void addMaxHeightToProperty(String maxHeight);
//
//void setImage(String filename);
//
//void setPropertyHealth(String health);
//void setPropertyDamage(String damage);
//void setPropertyPoints(String points);
//void setPropertyStatic(String isStatic);
//
//void setBehaviorInput(String input);
//void setBehaviorCommand(String command);
//void setBehaviorMethod(String behavior);
