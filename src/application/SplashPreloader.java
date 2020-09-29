package application;

import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * SplashPreloader Class to display a splash screen before main game
 * @author Daniel Newsom
 * @version 3.0
 *
 */
public class SplashPreloader extends Preloader {

	private Stage preloaderStage;
	private Scene scene;
	VBox root1;
	ProgressBar progressBar;
	Label progressLabel;

	@Override
	public void init() throws Exception {               
		/*
		 * create and set scene for splash screen
		 */
		createSplashScreen();
		scene = new Scene(root1); 
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//set stage and show
		this.preloaderStage = primaryStage;
		preloaderStage.setScene(scene);  
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.show();

	}

	@Override
	public void handleApplicationNotification(Preloader.PreloaderNotification info) {

		/*
		 * update loading label and progress bar when new value is received from main method
		 */
		if (info instanceof ProgressNotification) {
			int percent = (int)((ProgressNotification) info).getProgress();
			progressLabel.setText(String.format("Loading - %d", percent ) + "%");
			progressBar.setProgress(((ProgressNotification) info).getProgress()/100);
		}
	}

	@Override
	public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

		/*
		 * once counting complete, hide splash screen
		 */
		StateChangeNotification.Type type = info.getType();
		switch (type) {

		case BEFORE_START:
			preloaderStage.hide();
			break;
		default:
			break;
		}
	}

	/**
	 * Construct the splash screen rootPane and add elements
	 */
	private void createSplashScreen() {
		root1 = new VBox(20);
		root1.setPadding(new Insets(30));
		root1.setAlignment(Pos.CENTER);
		root1.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, new CornerRadii(0), Insets.EMPTY)));
		
		VBox mainPane = new VBox(20);
		mainPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
		mainPane.setPadding(new Insets(30));
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(10))));
		root1.getChildren().add(mainPane);
		
		Label titleLabel = new Label("YAHTZEE!");
		titleLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 60));
		titleLabel.setPadding(new Insets(20));

		Label nameLabel = new Label("By Daniel Newsom");
		nameLabel.setFont(Font.font(null, FontWeight.BOLD, 30));
		nameLabel.setPadding(new Insets(20));
		
		ImageView diceImage = new ImageView(new Image("images/splashLogo/logo.png"));
		diceImage.setFitHeight(80);
		diceImage.setFitWidth(450);
		

		progressLabel = new Label("Loading 0%");
		progressLabel.setFont(Font.font(null,FontWeight.BOLD,20));
		progressBar = new ProgressBar();
		progressBar.setPrefWidth(200);
		progressBar.setProgress(0.0);
		progressBar.setStyle("-fx-accent: skyblue;");

		VBox progressPane = new VBox();
		progressPane.setAlignment(Pos.CENTER);
		progressPane.setPadding(new Insets(20));
		progressPane.getChildren().addAll(progressLabel,progressBar);
		mainPane.getChildren().addAll(titleLabel,nameLabel,diceImage,progressPane);
	}
}
