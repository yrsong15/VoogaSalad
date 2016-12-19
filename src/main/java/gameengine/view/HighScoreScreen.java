/**
 *
 */
package gameengine.view;
import java.util.List;
import java.util.Map;

import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.ScoreScreen;
import javafx.scene.image.ImageView;

/**
 * @author Noel Moon (nm142)
 *         Delia Li (dl202)
 */
public class HighScoreScreen extends ScoreScreen{

    public HighScoreScreen(List<Integer> highScores, int time, Map<Long,
            Integer> scoreMapping, IGameEngineUI iGameEngine) {
        super(highScores, time, scoreMapping, iGameEngine);
    }
    
    @Override
    public String getStageTitle() {
        return "YOU WIN";
    }
    @Override
    protected String makeScreenText() {
        return "";
    }
    @Override
    protected void addButtons() {
    }
    @Override
    protected ImageView makeBackground() {
        return myFactory.makeBackgroundImage("Victory");
    }
}