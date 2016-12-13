package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;
import objects.interfaces.IGame;
import sun.management.counter.perf.PerfLongArrayCounter;

/**
 * Created by Soravit on 11/18/2016.
 * @ author Soravit, pratiksha Sharma
 */
public class Game implements IGame{

	private String name;
	private Map<Integer, Level> levels;
	private Level currentLevel;
	private List<Player> players;
	private Map<Long, List<Player>> clientMappings;
	private Map<Long, Integer> scoreMapping;

	public Map<Long, List<Player>> getClientMappings(){
	    return clientMappings;
    }

    private int minNumPlayers = 1;

	public Game(String name) {
		levels = new HashMap<>();
		scoreMapping = new HashMap<>();
		players = new ArrayList<>();
		clientMappings = new HashMap<>();
		this.name = name;
	}

	public void addPlayerToClient(long ID, Player player){
		if(!clientMappings.containsKey(ID)){
			List<Player> players = new ArrayList<>();
			players.add(player);
			clientMappings.put(ID, players);
		}else{
			List<Player> players = clientMappings.get(ID);
			players.add(player);
		}
	}

	public void removePlayer(Player player){
		players.remove(player);
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}

    public void addLevel(Level level) {
        levels.put(level.getLevel(),level);
    }

    public void removeLevel(int level) {
        levels.remove(level);
    }

    public Level getCurrentLevel(){
        return this.currentLevel;
    }

    public void setCurrentLevel(Level currentLevel){
        this.currentLevel = currentLevel;
    }

    public void setGameName(String name){
        this.name = name;
    }

    public int getNumberOfLevels(){
        return levels.size();
    }

    public String getGameName(){
        return this.name;
    }

    public Level getLevelByIndex(int index){
        return levels.get(index);
    }

    public int getScore(Long clientID){
        if(scoreMapping.get(clientID) == null){
            scoreMapping.put(clientID, 0);
        }
        return scoreMapping.get(clientID);
    }

    public void modifyScore(long clientID, int score){
        scoreMapping.put(clientID, score);
    }

    public Map<Long, Integer> getScoreMapping(){
        return scoreMapping;
    }

    public int getMinNumPlayers() {
        if(minNumPlayers == 0){
            minNumPlayers = 1;
        }
        return minNumPlayers;
    }

    public void setMinNumPlayers(int minNumPlayers) {
        this.minNumPlayers = minNumPlayers;
    }

}