package general;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Main class for VOOGASalad. Basic boilerplate code for launching the JavaFX
 * application.
 */
public class Main extends Application {
    private MainController controller;

    @Override
    public void start (Stage stage) throws IOException {

        System.setProperty("glass.accessible.force", "false");
        controller = new MainController(stage);
    }

    public static void main (String[] args) {
        launch(args);
    }
}