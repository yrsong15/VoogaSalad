package buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;



public interface IButtonLayout {
    public static final String PROPERTIES_RESOURCE_PACKAGE = "resources.properties";
    public static final String BUTTON_LABEL_FILE = "Button";

    public Button getButton();

    public void changeButtonSettings(double x, double y);

    public void setOnButtonAction( EventHandler<ActionEvent>  handler);
}
