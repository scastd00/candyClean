package candy.clean;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Main class.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */

public class MainCandyClean {
	private static final Logger logger = LogManager.getLogger(MainCandyClean.class);

	public static void main(String[] args) {
		do {
			try {
				int option;
				int score = 0;
				int dimensions = 0;
				int numColors = 0;
				do {
					option = TextUI.selectGameMode();
				} while (option < 0 || option > 6);

				switch (option) {
					case 0:
						System.exit(0);
						break;
					case 1:
						dimensions = 7;
						numColors = 3;
						score = 100;
						break;
					case 2:
						dimensions = 12;
						numColors = 3;
						score = 200;
						break;
					case 3:
						dimensions = 15;
						numColors = 4;
						score = 350;
						break;
					case 4:
						dimensions = 18;
						numColors = 5;
						score = 500;
						break;
					case 5:
						dimensions = 21;
						numColors = 6;
						score = 900;
						break;
					case 6:
						dimensions = 30;
						numColors = 7;
						score = 1900;
						break;
					default:
						System.exit(1);
						break;
				}

				/*
				 * To play the board defined in the practice script, remove both attributes from
				 * the constructor (dimensions and numColors). When using an empty constructor,
				 * it will create the predefined board.
				 * CandyClean game = new CandyClean()
				 */
				CandyClean game = new CandyClean(dimensions, numColors, score);
				TextUI ui = new TextUI(game);
				ui.init();
			} catch (CandyCleanException e) {
				logger.fatal(e.getMessage());
			}
		} while (true);
	}
}
