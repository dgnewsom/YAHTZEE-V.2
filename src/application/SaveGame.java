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

public abstract class SaveGame {

	private static Game[] saveGames = new Game[10];
	private static final File saveGameFile = new File("savegame.txt");
	
	public static void saveGame(Game game, int slotNumber) {
		
		game.setDate(LocalDateTime.now());
		saveGames[slotNumber] = game;
		exportSaveGames();
	}

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

	public static Game loadGame(GUI gui, int slotNumber) {
		
		Game temp = saveGames[slotNumber];
		temp.setGui(gui);
		gui.setGame(temp);
		return temp;
		
	}
	
	public static Game loadLatestGame(GUI gui) {
		
		Game temp = getLatestGame();
		temp.setGui(gui);
		gui.setGame(temp);
		return temp;
		
	}
	
	public static Game getLatestGame() {
		ArrayList<Game> sortedSaves = new ArrayList<>();
		for (int i = 0; i < saveGames.length; i++) {
			if(saveGames[i] != null) {
				sortedSaves.add(saveGames[i]);
			}
		}
		if(sortedSaves.size() > 0) {
			Collections.sort(sortedSaves);
			
			return sortedSaves.get(0);
		
		}
		return null;
	}

	public static void importSaveGames() {
		try {
			FileInputStream fileIn = new FileInputStream(saveGameFile);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        saveGames = (Game[]) in.readObject();
	        in.close();
	        fileIn.close();
		} catch (Exception e) {
			saveGames = new Game[10];
		}
		
	}
	
	public static void clearSaveGame(int slotNumber) {
		saveGames[slotNumber] = null;
		exportSaveGames();
	}
	
	public static void clearAllSaveGames() {
		saveGames = new Game[10];
		exportSaveGames();
	}
	
	public static Game[] getSaveGames() {
		return saveGames;
	}
}
