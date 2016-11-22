package gameengine.model;

import java.util.Map;

import gameengine.controller.interfaces.RuleActionHandler;

public class LossChecker {
	
	
	public static void checkLossConditions(RuleActionHandler handler, Map<String, Integer> lossConditions, Map<String, Integer> gameConditions){
		for (String condition : lossConditions.keySet()){
			if (lossConditions.get(condition)==gameConditions.get(condition)){
				handler.endGame();
			}
		}
	}
}
