package objects;

import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.scene.traversal.Direction;

/**
 * @author pratikshasharma
 */
public class ScrollType {
    public String myScrollTypeName;
    private List<Direction> myScrollDirectionList = new ArrayList<Direction>();

    ScrollType(String scrollType){
        this. myScrollTypeName = scrollType;
    }

    protected void addScrollDirection(Direction direction){
        myScrollDirectionList.add(direction);
    }

    public String getScrollType(){
        return this.myScrollTypeName;
    }
}
