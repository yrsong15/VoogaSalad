package general;
import com.sun.javafx.scene.traversal.Direction;
import gameengine.model.RandomGenFrame;
import gameengine.model.RandomGenFrameX;
import gameengine.model.RandomGenFrameY;
import gameengine.model.boundary.GameBoundary;
import gameengine.model.boundary.NoBoundary;
import gameengine.model.boundary.StopAtEdgeBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import gameengine.view.GameEngineUI;
import javafx.scene.input.KeyCode;
import objects.*;
import xml.XMLSerializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Soravit on 12/11/2016.
 */
public class GameExamples{
    public String getMarioXML(){
        Game game = new Game("Mario");
        GameObject mario = new GameObject(20, 200 , 50, 75, "mario.png", new HashMap<>());
        Player player1 = new Player(mario);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        mario.setProperty("movespeed", "10");
        mario.setProperty("gravity", "1.2");
        mario.setProperty("jumponce", "650");
        mario.setProperty("health", "30");

        ProjectileProperties projectileProperties = new ProjectileProperties("fireball.png", 40, 40, Direction.RIGHT, 200, 20, 30, 1);
        mario.setProjectileProperties(projectileProperties);
        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675, 3000, 675);
        ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.RIGHT);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        level.setBackgroundMusic("SuperMarioBrosTheme.mp3");
        game.setCurrentLevel(level);
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        player1.setControl(KeyCode.UP, "jump");
        player1.setControl(KeyCode.SPACE, "shoot");
        level.addPlayer(mario);
        GameObject ground = new GameObject(0, 600, 1000, 75, "quarterGrassyGround.png", new HashMap<>());
        level.addGameObject(ground);
        ground.setProperty("nonintersectable", "");

        GameObject pipe = new GameObject(350, 475, 50, 125, "pipes.png", new HashMap<>());
        level.addGameObject(pipe);
        pipe.setProperty("nonintersectable", "");

        GameObject pipe2 = new GameObject(850, 475, 50, 125, "pipes.png", new HashMap<>());
        level.addGameObject(pipe2);
        pipe2.setProperty("nonintersectable", "");

        GameObject coin3 = new GameObject(565, 315, 20, 30, "coinGif.gif", new HashMap<>());
        level.addGameObject(coin3);
        coin3.setProperty("points", "5");
        coin3.setProperty("removeobject", "");

        GameObject coin4 = new GameObject(640, 315, 20, 30, "coinGif.gif", new HashMap<>());
        level.addGameObject(coin4);
        coin4.setProperty("points", "5");
        coin4.setProperty("removeobject", "");



        //first block sequence
        GameObject block = new GameObject(500, 375, 250, 50, "fiveBlocks.png", new HashMap<>());
        level.addGameObject(block);
        block.setProperty("nonintersectable", "");

        level.addGameObject(makeBox(500, 330, 50, 50));
        level.addGameObject(makeBox(700, 330, 50, 50));



        GameObject enemy = new GameObject(300, 525, 50, 75, "goombo.png", new HashMap<>());
//			enemy.setProperty("enemy", "30");
        enemy.setProperty("bounceSpeed", "-1");
        enemy.setProperty("bounceBack", "120");
        enemy.setProperty("bounceTracker", "0");
        enemy.setProperty("removeobject", "");
        level.addGameObject(enemy);


        GameObject ground2 = new GameObject(1200, 600, 500, 75, "quarterGrassyGround.png", new HashMap<>());
        level.addGameObject(ground2);
        ground2.setProperty("nonintersectable", "");

        GameObject bomb = new GameObject(1650, 550, 25, 50, "bomb.png", new HashMap<>());
        bomb.setProperty("enemy", "30");
        level.addGameObject(bomb);


        GameObject pipe3 = new GameObject(1300, 450, 50, 150, "pipes.png", new HashMap<>());
        level.addGameObject(pipe3);
        pipe3.setProperty("nonintersectable", "");

        //first block sequence
        GameObject block2 = new GameObject(1350, 400, 250, 50, "fiveBlocks.png", new HashMap<>());
        level.addGameObject(block2);
        block2.setProperty("nonintersectable", "");

        GameObject coin41 = new GameObject(1420, 550, 20, 30, "coinGif.gif", new HashMap<>());
        level.addGameObject(coin41);
        coin41.setProperty("points", "5");
        coin41.setProperty("removeobject", "");

        GameObject coin5 = new GameObject(1500, 550, 20, 30, "coinGif.gif", new HashMap<>());
        level.addGameObject(coin5);
        coin5.setProperty("points", "5");
        coin5.setProperty("removeobject", "");

        GameObject ground3 = new GameObject(1900, 600, 200, 75, "quarterGrassyGround.png", new HashMap<>());
        level.addGameObject(ground3);
        ground3.setProperty("nonintersectable", "");

        GameObject flag = new GameObject(2000, 375, 150, 225, "flag.png", new HashMap<>());
        level.addGameObject(flag);
        flag.setProperty("levelup", "");




        GameObject killer = new GameObject(-100, GameEngineUI.myAppHeight-30, GameEngineUI.myAppWidth+200,50,"platform.png", new HashMap<>());
        killer.setProperty("damage", "30");
        killer.setProperty("nonscrollable", "");
        killer.setProperty("removeobject", "");
        level.addGameObject(killer);

        Level level2 = new Level(2);
        level2.setScrollType(scrollType);
        game.addLevel(level2);
        level2.addPlayer(mario);
        mario.setXPosition(48);
        mario.setYPosition(236);
        level2.setBackgroundImage("Background/christmas.jpg");
        level2.addGameObject(makeBox(168, 16, 50, 50));
        level2.addGameObject(makeBox(320, 114, 50, 50));
        level2.addGameObject(makeBox(460, 16, 50, 50));
        level2.addGameObject(makeBox(48, 335, 50, 50));
        level2.addGameObject(makeBox(97, 362, 50, 50));
        level2.addGameObject(makeBox(145, 392, 50, 50));

        level2.addGameObject(makeBox(194, 426, 50, 50));
        level2.addGameObject(makeBox(244, 443, 50, 50));
        level2.addGameObject(makeBox(294, 444, 50, 50));
        level2.addGameObject(makeBox(393, 444, 50, 50));
        level2.addGameObject(makeBox(442, 444, 50, 50));
        level2.addGameObject(makeBox(491, 406, 50, 50));

        level2.addGameObject(makeBox(540, 391, 50, 50));
        level2.addGameObject(makeBox(589, 365, 50, 50));
        level2.addGameObject(makeBox(638, 335, 50, 50));





        //second half of level 2
        level2.addGameObject(makeBox(850, 250, 50, 50));
        level2.addGameObject(makeBox(1050, 500, 50, 50));

        level2.addGameObject(makeBox(1250, 600, 50, 50));
        level2.addGameObject(makeBox(1350, 450, 50, 50));
        level2.addGameObject(makeBox(1550, 350, 50, 50));
        level2.addGameObject(makeBox(1750, 300, 50, 50));

        GameObject solid = new GameObject(2000, 400, 500, 50,"fourSolid.png", new HashMap<>());
        solid.setProperty("nonintersectable", "");
        level2.addGameObject(solid);

        GameObject pipeT = new GameObject(2000, 425, 100,300,"candyCane.png", new HashMap<>());
        level2.addGameObject(pipeT);

        GameObject candy = new GameObject(2175, 425, 100,300,"candyCane.png", new HashMap<>());
        level2.addGameObject(candy);

        GameObject candy2 = new GameObject(2350, 425, 100,300,"candyCane.png", new HashMap<>());
        level2.addGameObject(candy2);

        GameObject tree = new GameObject(2600, 400, 100,300,"christmasTree.png", new HashMap<>());
        tree.setProperty("bounce", "1000");
        level2.addGameObject(tree);

        GameObject star= new GameObject(2610, 10, 50, 50,"star.gif-c200", new HashMap<>());
        star.setProperty("levelup", "");
        level2.addGameObject(star);

        GameObject killer2 = new GameObject(-100, GameEngineUI.myAppHeight-30, GameEngineUI.myAppWidth+200,50,"platform.png", new HashMap<>());
        killer2.setProperty("damage", "60");
        killer2.setProperty("nonscrollable", "");
        level2.addGameObject(killer2);



        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "2000");
        DoodleJumpProperties.put("points", "5");
        RandomGeneration platform = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platform2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platform3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> randomGe = new ArrayList<>();
        randomGe.add(platform);
        randomGe.add(platform2);
        randomGe.add(platform3);
        RandomGenFrame fram = new RandomGenFrameY(level, randomGe, true);
        level.setRandomGenerationFrame(fram);
        HashMap<String,String> DoodleJumpProperties2 = new HashMap<>();
        DoodleJumpProperties2.put("bounce", "2000");
        DoodleJumpProperties2.put("points", "5");
        RandomGeneration platforms = new RandomGeneration(DoodleJumpProperties2,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platforms2 = new RandomGeneration(DoodleJumpProperties2,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platforms3 = new RandomGeneration(DoodleJumpProperties2,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> randomGen = new ArrayList<>();
        randomGen.add(platforms);
        randomGen.add(platforms2);
        randomGen.add(platforms3);
        RandomGenFrame frame = new RandomGenFrameY(level2, randomGen, true);
        level2.setRandomGenerationFrame(frame);

        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;

    }

    private GameObject makeBox(double xPos, double yPos, double width, double height){
        GameObject box = new GameObject(xPos, yPos, width, height, "block.png", new HashMap<>());
        box.setProperty("nonintersectable", "");
        return box;
    }



    public String getScrollingXML(){
        Game game = new Game("Scrolling Tester");
        GameObject shyGuy = new GameObject(200, 250, 75, 100, "spicybrian.png", new HashMap<>());
        Player player1 = new Player(shyGuy);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        shyGuy.setProperty("movespeed", "5");
        shyGuy.setProperty("gravity", "1.2");
        shyGuy.setProperty("jumponce", "400");
        Level level = new Level(1);
        GameBoundary gameBoundaries = new ToroidalBoundary(700, 675, 700, 675);
        ScrollType scrollType = new ScrollType("FreeScrolling", gameBoundaries);
        scrollType.addScrollDirection(Direction.RIGHT);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/bg.png");
        game.setCurrentLevel(level);
        player1.setControl(KeyCode.RIGHT, "right");
        player1.setControl(KeyCode.LEFT, "left");
        player1.setControl(KeyCode.UP, "jump");
        level.addPlayer(shyGuy);

        GameObject rock = new GameObject(150, 400, 150, 100,"platform.png", new HashMap<>());
        rock.setProperty("nonintersectable", "");
        level.addGameObject(rock);

        GameObject rock2 = new GameObject(450, 400, 150, 100,"platform.png", new HashMap<>());
        rock2.setProperty("nonintersectable", "");
        level.addGameObject(rock2);


        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "2000");
        DoodleJumpProperties.put("points", "5");
        RandomGeneration platform = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 0,200,1234,1234,400,500);
        RandomGeneration platform2 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 200,500,1234,1234,400,500);
        RandomGeneration platform3 = new RandomGeneration(DoodleJumpProperties,150,40,"platform.png", 2, 500,550,1234,1234,400,500);
        ArrayList<RandomGeneration> randomGe = new ArrayList<>();
        randomGe.add(platform);
        randomGe.add(platform2);
        randomGe.add(platform3);
        RandomGenFrame fram = new RandomGenFrameY(level, randomGe, true);
        level.setRandomGenerationFrame(fram);


        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;

    }
    public String getDoodleJumpXML(){
        Game game = new Game("Doodle Jump");
        GameObject shyGuy = new GameObject(GameEngineUI.myAppWidth/2-100, 10, 100, 100, "shyguy.png", new HashMap<>());
        Player player1 = new Player(shyGuy);
        game.addPlayer(player1);
        game.addPlayerToClient(0, player1);
        shyGuy.setProperty("jumpunlimited", "800");
        shyGuy.setProperty("gravity", "1.0");
        shyGuy.setProperty("movespeed", "20");
        shyGuy.setProperty("health", "30");

        Level level = new Level(1);
        GameBoundary gameBoundaries = new NoBoundary(700, 675, 700, 675);
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

        GameObject ground = new GameObject(0, GameEngineUI.myAppHeight, GameEngineUI.myAppWidth,1,"emptyimage.png", new HashMap<>());
        ground.setProperty("damage", "30");
        ground.setProperty("nonscrollable", "");
        HashMap<String,String> DoodleJumpProperties = new HashMap<>();
        DoodleJumpProperties.put("bounce", "1200");
        DoodleJumpProperties.put("points", "5");
        GameObject mainPlatform = new GameObject(GameEngineUI.myAppWidth/2-100, shyGuy.getYPosition() + 500, 150, 50, "platform.png", new HashMap<>());
        mainPlatform.setProperty("bounce", "1600");
        level.getGameObjects().add(mainPlatform);
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
        //level.addWinCondition("score", "1000");
        ProjectileProperties projectileProperties = new ProjectileProperties("doodler.png", 30, 30, Direction.UP, 400, 500, 30, 1);
        shyGuy.setProjectileProperties(projectileProperties);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }
    public String getDanceDanceRevolution(){
        Game game = new Game("Dance Dance Revolution");
        GameObject one = new GameObject(10, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject two = new GameObject(180, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject three = new GameObject(350, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject four = new GameObject(520, 550, 1, 10, "emptyimage.png", new HashMap<>());
        Map<String, String> properties = new HashMap<>();
        properties.put("nonscrollable", "");
        properties.put("points", "-10");
        GameObject hack = new GameObject(10+5, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack2 = new GameObject(180+5, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack3 = new GameObject(350+5, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack4 = new GameObject(520+5, 550, 50, 10, "emptyimage.png", properties);
        ProjectileProperties projectileProperties = new ProjectileProperties("emptyimage.png", 20, 30, Direction.RIGHT, 100, 50, 0, 0);
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
        level.setBackgroundMusic("Cascada - Everytime We Touch.mp3");
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("ForcedScrolling", gameBoundaries);
        scrollType.setScrollSpeed(20);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/ddrbackground.jpg");
        game.setCurrentLevel(level);
        level.addPlayer(one);
        level.addPlayer(two);
        level.addPlayer(three);
        level.addPlayer(four);
        level.getGameObjects().add(hack);
        level.getGameObjects().add(hack2);
        level.getGameObjects().add(hack3);
        level.getGameObjects().add(hack4);
        HashMap<String,String> DDRArrowProperties = new HashMap<String,String>();
        DDRArrowProperties.put("points", "20");
        DDRArrowProperties.put("removeobject", "");
        RandomGeneration arrow1 = new RandomGeneration(DDRArrowProperties,150,150,"ddrleftarrow.png",2, 20,20,1234,1234,700,800);
        RandomGeneration arrow2 = new RandomGeneration(DDRArrowProperties,150,150,"ddrdownarrow.png",2, 190 ,190,1234,1234,500,520);
        RandomGeneration arrow3 = new RandomGeneration(DDRArrowProperties,150,150,"ddruparrow.png",2, 360,360,1234,1234,300,600);
        RandomGeneration arrow4 = new RandomGeneration(DDRArrowProperties,150,150,"ddrrightarrow.png",2, 530,530,1234,1234,540,1000);
        ArrayList<RandomGeneration> randomGenerations = new ArrayList<RandomGeneration>();
        randomGenerations.add(arrow1);
        randomGenerations.add(arrow2);
        randomGenerations.add(arrow3);
        randomGenerations.add(arrow4);
        RandomGenFrame frame = new RandomGenFrameY(level,randomGenerations, false);
        level.setRandomGenerationFrame(frame);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }
    public String getMultiplayerDDR(){
        Game game = new Game("Dance Dance Revolution");
        game.setMinNumPlayers(2);
        GameObject one = new GameObject(5, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject two = new GameObject(10+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject three = new GameObject(10+85+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject four = new GameObject(10+85+85+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject five = new GameObject(10+85+85+85+100, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject six = new GameObject(10+85+85+85+100+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject seven = new GameObject(10+85+85+85+100+85+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        GameObject eight = new GameObject(10+85+85+85+100+85+85+85, 550, 1, 10, "emptyimage.png", new HashMap<>());
        Map<String, String> properties = new HashMap<>();
        properties.put("nonscrollable", "");
        properties.put("points", "-10");
        GameObject hack = new GameObject(5+11, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack2 = new GameObject(10+85+5, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack3 = new GameObject(10+85+85+11, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack4 = new GameObject(10+85+85+85+11, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack5 = new GameObject(10+85+85+85+100+4, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack6 = new GameObject(10+85+85+85+100+85+5, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack7 = new GameObject(10+85+85+85+100+85+85+11, 550, 50, 10, "emptyimage.png", properties);
        GameObject hack8 = new GameObject(10+85+85+85+100+85+85+85+11, 550, 50, 10, "emptyimage.png", properties);
        ProjectileProperties projectileProperties = new ProjectileProperties("emptyimage.png", 30, 30, Direction.RIGHT, 50, 30, 0, 0.5);
        one.setProjectileProperties(projectileProperties);
        two.setProjectileProperties(projectileProperties);
        three.setProjectileProperties(projectileProperties);
        four.setProjectileProperties(projectileProperties);
        five.setProjectileProperties(projectileProperties);
        six.setProjectileProperties(projectileProperties);
        seven.setProjectileProperties(projectileProperties);
        eight.setProjectileProperties(projectileProperties);
        one.setProperty("movespeed","0");
        two.setProperty("movespeed","0");
        three.setProperty("movespeed","0");
        four.setProperty("movespeed","0");
        five.setProperty("movespeed","0");
        six.setProperty("movespeed","0");
        seven.setProperty("movespeed","0");
        eight.setProperty("movespeed","0");
        Player player1 = new Player(one);
        Player player2 = new Player(two);
        Player player3 = new Player(three);
        Player player4 = new Player(four);
        Player player5 = new Player(five);
        Player player6 = new Player(six);
        Player player7 = new Player(seven);
        Player player8 = new Player(eight);
        player1.setControl(KeyCode.A, "shoot");
        player2.setControl(KeyCode.S, "shoot");
        player3.setControl(KeyCode.D, "shoot");
        player4.setControl(KeyCode.F, "shoot");
        player5.setControl(KeyCode.A, "shoot");
        player6.setControl(KeyCode.S, "shoot");
        player7.setControl(KeyCode.D, "shoot");
        player8.setControl(KeyCode.F, "shoot");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        game.addPlayer(player5);
        game.addPlayer(player6);
        game.addPlayer(player7);
        game.addPlayer(player8);
        game.addPlayerToClient(0, player1);
        game.addPlayerToClient(0, player2);
        game.addPlayerToClient(0, player3);
        game.addPlayerToClient(0, player4);
        game.addPlayerToClient(1, player5);
        game.addPlayerToClient(1, player6);
        game.addPlayerToClient(1, player7);
        game.addPlayerToClient(1, player8);
        Level level = new Level(1);
        level.setBackgroundMusic("Cascada - Everytime We Touch.mp3");
        GameBoundary gameBoundaries = new NoBoundary(700, 675);
        ScrollType scrollType = new ScrollType("ForcedScrolling", gameBoundaries);
        scrollType.setScrollSpeed(10);
        scrollType.addScrollDirection(Direction.UP);
        level.setScrollType(scrollType);
        level.setBackgroundImage("Background/multiplayerddr.png");
        game.setCurrentLevel(level);
        level.addPlayer(one);
        level.addPlayer(two);
        level.addPlayer(three);
        level.addPlayer(four);
        level.addPlayer(five);
        level.addPlayer(six);
        level.addPlayer(seven);
        level.addPlayer(eight);
        level.getGameObjects().add(hack);
        level.getGameObjects().add(hack2);
        level.getGameObjects().add(hack3);
        level.getGameObjects().add(hack4);
        level.getGameObjects().add(hack5);
        level.getGameObjects().add(hack6);
        level.getGameObjects().add(hack7);
        level.getGameObjects().add(hack8);
        HashMap<String,String> DDRArrowProperties = new HashMap<String,String>();
        DDRArrowProperties.put("points", "20");
        DDRArrowProperties.put("removeobject", "");
        RandomGeneration arrow1 = new RandomGeneration(DDRArrowProperties,75,75,"ddrleftarrow.png",2, 20,20,1234,1234,700,800);
        RandomGeneration arrow2 = new RandomGeneration(DDRArrowProperties,75,75,"ddrdownarrow.png",2, 20+85 ,20+85,1234,1234,500,520);
        RandomGeneration arrow3 = new RandomGeneration(DDRArrowProperties,75,75,"ddruparrow.png",2, 20+85+85,20+85+85,1234,1234,300,600);
        RandomGeneration arrow4 = new RandomGeneration(DDRArrowProperties,75,75,"ddrrightarrow.png",2, 20+85+85+85,20+85+85+85,1234,1234,540,1000);
        RandomGeneration arrow5 = new RandomGeneration(DDRArrowProperties,75,75,"ddrleftarrow.png",2, 20+85+85+85+100,20+85+85+85+100,1234,1234,700,800);
        RandomGeneration arrow6 = new RandomGeneration(DDRArrowProperties,75,75,"ddrdownarrow.png",2, 20+85+85+85+100+85 ,20+85+85+85+100+85,1234,1234,500,520);
        RandomGeneration arrow7 = new RandomGeneration(DDRArrowProperties,75,75,"ddruparrow.png",2, 20+85+85+85+100+85+85,20+85+85+85+100+85+85,1234,1234,300,600);
        RandomGeneration arrow8 = new RandomGeneration(DDRArrowProperties,75,75,"ddrrightarrow.png",2, 20+85+85+85+100+85+85+85,20+85+85+85+100+85+85+85,1234,1234,540,1000);
        ArrayList<RandomGeneration> randomGenerations = new ArrayList<RandomGeneration>();
        randomGenerations.add(arrow1);
        randomGenerations.add(arrow2);
        randomGenerations.add(arrow3);
        randomGenerations.add(arrow4);
        randomGenerations.add(arrow5);
        randomGenerations.add(arrow6);
        randomGenerations.add(arrow7);
        randomGenerations.add(arrow8);
        RandomGenFrame frame = new RandomGenFrameY(level,randomGenerations, false);
        level.setRandomGenerationFrame(frame);
        XMLSerializer testSerializer = new XMLSerializer();
        String xml = testSerializer.serializeGame(game);
        return xml;
    }
}