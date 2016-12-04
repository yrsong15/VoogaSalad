package objects;

import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * @author pratikshasharma, Chalena Scholl
 */
public class ScrollType {
    private String myScrollTypeName; 
    private List<Direction> myScrollDirectionList = new ArrayList<Direction>();
    private double scrollSpeed;

    public ScrollType(String scrollType){
        this.myScrollTypeName = scrollType;
    }

    public void addScrollDirection(Direction direction){
        if(!myScrollDirectionList.contains(direction)){
        myScrollDirectionList.add(direction);
        }
    }

    public String getScrollTypeName(){
        return this.myScrollTypeName;
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
