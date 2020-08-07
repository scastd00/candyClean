package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board predefinedBoard;
	private Board smallBoard;

	@Before
	public void setUp() {
		String[] boardString = {"RRRRR", "BBRRR", "BBBBB", "GGBBB", "BBGGG"};
		predefinedBoard = new Board(boardString, 4);
		String[] smallStringBoard = {"RGB", "RBY"};
		smallBoard = new Board(smallStringBoard, 4);
	}

	@Test
	public void testRandomConstructor() throws CandyCleanException {
		Board random = new Board(5, 4);
		assertFalse(random.toString(), predefinedBoard.equals(random.toString()));
	}

	@Test
	public void testIsPossibleToPlay() {
		assertTrue(predefinedBoard.toString(), predefinedBoard.isPossibleToPlay());
	}

	@Test
	public void testIsNotPossibleToPlay() {
		String[] notPossiblePlay = {"CGRB", "PBGY"};
		Board isNotPossibleBoard = new Board(notPossiblePlay, 4);
		assertFalse(isNotPossibleBoard.toString(), isNotPossibleBoard.isPossibleToPlay());
	}

	@Test
	public void testShoot() throws CandyCleanException {
		predefinedBoard.shoot(0, 0);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootOutOfLeftBounds() throws CandyCleanException {
		predefinedBoard.shoot(0, -10);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootOutOfRightBounds() throws CandyCleanException {
		predefinedBoard.shoot(0, 30);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootAboveBoard() throws CandyCleanException {
		predefinedBoard.shoot(-10, 0);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootBelowBoard() throws CandyCleanException {
		predefinedBoard.shoot(30, 0);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootSingleCandy() throws CandyCleanException {
		smallBoard.shoot(0, 1);
	}

	@Test
	public void testShootWithCompact() throws CandyCleanException {
		predefinedBoard.shoot(1, 0);
	}

	@Test
	public void testHasSurroundingBlocks() {
		predefinedBoard.hasSurroundingBlocks(0, 0);
		predefinedBoard.hasSurroundingBlocks(1, 1);
		smallBoard.hasSurroundingBlocks(0, 0);
		smallBoard.hasSurroundingBlocks(0, 1);
	}

	@Test
	public void testDebugBoard() {
		assertEquals("RGB RBY ", smallBoard.debugBoard());
	}

	@Test
	public void testToStringBoard15() {
		String[] board15S = {"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR",};
		Board board15 = new Board(board15S, 4);
		assertEquals(board15.toString().substring(0, 67),
			"                      |1|1|1|1|1|\n  |0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|");
	}
}
