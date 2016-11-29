package gameeditor.controller;
import java.util.HashMap;
import java.util.Map;
import gameeditor.controller.interfaces.ICreateLevel;
import gameeditor.controller.interfaces.ILevelManager;
import objects.GameObject;
import objects.Level;
import objects.interfaces.ILevel;


/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Ray Song(ys101)
 */

public class LevelManager implements  ICreateLevel{
    private Level myLevel;
    private Map<Level, ILevel> myLevels;
    //private LevelSettings mySettings;
    //private Settings mySettings;

    public LevelManager(){
        myLevels = new HashMap<Level,ILevel>();
    }
    
    public void createLevel(Level level, ILevel levelInterface) {
        this.myLevel = level;
        myLevels.put(level, levelInterface);
    }

//    public void setCurrentLevel(Level level){
//        this.myLevel = level; 
//    }
    
    public void addLevel(Level level, ILevel levelManager){
        myLevels.put(level, levelManager);
}
    
    public ILevel getCurrentLevelInterface(){
        return myLevels.get(myLevel);
    }

    
    
//    public Level getLevel() {
//        return myLevel;
//    }
//
//    @Override
//    public void addWinConditions(String type, String action) {
//        myLevel.addWinCondition(type, action);
//    }
//
//    @Override
//    public void addLoseConditions(String type, String action) {
//        myLevel.addLoseCondition(type, action);
//    }
//
//    @Override
//    public void addScore(double score) {
//        myLevel.setScore(score);
//    }
//
//    @Override
//    public void addTime(double time) {
//        myLevel.setTime(time);
//    }
    
//
//    @Override
//    public void addGameObjectToLevel(GameObject ob) {
//        return;
//    }
//
//    @Override
//    public void addBackgroundMusic(String musicFilePath) {
//        System.out.println(" Added Background Music " + musicFilePath);
//        myLevel.getViewSettings().setMusicFile(musicFilePath);
//    }
//
//    @Override
//    public void addBackgroundImage(String backgroundFilePath) {
//        System.out.println(" Here" );
//        myLevel.addBackgroundImage(backgroundFilePath);
//    }
//
//    @Override
//    public void setGameObjectToMainCharacter(GameObject object) {
//        // TODO Auto-generated method stub
//        myLevel.setMainCharacter(object);
//
//    }
//    
//    public ILevelManager getLevelManagerInterface(){
//       return myLevel;
//    }
//
//    @Override
//    public void createLevel (int levelNumber) {
//        // TODO Auto-generated method stub
//        
//    }
    
    
    
}
