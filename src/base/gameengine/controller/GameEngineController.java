package base.gameengine.controller;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ericsong
 *
 */
public class GameEngineController implements IGameEngineController{
	
	Parser parser;

	public GameEngineController(Parser parser) {
		this.parser = parser;
		
	}

	@Override
	public void startGame() {

	}

	@Override
	public void setCurrentXML(String fileName) {

	}

	@Override
	public void update(Observable o, Object arg) {

	}

}
