/**
 * 
 */
package gameengine.view.onscreenbuttons;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Noel Moon (nm142)
 *
 */
public class SaveButton extends OnScreenButton {

	public SaveButton(EventHandler<? super MouseEvent> event) {
		super("Save", event);
		setPos(220, 25);
	}
}
