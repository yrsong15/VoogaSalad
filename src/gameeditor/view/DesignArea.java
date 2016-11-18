package gameeditor.view;

import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DesignArea implements IDesignArea {
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private Pane myPane;
	private ScrollPane myScrollPane;
	
	private ImageView myAvatar;

	public DesignArea() {
        myScrollPane = new ScrollPane();

        myScrollPane.setMinWidth(AREA_WIDTH);
        myScrollPane.setMinHeight(AREA_HEIGHT);
        myScrollPane.setMaxWidth(AREA_WIDTH);
        myScrollPane.setMaxHeight(AREA_HEIGHT);

        myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setVmax(0);
		
		myScrollPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void setBackgroundImage(){
        HBox myHBox = new HBox();
        FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE,IMAGE_FILE_LOCATION).toURI().toURL().toString();
            ImageView backgroundImage = new ImageView(new Image(filePath));
            backgroundImage.setFitHeight(SCENE_HEIGHT);
            backgroundImage.setFitWidth(SCENE_WIDTH);
            myScrollPane.setPrefSize(0.75*SCENE_WIDTH, SCENE_HEIGHT);
            
            myHBox.getChildren().add(backgroundImage);

            myScrollPane.setContent(myHBox);

        } catch (MalformedURLException error) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No File Chosen");
            alert.showAndWait();
        }
    }
	
	public ScrollPane getScrollPane(){
		return myScrollPane;
	}
	
	public void updateAvatar(Image newAvatar){
		
	}

}
