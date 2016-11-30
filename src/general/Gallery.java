package general;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Gallery {

	private ArrayList<GameFile> gallery;

	public Gallery() {
		this.gallery = new ArrayList<GameFile>();
	}

	public void addToGallery(GameFile newGame) {
		gallery.add(newGame);

	}
	//TODO: fix this for arrayList
	public void removeFromGallery(String ganeName)
	{
		gallery.remove(ganeName);
	}
	
	/**
	 * THIS METHOD WILL BE REMOVED EVENTUALLY
	 * @param key
	 * @return
	 */
	public String getGalleryItem(String key){
		for(GameFile gameFile : gallery)
		{
			if(gameFile.getGameName().equals(key))
			{
				return gameFile.getGameData();
			}
		}
		return "ERROR";
	}
	
	// TODO: Makes this return an iterator
	public List<GameFile> getUnmodifiableListOfGameFiles()
	{
		return Collections.unmodifiableList(gallery);
	}
}