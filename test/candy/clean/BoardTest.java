package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class BoardTest {

	private Board predefinedBoard;
	private Board smallBoard;
	private Board specialTable;

	@Before
	public void setUp() {
		String[] boardString = {"RRRRR", "BBRRR", "BBBBB", "GGBBB", "BBGGG"};
		predefinedBoard = new Board(boardString, 4);
		String[] smallStringBoard = {"RGB", "RBY"};
		smallBoard = new Board(smallStringBoard, 4);
		String[] boardForSpecialBlocks = {
			"GBBBBBBBBBBBBBR",
			"GPRRRRRRRRRRRRR",
			"GRRRPPPPPPPRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRPRRRRRRRRRR",
			"GRRRRRRRRRRRRRR",
			"GRRRRRRRRRRRRRR",
			"GRRRRRRRRRRRRRR",
			"GRRRRRRRRRRRRRR",
			"GRRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR"};
		specialTable = new Board(boardForSpecialBlocks, 4);

	}

	@Test
	public void testRandomConstructor() throws CandyCleanException {
		Board random = new Board(5, 4);
		assertNotEquals(predefinedBoard.toString(), random.toString());
	}

	@Test
	public void testGetBoard() {
		Block[][] blocks = {
			{new Block('R'), new Block('G'), new Block('B')},
			{new Block('R'), new Block('B'), new Block('Y')}
		};

		assertEquals(Arrays.deepToString(blocks), Arrays.deepToString(smallBoard.getTable()));
	}

	@Test
	public void testIsPossibleToPlay() {
		assertTrue(predefinedBoard.toString(), predefinedBoard.isPossibleToPlay());
	}

	@Test
	public void testIsNotPossibleToPlay() {
		String[] notPossiblePlay = {
			"CGRB",
			"PBGY"};
		Board isNotPossibleBoard = new Board(notPossiblePlay, 4);
		assertFalse(isNotPossibleBoard.isPossibleToPlay());
	}

	@Test
	public void testShoot() throws CandyCleanException {
		predefinedBoard.shoot(0, 0);
		assertTrue(predefinedBoard.isPossibleToPlay());
	}

	@Test(expected = CandyCleanException.class)
	public void testShootOutOfLeftBounds() throws CandyCleanException {
		predefinedBoard.shoot(0, -10);
	}

	@Test(expected = CandyCleanException.class)
	public void testShootOutOfRightBounds() throws CandyCleanException {
		predefinedBoard.shoot(0, 100);
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
		assertTrue(predefinedBoard.isPossibleToPlay());
	}

	@Test
	public void testShootSpecialBlocks() throws CandyCleanException {
		specialTable.getTable()[1][1].setSpecialBlock(5);
		specialTable.shoot(1, 1); // Default block in removeBlocks
		specialTable.shoot(14, 14); // All Board Candy
		specialTable.shoot(1, 0); // Column Candy
		specialTable.shoot(1, 2); // Row Candy
		specialTable.shoot(3, 4); // Row/Column Candy

		specialTable.shoot(14, 0); // Column Candy
		specialTable.shoot(1, 2); // Row Candy
		specialTable.shoot(9, 4); // Row/Column Candy
		specialTable.shoot(14, 14); // All Board Candy

		assertTrue(specialTable.isPossibleToPlay());
	}

	@Test
	public void testRemoveBlocksWithMoreSpecialCandies() throws CandyCleanException {
		Block[][] aux = specialTable.getTable();

		aux[10][10].setSpecialBlock(Constants.COLUMN_TYPE);
		aux[14][10].setSpecialBlock(Constants.ROW_TYPE);
		aux[14][12].setSpecialBlock(Constants.ROW_COLUMN_TYPE);

		assertEquals(Constants.COLUMN_TYPE, aux[10][10].getType());
		assertEquals(Constants.ROW_TYPE, aux[14][10].getType());
		assertEquals(Constants.ROW_COLUMN_TYPE, aux[14][12].getType());

		specialTable.shoot(10, 10);

		aux = specialTable.getTable();

		assertEquals(Constants.NORMAL_TYPE, aux[10][10].getType());
		assertEquals(Constants.NORMAL_TYPE, aux[14][10].getType());
		assertEquals(Constants.NORMAL_TYPE, aux[14][12].getType());
	}

	@Test
	public void testHasSurroundingBlocks() {
		assertTrue(predefinedBoard.hasSurroundingBlocks(0, 0));
		assertTrue(predefinedBoard.hasSurroundingBlocks(0, 0));
		assertTrue(predefinedBoard.hasSurroundingBlocks(1, 1));
		assertTrue(smallBoard.hasSurroundingBlocks(0, 0));
		assertFalse(smallBoard.hasSurroundingBlocks(0, 1));
	}

	@Test
	public void testDebugBoard() {
		assertEquals("RGB RBY ", smallBoard.debugBoard());
	}

	@Test
	public void testToStringBoard15() {
		String[] board15S = {"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR",
			"RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR", "RRRRRRRRRRRRRRR"};
		Board board15 = new Board(board15S, 4);
		assertEquals("                      |1|1|1|1|1|\n  |0|1|2|3|4|5|6|7|8|9|0|1|2|3|4|",
			board15.toString().substring(0, 67));
	}
}
