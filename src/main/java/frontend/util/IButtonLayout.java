package frontend.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * 
 * @author pratikshasharma
 *
 */

public interface IButtonLayout {
    public static final String BUTTON_LABEL_FILE = "Button";

    public Button getButton();

    public void changeButtonSettings(double x, double y);

    public void setOnButtonAction( EventHandler<ActionEvent>  handler);
}
