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
import objects.interfaces.ILevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Delia on 12/11/2016.
 */
public abstract class ScoreScreen {

    protected double myAppWidth = 400;
    protected double myAppHeight = 340;
    protected NodeFactory myFactory = new NodeFactory();
    private Scene myScene;
    private ILevelInfo myLevel;
    private IGameEngineUI myGameEngine;
    private List<Integer> highScores;
    private BorderPane root;

    public ScoreScreen(ILevelInfo level, ArrayList<Integer> highScores, IGameEngineUI gameengine) {
        this.highScores = highScores;
        this.myLevel = level;
        this.myScene = new Scene(makeRoot(), myAppWidth, myAppHeight);
        this.myGameEngine = gameengine;
//        this.commandInterface = commandInterface;
    }

    private BorderPane makeRoot() {
        ImageView background = makeBackground();
        background.setFitHeight(myAppHeight);
        background.setFitWidth(myAppWidth);
        Rectangle backdrop = myFactory.makeBackdrop(20, 20, 350, 300, Color.WHITE);
        root = new BorderPane();
        Text score = new Text(50, 50, "Your Score: " + Integer.toString(myLevel.getScore()));
        score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Text highScoreText = new Text (50, 100, "High Scores");
        highScoreText.setFill(Color.RED);
        highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        root.getChildren().addAll(background, backdrop, score, highScoreText);
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
//        exit.setOnMouseClicked(e -> {
//            commandInterface.stop();
//            //stage.close();
//        });
        Button replay = replayTemplate.getButton();
        replay.setOnMouseClicked(e -> myGameEngine.pause());
//        root.getChildren().addAll(exit, replay);
        return root;
    }

    public Scene getScene(){
        return myScene;
    }

    public abstract String getStageTitle();


    protected abstract ImageView makeBackground();
//    Scene getScene();
}

