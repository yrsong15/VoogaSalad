package gameeditor.commanddetails;

import java.util.ArrayList;
import com.sun.javafx.scene.traversal.Direction;
import gameeditor.view.ViewResources;
import gameengine.model.boundary.BasicBoundary;
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
/**
 * @ author Pratiksha Sharma, John Martin
 *
 */
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
    private String scrollTypeClass = FREE_SCROLL_TYPE;
    private ArrayList<Direction> scrollTypeDirections;
    
    public BehaviorDetail() {
        super();
    }

    public void init() {
        addVBoxSettings();
        addLevelOptions();
        scrollTypeDirections = new ArrayList<Direction>();
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
        Label label = myDetailFrontEndUtil.createPropertyLbl("Game Boundary");
        gameBoundaryOptions = myDetailFrontEndUtil.createComboBox(GAME_BOUNDARY_OPTIONS, DetailDefaultsResources.GAME_BOUNDARY.getResource());
        gameBoundaryOptions.setMaxWidth(CB_WIDTH*1.2);
        gameBoundaryOptions.setMinWidth(CB_WIDTH*1.2);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(gameBoundaryOptions,label);
        myVBox.getChildren().add(bp);
    }

    private void addScrollSpeed(){
        scrollSpeedTextBox = myDetailFrontEndUtil.createInputField(DetailDefaultsResources.DEFAULT_SCROLL_SPEED.getResource());
        BorderPane scrollSpeed = myDetailFrontEndUtil.createBorderpane(scrollSpeedTextBox,myDetailFrontEndUtil.createPropertyLbl(SCROLL_SPEED_LABEL));
        myVBox.getChildren().add(scrollSpeed);
    }

    private void createWinConditions(){
        myTimeWin = myDetailFrontEndUtil.createInputField(DetailDefaultsResources.DEFAULT_TIME_VALUE.getResource());
        myVBox.getChildren().add(myDetailFrontEndUtil.createBorderpane(myTimeWin,myDetailFrontEndUtil.createPropertyLbl(TIME_PROPERTY_LABEL)));
        myPointsWin = myDetailFrontEndUtil.createInputField(DetailDefaultsResources.DEFAULT_POINTS_VALUE.getResource()); 
        myVBox.getChildren().add(myDetailFrontEndUtil.createBorderpane(myPointsWin,myDetailFrontEndUtil.createPropertyLbl(POINTS_PROPERTY_LABEL)));
    }

    private void addScrollTypeOptions(){
        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(150);
        scrollTypeMenu = new Menu(SCROLL_TYPE_LABEL);
        Menu limitedScrollSubMenu = createDirectionSubMenu(LIMITED_SCROLL_TYPE_LABEL);
        Menu forcedScrollSubMenu = createDirectionSubMenu(FORCED_SCROLL_TYPE_LABEL);
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
        myLimitWidthOption = myDetailFrontEndUtil.createBorderpane(limitDimension,myDetailFrontEndUtil.createPropertyLbl(LIMIT_SCROLL_WIDTH_LABEL));
        myVBox.getChildren().add(myLimitWidthOption);
        limitDimension.setOnAction(e-> cbOnAction(limitDimension));
    }

    private void cbOnAction(ComboBox<String> cb){
        if (cb.getValue().equals(TRUE)){
            scrollWidthTextBox = myDetailFrontEndUtil.createInputField(Double.toString(ViewResources.AREA_WIDTH.getDoubleResource()));
            myScrollWidthBP = myDetailFrontEndUtil.createBorderpane(scrollWidthTextBox,myDetailFrontEndUtil.createPropertyLbl(SCROLL_WIDTH_LABEL));
            int index = myVBox.getChildren().indexOf(myLimitWidthOption);
            myVBox.getChildren().add(index+1, myScrollWidthBP);

        } else if((cb.getValue().equals("False"))){
            if(myVBox.getChildren().contains(myScrollWidthBP)){
                myVBox.getChildren().remove(myVBox.getChildren().indexOf(myLimitWidthOption)+1);
            }
        }
    }

    private void addHeight(){
        myScreenHeight = myDetailFrontEndUtil.createInputField(String.valueOf(ViewResources.SCROLL_PANE_HEIGHT.getDoubleResource()));
        BorderPane scrollheight = myDetailFrontEndUtil.createBorderpane(myScreenHeight,myDetailFrontEndUtil.createPropertyLbl(SCROLL_HEIGHT_LABEL));
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
        addScrollType(width,height);
        
    }


    private void addScrollType(double width, double height){
        BasicBoundary boundary;
        if(gameBoundaryOptions.getValue().equals(GAME_BOUNDARY_OPTIONS[0])){
            // Toroidal
             boundary = new ToroidalBoundary(width,height,width,height);
            //myDataStore.addGameBoundary(boundary);
        } else {
             boundary = new StopAtEdgeBoundary(width,height);
            //myDataStore.addGameBoundary(boundary);
        }
        ScrollType myScrollType = new ScrollType(scrollTypeClass,boundary);
        myScrollType.addDirectionList(scrollTypeDirections);  
        myDataStore.addScrollType(myScrollType);
    }

    @SuppressWarnings("unused")
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
        myMenu.getItems().stream().forEach(item -> {
            item.setOnAction(e -> {
                scrollTypeClass = className;
                scrollTypeDirections.add(Direction.valueOf(item.getText()));
            });
        });
    }

    private void addFreeScrollTypeListener(MenuItem item){
        item.setOnAction(e -> {
            scrollTypeClass = FREE_SCROLL_TYPE;
            scrollTypeDirections.clear();
            scrollTypeDirections.add(Direction.LEFT); 
            scrollTypeDirections.add(Direction.RIGHT); 
            scrollTypeDirections.add(Direction.UP); 
            scrollTypeDirections.add(Direction.DOWN); 
        });
    }
}
