package usecases;
import general.GameFile;
import general.GameFileView;

public class UseCaseDeleteGame {
	
	/**
	 * The user would click the delete function on the GameFileView. The 
	 * event would then be sent up the chain to the GalleryView that contains
	 * the GameFileView. Then the GalleryView would access the gameFile in the
	 * GameFile view and delete that file.
	 */
	public void deleteGame(){
		GameFileView gameFileView = new GameFileView(new GameFile());
		// If the user selects the delete option in the GameFileView
		gameFileView.fireDeleteEvent();		
	}
}
