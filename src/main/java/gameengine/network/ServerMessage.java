package gameengine.network;

public class ServerMessage{
	public int messageType;
	
	public String command;
	public long id;
	public int port;
	public int charIdx;
	
	public ServerMessage(){}
	
	public ServerMessage(int msgType){
		messageType = msgType;
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
	
}
