package main;

import gui.GUI;
import javafx.application.Application;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import scoring.HighScores;

public class Main extends Application {

	private static Stage stage;
	private static int numberOfPlayers = 1;
		
	@Override
	public void start(Stage stage){
		
		Main.stage = stage;
		stage.setResizable(false);
		stage.setX(150);
		stage.setY(0);
		stage.setTitle("YAHTZEE!");
		stage.setOnCloseRequest(e->{HighScores.exportHighScoresToFile();});
		startGame();		
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
		return Main.stage;
	}

	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public static void setNumberOfPlayers(int numberOfPlayers) {
		Main.numberOfPlayers = numberOfPlayers;
	}

}
