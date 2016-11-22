package gameengine.model;

import java.util.Map;

import gameengine.controller.RuleActionHandler;

public class LossChecker {
	
	
	public void checkLossConditions(RuleActionHandler handler, Map<String, String> lossConditions, Map<String, String> gameConditions){
		for (String condition : lossConditions.keySet()){
			if (lossConditions.get(condition)==gameConditions.get(condition)){
				handler.endGame();
			}
		}
	}
}
