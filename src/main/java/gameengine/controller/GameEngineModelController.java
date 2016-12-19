// This entire file is part of my masterpiece.
// Soravit Sophastienphong
/**
 * I chose the game engine model controller as one of the classes in my masterpiece because it is the back-end half
 * of the controller that we divided into two parts in order to be able to integrate networking into the game engine.
 * When you first examine the class, then it appears that there may be too many public methods, but almost all of these
 * methods can only be accessed through one of the three interfaces that this class implements, one for rules, one
 * for random generation, and one for the server. I think this does well to encapsulate the data of the model
 * controller such that only the classes that need access to these public methods have access. In terms of contributing
 * to our implementation of the MVC design pattern, this class is completely encapsulated from the front-end of
 * the game engine, such that it has very little access to the visualization of the game objects it's manipulating.
 * Because of the way our TCP/UDP networking works, the game engine needed to run its backend on the server, which
 * increased the separation between the model and the view such that the client can talk to the server, but the two
 * controllers representing the model and the view cannot talk directly to reach other. If you consider the ClientGame
 * object that this class converts the Game class object into, it is stripped down to the only information that the
 * view would need to know to visualize the object, which is what the object looks like and where it is placed. To provide
 * an additional note, the class also makes good user lambda expressions with the Java 8 forEach functionality
 * that allows one to loop through a list and apply methods without having to modify each object in the list directly.
 * Dependency-wise, this class also requires very little to function properly, since it only requires that the game
 * it is working with has a level, a main character, and a specified scroll type. The rest of the methods being called
 * in the update function work fine regardless of whether aspects of the game such as collisions or random generations
 * are defined. Thus, the game is extremely flexible in not relying on great dependencies in such a way that any game,
 * especially multiplayer games could use this controller for the back-end for all of our features, which are general
 * and apply across a variety of games.
 *
 */

package gameengine.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import com.sun.javafx.scene.traversal.Direction;
import gameengine.controller.interfaces.CommandInterface;
import gameengine.controller.interfaces.ServerInterface;
import gameengine.controller.interfaces.RGInterface;
import gameengine.controller.interfaces.RuleActionHandler;
import gameengine.model.CollisionChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
import gameengine.model.ConditionChecker;
import gameengine.model.RandomGenFrame;
import gameengine.network.server.ServerMain;
import gameengine.view.GameEngineUI;
import objects.*;

/**
 * @author Soravit Sophastienphong, Eric Song, Brian Zhou
 */

public class GameEngineModelController implements RGInterface, ServerInterface, RuleActionHandler {

    private static final int MARGIN_COLLISION_SEPARATION = 10;
    private static final int TCP_PORT = 9090;
    private static final String NON_SCROLLABLE_PROPERTY = "nonscrollable";
    private static final String FREE_SCROLLING = "FreeScrolling";
    private static final String FORCED_SCROLLING = "ForcedScrolling";
    private static final String LIMITED_SCROLLING = "LimitedScrolling";

    private ServerMain serverMain;
    private List<RandomGenFrame> randomlyGeneratedFrames;
    private List<Integer> highScores;
    private Map<GameObject, Position> mainCharacterImprints;
    private CollisionChecker collisionChecker;
    private ConditionChecker conditionChecker;
    private MovementManager gameMovement;
    private Game currentGame;
    private String serverName;
    private CommandInterface commandInterface;

    /**
     * @param commandInterface An interface passed from the game engine view controller
     * @param serverName The name of the server on which the game is being run
     * Instantiates the classes and data structures necessary for the back-end to run
     */
    public GameEngineModelController(CommandInterface commandInterface, String serverName) {
        this.commandInterface = commandInterface;
        this.serverName = serverName;
        collisionChecker = new CollisionChecker(this);
        conditionChecker = new ConditionChecker();
        randomlyGeneratedFrames = new ArrayList<>();
        highScores = new ArrayList<>();
        mainCharacterImprints = new HashMap<>();
    }

    /**
     * Starts the game
     * @param game The game to be run
     */
    public void startGame(Game game) {
        currentGame = game;
        currentGame.getCurrentLevel().removeAllPlayers();
        Level currLevel = currentGame.getCurrentLevel();
        String scrollType = currLevel.getScrollType().getScrollTypeName();
        if (scrollType.equals(FREE_SCROLLING)){
            currLevel.setBackgroundObject();
        }
        gameMovement = new MovementManager(currLevel, GameEngineUI.APP_WIDTH,
                GameEngineUI.APP_HEIGHT);
        serverMain = new ServerMain(this, TCP_PORT, serverName);
    }

    /**
     * @return A reference to the current game
     */
    @Override
    public Game getGame() {
        return currentGame;
    }

    /**
     * Updates the model
     */
    @Override
    public void updateModel() {
        Level currLevel = currentGame.getCurrentLevel();
        gameMovement.setCurrLevel(currLevel);
        if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals(FORCED_SCROLLING) || currLevel.getScrollType().getScrollTypeName().equals(LIMITED_SCROLLING)) {
            removeOffscreenElements();
        }
        gameMovement.execute();
        checkProjectileDistances();
        if(currLevel.containsRandomGeneration()) {
            randomlyGenerateFrames();
        }
        collisionChecker.checkCollisions(currLevel.getPlayers(), currLevel.getGameObjects());
        collisionChecker.checkCollisions(currLevel.getProjectiles(), currLevel.getGameObjects()); // checkProjectileDistances();
        conditionChecker.checkConditions(this, currentGame.getCurrentLevel().getWinConditions(), currentGame.getCurrentLevel().getLoseConditions());
        List<GameObject> objects = currLevel.getAllGameObjects();
        objects.forEach(object -> object.setXPosition(object.getXPosition() + object.getVelX()));
        List<GameObject> mainChars = currLevel.getPlayers();
        mainChars.forEach(mainChar -> {
            Position position = new Position();
            position.setPosition(mainChar.getXPosition(), mainChar.getYPosition());
            mainCharacterImprints.put(mainChar, position);
            mainChar.checkPlatformStatus();
        });
    }

    /**
     * Maps a client to game objects based on the mapping defined in the game class object
     * @param clientID The client ID to be mapped to game objects
     */
    @Override
    public void addPlayersToClient(int clientID) {
        for (Long id : currentGame.getClientMappings().keySet()) {
            if (id.equals(new Long(clientID))) {
                for (Player p : currentGame.getClientMappings().get(id)) {
                    currentGame.getCurrentLevel().addPlayer(p.getMainChar());
                }
            }
        }
    }

    /**
     * @param currentGame The game to be set as the current game
     */
    public void setGame(Game currentGame) {
        this.currentGame = currentGame;
        if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals(FREE_SCROLLING)){
            currentGame.getCurrentLevel().setBackgroundObject();
        }
    }

    /**
     * Sets the current game as won
     */
    @Override
    public void winGame() {
        currentGame.setGameWon(true);
    }

    /**
     * Sets the current game as lost and saves the high score
     */
    @Override
    public void loseGame() {
        for(Map.Entry<Long, Integer> mapping : currentGame.getScoreMapping().entrySet()) {
            addHighScore(mapping.getValue());
        }
        currentGame.setGameOver(true);
    }

    /**
     * @param score The score to be checked against the current score
     * @return Whether or not the current game has reached the specified score
     */
    @Override
    public boolean reachedScore(int score) {
        for(Map.Entry<Long, Integer> mapping : currentGame.getScoreMapping().entrySet()) {
            if(mapping.getValue() >= score){
                return true;
            }
        }
        return false;
    }

    /**
     * @return The time of the current level
     */
    @Override
    public int getTime() {
        return currentGame.getCurrentLevel().getTime();
    }

    /**
     * @param object The ID of the client mapped to the specified game object
     * @return The desired client ID
     */
    @Override
    public long getClientID(GameObject object) {
        for(Map.Entry<Long, List<Player>> mapping : currentGame.getClientMappings().entrySet()){
            for(Player player : mapping.getValue()){
                if(player.getMainChar() == object){
                    return mapping.getKey();
                }else{
                    List<GameObject> projectiles = player.getMainChar().getProjectiles();
                    for(int i = 0; i <projectiles.size(); i ++){
                        if(projectiles.get(i) == object){
                            return mapping.getKey();
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Restarts the game engine view
     */
    @Override
    public void restart() {
        commandInterface.reset();
    }

    /**
     * Levels up the current game
     */
    @Override
    public void incrementLevel() {
        if (currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel()+1) != null) {
            currentGame.setCurrentLevel(currentGame.getLevelByIndex(currentGame.getCurrentLevel().getLevel() + 1));
            if (currentGame.getCurrentLevel().getScrollType().getScrollTypeName().equals(FREE_SCROLLING)){
                currentGame.getCurrentLevel().setBackgroundObject();
            }
        } else {
            winGame();
        }
    }

    /**
     * @param ID The ID of the player whose score is to be modified
     * @param score The score to be set
     */
    @Override
    public void modifyScore(long ID, int score) {
        int prevScore = currentGame.getScore(ID);
        int currScore = prevScore + score;
        currentGame.modifyScore(ID, currScore);
    }

    /**
     * Removes the specified object from the list of currently colliding objects
     * @param obj The object to be removed
     */
    @Override
    public void removeFromCollidedList(GameObject obj) {
        collisionChecker.manuallyRemoveFromConcurrentCollisionList(obj);
    }

    /**
     * Invokes a method in the movement manager based on the key pressed by the user
     * @param controlName The name of the command
     * @param ID The ID of the client
     * @param index The index of the main character being moved in the list of objects mapped to that client
     */
    @Override
    public void runControl(String controlName, int ID, int index) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = gameMovement.getClass().getDeclaredMethod(controlName, GameObject.class, double.class);
        method.invoke(gameMovement, currentGame.getClientMappings().get(new Long(ID)).get(index).getMainChar(), 10);
    }

    /**
     * Resets the position of an object for platforms
     * @param mainChar The colliding character
     * @param obj The object being collided with
     * @param oneSided Whether or not the object is a one-sided platform
     */
    @Override
    public void resetObjectPosition(GameObject mainChar, GameObject obj, boolean oneSided) {
        double newPosition;
        if(oneSided && SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar,
                obj) != IntersectionAmount.NOT_INTERSECTING) {
            newPosition = setNewPosition(mainCharacterImprints.get(mainChar).getY(), mainChar, obj);
        } else if ((SingletonBoundaryChecker.getInstance().getHorizontalIntersectionAmount(mainChar,obj) != IntersectionAmount.NOT_INTERSECTING)) {
            newPosition = setNewPosition(mainCharacterImprints.get(mainChar).getY() + mainChar.getHeight() - MARGIN_COLLISION_SEPARATION, mainChar, obj);
        } else {
            newPosition = mainCharacterImprints.get(mainChar).getY();
        }
        mainChar.setYPosition(newPosition);
        mainChar.setXPosition(mainCharacterImprints.get(mainChar).getX());
    }

    /**
     * Removes an object from the level
     * @param obj The object to be removed
     */
    @Override
    public void removeObject(GameObject obj) {
        currentGame.getCurrentLevel().removeGameObject(obj);
    }

    /**
     * @return A reference to the client game
     */
    @Override
    public ClientGame getClientGame() {
        return generateClientGame(currentGame);
    }

    private void addHighScore(int score) {
        if (highScores.size() < 5) {
            highScores.add(score);
            Collections.sort(highScores);
            Collections.reverse(highScores);
        } else {
            Integer lowestHighScore = highScores.get(4);
            if (score > lowestHighScore) {
                highScores.remove(lowestHighScore);
                highScores.add(score);
                Collections.sort(highScores);
                Collections.reverse(highScores);
            }
        }
    }


    private ClientGame generateClientGame(Game game) {
        Level currLevel = game.getCurrentLevel();
        ClientGame clientGame = new ClientGame(currLevel.getMusicFilePath(), currLevel.getBackgroundFilePath(), highScores);
        clientGame.addAll(game.getCurrentLevel().getAllGameObjects());
        clientGame.addScores(game.getScoreMapping());
        clientGame.setGameInfo(currLevel.getLevel(), game.isGameLost(), game.isGameWon());
        if (currLevel.getBackground()!=null){
            clientGame.setBackgroundObject(currLevel.getBackground());
        }
        return clientGame;
    }

    private void checkProjectileDistances() {
        for (Iterator<GameObject> itr = currentGame.getCurrentLevel().getProjectiles().iterator(); itr.hasNext();) {
            GameObject projectile = itr.next();
            ProjectileProperties properties = projectile.getProjectileProperties();
            if ((properties.getDirection().equals(Direction.RIGHT) || properties.getDirection().equals(Direction.LEFT))
                    && (projectile.getXDistanceMoved() >= properties.getRange())) {
                projectile.getProjectiles().remove(projectile);
                removeObject(projectile);
                itr.remove();
            } else if ((properties.getDirection().equals(Direction.UP) || properties.getDirection().equals(Direction.DOWN)
                    && Math.abs(projectile.getYDistanceMoved()) >= properties.getRange())){
                projectile.getProjectiles().remove(projectile);
                removeObject(projectile);
                itr.remove();
            }
        }
    }

    private double setNewPosition(double mainCharacterReference, GameObject mainChar, GameObject obj){
        double newPosition =  mainCharacterImprints.get(mainChar).getY();
        if (mainCharacterReference < obj.getYPosition()) {
            newPosition = obj.getYPosition() - mainChar.getHeight() - MARGIN_COLLISION_SEPARATION / 2;
            mainChar.setPlatformCharacterIsOn(obj);
        }

        return newPosition;
    }


    private void randomlyGenerateFrames(){
        for(RandomGeneration<Integer> randomGeneration : currentGame.getCurrentLevel().getRandomGenRules()) {
            currentGame.getCurrentLevel().getRandomGenerationFrame().possiblyGenerateNewFrame(randomGeneration);
        }
    }

    private void removeOffscreenElements() {
        List<GameObject> objects = currentGame.getCurrentLevel().getAllGameObjects();
        if (objects == null || objects.size() == 0)
            return;
        for (int i = objects.size() - 1; i >= 0; i--) {
            GameObject obj = objects.get(i);
            if(obj.getYPosition() > GameEngineUI.APP_HEIGHT && obj.getProperty(NON_SCROLLABLE_PROPERTY) == null){
                removeObject(obj);
            }
        }
    }
}
