package candy.clean;

/**
 * Class that have important information of the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class CandyClean {

	/**
	 * Board of the game.
	 */
	private final Board board;

	/**
	 * Class constructor of the game.
	 *
	 * @param dimensions Dimensions of the board introduced by the player.
	 * @param numColors  Number of colors the game will have.
	 * @param objective  The objective of the game depending on the selected difficulty level.
	 * @throws CandyCleanException If the size of the board or the number of colors aren't valid.
	 */
	public CandyClean(int dimensions, int numColors, int objective) throws CandyCleanException {
		this.board = new Board(dimensions, numColors, new Score(objective));
	}

	/**
	 * Constructor of the game with a predefined Board. It uses the next codification:
	 *
	 * <p style="color: red;">R - Red</p>
	 * <p style="color: yellow;">Y - Yellow</p>
	 * <p style="color: black;">E - Black</p>
	 * <p style="color: green;">G - Green</p>
	 * <p style="color: blue;">B - Blue</p>
	 * <p style="color: purple;">P - Purple</p>
	 * <p style="color: cyan;">C - Cyan</p>
	 * <p style="color: grey;">W - White</p>
	 */
	public CandyClean() {
		String[] predefinedBoard = {
			"GBBBBBBBBBBBBBR",
			"GRRRRRRRRRRRRPR",
			"GRRRPPPPPPPRPRR",
			"GRRRPRRRRRRPRRR",
			"GRRRPRRRRRPRRRR",
			"GRRRPRRRRPRRRRR",
			"GRRRPRRRPRRRRRR",
			"GRRRPRRPRRRRRRR",
			"GRRRPRPRRRRRRRR",
			"GRRRRPRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRPRRRRRRRRRRR",
			"GRPRRRRRRRRRRRR",
			"GPRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR"
		};
		this.board = new Board(predefinedBoard, 4, new Score(80));
	}

	/**
	 * Checks if the score is greater than the objective.
	 *
	 * @return <code>true</code> if the score is equals to or greater than the objective, <code>false</code> otherwise.
	 */
	public boolean haveWon() {
		return this.board.haveWon();
	}

	/**
	 * Selects the block introduced by keyboard.
	 *
	 * @param row    Row of the selected Block.
	 * @param column Column of the selected Block.
	 * @throws CandyCleanException If the selected spot is not valid or if the selected block hasn't any equal color to its sides.
	 */
	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.board.shoot(row, column);
		} catch (CandyCleanException e) {
			throw new CandyCleanException(e.getMessage());
		}
	}

	/**
	 * Returns the board as a String with colors and the numbers of the axis.
	 *
	 * @return The board as a String.
	 */
	public String toString() {
		return this.board.toString();
	}

	/**
	 * Logs the board of the game as a String.
	 *
	 * @return Returns the Board.
	 */
	public String debugBoard() {
		return board.debugBoard();
	}
}
