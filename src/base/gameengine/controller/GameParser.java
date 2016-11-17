package base.gameengine.controller;

import base.gameengine.controller.designinterfaces.Parser;
import com.sun.xml.internal.ws.org.objectweb.asm.Attribute;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * @author ericsong
 *
 */
public class GameParser implements Parser {

	public GameParser() {

	}
	

	@Override
	public void processXML(String fileName) {
		XMLParser xmlParser = new XMLParser();
		Element element = xmlParser.getRootElement(fileName);

	}

	private void instantiateNode(Node child, Object parent) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        NodeList childNodes = child.getChildNodes();
        Class<?> nodeClass = Class.forName(child.getNodeName());
        Object obj = nodeClass.newInstance();
        Method method = nodeClass.getDeclaredMethod("init");
        method.invoke(obj, parent);
        NamedNodeMap namedNodeMap = child.getAttributes();
        Map<String, String> stringMap = (Map<String, String>) namedNodeMap;
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            Field f = obj.getClass().getField(entry.getKey());
            f.setAccessible(true);
            f.setBoolean(obj, Boolean.parseBoolean(entry.getValue()));
        }
        for(int i = 0; i < childNodes.getLength(); i++){
            Node currNode = childNodes.item(i);
            instantiateNode(currNode, obj);
        }
    }

}
