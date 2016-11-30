package gameengine.model;

import java.util.Map;

import gameengine.controller.interfaces.RuleActionHandler;

public class LossChecker {
	
	
	public static void checkLossConditions(RuleActionHandler handler, Map<String, String> lossConditions, Map<String, Double> gameConditions){
		for (String condition : lossConditions.keySet()){
			if (gameConditions.containsKey(condition) && Double.parseDouble(lossConditions.get(condition))==gameConditions.get(condition)){
				handler.endGame();
			}
		}
	}
}
