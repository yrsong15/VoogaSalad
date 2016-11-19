package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ReflectionUtil {
	
	public static Object newInstanceOf(String className) 
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException, NoSuchMethodException, SecurityException{
		Class<?> c = Class.forName(className);
		Constructor<?> constructor = c.getConstructor();
		return constructor.newInstance();
	}
}