package objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObjectFactory implements IGameObjectFactory {
    //TODO: Remove hard coding of Object Types

    public GameObject getGameObject(String filePath, String gameObjectType){
        switch (gameObjectType){
            case ("Player"):


            case ("Obstacle"):


            case ("Health"):


            case ("Bonus"):



            case ("Enemy"):


        }
        return null;


    }
}
