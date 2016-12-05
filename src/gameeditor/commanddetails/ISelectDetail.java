package gameeditor.commanddetails;

import gameeditor.objects.GameObject;

public interface ISelectDetail {
	
    public static final String  RANDOM_GEN_KEY=DetailResources.RANDOM_GEN_KEY.getResource();
    public static final String X_POSITION_KEY=DetailResources.X_POSITION_KEY.getResource();
    public static final String Y_POSITION_KEY=DetailResources.Y_POSITION_KEY.getResource();
	public void initLevel2(GameObject sprite);
	
	public void updateSpritePosition(double x, double y);
	
	public void updateSpriteDimensions(double width, double height);
	
	public void clearSelect();
	
}
