/**
 * 
 */
package gameengine.view.onscreenbuttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author Noel Moon (nm142)
 *
 */
public class ResetButton extends OnScreenButton {

	public ResetButton(EventHandler<? super MouseEvent> resetEvent) {
		super("Reset", resetEvent);
		setPos(8, 25);
	}
}
