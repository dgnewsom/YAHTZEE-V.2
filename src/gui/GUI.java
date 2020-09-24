package gui;



import dice.Dice;
import dice.Die;
import dice.DieButton;
import dice.DieColour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Game;
import main.Main;
import player.Player;
import scoring.Category;

public class GUI {

	private Game game;
	private Object[][] playerDetails = new Object[][]{{"Danny","Louie"},{DieColour.WHITE,DieColour.BLACK}};
	private BorderPane root;
	
	/*
	 * Backgrounds
	 */
	private Background greenBackground = new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
	private Background darkGreenBackground = new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY));
	
	/*
	 * Fonts
	 */
	//Dice Pane
	private Font dicePaneFont = new Font("Arial Bold", 40);
	
	//ScoreCard
	private Font playerNameFont = new Font("Arial Bold", 45);
	private Font sectionTitleFont = new Font("Arial Bold", 25);
	private Font boldTitleFont = new Font("Arial Bold", 15);
	private Font titleFont = new Font("Arial", 15);
	private Font descriptionFont = new Font("Arial", 15);
	private Font scoreButtonFont = new Font("Arial Bold",15);
	private Font grandTotalFont = new Font("Arial Bold", 30);
	
	//Players pane
	private Font scoreAreaNamesFont = new Font("Arial Bold", 30);
	
	/*
	 * Menu Fields
	 */
	private MenuBar menu;
	private Menu file;
	private Menu players;
	
	/*
	 * Dice pane fields
	 */
	private TilePane dicePane;
	private Label rollNumber;
	private Label throwsRemaining;
	private TilePane diceButtons;
	private Button rollButton;
	/*
	 * Score pane fields
	 */
	private int scoreCardWidth = 700;
	private VBox scoreCardPane;
	private TilePane playerName;
	
	private int rowHeight = 30;
	private int leftColumnWidth = 200;
	private int centreColumnWidth = 100;
	private int rightColumnWidth = 300;
	private int totalsRowHeight = 25;
	
	//Upper Section
	private VBox upperSection;
	private TilePane upperTitle;
	private FlowPane upperScores;
	private TilePane upperCategoryLabels;
	private TilePane upperScoreLabels;
	private TilePane upperScoreButtons;
	private HBox upperTotals;
	private TilePane upperTotalLabels;
	private TilePane upperTotalScores;
	private TilePane upperTotalDescriptions;
	
	
	//Lower Section
	private VBox lowerSection;
	private TilePane lowerTitle;
	private FlowPane lowerScores;
	private TilePane lowerCategoryLabels;
	private TilePane lowerScoreLabels;
	private TilePane lowerScoreButtons;
	private HBox lowerTotals;
	private TilePane lowerTotalLabels;
	private TilePane lowerTotalScores;
	private TilePane lowerTotalDescriptions;
	
	
	/*
	 * Player pane fields
	 */
	private FlowPane playerBorder;
	private TilePane playerPane;
	private HBox grandTotals;

	
	
	
	public void constructWindow() {
		
		root = new BorderPane();
		createMenuBar();
		createDicePane();
		createScorePane();
		createPlayersPane();
		
		
		
		HBox gamePane = new HBox() ;
		gamePane.getChildren().addAll(dicePane,scoreCardPane);
		
		root.setTop(menu);
		root.setCenter(gamePane);
		root.setBottom(playerBorder);
		
		
		Scene scene = new Scene(root);
		Main.getStage().setScene(scene);
		Main.getStage().sizeToScene();
		Main.getStage().show();
	}

	private void createMenuBar() {
		menu = new MenuBar();
		
		file = new Menu("_File");
		MenuItem newGame = new MenuItem("_New");
		newGame.setOnAction(e->{Main.startGame(Main.getStage());});
		newGame.setAccelerator(new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN));
		
		/*
		 * To Add=============================================================
		MenuItem save = new MenuItem("_Save");
		//save.setOnAction(e->{saveContacts();});
		MenuItem load = new MenuItem("_Load");
		//load.setOnAction(e->{loadContacts();});
		*/
		
		file.getItems().addAll(newGame);
		
		players = new Menu("Player Details");
		
		for(Player player : game.getPlayers()) {
			Menu playerMenu = new Menu(player.getPlayerName());
			
			MenuItem scoreCard = new MenuItem(String.format("Display %s's scorecard", player.getPlayerName()));
			scoreCard.setOnAction(e->{displayScorecard(player);});
			
			Menu colours = new Menu(String.format("Change %s's dice colour", player.getPlayerName()));
			for(DieColour colour : DieColour.values()) {
				MenuItem colourMenuItem = new MenuItem("");
				colourMenuItem.setGraphic(new ImageView(String.format("/images/dice/Menu/%s.png",colour.getDescriptionName())));
				colourMenuItem.setOnAction(e->{
					player.setDieColour(colour);
					constructWindow();
				});
				colours.getItems().add(colourMenuItem);
			}
			
			playerMenu.getItems().addAll(scoreCard,colours);
			players.getItems().add(playerMenu);
		}
		
		menu.getMenus().addAll(file,players);
		
	}

	private void createDicePane() {
		
		dicePane = new TilePane(Orientation.VERTICAL);
		dicePane.setAlignment(Pos.CENTER);
		dicePane.setPadding(new Insets(5));
		dicePane.setPrefSize(800, 800);
		dicePane.setBackground(greenBackground );
		
		/*
		 * Players name
		 */
		Label nameLabel = new Label(String.format("%s's turn.", game.getCurrentPlayer().getPlayerName()));
		nameLabel.setFont(playerNameFont);
		nameLabel.setTextFill(Color.WHITE);
		nameLabel.setPrefHeight(50);
		playerName = new TilePane(nameLabel);
		playerName.setAlignment(Pos.CENTER);
		playerName.setPadding(new Insets(5,10,5,10));
		playerName.setPrefWidth(scoreCardWidth);
		playerName.setMaxHeight(60);
		dicePane.getChildren().add(playerName);
		
		rollNumber = new Label(String.format("Roll %d", 4-game.getCurrentPlayer().getThrowsRemaining()));	
		rollNumber.setFont(dicePaneFont);
		rollNumber.setAlignment(Pos.CENTER);
		rollNumber.setTextFill(Color.WHITE);
		if(game.getCurrentPlayer().getThrowsRemaining()<=0) {
			rollNumber.setVisible(false);
		}
		
		String throwsLabelString;
		if(game.getCurrentPlayer().getThrowsRemaining() == 1) {
			throwsLabelString = String.format("%d Roll remaining...", game.getCurrentPlayer().getThrowsRemaining());
		}
		else if(game.getCurrentPlayer().getThrowsRemaining() == 0){
			throwsLabelString = String.format(game.getCurrentPlayer().getPlayerName() + " please enter your score...", game.getCurrentPlayer().getThrowsRemaining());
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
			temp.setImage(game.getCurrentPlayer().getDieColour());
			temp.setBackground(null);
			temp.setOnMouseClicked(e->{if(game.getCurrentPlayer().getThrowsRemaining() > 0)
									   temp.Click(e);
									   constructWindow();});
			diceButtons.getChildren().add(temp);
		}
		if(game.getCurrentPlayer().getThrowsRemaining()>=3) {
			diceButtons.setVisible(false);
		}
		
		rollButton = new Button("Roll Dice");
		rollButton.setFont(dicePaneFont);
		rollButton.setTextFill(Color.WHITE);
		rollButton.setBackground(darkGreenBackground);
		if(game.getCurrentPlayer().getThrowsRemaining() < 1) {
       	 	rollButton.setDisable(true);
        }
		rollButton.setOnMouseClicked(e->{game.getCurrentPlayer().RollDice();
		                                 constructWindow();
		                                 
		});
		
		dicePane.getChildren().addAll(rollNumber,throwsRemaining, diceButtons, rollButton);
	}
	
	private void createScorePane() {
		
		scoreCardPane = new VBox(10);
		scoreCardPane.setBackground(greenBackground);
		scoreCardPane.setPadding(new Insets(10,20,10,20));
		
		/*
		 * Upper section
		 */
		
		upperSection = new VBox();
		upperSection.setPadding(new Insets(10,20,10,20));
		upperSection.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
		
		//Title
		Label upperTitleLabel = new Label("Upper Section");
		upperTitleLabel.setFont(sectionTitleFont);
		upperTitleLabel.setTextFill(Color.WHITE);
		//upperTitleLabel.setPrefHeight(30);
		upperTitle = new TilePane(upperTitleLabel);
		upperTitle.setAlignment(Pos.CENTER);
		upperTitle.setPadding(new Insets(5,0,20,0));
		upperTitle.setPrefWidth(scoreCardWidth);
		upperSection.getChildren().add(upperTitle);
		
		//Categories / Scores / Buttons
		upperScores = new FlowPane();
		upperScores.setPadding(new Insets(0,10,0,10));
		upperScores.setAlignment(Pos.CENTER);
		
		upperCategoryLabels = new TilePane(Orientation.VERTICAL,0,2);
		upperCategoryLabels.setPrefRows(6);
		
		upperScoreLabels = new TilePane(Orientation.VERTICAL,0,2);
		upperScoreLabels.setPrefRows(6);
		
		upperScoreButtons = new TilePane(Orientation.VERTICAL,0,2);
		upperScoreButtons.setPrefRows(6);
		
		for(Category category : game.getCurrentPlayer().getScorecard().getUpperCategories()) {
			Label name = new Label(category.getDescriptionName());
			name.setPrefSize(leftColumnWidth, rowHeight);
			name.setFont(titleFont);
			name.setTextFill(Color.WHITE);
			Label score = new Label("---");
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				score = new Label(String.format("%d", game.getCurrentPlayer().getScorecard().getCategoryScore(category)));
			}
			score.setPrefSize(centreColumnWidth, rowHeight);
			score.setAlignment(Pos.CENTER);
			score.setFont(titleFont);
			score.setTextFill(Color.WHITE);
			Button submit = new Button(String.format("Score %s for %s", Category.CheckScore(game.getCurrentPlayer().getDice(),category), category.getDescriptionName()));
			submit.setPrefSize(rightColumnWidth, rowHeight);
			submit.setFont(scoreButtonFont);
			submit.setTextFill(Color.WHITE);
			submit.setBackground(darkGreenBackground);
			submit.setAlignment(Pos.CENTER);
			submit.setOnMouseClicked(e->{
				game.getCurrentPlayer().getScorecard().setScore(game.getCurrentPlayer().getDice(), category);
				game.getCurrentPlayer().getScorecard().Calculate();
				game.getCurrentPlayer().getDice().CancelHeldDice();
				game.getCurrentPlayer().getDice().RollDice();
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
		upperSection.getChildren().add(upperScores);
		
		upperTotals = new HBox();
		upperTotals.setPrefHeight(100);
		upperTotals.setPadding(new Insets(10));
		upperTotals.setAlignment(Pos.CENTER);
		
		upperTotalLabels = new TilePane(Orientation.VERTICAL, 0, 0);
		
		Label upperSubTotalLabel = new Label("Sub Total");
		upperSubTotalLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		upperSubTotalLabel.setFont(boldTitleFont);
		upperSubTotalLabel.setTextFill(Color.WHITE);
		Label upperBonusLabel = new Label("Upper Bonus");
		upperBonusLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		upperBonusLabel.setFont(boldTitleFont);
		upperBonusLabel.setTextFill(Color.WHITE);
		Label upperTotalLabel = new Label("Upper Section Total");
		upperTotalLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		upperTotalLabel.setFont(boldTitleFont);
		upperTotalLabel.setTextFill(Color.WHITE);
		upperTotalLabels.getChildren().addAll(upperSubTotalLabel,upperBonusLabel,upperTotalLabel);
		
		upperTotalScores = new TilePane(Orientation.VERTICAL, 0, 0);
		
		Label upperSubTotalScore = new Label("" + game.getCurrentPlayer().getScorecard().getUpperSubTotal());
		upperSubTotalScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		upperSubTotalScore.setFont(boldTitleFont);
		upperSubTotalScore.setAlignment(Pos.CENTER);
		upperSubTotalScore.setTextFill(Color.WHITE);
		Label upperBonusScore = new Label("" + game.getCurrentPlayer().getScorecard().getUpperBonus());
		upperBonusScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		upperBonusScore.setFont(boldTitleFont);
		upperBonusScore.setAlignment(Pos.CENTER);
		upperBonusScore.setTextFill(Color.WHITE);
		Label upperTotalScore = new Label("" + game.getCurrentPlayer().getScorecard().getUpperTotal());
		upperTotalScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		upperTotalScore.setFont(boldTitleFont);
		upperTotalScore.setAlignment(Pos.CENTER);
		upperTotalScore.setTextFill(Color.WHITE);
		
		upperTotalScores.getChildren().addAll(upperSubTotalScore,upperBonusScore,upperTotalScore);
		
		upperTotalDescriptions = new TilePane(Orientation.VERTICAL,0,0);
		
		Label upperSubTotalDescription = new Label("");
		upperSubTotalDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		upperSubTotalDescription.setFont(descriptionFont);
		upperSubTotalDescription.setTextFill(Color.WHITE);
		Label upperBonusDescription = new Label("(Score 35 if subtotal > 63)");
		upperBonusDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		upperBonusDescription.setFont(descriptionFont);
		upperBonusDescription.setAlignment(Pos.CENTER);
		upperBonusDescription.setTextFill(Color.WHITE);
		Label upperTotalDescription = new Label("");
		upperTotalDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		upperTotalDescription.setFont(descriptionFont);
		upperTotalDescription.setTextFill(Color.WHITE);
		
		upperTotalDescriptions.getChildren().addAll(upperSubTotalDescription,upperBonusDescription,upperTotalDescription);
		
		upperTotals.getChildren().addAll(upperTotalLabels,upperTotalScores,upperTotalDescriptions);
		upperSection.getChildren().add(upperTotals);
		scoreCardPane.getChildren().add(upperSection);
		
		/*
		 * Lower section
		 */
		
		lowerSection = new VBox();
		lowerSection.setPadding(new Insets(10,20,0,20));
		lowerSection.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
		//Title
		Label lowerTitleLabel = new Label("Lower Section");
		lowerTitleLabel.setFont(sectionTitleFont);
		lowerTitleLabel.setTextFill(Color.WHITE);
		lowerTitle = new TilePane(lowerTitleLabel);
		lowerTitle.setAlignment(Pos.CENTER);
		lowerTitleLabel.setPadding(new Insets(5,0,20,0));
		lowerTitle.setPrefWidth(scoreCardWidth);
		//lowerTitle.setMaxHeight(30);
		lowerSection.getChildren().add(lowerTitle);
		
		//Categories / Scores / Buttons
		lowerScores = new FlowPane();
		lowerScores.setPadding(new Insets(0,10,0,10));
		lowerScores.setAlignment(Pos.CENTER);
		
		lowerCategoryLabels = new TilePane(Orientation.VERTICAL, 0, 2);
		lowerCategoryLabels.setPrefRows(7);
		
		lowerScoreLabels = new TilePane(Orientation.VERTICAL, 0, 2);
		lowerScoreLabels.setPrefRows(7);
		
		lowerScoreButtons = new TilePane(Orientation.VERTICAL, 0, 2);
		lowerScoreButtons.setPrefRows(7);
		
		for(Category category : game.getCurrentPlayer().getScorecard().getLowerCategories()) {
			Label name = new Label(category.getDescriptionName());
			name.setPrefSize(leftColumnWidth, rowHeight);
			name.setFont(titleFont);
			name.setTextFill(Color.WHITE);
			Label score = new Label("---");
			if(game.getCurrentPlayer().getScorecard().getCategoryScore(category) != null) {
				score = new Label(String.format("%d", game.getCurrentPlayer().getScorecard().getCategoryScore(category)));
			}
			score.setPrefSize(centreColumnWidth, rowHeight);
			score.setAlignment(Pos.CENTER);
			score.setFont(titleFont);
			score.setTextFill(Color.WHITE);
			Button submit = new Button(String.format("Score %s for %s", Category.CheckScore(game.getCurrentPlayer().getDice(),category), category.getDescriptionName()));
			submit.setPrefSize(rightColumnWidth, rowHeight);
			submit.setFont(scoreButtonFont);
			submit.setTextFill(Color.WHITE);
			submit.setBackground(darkGreenBackground);
			submit.setAlignment(Pos.CENTER);
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
		lowerSection.getChildren().add(lowerScores);
		
		lowerTotals = new HBox();
		lowerTotals.setPrefHeight(130);
		lowerTotals.setPadding(new Insets(10));
		lowerTotals.setAlignment(Pos.CENTER);
		
		lowerTotalLabels = new TilePane(Orientation.VERTICAL, 0, 0);
		
		Label lowerSubTotalLabel = new Label("Sub Total");
		lowerSubTotalLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		lowerSubTotalLabel.setFont(boldTitleFont);
		lowerSubTotalLabel.setTextFill(Color.WHITE);
		Label lowerBonusYahtzeesLabel = new Label("Bonus Yahtzees");
		lowerBonusYahtzeesLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		lowerBonusYahtzeesLabel.setFont(boldTitleFont);
		lowerBonusYahtzeesLabel.setTextFill(Color.WHITE);
		Label lowerBonusLabel = new Label("Lower Bonus");
		lowerBonusLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		lowerBonusLabel.setFont(boldTitleFont);
		lowerBonusLabel.setTextFill(Color.WHITE);
		Label lowerTotalLabel = new Label("Lower Section Total");
		lowerTotalLabel.setPrefSize(leftColumnWidth, totalsRowHeight);
		lowerTotalLabel.setFont(boldTitleFont);
		lowerTotalLabel.setTextFill(Color.WHITE);
		
		
		lowerTotalLabels.getChildren().addAll(lowerSubTotalLabel,lowerBonusYahtzeesLabel,lowerBonusLabel,lowerTotalLabel);
		
		
		lowerTotalScores = new TilePane(Orientation.VERTICAL, 0, 0);
		
		Label lowerSubTotalScore = new Label("" + game.getCurrentPlayer().getScorecard().getLowerSubTotal());
		lowerSubTotalScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		lowerSubTotalScore.setFont(boldTitleFont);
		lowerSubTotalScore.setAlignment(Pos.CENTER);
		lowerSubTotalScore.setTextFill(Color.WHITE);
		Label lowerBonusYahtzeeScore = new Label("" + game.getCurrentPlayer().getScorecard().getBonusYahtzees());
		lowerBonusYahtzeeScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		lowerBonusYahtzeeScore.setFont(boldTitleFont);
		lowerBonusYahtzeeScore.setAlignment(Pos.CENTER);
		lowerBonusYahtzeeScore.setTextFill(Color.WHITE);
		Label lowerBonusScore = new Label("" + game.getCurrentPlayer().getScorecard().getLowerBonus());
		lowerBonusScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		lowerBonusScore.setFont(boldTitleFont);
		lowerBonusScore.setAlignment(Pos.CENTER);
		lowerBonusScore.setTextFill(Color.WHITE);
		Label lowerTotalScore = new Label("" + game.getCurrentPlayer().getScorecard().getLowerTotal());
		lowerTotalScore.setPrefSize(centreColumnWidth, totalsRowHeight);
		lowerTotalScore.setFont(boldTitleFont);
		lowerTotalScore.setAlignment(Pos.CENTER);
		lowerTotalScore.setTextFill(Color.WHITE);
		
		
		lowerTotalScores.getChildren().addAll(lowerSubTotalScore,lowerBonusYahtzeeScore,lowerBonusScore,lowerTotalScore);
		
		lowerTotalDescriptions = new TilePane(Orientation.VERTICAL, 0, 0);
		
		Label lowerSubTotalDescription = new Label("");
		lowerSubTotalDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		lowerSubTotalDescription.setFont(boldTitleFont);
		lowerSubTotalDescription.setTextFill(Color.WHITE);
		Label lowerBonusYahtzeesDescription = new Label("");
		lowerBonusYahtzeesDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		lowerBonusYahtzeesDescription.setFont(descriptionFont);
		lowerBonusYahtzeesDescription.setAlignment(Pos.CENTER);
		lowerBonusYahtzeesDescription.setTextFill(Color.WHITE);
		Label lowerBonusDescription = new Label("(Score 100 for each bonus YAHTZEE!)");
		lowerBonusDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		lowerBonusDescription.setFont(descriptionFont);
		lowerBonusDescription.setAlignment(Pos.CENTER);
		lowerBonusDescription.setTextFill(Color.WHITE);
		Label lowerTotalDescription = new Label("");
		lowerTotalDescription.setPrefSize(rightColumnWidth, totalsRowHeight);
		lowerTotalDescription.setFont(boldTitleFont);
		lowerTotalDescription.setAlignment(Pos.CENTER);
		lowerTotalDescription.setTextFill(Color.WHITE);
		
		
		lowerTotalDescriptions.getChildren().addAll(lowerSubTotalDescription,lowerBonusYahtzeesDescription,lowerBonusDescription,lowerTotalDescription);
		
		
		lowerTotals.getChildren().addAll(lowerTotalLabels,lowerTotalScores,lowerTotalDescriptions);
		lowerSection.getChildren().add(lowerTotals);
		scoreCardPane.getChildren().add(lowerSection);
		
		grandTotals = new HBox(50);
		grandTotals.setPadding(new Insets(10));
		grandTotals.setAlignment(Pos.CENTER);
		grandTotals.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
		
		Label grandTotalLabel = new Label("GRAND TOTAL");
		grandTotalLabel.setFont(grandTotalFont);
		grandTotalLabel.setTextFill(Color.WHITE);
		
		Label grandTotalScore = new Label("" + game.getCurrentPlayer().getScorecard().getGrandTotal());
		grandTotalScore.setFont(grandTotalFont);
		grandTotalScore.setAlignment(Pos.CENTER);
		grandTotalScore.setTextFill(Color.WHITE);
		
		Label grandTotalDescription = new Label("");
		grandTotalDescription.setPrefSize(rightColumnWidth, rowHeight);
		grandTotalDescription.setFont(grandTotalFont);
		grandTotalDescription.setTextFill(Color.WHITE);
		
		grandTotals.getChildren().addAll(grandTotalLabel,grandTotalScore);
		scoreCardPane.getChildren().add(grandTotals);
		
	}
	
	private void createPlayersPane() {
		
		playerBorder = new FlowPane();
		playerBorder.setPadding(new Insets(0,5,5,5));
		playerBorder.setBackground(greenBackground);
		
		playerPane = new TilePane(Orientation.VERTICAL,5,5);
		playerPane.setPrefRows(2);
		playerPane.setAlignment(Pos.CENTER);
		playerPane.setPadding(new Insets(10));
		playerPane.setBackground(greenBackground);
		playerPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
		
		Label title = new Label("Current scores");
		title.setFont(scoreAreaNamesFont);
		title.setTextFill(Color.WHITE);
		
		
		TilePane names = new TilePane(Orientation.HORIZONTAL,5,5);
		names.setPrefColumns(10);
		names.setAlignment(Pos.CENTER);
		
		for(Player player : game.getPlayers()) {
			Label name = new Label(player.getPlayerName() + " : " + player.getScorecard().getGrandTotal());
			name.setPrefSize(150, 30);
			name.setFont(new Font("Arial Bold",20));
			name.setTextFill(Color.WHITE);
			name.setAlignment(Pos.CENTER);
			names.getChildren().add(name);
		}
		
		playerPane.getChildren().addAll(title,names);
		playerBorder.getChildren().add(playerPane);
	}
	
	
	public void setGame(Game game) {
		this.game = game;
	}

	public Object[][] getPlayerDetails() {
		/*
		setNumberOfPlayers();
		setPlayerDetails();
		*/
		return playerDetails;
	}
	
	private void setPlayerDetails() {
		
		playerDetails = new Object[2][Main.getNumberOfPlayers()];
		
		/*
		 * Create observable list of colours for the spinner on the dialog
		 */
		
		ObservableList<DieColour> colourList = FXCollections.observableArrayList(DieColour.values());
		
		/*
		 * Create and initialise the popup window
		 */
        final Stage dialog = new Stage();
        BorderPane rootPane = new BorderPane();
        dialog.setTitle("Enter Player Details");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getStage());
        //dialog.getIcons().add(new Image(Images.ICON.getImage()));
        dialog.setHeight((550));
        dialog.setWidth(500);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(e->{System.exit(0);});
		
        /*
		 * Create and initialise title pane for the popup        
		 */
        TilePane titlePane = new TilePane();
        titlePane.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Please enter each players name and die colour.");
        titleLabel.setFont(new Font("Arial Bold",18));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setPadding(new Insets(30,0,30,0));
        titlePane.getChildren().add(titleLabel);
        
        /*
         * Create panes for each player input
         */
        TilePane inputs = new TilePane(Orientation.VERTICAL,5,5);
        inputs.setAlignment(Pos.CENTER);
        inputs.setPadding(new Insets(20));
        for (int i = 0; i < Main.getNumberOfPlayers(); i++) {
			HBox playerInput = new HBox(10);
			TextField nameInput = new TextField(String.format("Player %d", i+1));
			nameInput.setAlignment(Pos.CENTER);
			ComboBox<DieColour> playerColour = new ComboBox<>(colourList);
			playerColour.setCellFactory(lv -> new ListCell<DieColour>(){

			    @Override
			    protected void updateItem(DieColour item, boolean empty) {
			        super.updateItem(item, empty);

			        if (empty || item == null) {
			            setBackground(Background.EMPTY);
			            setText("");
			        } else {
			            setBackground(new Background(new BackgroundFill(item.getColour(),
			                                                            CornerRadii.EMPTY,
			                                                            Insets.EMPTY)));
			            setText("");
			        }
			    }

			});
			playerColour.setOnAction(e->{
				playerColour.setBackground(new Background(new BackgroundFill(playerColour.getValue().getColour(), CornerRadii.EMPTY, Insets.EMPTY)));
				});
			playerColour.setValue(DieColour.WHITE);
			playerColour.setBackground(new Background(new BackgroundFill(playerColour.getValue().getColour(), CornerRadii.EMPTY, Insets.EMPTY)));
			playerInput.getChildren().addAll(nameInput,playerColour);
			inputs.getChildren().add(playerInput);
		}
        
        /*
         * Create pane for the buttons 
         */
        TilePane buttons = new TilePane();
        buttons.setPadding(new Insets(20));
        buttons.setHgap(10);
        BorderPane buttonsPane = new BorderPane();
        
        /*
         * Create the buttons for applying the settings or cancelling
         * and set event handlers
         */
        Button okButton = new Button("OK");
        okButton.setMinWidth(75);
        //Set event to set custom parameters close window and start new game
        okButton.setOnMouseClicked(e->{
        	for(int i = 0; i < inputs.getChildren().size();i++) {
        		HBox row = (HBox)inputs.getChildren().get(i);
        		TextField nameText = (TextField) row.getChildren().get(0);
        		ComboBox<DieColour> colourCombo = (ComboBox<DieColour>) row.getChildren().get(1);
        		String name = nameText.getText();
        		DieColour colour = colourCombo.getValue();
        		playerDetails[0][i] = name;
        		playerDetails[1][i] = colour;
        	}
        	dialog.close();
        });

        //add buttons to buttons pane
        buttons.getChildren().addAll(okButton);
        buttonsPane.setRight(buttons);;
        
        //Add three main panes to root pane
        rootPane.setTop(titlePane);
        rootPane.setCenter(inputs);
        rootPane.setBottom(buttonsPane);
        
        //create and set scene and add to main window
        Scene dialogScene = new Scene(rootPane, 500, 40);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
        
	}

	private void setNumberOfPlayers() {
		/*
		 * Create observable list from 1 - 10 for the spinner on the dialog
		 */
		Integer[] range = new Integer[10];
		for (int i = 0; i < range.length; i++) {
			range[i] = i+1;
		}
		ObservableList<Integer> rangeList = FXCollections.observableArrayList(range);
		
		/*
		 * Create and initialise the popup window
		 */
        final Stage dialog = new Stage();
        BorderPane rootPane = new BorderPane();
        dialog.setTitle("How Many Players?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.getStage());
        //dialog.getIcons().add(new Image(Images.ICON.getImage()));
        dialog.setHeight(250);
        dialog.setWidth(500);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(e->{System.exit(0);});
		
        /*
		 * Create and initialise title pane for the popup        
		 */
        TilePane titlePane = new TilePane();
        titlePane.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Please select the number of players");
        titleLabel.setFont(new Font(18));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setPadding(new Insets(15,0,15,0));
        titlePane.getChildren().add(titleLabel);
        
        /*
         * Create pane for the buttons 
         */
        TilePane buttons = new TilePane();
        buttons.setPadding(new Insets(20));
        buttons.setHgap(10);
        BorderPane buttonsPane = new BorderPane();
        
        /*
         * Create Combobox
         */
        ComboBox<Integer> playersComboBox = new ComboBox<Integer>(rangeList);
        playersComboBox.setValue(1);
        
        /*
         * Create the buttons for applying the settings or cancelling
         * and set event handlers
         */
        Button okButton = new Button("OK");
        okButton.setMinWidth(75);
        //Set event to set custom parameters close window and start new game
        okButton.setOnMouseClicked(e->{Main.setNumberOfPlayers(playersComboBox.getValue());
        							   dialog.close();});
        
        //add buttons to buttons pane
        buttons.getChildren().addAll(okButton);
        buttonsPane.setRight(buttons);;
        
        //Add three main panes to root pane
        rootPane.setTop(titlePane);
        rootPane.setCenter(playersComboBox);
        rootPane.setBottom(buttonsPane);
        
        //create and set scene and add to main window
        Scene dialogScene = new Scene(rootPane, 500, 40);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
	}
	
	private void displayScorecard(Player player) {
		System.out.println(String.format("%s's Scorcard displayed", player.getPlayerName()));
	}
}
