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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author pratikshasharma
 *
 */

public class EditorLevels {
    // TODO Add values to the resources file
    
    public static final double ADD_LEVELS_WIDTH = 400;
    public static final double ADD_LEVELS_HEIGHT=350;
    public static final double LEVEL_PANE_X_POSITION = 180;
    public static final double LEVEL_PANE_Y_POSITION = 70;
    public static final double BUTTON_ICON_PROPORTION = 50;
    public static final String DEFAULT_GAME_TITLE = "Untitled";
    
    private VBox myVBox;
    private Button newLevelButton;
    private List<Button> myLevels;
    private SimpleStringProperty myActiveButtonId;
    private Button submitButton;
    private Button loadGameButton;
    private SimpleStringProperty myGameTitle; 

    public EditorLevels(){
        myActiveButtonId = new SimpleStringProperty(null);
        myGameTitle = new SimpleStringProperty(null);
    }
    
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
        myPane.setLayoutX(LEVEL_PANE_X_POSITION);
        myPane.setLayoutY(LEVEL_PANE_Y_POSITION);
        
        addButton();
        myPane.setContent(myVBox);
        
        root.getChildren().addAll(myPane,newLevelButton,addGameTitle(),loadGameButton);
        return root; 
    }
    
    private HBox addGameTitle(){
        Label gameLabel = new Label("Enter Game Title: ");
        TextField myGameName = new TextField();
        HBox myHBox = new HBox(20);
        myHBox.setLayoutX(LEVEL_PANE_X_POSITION);
        myHBox.setLayoutY(LEVEL_PANE_Y_POSITION/2);
        ButtonTemplate submitButton = new ButtonTemplate("SubmitCommand",0,0);
        submitButton.setOnButtonAction(e-> addGameTitleListener(myGameName));
        addGameTitleListener(myGameName);
        myHBox.getChildren().addAll(gameLabel,myGameName,submitButton.getButton()); 
        return myHBox;
    }

    private void addGameTitleListener(TextField myGameName){
        if(myGameName.getText()!=null && !myGameName.getText().isEmpty()){
            myGameTitle.set(myGameName.getText());
        }
    }
    
    private void addButton(){
        newLevelButton = getButton("LevelCommand", LEVEL_PANE_X_POSITION, LEVEL_PANE_Y_POSITION*6.5);
        loadGameButton = getButton("LoadGameCommand",LEVEL_PANE_X_POSITION*2,LEVEL_PANE_Y_POSITION*6.5);
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/AddLevelIcon.png";
        ImageView newLevelIcon = new ImageView(new Image(userDirectoryString));
        
        newLevelIcon.setFitHeight(BUTTON_ICON_PROPORTION);
        newLevelIcon.setFitWidth(BUTTON_ICON_PROPORTION);
        
        newLevelButton.setGraphic(newLevelIcon);
        newLevelButton.setTooltip(new Tooltip("Click Here to add a Level"));
        newLevelButton.setOnAction(e -> addNewLevel());
    }

    public void setOnAddLevel(EventHandler<ActionEvent> handler){
        newLevelButton.setOnAction(handler);
    }

    public void addNewLevel(){
        Button level = new Button("Level " + (myVBox.getChildren().size() + 1)) ;
        level.setId(Integer.toString(myVBox.getChildren().size()));
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/gameLevelIcon.png";
        ImageView levelIcon = new ImageView(new Image(userDirectoryString));
        levelIcon.setFitHeight(BUTTON_ICON_PROPORTION);
        levelIcon.setFitWidth(BUTTON_ICON_PROPORTION);
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
    
    private Button getButton(String property, double xposition, double yposition){
        ButtonTemplate myButton = new ButtonTemplate(property, xposition, yposition);
        return myButton.getButton();
    }
    private void updateActiveButtonIdAndHandler(EventHandler<MouseEvent> handler, Button b){
        myActiveButtonId.set(b.getId());
        b.setOnMouseClicked(handler);   
    }
    
    public SimpleStringProperty getActiveLevelButtonID(){
        return myActiveButtonId;
    }
    
    public SimpleStringProperty getGameTitle(){
        return myGameTitle;
    }
    
    public void setOnLoadGameButton(EventHandler<MouseEvent> handler){
        loadGameButton.setOnMouseClicked(handler);
    }
    
}
