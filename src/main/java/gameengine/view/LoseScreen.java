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
 * 			Delia Li (dl202)
 *
 */
public class LoseScreen extends ScoreScreen{

	public LoseScreen(List<Integer> highScores, int time, Map<Long,
			Integer> scoreMapping, IGameEngineUI iGameEngine) {
		super(highScores, time, scoreMapping, iGameEngine);
	}

	@Override
	protected ImageView makeBackground() {
		return myFactory.makeBackgroundImage("GameOver");
	}

	@Override
	public String getStageTitle(){
		return "GAME OVER";
	}

	@Override
	protected void addButtons() {
		ButtonTemplate nextLevelButton = new ButtonTemplate("Replay", myAppWidth / 2, myAppHeight - 50);
		Button level = nextLevelButton.getButton();
		level.setOnMouseClicked(e -> getMyGameEngine().pause());
		getRoot().getChildren().add(level);
	}
}
