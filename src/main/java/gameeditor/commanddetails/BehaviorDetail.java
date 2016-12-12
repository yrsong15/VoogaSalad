package gameeditor.commanddetails;

import com.sun.javafx.scene.traversal.Direction;
import gameeditor.view.ViewResources;
import gameengine.model.boundary.StopAtEdgeBoundary;
import gameengine.model.boundary.ToroidalBoundary;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import objects.ScrollType;

public class BehaviorDetail extends AbstractCommandDetail implements IBehaviorDetail{
    //private VBox myVBox;
    private Menu scrollTypeMenu;
    private TextArea scrollWidthTextBox ;
    private TextArea myTimeWin;
    private TextArea myScreenHeight;
    private TextArea myPointsWin;
    private TextArea scrollSpeedTextBox;
    private BorderPane myLimitWidthOption;
    private ComboBox<String> gameBoundaryOptions;
    private BorderPane myScrollWidthBP;
    
    public BehaviorDetail() {
        super();
    }

    public void init() {
        addVBoxSettings();
        addLevelOptions();
    }

    private void addLevelOptions(){
        addScrollTypeOptions();
        createWinConditions();
        addScrollSpeed();
        addGameBoundary();
        addWidthOptions();
        addHeight();
        createSave();
    }

    private void createSave(){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",e -> handleSave());
        myVBox.getChildren().add(save);
    }

    private void addGameBoundary(){
        Label label = createPropertyLbl("Game Boundary");
        gameBoundaryOptions = myDetailFrontEndUtil.createComboBox(GAME_BOUNDARY_OPTIONS, DetailDefaultsResources.GAME_BOUNDARY.getResource());
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(gameBoundaryOptions,label);
        myVBox.getChildren().add(bp);
    }

    private void addScrollSpeed(){
        scrollSpeedTextBox = createInputField(String.valueOf(DetailDefaultsResources.DEFAULT_SCROLL_SPEED.getDoubleResource()));
        BorderPane scrollSpeed = myDetailFrontEndUtil.createBorderpane(scrollSpeedTextBox,createPropertyLbl(SCROLL_SPEED_LABEL));
        myVBox.getChildren().add(scrollSpeed);
    }

    private void createWinConditions(){
        myTimeWin = createInputField(String.valueOf(DetailDefaultsResources.DEFAULT_TIME_VALUE.getDoubleResource()));
        myVBox.getChildren().add(myDetailFrontEndUtil.createBorderpane(myTimeWin,createPropertyLbl(TIME_PROPERTY_LABEL)));
        myPointsWin = createInputField(String.valueOf(DetailDefaultsResources.DEFAULT_POINTS_VALUE.getDoubleResource())); 
        myVBox.getChildren().add(myDetailFrontEndUtil.createBorderpane(myPointsWin,createPropertyLbl(POINTS_PROPERTY_LABEL)));
    }

    private void addScrollTypeOptions(){
        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(150);
        scrollTypeMenu = new Menu(SCROLL_TYPE_LABEL);
        Menu limitedScrollSubMenu = createDirectionSubMenu(FORCED_SCROLL_TYPE_LABEL);
        Menu forcedScrollSubMenu = createDirectionSubMenu(LIMITED_SCROLL_TYPE_LABEL);
        MenuItem freeScrollType = createMenuItem(FREE_SCROLL_TYPE_LABEL);

        addFreeScrollTypeListener(freeScrollType);
        addScrollTypeListener(LIMITED_SCROLL_TYPE,limitedScrollSubMenu);
        addScrollTypeListener(FORCED_SCROLL_TYPE,forcedScrollSubMenu);

        scrollTypeMenu.getItems().addAll(limitedScrollSubMenu,forcedScrollSubMenu, freeScrollType);
        menuBar.getMenus().add(scrollTypeMenu);
        myVBox.getChildren().add(menuBar);
    }

    private void addWidthOptions(){
        ComboBox<String>limitDimension =myDetailFrontEndUtil. createComboBox(LIMIT_DIMENSION_OPTIONS,DetailDefaultsResources.LIMIT_WIDTH.getResource() );
        myLimitWidthOption = myDetailFrontEndUtil.createBorderpane(limitDimension,createPropertyLbl(LIMIT_SCROLL_WIDTH_LABEL));
        myVBox.getChildren().add(myLimitWidthOption);
        limitDimension.setOnAction(e-> cbOnAction(limitDimension));
    }

    private void cbOnAction(ComboBox<String> cb){
        if (cb.getValue().equals(TRUE)){
            scrollWidthTextBox = createInputField(Double.toString(ViewResources.AREA_WIDTH.getDoubleResource()));
            myScrollWidthBP = myDetailFrontEndUtil.createBorderpane(scrollWidthTextBox,createPropertyLbl(SCROLL_WIDTH_LABEL));
            int index = myVBox.getChildren().indexOf(myLimitWidthOption);
            myVBox.getChildren().add(index+1, myScrollWidthBP);

        } else if((cb.getValue().equals("False"))){
            if(myVBox.getChildren().contains(myScrollWidthBP)){
                myVBox.getChildren().remove(myVBox.getChildren().indexOf(myLimitWidthOption)+1);
            }
        }
    }

    private void addHeight(){
        myScreenHeight = createInputField(String.valueOf(ViewResources.SCROLL_PANE_HEIGHT.getDoubleResource()));
        BorderPane scrollheight = myDetailFrontEndUtil.createBorderpane(myScreenHeight,createPropertyLbl(SCROLL_HEIGHT_LABEL));
        myVBox.getChildren().add(scrollheight);
    }
    private void handleSave(){
        myDataStore.addWinCondition(POINTS_PROPERTY, myPointsWin.getText());
        myDataStore.addLoseCondition(TIME_PROPERTY, myTimeWin.getText());
        Double width=0.0;
        if(scrollWidthTextBox==null){
            width = Double.MAX_VALUE;
        }else{
            width = Double.valueOf(scrollWidthTextBox.getText());
        }

        Double height = Double.valueOf(myScreenHeight.getText());
        addGameBoundary(width,height);
    }


    private void addGameBoundary(double width, double height){
        if(gameBoundaryOptions.getValue().equals(GAME_BOUNDARY_OPTIONS[0])){
            // Toroidal
            ToroidalBoundary boundary = new ToroidalBoundary(width,height);
            myDataStore.addGameBoundary(boundary);
        } else {
            StopAtEdgeBoundary boundary = new StopAtEdgeBoundary(width,height);
            myDataStore.addGameBoundary(boundary);

        }

    }
    
    private boolean verifySave(){
        // TODO: Verify if right values entered
        return true;
    }

    private Menu createDirectionSubMenu(String type){
        Menu m = new Menu(type);
        String[] directionOptions = DetailResources.SCROLL_DIRECTIONS_OPTIONS.getArrayResource();
        for(String direction: directionOptions){
            m.getItems().addAll(createMenuItem(direction));
        }
        return m;
    }

    private MenuItem createMenuItem(String property){
        return new MenuItem(property);
    }

    private void addScrollTypeListener(String className, Menu myMenu){
        ScrollType myScrollType = new ScrollType(className);
        myMenu.getItems().stream().forEach(item -> {
            item.setOnAction(e -> {
                myScrollType.addScrollDirection(Direction.valueOf(item.getText()));
                myDataStore.addScrollType(myScrollType);
            });
        });
    }

    private void addFreeScrollTypeListener(MenuItem item){
        item.setOnAction(e -> {
            ScrollType myScrollType = new ScrollType(FREE_SCROLL_TYPE);
            myScrollType.addScrollDirection(Direction.LEFT); 
            myScrollType.addScrollDirection(Direction.RIGHT); 
            myScrollType.addScrollDirection(Direction.UP); 
            myScrollType.addScrollDirection(Direction.DOWN); 
        });
    }
}
