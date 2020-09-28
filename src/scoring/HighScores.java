package scoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import player.Player;

public abstract class HighScores {

	private static ArrayList<Score> highScores;
	
	private static final File highScoresFile = new File("highscores.txt");
	
	public static void importHighScoresFromFile() {
		highScores = new ArrayList<Score>();
		
		try  
		{  
			
			FileInputStream fileIn = new FileInputStream(highScoresFile);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         highScores = (ArrayList<Score>) in.readObject();
	         in.close();
	         fileIn.close();
	         
			Collections.sort(highScores);
		}
		catch (Exception e) {
			clearHighScores();
		}
	}
	
	public static void exportHighScoresToFile() {
		
		try  
		{  
			
			FileOutputStream fileOut =
			         new FileOutputStream(highScoresFile);
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
			         out.writeObject(highScores);
			         out.close();
			         fileOut.close();
			         
			out.close();
		}
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}
	}
	
	public static void checkHighScore(Player player) {
		Collections.sort(highScores);
		if(player.getScorecard().getGrandTotal() > highScores.get(highScores.size()-1).getScore()) {
			highScores.remove(highScores.size()-1);
			highScores.add(new Score(player.getPlayerName(),player.getScorecard().getGrandTotal()));
		}
	}
	
	public static void clearHighScores() {
		highScores = new ArrayList<Score>();
		for(int i = 0; i < 10;i ++) {
			highScores.add(new Score("Player", 0));
		}
	}
	
	/*
	 * Getters and setters
	 */
	public static ArrayList<Score> getHighScores() {
		return highScores;
	}
}
