/**
 * 
 */
package gameengine.view;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import objects.Level;

/**
 * @author Noel Moon (nm142)
 *
 */
public class HUD {
	
	private HBox myHUD;
	private Map<String, String> myStatMap;

	public HUD() {
		myHUD = new HBox();
		myStatMap = new TreeMap<String, String>();
	}
	
	public HBox getHUD() {
		return myHUD;
	}
	
	public void update(Level level) {
		myHUD.getChildren().clear();
		if(level.getGameConditions().get("score") != null) {
			Text text = new Text("Score: " + Integer.toString(level.getScore()));
			myHUD.getChildren().add(text);
		}
		if(level.getMainCharacter() != null) {
			Text health = new Text("  |  Health: " + level.getMainCharacter().getProperty("health"));
            myHUD.getChildren().add(health);
        }
	}
	
	public void addStat(String statName, String statValue) {
		myStatMap.put(statName, statValue);
	}
}