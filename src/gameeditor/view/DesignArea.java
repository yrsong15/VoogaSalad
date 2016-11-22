package gameeditor.view;

import java.net.MalformedURLException;
import java.util.ArrayList;

import frontend.util.FileOpener;
import gameeditor.view.interfaces.IDesignArea;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author pratikshasharma, John
 *
 */

public class DesignArea implements IDesignArea {
    // TODO: Remove hardcoding of the following values
    // Min Width, Max Width, Min Height

    private Pane myPane;
    private ScrollPane myScrollPane;
    private Group myGroup;
    private Region myRegion;

    private ImageView myAvatar;

    public DesignArea() {
        myScrollPane = new ScrollPane();
        myScrollPane.setMinWidth(AREA_WIDTH);
        myScrollPane.setMinHeight(AREA_HEIGHT);
        myScrollPane.setMaxWidth(AREA_WIDTH);
        myScrollPane.setMaxHeight(AREA_HEIGHT);
        myScrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        myScrollPane.setVmax(0);
        myScrollPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        myPane = new Pane();
        myScrollPane.setContent(myPane);
    }    
    
    public ScrollPane getScrollPane(){
        return myScrollPane;
    }

    public void updateAvatar(Image newAvatar){

    }
    
    public void setBackground(HBox bg){
    	ObservableList<Node> currentChildren = myPane.getChildren();
    	ArrayList<Node> children = new ArrayList<Node>();
    	for (Node child : currentChildren){
    		children.add(child);
    	}
    	myPane.getChildren().clear();
    	bg.setLayoutX(0);
    	bg.setLayoutY(0);
    	myPane.getChildren().add(bg);
    	myPane.getChildren().addAll(children);
    }

	@Override
	public void addSprite(ImageView sprite) {
		myPane.getChildren().add(sprite);
	}
	
	@Override
	public void removeSprite(ImageView sprite) {
		myPane.getChildren().remove(sprite);
	}

}
