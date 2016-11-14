package gameEditorView;

import java.net.MalformedURLException;
import buttons.ButtonTemplate;
import buttons.IGameEditorView;
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



public class GameEditorView implements IGameEditorView {
    private BorderPane myRoot;
    private ScrollPane myScrollPane;

    public GameEditorView(){
        myRoot = new BorderPane();    
    }

    public Parent createRoot(){
        myRoot.setLeft(createScrollBar());
        myRoot.setRight(createRight());
        myRoot.setPadding(new Insets(20));
        myRoot.getChildren().add(new Button(" HI"));
        return myRoot;
    }


    private ScrollPane createScrollBar(){
        myScrollPane = new ScrollPane();

        myScrollPane.setPrefWidth(0.75*SCENE_WIDTH);
        myScrollPane.setPrefHeight(SCENE_HEIGHT);

        myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

        return myScrollPane;
    }


    private Button makeButton(String property, EventHandler<ActionEvent>  handler){
        ButtonTemplate button = new ButtonTemplate(property);
        button.getButton().setOnAction(handler);
        return button.getButton();
    }


    private VBox createRight(){
        HBox buttonHouser = new HBox();
        VBox myControls = new VBox();
        buttonHouser.getChildren().addAll(makeButton("SelectBackgroundCommand",event -> setBackgroundImage()),makeButton("SelectPlayerCommand",event -> setPlayerImage()));
        myControls.getChildren().add(buttonHouser);
        return myControls;

    }

    private void setBackgroundImage(){
        HBox myHBox = new HBox();
        FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE,IMAGE_FILE_LOCATION).toURI().toURL().toString();
            //BackgroundSize b = new BackgroundSize(SCENE_WIDTH*4, SCENE_HEIGHT, false, false,false, true);
            //BackgroundImage bg = new BackgroundImage(new Image(filePath), null, null, null, b);
            //myPane.setBackground(new Background(bg));

            ImageView backgroundImage = new ImageView(new Image(filePath));
            backgroundImage.setFitHeight(SCENE_HEIGHT);
            backgroundImage.setFitWidth(SCENE_WIDTH);
            myScrollPane.setPrefSize(0.75*SCENE_WIDTH, SCENE_HEIGHT);

            // Can add new ImageView depending on Width of the ScrollPane
            myHBox.getChildren().add(new ImageView(new Image(filePath)));
            //myHBox.getChildren().add(backgroundImage);

            myScrollPane.setContent(myHBox);

        } catch (MalformedURLException error) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No File Chosen");
            alert.showAndWait();
        }
    }


    private void setPlayerImage(){

    }



}
