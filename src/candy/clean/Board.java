package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

/**
 * Class that represents the board of the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class Board {

	/**
	 * Logger of the class.
	 */
	private static final Logger logger = LogManager.getLogger(Board.class);

	/**
	 * Board of the game.
	 */
	private final Block[][] table;

	/**
	 * Number of colors used in the game.
	 */
	private final int numColors;

	/**
	 * Score of the game.
	 */
	private final Score gameScore;

	/**
	 * Class constructor for random matches.
	 *
	 * @param size      Size of the board (Square).
	 * @param numColors Number of colors the game will have.
	 * @throws CandyCleanException If the introduced dimensions or number of colors are incorrect.
	 */
	public Board(int size, int numColors, Score gameScore) throws CandyCleanException {
		StringBuilder error = new StringBuilder();
		if (size < Constants.MIN_DIMENSIONS || size > Constants.MAX_DIMENSIONS) {
			error.append(String.format(
				"You are not able to play with this board size: %d." + " The size must be between %d and %d\n",
				size, Constants.MIN_DIMENSIONS, Constants.MAX_DIMENSIONS));
		}

		if (numColors < Constants.MIN_COLORS || numColors > Constants.MAX_COLORS) {
			error.append(String.format("You are not able to play with this number of colors: %d."
				+ " The number of colors must be between %d and %d\n", numColors, Constants.MIN_COLORS, Constants.MAX_COLORS));
		}

		if (error.length() != 0) {
			throw new CandyCleanException(error.toString());
		} else {
			this.numColors = numColors;
			this.table = new Block[size][size];
			this.gameScore = gameScore;

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					this.table[i][j] = new Block(numColors);
				}
			}
		}
	}

	/**
	 * Class constructor for pre-designed boards. It uses the next codification:
	 *
	 * <p style="color: red;">R - Red</p>
	 * <p style="color: yellow;">Y - Yellow</p>
	 * <p style="color: black;">E - Black</p>
	 * <p style="color: green;">G - Green</p>
	 * <p style="color: blue;">B - Blue</p>
	 * <p style="color: purple;">P - Purple</p>
	 * <p style="color: cyan;">C - Cyan</p>
	 * <p style="color: grey;">W - White</p>
	 *
	 * @param stringBoard Pre-designed board in a String array. Note: this board can be a rectangle instead of a square.
	 * @param numColors   Number of colors used in the pre-designed board.
	 * @param gameScore   The score objective of the game.
	 */
	public Board(String[] stringBoard, int numColors, Score gameScore) {
		this.numColors = numColors;
		this.table = new Block[stringBoard.length][stringBoard[0].length()];
		this.gameScore = gameScore;

		for (int i = 0; i < stringBoard.length; i++) {
			for (int j = 0; j < stringBoard[i].length(); j++) {
				this.table[i][j] = new Block(stringBoard[i].charAt(j));
			}
		}

	}

	/**
	 * Returns the board that is played.
	 *
	 * @return The board of the current game.
	 */
	public Block[][] getTable() {
		return this.table;
	}

	/**
	 * Todo: Method not necessary
	 * Determines if there are possibilities to shoot at a color.
	 *
	 * @return <code>false</code> if there are no more valid color combinations to shoot at. Return <code>true</code>, otherwise.
	 */
	public boolean isPossibleToPlay() {
		boolean isPossibleToPlay = false;
		int i = 0;
		while (i < this.table.length && !isPossibleToPlay) {
			int j = 0;
			while (j < this.table.length && !isPossibleToPlay) {
				isPossibleToPlay = this.hasSurroundingBlocks(i, j);
				j++;
			}
			i++;
		}
		return isPossibleToPlay;
	}

	/**
	 * Checks if the selected spot has more equal Blocks in its surroundings. Otherwise, an exception will be thrown.
	 *
	 * @param row    Row of the selected Block.
	 * @param column Column of the selected Block.
	 * @throws CandyCleanException If the selected spot is not valid.
	 */
	private void checkValidSelectedSpot(int row, int column) throws CandyCleanException {
		if (row >= this.table.length || row < 0 || column >= this.table.length || column < 0) {
			throw new CandyCleanException("The selected spot is outside of the board boundaries. The current board size is "
				+ this.table.length + " x " + this.table.length);
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
	 * @throws CandyCleanException If the selected spot is out of the bounds of the Board or it has no surrounding Blocks with
	 *                             the same Color.
	 */
	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.checkValidSelectedSpot(row, column);

			if (this.table[row][column].isSpecialBlock()) {
				this.removeBlocks(row, column, true);
			} else {
				int leftPos = this.firstLeftCandyPos(row, column);
				int rightPos = this.lastRightCandyPos(row, column);
				int upperPos = this.firstUpperCandyPos(row, column);
				int lowerPos = this.lastLowerCandyPos(row, column);

				char blockLetter = this.table[row][column].getLetter();
				this.removeBlocks(row, column, false);

				this.createNewSpecialBlock(row, column, blockLetter, new int[] {leftPos, rightPos, upperPos, lowerPos});

				this.compactBoardWidth(row, leftPos, rightPos);
				this.compactBoardHeight(column, upperPos, lowerPos);
			}

			this.gameScore.increaseStreakUpdateMultiplier();
			this.fillEmptyWithNewBlocks();
		} catch (CandyCleanException e) {
			this.gameScore.resetMultiplierStreakDecreaseScore();
			throw new CandyCleanException(e.getMessage());
		}
	}

	/**
	 * Removes (set to black) the blocks (vertical and horizontal) that have the same color near to the selected point.
	 *
	 * @param row     The row of the selected Block.
	 * @param column  The column of the selected Block.
	 * @param special Specifies if the selected block is special.
	 * @throws CandyCleanException If the selected spot is not valid.
	 */
	private void removeBlocks(int row, int column, boolean special) throws CandyCleanException {
		if (special) {
			switch (this.table[row][column].getType()) {
				case Constants.ROW_TYPE:
					removeBlocksRow(row, column);
					compactBoardWidth(row, 0, this.table.length - 1);
					break;
				case Constants.COLUMN_TYPE:
					removeBlocksColumn(row, column);
					break;
				case Constants.ROW_COLUMN_TYPE:
					removeBlocksRow(row, column);
					removeBlocksColumn(row, column);
					compactBoardWidth(row, 0, this.table.length - 1);
					break;
				case Constants.ALL_BOARD_TYPE:
					clearTable();
					break;
				default:
					break;
			}
		} else {
			int firstLeftCandyPos = this.firstLeftCandyPos(row, column);
			int lastRightCandyPos = this.lastRightCandyPos(row, column);
			int firstUpperCandyPos = this.firstUpperCandyPos(row, column);
			int lastLowerCandyPos = this.lastLowerCandyPos(row, column);

			// Horizontal replacement.
			for (int i = firstLeftCandyPos; i <= lastRightCandyPos; i++) {
				this.table[row][i].setToBlank();
				this.gameScore.increaseScore();
			}

			// Vertical replacement.
			for (int i = firstUpperCandyPos; i <= lastLowerCandyPos; i++) {
				this.table[i][column].setToBlank();
				this.gameScore.increaseScore();
			}
		}
	}

	/**
	 * Removes all the blocks in the selected row.
	 *
	 * @param row    The selected row to remove the blocks.
	 * @param column The selected column to remove the blocks (used if there are special candies in the same row to shoot at).
	 * @throws CandyCleanException If the selected spot is not valid.
	 */
	private void removeBlocksRow(int row, int column) throws CandyCleanException {
		for (int i = 0; i < this.table.length; i++) {
			if (i != column && this.table[row][i].isSpecialBlock()) {
				this.shoot(row, i);
			} else {
				this.table[row][i].setToBlank();
				this.table[row][i].setSpecialBlock(Constants.NORMAL_TYPE);
				this.gameScore.increaseScore();
			}
		}
	}

	/**
	 * Removes all the blocks in the selected column.
	 *
	 * @param row    The selected row to remove the blocks. (Used if there are special candies in the same column to shoot at).
	 * @param column The selected column to remove the blocks.
	 * @throws CandyCleanException If the selected spot is not valid.
	 */
	private void removeBlocksColumn(int row, int column) throws CandyCleanException {
		for (int i = 0; i < this.table.length; i++) {
			if (i != row && this.table[i][column].isSpecialBlock()) {
				this.shoot(i, column);
			} else {
				this.table[i][column].setToBlank();
				this.table[i][column].setSpecialBlock(Constants.NORMAL_TYPE);
				this.gameScore.increaseScore();
			}
		}
	}

	/**
	 * Removes all the blocks in the table. (Used for the special candy).
	 */
	private void clearTable() {
		for (Block[] blocks : this.table) {
			for (int j = 0; j < this.table.length; j++) {
				blocks[j].setToBlank();
				this.gameScore.increaseScore();
			}
		}
	}

	/**
	 * Creates a new special block when 4 or more candies are removed. ALL_BOARD_TYPE special candies are generated when removed an
	 * entire column and row of the board.
	 *
	 * @param row         The row of the selected block.
	 * @param column      The column of the selected block.
	 * @param blockLetter The letter of the old block. Used for creating the new candy with the same background.
	 * @param positions   Specifies the conditions for creating the new special candies. The following:
	 *                    <p>[0] - First candy with the same color to the left.</p>
	 *                    <p>[1] - Last candy with the same color to the right.</p>
	 *                    <p>[2] - First candy with the same color to the top.</p>
	 *                    <p>[3] - Last candy with the same color to the bottom.</p>
	 */
	private void createNewSpecialBlock(int row, int column, char blockLetter, int[] positions) {
		int minimum = Constants.MINIMUM_CANDIES_FOR_SPECIAL_CANDY;

		// Adding 1 because of Arrays' structure. (e.g. Row: 0, Col: 4  ->  4 - 0 = 4 but you break 5 candies)
		if (((positions[1] - positions[0]) + 1 == this.table.length) && ((positions[3] - positions[2]) + 1 == this.table.length)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(Constants.ALL_BOARD_TYPE);

		} else if (((positions[1] - positions[0]) + 1 >= minimum) && ((positions[3] - positions[2]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(Constants.ROW_COLUMN_TYPE);

		} else if (((positions[1] - positions[0]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(Constants.ROW_TYPE);

		} else if (((positions[3] - positions[2]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(Constants.COLUMN_TYPE);
		}
	}

	/**
	 * Compacts the board horizontally. Takes the empty candies to the top of the board (It's the same process as getting up
	 * the empty candies).
	 *
	 * @param row      Row where the candies were removed.
	 * @param leftPos  Left position where the first candy was deleted.
	 * @param rightPos Right position where the last candy was deleted.
	 */
	private void compactBoardWidth(int row, int leftPos, int rightPos) {
		for (int i = row; i > 0; i--) {
			for (int j = leftPos; j <= rightPos; j++) {
				if (this.table[i][j].isBlank()) {
					Block aux = this.table[i - 1][j];
					this.table[i - 1][j] = this.table[i][j];
					this.table[i][j] = aux;
				}
			}
		}
	}

	/**
	 * Compacts the board vertically, using the method {@link #compactBoardWidth(int, int, int)} to take each empty block to the
	 * top of the board.
	 *
	 * @param column   Column where the candies were removed.
	 * @param upperPos Upper position where the first candy was deleted.
	 * @param lowerPos Bottom position where the last candy was deleted.
	 */
	private void compactBoardHeight(int column, int upperPos, int lowerPos) {
		for (int i = upperPos; i <= lowerPos; i++) {
			if (this.table[i][column].isBlank()) {
				this.compactBoardWidth(i, column, column);
			}
		}
	}

	/**
	 * Verifies if in a selected spot there are more Blocks with the same color at its left, right, top and/or bottom.
	 *
	 * @param row    Row of the selected spot.
	 * @param column Column of the selected spot.
	 * @return <code>true</code> if the selected Block has more Blocks with the same Color at its left, right, top and/or bottom,
	 * <code>false</code> otherwise.
	 */
	public boolean hasSurroundingBlocks(int row, int column) {
		return this.firstLeftCandyPos(row, column) != this.lastRightCandyPos(row, column) ||
			this.firstUpperCandyPos(row, column) != this.lastLowerCandyPos(row, column) ||
			this.table[row][column].isSpecialBlock();
	}

	/**
	 * Checks if the color of the selected block is equal to the color of the left blocks.
	 *
	 * @param row    The selected row to remove the blocks.
	 * @param column The selected block of the row.
	 * @return The number of blocks with same color at left.
	 */
	@Contract(pure = true)
	private int firstLeftCandyPos(int row, int column) {
		int before = column;
		while (before > 0 && this.table[row][before].equals(this.table[row][before - 1])) {
			before--;
		}
		return before;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the right blocks.
	 *
	 * @param row    The selected row to remove the blocks.
	 * @param column The selected block of the row.
	 * @return The number of blocks with the same color at right.
	 */
	@Contract(pure = true)
	private int lastRightCandyPos(int row, int column) {
		int after = column;
		while (after < this.table.length - 1 && this.table[row][after].equals(this.table[row][after + 1])) {
			after++;
		}
		return after;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the upper blocks.
	 *
	 * @param row    The selected row of the column.
	 * @param column The selected block of the column.
	 * @return The number of blocks with the same color at top.
	 */
	@Contract(pure = true)
	private int firstUpperCandyPos(int row, int column) {
		int before = row;

		while (before > 0 && (this.table[before][column].equals(this.table[before - 1][column]))) {
			before--;
		}

		return before;
	}

	/**
	 * Check if the color of the selected block is equal to the color of the lower blocks.
	 *
	 * @param row    The selected row of the column.
	 * @param column The selected block of the column.
	 * @return The number of blocks with the same color at bottom.
	 */
	@Contract(pure = true)
	private int lastLowerCandyPos(int row, int column) {
		int after = row;

		while (after < this.table.length - 1 && this.table[after][column].equals(this.table[after + 1][column])) {
			after++;
		}

		return after;
	}

	/**
	 * Fills the empty blocks after shooting, making the game infinite.
	 */
	private void fillEmptyWithNewBlocks() {
		for (int i = 0; i < this.table.length; i++) {
			for (int j = 0; j < this.table.length; j++) {
				if (this.table[i][j].isBlank()) {
					this.table[i][j] = new Block(this.numColors);
				}
			}
		}
	}

	/**
	 * Logs the board with its letters only.
	 *
	 * @return Returns the Board.
	 */
	public String debugBoard() {
		StringBuilder debug = new StringBuilder();
		logger.debug("Debugging board");

		for (Block[] blocks : this.table) {
			for (Block block : blocks) {
				debug.append(block.getLetter());
			}
			debug.append(' ');
		}
		return debug.toString();
	}

	/**
	 * Verifies if the player has won the game.
	 *
	 * @return <code>true</code> if the score is greater than the objective, <code>false</code> otherwise.
	 */
	public boolean haveWon() {
		return this.gameScore.objectiveCompleted();
	}

	/**
	 * Returns the played board as a String with colors, without letters and the axis' numbers.
	 *
	 * @return The Board that is played.
	 */
	public String toString() {

		StringBuilder outputBoard = new StringBuilder();

		// Appending the scoreboard
		outputBoard.append(this.gameScore.toString()).append("\n");

		// If the board size is greater than 9 prints the first number of the column.
		for (int i = 0; i < this.table.length; i++) {
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
		for (int i = 0; i < this.table.length; i++) {
			outputBoard.append("|").append(i % 10);
		}
		outputBoard.append("|" + "\n");

		for (int i = 0; i < this.table.length; i++) {
			// Space for the first number of the line number if it is greater than 9.
			if (i < 10) {
				outputBoard.append(" ");
			}

			// Candies in the line i
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < this.table[i].length; j++) {
				line.append(this.table[i][j]);
			}

			outputBoard.append(i).append("|").append(line.toString()).append("\n");
		}

		return outputBoard.toString();
	}
}
