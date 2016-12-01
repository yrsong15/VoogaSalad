/**
 * 
 */
package gameengine.view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Noel Moon (nm142)
 *
 */
public class HUD {
	
	private HBox myHUD;
	private Map<String, String> myStatMap;

	public HUD() {
		myHUD = new HBox();
		myStatMap = new HashMap<String, String>();
		
		addStat("Score", "0");
		updateStats();
	}
	
	public HBox getHUD() {
		return myHUD;
	}
	
	public void updateStats() {
		myHUD.getChildren().clear();
		for (String stat : myStatMap.keySet()) {
			Label label = new Label(stat + ":  " + myStatMap.get(stat));
			label.minWidth(40);
			myHUD.getChildren().add(label);
		}
	}
	
	public void addStat(String statName, String statValue) {
		myStatMap.put(statName, statValue);
	}
}
