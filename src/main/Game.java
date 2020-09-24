package main;

import dice.DieColour;
import gui.GUI;
import player.Player;

public class Game {

	Player[] players;
	int currentPlayerIndex;
	Player currentPlayer;
	boolean gameOver = false;
	GUI gui;
	
	public Game(Object[][] playerDetails, GUI gui) {
		players = new Player[playerDetails[0].length];
		for (int i = 0; i < playerDetails[0].length; i++) {
			players[i] = new Player((String)playerDetails[0][i],(DieColour)playerDetails[1][i]);
		}
		currentPlayerIndex = 0;
		currentPlayer = players[currentPlayerIndex];
		this.gui = gui;
	}

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
			gui.displayAllScores(true);
		}
	}
	
	public boolean isFinished() {
		
		for(Player player : players) {
			if(!player.isFinished()) {
				return false;
			}
		}
		gameOver = true;
		return true;
	}

	/*
	 * Getters and Setters
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGui(GUI gui) {
		this.gui = gui;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player[] getPlayers() {
		return players;
	}
}
