package exception;

public class ClassNotFoundException extends Exception {
	
	public ClassNotFoundException() {
		
	}

	public ClassNotFoundException(String inClass) {
		super(inClass);
	}

	private static final long serialVersionUID = -6180712680373051087L;

}
