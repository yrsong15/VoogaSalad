package gameengine.controller;

import java.util.Observable;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou
 *
 */
public class GameEngineController extends Observable{

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

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
        //Update the View in some way
	}
}

