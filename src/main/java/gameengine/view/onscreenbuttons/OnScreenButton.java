/**
 * 
 */
package gameengine.view.onscreenbuttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Noel Moon (nm142)
 *
 */
public abstract class OnScreenButton {
	
	private Text text;

	public OnScreenButton(String name, EventHandler<? super MouseEvent> event) {
		text = new Text(10, 30, name);
		text.setFont(Font.font(30));
		text.setStroke(Color.WHITE);
		text.setOnMouseClicked(event);
	}
	
	public Text getButton() {
		return text;
	}
}
