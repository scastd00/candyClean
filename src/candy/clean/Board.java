package candy.clean;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Class that represents the board of the game.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */

public class Board {

	/**
	 * Maximum number of colors allowed to play.
	 */
	public static final int MAX_COLORS = 7;

	/**
	 * Minimum number of colors allowed to play.
	 */
	public static final int MIN_COLORS = 2;

	/**
	 * Maximum dimensions of the board allowed to play.
	 */
	public static final int MAX_DIMENSIONS = 35;

	/**
	 * Minimum dimensions of the board allowed to play.
	 */
	public static final int MIN_DIMENSIONS = 3;

	/**
	 * Logger of the class.
	 */
	private static final Logger logger = LogManager.getLogger(Board.class);

	/**
	 * Board of the game.
	 */
	private final Block[][] board;

	/**
	 * Number of colors used in the game.
	 */
	private int numColors;

	/**
	 * Class constructor for random matches.
	 *
	 * @param size      Size of the board sides.
	 * @param numColors Number of colors the game will have.
	 */
	public Board(int size, int numColors) throws CandyCleanException {
		StringBuilder error = new StringBuilder();
		if (size < MIN_DIMENSIONS || size > MAX_DIMENSIONS) {
			error.append(String.format(
				"You are not able to play with this board size: %d." + " The size must be between %d and %d\n",
				size, MIN_DIMENSIONS, MAX_DIMENSIONS));
		}
		if (numColors < MIN_COLORS || numColors > MAX_COLORS) {
			error.append(String.format("You are not able to play with this number of colors: %d."
				+ " The number of colors must be between %d and %d\n", numColors, MIN_COLORS, MAX_COLORS));
		}
		if (error.length() != 0) {
			throw new CandyCleanException(error.toString());
		} else {
			this.numColors = numColors;
			this.board = new Block[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					this.board[i][j] = new Block(numColors);
				}
			}
		}
	}

	/**
	 * Class constructor for pre-designed boards. It uses the next codification:
	 * R - Red, Y - Yellow, E - Black, G - Green, B - Blue, P - Purple, C - Cyan, W - White.
	 *
	 * @param stringBoard Pre-designed board in a String array. Note: this board can be a rectangle instead of a square.
	 * @param numColors Number of colors used in the pre-designed board.
	 */
	public Board(String[] stringBoard, int numColors) {
		this.numColors = numColors;
		this.board = new Block[stringBoard.length][stringBoard[0].length()];
		for (int i = 0; i < stringBoard.length; i++) {
			for (int j = 0; j < stringBoard[i].length(); j++) {
				this.board[i][j] = new Block(stringBoard[i].charAt(j));
			}
		}
	}

	/**
	 * Determines if there are possibilities to shoot at a color.
	 *
	 * @return false if there are no more valid color combinations to shoot at. Return true, otherwise.
	 */
	public boolean isPossibleToPlay() {
		boolean isPossibleToPlay = false;
		int i = 0;
		while (i < this.board.length && !isPossibleToPlay) {
			int j = 0;
			while (j < this.board.length && !isPossibleToPlay) {
				isPossibleToPlay = this.hasSurroundingBlocks(i, j);
				j++;
			}
			i++;
		}
		return isPossibleToPlay;
	}

	/**
	 * Removes (set to black) the blocks (vertical and horizontal) that have the same color at the selected point.
	 *
	 * @param row    The row of the selected Block.
	 * @param column The column of the selected Block.
	 */
	private void removeBlocks(int row, int column) {
		int firstLeftCandyPos = this.firstLeftCandyPos(row, column);
		int lastRightCandyPos = this.lastRightCandyPos(row, column);
		int firstUpperCandyPos = this.firstUpperCandyPos(row, column);
		int lastLowerCandyPos = this.lastLowerCandyPos(row, column);

		// Horizontal replacement.
		for (int i = firstLeftCandyPos; i <= lastRightCandyPos; i++) {
			this.board[row][i].setToBlank();
		}

		// Vertical replacement.
		for (int i = firstUpperCandyPos; i <= lastLowerCandyPos; i++) {
			this.board[i][column].setToBlank();
		}
	}

	/**
	 * Checks if the selected spot has more Blocks in its surroundings. Otherwise, an exception will be thrown.
	 *
	 * @param row    Row of the selected Block.
	 * @param column Column of the selected Block.
	 * @throws CandyCleanException A CandyCleanException will be thrown if the selected spot is not valid.
	 */
	private void isValidSelectedSpot(int row, int column) throws CandyCleanException {
		if (row >= this.board.length || row < 0 || column >= this.board.length || column < 0) {
			throw new CandyCleanException("The selected spot is outside of the board boundaries. The current board size is "
				+ this.board.length + " x " + this.board.length);
		}

		if (!this.hasSurroundingBlocks(row, column)) {
			throw new CandyCleanException("The selected block doesn't have any surrounding blocks with the same color");
		}
	}

	/**
	 * Player action of shooting to an specific spot. If the spot is valid, all the surrounding Blocks with the same color
	 * will be deleted, the board will compact and the empty blocks at the top of the board are filled with new colors.
	 *
	 * @param row    Row of the selected spot.
	 * @param column Column of the selected spot.
	 * @throws CandyCleanException A CandyCleanException will be thrown if the selected spot is out of the bounds of the
	 *                             Board or it has no surrounding Blocks with the same Color.
	 */
	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.isValidSelectedSpot(row, column);
			int leftPos = this.firstLeftCandyPos(row, column);
			int rightPos = this.lastRightCandyPos(row, column);
			int upperPos = this.firstUpperCandyPos(row, column);
			int lowerPos = this.lastLowerCandyPos(row, column);
			this.removeBlocks(row, column);
			this.compactBoardWidth(row, leftPos, rightPos);
			this.compactBoardHeight(column, upperPos, lowerPos);
			this.fillEmptyWithNewBlocks();
		} catch (CandyCleanException e) {
			throw new CandyCleanException(e.getMessage());
		}
	}

	/**
	 * Compacts the board horizontally. Takes the empty candies to the top of the board (It's the same process as getting up
	 * the empty candies).
	 *
	 * @param row      Row where the candies were removed.
	 * @param leftPos  Left Position where the first candy was deleted.
	 * @param rightPos Right Position where the last candy was deleted.
	 */
	private void compactBoardWidth(int row, int leftPos, int rightPos) {
		for (int i = row; i > 0; i--) {
			for (int j = leftPos; j <= rightPos; j++) {
				Block aux = this.board[i - 1][j];
				this.board[i - 1][j] = this.board[i][j];
				this.board[i][j] = aux;
			}
		}
	}

	/**
	 * Compacts the board vertically, using the method {@link #compactBoardWidth(int, int, int)} to take each empty block to
	 * the top of the board.
	 *
	 * @param column   Column where the candies were removed.
	 * @param upperPos Upper position where the first candy was deleted.
	 * @param lowerPos Bottom position where the last candy was deleted.
	 */
	private void compactBoardHeight(int column, int upperPos, int lowerPos) {
		for (int i = upperPos; i <= lowerPos; i++) {
			if (this.board[i][column].isBlank()) {
				this.compactBoardWidth(i, column, column);
			}
		}
	}

	/**
	 * Verifies if in a selected spot there are more Blocks with the same color at its left, right, top and/or bottom.
	 *
	 * @param row    Row of the selected spot.
	 * @param column Column of the selected spot.
	 * @return true if the selected Block has more Blocks with the same Color at its left, right, top and/or bottom, false
	 * otherwise.
	 */
	public boolean hasSurroundingBlocks(int row, int column) {
		return this.firstLeftCandyPos(row, column) != this.lastRightCandyPos(row, column) ||
			this.firstUpperCandyPos(row, column) != this.lastLowerCandyPos(row, column);
	}

	/**
	 * Checks if the color of the selected block is equal to the color of the left blocks.
	 *
	 * @param row    the selected row to remove the blocks.
	 * @param column the selected block of the row.
	 * @return the number of blocks with same color at left.
	 */
	private int firstLeftCandyPos(int row, int column) {
		int before = column;
		while (before > 0 && this.board[row][before].equals(this.board[row][before - 1])) {
			before--;
		}
		return before;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the right blocks.
	 *
	 * @param row    the selected row to remove the blocks.
	 * @param column the selected block of the row.
	 * @return the number of blocks with the same color at right.
	 */
	private int lastRightCandyPos(int row, int column) {
		int after = column;
		while (after < this.board.length - 1 && this.board[row][after].equals(this.board[row][after + 1])) {
			after++;
		}
		return after;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the upper blocks.
	 *
	 * @param row    the selected row of the column.
	 * @param column the selected block of the column.
	 * @return the number of blocks with the same color at top.
	 */
	private int firstUpperCandyPos(int row, int column) {
		int before = row;
		while (before > 0 && (this.board[before][column].equals(this.board[before - 1][column]))) {
			before--;
		}
		return before;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the lower blocks.
	 *
	 * @param row    the selected row of the column.
	 * @param column the selected block of the column.
	 * @return the number of blocks with the same color at bottom.
	 */
	private int lastLowerCandyPos(int row, int column) {
		int after = row;
		while (after < this.board.length - 1 && this.board[after][column].equals(this.board[after + 1][column])) {
			after++;
		}
		return after;
	}

	/**
	 * Fills the empty blocks after shooting, making the game infinite.
	 */
	private void fillEmptyWithNewBlocks() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if (this.board[i][j].isBlank())
					this.board[i][j] = new Block(this.numColors);
			}
		}
	}

	/**
	 * Logs the board with its letters only.
	 *
	 * @return returns the Board.
	 */
	public String debugBoard() {
		StringBuilder debug = new StringBuilder();
		logger.debug("Debugging board");

		for (Block[] blocks : this.board) {
			for (Block block : blocks) {
				debug.append(block.getLetter());
			}
			debug.append(' ');
		}
		return debug.toString();
	}

	/**
	 * Returns the played board as a String with colors, without letters and the axis numbers.
	 *
	 * @return the Board that is played.
	 */
	public String toString() {
		StringBuilder outputBoard = new StringBuilder("  ");
		// If the board size is greater than 9 prints the first number of the column.
		for (int i = 0; i < this.board.length; i++) {
			if (i == 10) {
				outputBoard.append("|");
			}
			if (i >= 10) {
				outputBoard.append(i / 10).append("|");
			} else {
				outputBoard.append("  ");
			}
		}

		// Prints the numbers of the columns.
		outputBoard.append("\n  ");
		for (int i = 0; i < this.board.length; i++) {
			outputBoard.append("|").append(i % 10);
		}
		outputBoard.append("|" + "\n");

		for (int i = 0; i < this.board.length; i++) {
			// Space for the first number of the line number if it is greater than 9.
			if (i < 10) {
				outputBoard.append(" ");
			}

			// Candies in the line i
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < this.board[i].length; j++) {
				line.append(this.board[i][j]);
			}

			outputBoard.append(i).append("|").append(line.toString()).append("\n");
		}

		return outputBoard.toString();
	}
}
