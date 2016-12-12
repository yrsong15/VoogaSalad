package gameengine.view;

import gameengine.controller.interfaces.CommandInterface;
import gameengine.view.interfaces.ScoreScreen;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import objects.Level;

import java.util.List;

/**
 * Created by Delia on 12/11/2016.
 */
public class LevelScreen extends ScoreScreen {
    public LevelScreen(Level level, List<Integer> highScores, CommandInterface commandInterface) {
        super(level, highScores, commandInterface);
    }

    @Override
    protected ImageView makeBackground() {
        return null;
    }

//    @Override
//    public Scene getScene() {
//        return null;
//    }
}
