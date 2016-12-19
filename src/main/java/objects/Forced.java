package objects;

import com.sun.javafx.scene.traversal.Direction;

/**
 * @author pratikshasharma
 */
public class Forced extends ScrollType{

    public Forced(String scrollType){
        super(scrollType);
    }

    public void addDirectionList(Direction myDirection){
        addScrollDirection(myDirection);
    }

}
