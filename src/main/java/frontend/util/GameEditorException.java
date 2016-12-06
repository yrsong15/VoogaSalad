package frontend.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameEditorException extends RuntimeException{


            public GameEditorException() {
                    super();
            }
            
            public GameEditorException (String message){
                    super(message);
                    
            }
            
        public void showError (String commandName) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Error in input: " + commandName);
            alert.showAndWait();
        }
}
