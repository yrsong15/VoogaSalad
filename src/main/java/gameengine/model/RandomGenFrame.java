package gameengine.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;

public abstract class RandomGenFrame<T> {

    protected Level level;
    protected ArrayList<RandomGeneration<Integer>> randomGenRules;
    protected GameObject referenceFirstObject;
    protected static final Random RNG = new Random();

    public abstract void possiblyGenerateNewFrame (RandomGeneration<Integer> randomGenRules);

    public abstract void setNewFirstBenchmark(GameObject object);

    public abstract double calculateY(int margin, RandomGeneration<Integer> elem, double buffer);

    public abstract int calculateMargin(RandomGeneration<Integer> elem);

    public abstract double calculateX(int margin, RandomGeneration<Integer> elem, double buffer);

    public ArrayList<RandomGeneration<Integer>> getRandomGenerationRules(){
        return this.randomGenRules;
    }

    protected void generateNewFrameAndSetBenchmark(Level level) {
        ArrayList<RandomGeneration<Integer>> randomGenRulesList = randomGenRules;
        for(RandomGeneration<Integer> elem:randomGenRulesList){
            int minSep = elem.getMinSpacing(); double width = elem.getWidth();
            int maxSep = elem.getMaxSpacing(); double height = elem.getHeight();
            int buffer = 0;
            for(int i=0; i<elem.getNumObjects();i++){
                double nextSeparationDist = RNG.nextInt(maxSep - minSep) + minSep;
                buffer += nextSeparationDist;
                int margin = calculateMargin(elem);
                if(margin <= 0) margin = 1;
                double YPosition = calculateY(margin,elem,buffer);
                double XPosition = calculateX(margin,elem,buffer);
                generateObject(XPosition, YPosition, width, height, elem.getImageURL(),elem.getObjectProperties());
            }
        }
    }

    protected void generateObject(double xPosition,double yPosition, double width, double height, String URL, Map<String, String> objectProperties) {
        GameObject object = new GameObject(xPosition, yPosition, width, height, URL,objectProperties);
        level.getGameObjects().add(object);
        setNewFirstBenchmark(object);
    }
}