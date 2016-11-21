package gameengine.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import objects.Game;
import objects.GameObject;
import objects.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soravit on 11/17/2016.
 */
public class XMLTest {

    static XStream mySerializer = new XStream(new DomDriver());
    static Map<String, String> map = new HashMap<String, String>();

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        Game game = new Game("flappy bird");
        map.put("collidable", "die");
        GameObject go = new GameObject(1, 2, 50, 50, "hi", map);
        go.setProperty("removeobject", "doesn't matter what you put here (remove object doesn't care)");
        go.setProperty("damage", "50");
        Level level = new Level(1);
        level.addGameObject(go);
        level.addWinCondition("score", "10");
        level.addLoseCondition("time", "30");
        game.addLevel(level);
        String s = mySerializer.toXML(game);
        System.out.println(s);
        Game a = (Game) mySerializer.fromXML(s);
    }
}
