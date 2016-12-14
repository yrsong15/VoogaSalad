package xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Eric Song(ess42)
 *
 */
public class XMLTrimmer {

	public static String trim(String input) {
	    BufferedReader reader = new BufferedReader(new StringReader(input));
	    StringBuffer result = new StringBuffer();
	    try {
	        String line;
	        while ( (line = reader.readLine() ) != null)
	            result.append(line.trim());
	        return result.toString();
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}

}
