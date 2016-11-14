package general;

import javafx.scene.Node;

public class GalleryView 
{
	private Gallery gallery;
	private Node view;
	
	public GalleryView(Gallery gallery)
	{
		this.gallery = gallery;
		configureEventListeners();
	}
	
	private void configureEventListeners()
	{
		view.addEventHandler(GameFileEvent.REMOVE_FROM_GALLERY, e -> removeGameFile(e));
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
