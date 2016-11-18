package gameeditor.view;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public interface ICommandButton {
	
	public Rectangle getBorder();
	public Rectangle getBG();
	public ImageView getImageView();
	
	public void highlight();
	public void lowlight();
	
}
