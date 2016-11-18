package gameengine.controller;

import java.util.Observable;

import gameengine.model.CollisionChecker;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou
 *
 */
public class GameEngineController extends Observable{

	private String xmlFileName;
    private GameParser parser;
    private CollisionChecker collisionChecker;
    private boolean gameOver;

	public GameEngineController() {
		parser = new GameParser();
		collisionChecker = new CollisionChecker();
	}

	public void startGame() {
        parser.processXML(xmlFileName);
        gameOver = false;
        while (!gameOver){
        	loopGame();
        }
	}
	
	/**
	 * Applies gravity and scrolls, checks for collisions
	 */
	public void loopGame(){
		
	}

	public void setCurrentXML(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
        //Update the View in some way
	}
}

