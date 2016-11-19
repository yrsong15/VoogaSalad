package gameeditor.xmlcreator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import objects.Game;

/**
 * Created by Soravit on 11/18/2016.
 */
public class XMLSerializer {

    private XStream mySerializer = new XStream(new DomDriver());

    public String serializeGame(Game game){
        return mySerializer.toXML(game);
    }
}
