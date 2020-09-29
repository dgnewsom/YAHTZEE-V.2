package dice;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @author Danny Newsom
 *
 */
public class Dice implements Serializable {

	private static final long serialVersionUID = -5846231332472789185L;

	Die[] dice;
	
	String[][] diceDots;
	
	public Dice() {
		dice = new Die[5];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die();
		}
	}
	
	public Dice(int[] diceArr) {
		dice = new Die[5];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(diceArr[i]);
		}
	}
	
	public void ToggleHold(int die) {
		if(die >= 0 && die <= 5) {
			dice[die].toggleHeld();
		}
	}
	
	public void RollDice() {
		for(Die die : dice) {
			die.RollDie();
		}
	}
	
	public int[] DiceAsIntArray() {
		int[] diceArr = new int[dice.length];
		for (int i = 0; i < diceArr.length; i++) {
			diceArr[i] = dice[i].getCurrentValue();
		}
		return diceArr;
	}
	
	public Boolean IsYahtzee() {
		for(int i = 1; i<=6;i++) {
			if(CountOccurences(DiceAsIntArray(), i) ==5) {
				return true;
			}
		}
		return false;
	}
	
	private static int CountOccurences(int[] arr, int num) {
		int result = 0;
		for (int i : arr) {
			if(i == num) {
				result++;
			}
		}
		return result;
	}

	public void HoldDice(Set<Integer> diceInts) {
		
		for (Integer integer : diceInts) {
			ToggleHold(integer -1);
		}
	}

	public void CancelHeldDice() {
		for(Die die : dice) {
			die.Unhold();
		}
	}
	
	/*
	 * Getters and Setters
	 */
	
	public Die[] getDieArray() {
		return dice;
	}
}
