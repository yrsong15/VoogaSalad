package objects;

import java.util.Random;

/**
 * Created by Soravit on 11/21/2016.
 */
public class RandomGeneration<T> {

    private GameObject gameObject;
    private T numObjects;
    private T minX;
    private T maxX;
    private T minY;
    private T maxY;
    private T minSpacing;
    private T maxSpacing;

    public RandomGeneration(GameObject gameObject, T numObjects, T minX, T maxX, T minY, T maxY, T minSpacing, T maxSpacing){
        this.gameObject = gameObject;
        this.numObjects = numObjects;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minSpacing = minSpacing;
        this.maxSpacing = maxSpacing;

    }

    public GameObject getGameObject(){return gameObject;}
    public T getNumObjects(){
    	return numObjects;
    }
    public T getMinX(){
    	return minX;
    }
    public T getMaxX(){
    	return maxX;
    }
    public T getMinY(){
    	return minY;
    }
    public T getMaxY(){
    	return maxY;
    }
    public T getMinSpacing(){
    	return minSpacing;
    }
    public T getMaxSpacing(){
    	return maxSpacing;
    }
}	
