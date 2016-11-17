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
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou
 *
 */
public class GameEngineController extends Observable implements Observer{

	private String xmlFileName;
    private GameParser parser;

	public GameEngineController() {
		parser = new GameParser();
	}

	public void startGame() {

	}

	public void setCurrentXML(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	@Override
	public void update(Observable o, Object arg) {

	}
	
	/**
	 * Pauses the simulation
	 */
	public void stopGame () {

	}

	/**
	 * Resumes the simulation
	 */
	public void resumeGame() {

	}
}

