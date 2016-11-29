package gameengine.model;

import java.util.Map;

import gameengine.controller.interfaces.RuleActionHandler;

public class WinChecker {
	
	
	public static void checkWinConditions(RuleActionHandler handler, Map<String, String> winConditions, Map<String, Double> gameConditions){
		for (String condition : winConditions.keySet()){
			if (gameConditions.containsKey(condition) && Double.parseDouble(winConditions.get(condition))==gameConditions.get(condition)){
				handler.endGame();
			}
		}
		
		
	}

}
