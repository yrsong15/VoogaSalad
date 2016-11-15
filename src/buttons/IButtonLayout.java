package buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;



    public interface IButtonLayout {

    public Button getButton();

    public void changeButtonSettings(double x, double y);
    
    public void setOnButtonAction( EventHandler<ActionEvent>  handler);
}
