package gameengine.controller.interfaces;

import java.lang.reflect.InvocationTargetException;

/**
 * The purpose of this class is to parse the XML file being passed from integration and instantiate classes
 * both from the Game Editor and the Game Engine based on the data in the XML.
 */
public interface Parser{

    /**
     * Parses the XML and instantiates specified game objects using reflection
     * @param fileName The name of the XML file
     */
    public void processXML(String fileName) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException;

}
