package base.gameengine.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Soravit on 11/17/2016.
 */
public class XMLTest {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        GameEngineController controller = new GameEngineController();
        controller.setCurrentXML("data/GameEditorExampleXML.xml");
        controller.startGame();
    }
}
