package objects;


	
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject implements IGameObject {
    private ImageView myGameObjectImageView;

    //Constructor
    public GameObject(String filePath){
        if(filePath!=null){
            myGameObjectImageView = new ImageView(new Image(filePath));

        }
    }
    
    @Override
    public ImageView getImageView () {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void setXPosition () {
        // TODO Auto-generated method stub


    }

    @Override
    public void setYPosition () {
        // TODO Auto-generated method stub

    }

    @Override
    public void init (String filePath) {
        // TODO Auto-generated method stub

    }

}
