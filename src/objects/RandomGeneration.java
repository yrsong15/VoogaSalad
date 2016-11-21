package objects;

import java.util.Random;

/**
 * Created by Soravit on 11/21/2016.
 */
public class RandomGeneration {

    private int numObjects;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minSpacing;
    private int maxSpacing;

    private RandomGeneration(int numObjects, int minX, int maxX, int minY, int maxY, int minSpacing, int maxSpacing){
        this.numObjects = numObjects;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minSpacing = minSpacing;
        this.maxSpacing = maxSpacing;

    }
}
