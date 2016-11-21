package gameengine.model.rules.settingsrules;

import objects.Level;

/**
 * Created by Soravit on 11/21/2016.
 */
public class ModifyTimeRule extends SettingsRule{


    @Override
    public void applyRule(Level level, int num) {
        level.setTime(level.getTime() + num);
    }
}
