package gameeditor.view;

import java.net.MalformedURLException;
import frontend.util.FileOpener;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import gameeditor.view.interfaces.IEditorToolbar;
import gameeditor.view.interfaces.IGameEditorView;
import gameeditor.view.interfaces.IToolbarParent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * 
 * @author pratikshasharma, John
 *
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


//    private Button makeButton(String property, EventHandler<ActionEvent>  handler){
//        ButtonTemplate button = new ButtonTemplate(property);
//        button.getButton().setOnAction(handler);
//        return button.getButton();
//    }
    
    public void setBackground(){
        HBox myHBox = new HBox();
        FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE, BG_IMAGE_LOCATION).toURI().toURL().toString();

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


    public void setAvatar(){
    	FileOpener myFileOpener = new FileOpener();
        try {
            String filePath = myFileOpener.chooseFile(IMAGE_FILE_TYPE, AVATAR_IMAGE_LOCATION).toURI().toURL().toString();
            Image newAvatar = new Image(filePath);
            myDetailPane.setAvatar(newAvatar);
        } catch (MalformedURLException error) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("No File Chosen");
            alert.showAndWait();
        }
    }

        
    @Override
    public void sendDataToGameEngine () {
       // Call in the XMlSerializer to send the Xml file 
        System.out.println(" Send Data " );
        
    }
    
    public void setMusic(){
        FileOpener myFileOpener = new FileOpener();
//        try{
//            String musicFilePath = new FileOpener.chooseFile(MUSIC_FILE_TYPE,MUSIC_FILE_LOCATION).toURI().toString();
//            
//        }
    }
    

}
