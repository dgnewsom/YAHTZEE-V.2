package player;

import dice.Dice;
import dice.DieColour;
import scoring.ScoreCard;

public class Player {

	private Dice dice;
	private ScoreCard scorecard;
	private String playerName;
	private int throwsRemaining;
	private DieColour dieColour;

	public Player(String name, DieColour dieColour) {
		dice = new Dice();
		scorecard = new ScoreCard();
		playerName = name;
		throwsRemaining = 2;
		this.dieColour = dieColour;
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

	public void resetThrowsRemaining() {
		RollDice();
		throwsRemaining = 2;
	}

	public DieColour getDieColour() {
		return dieColour;
	}

	public void setDieColour(DieColour dieColour) {
		this.dieColour = dieColour;
	}
	
	
}
