package gameengine.view;

import java.io.IOException;

import gameengine.view.onscreenbuttons.ResetButton;
import general.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestButtons extends Application {

    @Override
    public void start (Stage stage) throws IOException {
    	ResetButton rb = new ResetButton(e -> reset());
    	Pane pane = new Pane();
    	pane.getChildren().add(rb.getButton());
    	Scene scene = new Scene(pane, 300, 300);
    	stage.setScene(scene);
    	stage.show();
    }
    
    private void reset() {
    	System.out.println("reset");
    }

    public static void main (String[] args) {
        launch(args);
    }
}
