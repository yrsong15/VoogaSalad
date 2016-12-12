package frontend.util;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Pratiksha Sharma
 *
 */

public interface IButtonLayout {
    public static final String BUTTON_LABEL_FILE = "Button";

    public Button getButton();

    public void changeButtonSettings(double x, double y);

    public void setOnButtonAction( EventHandler<MouseEvent>  handler);
}
