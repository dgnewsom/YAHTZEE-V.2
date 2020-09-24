package main;

import dice.DieColour;
import gui.GUI;
import player.Player;

public class Game {

	Player[] players;
	int currentPlayerIndex;
	Player currentPlayer;

	GUI gui;
	
	public Game(Object[][] playerDetails) {
		players = new Player[playerDetails[0].length];
		for (int i = 0; i < playerDetails[0].length; i++) {
			players[i] = new Player((String)playerDetails[0][i],(DieColour)playerDetails[1][i]);
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
