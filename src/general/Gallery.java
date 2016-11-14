package general;

import java.util.List;
import java.util.ArrayList;

public class Gallery {

	private List<GameFile> gallery;
	
	public Gallery()
	{
		gallery = new ArrayList<GameFile>();
	}
	
	public void addToGallery(GameFile gameFile)
	{
		gallery.add(gameFile);
	}
	
	public void removeFromGallery(GameFile gameFile)
	{
		gallery.remove(gallery.indexOf(gameFile));
	}
}
