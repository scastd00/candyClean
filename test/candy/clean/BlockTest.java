package candy.clean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BlockTest {

    private Block black;
    private Block otherColor;
    private Block random;

    @Before
    public void setUp() {
        black = new Block('E');
        otherColor = new Block('R');
    }

    @Test
    public void testRandomConstructor() {
        for (int i = 0; i < 20; i++) {
            random = new Block(7);
            assertFalse(black.equals(random));
        }
    }

    @Test
    public void testBlankConstructor() {
        Block blank;
        do {
            blank = new Block(12);
        } while (!black.equals(blank));

        assertTrue(black.equals(blank));
    }

    @Test
    public void testGetLetter() {
        assertEquals('E', black.getLetter());
        assertEquals('R', otherColor.getLetter());
    }

    @Test
    public void testIsBlack() {
        assertTrue(black.isBlank());
        assertFalse(otherColor.isBlank());
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
    public void testToString() {
        assertEquals("\u001B[40m  \u001B[0m", black.toString());
    }
}
