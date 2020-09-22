package main;

import gui.GUI;
import player.Player;

public class Game {

	Player[] players;
	int currentPlayerIndex;
	Player currentPlayer;

	GUI gui;
	
	public Game(String[] playerNames) {
		players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			players[i] = new Player(playerNames[i]);
		}
		currentPlayerIndex = 0;
		currentPlayer = players[currentPlayerIndex];
		
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
	}

	/*
	 * Getters and Setters
	 */
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
