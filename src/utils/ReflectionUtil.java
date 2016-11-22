package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectionUtil {
	
	public static Object newInstanceOf(String className) 
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException, NoSuchMethodException, SecurityException{
		Class<?> c = Class.forName(className);
		Constructor<?> constructor = c.getConstructor();
		return constructor.newInstance();
	}
	
	public static Method getMethodFromClass(String className, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException, SecurityException, ClassNotFoundException{
		
		Class<?> c = Class.forName(className);
		return c.getMethod(methodName, parameterTypes);
	}
}