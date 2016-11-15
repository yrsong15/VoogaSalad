package base.gameengine.view;

import javafx.scene.Node;

public interface ScreenViewables {
	/**
	 * Returns Node to add to viewable screen
	 * @return
	 */
	public Node getNode();
	
	/**
	 * Used to set initial position of the object on the screen
	 * @param object
	 * @param xCoord
	 * @param yCoord
	 */
	public void setInitialPosition(Node object, double xCoord, double yCoord);
}
