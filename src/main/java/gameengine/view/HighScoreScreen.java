/**
 *
 */
package gameengine.view;

import java.util.ArrayList;
import java.util.List;

import frontend.util.ButtonTemplate;
import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.ControlInterface;
import general.NodeFactory;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import objects.Level;
import side.Side;
import viewformatter.ViewFormatter;

import org.w3c.dom.css.Rect;

/**
 * @author Noel Moon (nm142)
 *         Delia Li (dl202)
 */
public class HighScoreScreen {

	public static final double myAppWidth = 400;
	public static final double myAppHeight = 340;
	private NodeFactory myFactory = new NodeFactory();
	private Scene myScene;
	private Level myLevel;
	private List<Integer> highScores;
	private BorderPane root;
	private CommandInterface commandInterface;
	
	public HighScoreScreen(Level level, List<Integer> highScores, CommandInterface commandInterface) {
		this.highScores = highScores;
		myLevel = level;
		myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
		this.commandInterface = commandInterface;
	}
	
	public Scene getScene() {
		return myScene;
	}
	
	private BorderPane makeRoot() {
		root = new BorderPane();
		ViewFormatter formatter = new ViewFormatter();
        
		ImageView background = myFactory.makeBackgroundImage("GameOver");
		background.setFitHeight(myAppHeight);
		background.setFitWidth(myAppWidth);
		formatter.addView(background,"Background")
 			.setZ(-1);
		
		Rectangle backdrop = myFactory.makeBackdrop(20, 20, 350, 300, Color.WHITE);
		formatter.addView(backdrop,"Back Drop");
//		Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
//		score.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
//		score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		Text highScoreText = new Text ("High Scores");
		highScoreText.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
		highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		highScoreText.setFill(Color.RED);
		formatter.addView(highScoreText, "High Score Text")
    		.setXAsFractionOfWidth(.05, "Back Drop")
    		.setYAsFractionOfHeight(.15, "Back Drop")
    		.centerXInScreen();
		
		VBox vbox = new VBox();
		int index = 0;
		for (Integer highScore : highScores) {
			Text text = new Text (50, 120 + index * 20, " " + (index + 1) + ".\t" + Integer.toString(highScore));
			text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
			text.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
            vbox.getChildren().add(text);
			index++;
		}
		
		formatter.addView(vbox,"High Scores")
    		.position(Side.BOTTOM, "High Score Text", 10);
    
		ButtonTemplate exitTemplate = new ButtonTemplate("Quit", 10, 10);
		ButtonTemplate replayTemplate = new ButtonTemplate("Replay", 20, 20);
		Button exit = exitTemplate.getButton();
		exit.setOnMouseClicked(e -> {
			commandInterface.stop();
			//stage.close();
		});
		Button replay = replayTemplate.getButton();
		replay.setOnMouseClicked(e -> commandInterface.reset());
		root.getChildren().addAll(exit, replay);
		return root;
	}
//=======
//    public static final double myAppWidth = 400;
//    public static final double myAppHeight = 340;
//    private NodeFactory myFactory = new NodeFactory();
//    private Scene myScene;
//    private Level myLevel;
//    private List<Integer> highScores;
//    private BorderPane root;
//    private CommandInterface commandInterface;
//
//    public HighScoreScreen(Level level, List<Integer> highScores, CommandInterface commandInterface) {
//        this.highScores = highScores;
//        myLevel = level;
//        myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
//        this.commandInterface = commandInterface;
//    }
//
//    public Scene getScene() {
//        return myScene;
//    }
//
//    private BorderPane makeRoot() {
//        ImageView background = myFactory.makeBackgroundImage("GameOver");
//        background.setFitHeight(myAppHeight);
//        background.setFitWidth(myAppWidth);
//        Rectangle backdrop = myFactory.makeBackdrop(20, 20, 350, 300, Color.WHITE);
//        root = new BorderPane();
////		Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
////		score.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
////		score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
//        Text highScoreText = new Text(50, 100, "High Scores");
//        highScoreText.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
//        highScoreText.setFill(Color.RED);
//        highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
//        root.getChildren().addAll(background, backdrop, highScoreText);
//        int index = 0;
//        for (Integer highScore : highScores) {
//            Text text = new Text(50, 120 + index * 20, " " + (index + 1) + ".\t" + Integer.toString(highScore));
//            text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
//            text.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
//            root.getChildren().add(text);
//            index++;
//        }
//        ButtonTemplate exitTemplate = new ButtonTemplate("Quit", 10, 10);
//        ButtonTemplate replayTemplate = new ButtonTemplate("Replay", 20, 20);
//        Button exit = exitTemplate.getButton();
//        exit.setOnMouseClicked(e -> {
//            commandInterface.stop();
//            //stage.close();
//        });
//        Button replay = replayTemplate.getButton();
//        replay.setOnMouseClicked(e -> commandInterface.reset());
//        root.getChildren().addAll(exit, replay);
//        return root;
//    }
//>>>>>>> b14313b043b3d63d453bf9f071a9433286eb9871

//	public BorderPane getRoot(){
//		return root;
//	}
}
