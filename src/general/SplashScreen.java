package general;
import base.integration.ISplashScreen;
import buttons.ButtonTemplate;
import editor.view.FileOpener;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static editor.view.IGameEditorView.IMAGE_FILE_TYPE;

import java.net.MalformedURLException;
/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {

    public static final String BG_IMAGE_LOCATION = "images";
    private static final int SPLASH_WIDTH = 700;
    private static final int SPLASH_HEIGHT = 600;
    private Pane startWindow;
    private MainController mainController;

    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
                                                                                CycleMethod.NO_CYCLE,
                                                                                new Stop(0, Color.WHITE),
                                                                                new Stop(0.15, Color.HONEYDEW),
                                                                                new Stop(0.3, Color.LIGHTBLUE),
                                                                                new Stop(0.45, Color.WHITE),
                                                                                new Stop(0.6, Color.LIGHTBLUE),
                                                                                new Stop(0.75, Color.HONEYDEW),
                                                                                new Stop(1, Color.WHITE));
    public SplashScreen(Stage myStage, MainController mainController){
        this.mainController = mainController;
	}

	@Override
	public Parent setUpWindow() {
		startWindow = new Pane();
		startWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
			String userDirectoryString = "file:" +  System.getProperty("user.dir")+ "/images/Background/floatingCubes.jpg";
//			String userDirectoryString = myFileOpener.chooseFile(IMAGE_FILE_TYPE, BG_IMAGE_LOCATION).toURI().toURL().toString();//"file:" +  System.getProperty("user.dir")+ "/images/Background/floatingCubes.jpg";
			Image background = new Image(userDirectoryString);
//        String filePath = System.getProperty("user.dir") + "/images/floatingCubes.jpg";
//        String slash = "\\\\";
//        filePath.replace(slash.substring(1), "/");
//        System.out.println(slash.substring(1));
//        System.out.println(filePath);
//        ImageView backgroundImageMainScreen = new ImageView(new Image(filePath));
//		Image background = new Image(getClass().getClassLoader()
//				.getResourceAsStream("images/floatingCubes.jpg"));
			ImageView backgroundImageMainScreen = new ImageView(background);
			backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
			backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
			startWindow.getChildren().add(backgroundImageMainScreen);


		addTitle();
		addButtons();
		return startWindow;
	}
	


	@Override
	public void launchGameEditorWith() {

	}

	@Override
	public void launchGameEditor() {

	}

	@Override
	public void launchGameGallery() {
		mainController.presentGallery();
	}

	@Override
	public void launchSelectedGalleryItem() {

	}
	
	public void launchGameEngine() {
		
	}
	
	public void launchGameLoader() {
		
	}
	

	private void addButtons(){
		// TODO: Change this hash map into reflection where the method of launch + the buttonName is called
		HashMap<String,EventHandler<MouseEvent>> eventHandlerForButton = new HashMap<String,EventHandler<MouseEvent>>();
		eventHandlerForButton.put("GameEngine", e -> launchGameEngine());
		eventHandlerForButton.put("GameEditor", e -> launchGameEditor());
		eventHandlerForButton.put("GameGallery", e -> launchGameGallery());
		eventHandlerForButton.put("GameLoader", e -> launchGameLoader());
				
		String[] buttonNames = {"GameEngine","GameEditor","GameGallery","GameLoader"};
		
		double initialX = 100;
		double initialY = 280;
		double xSpacing = 300;
		double ySpacing = 70;
		int buttonsPerCol = 3; // Also rows
		
		for(int i = 0; i < buttonNames.length; i++)
		{
			ButtonTemplate buttonTemplate = new ButtonTemplate(buttonNames[i]);
			Button button = buttonTemplate.getButton();
			button.setTranslateX(initialX + (i/buttonsPerCol) * xSpacing);
			button.setTranslateY(initialY + (i%buttonsPerCol) * ySpacing);
			button.setOnMouseClicked(eventHandlerForButton.get(buttonNames[i]));
			
			startWindow.getChildren().add(button);
		}
		/*
		ButtonTemplate engineButton = new ButtonTemplate("GameEngine");
		Button engine = engineButton.getButton();
		engine.setTranslateX(100);
		engine.setTranslateY(350);
		engine.setOnMouseClicked(e -> System.out.println("engine"));
		
		ButtonTemplate editorButton = new ButtonTemplate("GameEditor");
		Button editor = editorButton.getButton();
		editor.setTranslateX(100);
		editor.setTranslateY(280);

		ButtonTemplate galleryButton = new ButtonTemplate("GameGallery");
		Button gallery = galleryButton.getButton();
		gallery.setTranslateX(100);
		gallery.setTranslateY(420);
        gallery.setOnMouseClicked(e -> mainController.presentGallery());

		ButtonTemplate loaderButton = new ButtonTemplate("GameLoader");
		Button loader = loaderButton.getButton();
		loader.setTranslateX(400);
		loader.setTranslateY(280);

		startWindow.getChildren().addAll(engine, editor, gallery, loader, test);
		*/
	}

	private void addTitle() {
		BigNameText title = new BigNameText("Welcome to \n\tVoogaSalad");
		title.setTranslateX(75);
		title.setTranslateY(75);
		startWindow.getChildren().add(title);

	}


	private static class BigNameText extends StackPane {
		/**
		 * @param Name
		 */
		public BigNameText(String Name) {
			Text titleText = new Text(Name);
			titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
			titleText.setFill(textAndBoxGradient);
			getChildren().add(titleText);
		}
	}

}