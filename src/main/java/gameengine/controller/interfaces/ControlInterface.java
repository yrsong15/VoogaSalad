package gameengine.controller.interfaces;

import objects.GameObject;
import objects.Player;

public interface ControlInterface {
	public void moveUp(GameObject player, double speed);
	public void moveDown(GameObject player, double speed);
	public void moveRight(GameObject player, double speed);
	public void moveLeft(GameObject player, double speed);
	public void jump(GameObject player, double speed);
    public void shootProjectile(GameObject player, double speed);
}
