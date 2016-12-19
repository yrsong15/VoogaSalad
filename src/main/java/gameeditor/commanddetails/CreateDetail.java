package gameeditor.commanddetails;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

// This entire file is part of my masterpiece.
//Pratiksha Sharma
// This class is called by the DetailFactory.java class when the Button for creating game objects is clicked in the command pane.
// DetailFactory uses reflection to call this class, which encapsulates the implementation of this class from the main view class.
// It represents the use of composition instead of using inheritance structure. This class contains instances of Projectile Detail 
// and SpriteDetail classes. It also shows the use of lambda expression. It also implements the Single Responsibility Principle and Open closed principle
//of the SOLID design principles. 
// It fulfills its' responsibility of populating the Command Pane with the options for Game objects and Projectile.
//This class follows the open closed principle, as it does
// not allow room for modifications within the class, however, leaves room for extension (the AbstractCommandDetail class contains getters and setters that provide access to objects like containerPane, etc.)
// It exhibits the use of "Keep it dry,shy and tell the other guy" design principle by delegating tasks to the ProjectileDetail
// and SpriteDetail class rather than doing it on its own. The data in this class is well encapsulated as the only API it provides 
// to any other classes is the method to set content of the container pane. T


/**
 * @author Pratiksha Sharma, John Martin
 *The purpose of this class is to add Tabs for setting properties to game objects (sprites) and Projectiles.
 *
 */
public class CreateDetail extends AbstractCommandDetail {
    private TabPane myTabPane;
    private Tab mySpriteTab;
    private Tab myProjectileTab;
    private SpriteDetail mySpriteDetail;
    private ProjectileDetail myProjectileDetail;
 

    public CreateDetail() {
        super();
    }

    @Override
    /**
     * sets the content of the Detail pane to the Vbox 
     */
    public void init() {      
        myTabPane = new TabPane();
        createGameObjectsTab();
        createProjectileTab();
        myTabPane.getTabs().addAll(mySpriteTab,myProjectileTab);
        myContainerPane.setContent(myTabPane);
    }

    private void createGameObjectsTab(){
        mySpriteDetail = new SpriteDetail(myDataStore);
        mySpriteTab = new Tab(DetailResources.GAME_OBJECTS_LABEL.getResource()); 
        mySpriteTab.setOnSelectionChanged(e-> setSpriteTab());
    }

    private void createProjectileTab(){
        myProjectileTab = new Tab(DetailResources.PROJECTILE_LABEL.getResource());
        myProjectileDetail = new ProjectileDetail(myDataStore);    
        myProjectileTab.setOnSelectionChanged(e-> setProjectileTab());
    }
    

    private void setSpriteTab(){
        if(mySpriteTab.isSelected()){ 
            VBox vbox = mySpriteDetail.getTabContent();
            mySpriteTab.setContent(vbox);
        }
    }

    private void setProjectileTab(){
        if(myProjectileTab.isSelected()){
            myProjectileTab.setContent(myProjectileDetail.getTabContent());
        }
    }
}

    
