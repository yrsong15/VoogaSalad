package gameeditor.view.interfaces;

import gameeditor.objects.GameObjectView;
import gameeditor.objects.MultiBoundingBox;
/**
 * 
 * @author John Martin
 *
 */
public interface IStandardDesignArea extends IDesignArea {
	
	public void addSprite(GameObjectView gameObject);
    
    public void removeSprite(GameObjectView gameObject);
    
	public void addMultiBoundingBox(MultiBoundingBox multiBoundingBox);

	public void removeMultiBoundingBox();
}
