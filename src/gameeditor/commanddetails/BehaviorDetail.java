package gameeditor.commanddetails;
import java.util.Map;
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

public class BehaviorDetail extends AbstractCommandDetail implements IBehaviorDetail {


    private VBox myVBox;
    private Menu scrollTypeMenu;
    private TextArea scrollWidthTextBox ;
    private TextArea myTimeWin;
    private TextArea myPointsWin;
    private Map<String,String> myLevelData;
    private BorderPane myScrollWidthBP;


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
    }

    private void createWinConditions(){
        myTimeWin = addTextInputBP(TIME_PROPERTY_LABEL, "0.0");
        myPointsWin = addTextInputBP(POINTS_PROPERTY_LABEL, "0.0");    
    }

    public void createSave(){
        Button save = new Button();
        save.setText("");
        save.setMinWidth(cbWidth);
        save.setMinHeight(cbHeight);
        save.setOnAction((e) -> {handleSave();});
        myVBox.getChildren().add(save);
    }

    private void addScrollTypeOptions(){
        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(150);
        scrollTypeMenu = new Menu(SCROLL_TYPE_LABEL);
        Menu limitedScrollSubMenu = createDirectionSubMenu(FORCED_SCROLL_TYPE_LABEL);
        Menu forcedScrollSubMenu = createDirectionSubMenu(LIMITED_SCROLL_TYPE_LABEL);
        MenuItem freeScrollType = createMenuItem(FREE_SCROLL_TYPE_LABEL);
        scrollTypeMenu.getItems().addAll(limitedScrollSubMenu,forcedScrollSubMenu, freeScrollType);
        menuBar.getMenus().add(scrollTypeMenu);
        myVBox.getChildren().add(menuBar);
    }

    private void addWidthOptions(){
        ComboBox<String>limitDimension = createComboBox(DetailResources.LIMIT_DIMENSION_OPTIONS.getArrayResource());
        myScrollWidthBP = createBorderpane(limitDimension,createLabel(LIMIT_SCROLL_WIDTH_LABEL));
        myVBox.getChildren().add(myScrollWidthBP);
        limitDimension.setOnAction(e-> cbOnAction(limitDimension));
    }

    public void cbOnAction(ComboBox<String> cb){
        if (cb.getValue().equals(TRUE)){
            scrollWidthTextBox = createInputField(Double.toString(ViewResources.AREA_WIDTH.getDoubleResource()));
            BorderPane bp = createBorderpane(scrollWidthTextBox,createLabel(SCROLL_WIDTH_LABEL));
            int index = myVBox.getChildren().indexOf(myScrollWidthBP);
            myVBox.getChildren().add(index+1, bp);
        } else {
            myVBox.getChildren().remove(myVBox.getChildren().indexOf(myScrollWidthBP)+1);
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

    private TextArea addTextInputBP(String label, String initValue){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(paddedPaneWidth);
        bp.setMaxWidth(paddedPaneWidth);
        Label labl = createLabel(label);
        TextArea text = createInputField(initValue);
        bp.setLeft(labl);
        bp.setRight(text);
        BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
        // Add in the VBox
        myVBox.getChildren().add(bp);
        return text;
    }

    public void handleSave(){
        myLevelData.put(TIME_PROPERTY,myTimeWin.getText());
        myLevelData.put(POINTS_PROPERTY,myTimeWin.getText());
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

}
