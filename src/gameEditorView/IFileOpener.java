package gameEditorView;

import java.io.File;

public interface IFileOpener {
    public static final String BACKGROUND_IMAGE_FOLDER = "images/Background";
    public File chooseFile(String fileType , String fileLocation);
}
