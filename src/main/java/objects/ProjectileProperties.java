package objects;
import com.sun.javafx.scene.traversal.Direction;

/**
 * Created by Soravit on 12/6/2016.
 */
public class ProjectileProperties {
    String imageFileName;
    private double width;
    private double height;
    private Direction direction;
    private double range;
    private double speed;
    private double damage;
    private double timeBetweenShots;

    public ProjectileProperties(String imageFileName, int width, int height, Direction direction, double range, double speed, double damage, double timeBetweenShots){
        this.imageFileName = imageFileName;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.timeBetweenShots = timeBetweenShots;
    }

    public double getTimeBetweenShots() {
        return timeBetweenShots;
    }

    public void setTimeBetweenShots(double timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getRange() {
        return range;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        return speed;
    }

    public void setImageFileName(String imageFileName){
        this.imageFileName = imageFileName;
    }
}