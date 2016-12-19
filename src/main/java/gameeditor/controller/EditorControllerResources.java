
package gameeditor.controller;
/**
 * @author pratikshasharma
 *
 */

public enum EditorControllerResources{
    DEFAULT_GAME_TITLE ("UNTITLED"),
    DEFAULT_GAME_TYPE("Scrolling"),
    XML_DATA_TYPE("XML"),
    DEFAULT_SAVING_NAME("vooga"),
    DEFAULT_SAVE_FOLDER("data"),
    GAME_EDITOR_TITLE("Game Editor");
    ;

    private String resourceString;
    private double resourceDouble;


    EditorControllerResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
    }

    public String getResource() {
        return resourceString;
    }

    public double getDoubleResource() {

        return resourceDouble;
    }


}
