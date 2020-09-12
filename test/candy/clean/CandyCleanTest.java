package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CandyCleanTest {

	private CandyClean candy1;
	private CandyClean predefinedBoard;

	@Before
	public void setUp() throws Exception {
		candy1 = new CandyClean(16, 5, 1);
		predefinedBoard = new CandyClean();
	}

	@Test
	public void testCorrectConstructor() throws CandyCleanException {
		CandyClean otherBoard = new CandyClean(16, 5, 1);
		assertNotEquals(otherBoard.toString(), candy1.toString());
	}

	@Test(expected = CandyCleanException.class)
	public void testNegativeDimensions() throws CandyCleanException {
		new CandyClean(-2, 5, 1);
	}

	@Test(expected = CandyCleanException.class)
	public void testHugeDimensions() throws CandyCleanException {
		new CandyClean(50, 6, 1);
	}

	@Test(expected = CandyCleanException.class)
	public void testNegativeCandies() throws CandyCleanException {
		new CandyClean(15, -4, 1);
	}

	@Test(expected = CandyCleanException.class)
	public void testNotEnoughCandies() throws CandyCleanException {
		new CandyClean(12, 1, 1);
	}

	@Test(expected = CandyCleanException.class)
	public void testALotOfCandies() throws CandyCleanException {
		new CandyClean(13, 8, 1);
	}

	@Test
	public void testHaveWon() throws CandyCleanException {
		predefinedBoard.shoot(0, 0);
		predefinedBoard.shoot(1, 1);
		predefinedBoard.shoot(2, 2);
		predefinedBoard.shoot(3, 3);
		predefinedBoard.shoot(4, 4);
		predefinedBoard.shoot(5, 5);
		predefinedBoard.shoot(6, 6);
		predefinedBoard.shoot(8, 8);

		assertTrue(predefinedBoard.haveWon());
	}

	@Test
	public void testHaveNotWon() {
		assertFalse(predefinedBoard.haveWon());
	}

	@Test
	public void testDebugBoard() {
		assertEquals("GBBBBBBBBBBBBBR " +
			"GRRRRRRRRRRRRPR " +
			"GRRRPPPPPPPRPRR " +
			"GRRRPRRRRRRPRRR " +
			"GRRRPRRRRRPRRRR " +
			"GRRRPRRRRPRRRRR " +
			"GRRRPRRRPRRRRRR " +
			"GRRRPRRPRRRRRRR " +
			"GRRRPRPRRRRRRRR " +
			"GRRRRPRRRRRRRRR " +
			"GRRRPRRRRRRRRRR " +
			"GRRPRRRRRRRRRRR " +
			"GRPRRRRRRRRRRRR " +
			"GPRRRRRRRRRRRRR " +
			"RRRRRRRRRRRRRRR ", predefinedBoard.debugBoard());
	}

	@Test(expected = CandyCleanException.class)
	public void testShootReset() throws CandyCleanException {
		predefinedBoard.shoot(-1, 4);
	}
}
