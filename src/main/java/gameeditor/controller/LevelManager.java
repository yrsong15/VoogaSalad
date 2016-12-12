package gameeditor.controller;
import java.util.ArrayList;
import java.util.List;
import gameeditor.controller.interfaces.ICreateLevel;
import objects.Level;
import objects.interfaces.ILevel;


/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Pratiksha Sharma, Ray Song(ys101)
 */

public class LevelManager implements  ICreateLevel{
    private Level myLevel;
    private List<Level> myLevels;

    public LevelManager(){
    	myLevels = new ArrayList<Level>();
    }
    
    public Level getLevel(){
    	return myLevel;
    }
    
    public void createLevel(Level level) {
        this.myLevel = level;
        myLevels.add(level);
    }

    public void setCurrentLevel(Level level){
        this.myLevel = level; 
    }
    
    
}
