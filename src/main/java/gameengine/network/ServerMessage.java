// This entire file is part of my masterpiece.
// Ray Song (ys101)
// The changes made to ServerMessage display my understanding of basic encapsulation principles in Java,
// as well as the use of enums to replace public static variables.

package gameengine.network;

public class ServerMessage{
	private TcpCommand message;
	
	private String command;
	private long id;
	private int port;
	private int charIdx;
	
	public ServerMessage(TcpCommand message){
		this.message = message;
	}
	
	public TcpCommand getMessage(){
		return message;
	}
	
	public void setCommand(String cmd){
		command = cmd;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setCharIdx(int idx){
		charIdx = idx;
	}
	
	public String getCommand(){
		return command;
	}
	
	public long getId(){
		return id;
	}
	
	public int getPort(){
		return port;
	}
	
	public int getCharIdx(){
		return charIdx;
	}
}
