package candy.clean;

import org.jetbrains.annotations.Contract;

import java.util.Random;

/**
 * Class that represents the individual blocks of the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class Block {

	/**
	 * Color of the block.
	 */
	private Color color;

	/**
	 * Letter which also represents the assigned color to the block.
	 */
	private char letter;

	/**
	 * Type of the candy explosion. Notation:
	 *
	 * <p>0 - Normal</p>
	 * <p>1 - Row</p>
	 * <p>2 - Column</p>
	 * <p>3 - Row and Column</p>
	 * <p>4 - All the board</p>
	 */
	private int type;

	/**
	 * Specifies if the candy has a special explosion or not.
	 */
	private boolean isSpecialBlock;

	/**
	 * Class constructor that takes a specific color as a char parameter. It uses the next codification:
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

		char[] blocks = new char[] {'E', 'R', 'G', 'Y', 'B', 'P', 'C', 'W'};
		int randomNum = new Random().nextInt(num) + 1;

		if (randomNum > Constants.MAX_COLORS) {
			this.color = new Color(BackgroundColor.BLACK);
			this.letter = blocks[0];
		} else {
			this.color = new Color(randomNum);
			this.letter = blocks[randomNum];
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
	 * Returns the letter assigned to a block which corresponds to its color.
	 *
	 * @return Character that represents the color.
	 */
	public char getLetter() {
		return this.letter;
	}

	/**
	 * Returns the type of the candy.
	 *
	 * @return Number depending on the type of the candy (Defined in Constants class).
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type for special explosions.
	 *
	 * @param type The type to set.
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
	 * Set the state of the block. If the type is different than NORMAL_TYPE it's considered as a special block.
	 *
	 * @param type The explosion type of the special block.
	 */
	public void setSpecialBlock(int type) {
		this.type = type;
		this.isSpecialBlock = (type != Constants.NORMAL_TYPE);
	}

	/**
	 * Checks if the block is empty.
	 *
	 * @return <code>true</code> if empty (black color, <code>false</code> otherwise.
	 */
	public boolean isBlank() {
		return this.letter == 'E';
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
	 * @return <code>true</code> if it's a block with the same color or it's the same block, <code>false</code> otherwise.
	 */
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Block) {
			Block other = (Block) o;
			return (this.color.equals(other.getColor())) && (this.isSpecialBlock == other.isSpecialBlock());
		}

		return false;
	}

	/**
	 * Returns an Integer which is the sum of the letter assigned to the block and the {@link Color#hashCode()} of the color.
	 *
	 * @return An Integer value of the block.
	 */
	@Override
	public int hashCode() {
		return this.letter + this.color.hashCode();
	}

	/**
	 * Returns the colored block.
	 *
	 * @return String with the colored block.
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
