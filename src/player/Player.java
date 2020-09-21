package player;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import dice.Dice;
import scoring.Category;
import scoring.ScoreCard;

public class Player {

	private Dice dice;
	private ScoreCard scorecard;
	private String playerName;
	private int throwsRemaining;

	public Player(String name) {
		dice = new Dice();
		scorecard = new ScoreCard();
		playerName = name;
		throwsRemaining = 3;
	}

	public void RollDice() {
		if(throwsRemaining > 0) {
			dice.RollDice();
			throwsRemaining --;
		}
		if(throwsRemaining == 0) {
			dice.CancelHeldDice();
		}
	}


	private void ChooseScoreCategory(Category category) {

		boolean validCategory = false;
		
		dice.CancelHeldDice();
		dice.RollDice();
		
	}
	
	/*
	 * Getters and setters
	 */
	
	public Dice getDice() {
		return dice;
	}

	public ScoreCard getScorecard() {
		return scorecard;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public int getThrowsRemaining() {
		return throwsRemaining;
	}
}
