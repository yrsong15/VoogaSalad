/**
 * 
 */
package gameengine.view;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 *
 * @citation http://stackoverflow.com/questions/9966136/javafx-periodic-background-task
 */
public class HUD {
	
	private HBox myHUD;
	private Timeline timer;
	private int timeCount;

	public HUD() {
		timeCount = 0;
		timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> incrementTime()));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
		myHUD = new HBox();
	}
	
	public HBox getHUD() {
		return myHUD;
	}
	
	public void update(Level level) {
		myHUD.getChildren().clear();
		if(level.getGameConditions().get("score") != null) {
			Text scoreText = new Text("Score: " + Integer.toString(level.getScore()));
			myHUD.getChildren().add(scoreText);
		}
		if(level.getMainCharacter() != null) {
			Text healthText = new Text("  |  Health: " + level.getMainCharacter().getProperty("health"));
            myHUD.getChildren().add(healthText);
        }
		Text timeText = new Text("  |  Time: " + Integer.toString(timeCount));
		myHUD.getChildren().add(timeText);
	}
	
	public void resetTimer() {
		timer.stop();
		timer.play();
	}
	
	private void incrementTime() {
		timeCount++;
	}
}