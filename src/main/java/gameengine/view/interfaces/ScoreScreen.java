package gameengine.view.interfaces;

import frontend.util.ButtonTemplate;
import gameengine.controller.interfaces.CommandInterface;
import general.NodeFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import objects.Level;
import objects.interfaces.ILevelInfo;

import java.util.List;

/**
 * Created by Delia on 12/11/2016.
 */
public abstract class ScoreScreen {

    protected double myAppWidth = 400;
    protected double myAppHeight = 340;
    protected NodeFactory myFactory = new NodeFactory();
    private Scene myScene;
    private Level myLevel;
    private List<Integer> highScores;
    private BorderPane root;
    private CommandInterface commandInterface;

    public ScoreScreen(ILevelInfo level, List<Integer> highScores, IGameEngineUI iGameEngine) {
        this.highScores = highScores;
       // this.myLevel = level;
        this.myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
        //this.commandInterface = iGameEngine;
    }

    private BorderPane makeRoot() {
        ImageView background = makeBackground();
        background.setFitHeight(myAppHeight);
        background.setFitWidth(myAppWidth);
        Rectangle backdrop = myFactory.makeBackdrop(20, 20, 350, 300, Color.WHITE);
        root = new BorderPane();
//        Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
//        score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Text highScoreText = new Text (50, 100, "High Scores");
        highScoreText.setFill(Color.RED);
        highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        root.getChildren().addAll(background, backdrop, highScoreText);
        int index = 0;
        for (Integer highScore : highScores) {
            Text text = new Text (50, 120 + index * 20, " " + (index + 1) + ".\t" + Integer.toString(highScore));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            root.getChildren().add(text);
            index++;
        }
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


    protected abstract ImageView makeBackground();
//    Scene getScene();
}

