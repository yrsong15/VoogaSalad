package objects;

import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.scene.traversal.Direction;

/**
 * @author pratikshasharma, Chalena Scholl
 */
public class ScrollType {
    public String myScrollTypeName; 
    private List<Direction> myScrollDirectionList = new ArrayList<Direction>();

    public ScrollType(String scrollType){
        this.myScrollTypeName = scrollType;
    }

    public void addScrollDirection(Direction direction){
        myScrollDirectionList.add(direction);
    }

    public String getScrollType(){
        return this.myScrollTypeName;
    }
    
    public List<Direction> getDirections(){
    	return myScrollDirectionList;
    }
    
    
}
