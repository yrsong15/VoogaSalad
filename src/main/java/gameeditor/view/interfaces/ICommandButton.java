package gameeditor.view.interfaces;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
/**
 * 
 * @author John Martin
 *
 */
public interface ICommandButton {
	
	public Rectangle getBorder();
	public Rectangle getBG();
	public ImageView getImageView();
	
	public void highlight();
	public void lowlight();
	public void checkHighlight(String paneType);
	
}
