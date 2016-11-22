package gameeditor.view;

import java.io.File;
import frontend.util.FileOpener;
import gameeditor.controller.interfaces.ILevelManager;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import gameeditor.view.interfaces.IEditorToolbar;
import gameeditor.view.interfaces.IGameEditorView;
import gameeditor.view.interfaces.IToolbarParent;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.interfaces.ISettings;


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
    private ISettings mySettings;
    //private ISettings mySettings;
    private ILevelManager myLevelSettings;
    
    public GameEditorView(){
        myRoot = new BorderPane();    
    }
    
    public Parent createRoot(){
        myRoot.setLeft(createLeftAlt());
        myRoot.setCenter(createCenter());
        return myRoot;
    }

    private HBox createLeftAlt(){
        DetailPane dp = new DetailPane();
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
            myScrollPane.setContent(myHBox); 
            
            //Setting Data For the Settings
            //mySettings.setBackgroundFilePath(filePath);

            //mySettings.setBackgroundFilePath(filePath);
            //myLevelSettings.addBackgroundImage(filePath);
            
        }
    }
 
    public void setAvatar(){
        String filePath = getFilePath(IMAGE_FILE_TYPE, AVATAR_IMAGE_LOCATION);
        if(filePath!=null){
            Image newAvatar = new Image(filePath);
            myDetailPane.setAvatar(newAvatar);
               
        } 
    }

    @Override
    public void sendDataToGameEngine () {
        // Call in the XMlSerializer to send the Xml file 
        System.out.println(" Send Data ");
        
    }

    public void setMusic(){
       String musicFilePath = getFilePath(MUSIC_FILE_TYPE,MUSIC_FILE_LOCATION);
       //mySettings.setMusicFile(musicFilePath);
       //myLevelSettings.addBackgroundMusic(musicFilePath);
    }
    
    private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file =(myFileOpener.chooseFile(fileType, fileLocation));
        if(file !=null){
            return file.toURI().toString();
        }
        return null;
    }
    
}

