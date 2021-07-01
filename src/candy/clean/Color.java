package candy.clean;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Class that represents the colors of the blocks.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class Color implements Comparable<Color> {

	/**
	 * Black blinking color for special blocks.
	 */
	public static final String BLACK_BLINK = "\u001B[30;5m";

	/**
	 * Black background color.
	 */
	private static final String BLACK = "\u001B[40m";

	/**
	 * Red background color.
	 */
	private static final String RED = "\u001B[41m";

	/**
	 * Green background color.
	 */
	private static final String GREEN = "\u001B[42m";

	/**
	 * Yellow background color.
	 */
	private static final String YELLOW = "\u001B[43m";

	/**
	 * Blue background color.
	 */
	private static final String BLUE = "\u001B[44m";

	/**
	 * Purple background color.
	 */
	private static final String PURPLE = "\u001B[45m";

	/**
	 * Cyan background color.
	 */
	private static final String CYAN = "\u001B[46m";

	/**
	 * White background color.
	 */
	private static final String WHITE = "\u001B[47m";

	/**
	 * Reset background color (to restore the default background of the console).
	 */
	private static final String RESET = "\u001B[0m";

	/**
	 * String that represents the background color.
	 */
	private final String background;

	/**
	 * Constructor that assigns a color based on a BackgroundColor.
	 *
	 * @param background The color is going to be assigned.
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
	 * @param i Number of color to be used.
	 */
	public Color(int i) {
		this(BackgroundColor.values()[i]);
	}

	/**
	 * Constructor that copies another color.
	 *
	 * @param another Color to copy.
	 */
	public Color(Color another) {
		this.background = another.getBackground();
	}

	/**
	 * Returns the color assigned to the object.
	 *
	 * @return The string value assigned to the object.
	 */
	public String getBackground() {
		return this.background;
	}

	/**
	 * Compares two different colors.
	 *
	 * @param o Object that is compared with the instanced color.
	 *
	 * @return Boolean value depending on the condition.
	 */
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o instanceof Color) {
			Color other = (Color) o;
			return this.background.equals(other.getBackground());
		}

		return false;
	}

	/**
	 * Returns an Integer which is the hashcode of the background ({@link String#hashCode()}).
	 *
	 * @return An Integer value of the block.
	 */
	@Override
	public int hashCode() {
		return this.background.hashCode();
	}

	/**
	 * Converts the value of the object to a String variable.
	 *
	 * @param content The content to represent the color.
	 *
	 * @return The content of the variable and its value in String.
	 */
	public String toString(String content) {
		return this.background + content + Color.RESET;
	}

	/**
	 * Convert the value of the object to String.
	 *
	 * @return The String value of the object.
	 */
	@Override
	public String toString() {
		return this.toString("  ");
	}

	@Override
	public int compareTo(Color other) {
		return this.background.compareTo(other.background);
	}
}
