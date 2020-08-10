package candy.clean;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Class that represents the colors of the blocks.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */
public class Color {

	private static final String BLACK = "\u001B[40m";
	private static final String RED = "\u001B[41m";
	private static final String GREEN = "\u001B[42m";
	private static final String YELLOW = "\u001B[43m";
	private static final String BLUE = "\u001B[44m";
	private static final String PURPLE = "\u001B[45m";
	private static final String CYAN = "\u001B[46m";
	private static final String WHITE = "\u001B[47m";
	private static final String RESET = "\u001B[0m";

	public static final String BLACK_BLINK = "\u001B[30;5m";

	/**
	 * String that represents the color.
	 */
	private final String background;

	/**
	 * Constructor that assigns a color based on a BackgroundColor.
	 *
	 * @param background the color is going to be assigned.
	 */
	@Contract(pure = true)
	public Color(@NotNull BackgroundColor background) {
		switch (background) {
			case RED:
				this.background = Color.RED;
				break;
			case GREEN:
				this.background = Color.GREEN;
				break;
			case YELLOW:
				this.background = Color.YELLOW;
				break;
			case BLUE:
				this.background = Color.BLUE;
				break;
			case PURPLE:
				this.background = Color.PURPLE;
				break;
			case CYAN:
				this.background = Color.CYAN;
				break;
			case WHITE:
				this.background = Color.WHITE;
				break;
			case BLACK:
			default:
				this.background = Color.BLACK;
				break;
		}

	}

	/**
	 * Class constructor that assigns colors based in a number.
	 *
	 * @param i number of color to be used.
	 */
	public Color(int i) {
		this(BackgroundColor.values()[i]);
	}

	/**
	 * Constructor that copies another color.
	 *
	 * @param another color to copy.
	 */
	public Color(Color another) {
		this.background = another.getBackground();
	}

	/**
	 * Returns the color assigned to the object.
	 *
	 * @return the string value assigned to the object.
	 */
	public String getBackground() {
		return this.background;
	}

	/**
	 * Compares two different colors.
	 *
	 * @param o Object that its compared with the instanced color.
	 * @return boolean value depending on the condition.
	 */
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			Color other = (Color) o;
			return this.background.equals(other.getBackground());
		}
	}

	@Override
	public int hashCode() {
		return this.background.hashCode();
	}

	/**
	 * Converts the value of the object to a String variable.
	 *
	 * @param content the content to represent the color.
	 * @return the name of the variable and its value in String.
	 */
	public String toString(String content) {
		return this.background + content + Color.RESET;
	}

	/**
	 * Convert the value of the object to String.
	 *
	 * @return the String value of the object.
	 */
	@Override
	public String toString() {
		return this.toString("  ");
	}

}
