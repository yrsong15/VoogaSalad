package gameeditor.view;

import java.util.ArrayList;
import java.util.List;
import frontend.util.ButtonTemplate;
import general.SplashScreen;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/Background/bubbles.jpg";
        ImageView background = new ImageView(new Image(userDirectoryString));
        background.setFitWidth(SplashScreen.SPLASH_WIDTH);
        background.setFitHeight(SplashScreen.SPLASH_HEIGHT);

        root.getChildren().add(background);

        ScrollPane myPane = new ScrollPane();
        myPane.setMaxSize(ADD_LEVELS_WIDTH,ADD_LEVELS_HEIGHT);

        myVBox.setPadding(new Insets(10,20,20,20));

        myPane.setPrefSize(ADD_LEVELS_WIDTH, ADD_LEVELS_HEIGHT);
        myPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); 
        //myVBox.setStyle(" -fx-background-color: blue");
        addButton();
        myPane.setContent(myVBox);
        root.getChildren().addAll(myPane,newLevelButton);
        return root; 
    }

    private void addButton(){
        ButtonTemplate myButton = new ButtonTemplate("LevelCommand");
        newLevelButton = myButton.getButton();
        newLevelButton.setTranslateX(400);
        newLevelButton.setTranslateY(450);

        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/AddLevelIcon.png";
        ImageView newLevelIcon = new ImageView(new Image(userDirectoryString));
        newLevelIcon.setFitHeight(50);
        newLevelIcon.setFitWidth(50);
        newLevelButton.setGraphic(newLevelIcon);

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
        //addLevelListener();
    
    }

    public void setOnLevelClicked(EventHandler<MouseEvent> handler){
        for(Button l:myLevels){
            l.setOnMouseClicked(handler);
            myActiveButtonId.set(l.getId());
            System.out.println("Button Clicked: ");
            System.out.println(" myActiveId : " + myActiveButtonId.get());
        }
        
    }
    
    public SimpleStringProperty getActiveLevelButtonID(){
        return myActiveButtonId;
    }
    
    

}
