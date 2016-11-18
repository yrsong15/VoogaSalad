package objects;

import javafx.scene.image.ImageView;

public interface IGameObject {
    
    public ImageView getImageView();

    public void setXPosition();
    
    public void setYPosition();
    
    public void init(String filePath);
    
}
