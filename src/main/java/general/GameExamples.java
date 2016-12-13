package general;

import com.sun.javafx.scene.traversal.Direction;
import gameengine.model.RandomGenFrame;
import gameengine.model.RandomGenFrameY;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.NoBoundary;
import gameengine.model.boundary.ToroidalBoundary;
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

    public static int score = 0;

    public String getDoodleJumpXML(){
        Game game = new Game("Doodle Jump");
        GameObject shyGuy = new GameObject(400, 500, 100, 100, "shyguy.png", new HashMap<>());
        
        Player player1 = new Player(shyGuy);
        
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        shyGuy.setProperty("jumpunlimited", "800");
        shyGuy.setProperty("gravity", "1.0");
        shyGuy.setProperty("movespeed", "20");
        shyGuy.setProperty("health", "30");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new ToroidalBoundary(700, 675);
        ScrollType scrollType = new ScrollType("LimitedScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player1.setControl(KeyCode.UP, "jump");
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        player1.setControl(KeyCode.SPACE, "shoot");
        
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
        RandomGenFrame frame = new RandomGenFrameY(level, randomGen, true);
        level.setRandomGenerationFrame(frame);
        level.addGameObject(ground);
        ProjectileProperties projectileProperties = new ProjectileProperties("doodler.png", 30, 30, Direction.UP, 400, 500, 30, 3);
        
        shyGuy.setProjectileProperties(projectileProperties);
        
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }

    public String getDanceDanceRevolution(){
        Game game = new Game("Dance Dance Revolution");
        GameObject one = new GameObject(10, 550, 10, 10, "emptyimage.png", new HashMap<>());
        GameObject two = new GameObject(180, 550, 10, 10, "emptyimage.png", new HashMap<>());
        GameObject three = new GameObject(350, 550, 10, 10, "emptyimage.png", new HashMap<>());
        GameObject four = new GameObject(520, 550, 10, 10, "emptyimage.png", new HashMap<>());

        ProjectileProperties projectileProperties = new ProjectileProperties("emptyimage.png", 30, 30, Direction.RIGHT, 100, 30, 0, 1);
        one.setProjectileProperties(projectileProperties);
        two.setProjectileProperties(projectileProperties);
        three.setProjectileProperties(projectileProperties);
        four.setProjectileProperties(projectileProperties);
        one.setProperty("movespeed","0");
        two.setProperty("movespeed","0");
        three.setProperty("movespeed","0");
        four.setProperty("movespeed","0");

        Player player1 = new Player(one);
        Player player2 = new Player(two);
        Player player3 = new Player(three);
        Player player4 = new Player(four);
        player1.setControl(KeyCode.A, "shoot");
        player2.setControl(KeyCode.S, "shoot");
        player3.setControl(KeyCode.D, "shoot");
        player4.setControl(KeyCode.F, "shoot");

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        game.addPlayerToClient(0, player1);
        game.addPlayerToClient(0, player2);
        game.addPlayerToClient(0, player3);
        game.addPlayerToClient(0, player4);

        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("ForcedScrolling", gameBoundaries);
        scrollType.setScrollSpeed(10);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/giphy2.gif");
        game.setCurrentLevel(level);
        level.addPlayer(one);
        level.addPlayer(two);
        level.addPlayer(three);
        level.addPlayer(four);

        HashMap<String,String> DDRArrowProperties = new HashMap<String,String>();
        DDRArrowProperties.put("points", "50");
        RandomGeneration arrow1 = new RandomGeneration(DDRArrowProperties,150,150,"ddrleftarrow.png",2, 20,20,1234,1234,700,800);
        RandomGeneration arrow2 = new RandomGeneration(DDRArrowProperties,150,150,"ddrdownarrow.png",2, 190 ,190,1234,1234,500,520);
        RandomGeneration arrow3 = new RandomGeneration(DDRArrowProperties,150,150,"ddruparrow.png",2, 360,360,1234,1234,300,600);
        RandomGeneration arrow4 = new RandomGeneration(DDRArrowProperties,150,150,"ddrrightarrow.png",2, 530,530,1234,1234,540,1000);
        ArrayList<RandomGeneration> randomGenerations = new ArrayList<RandomGeneration>();
        randomGenerations.add(arrow1);
        randomGenerations.add(arrow2);
        randomGenerations.add(arrow3);
        randomGenerations.add(arrow4);
        RandomGenFrame frame = new RandomGenFrameY(level,randomGenerations, true);
        level.setRandomGenerationFrame(frame);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }
}
