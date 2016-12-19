package gameengine.view;

import frontend.util.ButtonTemplate;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.ScoreScreen;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.util.List;
import java.util.Map;

/**
 * Created by Delia on 12/11/2016.
 */
public class LevelScreen extends ScoreScreen { 
    public LevelScreen(List<Integer> highScores, int time, Map<Long,
            Integer> scoreMapping, IGameEngineUI iGameEngine) {
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
    protected String makeScreenText() {
        return "Click anywhere to play the next level";
    }

    @Override
    protected void addButtons() {
        ButtonTemplate nextLevelButton = new ButtonTemplate("NextLevel", 10, 10);
        Button level = nextLevelButton.getButton();
        level.setOnMouseClicked(e -> getMyGameEngine().pause());
        getRoot().getChildren().add(level);
    }
}
