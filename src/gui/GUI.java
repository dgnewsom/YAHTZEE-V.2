package gui;

import dice.Dice;
import dice.Die;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import main.Game;
import main.Main;
import player.Player;
import scoring.Category;

public class GUI {

	private Game game;
	Font font = new Font("Arial Bold", 30);
	private BorderPane root;
	private MenuBar menu;
	
	/*
	 * Dice pane fields
	 */
	private TilePane dicePane;
	private Label throwsRemaining;
	private TilePane diceButtons;
	private Button rollButton;
	/*
	 * Score pane fields
	 */
	private BorderPane scorePane;
	private TilePane playerName;
	private TilePane categoryLabels;
	private TilePane scoreLabels;
	private TilePane scoreButtons;
	/*
	 * Player pane fields
	 */
	private TilePane playerPane;
	
	public void constructWindow() {
		
		root = new BorderPane();
		createMenuBar();
		createDicePane();
		createScorePane();
		createPlayersPane();
		
		BorderPane gamePane = new BorderPane();
		gamePane.setLeft(dicePane);
		gamePane.setRight(scorePane);
		
		root.setTop(menu);
		root.setCenter(gamePane);
		root.setBottom(playerPane);
		
		Scene scene = new Scene(root);
		Main.getStage().setScene(scene);
		Main.getStage().show();
	}

	private void createMenuBar() {
		menu = new MenuBar();
		
		Menu file = new Menu("_File");
		MenuItem newGame = new MenuItem("_New");
		//save.setOnAction(e->{saveContacts();});
		MenuItem save = new MenuItem("_Save");
		//save.setOnAction(e->{saveContacts();});
		MenuItem load = new MenuItem("_Load");
		//load.setOnAction(e->{loadContacts();});
		file.getItems().addAll(newGame, save, load);
		
		menu.getMenus().add(file);
		
	}
	
	private void createDicePane() {
		
		dicePane = new TilePane(Orientation.VERTICAL);
		dicePane.setAlignment(Pos.CENTER);
		dicePane.setPadding(new Insets(5));
		dicePane.setPrefWidth(600);
		
		throwsRemaining = new Label(String.format("%d Throws remaining...", game.getCurrentPlayer().getThrowsRemaining()));
		throwsRemaining.setFont(font);
		
		diceButtons = new TilePane(Orientation.HORIZONTAL, 10, 10);
		diceButtons.setPrefColumns(5);
		
		Dice dice = game.getCurrentPlayer().getDice(); 
		for(Die die : dice.getDieArray()) {
			Button temp = new Button();
			temp.setFont(font);
			temp.setText("" + die.getCurrentValue());
			temp.setPrefWidth(100);
			temp.setPrefHeight(100);
			diceButtons.getChildren().add(temp);
		}
		
		rollButton = new Button("Roll Dice");
		rollButton.setFont(font);
		
		dicePane.getChildren().addAll(throwsRemaining, diceButtons, rollButton);
	}
	
	private void createScorePane() {
		scorePane = new BorderPane();
		scorePane.setPadding(new Insets(10));
		
		Label nameLabel = new Label(String.format("%s's turn.", game.getCurrentPlayer().getPlayerName()));
		nameLabel.setFont(font);
		playerName = new TilePane(nameLabel);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPadding(new Insets(10));
		playerName.setPrefWidth(200);
		
		categoryLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		categoryLabels.setPrefRows(13);
		
		scoreLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		scoreLabels.setPrefRows(13);
		
		scoreButtons = new TilePane(Orientation.VERTICAL, 0, 5);
		scoreButtons.setPrefRows(13);
		
		for(Category category : Category.values()) {
			Label name = new Label(category.getDescriptionName());
			name.setPrefSize(100, 30);
			Label score = new Label("___");
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				score = new Label(String.format("%03d", game.getCurrentPlayer().getScorecard().getCategoryScore(category)));
			}
			score.setPrefSize(100, 30);
			score.setAlignment(Pos.CENTER);
			Button submit = new Button(String.format("Score %s for %s", Category.CheckScore(game.getCurrentPlayer().getDice(),category), category.getDescriptionName()));
			submit.setPrefSize(200, 30);
			categoryLabels.getChildren().add(name);
			scoreLabels.getChildren().add(score);
			scoreButtons.getChildren().add(submit);
		}
		
		scorePane.setTop(playerName);
		scorePane.setLeft(categoryLabels);
		scorePane.setCenter(scoreLabels);
		scorePane.setRight(scoreButtons);
	}
	
	private void createPlayersPane() {
		playerPane = new TilePane(Orientation.VERTICAL,5,5);
		playerPane.setPrefRows(2);
		playerPane.setAlignment(Pos.CENTER);
		
		Label title = new Label("Current scores");
		title.setFont(new Font(30));
		
		TilePane names = new TilePane(Orientation.HORIZONTAL,5,5);
		names.setPrefColumns(5);
		names.setAlignment(Pos.CENTER);
		
		for(Player player : game.getPlayers()) {
			Label name = new Label(player.getPlayerName() + " : " + player.getScorecard().getGrandTotal());
			name.setPrefSize(100, 30);
			name.setFont(new Font(20));
			name.setAlignment(Pos.CENTER);
			
			names.getChildren().add(name);
		}
		
		playerPane.getChildren().addAll(title,names);
	}
	
	
	public void setGame(Game game) {
		this.game = game;
	}

	

}
