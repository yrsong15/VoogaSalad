package gameeditor.commanddetails;

import gameeditor.objects.GameObjectView;
/**
 * @author John Martin
 *
 */
public interface ISelectDetail extends ILevelTwo {
	
    public static final String  RANDOM_GEN_KEY=DetailResources.RANDOM_GEN_KEY.getResource();
    public static final String X_POSITION_KEY=DetailResources.X_POSITION_KEY.getResource();
    public static final String Y_POSITION_KEY=DetailResources.Y_POSITION_KEY.getResource();
		
    public void updateSpritePosition(double x, double y);
	
    public void updateSpriteDimensions(double width, double height);
	
    public void clearSelect();
	
    public void switchSelectStyle(GameObjectView sprite);
	
}
