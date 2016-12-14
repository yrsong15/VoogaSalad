/**
 *
 */
package gameengine.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import frontend.util.ButtonTemplate;
import gameengine.controller.GameEngineController;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.ControlInterface;
import gameengine.view.interfaces.IGameEngineUI;
import gameengine.view.interfaces.ScoreScreen;
import general.NodeFactory;
import javafx.scene.Group;
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
import org.w3c.dom.css.Rect;

/**
 * @author Noel Moon (nm142)
 *         Delia Li (dl202)
 */
public class HighScoreScreen extends ScoreScreen{
    public static final double myAppWidth = 400;
    public static final double myAppHeight = 340;
    private NodeFactory myFactory = new NodeFactory();
    private Scene myScene;
    private Level myLevel;
    private List<Integer> highScores;
    private BorderPane root;
    private CommandInterface commandInterface;

    public HighScoreScreen(List<Integer> highScores, int time, Map<Long, Integer> scoreMapping, IGameEngineUI iGameEngine) {
        super(highScores, time, scoreMapping, iGameEngine);
    }

    public Scene getScene() {
        return myScene;
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
