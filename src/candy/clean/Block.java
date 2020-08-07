package candy.clean;

import java.util.Random;

/**
 * Class that represents the game's individual block.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */

public class Block {

	/**
	 * Color of the block.
	 */
	private Color color;

	/**
	 * Letter which also represents the assigned color for the block.
	 */
	private char letter;

	/**
	 * Class constructor that takes an specific color as a char. It uses the next codification:
	 * R - Red, Y - Yellow, E - Black, G - Green, B - Blue, P - Purple, C - Cyan, W - White.
	 *
	 * @param letter Letter assigned to the block.
	 */
	public Block(char letter) {
		this.setColor(letter);
	}

	/**
	 * Class constructor for the block that uses an integer to assign the color.
	 *
	 * @param num Number of colors that can be used in the game.
	 */
	public Block(int num) {
		Random rand = new Random();
		int randomNum= rand.nextInt(num) + 1;
		if (randomNum > Board.MAX_COLORS) {
			this.color = new Color(BackgroundColor.BLACK);
		} else {
			this.color = new Color(randomNum);
		}

		char[] chars = new char[]{'E', 'R', 'G', 'Y', 'B', 'P', 'C', 'W', 'E'};

		if (randomNum > Board.MAX_COLORS) {
			this.letter = chars[0];
		} else {
			this.letter = chars[randomNum];
		}
	}

	/**
	 * Returns the color of the block.
	 *
	 * @return Color class of the color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color of the block to a specified one.
	 *
	 * @param color BackgroundColor type used to set the color.
	 */
	public void setColor(char color) {
		this.letter = color;
		switch (color) {
			case 'R':
				this.color = new Color(BackgroundColor.RED);
				break;
			case 'G':
				this.color = new Color(BackgroundColor.GREEN);
				break;
			case 'Y':
				this.color = new Color(BackgroundColor.YELLOW);
				break;
			case 'B':
				this.color = new Color(BackgroundColor.BLUE);
				break;
			case 'P':
				this.color = new Color(BackgroundColor.PURPLE);
				break;
			case 'C':
				this.color = new Color(BackgroundColor.CYAN);
				break;
			case 'W':
				this.color = new Color(BackgroundColor.WHITE);
				break;
			default:
				this.color = new Color(BackgroundColor.BLACK);
				break;
		}
	}

	/**
	 * Returns the letter of the color assigned to a block.
	 *
	 * @return Character that represents the color.
	 */
	public char getLetter() {
		return this.letter;
	}

	/**
	 * Checks if the block is empty.
	 *
	 * @return true if empty, false otherwise.
	 */
	public boolean isBlank() {
		return (this.letter == 'E'); // Black color
	}

	/**
	 * Sets to blank the instanced block.
	 */
	public void setToBlank() {
		this.setColor('E');
	}

	/**
	 * Checks if the block has the same color as another one.
	 *
	 * @param o Object to be compared with.
	 * @return true if it's a block with the same color or its the same block; false
	 * otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			Block other = (Block) o;
			return this.color.equals(other.getColor());
		}
	}

	@Override
	public int hashCode() {
		return this.letter + this.color.hashCode();
	}

	/**
	 * Returns the block colored that represents the color.
	 *
	 * @return String block colored.
	 */
	public String toString() {
		if (!this.isBlank()) {
			return this.color.toString("  ");
		} else {
			return this.color.toString();
		}
	}
}
