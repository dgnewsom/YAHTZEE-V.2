package main;

import dice.DieColour;
import gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

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
		startGame(stage);		
	}
	
	public static void startGame(Stage stage) {
		GUI gui = new GUI();
		Game game = new Game(gui.getPlayerDetails());
		gui.setGame(game);
		gui.constructWindow();
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
