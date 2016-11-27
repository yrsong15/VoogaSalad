package gameeditor.view;

import java.util.ArrayList;
import java.util.List;
import frontend.util.ButtonTemplate;
import general.SplashScreen;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * 
 * @author pratikshasharma
 *
 */

public class EditorLevels {
    public static final double ADD_LEVELS_WIDTH = 400;
    public static final double ADD_LEVELS_HEIGHT=350;
    private VBox myVBox;
    private Button newLevelButton;
    private List<Button> myLevels;
    private SimpleStringProperty myActiveButtonId = new SimpleStringProperty(null);;

    public Parent createRoot(){
        Group root = new Group();
        myVBox = new VBox(20);
        myLevels = new ArrayList<Button>();
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/Background/bg2.jpg";
        ImageView background = new ImageView(new Image(userDirectoryString));
        background.setFitWidth(SplashScreen.SPLASH_WIDTH);
        background.setFitHeight(SplashScreen.SPLASH_HEIGHT);

        root.getChildren().add(background);

        ScrollPane myPane = new ScrollPane();
        
        myPane.setMaxSize(ADD_LEVELS_WIDTH,ADD_LEVELS_HEIGHT);
        myPane.setPrefSize(ADD_LEVELS_WIDTH, ADD_LEVELS_HEIGHT);
        myPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); 
        myPane.setLayoutX(200);
        myPane.setLayoutY(50);
        
        addButton();
        myPane.setContent(myVBox);
        
        root.getChildren().addAll(myPane,newLevelButton);
        return root; 
    }

    private void addButton(){
        ButtonTemplate myButton = new ButtonTemplate("LevelCommand", 350, 450);
        newLevelButton = myButton.getButton();
//        newLevelButton.setTranslateX(400);
//        newLevelButton.setTranslateY(450);

        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/AddLevelIcon.png";
        ImageView newLevelIcon = new ImageView(new Image(userDirectoryString));
        newLevelIcon.setFitHeight(50);
        newLevelIcon.setFitWidth(50);
        newLevelButton.setGraphic(newLevelIcon);
        newLevelButton.setTooltip(new Tooltip("Click Here to add a Level"));
       newLevelButton.setOnAction(e -> addNewLevel());
        myVBox.getChildren().add(newLevelButton);
    }

    public void setOnAddLevel(EventHandler<ActionEvent> handler){
        //newLevelButton.setOnAction(handler);
        newLevelButton.setOnAction(handler);
    }

    public void addNewLevel(){
        Button level = new Button("Level " + (myVBox.getChildren().size()+ 1)) ;
        level.setId(Integer.toString(myVBox.getChildren().size()));
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/gameLevelIcon.png";
        ImageView levelIcon = new ImageView(new Image(userDirectoryString));
        levelIcon.setFitHeight(50);
        levelIcon.setFitWidth(50);
        level.setGraphic(levelIcon);
        myVBox.getChildren().add(level);
        myLevels.add(level);
    }

    public void setOnLevelClicked(EventHandler<MouseEvent> handler){
        for(Button l:myLevels){
            l.setOnAction(e -> updateActiveButtonIdAndHandler(handler,l));
            myActiveButtonId.set(l.getId());
        }   
    }
    
    private void updateActiveButtonIdAndHandler(EventHandler<MouseEvent> handler, Button b){
        myActiveButtonId.set(b.getId());
        b.setOnMouseClicked(handler);
        
    }
    
    public SimpleStringProperty getActiveLevelButtonID(){
        return myActiveButtonId;
    }
}
