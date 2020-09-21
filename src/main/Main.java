package main;

import gui.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
		
	@Override
	public void start(Stage stage){
		
		Main.stage = stage;
		stage.sizeToScene();
		stage.setTitle("YAHTZEE!");
		startGame(stage);		
	}
	
	public static void startGame(Stage stage) {
		GUI gui = new GUI();
		Game game = new Game(new String[] {"Danny","Louie","Lacey","Bob","Fred","Dave","Bill","Frank","John","Steve"});//,"Bob","Fred","Dave","Bill","Frank","John","Steve"
		gui.setGame(game);
		gui.constructWindow();
	}

	public static Stage getStage() {
		return Main.stage;
	}

}
