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

    public ProjectileProperties(String imageFileName, double width, double height, Direction direction, double range, double speed, double damage){
        this.imageFileName = imageFileName;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
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
