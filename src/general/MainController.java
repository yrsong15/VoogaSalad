package general;

import base.gameengine.controller.GameEngineController;
import base.gameengine.controller.GameParser;
import javafx.stage.Stage;

/**
 * @author ericsong
 *
 */
public class MainController {
	
	GameEngineController gameEngineController;
	
	public MainController(Stage stage){
		gameEngineController = new GameEngineController(new GameParser());
	}

}
