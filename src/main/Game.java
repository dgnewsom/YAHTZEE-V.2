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
