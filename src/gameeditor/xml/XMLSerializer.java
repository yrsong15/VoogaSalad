package gameeditor.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import objects.Game;


/**
 * This class is to be called when the Game Editor sends all of its information to the Game Engine.
 * @author Ray Song(ys101)
 */
public class XMLSerializer{

    private XStream mySerializer = new XStream(new DomDriver());

    public String serializeGame(Game game){
        return mySerializer.toXML(game);
    }

}
