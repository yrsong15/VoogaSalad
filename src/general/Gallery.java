package general;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import general.interfaces.IGallery;
import objects.Game;
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Gallery implements IGallery {

	private ArrayList<GameFile> gallery;


	public Gallery() throws IOException {
		this.gallery = new ArrayList<GameFile>();
		addDefaultGame();
	}

	private void addDefaultGame() throws IOException {
		GameFile defaultGame = new GameFile("Example Game", readFile("data/GameEditorExampleXML.xml")); //
		addToGallery(defaultGame);
	}

	@Override
	public String readFile(String path) throws IOException{
		StringBuilder sb = new StringBuilder();
		String sCurrentLine;
		try (BufferedReader br = new BufferedReader(new FileReader(path))){

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		}
		return sb.toString();
	}

	@Override
	public void addToGallery(GameFile newGame) {
		gallery.add(newGame);

	}

	//TODO: fix this for arrayList
	@Override
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
	@Override
	public List<GameFile> getUnmodifiableListOfGameFiles()
	{
		return Collections.unmodifiableList(gallery);
	}
}