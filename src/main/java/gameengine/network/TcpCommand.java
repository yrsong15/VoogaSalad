// This entire file is part of my masterpiece.
// Ray Song (ys101)
// This is a Java enum that I have created to replace the static variables that are used throughout the TCP connection.
// Not only does it show my understanding of enums, which is a concept that we learned in lecture, 
// but it also shows my ability to implement new concepts in situations that had not been considered before.
// To give a high-level explanation of the my code masterpiece, I have used Java enums and interfaces to add a new
// functionality where a client thread can be cleanly deleted over the TCP connection.
// The hierarchy is as follows:
// ClientTcpConnection -> ServerTcpConnection -> ServerMain -> GameHandler(interface) -> GameEngineBackend. 
// Miscellaneous changes have also been made to Level.java to complete this functionality.

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
