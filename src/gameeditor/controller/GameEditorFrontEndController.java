package gameeditor.controller;
import java.util.HashMap;
import gameeditor.controller.interfaces.IGameEditorFrontEndController;
import gameeditor.view.EditorLevels;
import gameeditor.view.GameEditorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

    
    
    public Parent startEditor() {
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
            myGameEditor = new GameEditorView();
            myLevelEditorMap.put(activeButtonId, myGameEditor);
            displayInitiallyOnSytage();
        }
    }
    private void displayInitiallyOnSytage(){
        Stage myLevelStage = new Stage();
        myLevelScene = new Scene(myGameEditor.createRoot(), GameEditorView.SCENE_WIDTH, GameEditorView.SCENE_HEIGHT);
        myLevelStage.setScene(myLevelScene);
        myLevelStage.show();
    }
}