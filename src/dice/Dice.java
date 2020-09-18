package dice;

import java.util.Set;

public class Dice {

	Die[] dice;
	
	String[][] diceDots;
	
	public Dice() {
		dice = new Die[5];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die();
		}
		diceDots = new String[][] {{"     ","*    ","*    ","*   *","*   *","*   *"},
								   {"  *  ","     ","  *  ","     ","  *  ","*   *"},
								   {"     ","    *","    *","*   *","*   *","*   *"}};
	}
	
	public Dice(int[] diceArr) {
		dice = new Die[5];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(diceArr[i]);
		}
		diceDots = new String[][] {{"     ","*    ","*    ","*   *","*   *","*   *"},
			   {"  *  ","     ","  *  ","     ","  *  ","*   *"},
			   {"     ","    *","    *","*   *","*   *","*   *"}};
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
	
	public void PrintDice() {
		int[] diceArr = DiceAsIntArray();
		String[] held = new String[5];
		for (int i = 0; i < held.length; i++) {
			if(dice[i].isHeld()) {
				held[i] = "X";
			}
			else {
				held[i] = " ";
			}
		}
		
		System.out.println(String.format("          1        2        3        4        5\n"
						               + "       -------  -------  -------  -------  ------- \n"
				                       + "      | %5s || %5s || %5s || %5s || %5s |\n"
				                       + "      | %5s || %5s || %5s || %5s || %5s |\n"
				                       + "      | %5s || %5s || %5s || %5s || %5s |\n"
				                       + "       -------  -------  -------  -------  ------- \n\n"
				                       + "Held?     %1s        %1s        %1s        %1s        %1s\n", 
				                       diceDots[0][diceArr[0]-1],diceDots[0][diceArr[1]-1],diceDots[0][diceArr[2]-1],diceDots[0][diceArr[3]-1],diceDots[0][diceArr[4]-1]
				                      ,diceDots[1][diceArr[0]-1],diceDots[1][diceArr[1]-1],diceDots[1][diceArr[2]-1],diceDots[1][diceArr[3]-1],diceDots[1][diceArr[4]-1]
		                              ,diceDots[2][diceArr[0]-1],diceDots[2][diceArr[1]-1],diceDots[2][diceArr[2]-1],diceDots[2][diceArr[3]-1],diceDots[2][diceArr[4]-1]
		                              ,held[0],held[1],held[2],held[3],held[4]));
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
