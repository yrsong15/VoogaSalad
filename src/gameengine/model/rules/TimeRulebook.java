package gameengine.model.rules;

public class TimeRulebook {
	private double totalTime;

	public TimeRulebook(double timeAllowed) {
		totalTime = timeAllowed;
	}
	
	public void modifyTime(double sec){
		totalTime += sec;
	}

}
