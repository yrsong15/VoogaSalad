package gameengine.model;

import java.util.Map;

import gameengine.controller.RuleActionHandler;

public class WinChecker {
	
	
	public static void checkWinConditions(RuleActionHandler handler, Map<String, Integer> map, Map<String, Integer> map2){
		for (String condition : map.keySet()){
			if (map.get(condition)==map2.get(condition)){
				handler.endGame();
			}
		}
		
		
	}

}
