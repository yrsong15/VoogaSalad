package gameeditor.commanddetails;

import gameeditor.objects.GameObject;

public interface ISelectDetail {
	
	public void initLevel2(GameObject sprite);
	
	public void updateSpritePosition(double x, double y);
	
	public void updateSpriteDimensions(double width, double height);
	
	public void clearSelect();
	
}
