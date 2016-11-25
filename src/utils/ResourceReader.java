package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * @author Chalena Scholl
 * This creates a resource bundle reader, given a properties file to read from.
 */
public class ResourceReader {
		
	 private ResourceBundle myResources;
	 public String myFile;
	 
	 public ResourceReader(String fileLocation){
		 myResources = ResourceBundle.getBundle(fileLocation);
		 myFile = fileLocation;
	 }
	 
	 public Iterator<String> getKeys(){
		ArrayList<String> keysList = Collections.list(myResources.getKeys()); 
		 return keysList.iterator();
	 }
	 
	 public String getResource(String value){
		 return myResources.getString(value);
	 }
	 
	 public Boolean containsResource(String value){
		 return myResources.containsKey(value);
	 }
}

