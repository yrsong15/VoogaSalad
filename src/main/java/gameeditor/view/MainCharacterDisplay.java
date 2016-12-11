package gameeditor.view;

import java.util.ArrayList;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainCharacterDisplay {
	
	private static final double TOTAL_ZONE_WIDTH = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource()-2*ViewResources.DETAIL_ZONE_PADDING.getDoubleResource();
	private static final double TOTAL_ZONE_HEIGHT = ViewResources.AVATAR_ZONE_HEIGHT.getDoubleResource();
	private static final double BUTTON_WIDTH = 200;
	private static final double BUTTON_HEIGHT = 30;
	private static final double PADDING = ViewResources.AVATAR_ZONE_PADDING.getDoubleResource();
	private static final double AVATAR_ZONE_WIDTH = ViewResources.AVATAR_ZONE_WIDTH.getDoubleResource();
	private static final double AVATAR_ZONE_HEIGHT = ViewResources.AVATAR_ZONE_HEIGHT.getDoubleResource() - BUTTON_HEIGHT;
	private static final double DEFAULT_PLAYER_X = 100;
    private static final double DEFAULT_PLAYER_Y = 200;
    private static final double DEFAULT_PLAYER_WIDTH = 75;
    private static final double DEFAULT_PLAYER_HEIGHT = 75;
	
	private IGameEditorData myDataStore;
	private IDesignArea myDesignArea;
	private IDetailPane myDetailPane;
	private BorderPane myBP = new BorderPane();
	private ScrollPane myCenterParent = new ScrollPane();
	private Pane myCenterPane = new Pane();
	
	private Button myButton;
	private boolean mainCharPropActive = false;
//	private double buttonPadding = 50;
	
//	private ArrayList <PlayerView> myPlayers;
	private ArrayList <Pane> myAvatarPanes = new ArrayList<Pane>();
	private ArrayList <String> myPlayers = new ArrayList<String>();
	private int myTotalChars = 0;
	private int myCurrentChar = 0;
	private double myCharSpacing = 10;
	private double myDelta = myCharSpacing + AVATAR_ZONE_WIDTH;
	private double baseX = (TOTAL_ZONE_WIDTH - AVATAR_ZONE_WIDTH)/2;
	private double currentX = baseX;
	private Rectangle myLeft;
	private Rectangle myRight;

	public MainCharacterDisplay(IGameEditorData ds, IDesignArea da, IDetailPane dp) {
		myDataStore = ds;
		myDesignArea = da;
		myDetailPane = dp;
		createCenterParent();
		createCenterPane();
		createAvatarButton();
		createBP();	
	}
	
	@SuppressWarnings("static-access")
	private void createBP(){
		myBP.setMinWidth(TOTAL_ZONE_WIDTH); myBP.setMaxWidth(TOTAL_ZONE_WIDTH);
		myBP.setMinHeight(TOTAL_ZONE_HEIGHT); myBP.setMaxHeight(TOTAL_ZONE_HEIGHT);
		myBP.setLayoutX(PADDING);
		myBP.setLayoutY(GameEditorView.SCENE_HEIGHT-PADDING-TOTAL_ZONE_HEIGHT);
		myBP.setCenter(myCenterParent);
		myBP.setBottom(myButton);
		myBP.setAlignment(myButton, Pos.CENTER);
	}
	
	private void createCenterParent(){
		myCenterParent.setMinWidth(TOTAL_ZONE_WIDTH); myCenterParent.setMaxWidth(TOTAL_ZONE_WIDTH);
		myCenterParent.setMinHeight(TOTAL_ZONE_HEIGHT); myCenterParent.setMaxHeight(TOTAL_ZONE_HEIGHT);
		myCenterParent.setHbarPolicy(ScrollBarPolicy.NEVER);
		myCenterParent.setVbarPolicy(ScrollBarPolicy.NEVER);
		myCenterParent.setHmax(0);
		myCenterParent.setVmax(0);
		myCenterParent.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), 
				CornerRadii.EMPTY, Insets.EMPTY)));
		myCenterParent.getStylesheets().add("gameeditor/commanddetails/DetailPane.css");

	}
	
	private void createCenterPane(){
		myCenterParent.setContent(myCenterPane);
	}
	
	private void createAvatarButton(){
		myButton = new Button();
    	myButton.setText("Main Character Properties");
    	myButton.setMinWidth(BUTTON_WIDTH); myButton.setMaxWidth(BUTTON_WIDTH);
    	myButton.setMinHeight(BUTTON_HEIGHT); myButton.setMaxHeight(BUTTON_HEIGHT);
    	myButton.setOnAction((e) -> {handleButton();});
    }
	
	private void update(){
		myCenterPane.getChildren().remove(myLeft);
		myCenterPane.getChildren().remove(myRight);
		createLeft();
		createRight();
	}
	
	private void init(){
		myCurrentChar = 1;
		
	}
	
	private void createLeft(){
		myLeft = new Rectangle(0, 0, 30, AVATAR_ZONE_HEIGHT);
		myLeft.setOnMouseClicked((e) -> scrollLeft());
//		myLeft.setFill(Color.TRANSPARENT);
		myLeft.setOpacity(0.1);
		myCenterPane.getChildren().add(myLeft);
	}
	
	private void createRight(){
		myRight = new Rectangle(TOTAL_ZONE_WIDTH-30, 0, 30, AVATAR_ZONE_HEIGHT);
		myRight.setOnMouseClicked((e) -> scrollRight());
//		myRight.setFill(Color.TRANSPARENT);
		myRight.setOpacity(0.1);
		myCenterPane.getChildren().add(myRight);
	}
	
	private void handleButton(){
    	if (mainCharPropActive){
    		myDetailPane.removeDetail();
    	} else {
    		myDetailPane.setDetail("MainCharacter");
    	}
    	mainCharPropActive = !mainCharPropActive;
    }
	
	private void scrollLeft(){
		scroll(1);
		update();
	}
	
	private void scrollRight(){
		scroll(-1);
		update();
	}
	
	private void scroll(double direction){
		double newX = currentX + direction*myDelta;
		double compare = (myTotalChars-1)*myDelta;
		if (newX <= baseX && newX >= -compare){
			for (Pane p : myAvatarPanes){
				p.setLayoutX(p.getLayoutX() + direction*myDelta);
			}
			myCurrentChar -= direction;
			currentX = newX;
		}
	}
	
	public void createNewAvatar(String filePath){
		if (myTotalChars == 0){
			init();
		}
		Pane avPane = new Pane();
		Rectangle avZone = createAvatarZone();
		ImageView avView = createAvatarView(filePath, avZone);
		Label lbl = createLabel();
		avPane.getChildren().add(avZone);
		avPane.getChildren().add(avView);
		avPane.getChildren().add(lbl);
		avPane.setLayoutX(currentX + myTotalChars*(myCharSpacing + AVATAR_ZONE_WIDTH));
		myTotalChars += 1;
		myAvatarPanes.add(avPane);
		myPlayers.add(filePath);
		myCenterPane.getChildren().add(avPane);
		update();
	}
	
	private Label createLabel(){
		Label l = new Label(Integer.toString(myTotalChars + 1));
		l.setFont(Font.font ("Futura", FontWeight.EXTRA_BOLD, 25)); 
		l.setTextFill(Color.LIGHTSLATEGRAY);
		l.setLayoutX(5);
		l.setLayoutY(5);
		return l;
	}
	
	private Rectangle createAvatarZone(){
		double padding = ViewResources.AVATAR_ZONE_PADDING.getDoubleResource();
        double cornerRadius = padding;
        Rectangle avatarZone = new Rectangle(0, 0, AVATAR_ZONE_WIDTH, AVATAR_ZONE_HEIGHT);
        avatarZone.setFill(Color.GHOSTWHITE);
        avatarZone.setArcWidth(cornerRadius);
        avatarZone.setArcHeight(cornerRadius);
        return avatarZone;
    }
	
	private ImageView createAvatarView(String filePath, Rectangle avZone){
        Image newAvatar = new Image(filePath);
        String file = filePath.substring(filePath.lastIndexOf("/") +1);
        myDataStore.addMainCharacterImage(file);
        double fitWidth = avZone.getWidth() - PADDING;
        double fitHeight = avZone.getHeight() - PADDING;
        double widthRatio = fitWidth/newAvatar.getWidth();
        double heightRatio = fitHeight/newAvatar.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = newAvatar.getWidth()*ratio;
        double endHeight = newAvatar.getHeight()*ratio;
        ImageView avView = new ImageView(newAvatar);
        avView.setPreserveRatio(true);
        avView.setFitWidth(fitWidth);
        avView.setFitHeight(fitHeight);
        avView.setLayoutX(avZone.getX() + avZone.getWidth()/2 - endWidth/2);
        avView.setLayoutY(avZone.getY() + avZone.getHeight()/2 - endHeight/2);
        // TODO: Remove Hard Coding
        double playerX = DEFAULT_PLAYER_X + myTotalChars*(10+DEFAULT_PLAYER_WIDTH);
        double playerY = DEFAULT_PLAYER_Y - endHeight/2;
        myDesignArea.addAvatar(filePath, playerX, playerY, DEFAULT_PLAYER_WIDTH, DEFAULT_PLAYER_HEIGHT, myDataStore);
        return avView;
    }
	
	public BorderPane getPane(){
		return myBP;
	}

}
