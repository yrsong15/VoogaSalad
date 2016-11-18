package editor.view;

import java.io.File;

public interface IFileOpener {
    public static final String BACKGROUND_IMAGE_FOLDER = ViewResources.BG_FILE_LOCATION.getResource();
    public File chooseFile(String fileType , String fileLocation);
}
