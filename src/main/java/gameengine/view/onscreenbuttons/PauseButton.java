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
public class PauseButton extends OnScreenButton {

	public PauseButton(EventHandler<? super MouseEvent> event) {
		super("Pause", event);
		setPos(145, 25);
	}
}
