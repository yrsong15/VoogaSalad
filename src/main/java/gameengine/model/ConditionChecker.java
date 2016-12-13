package gameengine.model;

import java.util.Map;

import gameengine.controller.interfaces.RuleActionHandler;

public class ConditionChecker {
	
	public static void checkConditions(RuleActionHandler handler, Map<String, String> winConditions, Map<String, String> loseConditions){
	    for(Map.Entry<String, String> condition : winConditions.entrySet()){
			if(condition.getKey().equals("time") && handler.getTime() >= Integer.parseInt(condition.getValue())){
				handler.winGame();
			}else if(condition.getKey().equals("score") && handler.reachedScore(Integer.parseInt(condition.getValue()))){
				handler.winGame();
			}
		}
		for(Map.Entry<String, String> condition : loseConditions.entrySet()){
			if(condition.getKey().equals("time") && handler.getTime() >= Integer.parseInt(condition.getValue())){
				handler.endGame();
			}else if(condition.getKey().equals("score") && handler.reachedScore(Integer.parseInt(condition.getValue()))){
                handler.endGame();
			}
		}
		
	}

}
