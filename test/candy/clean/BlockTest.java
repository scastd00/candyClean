package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BlockTest {

	private Block black;
	private Block otherColor;

	@Before
	public void setUp() {
		black = new Block('E');
		otherColor = new Block('R');
	}

	@Test
	public void testRandomConstructor() {
		Block random;
		for (int i = 0; i < 20; i++) {
			random = new Block(7);
			assertNotSame(black, random);
		}
	}

	@Test
	public void testBlankConstructor() {
		Block blank;
		do {
			blank = new Block(12);
		} while (!black.equals(blank));

		assertEquals(black, blank);
	}

	@Test
	public void testGetColor() {
		assertEquals(new Color(BackgroundColor.BLACK), black.getColor());
		assertEquals(new Color(BackgroundColor.RED), otherColor.getColor());
	}

	@Test
	public void testSetIsBlank() {
		assertTrue(black.isBlank());
		assertFalse(otherColor.isBlank());

		otherColor.setToBlank();
		assertTrue(otherColor.isBlank());
	}

	@Test
	public void testSetRed() {
		otherColor.setColor('R');
		assertEquals("\u001B[41m  \u001B[0m", otherColor.toString());
		assertEquals('R', otherColor.getLetter());
	}

	@Test
	public void testSetGreen() {
		otherColor.setColor('G');
		assertEquals("\u001B[42m  \u001B[0m", otherColor.toString());
		assertEquals('G', otherColor.getLetter());
	}

	@Test
	public void testSetYellow() {
		otherColor.setColor('Y');
		assertEquals("\u001B[43m  \u001B[0m", otherColor.toString());
		assertEquals('Y', otherColor.getLetter());
	}

	@Test
	public void testSetBlue() {
		otherColor.setColor('B');
		assertEquals("\u001B[44m  \u001B[0m", otherColor.toString());
		assertEquals('B', otherColor.getLetter());
	}

	@Test
	public void testSetPurple() {
		otherColor.setColor('P');
		assertEquals("\u001B[45m  \u001B[0m", otherColor.toString());
		assertEquals('P', otherColor.getLetter());
	}

	@Test
	public void testSetCyan() {
		otherColor.setColor('C');
		assertEquals("\u001B[46m  \u001B[0m", otherColor.toString());
		assertEquals('C', otherColor.getLetter());
	}

	@Test
	public void testSetWhite() {
		otherColor.setColor('W');
		assertEquals("\u001B[47m  \u001B[0m", otherColor.toString());
		assertEquals('W', otherColor.getLetter());
	}

	@Test
	public void testGetLetter() {
		assertEquals('E', black.getLetter());
		assertEquals('R', otherColor.getLetter());

	}

	@Test
	public void testGetSetType() {
		assertEquals(Constants.NORMAL_TYPE, black.getType());
		assertEquals(Constants.NORMAL_TYPE, otherColor.getType());

		otherColor.setType(1);
		assertEquals(Constants.ROW_TYPE, otherColor.getType());
		otherColor.setType(2);
		assertEquals(Constants.COLUMN_TYPE, otherColor.getType());
		otherColor.setType(3);
		assertEquals(Constants.ROW_COLUMN_TYPE, otherColor.getType());
		otherColor.setType(4);
		assertEquals(Constants.ALL_BOARD_TYPE, otherColor.getType());
	}

	@Test
	public void testIsSpecialBlock() {
		assertFalse(black.isSpecialBlock());
		assertFalse(otherColor.isSpecialBlock());

		black.setType(Constants.ROW_TYPE);
		otherColor.setType(Constants.ROW_TYPE);

		assertTrue(black.isSpecialBlock());
		assertTrue(otherColor.isSpecialBlock());
	}

	@Test
	public void testSetSpecialBlock() {
		assertFalse(otherColor.isSpecialBlock());
		assertEquals(Constants.NORMAL_TYPE, otherColor.getType());

		otherColor.setSpecialBlock(true, Constants.ROW_TYPE);

		assertTrue(otherColor.isSpecialBlock());
		assertEquals(Constants.ROW_TYPE, otherColor.getType());
	}

	@Test
	public void testEquals() {
		otherColor.setColor('E');
		assertTrue(black.equals(otherColor));
		assertTrue(black.equals(black));
	}

	@Test
	public void testNotEquals() {
		assertFalse(black.equals(otherColor));
		Color color = new Color(1);
		assertFalse(black.equals(color));
		assertFalse(black.equals(null));
	}

	@Test
	public void testHashCode() {
		assertEquals(27697686, black.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("\u001B[40m  \u001B[0m", black.toString());

		otherColor.setType(1);
		assertEquals("\u001B[41m" + Color.BLACK_BLINK + "RR" + "\u001B[0m", otherColor.toString());
		otherColor.setType(2);
		assertEquals("\u001B[41m" + Color.BLACK_BLINK + "CC" + "\u001B[0m", otherColor.toString());
		otherColor.setType(3);
		assertEquals("\u001B[41m" + Color.BLACK_BLINK + "RC" + "\u001B[0m", otherColor.toString());
		otherColor.setType(4);
		assertEquals("\u001B[41m" + Color.BLACK_BLINK + "AA" + "\u001B[0m", otherColor.toString());
		otherColor.setType(5);
		assertEquals("\u001B[41m  \u001B[0m", otherColor.toString());
	}
}
