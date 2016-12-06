package gameeditor.controller;

import java.util.HashMap;
import java.util.Map;

import gameeditor.controller.interfaces.IMapManager;

/**
 * This is an intermediate controller that manages maps of the Game (properties, rules).
 * @author Ray Song(ys101)
 *
 */
public class MapManager implements IMapManager{
	
	Map<String, String> myMap;

	@Override
	public void createMap() {
		myMap = new HashMap<String,String>();
	}

	@Override
	public void addToMap(String key, String value){
		myMap.put(key, value);
	}

	@Override
	public Map<String,String> getCurrentMap() {
		return myMap;
	}

}
