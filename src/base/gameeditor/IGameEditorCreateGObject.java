package base.gameeditor;


/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface IGameEditorCreateGObject {
	void createGObject(String type);
	void setPosition(String isRandom);
	
	void setImage(String filename);
	
	void setBehaviorInput(String input);
	void setBehaviorCommand(String command);
	void setBehaviorMethod(String behavior);
	
	void setPropertyHealth(String health);
	void setPropertyDamage(String damage);
	void setPropertyPoints(String points);
	void setPropertyDestructible(String isDestructible);
	void setPropertyStatic(String isStatic);
	
	
}
