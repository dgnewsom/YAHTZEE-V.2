package player;

import java.io.Serializable;

import dice.Dice;
import dice.DieColour;
import scoring.ScoreCard;

public class Player implements Comparable<Player>, Serializable{

	
	private static final long serialVersionUID = -6691286326952273885L;
	private Dice dice;
	private ScoreCard scorecard;
	private String playerName;
	private int throwsRemaining;
	private DieColour dieColour;

	public Player(String name, DieColour dieColour) {
		dice = new Dice();
		scorecard = new ScoreCard();
		playerName = name;
		throwsRemaining = 3;
		this.dieColour = dieColour;
	}

	public void RollDice() {
		if(throwsRemaining > 0) {
			dice.RollDice();
			throwsRemaining --;
		}
	}
	
	public boolean isFinished() {
		return scorecard.IsFinished();
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
		throwsRemaining = 3;
	}

	public DieColour getDieColour() {
		return dieColour;
	}

	public void setDieColour(DieColour dieColour) {
		this.dieColour = dieColour;
	}

	public int compareTo(Player other) {
		if(this.scorecard.getGrandTotal() != other.getScorecard().getGrandTotal()) {
			return Integer.compare(other.getScorecard().getGrandTotal(), this.getScorecard().getGrandTotal());
		}
		else {
			return this.getPlayerName().compareTo(other.getPlayerName());
		}
	}
	
	
}
