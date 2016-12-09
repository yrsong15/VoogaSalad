package gameengine.controller.interfaces;

import objects.GameObject;
import objects.Player;

public interface ControlInterface {
	
	public void moveUp(Player player);
	public void moveDown(Player player);
	public void moveRight(Player player);
	public void moveLeft(Player player);
	public void jump(Player player);
    public void shootProjectile(Player player);
}
