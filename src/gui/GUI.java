package gui;



import javax.swing.border.LineBorder;

import dice.Dice;
import dice.Die;
import dice.DieButton;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Game;
import main.Main;
import player.Player;
import scoring.Category;
import scoring.ScoreCard;

public class GUI {

	private Game game;
	private BorderPane root;
	private MenuBar menu;
	
	/*
	 * Dice pane fields
	 */
	private Font dicePaneFont = new Font("Arial Bold", 45);
	private TilePane dicePane;
	private Label throwsRemaining;
	private TilePane diceButtons;
	private Button rollButton;
	/*
	 * Score pane fields
	 */
	private int scoreCardWidth = 600;
	
	private Font playerNameFont = new Font("Arial Bold", 30);
	private Font titleFont = new Font("Arial Bold", 20);
	private Font scoreFont = new Font("Arial", 20);
	private FlowPane scoreCardPane;
	private TilePane playerName;
	
	private TilePane upperSection;
	private FlowPane upperScores;
	private TilePane upperCategoryLabels;
	private TilePane upperScoreLabels;
	private TilePane upperScoreButtons;
	private TilePane upperTotals;
	
	private TilePane lowerSection;
	private TilePane lowerScores;
	private TilePane lowerCategoryLabels;
	private TilePane lowerScoreLabels;
	private TilePane lowerScoreButtons;
	private TilePane lowerTotals;
	
	private Label grandTotal;
	
	/*
	 * Player pane fields
	 */
	private Font scoreAreaNamesFont = new Font("Arial Bold", 30);
	private TilePane playerPane;
	private Background greenBackground = new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
	
	
	public void constructWindow() {
		
		root = new BorderPane();
		createMenuBar();
		createDicePane();
		createScorePane();
		createPlayersPane();
		
		
		
		FlowPane gamePane = new FlowPane();
		gamePane.getChildren().addAll(dicePane,scoreCardPane);
		
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
		//dicePane.setPrefSize(700, 700);
		dicePane.setBackground(greenBackground );
		String throwsLabelString;
		if(game.getCurrentPlayer().getThrowsRemaining() == 1) {
			throwsLabelString = String.format("%d Roll remaining...", game.getCurrentPlayer().getThrowsRemaining());
		}
		else if(game.getCurrentPlayer().getThrowsRemaining() == 0){
			throwsLabelString = String.format(game.getCurrentPlayer().getPlayerName() + "please enter your score...", game.getCurrentPlayer().getThrowsRemaining());
		}
		else {
			throwsLabelString = String.format("%d Rolls remaining...", game.getCurrentPlayer().getThrowsRemaining());
		}
		throwsRemaining = new Label(throwsLabelString);
		throwsRemaining.setFont(dicePaneFont);
		throwsRemaining.setAlignment(Pos.CENTER);
		throwsRemaining.setTextFill(Color.WHITE);
		
		diceButtons = new TilePane(Orientation.HORIZONTAL, 0, 0);
		diceButtons.setPrefColumns(5);
		
		Dice dice = game.getCurrentPlayer().getDice(); 
		for(Die die : dice.getDieArray()) {
			DieButton temp = new DieButton(die);
			temp.setFont(dicePaneFont);
			temp.setPrefWidth(100);
			temp.setPrefHeight(100);
			temp.setImage();
			temp.setBackground(null);
			temp.setOnMouseClicked(e->{if(game.getCurrentPlayer().getThrowsRemaining() > 0)
									   temp.Click(e);
									   constructWindow();});
			diceButtons.getChildren().add(temp);
		}
		
		rollButton = new Button("Roll Dice");
		rollButton.setFont(dicePaneFont);
		if(game.getCurrentPlayer().getThrowsRemaining() < 1) {
       	 	rollButton.setDisable(true);
        }
		rollButton.setOnMouseClicked(e->{game.getCurrentPlayer().RollDice();
		                                 constructWindow();
		                                 
		});
		
		dicePane.getChildren().addAll(throwsRemaining, diceButtons, rollButton);
	}
	
	private void createScorePane() {
		scoreCardPane = new FlowPane();
		scoreCardPane.setBackground(greenBackground);
		/*
		 * Players name
		 */
		Label nameLabel = new Label(String.format("%s's turn.", game.getCurrentPlayer().getPlayerName()));
		nameLabel.setFont(playerNameFont);
		nameLabel.setPrefHeight(50);
		playerName = new TilePane(nameLabel);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPadding(new Insets(10));
		playerName.setPrefWidth(scoreCardWidth);
		playerName.setMaxHeight(50);
		scoreCardPane.getChildren().add(playerName);
		
		/*
		 * Upper section
		 */
		//Categories / Scores / Buttons
		upperScores = new FlowPane();
		upperScores.setPadding(new Insets(10));
		upperScores.setAlignment(Pos.CENTER);
		upperCategoryLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		upperCategoryLabels.setPrefRows(13);
		
		upperScoreLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		upperScoreLabels.setPrefRows(13);
		
		upperScoreButtons = new TilePane(Orientation.VERTICAL, 0, 5);
		upperScoreButtons.setPrefRows(13);
		
		for(Category category : game.getCurrentPlayer().getScorecard().getUpperCategories()) {
			Label name = new Label(category.getDescriptionName());
			name.setPrefSize(100, 30);
			name.setFont(titleFont);
			Label score = new Label("___");
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				score = new Label(String.format("%d", game.getCurrentPlayer().getScorecard().getCategoryScore(category)));
			}
			score.setPrefSize(100, 30);
			score.setAlignment(Pos.CENTER);
			score.setFont(scoreFont);
			Button submit = new Button(String.format("Score %s for %s", Category.CheckScore(game.getCurrentPlayer().getDice(),category), category.getDescriptionName()));
			submit.setPrefSize(200, 30);
			submit.setOnMouseClicked(e->{
				game.getCurrentPlayer().getScorecard().setScore(game.getCurrentPlayer().getDice(), category);
				game.getCurrentPlayer().getScorecard().Calculate();
				game.nextPlayer();
				constructWindow();
			});
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				submit.setVisible(false);
			}
			upperCategoryLabels.getChildren().add(name);
			upperScoreLabels.getChildren().add(score);
			upperScoreButtons.getChildren().add(submit);
		}
		upperScores.getChildren().addAll(upperCategoryLabels,upperScoreLabels,upperScoreButtons);
		
		//upperSection = new TilePane();
		//upperSection.setAlignment(Pos.TOP_LEFT);
		//upperSection.setPrefWidth(scoreCardWidth);
		//upperSection.getChildren().add(upperScores);
				
		scoreCardPane.getChildren().add(upperScores);
		/*
		scorePane.setLeft(upperCategoryLabels);
		scorePane.setCenter(upperScoreLabels);
		scorePane.setRight(upperScoreButtons);
		*/
	
		/*
		 * lower section
		 
		//Categories / Scores / Buttons
		lowerScores = new TilePane(Orientation.HORIZONTAL,5,5);
		lowerScores.setPrefColumns(3);
		lowerScores.setPadding(new Insets(10));
		lowerScores.setAlignment(Pos.TOP_CENTER);
		lowerCategoryLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		lowerCategoryLabels.setPrefRows(13);
		
		lowerScoreLabels = new TilePane(Orientation.VERTICAL, 0, 5);
		lowerScoreLabels.setPrefRows(13);
		
		lowerScoreButtons = new TilePane(Orientation.VERTICAL, 0, 5);
		lowerScoreButtons.setPrefRows(13);
		
		for(Category category : game.getCurrentPlayer().getScorecard().getLowerCategories()) {
			Label name = new Label(category.getDescriptionName());
			name.setPrefSize(100, 30);
			name.setFont(titleFont);
			Label score = new Label("___");
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				score = new Label(String.format("%d", game.getCurrentPlayer().getScorecard().getCategoryScore(category)));
			}
			score.setPrefSize(100, 30);
			score.setAlignment(Pos.CENTER);
			score.setFont(scoreFont);
			Button submit = new Button(String.format("Score %s for %s", Category.CheckScore(game.getCurrentPlayer().getDice(),category), category.getDescriptionName()));
			submit.setPrefSize(200, 30);
			submit.setOnMouseClicked(e->{
				game.getCurrentPlayer().getScorecard().setScore(game.getCurrentPlayer().getDice(), category);
				game.getCurrentPlayer().getScorecard().Calculate();
				game.nextPlayer();
				constructWindow();
			});
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				submit.setVisible(false);
			}
			lowerCategoryLabels.getChildren().add(name);
			lowerScoreLabels.getChildren().add(score);
			lowerScoreButtons.getChildren().add(submit);
		}
		lowerScores.getChildren().addAll(lowerCategoryLabels,lowerScoreLabels,lowerScoreButtons);
		
		lowerSection = new TilePane();
		lowerSection.setPrefWidth(scoreCardWidth);
		lowerSection.getChildren().add(lowerScores);
		
		
		scoreCardPane.getChildren().add(lowerSection);
		/*
		scorePane.setLeft(lowerCategoryLabels);
		scorePane.setCenter(lowerScoreLabels);
		scorePane.setRight(lowerScoreButtons);
		*/
	}
	
	private void createPlayersPane() {
		playerPane = new TilePane(Orientation.VERTICAL,5,5);
		playerPane.setPrefRows(2);
		playerPane.setAlignment(Pos.CENTER);
		
		Label title = new Label("Current scores");
		title.setFont(scoreAreaNamesFont);
		
		
		TilePane names = new TilePane(Orientation.HORIZONTAL,5,5);
		names.setPrefColumns(5);
		names.setAlignment(Pos.CENTER);
		
		for(Player player : game.getPlayers()) {
			Label name = new Label(player.getPlayerName() + " : " + player.getScorecard().getGrandTotal());
			name.setPrefSize(100, 30);
			name.setFont(new Font("Arial Bold",20));
			name.setAlignment(Pos.CENTER);
			
			names.getChildren().add(name);
		}
		
		playerPane.getChildren().addAll(title,names);
	}
	
	
	public void setGame(Game game) {
		this.game = game;
	}

	

}
