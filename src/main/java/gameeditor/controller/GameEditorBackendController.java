package gameeditor.controller;

import gameeditor.controller.interfaces.ICreateGame;
import gameeditor.xml.XMLSerializer;
import gameeditor.controller.interfaces.IGameEditorBackEndController;
import objects.Game;
import objects.Level;
import objects.interfaces.IGame;

/**
 * This controller manages the Game class used within the Game Editor.
 * 
 * @author Ray Song(ys101), pratiksha sharma
 *
 */

public class GameEditorBackendController
implements IGameEditorBackEndController, ICreateGame{

    private Game myGame;
    private Level myCurrentLevel;
    private XMLSerializer mySerializer;
    @SuppressWarnings("unused")
	private IGame myGameInterface;

    public GameEditorBackendController(){
    	mySerializer = new XMLSerializer();
    }

    @Override
    public Game getGame() {
        return myGame;
    }
      
    @Override
    public void createGame(String title) {
        Game game = new Game(title);
        myGame = game;
    }
    
    public void setCurrentLevel(Level level){
        myCurrentLevel = level;
        myGame.setCurrentLevel(level);
    }

    @Override
    public void addCurrentLevelToGame() {
        myGame.addLevel(myCurrentLevel);
    }

    @Override
    public void setCurrentLevelToGame() {
        myGame.setCurrentLevel(myCurrentLevel);
    }
    
    public String serializeGame(){
    	return mySerializer.serializeGame(myGame);
    }
    
    public void setGameName(String name){
        myGame.setGameName(name);
    }
    
    public void setGame(Game game){
        this.myGame=game;
    }

}