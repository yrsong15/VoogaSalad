/**
 * 
 */
package gameengine.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * @author Noel Moon (nm142)
 *
 */
public class SplashScreen {
	public static final double myAppWidth = 300;
	public static final double myAppHeight = 300;

	private Scene myScene;
	
	public SplashScreen() {
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		Text scoreText = new Text(50, 50, "Your Score: ");
		Text highScoreText = new Text (50, 100, "High Scores");
		root.getChildren().addAll(scoreText, highScoreText);
		return root;
	}
}
