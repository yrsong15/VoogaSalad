/**
 * 
 */
package gameengine.view.onscreenbuttons;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author Noel Moon (nm142)
 *
 */
public class MuteButton extends OnScreenButton {

	public MuteButton(EventHandler<? super MouseEvent> muteEvent) {
		super("Mute", muteEvent);
		setPos(80, 25);
	}
}
