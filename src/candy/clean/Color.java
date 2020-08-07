package candy.clean;

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

	/**
	 * String that represents the color.
	 */
	private final String color;

	/**
	 * Constructor that assigns a color based on a BackgroundColor.
	 *
	 * @param color the color is going to be assigned.
	 */
	public Color(BackgroundColor color) {
		switch (color) {
			case RED:
				this.color = Color.RED;
				break;
			case GREEN:
				this.color = Color.GREEN;
				break;
			case YELLOW:
				this.color = Color.YELLOW;
				break;
			case BLUE:
				this.color = Color.BLUE;
				break;
			case PURPLE:
				this.color = Color.PURPLE;
				break;
			case CYAN:
				this.color = Color.CYAN;
				break;
			case WHITE:
				this.color = Color.WHITE;
				break;
			case BLACK:
			default:
				this.color = Color.BLACK;
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
		this.color = another.getColor();
	}

	/**
	 * Returns the color assigned to the object.
	 *
	 * @return the string value assigned to the object.
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Compares two different colors.
	 *
	 * @param o Object that its compared with the instanced color.
	 * @return boolean value depending on the condition.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			Color other = (Color) o;
			return this.color.equals(other.getColor());
		}
	}

	@Override
	public int hashCode() {
		return this.color.hashCode();
	}

	/**
	 * Converts the value of the object to a String variable.
	 *
	 * @param content the content to represent the color.
	 * @return the name of the variable and its value in String.
	 */
	public String toString(String content) {
		return this.color + content + Color.RESET;
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
