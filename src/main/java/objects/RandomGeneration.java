package objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomGeneration<T> {

    private HashMap<String,String> objectProperties;
    private String URL;
    
    private T numObjects;
    private T minX;
    private T maxX;
    private T minY;
    private T maxY;
    private T minSpacing;
    private T maxSpacing;
    private T width;
    private T height;

    public RandomGeneration(HashMap<String,String> objectProperties, T width, T height, String imageURL, T numObjects, T minX, T maxX, T minY, T maxY, T minSpacing, T maxSpacing){
        this.URL = imageURL;
        this.width = width;
        this.height = height;
    	this.objectProperties = objectProperties;
        this.numObjects = numObjects;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minSpacing = minSpacing;
        this.maxSpacing = maxSpacing;
    }

    public HashMap<String,String> getObjectProperties(){
    	return objectProperties;
    }
    
    public String getImageURL(){
    	return this.URL;
    }
    
    public T getWidth(){
    	return width;
    }
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
	public T getHeight() {
		return height;
	}
}	
