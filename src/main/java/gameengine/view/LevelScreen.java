package gameengine.view;

import frontend.util.ButtonTemplate;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.ScoreScreen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import objects.Level;
import objects.interfaces.ILevelInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Delia on 12/11/2016.
 */
public class LevelScreen extends ScoreScreen { 
    public LevelScreen(List<Integer> highScores, int time, Map<Long, Integer> scoreMapping, IGameEngineUI iGameEngine) {
        super(highScores, time, scoreMapping, iGameEngine);
    }

    @Override
    protected ImageView makeBackground() {
        return myFactory.makeBackgroundImage("LevelUp");
    }

    @Override
    public String getStageTitle(){
        return "LEVEL UP";
    }

    @Override
    protected void addButtons() {
        ButtonTemplate nextLevelButton = new ButtonTemplate("NextLevel", 10, 10);//myAppWidth / 2, myAppHeight - 50);
        Button level = nextLevelButton.getButton();
        level.setOnMouseClicked(e -> getMyGameEngine().pause());
        getRoot().getChildren().add(level);
    }

//    @Override
//    public Scene getScene() {
//        return null;
//    }
}
