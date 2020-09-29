package application;

import java.io.Serializable;
import java.time.LocalDateTime;

import dice.DieColour;
import gui.GUI;
import player.Player;
import scoring.HighScores;

/**
 * Game class to represent a Yahtzee game instance
 * @author Danny Newsom
 * @version 2.0
 *
 */
public class Game implements Comparable<Game>,Serializable{
	
	//random long for serialization 
	private static final long serialVersionUID = 6005740228739669114L;
	//array of players
	Player[] players;
	//current player pointers
	int currentPlayerIndex;
	Player currentPlayer;
	//Date and time game started / saved
	LocalDateTime date;
	//link to current gui instance
	transient GUI gui;
	
	/**
	 * Constructor for the Game class
	 * @param playerDetails Object[][] array containing array of player name strings and array of DieColors
	 * @param gui current GUI object to assign to the game object
	 */
	public Game(Object[][] playerDetails, GUI gui) {
		players = new Player[playerDetails[0].length];
		for (int i = 0; i < playerDetails[0].length; i++) {
			players[i] = new Player((String)playerDetails[0][i],(DieColour)playerDetails[1][i]);
		}
		currentPlayerIndex = 0;
		currentPlayer = players[currentPlayerIndex];
		this.gui = gui;
		HighScores.importHighScoresFromFile();
		date = LocalDateTime.now();
	}

	/**
	 * Method to switch game to the next player in the players array
	 * if out of bound start at index 0
	 */
	public void nextPlayer() {
		if(currentPlayerIndex == players.length-1) {
			currentPlayerIndex = 0;
		}
		else {
			currentPlayerIndex ++;
		}
		currentPlayer = players[currentPlayerIndex];
		currentPlayer.resetThrowsRemaining();
		currentPlayer.getDice().RollDice();
		if(isFinished()) {
			for(Player player : players) {
				HighScores.checkHighScore(player);
			}
			gui.displayAllScores(true);
		}
	}
	
	/**
	 * Method to check if all score fields are 
	 * entered for each player in the players array
	 * @return true if game is complete, false otherwise
	 */
	public boolean isFinished() {
		
		for(Player player : players) {
			if(!player.isFinished()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Getters and Setters
	 */
	/**
	 * Setter for the current gui field
	 * @param gui GUI object representing the current GUI object
	 */
	public void setGui(GUI gui) {
		this.gui = gui;
	}
	
	/**
	 * Getter for the currentPlayer field
	 * @return Player object representing the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Getter for the players array
	 * @return Player[] of all players
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	/**
	 * Getter for the date field
	 * @return LocalDateTime object representing the games date and time
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Setter for the date field
	 * @param date LocalDateTime object representing the games date and time
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Override for the compareTo method to sort by date/time
	 */
	@Override
	public int compareTo(Game other) {
		return other.getDate().compareTo(this.getDate());
	}
}
