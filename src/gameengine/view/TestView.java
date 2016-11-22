/**
 * 
 */
package gameengine.view;

/**
 * @author Noel Moon (nm142)
 *
 */

import gameengine.view.interfaces.IGameEngineUI;
import javafx.application.Application;
import javafx.stage.Stage;
import objects.GameObject;
import objects.Level;

public class TestView extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) {
		Level level = new Level(1);
		level.addGameObject(new GameObject(50, 50, 5, 5, "Sprite/bird2.gif", null));
		IGameEngineUI ui = new GameEngineUI();
		stage = new Stage();
		stage.setTitle("Game");
		
		stage.setScene(ui.getScene());
		stage.show();
	}

}
