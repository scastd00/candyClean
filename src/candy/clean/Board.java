package candy.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

/**
 * Class that represents the board of the game.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
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
	 * Class constructor for random matches.
	 *
	 * @param size      Size of the board sides.
	 * @param numColors Number of colors the game will have.
	 */
	public Board(int size, int numColors) throws CandyCleanException {
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
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					this.table[i][j] = new Block(numColors);
				}
			}
		}
	}

	/**
	 * Class constructor for pre-designed boards. It uses the next codification:
	 * R - Red, Y - Yellow, E - Black, G - Green, B - Blue, P - Purple, C - Cyan, W - White.
	 *
	 * @param stringBoard Pre-designed board in a String array. Note: this board can be a rectangle instead of a square.
	 * @param numColors   Number of colors used in the pre-designed board.
	 */
	public Board(String[] stringBoard, int numColors) {
		this.numColors = numColors;
		this.table = new Block[stringBoard.length][stringBoard[0].length()];
		for (int i = 0; i < stringBoard.length; i++) {
			for (int j = 0; j < stringBoard[i].length(); j++) {
				this.table[i][j] = new Block(stringBoard[i].charAt(j));
			}
		}
	}

	/**
	 * Returns the board that is played.
	 *
	 * @return the board of the current game.
	 */
	public Block[][] getTable() {
		return this.table;
	}

	/**
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
	 * Checks if the selected spot has more Blocks in its surroundings. Otherwise, an exception will be thrown.
	 *
	 * @param row    Row of the selected Block.
	 * @param column Column of the selected Block.
	 * @throws CandyCleanException A CandyCleanException will be thrown if the selected spot is not valid.
	 */
	private void isValidSelectedSpot(int row, int column) throws CandyCleanException {
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
	 * @throws CandyCleanException A CandyCleanException will be thrown if the selected spot is out of the bounds of the
	 *                             Board or it has no surrounding Blocks with the same Color.
	 */
	public void shoot(int row, int column) throws CandyCleanException {
		try {
			this.isValidSelectedSpot(row, column);

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

			this.fillEmptyWithNewBlocks();
		} catch (CandyCleanException e) {
			throw new CandyCleanException(e.getMessage());
		}
	}

	/**
	 * Removes (set to black) the blocks (vertical and horizontal) that have the same color at the selected point.
	 *
	 * @param row     The row of the selected Block.
	 * @param column  The column of the selected Block.
	 * @param special Specifies if the selected block is special.
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
			}

			// Vertical replacement.
			for (int i = firstUpperCandyPos; i <= lastLowerCandyPos; i++) {
				this.table[i][column].setToBlank();
			}
		}
	}

	/**
	 * Removes all the blocks in the selected row.
	 *
	 * @param row    the selected row to remove the blocks.
	 * @param column the selected column to remove the blocks (used if there are special candies in the same row to shoot at).
	 */
	private void removeBlocksRow(int row, int column) throws CandyCleanException {
		for (int i = 0; i < this.table.length; i++) {
			if (i != column && this.table[row][i].isSpecialBlock()) {
				this.shoot(row, i);
			} else {
				this.table[row][i].setToBlank();
				this.table[row][i].setSpecialBlock(false, 0);
			}
		}
	}

	/**
	 * Removes all the blocks in the selected column.
	 *
	 * @param row    the selected row to remove the blocks (used if there are special candies in the same column to shoot at).
	 * @param column the selected column to remove the blocks.
	 */
	private void removeBlocksColumn(int row, int column) throws CandyCleanException {
		for (int i = 0; i < this.table.length; i++) {
			if (i != row && this.table[i][column].isSpecialBlock()) {
				this.shoot(i, column);
			} else {
				this.table[i][column].setToBlank();
				this.table[i][column].setSpecialBlock(false, 0);
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
			}
		}
	}

	/**
	 * Creates a new special block when 4 or more candies are removed. All board special candies are generated when removed an
	 * entire column and row of the board.
	 *
	 * @param row         The row of the selected block.
	 * @param column      the column of the selected block.
	 * @param blockLetter The letter of the old block. Used for creating the new candy with the same background.
	 * @param positions   Specifies the conditions for creating the new special candies.
	 *                    [0] - First candy with the same color to the left.
	 *                    [1] - Last candy with the same color to the right.
	 *                    [2] - First candy with the same color to the top.
	 *                    [3] - Last candy with the same color to the bottom.
	 */
	private void createNewSpecialBlock(int row, int column, char blockLetter, int[] positions) {
		int minimum = Constants.MINIMUM_CANDIES_FOR_SPECIAL_CANDY;

		// Adding 1 because of Arrays' structure. (e.g. Row: 0, Col: 4  ->  4 - 0 = 4 but you break 5 candies)
		if (((positions[1] - positions[0]) + 1 == this.table.length) && ((positions[3] - positions[2]) + 1 == this.table.length)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(true, Constants.ALL_BOARD_TYPE);

		} else if (((positions[1] - positions[0]) + 1 >= minimum) && ((positions[3] - positions[2]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(true, Constants.ROW_COLUMN_TYPE);

		} else if (((positions[1] - positions[0]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(true, Constants.ROW_TYPE);

		} else if (((positions[3] - positions[2]) + 1 >= minimum)) {
			this.table[row][column] = new Block(blockLetter);
			this.table[row][column].setSpecialBlock(true, Constants.COLUMN_TYPE);
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
				if (this.table[i][j].isBlank()) {
					Block aux = this.table[i - 1][j];
					this.table[i - 1][j] = this.table[i][j];
					this.table[i][j] = aux;
				}
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
	 * @return <code>true</code> if the selected Block has more Blocks with the same Color at its left, right, top and/or
	 * bottom, <code>false</code> otherwise.
	 */
	public boolean hasSurroundingBlocks(int row, int column) {
		return this.firstLeftCandyPos(row, column) != this.lastRightCandyPos(row, column) ||
			this.firstUpperCandyPos(row, column) != this.lastLowerCandyPos(row, column) ||
			this.table[row][column].isSpecialBlock();
	}

	/**
	 * Checks if the color of the selected block is equal to the color of the left blocks.
	 *
	 * @param row    the selected row to remove the blocks.
	 * @param column the selected block of the row.
	 * @return the number of blocks with same color at left.
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
	 * @param row    the selected row to remove the blocks.
	 * @param column the selected block of the row.
	 * @return the number of blocks with the same color at right.
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
	 * @param row    the selected row of the column.
	 * @param column the selected block of the column.
	 * @return the number of blocks with the same color at top.
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
	 * @param row    the selected row of the column.
	 * @param column the selected block of the column.
	 * @return the number of blocks with the same color at bottom.
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
	 * @return returns the Board.
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
	 * Returns the played board as a String with colors, without letters and the axis numbers.
	 *
	 * @return the Board that is played.
	 */
	public String toString() {
		StringBuilder outputBoard = new StringBuilder("  ");
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
