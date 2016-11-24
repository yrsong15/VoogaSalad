package gameeditor.commanddetails;

import gameeditor.objects.GameObject;

public interface ISelectDetail {
	
	public void initLevel2(GameObject sprite);
	
	public void updateSpriteDetails(double x, double y);
	
	public void clearSelect();
	
}
