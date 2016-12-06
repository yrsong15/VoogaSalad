package objects;

public class PlatformGameBlock extends AbstractGameBlock{
	public PlatformGameBlock(double xPosition, double yPosition, double width, double height, String imageFileName){
		super(xPosition, yPosition, width, height, imageFileName);
		west = false;
		east = false;
		south = true;
		north = false;
		isBreakable = false;
	}
}
