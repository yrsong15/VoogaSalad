package objects;
public class Position {
    private double xCoord;
    private double yCoord;

    public void setPosition(double xCoord, double yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public double getX(){
        return this.xCoord;
    }

    public double getY(){
        return this.yCoord;
    }
}