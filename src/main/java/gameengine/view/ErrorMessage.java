package gameengine.view;

import java.awt.Dimension;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

/**
 * @author Noel Moon (nm142)
 *
 * Citations: http://code.makery.ch/blog/javafx-dialogs-official/
 *
 */
public class ErrorMessage {
	
//	public static final Dimension DEFAULT_SIZE = new Dimension(1000, 700);
	private Alert myAlert;
	
	public ErrorMessage() {
		myAlert = new Alert(AlertType.INFORMATION);
		myAlert.setTitle("Error");
		myAlert.setHeaderText(null);
	}

	public void showError(String errorMessage) {
		myAlert.setContentText(errorMessage);
		myAlert.showAndWait();
	}
}
