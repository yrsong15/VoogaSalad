package gameeditor.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import gameeditor.view.interfaces.IEditorToolbar;
import gameeditor.view.interfaces.IToolbarParent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class EditorToolbar implements IEditorToolbar {

	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private IToolbarParent myOutput;
	
	private Pane myPane;
	
	private ImageView myBackgroundImageView;
	private ImageView myAvatarImageView;
	private ImageView myMusicImageView;
	private ImageView myLoadGameImageView;
	
	private TextArea myXTextArea;
	private BorderPane myXTextBP;
	private ComboBox<String> myDimComboBox;
	private TextArea myTimeWin;
	private TextArea myPointsWin;
	private Map<String,String> myLevelData;
	
	private Menu forcedScrollSubMenu;
	private Menu limitedScrollSubMenu;
	private Menu scrollTypeMenu;
	private MenuItem freeScrollType;
	//private Button updateScrollWidthButton;
	
	
	public static final String TIME_PROPERTY = "time";
	public static final String POINTS_PROPERTY = "points";
	public static final String SCROLL_WIDTH_PROPERTY = "scrollWidth";

	
	
	public EditorToolbar(IToolbarParent toolOut) {
		myOutput = toolOut;
		myLevelData = new HashMap<String,String>();
		myPane = new Pane();
		myPane.setMinWidth(TOOLBAR_WIDTH); myPane.setMaxWidth(TOOLBAR_WIDTH);
		myPane.setMinHeight(TOOLBAR_HEIGHT); myPane.setMaxHeight(TOOLBAR_HEIGHT);
		myPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		createButton(myBackgroundImageView, "/Background.png", BG_IMAGE_WIDTH, BG_IMAGE_XOFFSET, e -> myOutput.setBackground());
		createButton(myAvatarImageView, "/Avatar.png", AVATAR_IMAGE_WIDTH, AVATAR_IMAGE_XOFFSET, e -> myOutput.setAvatar());
		createButton(myMusicImageView, "/Music.png", MUSIC_IMAGE_WIDTH, MUSIC_IMAGE_XOFFSET, e -> myOutput.setMusic());
		// Create load button
		createButton(myLoadGameImageView,"/Save.png",LOAD_GAME_IMAGE_WIDTH,LOAD_GAME_IMAGE_XOFFSET,e-> sendLevelData());
		createDimensions();
		createWinConditions();
		
		addScrollTypeOptions();
		
	}
	
	// TODO: REFACTOR THIS METHOD TO WORK GENERALLY, USE image.getWidth();
	private void createButton(ImageView myImageView, String fileLocation, 
		double imageWidth, double imageXOffset, EventHandler<MouseEvent> handler){
		Image buttonImage;
		try {
			buttonImage = new Image(new FileInputStream(IMAGE_FILE_LOCATION + fileLocation));
			myImageView = new ImageView(buttonImage);
			myImageView.setPreserveRatio(true);
			myImageView.setFitHeight(BUTTON_IMAGE_HEIGHT);
			myImageView.setFitWidth(imageWidth);
			myImageView.setLayoutX(imageXOffset);
		
			myImageView.setLayoutY(BUTTON_IMAGE_YOFFSET);
			myImageView.setOnMouseClicked(handler);
			myPane.getChildren().add(myImageView);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createWinConditions(){
		myTimeWin = createInputBP("Time: ", "N/A", 160, 5);
		myLevelData.put(TIME_PROPERTY,myTimeWin.getText());
		myPointsWin = createInputBP("Points: ", "N/A", 160, 40);
		myLevelData.put(TIME_PROPERTY,myTimeWin.getText());
		
	}
	
	private void createDimensions(){
		myDimComboBox = createWidthDimCB("Limit Width: ", 10, 5);
	}
	
	private void addScrollTypeOptions(){
	   MenuBar menuBar = new MenuBar();
	   menuBar.setLayoutX(350);
	   menuBar.setLayoutY(10);
	   scrollTypeMenu = new Menu(SCROLL_TYPE_LABEL);
	   limitedScrollSubMenu = createSubMenu(LIMITED_SCROLL_TYPE_LABEL);
	   forcedScrollSubMenu = createSubMenu(FORCED_SCROLL_TYPE_LABEL);
	   freeScrollType = createMenuItem(FREE_SCROLL_TYPE_LABEL);
	   scrollTypeMenu.getItems().addAll(limitedScrollSubMenu,forcedScrollSubMenu, freeScrollType);
	   menuBar.getMenus().add(scrollTypeMenu);
	   myPane.getChildren().add(menuBar);
	}
	
	private Menu createSubMenu(String type){
	    // TODO: going to use ReosurceBundle 
	    Menu m = new Menu(type);
	    m.getItems().addAll(createMenuItem("LEFT"), createMenuItem("RIGHT"), createMenuItem("UP"), createMenuItem("DOWN"));
	    return m;
	}
	
	private MenuItem createMenuItem(String property){
	    return new MenuItem(property);
	}

	private ComboBox<String> createWidthDimCB(String initValue, double x, double y){
		ComboBox<String> cb = new ComboBox<String>();
		cb.setValue(initValue);
		cb.getItems().add("True");
		cb.getItems().add("False");
		cb.setMinWidth(125); cb.setMaxWidth(125);
		cb.setMinHeight(30); cb.setMaxHeight(30);
		cb.setLayoutX(x);
		cb.setLayoutY(y);
		cb.setOnAction((e) -> cbOnAction(cb));
		myPane.getChildren().add(cb);		
		return cb;
		
	}
	
	public void cbOnAction(ComboBox<String> cb){
		if (cb.getValue().equals("True")){
			myXTextArea = createInputBP("Width: ", Double.toString(ViewResources.AREA_WIDTH.getDoubleResource()), 10, 40);
			myLevelData.put(SCROLL_WIDTH_PROPERTY, myXTextArea.getText());
		} else {
			myPane.getChildren().remove(myXTextBP);	
		}
	}
	
	public TextArea createInputBP(String label, String initValue, double x, double y){
		myXTextBP = new BorderPane();
		myXTextBP.setMinWidth(125);
		myXTextBP.setMaxWidth(125);
		Label labl = createLbl(label);
		
		TextArea ta = new TextArea();
		ta.setText(initValue);
		ta.setMinWidth(75); ta.setMaxWidth(75);
		ta.setMinHeight(30); ta.setMaxHeight(30);
		
		ta.setOnMouseClicked((e) -> handleClick(ta));
		
		myXTextBP.setLeft(labl);
		myXTextBP.setRight(ta);
		BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
		myXTextBP.setLayoutX(x);
		myXTextBP.setLayoutY(y);
		
		myPane.getChildren().add(myXTextBP);
		return ta;
	}
	
	
	private void sendLevelData(){
	    myLevelData.put(TIME_PROPERTY,myTimeWin.getText());
	    myLevelData.put(POINTS_PROPERTY, myPointsWin.getText());
	    
	    myOutput.saveLevelData(myLevelData);
	}
	
	public Label createLbl(String labelText){
		Label labl = new Label (labelText);
		return labl;
	}
	
	public void handleClick(TextArea field){
		field.setText("");
	}

	public Pane getPane(){
		return myPane;
	}
	
	public String getTimeWin(){
	    return myTimeWin.getText();
	}
	
	public String getWinPoints(){
	    return myPointsWin.getText();
	}
		
	public Menu getLimitedScrollingMenu(){
	    return this.limitedScrollSubMenu;
	}
	
	public Menu getForcedScrollMenu(){
	    return this.forcedScrollSubMenu;
	}

    @Override
    public MenuItem getFreeScrollTypeMenuItem () {
        return this.freeScrollType;
    }
	
	
}
