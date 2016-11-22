package gameengine.model;

public class Condition {
	
	private String name;
	private int number;
	private String result;
	
	public Condition(String name, int total, String result){
		this.name = name;
		this.number = total;
		this.result = result;
	}
	
	public boolean equals(Condition other){
		return this.getName().equals(other.getName()) 
				&& this.getNumber()==other.getNumber();
		
	}
	
	protected String getName(){
		return name;
	}
	
	protected int getNumber(){
		return number;
	}
	
	protected String getResult(){
		return result;
	}
}
