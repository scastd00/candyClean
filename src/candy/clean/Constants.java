package candy.clean;

import org.jetbrains.annotations.Contract;

/**
 * Class that represents the constants used in the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public final class Constants {

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
	 * Candy that explodes the connected horizontal or vertical candies.
	 */
	public static final int NORMAL_TYPE = 0;

	/**
	 * Special candy that explodes all the row where the player shoots.
	 */
	public static final int ROW_TYPE = 1;

	/**
	 * Special candy that explodes all the column where the player shoots.
	 */
	public static final int COLUMN_TYPE = 2;

	/**
	 * Special candy that explodes all the candies in the row and column where the player shoots.
	 */
	public static final int ROW_COLUMN_TYPE = 3;

	/**
	 * Special candy that explodes all the candies.
	 */
	public static final int ALL_BOARD_TYPE = 4;

	/**
	 * Candies required to make a candy with special explosion.
	 */
	public static final int MINIMUM_CANDIES_FOR_SPECIAL_CANDY = 4;

	@Contract(value = " -> fail", pure = true)
	private Constants() {
		throw new IllegalStateException("Utility class");
	}
}
