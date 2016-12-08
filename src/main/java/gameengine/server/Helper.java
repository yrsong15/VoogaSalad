package gameengine.server;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gameengine.client.ServerMessage;

public class Helper {
	
	public static ServerMessage unmarshall(String data) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(ServerMessage.class);
		
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StringReader sr = new StringReader(data);
		
		return (ServerMessage) unmarshaller.unmarshal(sr);
	}
}
