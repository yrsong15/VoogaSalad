package base.gameengine.model;


/**
 * This interface should be implemented when adding a type of scrolling
 * @author Chalena
 */
public interface Scrolling {
	
	
	/**
	 * Sets how fast the screen will be scrolled
	 * @param speed
	 */
	void setSpeed(double speed);
	
	
	/**
	 * Completes the scrolling of the screen based on the speed
	 */
	void scrollScreen();

}
