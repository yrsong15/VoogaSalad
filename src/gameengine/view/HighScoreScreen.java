/**
 * 
 */
package gameengine.view;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public class HighScoreScreen {
	public static final double myAppWidth = 300;
	public static final double myAppHeight = 300;

	private Scene myScene;
	private Level myLevel;
	private ArrayList<Integer> myHighScores;
	
	public HighScoreScreen(Level level, ArrayList<Integer> highScores) {
		myHighScores = highScores;
		myLevel = level;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		BorderPane root = new BorderPane();
		Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
		Text highScoreText = new Text (50, 100, "High Scores");
		root.getChildren().addAll(score, highScoreText);
		int index = 0;
		for (Integer highScore : myHighScores) {
			Text text = new Text (50, 123 + index * 23, Integer.toString(highScore));
			root.getChildren().add(text);
			index++;
		}
		return root;
	}
}
