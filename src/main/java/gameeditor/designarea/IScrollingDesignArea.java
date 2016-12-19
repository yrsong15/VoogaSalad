package gameeditor.designarea;

//This entire file is part of my masterpiece.
//John Martin
//This interface is specific to the scrolling type design area, and enables the scrolling design area to implement
//specific objects relevant only to the scrolling type game editor design. I believe this is good design as it together
//with the other parts of the ScrollingDesignArea inheritance hierarchies, enables the flexible extensibility that makes
//this entire component flexible.

import gameeditor.objects.BoundingBox;
import gameeditor.objects.MultiBoundingBox;
/**
 * 
 * @author John Martin
 *
 */
public interface IScrollingDesignArea {
    
	public void addMultiBoundingBox(MultiBoundingBox multiBoundingBox);

	public void removeMultiBoundingBox();
    
    public void addBoundingBox(BoundingBox bb);
    
    public void removeBoundingBox(BoundingBox bb);
}
