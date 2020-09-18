package scoring;

import java.util.Arrays;

import dice.Dice;

public enum Category {
	ACES("Aces",1), 
	TWOS("Twos",2), 
	THREES("Threes",3), 
	FOURS("Fours",4), 
	FIVES("Fives",5), 
	SIXES("Sixes",6), 
	THREEOFAKIND("Three of a Kind",7),
	FOUROFAKIND("Four of a Kind",8),
	FULLHOUSE("Full House",9),
	SMALLSTRAIGHT("Small Straight",10),
	LARGESTRAIGHT("Large Straight",11),
	YAHTZEE("YAHTZEE!",12),
	CHANCE("Chance",13);
	
	private String descriptionName;
	private int option;
	private Category(String descriptionName,int option) {
		this.descriptionName = descriptionName;
		this.option = option;
	}
	
	public static int CheckScore(Dice dice, Category category) {
		
		int result = 0;
		int[] diceArr = dice.DiceAsIntArray();
		
		switch (category) {
		case ACES:
			result = (CountOccurences(diceArr, 1) * 1);
			break;
		case TWOS:
			result = (CountOccurences(diceArr, 2) * 2);
			break;
		case THREES:
			result = (CountOccurences(diceArr, 3) * 3);
			break;
		case FOURS:
			result = (CountOccurences(diceArr, 4) * 4);
			break;
		case FIVES:
			result = (CountOccurences(diceArr, 5) * 5);
			break;
		case SIXES:
			result = (CountOccurences(diceArr, 6) * 6);
			break;
		case THREEOFAKIND:
			for(int i = 1; i<=6;i++) {
				if(CountOccurences(diceArr, i) >= 3) {
					for (int j = 0; j < diceArr.length; j++) {
						result += diceArr[j];
					}
				}
			}
			break;
		case FOUROFAKIND:
			for(int i = 1; i<=6;i++) {
				if(CountOccurences(diceArr, i) >= 4) {
					for (int j = 0; j < diceArr.length; j++) {
						result += diceArr[j];
					}
				}
			}
			break;
		case FULLHOUSE:
			boolean two = false;
			boolean three = false;
			for(int i = 1; i<=6;i++) {
				if(CountOccurences(diceArr, i) == 2) {
					two = true;
				}
				if(CountOccurences(diceArr, i) == 3) {
					three = true;
				}
			}
			if(two && three) {
				result = 25;
			}
			break;
		case SMALLSTRAIGHT:
			Arrays.sort(diceArr);
			int smallCount = 0;
			for(int i = 1; i<diceArr.length;i++) {
				if(diceArr[i] == diceArr[i-1]+1) {
					smallCount ++;
				}
			}
			if(smallCount >= 3) {
				result = 30;
			}
			break;
		case LARGESTRAIGHT:
			Arrays.sort(diceArr);
			int largeCount = 0;
			for(int i = 1; i<diceArr.length;i++) {
				if(diceArr[i] == diceArr[i-1]+1) {
					largeCount ++;
				}
			}
			if(largeCount >= 4) {
				result = 40;
			}
			break;
		case YAHTZEE:
			for(int i = 1; i<=6;i++) {
				if(CountOccurences(diceArr, i) ==5) {
					result = 50;
				}
			}
			break;
		case CHANCE:
			for (int j = 0; j < diceArr.length; j++) {
				result += diceArr[j];
			}
			break;
		default:
			result = -1;
			break;
		}
		return result;
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
	
	public static Category getCategoryByOption(int option) {
		for(Category category : Category.values()) {
			if(category.getOption() == option) {
				return category;
			}
		}
		return null;
	}

	public String getDescriptionName() {
		return descriptionName;
	}
	
	public int getOption() {
		return option;
	}
}
