package buttons;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import javax.print.DocFlavor;


public class ButtonTemplate implements IButtonLayout{
    private Button myButton;
    private ResourceBundle myResources;
    public static final String RESOURCE_PACKAGE = "resources.properties";
    public static final String BUTTON_LABEL_FILE = "Button";
    private static final String BUTTON_DEFAULT_STYLE = "ButtonDefaultStyle";
    private static final String BUTTON_MOUSE_ENTERED_STYLE = "ButtonMouseEnteredStyle";

    /**
     * @param property
     * Creates a button based on the String property
     */
    public ButtonTemplate(String property){
        myResources = ResourceBundle.getBundle(RESOURCE_PACKAGE + File.separator + BUTTON_LABEL_FILE );
        String label = myResources.getString(property);
        myButton = new Button(label);
        String buttonFill = myResources.getString(BUTTON_DEFAULT_STYLE);
        String overButton = myResources.getString(BUTTON_MOUSE_ENTERED_STYLE);
        myButton.setStyle(buttonFill);
        myButton.setOnMouseEntered(e -> myButton.setStyle(overButton));
        myButton.setOnMouseExited(ee -> myButton.setStyle(buttonFill));
    }

    /**
     * @return Button
     */
    public Button getButton(){
        return this.myButton;
    }
    
    /**
     * Changes layout positions of the button
     */
    public void changeButtonSettings(double xPosition, double yPosition){
        myButton.setLayoutX(xPosition);
        myButton.setLayoutY(yPosition);
        myButton.setFont(Font.font("Comic Sans",14));
    }
    

    @Override
    public void setOnButtonAction (EventHandler<ActionEvent> handler) {
       myButton.setOnAction(handler);
        
    }
}

