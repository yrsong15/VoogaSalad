package gameeditor.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import gameengine.client.ServerMessage;
import objects.Game;


/**
 * This class is to be called when the Game Editor sends all of its information to the Game Engine
 * as a single Game instance.
 * @author Ray Song(ys101)
 */
public class XMLSerializer{

    private XStream mySerializer = new XStream(new DomDriver());

    public String serializeGame(Game game)
    {
        return mySerializer.toXML(game);
    }

    public Game getGameFromString(String s)
    {
        return (Game)mySerializer.fromXML(s);
    }
    
    public String serializeServerMessage(ServerMessage msg)
    {
        return mySerializer.toXML(msg);
    }

    public ServerMessage getServerMessageFromString(String s)
    {
        return (ServerMessage)mySerializer.fromXML(s);
    }

}
