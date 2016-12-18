package objects;

import java.util.ArrayList;
import java.util.List;
import gameengine.model.boundary.GameBoundary;
import gameengine.scrolling.ScrollDirection;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * @author pratikshasharma, Chalena Scholl
 */
public class ScrollType {
    private String myScrollTypeName; 
    private List<ScrollDirection> myScrollDirectionList = new ArrayList<ScrollDirection>();
    private double scrollSpeed;
    private GameBoundary gameBoundaries;
    
    public ScrollType(String scrollType){
    	this.myScrollTypeName = scrollType;
    }

    public ScrollType(String scrollType, GameBoundary gameBoundaries){
        this.myScrollTypeName = scrollType;
        this.gameBoundaries = gameBoundaries;
    }

    public void addScrollDirection(ScrollDirection direction){
        if(!myScrollDirectionList.contains(direction)){
        myScrollDirectionList.add(direction);
        }
    }

    public String getScrollTypeName(){
        return this.myScrollTypeName;
    }
    
    public GameBoundary getGameBoundary(){
    	return gameBoundaries;
    }
    
    public List<ScrollDirection> getDirections(){
    	return myScrollDirectionList;
    }

    public double getScrollSpeed(){
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed){
        this.scrollSpeed = scrollSpeed;
    }
    
    public void addDirectionList(ArrayList<ScrollDirection> directions){
        this.myScrollDirectionList=directions;
    }
}
