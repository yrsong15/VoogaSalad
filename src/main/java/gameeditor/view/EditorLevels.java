package gameeditor.view;

import java.util.ArrayList;
import java.util.List;
import frontend.util.ButtonTemplate;
import general.NodeFactory;
import general.SplashScreen;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import side.Side;
import viewformatter.ViewFormatter;

/**
 * @author pratikshasharma
 */

public class EditorLevels implements IEditorLevels{
    // TODO Add values to the resources file

    private VBox myVBox;
    private Button newLevelButton;
    private List<Button> myLevels;
    private SimpleStringProperty myActiveButtonId;
    private Button loadGameButton;
    private SimpleStringProperty myGameTitle;
    private NodeFactory myFactory;
    private Group root;
    private Button saveGameButton;
    
    private Button testEditButton;

    public EditorLevels(){
        myActiveButtonId = new SimpleStringProperty(null);
        myGameTitle = new SimpleStringProperty(null);
        this.myFactory = new NodeFactory();
    }

    public Parent createRoot(String gameName){
    	
    	ViewFormatter formatter = new ViewFormatter();
    	
    	ImageView background = myFactory.makeBackgroundImage("Space");
        background.setFitWidth(SplashScreen.SPLASH_WIDTH);
        background.setFitHeight(SplashScreen.SPLASH_HEIGHT);
        
        formatter.addView(background, "Background", SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_WIDTH)
        		.setZ(-1);	
        
        root = new Group();
        myVBox = new VBox(20);
        myLevels = new ArrayList<Button>();
        
        ScrollPane myPane = createLevelView();
        formatter.addView(myPane, "LevelView", ADD_LEVELS_WIDTH, ADD_LEVELS_HEIGHT)
        		.setX(100)
        		.centerYInScreen(); 
    
     // addButtons();
        newLevelButton = createNewLevelButton();
        formatter.addView(newLevelButton, "NewLevelButton", 150, 50)
        		.position(Side.BOTTOM, "LevelView", 10)
        		.setWidth(.4, "LevelView")
        		.setZ(2);
        		//.setPosition(LEVEL_PANE_X_POSITION, LEVEL_PANE_Y_POSITION*6);
        
        loadGameButton = new Button("LOAD GAME");//getButton("LoadGameCommand",LEVEL_PANE_X_POSITION*2.5,LEVEL_PANE_Y_POSITION*6);
        formatter.addView(loadGameButton, "LoadGameButton", 125, 50)
        		.position(Side.BOTTOM, "LevelView", 10)
        		.setXAsFractionOfWidthFromFarSide(0, "LevelView");
        
        saveGameButton = new Button(SAVE_LABEL);
        formatter.addView(saveGameButton, "SaveGameButton", 70, 50)
        		.position(Side.LEFT, "LoadGameButton", 10)
        		//.setXAsFractionOfWidthFromFarSide(0, "LevelView")
        		.setZ(1);
        
        Region title = createTitle(gameName);
        formatter.addView(title, "Title", 0, 30)
        	.position(Side.TOP, "LevelView", 10)
        	.setWidth(.8,"LevelView");
       // root.getChildren().addAll(myPane,newLevelButton,addGameTitle(gameName),loadGameButton,saveGameButton);
        return formatter.renderView(SplashScreen.SPLASH_WIDTH, SplashScreen.SPLASH_HEIGHT); 
    }
    
    private Button createNewLevelButton()
    {
    	Button button = new Button("New Level");
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/AddLevelIcon.png";
        ImageView newLevelIcon = new ImageView(new Image(userDirectoryString));
        newLevelIcon.setFitHeight(BUTTON_ICON_PROPORTION);
        newLevelIcon.setFitWidth(BUTTON_ICON_PROPORTION);
        button.setGraphic(newLevelIcon); 
        button.setOnAction(e -> addNewLevel()); 
        return button;
    }

    private Region createTitle(String gameName)
    {
    	Label gameLabel = new Label("Game Title: ");
        TextField myGameName = new TextField(gameName);
        myGameName.setOnMouseExited(e->addGameTitleListener(myGameName));
        HBox myHBox = new HBox(40);
        myHBox.setLayoutX(LEVEL_PANE_X_POSITION);
        myHBox.setLayoutY(LEVEL_PANE_Y_POSITION/2);
        myHBox.getChildren().addAll(gameLabel,myGameName); 
        return myHBox;
    }
    private void addGameTitleListener(TextField myGameName){
        if(myGameName.getText()!=null && !myGameName.getText().isEmpty()){
            myGameTitle.set(myGameName.getText());
        }
    }
    
    private ScrollPane createLevelView()
    {
    	ScrollPane pane = new ScrollPane();
        pane.setOpacity(0.5);
        pane.setOnMouseEntered(e -> pane.setOpacity(0.8));
        pane.setOnMouseExited(e -> pane.setOpacity(0.5));
        pane.setMaxSize(ADD_LEVELS_WIDTH,ADD_LEVELS_HEIGHT);
        pane.setPrefSize(ADD_LEVELS_WIDTH, ADD_LEVELS_HEIGHT);
        pane.setContent(myVBox);
        return pane;
    }
    public void setOnAddLevel(EventHandler<ActionEvent> handler){
        newLevelButton.setOnAction(handler);
    }

    public void addNewLevel(){
        Button level = new Button(Integer.toString(myVBox.getChildren().size() + 1)) ;
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

    public Button getLoadButton(){
        return this.loadGameButton;
    }  

    public Parent getRoot(){
        return this.root;
    }

    public void setOnSaveGame(EventHandler<MouseEvent> handler){
        saveGameButton.setOnMouseClicked(handler);
    }
}
