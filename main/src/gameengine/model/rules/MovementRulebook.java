package gameengine.model.rules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Soravit on 11/22/2016.
 */
public class MovementRulebook {

    private ResourceReader resources;

    public MovementRulebook() {
        resources = new ResourceReader("GameEngineMovementProperties");
    }

    public void applyRules(GameObject obj) throws ClassNotFoundException, InstantiationException {
    	if(obj.getProperty("fallspeed")==null) obj.setProperty("fallspeed", "0");
////    	Iterator<String> itr = obj.getPropertiesList().iterator();
//    	String[] propertiesArray = Collection.toArray(obj.getPropertiesList());
//    	while ( itr.hasNext()) {
    	for (Iterator<String> itr = obj.getPropertiesList().iterator(); itr.hasNext();) {
    	String property = itr.next();
            if(resources.containsResource(property)) {
                String ruleName = resources.getResource(property);
                ruleName = "gameengine.model.rules.movementrules." + ruleName;
                try {
                    Object o = ReflectionUtil.newInstanceOf(ruleName);
                    Method method = ReflectionUtil.getMethodFromClass(ruleName, "applyRule", GameObject.class);
                    method.invoke(o, obj);

                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | ClassNotFoundException | NoSuchMethodException
                        | SecurityException e) {
                    e.printStackTrace();
                    //System.out.print(ruleName);
                    //throw new ClassNotFoundException();
                }
            }
        }
    }
}
