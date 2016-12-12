package gameeditor.view;

import java.io.File;
import java.util.HashMap;
import frontend.util.FileOpener;
import frontend.util.GameEditorException;
import gameeditor.controller.GameEditorData;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.rpg.GridDesignArea;
import gameeditor.rpg.IGridDesignArea;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import gameeditor.view.interfaces.IEditorToolbar;
import gameeditor.view.interfaces.IGameEditorView;
import gameeditor.view.interfaces.IToolbarParent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.GameObject;

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
    private IGridDesignArea myGridDesignArea;
    private IGameEditorData myDataStoreInterface;
    private IDetailPane myDetailPane;
    private ILevel myLevelSettings;
    private BooleanProperty closeLevelWindow;
    public static final String DEFAULT_MAIN_CHARACTER = "bird2.gif";
    public static final String SCORE_PROPERTY="score";
    private String myGameType;


    public GameEditorView(ILevel levelSettings, String gameType){
        this.myLevelSettings = levelSettings;
        myGameType = gameType;
        myRoot = new BorderPane();  
        closeLevelWindow = new SimpleBooleanProperty(false);
    }

    public Parent createRoot(){
        myRoot.setCenter(createCenter());
        myRoot.setLeft(createLeftAlt());
        try{
            addBackground();
            addAvatar();
           // addSprites();
        }catch(NullPointerException e){
            GameEditorException exception = new GameEditorException();
            exception.showError(e.getMessage());
        }
        return myRoot;
    }

    
    private void addSprites(){
        if(myLevelSettings.getGameObjects().size()>0){
            for(GameObject object: myLevelSettings.getGameObjects()){
                double height = object.getHeight();
                double width = object.getWidth();
                String fileName = object.getImageFileName();
                Image image = new Image(getClass().getClassLoader().getResourceAsStream("Sprite/"+object.getImageFileName()));
                ImageView spriteimageView = new ImageView(image); 
                double xposition = object.getXPosition();
                double ypositon = object.getYPosition();
            }
        }    
    }
    
    private HBox createLeftAlt(){
        myDataStoreInterface = new GameEditorData(myLevelSettings);
        DetailPane dp = new DetailPane(myDesignArea, myDataStoreInterface);
        myDetailPane = dp;
        myCommandPane = new CommandPane(dp);
        myLeftBox = new HBox();
        myLeftBox.getChildren().add(myCommandPane.getPane());
        myLeftBox.getChildren().add(myDetailPane.getPane());
        return myLeftBox;
    }

    private VBox createCenter(){
        myCenterBox = new VBox();
        if (myGameType.equals("Scrolling")){
            myDesignArea = new DesignArea();
        } else if (myGameType.equals("RPG")){
        	myDesignArea = new GridDesignArea();
        }
        myScrollPane = myDesignArea.getScrollPane();
        myToolbar = new EditorToolbar(this);
        myCenterBox.getChildren().add(myToolbar.getPane());
        myCenterBox.getChildren().add(myScrollPane);
        return myCenterBox;
    }

    private void addBackground(){
        if(myLevelSettings.getBackgroundFilePath()!=null){
            String filePath = FILE_PREFIX + getUserDirectory()+ IMAGES_LOCATION + myLevelSettings.getBackgroundFilePath();
            displayBackgroundOnScreen(filePath);
        }
    }

    private void addAvatar(){
        //if(myLevelSettings.getMainCharacter()!=null){
            //if(myLevelSettings.getMainCharacter().getImageFileName()!=null){
                //String filePath = FILE_PREFIX+getUserDirectory()+AVATAR_IMAGE_LOCATION+ File.separator+myLevelSettings.getMainCharacter().getImageFileName();
               // myDetailPane.setAvatar(filePath);
           // }
       // }
    }

    public void setBackground(){
        String filePath = getFilePath(IMAGE_FILE_TYPE,BG_IMAGE_LOCATION);
        displayBackgroundOnScreen(filePath);
    }

    private void displayBackgroundOnScreen(String filePath){
        if(filePath!=null){
            ImageView backgroundImage = new ImageView(new Image(filePath));
            backgroundImage.setFitHeight(ViewResources.SCROLL_PANE_HEIGHT.getDoubleResource());
            backgroundImage.setFitWidth(ViewResources.SCROLL_PANE_WIDTH.getDoubleResource());
            backgroundImage.setId(BACKGROUND_IMAGE_ID);

            myScrollPane.setPrefSize(ViewResources.SCROLL_PANE_WIDTH.getDoubleResource(), ViewResources.SCROLL_PANE_HEIGHT.getDoubleResource()); 
            myDesignArea.setBackground(backgroundImage); 
            
            String file = filePath.substring(filePath.lastIndexOf("/") +1);
            myLevelSettings.setBackgroundImage("Background/" + file);
        }
    }

    public void setAvatar(){
        String filePath = getFilePath(IMAGE_FILE_TYPE, AVATAR_IMAGE_LOCATION);
        if(filePath!=null){
            myDetailPane.setAvatar(filePath);
        }   
    }

    public void setMusic(){
        try {
            String musicFilePath = getFilePath(MUSIC_FILE_TYPE,MUSIC_FILE_LOCATION);
            String file = musicFilePath.substring(musicFilePath.lastIndexOf("/") +1);
            myLevelSettings.setBackgroundMusic(file);

        }catch (NullPointerException e){
            System.out.println("Music was not added");
        }
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
    public void saveLevelData () {
        //if(myLevelSettings.getMainCharacter()==null){
            //myDataStoreInterface.addMainCharacter(0, 0, IGameEditorData.MAIN_CHAR_WIDTH, IGameEditorData.MAIN_CHAR_HEIGHT,null);
       // }
        // add Game Objects to level
        myDataStoreInterface.addGameObjectsToLevel();
        myDataStoreInterface.storeMainCharToXML();
        closeLevelWindow.set(true);
    }


    public BooleanProperty getSaveLevelProperty(){
        return this.closeLevelWindow;
    }

    public void setSaveProperty(Boolean bool){
        closeLevelWindow.set(bool);
    }

    private String getUserDirectory(){
        return System.getProperty("user.dir") + "/" ;
    }
}
