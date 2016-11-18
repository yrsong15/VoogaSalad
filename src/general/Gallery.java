package general;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Gallery {

	private Map<String, String> gallery;
	
	public Gallery() {
		gallery = new HashMap<String, String>();
	}
	
	public void addToGallery(String gameName, String XMLData) throws IOException {
		FileUtils.writeStringToFile(new File(gameName), XMLData);
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
