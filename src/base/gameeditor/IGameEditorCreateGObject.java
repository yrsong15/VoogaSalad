package base.gameeditor;

import usecases.mockObjects.UseCaseBehavior;

/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface IGameEditorCreateGObject {
	void createGObject(String type);
	void setPosition(boolean isRandom);
	
	void setBehavior(UseCaseBehavior behavior);
}
