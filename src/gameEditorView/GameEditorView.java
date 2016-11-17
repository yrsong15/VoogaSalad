package gameEditorView;

import java.net.MalformedURLException;
import buttons.ButtonTemplate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class GameEditorView implements IGameEditorView, IToolbarOutput {
	
    private BorderPane myRoot;
    private ScrollPane myScrollPane;
    private HBox myLeftBox;
    private VBox myCenterBox;
    private IEditorToolbar myToolbar;
    private IDesignArea myDesignArea;
    private IDetailPane myDetailPane;

    public GameEditorView(){
        myRoot = new BorderPane();    
    }

    public Parent createRoot(){
        myRoot.setLeft(createLeftAlt());
        myRoot.setCenter(createCenter());
        return myRoot;
    }
    
    private HBox createLeftAlt(){
    	CommandPane lbp = new CommandPane();
    	myDetailPane = new DetailPane();
    	myLeftBox = new HBox();
    	myLeftBox.getChildren().add(lbp.getPane());
    	myLeftBox.getChildren().add(myDetailPane.getPane());
    	return myLeftBox;
    }
    
    private VBox createCenter(){
    	myCenterBox = new VBox();
    	myDesignArea = new DesignArea();
    	myScrollPane = myDesignArea.getScrollPane();
    	myToolbar = new EditorToolbar(this);
    	myCenterBox.getChildren().add(myToolbar.getPane());
    	myCenterBox.getChildren().add(myScrollPane);
    	return myCenterBox;
    }


    private Button makeButton(String property, EventHandler<ActionEvent>  handler){
        ButtonTemplate button = new ButtonTemplate(property);
        button.getButton().setOnAction(handler);
        return button.getButton();
    }
    
    public void setBackground(){
        HBox myHBox = new HBox();
        FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE,BG_IMAGE_LOCATION).toURI().toURL().toString();
            //BackgroundSize b = new BackgroundSize(SCENE_WIDTH*4, SCENE_HEIGHT, false, false,false, true);
            //BackgroundImage bg = new BackgroundImage(new Image(filePath), null, null, null, b);
            //myPane.setBackground(new Background(bg));

            ImageView backgroundImage = new ImageView(new Image(filePath));
            backgroundImage.setFitHeight(SCENE_HEIGHT);
            backgroundImage.setFitWidth(SCENE_WIDTH);
            myScrollPane.setPrefSize(0.75*SCENE_WIDTH, SCENE_HEIGHT);

            // Can add new ImageView depending on Width of the ScrollPane
            
            myHBox.getChildren().add(backgroundImage);
            //myHBox.getChildren().add(backgroundImage);

            myScrollPane.setContent(myHBox);

        } catch (MalformedURLException error) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No File Chosen");
            alert.showAndWait();
        }
    }


    public void setAvatar(){
    	FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE, AVATAR_IMAGE_LOCATION).toURI().toURL().toString();
            //BackgroundSize b = new BackgroundSize(SCENE_WIDTH*4, SCENE_HEIGHT, false, false,false, true);
            //BackgroundImage bg = new BackgroundImage(new Image(filePath), null, null, null, b);
            //myPane.setBackground(new Background(bg));
            Image newAvatar = new Image(filePath);
            myDetailPane.setAvatar(newAvatar);
        } catch (MalformedURLException error) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No File Chosen");
            alert.showAndWait();
        }
    }

}
