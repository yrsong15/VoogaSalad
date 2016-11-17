package general;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GalleryView 
{
	private Gallery gallery;
	private Scene scene;
	
	public GalleryView(Gallery gallery)
	{
		this.gallery = gallery;
		Pane root = new Pane();
		root.setMinSize(400, 800);
		this.scene = new Scene(root);
		configureEventListeners();
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	private void configureEventListeners()
	{
		scene.addEventHandler(GameFileEvent.REMOVE_FROM_GALLERY, e -> removeGameFile(e));
	}
	
	private void removeGameFile(GameFileEvent gameFileEvent)
	{
		gallery.removeFromGallery(gameFileEvent.getGameFile());
		updateView();
	}
	
	private void updateView()
	{
		// This method reconfigures the GalleryView so that it accurately presents all files in the gallery
	}
	
	
}
