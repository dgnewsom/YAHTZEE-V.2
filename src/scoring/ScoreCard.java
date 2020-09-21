package scoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dice.Dice;

public class ScoreCard {


	private List<Category> categoriesUpper = Arrays.asList(Category.ACES,Category.TWOS,Category.THREES,Category.FOURS,Category.FIVES,Category.SIXES);
	private List<Category> categoriesLower = Arrays.asList(Category.THREEOFAKIND,Category.FOUROFAKIND,Category.FULLHOUSE,Category.SMALLSTRAIGHT,Category.LARGESTRAIGHT,Category.YAHTZEE,Category.CHANCE);

	private Map<Category, Integer> scoresUpper;
	private int subTotalUpper;
	private int bonusUpper;
	private int totalUpper;

	private Map<Category, Integer> scoresLower;
	private int subTotalLower;
	private int bonusYahtzees;
	private int bonusLower;
	private int totalLower;
	private int grandTotal;


	public int getGrandTotal() {
		return grandTotal;
	}

	public ScoreCard() {
		scoresUpper = new HashMap<Category,Integer>();
		for (Category category : categoriesUpper) {
			scoresUpper.put(category, null);
		}

		subTotalUpper = 0;
		bonusUpper = 0;
		totalUpper = 0;

		scoresLower = new HashMap<Category,Integer>();
		for (Category category : categoriesLower) {
			scoresLower.put(category, null);
		}

		bonusYahtzees = 0;
		totalLower = 0;

		grandTotal = 0;
	}

	public void Calculate() {

		subTotalUpper = 0;
		for(Category category : categoriesUpper) {
			if(scoresUpper.get(category) == null) {
				subTotalUpper += 0;
			}
			else {
				subTotalUpper += (int)scoresUpper.get(category);
			}
		}

		if(subTotalUpper >=63) {
			bonusUpper = 35;
		}

		totalUpper = bonusUpper + subTotalUpper;

		subTotalLower = 0;
		for(Category category : categoriesLower) {
			if(scoresLower.get(category) == null) {
				subTotalLower += 0;
			}
			else {
				subTotalLower += (int)scoresLower.get(category);
			}
		}

		bonusLower = bonusYahtzees * 100;

		totalLower = bonusLower + subTotalLower;

		grandTotal = totalUpper + totalLower;
	}

	private boolean setScoreUpper(Dice dice, Category category) {

		if(dice.IsYahtzee() && scoresLower.get(Category.YAHTZEE)!=null && bonusYahtzees < 3) {
			if(scoresUpper.get(category) == null) {
				int[] diceArr = dice.DiceAsIntArray();
				int result = 0;
				for (int j = 0; j < diceArr.length; j++) {
					result += diceArr[j];
				}
				scoresUpper.put(category, result);
				return IncrementBonusYahtzees();
			}
		}

		if(scoresUpper.get(category) == null) {
			scoresUpper.put(category, Category.CheckScore(dice,category));
			return true;
		}
		return false;
	}

	private boolean setScoreLower(Dice dice, Category category) {

		if(dice.IsYahtzee() && scoresLower.get(Category.YAHTZEE)!=null && bonusYahtzees < 3) {
			if(scoresLower.get(category) == null) {
				int[] diceArr = dice.DiceAsIntArray();
				int result = 0;
				for (int j = 0; j < diceArr.length; j++) {
					result += diceArr[j];
				}
				scoresLower.put(category, result);
				return IncrementBonusYahtzees();
			}
		}

		if(scoresLower.get(category) == null) {
			scoresLower.put(category, Category.CheckScore(dice,category));
			return true;
		}

		return false;
	}

	public boolean IncrementBonusYahtzees() {
		if(bonusYahtzees < 3) {
			bonusYahtzees ++;
			return true;
		}
		return false;
	}

	public boolean setScore(Dice dice, Category category) {

		if(categoriesUpper.contains(category)) {
			return setScoreUpper(dice, category);
		}
		else {
			return setScoreLower(dice, category);
		}
	}

	public boolean IsFinished() {
		for (Category category : categoriesUpper) {
			if(scoresUpper.get(category)==null) {
				return false;
			}
		}
		for (Category category : categoriesLower) {
			if(scoresLower.get(category)==null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Getters and Setters
	 */
	public Integer getCategoryScore(Category category) {
		if(categoriesUpper.contains(category)) {
			return scoresUpper.get(category);
		}
		else {
			return scoresLower.get(category);
		}
	}

	public List<Category> getUpperCategories() {
		return categoriesUpper;
	}
	
	public List<Category> getLowerCategories() {
		return categoriesLower;
	}
}
