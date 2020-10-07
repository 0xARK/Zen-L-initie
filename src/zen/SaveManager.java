package zen;

import java.io.*;

/**
 * This class allows to save or load a zen game
 * @author Castella Mateo
 * @version 1.0.0
 */

public class SaveManager implements Serializable{

	/**
	 * This method allows to save the current game
	 * @param g - The game we want to save in the file
	 * @param filename - The path and the name of the file where we will save the game
	 */

	protected void saveGame(Game g, String filename) throws IOException {

		OutputStream out = new FileOutputStream("saved/" + filename);
		ObjectOutputStream outputStream = new ObjectOutputStream(out);

		g.savedFile = filename;

		outputStream.writeObject(g);

		outputStream.close();
		out.close();

	}

	/**
	 * This method allows to load the game selected
	 * @param filename - The path and the name of the file where the game we want to load was saved
	 */

	public void loadGame(String filename, Zen z) {

		try {

			InputStream in = new FileInputStream(filename);
			ObjectInputStream inputStream = new ObjectInputStream(in);

			Game g = (Game)inputStream.readObject();

			inputStream.close();
			in.close();

			z.startSavedGame(g);

		} catch (ClassNotFoundException | IOException e) {
			System.out.print("\033[1;31m" + "[-] Chargement échoué : " + "\033[0;37m" + e.getMessage());
		}

	}

}