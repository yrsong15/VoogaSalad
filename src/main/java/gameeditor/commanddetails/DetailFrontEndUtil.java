package gameeditor.commanddetails;

import java.util.Locale;
import frontend.util.ButtonTemplate;
import frontend.util.GameEditorException;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
/**
 * 
 * @author Pratiksha Sharma
 *
 */
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

    public TextArea createTypeName(){
        TextArea  myTypeTextArea = new TextArea(DetailResources.TYPE_NAME.getResource());
        myTypeTextArea.setMinWidth(IAbstractCommandDetail.PADDED_PANE_WIDTH);
        myTypeTextArea.setMaxWidth(IAbstractCommandDetail.PADDED_PANE_WIDTH);
        myTypeTextArea.setMinHeight(IAbstractCommandDetail.CB_HEIGHT);
        myTypeTextArea.setMaxHeight(IAbstractCommandDetail.CB_HEIGHT); 
        myTypeTextArea.setOnMouseClicked(e-> handleClick(myTypeTextArea)); 
        return myTypeTextArea; 
    }

    public void verifyValue(TextArea myTypeTextArea){
        myTypeTextArea.textProperty().addListener(new javafx.beans.value.ChangeListener<String>(){
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {
                if(!newValue.isEmpty()){
                    try {
                        @SuppressWarnings("unused")
                        int val = Integer.parseInt(newValue);
                    } catch (NumberFormatException e) {
                        GameEditorException exception = new GameEditorException();
                        exception.showError("Value must be a number");;
                    }
                }  
            }
        }); 
    }

    public ComboBox<String> createPropertyCB(String property, String defaultValue){
        DetailResources resourceChoice = DetailResources.valueOf(property.toUpperCase(Locale.ENGLISH));
        String [] optionsArray = resourceChoice.getArrayResource();
        ComboBox<String> cb = createComboBox(optionsArray,defaultValue);
        return cb;
    }

    public Label createPropertyLbl(String property){
        return  new Label (property);
    }

    public TextArea createInputField(String initValue){
        TextArea inputField = new TextArea();
        inputField.setMinWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH);
        inputField.setMaxWidth(IAbstractCommandDetail.PADDED_DETAIL_WIDTH);
        inputField.setMinHeight(IAbstractCommandDetail.CB_HEIGHT);
        inputField.setMaxHeight(IAbstractCommandDetail.CB_HEIGHT);
        inputField.setText(initValue);
        inputField.setOnMouseClicked(e -> handleClick(inputField));
        if(initValue!=null && initValue.equals("0")){
            inputField.setOnMouseExited(e-> verifyValue(inputField));
        }
        return inputField;
    }

    public void handleClick(TextArea field){
        field.setText("");
    }

    public Button createSave(EventHandler <MouseEvent> handler){
        return createButton("SaveCommand",handler);
    }
}
