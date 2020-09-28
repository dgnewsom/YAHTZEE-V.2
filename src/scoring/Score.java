package scoring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Score Class to represent a score item
 * 
 * @author Daniel Newsom
 * @version 3.0
 */
public class Score implements Comparable<Score>, Comparator<Score>, Serializable{

	private static final long serialVersionUID = 4614907272587876617L;
	private String name;
	private int score;

	/**
	 * Constructor for the Score Class
	 * @param name String representing the scores name
	 * @param time int representing the score
	 */
	public Score(String name, int time) {
		this.name = name;
		this.score = time;
	}
	
	/**
	 * Override the compareTo method to compare by time
	 * then by name.
	 */
	@Override
	public int compareTo(Score other) {

		if(this.score == other.getScore()) {
			return this.name.compareTo(other.getName());
		}
		else {
			return other.score - this.getScore();		
		}
	}
	
	/**
	 * Override the compare method for sorting
	 */
	@Override
	public int compare(Score o1, Score o2) {
		return o1.compareTo(o2);
	}

	/**
	 * method to sort list of scores
	 * @param list list to sort
	 */
	public void sortList(ArrayList<Score> list) {
		list.sort(this);
	}
	
	/*
	 * Getters and setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int time) {
		this.score = time;
	}

	/**
	 * Override toString method
	 * @return String string representation of the object
	 */
	public String toString() {
				
		return String.format("%s %s", name, score);
	}
}
