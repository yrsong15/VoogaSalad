package gameeditor.interfaces;


/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface IGameEditorCreateGObject {
	void createGObject(String type);
	void setPosition(String isRandom);
	void addXCorToProperty(String xcor);
	void addYCorToProperty(String ycor);
	void addSpacingToProperty(String spacing);
	void addMinHeightToProperty(String minHeight);
	void addMaxHeightToProperty(String maxHeight);
	
	void setImage(String filename);
	
	void setPropertyHealth(String health);
	void setPropertyDamage(String damage);
	void setPropertyPoints(String points);
	void setPropertyStatic(String isStatic);
	
	void setBehaviorInput(String input);
	void setBehaviorCommand(String command);
	void setBehaviorMethod(String behavior);
}
