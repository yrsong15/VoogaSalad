package gameeditor.view.interfaces;

import java.util.ArrayList;

import gameeditor.objects.GameObjectView;
import javafx.scene.layout.Pane;
/**
 * 
 * @author John Martin
 *
 */
public interface IDetailPane {
	
    public void setAvatar(String  filePath);
	
    public Pane getPane();

    public void removeDetail();

    public void setDetail(String string);
	
    public GameObjectView getCurrentAvatar();

    public  String getLastPaneType ();

    public void updateDetail ();

	public ArrayList<GameObjectView> getCurrentAvatars();

}
