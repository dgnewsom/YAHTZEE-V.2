package scoring;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
			/*
			 * Try to read from text file and populate highscore lists 
			 */
			FileReader fr = new FileReader(highScoresFile);
			BufferedReader br=new BufferedReader(fr);
			for (int i = 0; i < 10; i++) {
				String[] line = br.readLine().split(":");
				highScores.add(new Score(line[0], Integer.parseInt(line[1])));
			}
			br.close();
			Collections.sort(highScores);
		}
		catch (Exception e) {
			clearHighScores();
		}
	}
	
	public static void exportHighScoresToFile() {
		
		try  
		{  
			FileOutputStream fos = new FileOutputStream(highScoresFile);   
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));  

			for (Score score : highScores) {
				bw.write(score.getName() + ":" + score.getScore());
				bw.newLine();
			}
			bw.close();
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
