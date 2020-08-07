package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreTest {
	private Score score;
	private Score scoreObj;

	@Before
	public void setUp() throws Exception {
	    score = new Score();
	    scoreObj = new Score(100);
	}

	@Test
	public void testGetSetScore() {
	    assertEquals(0, score.getScore());
	    // Auto-setter in constructor method.
	}

	@Test
	public void testGetSetObjective() {
	    assertEquals(500, score.getObjective());
	    score.setObjective(100);
	    assertEquals(100, score.getObjective());
	}

	@Test
	public void testGetSetMultiplier() {
	    assertEquals(1, score.getMultiplier());
	    score.setMultiplier(2);
		assertEquals(2, score.getMultiplier());
	}

	@Test
	public void testIncreaseDecreaseScore() {
	    score.increaseScoreAndStreakUpdateMultiplier();
	    score.decreaseScore();
	    score.decreaseScore();
		for (byte i = 0; i < 16; i++) {
			score.increaseScoreAndStreakUpdateMultiplier();
		}
	}

	@Test
	public void testResetMultiplierStreakDecreaseScore() {
		for (byte i = 0; i < 6; i++) {
			score.increaseScoreAndStreakUpdateMultiplier();
		}

		assertEquals(70, score.getScore());
		assertEquals(2, score.getMultiplier());

		score.resetMultiplierStreakDecreaseScore();
		assertEquals(60, score.getScore());
		assertEquals(1, score.getMultiplier());
	}

	@Test
	public void testObjectiveCompleted() {
	    assertFalse(score.objectiveCompleted());
		for (byte i = 0; i < 20; i++) {
			score.increaseScoreAndStreakUpdateMultiplier();
		}
		assertTrue(score.objectiveCompleted());
	}

	@Test
	public void testToString() {
	    assertEquals("\nScore = 0  Objective = 100  Multiplier = 1  Current streak = 0\n", scoreObj.toString());
	}
}
