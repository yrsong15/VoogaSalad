package objects.interfaces;

import general.objects.GameObject;

public interface IGameObjectFactory {

    public GameObject getGameObject(String filePath, String gameObjectType);
    
}
