package usecases.mockObjects;

/**
 * 
 * @author Ray Song(ys101)
 *
 */
public class UseCaseGObject {
	String name;
	UseCaseBehavior myBehavior;
	
	public void setBehavior(UseCaseBehavior myBehavior){
		this.myBehavior = myBehavior;
	}
}
