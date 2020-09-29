package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import gui.GUI;

/**
 * Abstract SaveGame class to handle save game slots
 * @author Danny Newsom
 * @version 2.0
 *
 */
public abstract class SaveGame {

	//array of current game saves
	private static Game[] saveGames = new Game[10];
	//location of the savegame.txt file
	private static final File saveGameFile = new File("savegame.txt");
	
	/**
	 * Method to add a game to array of saveGames 
	 * and export savegames array to text file 
	 * @param game Game object to save
	 * @param slotNumber int representing slot number to save game to
	 */
	public static void saveGame(Game game, int slotNumber) {	
		//update save time before saving
		game.setDate(LocalDateTime.now());
		saveGames[slotNumber] = game;
		exportSaveGames();
	}

	/**
	 * Method to export current saveGames to text file
	 */
	public static void exportSaveGames() {
		try  
		{  
			FileOutputStream fileOut =
			         new FileOutputStream(saveGameFile);
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
			         out.writeObject(saveGames);
			         out.close();
			         fileOut.close();
			         
			out.close();
		}
		catch(IOException e)  
		{  
			e.printStackTrace();  
		}
		
	}

	/**
	 * Method to retrieve a Game from the saveGames array
	 * and couple with current GUI Object
	 * @param gui Current GUI to assign to the game to be loaded
	 * @param slotNumber int representing slot number to load game from
	 * @return Game object to load
	 */
	public static Game loadGame(GUI gui, int slotNumber) {
		
		Game temp = saveGames[slotNumber];
		temp.setGui(gui);
		gui.setGame(temp);
		return temp;
		
	}
	
	/**
	 * Method to return the latest Game saved
	 * @param gui Current GUI to assign to the Game to be loaded
	 * @return Game object to load
	 */
	public static Game loadLatestGame(GUI gui) {
		
		Game temp = getLatestGame();
		temp.setGui(gui);
		gui.setGame(temp);
		return temp;
		
	}
	
	/**
	 * Method to locate and return the latest 
	 * Game in the saveGames Array
	 * @return Game object representing the latest Game in the saveGames array
	 */
	public static Game getLatestGame() {
		//create Arraylist and add each valid save game
		ArrayList<Game> sortedSaves = new ArrayList<>();
		for (int i = 0; i < saveGames.length; i++) {
			if(saveGames[i] != null) {
				sortedSaves.add(saveGames[i]);
			}
		}
		//sort the list and return the first item
		if(sortedSaves.size() > 0) {
			Collections.sort(sortedSaves);
			
			return sortedSaves.get(0);
		
		}
		return null;
	}

	/**
	 * Method to import from saveGames text file to saveGames Array
	 */
	public static void importSaveGames() {
		try {
			FileInputStream fileIn = new FileInputStream(saveGameFile);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        saveGames = (Game[]) in.readObject();
	        in.close();
	        fileIn.close();
		} catch (Exception e) {
			//If no saveGame file can be loaded initailise with empty array
			saveGames = new Game[10];
		}
		
	}
	
	/**
	 * Method to delete a save game from a slot
	 * and export updated saveGames to text file
	 * @param slotNumber int representing slot number to clear
	 */
	public static void clearSaveGame(int slotNumber) {
		saveGames[slotNumber] = null;
		exportSaveGames();
	}
	
	/**
	 * Method to clear all save games and export to text file
	 */
	public static void clearAllSaveGames() {
		saveGames = new Game[10];
		exportSaveGames();
	}
	
	/**
	 * Getter for the saveGames array
	 * @return Game[] of save games
	 */
	public static Game[] getSaveGames() {
		return saveGames;
	}
	
}
