package objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.sun.javafx.scene.traversal.Direction;
import gameengine.controller.SingletonBoundaryChecker;
import gameengine.controller.SingletonBoundaryChecker.IntersectionAmount;
/**
 *
 * @author Ray Song, Soravit
 *
 */
public class GameObject {
    private static final int marginForPlatformRecognition = 20;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    private String imageFileName;
    private Map<String, String> properties;
    private double xDistanceMoved;
    private double yDistanceMoved;
    private boolean onPlatform = false;
    private GameObject platformCharacterIsOn;
    private ProjectileProperties projectileProperties;
    private boolean isPlayer;
    private Direction direction;
    private int id;
    private String typeName;
    private List<GameObject> projectiles;

    public GameObject(int id, double xPosition, double yPosition, double width, double height, String imageFileName,
                      Map<String, String> properties) {
        this(id, xPosition, yPosition, width, height, properties);
        this.imageFileName = imageFileName;
    }

    public GameObject(double xPosition, double yPosition, double width, double height, String imageFileName,
                      Map<String, String> properties) {
        this(0, xPosition, yPosition, width, height, properties);
        this.imageFileName = imageFileName;
    }

    public GameObject(int id, double xPosition, double yPosition, double width, double height, Map<String, String> properties) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.properties = properties;
        projectiles = new ArrayList<>();
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public Direction getDirection(){
        return direction;
    }

    public String getProperty(String propertyName) {
        String val = properties.get(propertyName);
        return val;
    }

    public void setPlatformCharacterIsOn(GameObject platform){
        platformCharacterIsOn = platform;
    }

    public GameObject getPlatformCharacterIsOn(){
        return platformCharacterIsOn;
    }
    public void setPlatformStatus(boolean status){
        this.onPlatform = status;
    }
    public boolean isOnPlatform(){
        return onPlatform;
    }
    public void checkPlatformStatus(){
        if(platformCharacterIsOn == null){
            this.onPlatform = false;
            return;
        }
        boolean isHorizontallyOnPlatform = (SingletonBoundaryChecker.getInstance().
                getHorizontalIntersectionAmount(this,platformCharacterIsOn) != IntersectionAmount.NOT_INTERSECTING);
        boolean isVerticallyOnPlatform = (((this.yPosition + this.height) <=
                (platformCharacterIsOn.getYPosition() + 20)) && ((this.yPosition + this.height) >=
                (platformCharacterIsOn.getYPosition())));
        this.onPlatform = isHorizontallyOnPlatform && isVerticallyOnPlatform;
    }

    public void setProperty(String propertyName, String propertyValue) {
        properties.put(propertyName, propertyValue);
    }
    public String getImageFileName() {
        return imageFileName;
    }
    public Map<String, String> getProperties(){
        return properties;
    }
    public Set<String> getPropertiesList() {
        return properties.keySet();
    }
    public void setPropertiesList(Map<String, String> properties) {
        this.properties = properties;
    }
    public void killSpeed(){
        setProperty("fallspeed", "0.0");
    }
    public double getXPosition() {
        return xPosition;
    }
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }
    public double getYPosition() {
        return yPosition;
    }
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getXDistanceMoved(){
        return xDistanceMoved;
    }
    public double getYDistanceMoved(){
        return yDistanceMoved;
    }
    public void setXDistanceMoved(double xDistanceMoved){
        this.xDistanceMoved = xDistanceMoved;
    }
    public void setYDistanceMoved(double yDistanceMoved){
        this.yDistanceMoved = yDistanceMoved;
    }
    public void setProjectileProperties(ProjectileProperties projectileProperties){
        this.projectileProperties = projectileProperties;
    }
    public ProjectileProperties getProjectileProperties(){
        return projectileProperties;
    }
    public boolean isPlayer(){
        return isPlayer;
    }
    public void setIsPlayer(boolean value){
        //System.out.println(" boolean: " + value);
        isPlayer = value;
    }

    public List getProjectiles(){
        return projectiles;
    }

    public void setTypeName(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName(){
        return this.typeName;
    }
}