package gameengine.model.rules;

import objects.Level;

/**
 * Created by Soravit on 11/18/2016.
 */
public class SettingsRulebook {

    public SettingsRulebook(){

    }

    public void applyRules(Level level){
        for(String property: level.getExternalRules()){

        }
    }
}
