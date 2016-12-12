package general;

import com.sun.javafx.scene.traversal.Direction;
import gameengine.model.RandomGenFrame;
import gameengine.model.RandomGenFrameY;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.NoBoundary;
import javafx.scene.input.KeyCode;
import objects.*;
import xml.XMLSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Soravit on 12/11/2016.
 */
public class GameExamples{

    public String getDoodleJumpXML(){
        Game game = new Game("Doodle Jump");
        GameObject shyGuy = new GameObject(400, 500, 100, 100, "shyguy.png", new HashMap<>());
        Player player1 = new Player(shyGuy);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        shyGuy.setProperty("jumpunlimited", "800");
        shyGuy.setProperty("gravity", "0.8");
        shyGuy.setProperty("movespeed", "10");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("LimitedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player1.setControl(KeyCode.UP, "jump");
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        level.addPlayer(shyGuy);
        GameObject ground = new GameObject(0, 570,700,50,"platform.png", new HashMap<>());
        ground.setProperty("nonintersectable", "bottom");
        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "1000");
        RandomGeneration platforms = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platforms2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platforms3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> randomGen = new ArrayList<>();
        randomGen.add(platforms);
        randomGen.add(platforms2);
        randomGen.add(platforms3);
        RandomGenFrame frame = new RandomGenFrameY(level, randomGen);
        level.setRandomGenerationFrame(frame);
        level.addGameObject(ground);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }
}
