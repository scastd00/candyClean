package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

/**
 * User Interface of the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class TextUI {

	/**
	 * Logger of the TextUI.
	 */
	private static final Logger logger = LogManager.getLogger(TextUI.class);

	/**
	 * Constant defined to print when a bad character is introduced.
	 */
	private static final String NAN = " is not a number.";

	/**
	 * The game is going to be played.
	 */
	private final CandyClean game;

	/**
	 * Constructor of the class.
	 *
	 * @param game The game is going to be played.
	 */
	@Contract(pure = true)
	public TextUI(CandyClean game) {
		this.game = game;
	}

	/**
	 * Game difficulty menu when the game starts or when a game is finished.
	 *
	 * @return The selected game mode.
	 *
	 * @throws CandyCleanException If the option is not an Integer.
	 */
	public static int selectGameMode() throws CandyCleanException {
		logger.trace("What level do you want to play? Select an option\n");
		logger.trace("0 - Exit\n1 - Easy\n2 - Medium\n3 - Hard\n4 - Very Hard\n5 - Extreme");

		String option = Keyboard.readLine();
		try {
			return Integer.parseInt(option);
		} catch (NumberFormatException e) {
			throw new CandyCleanException(option + NAN);
		}
	}

	/**
	 * Game manager.
	 */
	public void init() {
		logger.info("Welcome to the Candy Clean Game!");
		this.game.debugBoard();

		while (true) {
			this.printBoard();

			try {
				int inputRow = inputRow();
				int inputCol = inputColumn();
				game.shoot(inputRow, inputCol);
				logger.debug("Valid shoot");
			} catch (CandyCleanException e) {
				logger.warn(e.getMessage());
			}

			if (this.game.haveWon()) {
				wonMatch();
				this.printBoard();
				break;
			}
		}
	}

	/**
	 * Asks the number of the row of the board where the player wants to shoot.
	 *
	 * @return Number of the row introduced by the player.
	 *
	 * @throws CandyCleanException If the row introduced is not an integer value.
	 */
	private int inputRow() throws CandyCleanException {
		logger.trace("Introduce a row to shoot: ");
		String input = Keyboard.readLine().trim();
		logger.debug("Row: {}", input);

		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new CandyCleanException(input + NAN);
		}
	}

	/**
	 * Asks the number of the column of the board where the player wants to shoot.
	 *
	 * @return Number of the column introduced by the player.
	 *
	 * @throws CandyCleanException If the column introduced is not an integer value.
	 */
	private int inputColumn() throws CandyCleanException {
		logger.trace("Introduce a column to shoot: ");
		String input = Keyboard.readLine().trim();
		logger.debug("Column: {}", input);

		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new CandyCleanException(input + NAN);
		}
	}

	/**
	 * Shows the board with colors and numbers.
	 */
	public void printBoard() {
		logger.trace(this.game);
	}

	/**
	 * Says if the player won the game.
	 */
	private void wonMatch() {
		logger.info("You won");
	}
}
