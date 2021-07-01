package candy.clean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

	private Color black;
	private Color red;
	private Color green;
	private Color yellow;
	private Color blue;
	private Color purple;
	private Color cyan;
	private Color white;
	private Color otherBlack;

	@Before
	public void setUp() throws Exception {
		black = new Color(BackgroundColor.BLACK);
		red = new Color(BackgroundColor.RED);
		green = new Color(BackgroundColor.GREEN);
		yellow = new Color(BackgroundColor.YELLOW);
		blue = new Color(BackgroundColor.BLUE);
		purple = new Color(BackgroundColor.PURPLE);
		cyan = new Color(BackgroundColor.CYAN);
		white = new Color(BackgroundColor.WHITE);
		otherBlack = new Color(black);
	}

	@Test
	public void testColorConst() {
		Color otherConstructor = new Color(0);
		assertEquals(black, otherConstructor);
	}

	@Test
	public void testCloneConstructor() {
		assertEquals(otherBlack, black);
	}

	@Test
	public void testEquals() {
		Color otherColor = new Color(BackgroundColor.BLACK);
		assertTrue(otherColor.equals(black));
		assertTrue(black.equals(black));
	}

	@Test
	public void testNotEquals() {
		Block block = new Block(5);
		assertFalse(black.equals(block));
		assertFalse(black.equals(purple));
		assertFalse(black.equals(null));
	}

	@Test
	public void testHashCode() {
		assertEquals(27697617, black.hashCode());
		assertEquals(27697648, red.hashCode());
		assertEquals(27697679, green.hashCode());
		assertEquals(27697710, yellow.hashCode());
		assertEquals(27697741, blue.hashCode());
		assertEquals(27697772, purple.hashCode());
		assertEquals(27697803, cyan.hashCode());
		assertEquals(27697834, white.hashCode());
	}

	@Test
	public void testGetColor() {
		assertEquals("\u001B[40m", black.getBackground());
		assertEquals("\u001B[41m", red.getBackground());
		assertEquals("\u001B[42m", green.getBackground());
		assertEquals("\u001B[43m", yellow.getBackground());
		assertEquals("\u001B[44m", blue.getBackground());
		assertEquals("\u001B[45m", purple.getBackground());
		assertEquals("\u001B[46m", cyan.getBackground());
		assertEquals("\u001B[47m", white.getBackground());
	}

	@Test
	public void testToString() {
		assertEquals("\u001B[45m  \u001B[0m", purple.toString());
		assertEquals("\u001B[40m  \u001B[0m", black.toString());
	}

	@Test
	public void testToStringWithParams() {
		assertEquals("\u001B[45mPP\u001B[0m", purple.toString("PP"));
	}

}
