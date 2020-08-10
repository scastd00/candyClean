package candy.clean;

import org.jetbrains.annotations.Contract;

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
	 * Type of the candy explosion. Notation: 0 - Normal, 1 - Row, 2 - Column, 3 - Row and Column, 4 - All the board.
	 */
	private int type;

	private boolean isSpecialBlock;

	/**
	 * Class constructor that takes an specific color as a char. It uses the next codification:
	 * R - Red, Y - Yellow, E - Black, G - Green, B - Blue, P - Purple, C - Cyan, W - White.
	 *
	 * @param letter Letter assigned to the block.
	 */
	public Block(char letter) {
		this.setColor(letter);
		this.type = 0;
		this.isSpecialBlock = false;
	}

	/**
	 * Class constructor for the block that uses an integer to assign the color.
	 *
	 * @param num Number of colors that can be used in the game.
	 */
	public Block(int num) {
		this.type = 0;
		this.isSpecialBlock = false;

		int randomNum = new Random().nextInt(num) + 1;

		if (randomNum > Constants.MAX_COLORS) {
			this.color = new Color(BackgroundColor.BLACK);
		} else {
			this.color = new Color(randomNum);
		}

		char[] chars = new char[] {'E', 'R', 'G', 'Y', 'B', 'P', 'C', 'W', 'E'};

		if (randomNum > Constants.MAX_COLORS) {
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
	 * Returns the type of the candy.
	 *
	 * @return number depending on the type of the candy (Defined in Constants class).
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type for special explosions.
	 *
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
		this.isSpecialBlock = true;
	}

	/**
	 * Returns the state of the block.
	 *
	 * @return <code>true</code> if the block has a special explosion, <code>false</code> if it is a normal block.
	 */
	public boolean isSpecialBlock() {
		return this.isSpecialBlock;
	}

	/**
	 * Set the state of the block.
	 *
	 * @param specialBlock <code>true</code> if the block is going to be special or <code>false</code> if not.
	 * @param type the explosion type of the special block.
	 */
	public void setSpecialBlock(boolean specialBlock, int type) {
		this.isSpecialBlock = specialBlock;
		this.type = type;
	}

	/**
	 * Checks if the block is empty.
	 *
	 * @return <code>true</code> if empty, <code>false</code> otherwise.
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
	 * @return <code>true</code> if it's a block with the same color or its the same block; <code>false</code>
	 * otherwise.
	 */
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			Block other = (Block) o;
			return this.color.equals(other.getColor()) && this.isSpecialBlock == other.isSpecialBlock();
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
		StringBuilder out = new StringBuilder();
		if (!this.isBlank()) {
			if (this.isSpecialBlock) {
				switch (this.type) {
					case 1:
						out.append(this.color.toString(Color.BLACK_BLINK + "RR"));
						break;
					case 2:
						out.append(this.color.toString(Color.BLACK_BLINK + "CC"));
						break;
					case 3:
						out.append(this.color.toString(Color.BLACK_BLINK + "RC"));
						break;
					case 4:
						out.append(this.color.toString(Color.BLACK_BLINK + "AA"));
						break;
					default:
						out.append(this.color.toString());
				}
			} else {
				out.append(this.color.toString());
			}
		} else {
			out.append(this.color.toString());
		}

		return out.toString();
	}
}
