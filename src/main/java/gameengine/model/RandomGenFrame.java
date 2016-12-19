package gameengine.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import objects.GameObject;
import objects.Level;
import objects.RandomGeneration;
public abstract class RandomGenFrame<T> {
    private static final double enemySize = 50;

    protected Level level;
    protected ArrayList<RandomGeneration<Integer>> randomGenRules;
    protected GameObject referenceFirstObject;
    protected static final Random RNG = new Random();
    protected boolean generatingEnemies;

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
            int platformIndexWithEnemyForThisFrame = RNG.nextInt(elem.getNumObjects());   
            int minSep = elem.getMinSpacing();        
            double width = (double) ((Integer) elem.getWidth()).intValue() ;
            double height = (double) ((Integer) elem.getHeight()).intValue();
            int maxSep = elem.getMaxSpacing();
            
            int buffer = 0;
            ArrayList<GameObject> newlyCreatedPlatforms = new ArrayList<>();
            for(int i=0; i<elem.getNumObjects();i++){
                double nextSeparationDist = RNG.nextInt(maxSep - minSep) + minSep;
                buffer += nextSeparationDist;
                int margin = calculateMargin(elem);
                if(margin <= 0) margin = 1;
                double YPosition = calculateY(margin,elem,buffer);
                double XPosition = calculateX(margin,elem,buffer);
                newlyCreatedPlatforms.add(generateObject(XPosition, YPosition, width, height, elem.getImageURL(),elem.getObjectProperties()));
            }
            if(generatingEnemies)
                generateEnemyOnPlatform(newlyCreatedPlatforms.get(platformIndexWithEnemyForThisFrame));
        }
    }

	private void generateEnemyOnPlatform(GameObject referencePlatform){
		HashMap<String,String> enemyProperties = new HashMap<String,String>();
		GameObject enemy = new GameObject(referencePlatform.getXPosition() + ((referencePlatform.getWidth() - enemySize)/2), referencePlatform.getYPosition() - enemySize, enemySize, enemySize, "duvall.png", enemyProperties);
		enemy.setProperty("enemy", "30");
		enemy.setProperty("removeobject", "");
		level.getGameObjects().add(enemy);
	}


    protected GameObject generateObject(double xPosition,double yPosition, double width, double height, String URL, Map<String, String> objectProperties) {
        GameObject object = new GameObject(xPosition, yPosition, width, height, URL,objectProperties);
        level.getGameObjects().add(object);
        setNewFirstBenchmark(object);
        return object;
    }
}