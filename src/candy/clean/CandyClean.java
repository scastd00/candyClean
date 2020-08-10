package candy.clean;

/**
 * Class that have important information of the game.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */
public class CandyClean {

	/**
	 * Board of the game.
	 */
	private final Board board;

	/**
	 * The score of the game.
	 */
	private final Score score;

	/**
	 * Class constructor of the game.
	 *
	 * @param dimensions Dimensions of the board introduced by the player.
	 * @param numColors  Number of colors the game will have.
	 * @param objective  The objective of de game depending on the selected difficulty.
	 * @throws CandyCleanException A CandyCleanException will be thrown if the size of the board or the number of colors
	 *                             aren't valid.
	 */
	public CandyClean(int dimensions, int numColors, int objective) throws CandyCleanException {
		this.board = new Board(dimensions, numColors);
		this.score = new Score(objective);
	}

	/**
	 * Constructor of the game with a predefined Board. It uses the next codification:
	 * R - Red, Y - Yellow, E - Black, G - Green, B - Blue, P - Purple, C - Cyan, W - White.
	 */
	public CandyClean() {
		String[] predefinedBoard = {"RRRRR", "BBRRR", "BBBBB", "GGBBB", "BBGGG"};
		this.board = new Board(predefinedBoard, 4);
		this.score = new Score(10);
	}

	/**
	 * Checks if there are possibilities to shoot at a color.
	 *
	 * @return <code>false</code> if there are no more valid color combinations to shoot at. Returns <code>true</code>, otherwise.
	 */
	public boolean isPossibleToPlay() {
		return this.board.isPossibleToPlay();
	}

	/**
	 * Checks if the score is greater than the objective.
	 *
	 * @return <code>true</code> if the score is equals to or greater than the objective. Returns <code>false</code> otherwise.
	 */
	public boolean haveWon() {
		return this.score.objectiveCompleted();
	}

	/**
	 * Selects the block introduced by keyboard.
	 *
	 * @param row    Row of the selected Block.
	 * @param column Column of the selected Block.
	 * @throws CandyCleanException A CandyCleanException will be thrown if the selected spot is not valid, if the selected
	 *                             block hasn't any equal color to its sides.
	 */
	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.board.shoot(row, column);
			this.score.increaseScoreAndStreakUpdateMultiplier();
		} catch (CandyCleanException e) {
			this.score.resetMultiplierStreakDecreaseScore();
			throw new CandyCleanException(e.getMessage());
		}
	}

	/**
	 * Returns the board as a String with colors and the numbers of the different axis.
	 *
	 * @return the board as a String.
	 */
	public String toString() {
		return this.score.toString() + '\n' + this.board.toString();
	}

	/**
	 * Logs the board of the game as a String.
	 *
	 * @return return the Board.
	 */
	public String debugBoard() {
		return board.debugBoard();
	}
}
