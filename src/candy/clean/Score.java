package candy.clean;


/**
 * Class that manages the score of the game.
 *
 * @author Samuel Castrillo Domínguez
 * @version 1.1.0
 */
public class Score {

	/**
	 * Quantity to add every time candies are broken.
	 */
	private final int additionScore;
	/**
	 * Score of the game.
	 */
	private int score;
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
	 * @param objective objective of the game.
	 */
	public Score(int objective) {
		this.additionScore = 10;
		this.objective = objective;
		this.multiplier = 1;
		this.streak = 0;
	}

	/**
	 * Class constructor for default vales.
	 */
	public Score() {
		this.additionScore = 10;
		this.objective = 500;
		this.multiplier = 1;
		this.streak = 0;
	}

	/**
	 * Score getter.
	 *
	 * @return the score of the game.
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Objective getter.
	 *
	 * @return the objective of the game.
	 */
	public int getObjective() {
		return this.objective;
	}

	/**
	 * Objective setter.
	 *
	 * @param objective the objective to set.
	 */
	public void setObjective(int objective) {
		this.objective = objective;
	}

	/**
	 * Multiplier getter.
	 *
	 * @return the current multiplier of the game.
	 */
	public int getMultiplier() {
		return this.multiplier;
	}

	/**
	 * Multiplier setter.
	 *
	 * @param multiplier the multiplier to set.
	 */
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Decreases the score when the player doesn't shoot to the correct candies. If the score is less than the addition
	 * score, the score sets to 0 and never is negative.
	 */
	public void decreaseScore() {
		if (this.score < this.additionScore) {
			this.score = 0;
		} else {
			this.score -= 10;
		}
	}

	/**
	 * Update all the counters in the game: Score, Streak and Multiplier (+1/+3 when the player hits 5/15 candies in a row.
	 */
	public void increaseScoreAndStreakUpdateMultiplier() {
		this.score += this.additionScore * this.multiplier;
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
	 * Checks if the game has ended. (NOT WORKING)
	 *
	 * @return true if the score is equal to or greater than the game objective.
	 */
	public boolean objectiveCompleted() {
		return this.score >= this.objective;
	}

	/**
	 * String version of the object.
	 *
	 * @return the string display of the score and the other counters.
	 */
	@Override
	public String toString() {
		return "\nScore = " + this.score + "  Objective = " + this.objective + "  Multiplier = " + this.multiplier +
			"  Current streak" + " = " + this.streak + '\n';
	}
}
