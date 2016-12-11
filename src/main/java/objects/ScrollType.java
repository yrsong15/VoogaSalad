package objects;

import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.scene.traversal.Direction;

import gameengine.model.boundary.ScreenBoundary;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * @author pratikshasharma, Chalena Scholl
 */
public class ScrollType {
    private String myScrollTypeName; 
    private List<Direction> myScrollDirectionList = new ArrayList<Direction>();
    private double scrollSpeed;
    private ScreenBoundary gameBoundaries;
    
    public ScrollType(String scrollType){
    	this.myScrollTypeName = scrollType;
    }

    public ScrollType(String scrollType, ScreenBoundary gameBoundaries){
        this.myScrollTypeName = scrollType;
        this.gameBoundaries = gameBoundaries;
    }

    public void addScrollDirection(Direction direction){
        if(!myScrollDirectionList.contains(direction)){
        myScrollDirectionList.add(direction);
        }
    }

    public String getScrollTypeName(){
        return this.myScrollTypeName;
    }
    
    public ScreenBoundary getScreenBoundary(){
    	return gameBoundaries;
    }
    
    public List<Direction> getDirections(){
    	return myScrollDirectionList;
    }

    public double getScrollSpeed(){
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed){
        this.scrollSpeed = scrollSpeed;
    }
}
