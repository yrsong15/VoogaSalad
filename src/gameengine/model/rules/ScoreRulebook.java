package gameengine.model.rules;

public class ScoreRulebook {
	private double totalScore;

	public ScoreRulebook() {
		totalScore = 0;
	}
	
	public void modifyScore(int score){
		totalScore += score;
	}

}
