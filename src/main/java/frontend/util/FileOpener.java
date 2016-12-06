package frontend.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import gameeditor.view.IEditorLevels;
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
    private FileChooser fileChooser;


    public File chooseFile(String fileType , String fileLocation) {
        setUpFileChooser(fileType,fileLocation);
        File chosenFile = fileChooser.showOpenDialog(myStage);
        return chosenFile;
    }

    public void addFileExtensionFilter(String fileType, FileChooser myFileChooser){
        if(fileType.equals(IGameEditorView.IMAGE_FILE_TYPE)){
            myFileChooser.getExtensionFilters().addAll(
                                                       new FileChooser.ExtensionFilter("All Images", "*.*"),
                                                       new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                                       new FileChooser.ExtensionFilter("PNG", "*.png"));

        } else if (fileType.equals(IGameEditorView.MUSIC_FILE_TYPE)){
            myFileChooser.getExtensionFilters().addAll(
                                                       new FileChooser.ExtensionFilter("Music Files", "*.*"), 
                                                       new FileChooser.ExtensionFilter("MP3", "*.mp3"));

        }else if(fileType.equals(IEditorLevels.XML_FILE_TYPE)){
            myFileChooser.getExtensionFilters().addAll(
                                                       new FileChooser.ExtensionFilter("XML", "*.xml"));
        }else{
                // do nothing
            }
}

    private void setUpFileChooser(String fileType, String fileLocation){
        myStage = new Stage();
        fileChooser = new FileChooser();
        addFileExtensionFilter(fileType,fileChooser);
        String userDirectoryString = System.getProperty("user.dir") + "/" + fileLocation;
        File userDirectory = new File(userDirectoryString);
        fileChooser.setInitialDirectory(userDirectory);
    }


    @Override
    public void saveFile (String fileType, String fileLocation, String data, String defaultFileName) {
        setUpFileChooser( fileType, fileLocation);
        fileChooser.setInitialFileName(defaultFileName);
        File file = fileChooser.showSaveDialog(myStage);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            GameEditorException exception = new GameEditorException();
            exception.showError("IOException");
            
        } catch (NullPointerException e){
            GameEditorException exception = new GameEditorException();
            exception.showError("File has not been saved");
        }
    }
}
