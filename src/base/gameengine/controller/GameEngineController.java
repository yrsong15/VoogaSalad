package base.gameengine.controller;

import java.lang.reflect.InvocationTargetException;
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
public class GameEngineController implements Observer{

	private String xmlFileName;
    private GameParser parser;

	public GameEngineController() {
		parser = new GameParser();
	}

	public void startGame() {
        parser.processXML(xmlFileName);
	}

	public void setCurrentXML(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}

