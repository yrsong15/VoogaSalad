package objects;

import gameengine.scrolling.ScrollDirection;

/**
 * @author pratikshasharma
 */
public class Forced extends ScrollType{


    public Forced(String scrollType){
        super(scrollType);
    }
      

    public void addDirectionList(ScrollDirection myDirection){
        addScrollDirection(myDirection);
    }

}
