package gameeditor.commanddetails;

import frontend.util.ButtonTemplate;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class DetailFrontEndUtil implements IDetailFrontEndUtil{
    public BorderPane createBorderpane(Node right, Node left){
        BorderPane bp = new BorderPane();
        bp.setMinWidth(IAbstractCommandDetail.PADDED_PANE_WIDTH);
        bp.setMaxWidth(IAbstractCommandDetail.PADDED_PANE_WIDTH);
        bp.setLeft(left);
        bp.setRight(right);
        return bp; 
    }
    
    public ComboBox<String> createComboBox(String [] boxOptions, String defaultValue){
        ComboBox<String> cb = new ComboBox<String>();
        cb.getItems().addAll(boxOptions);
        cb.setValue(defaultValue);
        cb.setMinWidth(IAbstractCommandDetail.CB_WIDTH);
        cb.setMaxWidth(IAbstractCommandDetail.CB_WIDTH);
        cb.setMinHeight(IAbstractCommandDetail.CB_HEIGHT);
        cb.setMaxHeight(IAbstractCommandDetail.CB_HEIGHT);
        return cb;
    } 

    public Button createButton(String property, EventHandler<MouseEvent> handler){
        ButtonTemplate button = new ButtonTemplate(property);
        button.getButton().setMinHeight(IAbstractCommandDetail.CB_HEIGHT);
        button.getButton().setMinWidth(IAbstractCommandDetail.CB_WIDTH);
       button.setOnButtonAction(handler);
        return button.getButton();
    }


}
