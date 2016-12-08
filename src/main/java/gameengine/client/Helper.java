package gameengine.client;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * 
 * @author Titas Skrebe
 *
 * Helper class for (un)marshalling data
 * 
 */
public class Helper {
	
	
	/**
	 * Marshalls ServerMessage class to a string.
	 * 
	 * @param sm ServerMessage class
	 * @return an XML string 
	 * @throws JAXBException
	 */
	
	public static String marshall(ServerMessage sm) throws JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance(ServerMessage.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        StringWriter sw = new StringWriter();
        marshaller.marshal(sm, sw);
        
		return sw.toString();
	}


}