package gameeditor.controller.interfaces;

import java.util.Map;

/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface IMapManager {
	public void createMap();
	public void addToMap(String key, String value);
	public Map<?,?> getCurrentMap();
}
