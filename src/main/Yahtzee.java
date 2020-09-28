package main;

import gui.GUI;
import javafx.application.Application;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scoring.HighScores;

public class Yahtzee extends Application {

	private static Stage stage;
	private static int numberOfPlayers = 1;
	
	public static void main(String[] args) {
		launch(args);
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
		startGame();		
	}
	
	public static void startGame() {
		GUI gui = new GUI();
		Game game = new Game(gui.getPlayerDetails(),gui);
		gui.setGame(game);
		SaveGame.importSaveGames();
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
