package gameengine.scrolling;

import java.util.List;
import objects.GameObject;


/**
 * This is a general scrolling class that shifts all objects given to it to give the 
 * appearance of movement to whichever objects are not given to it.
 * @author Chalena Scholl, 
 */
public abstract class GeneralScroll implements Scrolling{
	
		public void scrollDirection(ScrollDirection scrollDir){
			  switch (scrollDir) {
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case UP:
				break;
			  }	
		}
	
	    public void scrollUP(List<GameObject> gameObjects, double speed){
			for(GameObject obstacle: gameObjects){
				double newPos = obstacle.getYPosition() + speed;
				obstacle.setYPosition(newPos);
			}
	    }
	    

	    public void scrollDOWN(List<GameObject> gameObjects, double speed){
	    	for(GameObject obstacle: gameObjects){
				double newPos = obstacle.getYPosition() - speed;
				obstacle.setYPosition(newPos);
	    	}
	    }

	    public void scrollRIGHT(List<GameObject> gameObjects, double speed){
	        for(GameObject obstacle: gameObjects){
	            double newPos = obstacle.getXPosition() - speed;
	            obstacle.setXPosition(newPos);
	        }
	    }

	    public void scrollLEFT(List<GameObject> gameObjects, double speed){
	        for(GameObject obstacle: gameObjects){
	            double newPos = obstacle.getXPosition() + speed;
	            obstacle.setXPosition(newPos);
	        }
	    }



}

