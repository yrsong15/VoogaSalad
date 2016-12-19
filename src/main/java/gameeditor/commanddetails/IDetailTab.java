package gameeditor.commanddetails;

import javafx.scene.layout.VBox;

//This entire file is part of my masterpiece.
//Pratiksha Sharma
/**
 * This interface is implemented in SpriteDetail and ProjectileDetail classes.
 * The method retuns a VBox containing the contents for the specific tab.
 * @author pratikshasharma
 *
 */
public interface IDetailTab {
    public VBox getTabContent();

}
