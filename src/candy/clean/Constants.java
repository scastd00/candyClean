package candy.clean;

import org.jetbrains.annotations.Contract;

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
	 * Explodes the connected horizontal or vertical candies.
	 */
	public static final int NORMAL_TYPE = 0;

	/**
	 * Explodes all the row.
	 */
	public static final int ROW_TYPE = 1;

	/**
	 * Explodes all the column.
	 */
	public static final int COLUMN_TYPE = 2;

	/**
	 * Explodes all the candies in the row and column.
	 */
	public static final int ROW_COLUMN_TYPE = 3;

	/**
	 * Explodes all the candies.
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
