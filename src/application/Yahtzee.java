package application;

import com.sun.javafx.application.LauncherImpl;

import gui.GUI;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scoring.HighScores;

public class Yahtzee extends Application {

	private static Stage stage;
	private static int numberOfPlayers = 1;
	private static final int COUNT_LIMIT = 100;
	
	public static void main(String[] args) {
		//Load splash screen
		LauncherImpl.launchApplication(Yahtzee.class, SplashPreloader.class, args);
	}

	@Override
	public void init() throws Exception {
		//count and pass value to the Splash preLoader
		for (int i = 1; i <= COUNT_LIMIT; i ++) {
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(i));
            Thread.sleep(30);
        }
	}
	@Override
	public void start(Stage stage){
		
		Yahtzee.stage = stage;
		stage.setResizable(false);
		stage.setX(150);
		stage.setY(0);
		stage.setTitle("YAHTZEE!");
		stage.getIcons().add(new Image("images/icon.png"));
		stage.setOnCloseRequest(e->{HighScores.exportHighScoresToFile();});
		SaveGame.importSaveGames();
		GUI gui = new GUI();
		if(SaveGame.getLatestGame()!=null) {
			gui.checkContinue();
		}
		else
		{
			startGame();		
		}
	}
	
	public static void startGame() {
		GUI gui = new GUI();
		Game game = new Game(gui.getPlayerDetails(),gui);
		gui.setGame(game);
		
		gui.constructWindow(false);
	}
	
	public static void quitGame() {
		if(GUI.confirmDialog("Quit", "Quitting Game", "Are you sure?", AlertType.CONFIRMATION)) {
			HighScores.exportHighScoresToFile();
			System.exit(0);
		}
		
	}

	public static Stage getStage() {
		return Yahtzee.stage;
	}

	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public static void setNumberOfPlayers(int numberOfPlayers) {
		Yahtzee.numberOfPlayers = numberOfPlayers;
	}

}
