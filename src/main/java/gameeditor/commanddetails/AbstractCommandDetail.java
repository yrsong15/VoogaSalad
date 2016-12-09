package gameeditor.commanddetails;


import frontend.util.ButtonTemplate;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.ViewResources;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;

public abstract class AbstractCommandDetail  implements IAbstractCommandDetail{
    protected IDetailPane myDetailPane;
    protected ScrollPane myContainerPane;
    protected IGameEditorData myDataStore;
    protected IDesignArea myDesignArea;
    protected VBox myVBox;

    public AbstractCommandDetail() {
        myVBox = new VBox();
        myContainerPane = new ScrollPane();
        myContainerPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        myContainerPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        myContainerPane.setMinWidth(MY_PANE_WIDTH);
        myContainerPane.setMaxWidth(MY_PANE_WIDTH);
        myContainerPane.setMinHeight(MY_PANE_HEIGHT);
        myContainerPane.setMaxHeight(MY_PANE_HEIGHT);
        myContainerPane.setHmax(0);
        myContainerPane.setLayoutX(DETAIL_PANE_WIDTH/2 - MY_PANE_WIDTH/2 + 10);
        myContainerPane.setLayoutY(DETAIL_PADDING);
        myContainerPane.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), 
                                                                        CornerRadii.EMPTY, Insets.EMPTY)));
        myContainerPane.getStylesheets().add("gameeditor/commanddetails/DetailPane.css");
    }

    public abstract  void init();

    public void setDataStore(IGameEditorData ged){
        myDataStore = ged;
    }


    public void setDesignArea(IDesignArea da){
        myDesignArea = da;
    }

    public ScrollPane getPane(){
        return myContainerPane;
    }

    public void setDetailPane(IDetailPane idp) {
        myDetailPane = idp;
    }

    protected void addVBoxSettings(){
        myVBox.setSpacing(MY_DETAIL_PADDING);
        myVBox.setAlignment(Pos.CENTER);
        myContainerPane.setContent(myVBox);    
    }

    protected TextArea createInputField(String initValue){
        TextArea inputField = new TextArea();
        inputField.setMinWidth(PADDED_DETAIL_WIDTH);
        inputField.setMaxWidth(PADDED_DETAIL_WIDTH);
        inputField.setMinHeight(CB_HEIGHT);
        inputField.setMaxHeight(CB_HEIGHT);
        inputField.setText(initValue);
        inputField.setOnMouseClicked(e -> handleClick(inputField));
        return inputField;
    }

    protected BorderPane createBorderpane(Node right, Node left){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(PADDED_PANE_WIDTH);
        bp.setMaxWidth(PADDED_PANE_WIDTH);
        bp.setLeft(left);
        bp.setRight(right);
        return bp; 
    }
    
    protected ComboBox<String> createComboBox(String [] boxOptions){
        ComboBox<String> cb = new ComboBox<String>();
        cb.getItems().addAll(boxOptions);
        cb.setMinWidth(CB_WIDTH);
        cb.setMaxWidth(CB_WIDTH);
        cb.setMinHeight(CB_HEIGHT);
        cb.setMaxHeight(CB_HEIGHT);
        return cb;
    } 

    protected Button createButton(String property, EventHandler<MouseEvent> handler){
        ButtonTemplate button = new ButtonTemplate(property);
        button.getButton().setMinHeight(CB_HEIGHT);
        button.getButton().setMinWidth(CB_WIDTH);
        button.setOnButtonAction(handler);
        return button.getButton();
    }

    protected void handleClick(TextArea field){
        field.setText("");
    }

    protected Label createPropertyLbl(String property){
        return  new Label (property);
    }
}
