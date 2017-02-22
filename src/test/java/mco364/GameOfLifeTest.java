package mco364;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameOfLifeTest {

    @Test
    public void test1() {

    }

    @Test
    public void testIsAliveNextGeneration() {
        GameOfLife g = new GameOfLife(Board.Oscillator.TOAD);

        assertFalse(g.isAliveNextGeneration(0, 0));
        assertTrue(g.isAliveNextGeneration(1, 3));
        assertFalse(g.isAliveNextGeneration(1, 1));
    }
}
