package gameeditor.controller;
import java.util.HashMap;
import gameeditor.controller.interfaces.IGameEditorFrontEndController;
import gameeditor.controller.interfaces.ILevelManager;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Level;
import objects.interfaces.ILevel;
/**
 * @author pratikshasharma
 *
 */
public class GameEditorFrontEndController implements IGameEditorFrontEndController{
    private EditorLevels myEditorLevels;
    private HashMap<String,GameEditorView> myLevelEditorMap ;
    private String activeButtonId;
    private GameEditorView myGameEditor;
    private Scene myLevelScene;
    private GameEditorBackendController myGameEditorBackEndController;
    private LevelManager myLevelManager;

    
    public Parent startEditor() {
        myLevelManager = new LevelManager();
        myGameEditorBackEndController = new GameEditorBackendController();
        myEditorLevels= new EditorLevels();
        Parent parent = myEditorLevels.createRoot();
        myEditorLevels.setOnAddLevel( e-> addLevelButton());
        return parent;
    }
    private void addLevelButton(){
        myLevelEditorMap = new HashMap<String,GameEditorView>();
        myEditorLevels.addNewLevel();
        addActiveLevelButtonListener();
        myEditorLevels.setOnLevelClicked((e -> displayLevel()));
    }
    private void addActiveLevelButtonListener(){
        myEditorLevels.getActiveLevelButtonID().addListener(new ChangeListener<String>(){
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {
                activeButtonId = newValue;
            }
        });
    }
    
    private void displayLevel(){
        if(myLevelEditorMap.containsKey(activeButtonId)){
            myGameEditor=myLevelEditorMap.get(activeButtonId);
        } else{
            Level level = new Level(Integer.parseInt(activeButtonId));
            ILevel levelInterface = (ILevel) level;
            myLevelManager.createLevel(level,levelInterface);
            //myLevelManager.setCurrentLevel(level);
            myGameEditor = new GameEditorView(levelInterface);
            myLevelEditorMap.put(activeButtonId, myGameEditor);
            displayInitiallyOnSytage();
        }
    }
    private void displayInitiallyOnSytage(){
        
        
        //myGameEditorBackEndController.setCurrentLevel(level);
        //myLevelManager.createLevel(1);
        
        
        Stage myLevelStage = new Stage();
        myLevelScene = new Scene(myGameEditor.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myLevelStage.setScene(myLevelScene);
        myLevelStage.show();
    }
    
    
}