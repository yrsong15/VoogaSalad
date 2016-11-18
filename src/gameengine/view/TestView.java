/**
 * 
 */
package gameengine.view;

/**
 * @author Noel Moon (nm142)
 *
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class TestView extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		IGameEngineUI ui = new GameEngineUI();
		stage = new Stage();
		stage.setTitle("Game");
		
		stage.setScene(ui.getScene());
		stage.show();
	}

}
