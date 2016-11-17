package base.gameengine.controller.designinterfaces;

/**
 * The purpose of this class is to parse the XML file being passed from integration and instantiate classes
 * both from the Game Editor and the Game Engine based on the data in the XML.
 */
public interface Parser{

    /**
     * Parses the XML and instantiates specified game objects using reflection
     * @param fileName The name of the XML file
     */
    public void processXML(String fileName);

}
