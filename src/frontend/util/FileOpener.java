package frontend.util;

import java.io.File;

import gameeditor.view.interfaces.IFileOpener;
import gameeditor.view.interfaces.IGameEditorView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author pratikshasharma
 *
 */
public class FileOpener implements IFileOpener {
    private Stage myStage;


    public File chooseFile(String fileType , String fileLocation) {
        myStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        addFileExtensionFilter(fileType,fileChooser);
        String userDirectoryString = System.getProperty("user.dir") + "/" + fileLocation;
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
        File chosenFile = fileChooser.showOpenDialog(myStage);

        //        if (chosenFile == null) {
        //            Alert alert = new Alert(AlertType.ERROR);
        //            alert.setContentText("No File Chosen");
        //            alert.showAndWait();
        //myStage.close(); 
        //        }
        
        return chosenFile;
    }

    private void addFileExtensionFilter(String fileType, FileChooser myFileChooser){
        if(fileType.equals(IGameEditorView.IMAGE_FILE_TYPE)){
            myFileChooser.getExtensionFilters().addAll(
                                                       new FileChooser.ExtensionFilter("All Images", "*.*"),
                                                       new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                                       new FileChooser.ExtensionFilter("PNG", "*.png"));

        } else if (fileType.equals(IGameEditorView.MUSIC_FILE_TYPE)){
            myFileChooser.getExtensionFilters().addAll(
                                                       new FileChooser.ExtensionFilter("Music Files", "*.*"), 
                                                       new FileChooser.ExtensionFilter("MP3", "*.mp3"));

        }
    }
}
