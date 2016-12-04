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

import com.sun.javafx.scene.traversal.Direction;

import exception.MovementRuleNotFoundException;

/**
 * Created by Soravit on 11/22/2016.
 * Other contributors: Chalena
 */
public class MovementRulebook {

    private ResourceReader resources;
    private static final String resourcesPath = "GameEngineMovementProperties";
    private static final String rulesPath = "gameengine.model.rules.movementrules.";

    public MovementRulebook() {
        resources = new ResourceReader(resourcesPath);
    }

    public void applyRules(GameObject obj) throws MovementRuleNotFoundException{
    	if(obj.getProperty("fallspeed")==null) obj.setProperty("fallspeed", "0");
    	for (Iterator<String> itr = obj.getPropertiesList().iterator(); itr.hasNext();) {
    	String property = itr.next();
            if(resources.containsResource(property)) {
                String ruleName = rulesPath + resources.getResource(property);
        		Object[] parameters = new Object[]{obj};
        		Class<?>[] parameterTypes = new Class<?>[]{GameObject.class};
                try {
					ReflectionUtil.runMethod(ruleName, "applyRule", parameters, parameterTypes);
				} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw (new MovementRuleNotFoundException());
				}
            }
        }
    }
}
