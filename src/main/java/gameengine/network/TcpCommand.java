package gameengine.network;

public enum TcpCommand {
	GET_ID, 
	SEND_COMMAND, 
	GET_ID_IP_PORT,
	REMOVE_CHARACTER,
	PAUSE, 
	RESTART, 
	SERVER_THREAD_SHUTDOWN;
}
