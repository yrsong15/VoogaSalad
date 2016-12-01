/**
 * 
 */
package gameengine.view;

import java.util.ArrayList;

import general.NodeFactory;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import objects.Level;
import org.w3c.dom.css.Rect;

/**
 * @author Noel Moon (nm142)
 *
 */
public class HighScoreScreen {
	public static final double myAppWidth = 400;
	public static final double myAppHeight = 340;
	private NodeFactory myFactory = new NodeFactory();
	private Scene myScene;
	private Level myLevel;
	private ArrayList<Integer> myHighScores;
	private BorderPane root;
	
	public HighScoreScreen(Level level, ArrayList<Integer> highScores) {
		myHighScores = highScores;
		myLevel = level;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		ImageView background = myFactory.makeBackgroundImage("GameOver");
		background.setFitHeight(myAppHeight);
		background.setFitWidth(myAppWidth);
		Rectangle backdrop = myFactory.makeBackdrop(20, 20, 350, 300);
		root = new BorderPane();
		Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
		score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		Text highScoreText = new Text (50, 100, "High Scores");
		highScoreText.setFill(Color.RED);
		highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		root.getChildren().addAll(background, backdrop, score, highScoreText);
		int index = 0;
		for (Integer highScore : myHighScores) {
			Text text = new Text (50, 120 + index * 20, " " + (index + 1) + ".\t" + Integer.toString(highScore));
			text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
			root.getChildren().add(text);
			index++;
		}
		return root;
	}

	public BorderPane getRoot(){
		return root;
	}
}