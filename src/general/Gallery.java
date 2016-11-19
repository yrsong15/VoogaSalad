package general;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class Gallery {

	private Map<String, String> gallery;
	
	public Gallery() {
		gallery = new HashMap<String, String>();
	}
	
	public void addToGallery(String gameName, String XMLData) throws IOException {
		FileUtils.writeStringToFile(new File(gameName), XMLData, true);
		gallery.put(gameName, XMLData);
	}
	
	public void removeFromGallery(String ganeName)
	{
		gallery.remove(ganeName);
	}

	public String getGalleryItem(String key){
		return gallery.get(key);
	}
}
