package gameengine.model;

public class EnemyMisreferencedException extends Exception {

	public EnemyMisreferencedException(String message) {
		super(message);
	}

	public EnemyMisreferencedException(String message, Throwable cause) {
		super(message, cause);
	}
}
