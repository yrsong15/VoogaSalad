package base.gameengine.controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import base.gameengine.model.GameData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * @author ericsong
 *
 */
public class GameEngineController implements IGameEngineController{
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	
	//FOR NEXT PERSON WHO WORKS ON THIS:
	//Is the GameClock a Model or Controller thing?
	
	private Timeline gameClock;
	private Parser parser;
	private GameData data;
	
	//Main Character
	private Object playableCharacter;
	
	//Stuff that doesn't disappear when you touch then, e.g. platforms or Mario pipes
	//Each of these Objects probably need to extend the GameEngineView interfaces
	private List<Object> fixedObjects;
	
	//Stuff that disappear when you touch them, e.g. Goombas/Turtles/Coins in Mario
	//Each of these Objects probably need to extend the GameEngineView interfaces
	private List<Object> collapsibleObjects;

	public GameEngineController(Parser parser,GameData data) {
		this.parser = parser;
		this.data = data;
	}

	@Override
	public void startGame() {
		initGameClock();
		initGameMusic("DUMMYTRACK.wav");
		data = new GameData("BACKGROUNDIMAGE.jpg", "DUMMYTRACK.wav");
	}

	@Override
	public void setCurrentXML(String fileName) {

	}

	@Override
	public void update(Observable o, Object arg) {

	}
	
	/**
	 * Pauses the simulation
	 */
	public void stopGame () {
    	try {
    		gameClock.stop();
    	}
    	catch(NullPointerException e) {
    		System.out.println("Game clock hasn't been initialized yet");
    	}
	}

	/**
	 * Resumes the simulation
	 */
	public void resumeGame() {
		gameClock.play();
	}
	
	private void step(){
		
	}
	
	private void initGameClock(){
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> step());
		gameClock = new Timeline();
		gameClock.setCycleCount(Timeline.INDEFINITE);
		gameClock.getKeyFrames().add(frame);
		gameClock.play();
	}
	
	private void initGameMusic(String currentTrack){
		final Task task = new Task() {
	        @Override
	        protected Object call() throws Exception {
	            AudioClip audio = new AudioClip(getClass().getResource(currentTrack).toExternalForm());
	            audio.setVolume(0.75f);
	            audio.setCycleCount(Timeline.INDEFINITE);
	            audio.play();
	            return null;
	        }
	    };
	    Thread thread = new Thread(task);
	    thread.start();
	}

}

