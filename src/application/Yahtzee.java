package application;

import com.sun.javafx.application.LauncherImpl;

import gui.GUI;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scoring.HighScores;

/**
 * Yahtzee class to represent the main entry point for Yahtzee game
 * @author Danny Newsom
 * @version 2.0
 */
public class Yahtzee extends Application {

	/*
	 * Fields
	 */
	//MainStage
	private static Stage stage;
	//Initial number of players
	private static int numberOfPlayers = 1;
	//Count limit for splash screen progress bar
	private static final int COUNT_LIMIT = 100;
	
	/**
	 * Main method for the Yahtzee class
	 * @param args String[] for main method
	 */
	public static void main(String[] args) {
		//Load splash screen
		LauncherImpl.launchApplication(Yahtzee.class, SplashPreloader.class, args);
	}

	/**
	 * Init method for the Yahtzee Class
	 */
	public void init() throws Exception {
		//count and pass value to the Splash preLoader
		for (int i = 1; i <= COUNT_LIMIT; i ++) {
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(i));
            Thread.sleep(30);
        }
	}
	
	/**
	 * Start method for the Yahtzee Class
	 */
	public void start(Stage stage){
		/*
		 * Initialise stage
		 */
		Yahtzee.stage = stage;
		stage.setResizable(false);
		stage.setX(150);
		stage.setY(0);
		stage.setTitle("YAHTZEE!");
		stage.getIcons().add(new Image("images/icon.png"));
		stage.setOnCloseRequest(e->{HighScores.exportHighScoresToFile();});
		//Import save games from file
		SaveGame.importSaveGames();
		GUI gui = new GUI();
		//If save game is present ask to continue
		if(SaveGame.getLatestGame()!=null) {
			gui.checkContinue();
		}
		//If not start new game 
		else
		{
			startGame();		
		}
	}
	
	/**
	 * Method to start a new game
	 */
	public static void startGame() {
		GUI gui = new GUI();
		//Get player details from dialogs and start new game
		Game game = new Game(gui.getPlayerDetails(),gui);
		gui.setGame(game);
		//Draw game window
		gui.constructWindow(false);
	}
	
	/**
	 * Method to confirm exit from the game
	 */
	public static void quitGame() {
		if(GUI.confirmDialog("Quit", "Quitting Game", "Are you sure?", AlertType.CONFIRMATION)) {
			HighScores.exportHighScoresToFile();
			System.exit(0);
		}
	}

	/**
	 * Getter for the main stage
	 * @return main Stage object
	 */
	public static Stage getStage() {
		return Yahtzee.stage;
	}

	/**
	 * Getter for the number of players field
	 * @return int representing the current number of players
	 */
	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * Setter for the number of players field
	 * @param numberOfPlayers int representing the new number of players
	 */
	public static void setNumberOfPlayers(int numberOfPlayers) {
		Yahtzee.numberOfPlayers = numberOfPlayers;
	}

}
