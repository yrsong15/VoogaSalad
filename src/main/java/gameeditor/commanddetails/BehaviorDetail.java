package gameeditor.commanddetails;

import com.sun.javafx.scene.traversal.Direction;
import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import objects.ScrollType;

public class BehaviorDetail extends AbstractCommandDetail implements IBehaviorDetail {
    private VBox myVBox;
    private Menu scrollTypeMenu;
    private TextArea scrollWidthTextBox ;
    private TextArea myTimeWin;
    private TextArea myPointsWin;
    private TextArea scrollSpeedTextBox;
    private BorderPane myLimitWidthOption;
    private BorderPane myScrollWidthBP;
    public static final String DEFAULT_TIME_VALUE = "400";
    public static final String DEFAULT_POINTS_VALUE = "20";
    public static final String DEFAULT_SCROLL_SPEED = "10";


    public BehaviorDetail() {
        super();
    }

    public void init() {
        myVBox = new VBox();
        myVBox.setSpacing(myDetailPadding);
        myVBox.setAlignment(Pos.CENTER);
        myContainerPane.setContent(myVBox); 
        addLevelOptions();
    }

    private void addLevelOptions(){
        addScrollTypeOptions();
        addWidthOptions();
        createWinConditions();
        addScrollSpeed();
        createSave();
    }

    private void createSave(){
        Button save = new Button(SAVE_BUTTON_LABEL);
        save.setMinHeight(cbHeight);
        save.setMinWidth(cbWidth);
        save.setOnMouseClicked((e) -> {handleSave();});
        myVBox.getChildren().add(save);

    }

    private void addScrollSpeed(){
        scrollSpeedTextBox = createInputField(DEFAULT_SCROLL_SPEED);
        BorderPane scrollSpeed = createBorderpane(scrollSpeedTextBox,createLabel(SCROLL_SPEED_LABEL));
        myVBox.getChildren().add(scrollSpeed);

    }

    private void createWinConditions(){
        myTimeWin = createInputField(DEFAULT_TIME_VALUE);
        myVBox.getChildren().add(createBorderpane(myTimeWin,createLabel(TIME_PROPERTY_LABEL)));
        myPointsWin = createInputField(DEFAULT_POINTS_VALUE); 
        myVBox.getChildren().add(createBorderpane(myPointsWin,createLabel(POINTS_PROPERTY_LABEL)));
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
        ComboBox<String>limitDimension = createComboBox(DetailResources.LIMIT_DIMENSION_OPTIONS.getArrayResource());
        myLimitWidthOption = createBorderpane(limitDimension,createLabel(LIMIT_SCROLL_WIDTH_LABEL));
        myVBox.getChildren().add(myLimitWidthOption);
        limitDimension.setOnAction(e-> cbOnAction(limitDimension));
    }

    public void cbOnAction(ComboBox<String> cb){
        if (cb.getValue().equals(TRUE)){
            scrollWidthTextBox = createInputField(Double.toString(ViewResources.AREA_WIDTH.getDoubleResource()));
            myScrollWidthBP = createBorderpane(scrollWidthTextBox,createLabel(SCROLL_WIDTH_LABEL));
            int index = myVBox.getChildren().indexOf(myLimitWidthOption);
            myVBox.getChildren().add(index+1, myScrollWidthBP);

        } else if((cb.getValue().equals("False"))){
            if(myVBox.getChildren().contains(myScrollWidthBP)){
            myVBox.getChildren().remove(myVBox.getChildren().indexOf(myLimitWidthOption)+1);
            }
        }
    }

    private BorderPane createBorderpane(Node right, Node left){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        bp.setLeft(left);
        bp.setRight(right);
        return bp; 
    }
    private ComboBox<String> createComboBox(String [] boxOptions){
        ComboBox<String> cb = new ComboBox<String>();
        cb.getItems().addAll(boxOptions);
        cb.setMinWidth(cbWidth);
        cb.setMaxWidth(cbWidth);
        cb.setMinHeight(cbHeight);
        cb.setMaxHeight(cbHeight);
        return cb;
    }

    private TextArea createInputField(String initValue){
        TextArea inputField = new TextArea();
        inputField.setMinWidth(paddedDetailWidth);
        inputField.setMaxWidth(paddedDetailWidth);
        inputField.setMinHeight(cbHeight);
        inputField.setMaxHeight(cbHeight);
        inputField.setText(initValue);
        inputField.setOnMouseClicked(e -> handleClick(inputField));
        return inputField;
    }

    private void handleClick(TextArea field){
        field.setText("");
    }

    public void handleSave(){
        myDataStore.addWinCondition(POINTS_PROPERTY, myPointsWin.getText());
        myDataStore.addLoseCondition(TIME_PROPERTY, myTimeWin.getText());
        myDataStore.addScrollWidth(scrollWidthTextBox.getText());
        myDataStore.addScrollSpeed(DEFAULT_SCROLL_SPEED);
        
    }

    private Label createLabel(String property){
        return new Label(property);
    }

    public boolean verifySave(){
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
