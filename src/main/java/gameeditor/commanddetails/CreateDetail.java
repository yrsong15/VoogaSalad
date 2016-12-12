package gameeditor.commanddetails;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class CreateDetail extends AbstractCommandDetail {
    private TabPane myTabPane;
    private Tab mySpriteTab;
    private Tab myPlatformTab;
    private Tab myProjectileTab;

    private SpriteDetail mySpriteDetail;
    private PlatformDetail myPlatformDetail;
    private ProjectileDetail myProjectileDetail;

    public CreateDetail() {
        super();
    }

    @Override
    public void init() {      
        myTabPane = new TabPane();
        createSpriteTab();
        createPlatformTab();
        createProjectileTab();
        myTabPane.getTabs().addAll(mySpriteTab, myPlatformTab,myProjectileTab);
        myContainerPane.setContent(myTabPane);
    }

    private void createSpriteTab(){
        mySpriteDetail = new SpriteDetail(myDataStore);
        mySpriteTab = new Tab("Sprite"); 
        mySpriteTab.setOnSelectionChanged(e-> setSpriteTab());
    }

    private void createPlatformTab(){
        myPlatformDetail = new PlatformDetail(myDataStore);
        myPlatformTab = new Tab(PLATFORM_LABEL);
        myPlatformTab.setOnSelectionChanged(e -> setPlatformTab());  
    }

    private void createProjectileTab(){
        myProjectileTab = new Tab("Projectile");
        myProjectileDetail = new ProjectileDetail(myDataStore);    
        myProjectileTab.setOnSelectionChanged(e-> setProjectileTab());
    }
    
    private void setSpriteTab(){
        if(mySpriteTab.isSelected()){  
            mySpriteTab.setContent(mySpriteDetail.getTabContent());
        }
    }

    private void setPlatformTab(){
        if(myPlatformTab.isSelected()){
            myPlatformTab.setContent(myPlatformDetail.getTabContent());
        }
    }
    
    private void setProjectileTab(){
        if(myProjectileTab.isSelected()){
            myProjectileTab.setContent(myProjectileDetail.getTabContent());
        }
    }
}

    
