package gameeditor.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameeditor.controller.interfaces.ICreateLevel;
import objects.Level;
import objects.interfaces.ILevel;


/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Ray Song(ys101)
 */

public class LevelManager implements  ICreateLevel{
    private Level myLevel;
//    private Map<Level, ILevel> myLevels;
    private List<Level> myLevels;

    public LevelManager(){
//        myLevels = new HashMap<Level,ILevel>();
    	myLevels = new ArrayList<Level>();
    }
    
    public Level getLevel(){
    	return myLevel;
    }
    
    public void createLevel(Level level) {
        this.myLevel = level;
//        myLevels.put(level, levelInterface);
        myLevels.add(level);
    }

    public void setCurrentLevel(Level level){
        this.myLevel = level; 
    }
    
    
//    public ILevel getCurrentLevelInterface(){
//        return myLevels.get(myLevel);
//    }
    
}
