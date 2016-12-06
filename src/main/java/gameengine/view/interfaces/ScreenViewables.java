package gameengine.view.interfaces;

import javafx.scene.Node;

public interface ScreenViewables {
	/**
	 * Returns Node to add to viewable screen
	 * @return
	 */
	public Node getNode();
	
	/**
	 * Used to set initial position of the objects on the screen
	 * @param object
	 * @param xCoord
	 * @param yCoord
	 */
	public void setInitialPosition(Node object, double xCoord, double yCoord);
}
