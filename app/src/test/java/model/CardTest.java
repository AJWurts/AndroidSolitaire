package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CardTest {
    Card h1;
    Card h1_copy;
    Card s1;
    Card d1;
    Card c1;

    @Before
    public void setUp() {
        c1 = new Card('c', 1, 0);
        s1 = new Card('s', 1, 0);
        d1 = new Card('d', 1, 0);
        h1 = new Card('h', 1, 0);
        h1_copy = new Card('h', 1, 0);
    }


    @Test
    public void equals() throws Exception {
        assertTrue(h1_copy.equals(h1));
        assertTrue(h1.equals(h1));
        assertFalse(c1.equals(s1));
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("c1", c1.toString());
    }

    @Test
    public void getString() throws Exception {
        assertEquals(c1.getChar(), "A");
        assertEquals("2", new Card('c', 2, 0).getChar());
        assertEquals("J", new Card('d', 11, 0).getChar());
        assertEquals("Q", new Card('h', 12, 0).getChar());
        assertEquals("K", new Card('c', 13, 0).getChar());
    }

    @Test
    public void flip() throws Exception {
        Card c = new Card('d', 5, 0);
        boolean isFlipped = c.isFlipped();
        c.flip();
        assertTrue(!isFlipped == c.isFlipped());
        isFlipped = c.isFlipped();
        c.flip();
        assertTrue(!isFlipped == c.isFlipped());
    }

    @Test
    public void setXY() throws Exception {
        float x = 10.5f;
        float y = 11.5f;
        Card temp = new Card('c', 1, 0);

        temp.setXY(x, y);
        assertEquals(x, temp.x, 0.0001);
        assertEquals(y, temp.y, 0.0001);

    }

    @Test
    public void setXY1() throws Exception {
        float xy[] = {1, 2};
        Card temp = new Card('s', 1, 0);

        temp.setXY(xy);
        assertEquals(xy[0], temp.x, 0.0001);
        assertEquals(xy[1], temp.y, 0.0001);
    }

}