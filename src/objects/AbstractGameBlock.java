package objects;

/**
 * This is an abstract class that provides the template for the Game Blocks that will be used throughout VOOGA.
 * The four boolean values correspond to the four sides; "true" means character can pass through, "false" means impenetrable.
 * 
 * @author Ray Song
 *
 */
public abstract class AbstractGameBlock extends AbstractGameObject{
	
	protected boolean west;
	protected boolean east;
	protected boolean north;
	protected boolean south;
	protected boolean isBreakable;
	
	private String imageFileName;
	
	//TODO: How should we handle obstacles that have special properties? (ex: create mushrooms when hit from bottom) 
	public AbstractGameBlock(double xPosition, double yPosition, double width, double height, String imageFileName) {
		super(xPosition, yPosition, width, height);
		this.imageFileName = imageFileName;
	}
	
	public boolean getIsBreakable(){
		return isBreakable;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}

}
