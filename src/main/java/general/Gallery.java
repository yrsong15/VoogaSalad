package general;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import general.interfaces.IGallery;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.Game;
import org.apache.commons.io.FileUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Delia Li, Ryan Bergamini
 */

public class Gallery implements IGallery {

	private ArrayList<GameFile> gallery;

	public Gallery() throws IOException {
		this.gallery = new ArrayList<>();
		addDefaultGame();
	}

	private void addDefaultGame() throws IOException {
		String ddRString = "file:" + System.getProperty("user.dir") + "/images/Sprite/ddrrightarrow.png";
		
		GameFile ddr = new GameFile("Dance Dance Revolution",
				readFile("data/DanceDanceRevolution.xml"), new Image(ddRString));
		addToGallery(ddr);
		String marioString = "file:" + System.getProperty("user.dir") + "/images/Sprite/mario.png";
		
		GameFile mario = new GameFile("Super Mario",
				readFile("data/Mario.xml"), new Image(marioString));
		addToGallery(mario);
		String doodleString = "file:" + System.getProperty("user.dir") + "/images/Sprite/doodler.png";
		
		GameFile doodle = new GameFile("Doodle Jump",
				readFile("data/DoodleJump.xml"), new Image(doodleString));
		addToGallery(doodle);
		String flappyString = "file:" + System.getProperty("user.dir") + "/images/Sprite/bird5.png";
		
		GameFile flappy = new GameFile("Example Flappy Bird",
				readFile("data/FlappyExample.xml"), new Image(flappyString));
		addToGallery(flappy);
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
	public void removeFromGallery(String gameName)
	{
		gallery.remove(gameName);
	}
	
	// TODO: Makes this return an iterator
	@Override
	public List<GameFile> getUnmodifiableListOfGameFiles()
	{
		return Collections.unmodifiableList(gallery);
	}
}