package objects.interfaces;

import objects.GameObject;

public interface IGameObjectFactory {

    public GameObject getGameObject(String filePath, String gameObjectType);
    
}
