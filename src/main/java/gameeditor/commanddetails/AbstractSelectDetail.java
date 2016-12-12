package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.GameObjectView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
/**
 * @author John Martin
 *
 */
abstract public class AbstractSelectDetail extends AbstractCommandDetail implements ISelectDetail {

    protected static final String X_LABEL = "X: ";
    protected static final String Y_LABEL = "Y: ";
    protected static final String WIDTH_LABEL = "W: ";
    protected static final String HEIGHT_LABEL = "H: ";

    // private VBox myVBox = new VBox();

    protected Label mySelectLabel;

    protected TextArea myXTextArea = new TextArea();
    protected TextArea myYTextArea = new TextArea();
    protected TextArea myWidthTextArea = new TextArea();
    protected TextArea myHeightTextArea = new TextArea();
    protected GameObjectView myGO;

    protected List<TextArea> myRandomGenerationList = new ArrayList<TextArea>();
    protected String[] myRandomGenerationParameters = DetailResources.RANDOM_GENERATION_PARAMETERS.getArrayResource();


    private String myType;
    private ComboBox<String>IsEnemyAllowed;

    public AbstractSelectDetail() {
        super();
    }

    @Override
    abstract public void init();

    abstract public void initLevel2(GameObjectView sprite);

    public void clearSelect(){
        init();
    }

    public void updateSpritePosition(double x, double y){
        String xString = Double.toString(x);
        xString = xString.substring(0, xString.indexOf(".")+2);
        String yString = Double.toString(y);
        yString = yString.substring(0, yString.indexOf(".")+2);
        myXTextArea.setText(X_LABEL + xString);
        myYTextArea.setText(Y_LABEL + yString);
    }

    public void updateSpriteDimensions(double width, double height){
        String widthString = Double.toString(width);
        widthString = widthString.substring(0, widthString.indexOf(".")+2);
        String heightString = Double.toString(height);
        heightString = heightString.substring(0, heightString.indexOf(".")+2);
        myWidthTextArea.setText(WIDTH_LABEL + widthString);
        myHeightTextArea.setText(HEIGHT_LABEL + heightString);
    }

    @SuppressWarnings("unused")
	private void createUpdate(){
        Button update = new Button();
        update.setText(DetailResources.UPDATE_BUTTON_TEXT.getResource());
        update.setMinWidth((PADDED_PANE_WIDTH - HBOX_SPACING)/2);
        update.setMaxWidth((PADDED_PANE_WIDTH - HBOX_SPACING)/2);
        update.setMinHeight(CB_HEIGHT);
        update.setOnAction((e) -> {handleUpdate();});
        myVBox.getChildren().add(update);
    }

    private void handleUpdate() {
        String xString = myXTextArea.getText();
        String yString = myYTextArea.getText();
        String widthString = myWidthTextArea.getText();
        String heightString = myHeightTextArea.getText();
        double x = Double.parseDouble(xString.substring(X_LABEL.length()));
        double y = Double.parseDouble(yString.substring(Y_LABEL.length()));
        double width = Double.parseDouble(widthString.substring(WIDTH_LABEL.length()));
        double height = Double.parseDouble(heightString.substring(HEIGHT_LABEL.length()));

        myGO.update(x, y, width, height);

        // Update Data in the Back End
        Map<String, String> typeMap = myDataStore.getType(myGO.getType());
        typeMap.put(ISelectDetail.X_POSITION_KEY,xString.substring(X_LABEL.length()));
        typeMap.put(ISelectDetail.Y_POSITION_KEY, yString.substring(Y_LABEL.length()));
        typeMap.put(IGameEditorData.WIDTH_KEY,widthString.substring(WIDTH_LABEL.length()));
        typeMap.put(IGameEditorData.HEIGHT_KEY, heightString.substring(HEIGHT_LABEL.length()));


        String randomGen = typeMap.get(DetailResources.RANDOM_GEN_KEY.getResource());
        if(randomGen!=null && randomGen.equals("True")){ 
            myDataStore.addRandomGeneration(myGO.getType(), myRandomGenerationList);
        }
    }

    protected void addSelectLabel(){
        BorderPane bp = new BorderPane();
        mySelectLabel = myDetailFrontEndUtil.createPropertyLbl(DetailResources.SELECT_LABEL_TEXT.getResource());
        bp.setCenter(mySelectLabel);
        bp.setMinWidth(PADDED_PANE_WIDTH);
        bp.setMaxWidth(PADDED_PANE_WIDTH);
        myVBox.getChildren().add(bp);
    }	

    protected void createPos(){
        createInfoBP(myXTextArea, X_LABEL, myGO.getX(), myYTextArea, Y_LABEL, myGO.getY());
        createInfoBP(myWidthTextArea, WIDTH_LABEL, myGO.getWidth(), myHeightTextArea, HEIGHT_LABEL, myGO.getHeight());
    }

    private void createInfoBP(TextArea ta1, String label1, double value1, TextArea ta2, String label2, double value2){
        ta1 = createTextArea(label1, value1, ta1);
        ta2 = createTextArea(label2, value2, ta2);
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(ta2,ta1);
        myVBox.getChildren().add(bp);
    }

    private TextArea createTextArea(String label, double value, TextArea ta){
        String valueString = Double.toString(value);
        valueString = valueString.substring(0, valueString.indexOf(".")+2);
        ta.setText(label + valueString);
        ta.setMinWidth(CB_WIDTH); ta.setMaxWidth(CB_WIDTH);
        ta.setMinHeight(CB_HEIGHT); ta.setMaxHeight(CB_HEIGHT);
        ta.setOnKeyReleased((e) -> handleKeyRelease(e.getCode(), e.getCharacter(), ta, label));
        //		ta.setOnMouseClicked((e) -> handleClick(ta));
        return ta;
    }

    protected void createTypeLabel(){
        myType = myGO.getType();
        mySelectLabel.setText(myType);
    }

    protected void createRandomGenProperties(){
        String [] options = DetailResources.LIMIT_DIMENSION_OPTIONS.getArrayResource();
        IsEnemyAllowed = myDetailFrontEndUtil.createComboBox(options, "True");
        Label propertyLabel = myDetailFrontEndUtil.createPropertyLbl("Is Enemy Allowed");
        BorderPane borderpane = myDetailFrontEndUtil.createBorderpane( IsEnemyAllowed,propertyLabel);
        myVBox.getChildren().add(borderpane);
        
        for (String label : myRandomGenerationParameters){           
            Label labl =myDetailFrontEndUtil. createPropertyLbl(label);
            TextArea input= myDetailFrontEndUtil.createInputField("0");
            myRandomGenerationList.add(input);
            BorderPane bp = myDetailFrontEndUtil.createBorderpane(input,labl);
            BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
            myVBox.getChildren().add(bp);
        }
    }

    private void handleKeyRelease(KeyCode kc, String character, TextArea field, String label){
        //		if (kc == KeyCode.BACK_SPACE){
        if (field.getText().length() < label.length() && kc.isDigitKey()){
            field.setText(label + character);
            field.positionCaret(field.getText().length());
        } else if (field.getText().length() < label.length()){
            field.setText(label);
            field.positionCaret(field.getText().length());
        } else if (kc.isDigitKey()){
            field.setText(label + field.getText().substring(label.length()));
            field.positionCaret(field.getText().length());
        } else if (kc.isLetterKey() || kc.isWhitespaceKey()){
            field.setText(label + field.getText().substring(label.length(), field.getText().length()-1));
            field.positionCaret(field.getText().length());
        }
    }  
}
