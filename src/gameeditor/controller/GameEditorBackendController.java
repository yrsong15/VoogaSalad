package gameeditor.controller;

import java.util.HashMap;
import java.util.Map;
import gameeditor.controller.interfaces.ICreateGame;
import gameeditor.controller.interfaces.ICreateGameObject;
import gameeditor.controller.interfaces.ILevelManager;
import gameeditor.xml.XMLSerializer;
import gameeditor.controller.interfaces.IGameEditorController;
import objects.Game;
import objects.GameObject;
import objects.Level;

/**
 * This is the central class for the Game Editor backend that contains all the
 * methods that can be called by the Game Editor frontend.
 * 
 * @author Ray Song(ys101)
 *
 */

public class GameEditorBackendController
implements IGameEditorController, ICreateGame, ILevelManager, ICreateGameObject {

    private Game myGame;
    private Level myCurrentLevel;
    private GameObject myGameObject;
    private HashMap<String, String> myControlMap;


    public GameEditorBackendController(){
        //myCurrentLevel = new Level();  
    }

    @Override
    public Game getGame() {
        return myGame;
        
    }

    @Override
    public void createGame(String title) {
        System.out.println("Created Game " + title);
        Game game = new Game(title);
        myGame = game;
    }

//    @Override
//    public void createLevel(int levelNumber) {
//        myCurrentLevel = new Level(levelNumber);
//    }

    @Override
    public void addCurrentLevelToGame() {
        myGame.addLevel(myCurrentLevel);
    }

    @Override
    public void addGameObjectToLevel(GameObject ob) {
        myCurrentLevel.addGameObject(ob);
    }

    @Override
    public void addWinConditions(String type, String action) {
        myCurrentLevel.addWinCondition(type, action);
    }

    @Override
    public void addLoseConditions(String type, String action) {
        myCurrentLevel.addLoseCondition(type, action);
    }

    @Override
    public void addPropertiesToGameObject(Map<String, String> properties) {
        myGameObject.setPropertiesList(properties);

    }

    @Override
    public void addScore(double score) {
        myCurrentLevel.setScore(score);
    }

    @Override
    public void addTime(double time) {
        myCurrentLevel.setTime(time);
    }

    @Override
    public void addBackgroundMusic(String musicFilePath) {
        myCurrentLevel.getViewSettings().setMusicFile(musicFilePath);
    }

    @Override
    public void addBackgroundImage(String backgroundFilePath) {
        System.out.println(" Backgtound Image Set " + backgroundFilePath);
        myCurrentLevel.getViewSettings().setBackgroundFilePath(backgroundFilePath);
    }

    @Override
    public void setCurrentLevelToGame() {
        myGame.setCurrentLevel(myCurrentLevel);
    }

    @Override
    public void setGameObjectToMainCharacter(GameObject object) {
        myCurrentLevel.setMainCharacter(object);
    }

    public GameObject getCurrentGameObject() {
        return myGameObject;
    }

    public void setCurrentLevel(Level level){
        myCurrentLevel = level;
    }
    
    public void setGameName(String name){
        myGame.setGameName(name);
    }
}