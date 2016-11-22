package gameengine.model;

import java.util.Map;

import gameengine.controller.RuleActionHandler;

public class WinChecker {
	
	
	public void checkWinConditions(RuleActionHandler handler, Map<String, String> winConditions, Map<String, String> gameConditions){
		for (String condition : winConditions.keySet()){
			if (winConditions.get(condition)==gameConditions.get(condition)){
				handler.endGame();
			}
		}
		
		
	}

}
