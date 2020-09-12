package candy.clean;


import org.jetbrains.annotations.Contract;

/**
 * Class that manages the score of the game.
 *
 * @author Samuel Castrillo Dominguez
 * @version 1.2.0
 */
public class Score {

	/**
	 * Quantity to add every time candies are broken.
	 */
	private final int additionScore;

	/**
	 * Score of the game.
	 */
	private int punctuation;

	/**
	 * Game objective.
	 */
	private int objective;

	/**
	 * Score multiplier.
	 */
	private int multiplier;

	/**
	 * Shot streak of the player.
	 */
	private int streak;

	/**
	 * Class constructor with the specified objective of the game.
	 *
	 * @param objective Objective of the game.
	 */
	@Contract(pure = true)
	public Score(int objective) {
		this.additionScore = 10;
		this.objective = objective;
		this.multiplier = 1;
		this.streak = 0;
	}

	/**
	 * Class constructor for default values.
	 */
	@Contract(pure = true)
	public Score() {
		this.additionScore = 10;
		this.objective = 500;
		this.multiplier = 1;
		this.streak = 0;
	}

	/**
	 * Score getter.
	 *
	 * @return The score of the game.
	 */
	public int getPunctuation() {
		return this.punctuation;
	}

	/**
	 * Objective getter.
	 *
	 * @return The objective of the game.
	 */
	public int getObjective() {
		return this.objective;
	}

	/**
	 * Objective setter.
	 *
	 * @param objective The objective to set.
	 */
	public void setObjective(int objective) {
		this.objective = objective;
	}

	/**
	 * Multiplier getter.
	 *
	 * @return The current multiplier of the game.
	 */
	public int getMultiplier() {
		return this.multiplier;
	}

	/**
	 * Multiplier setter.
	 *
	 * @param multiplier The multiplier to set.
	 */
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Increases the score when a block is broken.
	 */
	public void increaseScore() {
		this.punctuation += this.additionScore * this.multiplier;
	}

	/**
	 * Decreases the score when the player doesn't shoot to the correct candies. If the score is less than the addition score,
	 * it resets to 0 and never is negative.
	 */
	public void decreaseScore() {
		if (this.punctuation < this.additionScore) {
			this.punctuation = 0;
		} else {
			this.punctuation -= 10;
		}
	}

	/**
	 * Update all the counters in the game: Streak and Multiplier (+1/+3 when the player has 5/15 shot streak).
	 */
	public void increaseStreakUpdateMultiplier() {
		this.streak++;

		if (this.streak % 5 == 0)
			this.multiplier += 1;
		if (this.streak % 15 == 0)
			this.multiplier += 3;
	}

	/**
	 * Reduces the score by the default addition score and resets Multiplier and Streak values.
	 */
	public void resetMultiplierStreakDecreaseScore() {
		this.multiplier = 1;
		this.streak = 0;
		this.decreaseScore();
	}

	/**
	 * Checks if the game has ended.
	 *
	 * @return <code>true</code> if the score is equal to or greater than the game objective, <code>false</code> otherwise.
	 */
	public boolean objectiveCompleted() {
		return this.punctuation >= this.objective;
	}

	/**
	 * String version of the object.
	 *
	 * @return The string display of the score and the other counters.
	 */
	@Override
	public String toString() {
		return "\nScore = " + this.punctuation + "  Objective = " + this.objective + "  Multiplier = x" + this.multiplier +
			"  Current streak = " + this.streak + '\n';
	}
}
