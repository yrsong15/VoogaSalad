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
	private Map<String, String> gallery;
	private ArrayList<GameFile> myGallery;

	public Gallery() {
		gallery = new HashMap<String, String>();
		myGallery = new ArrayList<>();
	}

	//	public void addToGallery(String gameName, String XMLData) throws IOException {
//		FileUtils.writeStringToFile(new File(gameName), XMLData, true);
////		gallery.put(gameName, XMLData);
//		myGallery.add(new GameFile(gameName, XMLData));
//	}
	public void addToGallery(GameFile newGame) throws IOException {
//		FileUtils.writeStringToFile(new File(newGame.getGameName()), newGame.getGameData(), true);
//		gallery.put(gameName, XMLData);
		myGallery.add(newGame);
	}
	//TODO: fix this for arrayList
	public void removeFromGallery(String ganeName)
	{
		gallery.remove(ganeName);
	}
	public String getGalleryItem(String key){
		return gallery.get(key);
	}

	// TODO: Makes this return an iterator
	public List<GameFile> getUnmodifiableListOfGameFiles()
	{
		return Collections.unmodifiableList(myGallery);
	}
}