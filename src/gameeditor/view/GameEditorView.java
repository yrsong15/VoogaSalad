package gameeditor.view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sun.javafx.scene.traversal.Direction;
import frontend.util.FileOpener;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import gameeditor.view.interfaces.IEditorToolbar;
import gameeditor.view.interfaces.IGameEditorView;
import gameeditor.view.interfaces.IToolbarParent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.GameObject;
import objects.ScrollType;
import objects.interfaces.ILevel;


/**
 * @author pratikshasharma, John
 */
public class GameEditorView implements IGameEditorView, IToolbarParent {
    private BorderPane myRoot;
    private ScrollPane myScrollPane;
    private HBox myLeftBox;
    private VBox myCenterBox;
    private IEditorToolbar myToolbar;
    private CommandPane myCommandPane;
    private IDesignArea myDesignArea;
    private IDetailPane myDetailPane;
    private ILevel myLevelSettings;
    private BooleanProperty closeLevelWindow;
    
    public static final String DEFAULT_MAIN_CHARACTER = "bird2.gif";
    public static final String SCORE_PROPERTY="score";
    
    

    public GameEditorView(ILevel levelSettings){
       this.myLevelSettings = levelSettings;
        myRoot = new BorderPane();  
        closeLevelWindow = new SimpleBooleanProperty(false);
    }

    public Parent createRoot(){
        myRoot.setCenter(createCenter());
        myRoot.setLeft(createLeftAlt());
        
        addScrollType();
        
        return myRoot;
        
        
    }

    private HBox createLeftAlt(){
        DetailPane dp = new DetailPane(myDesignArea, myLevelSettings);
        myDetailPane = dp;
        myCommandPane = new CommandPane(dp);
        myLeftBox = new HBox();
        myLeftBox.getChildren().add(myCommandPane.getPane());
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


    public void setBackground(){
        HBox myHBox = new HBox();
        String filePath = getFilePath(IMAGE_FILE_TYPE, BG_IMAGE_LOCATION);
        if(filePath!=null){
            ImageView backgroundImage = new ImageView(new Image(filePath));
            backgroundImage.setFitHeight(SCENE_HEIGHT);
            backgroundImage.setFitWidth(SCENE_WIDTH);
            myScrollPane.setPrefSize(0.75*SCENE_WIDTH, SCENE_HEIGHT);      
            myHBox.getChildren().add(backgroundImage);        
            myDesignArea.setBackground(myHBox); 
            
            String file = filePath.substring(filePath.lastIndexOf("/") +1);
            myLevelSettings.addBackgroundImage("Background/" + file);
        } 
    }

    public void setAvatar(){
        String filePath = getFilePath(IMAGE_FILE_TYPE, AVATAR_IMAGE_LOCATION);
        if(filePath!=null){
            //Image newAvatar = new Image(filePath);
            myDetailPane.setAvatar(filePath);
        }   
    }



    public void setMusic(){
        String musicFilePath = getFilePath(MUSIC_FILE_TYPE,MUSIC_FILE_LOCATION);
        String file = musicFilePath.substring(musicFilePath.lastIndexOf("/") +1);
        myLevelSettings.addBackgroundMusic(file);
    }

    private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file =(myFileOpener.chooseFile(fileType, fileLocation));
        if(file !=null){
            return file.toURI().toString();
        }
        return null;
    }

    public Parent getRoot(){
        return this.myRoot;
    }

    @Override
    public void saveLevelData (Map<String,String> myLevelData) {
        myLevelSettings.addWinCondition(SCORE_PROPERTY,myLevelData.get(EditorToolbar.POINTS_PROPERTY));
        myLevelSettings.addLoseCondition(EditorToolbar.TIME_PROPERTY, myLevelData.get(EditorToolbar.TIME_PROPERTY));
        if(myLevelData.containsKey(EditorToolbar.SCROLL_WIDTH_PROPERTY)){
        myLevelSettings.addScrollWidth(Double.parseDouble(myLevelData.get(EditorToolbar.SCROLL_WIDTH_PROPERTY)));
        }
        
         addGround();
        closeLevelWindow.set(true);
    }
       
    private void addGround(){
        GameObject ground = new GameObject(0,600,1000000,200, new HashMap<>());
        ground.setProperty("damage","30");
        myLevelSettings.addGameObject(ground);
    }

    public void addScrollType(){
        createScrollType(ViewResources.FORCED_SCROLLING_TYPE.getResource(),myToolbar.getForcedScrollMenu());
        createScrollType(ViewResources.LIMITED_SCROLLING_TYPE.getResource(),myToolbar.getLimitedScrollingMenu());
        addFreeScrollTypeListener(); 
    }
               
        private void createScrollType(String className, Menu myMenu){
            ScrollType myScrollType = new ScrollType(className);
            myMenu.getItems().stream().forEach(item -> {
                item.setOnAction(e -> {
                    myScrollType.addScrollDirection(Direction.valueOf(item.getText()));
                    myLevelSettings.setScrollType(myScrollType);
                });
            });
        }   
        
        private void addFreeScrollTypeListener(){
            ScrollType myScrollType = new ScrollType(ViewResources.FREE_SCROLLING_TYPE.getResource());
            myToolbar.getFreeScrollTypeMenuItem().setOnAction(e -> {
                myScrollType.addScrollDirection(Direction.LEFT); 
                myScrollType.addScrollDirection(Direction.RIGHT); 
                myScrollType.addScrollDirection(Direction.UP); 
                myScrollType.addScrollDirection(Direction.DOWN); 
            });
        }
        
        public BooleanProperty getSaveLevelProperty(){
            return this.closeLevelWindow;
        }
        
}
