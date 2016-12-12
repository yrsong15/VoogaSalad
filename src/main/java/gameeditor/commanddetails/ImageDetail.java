package gameeditor.commanddetails;

import java.io.File;
import frontend.util.FileOpener;
import gameeditor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ImageDetail {
    private String myImagePath="";
    private Pane myImagePane;
    private DetailFrontEndUtil myDetailFrontEndUtil;
    private ImageView myIV;
    
   
    public ImageDetail(){
        myDetailFrontEndUtil = new DetailFrontEndUtil();
    }
  
    @SuppressWarnings("static-access")
	public BorderPane createImageChoose(){
        myImagePane = new Pane();
        myImagePane.setMinWidth(60);
        myImagePane.setMaxWidth(60);
        Button choose = createImageButton();
        Rectangle imageZone = new Rectangle(DetailResources.TYPE_IMAGE_ZONE_WIDTH.getDoubleResource(), DetailResources.TYPE_IMAGE_ZONE_HEIGHT.getDoubleResource(), Color.GHOSTWHITE);
        myImagePane.getChildren().add(imageZone);
        imageZone.setArcHeight(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource()); imageZone.setArcWidth(DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource());
        BorderPane bp = myDetailFrontEndUtil.createBorderpane(myImagePane,choose);
        bp.setAlignment(choose, Pos.CENTER);
        return bp;
    }

    public void createImageView(){
    	if (myIV != null){
    		myImagePane.getChildren().remove(myIV);
    	}
        myImagePath = getFilePath(ViewResources.IMAGE_FILE_TYPE.getResource(), ViewResources.SPRITE_IMAGE_LOCATION.getResource());       
        Image i = new Image(myImagePath);
        double imageZonePadding = DetailResources.TYPE_IMAGE_ZONE_PADDING.getDoubleResource();
        double imageZoneWidth = DetailResources.TYPE_IMAGE_ZONE_WIDTH.getDoubleResource();
        double imageZoneHeight = DetailResources.TYPE_IMAGE_ZONE_HEIGHT.getDoubleResource();
        double fitWidth = imageZoneWidth-imageZonePadding;
        double fitHeight = imageZoneHeight-imageZonePadding;
        double widthRatio = fitWidth/i.getWidth();
        double heightRatio = fitHeight/i.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = i.getWidth()*ratio;
        double endHeight = i.getHeight()*ratio;
        myIV = new ImageView(i);
        myIV.setFitWidth(fitWidth);
        myIV.setFitHeight(fitHeight);
        myIV.setPreserveRatio(true);
        myIV.setLayoutX(imageZoneWidth/2 - endWidth/2);
        myIV.setLayoutY(imageZoneHeight/2 - endHeight/2);
        myImagePane.getChildren().add(myIV);
    }

    private Button createImageButton(){
        return myDetailFrontEndUtil.createButton("ChooseImageCommand", e-> createImageView());
    }
    
    private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file =(myFileOpener.chooseFile(fileType, fileLocation));
        if(file !=null){
            return file.toURI().toString();
        }
        return null;
    }

    public String getFilePath(){
        return myImagePath;
    }
    
}
