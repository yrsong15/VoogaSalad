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
    public String myScrollTypeName; 
    private List<Direction> myScrollDirectionList = new ArrayList<Direction>();
    private int scrollSpeed;

    public ScrollType(String scrollType){
        this.myScrollTypeName = scrollType;
    }

    public void addScrollDirection(Direction direction){
        if(!myScrollDirectionList.contains(direction)){
        myScrollDirectionList.add(direction);
        }
    }

    public String getScrollType(){
        return this.myScrollTypeName;
    }
    
    public List<Direction> getDirections(){
    	return myScrollDirectionList;
    }

    public int getScrollSpeed(){
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed){
        this.scrollSpeed = scrollSpeed;
    }
}
