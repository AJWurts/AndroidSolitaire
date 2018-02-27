package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DeckTest {

    Deck d = new Deck(0);

    ArrayList<Card> empty = new ArrayList<>();

    @Before
    public void setUp() {
        ArrayList<Pile> piles = d.getPiles();
        Pile p;
        for (int i = 0; i < 12; i++) {
            p = piles.get(i);
            p.setXY(200 * i, 0);
        }

        // Pile 0 is empty
        piles.get(1).addCard(d.getCard(0, 0)); // 2 of Hearts
        piles.get(2).addCard(d.getCard(1, 5)); // 4 of Spades
        piles.get(3).addCard(d.getCard(2, 8)); // 7 of Diamonds
        piles.get(4).addCard(d.getCard(1, 12)); // King of Spades
        piles.get(5).addCard(d.getCard(3, 10)); // Jack of Clubs
        piles.get(6).addCard(d.getCard(2, 11)); // Queen of Diamonds
        // Pile 7 is empty
        // Pile 8 is empty
        piles.get(9).addCard(d.getCard(0, 0)); // Ace of Hearts
        piles.get(10).addCard(d.getCard(2, 12)); // King of Diamonds
        piles.get(11).addCard(d.getCard(3, 7)); // 6 of Clubs
    }


    @Test
    public void getCard() throws Exception {
        Card s1 = new Card('s', 1, 0);
        Card s1FromDeck = d.getCard(Deck.SPADES, 0);
        assertTrue(s1.equals(s1FromDeck));

        Card h5 = new Card('h', 5, 0);
        Card h5FromDeck = d.getCard(Deck.HEARTS, 4);
        assertTrue(h5.equals(h5FromDeck));

        Card d10 = new Card('d', 10, 0);
        Card d10FromDeck = d.getCard(Deck.DIAMONDS, 9);
        assertTrue(d10.equals(d10FromDeck));

        Card c13 = new Card('c', 13, 0);
        Card c13FromDeck = d.getCard(Deck.CLUBS, 12);
        assertTrue(c13.equals(c13FromDeck));
    }

    @Test
    public void getClosestValidPile() throws Exception {
        Card base = new Card('h', 10, 0); // 10 of Hearts
        Movement m = new Movement(base, empty, 0);
        int i1 = d.getClosestValidPile(0, 0, m); // Can go on Pile 0 or 5 but closest to 0
        assertEquals(0, i1);

        int i2 = d.getClosestValidPile(5 * 200, 0, m);
        assertEquals(5, i2);
    }

    @Test
    public void getMovement() throws Exception {
    }

    @Test
    public void getCard1() throws Exception {
    }

    @Test
    public void flipLastCard() throws Exception {
    }

    @Test
    public void addToPile() throws Exception {
    }

    @Test
    public void updateCard() throws Exception {
    }

    @Test
    public void updateCards() throws Exception {
    }

    @Test
    public void getDrawOrder() throws Exception {
    }

    @Test
    public void loadSolitare() throws Exception {
    }

    @Test
    public void addCard() throws Exception {
    }

    @Test
    public void incDeckCards() throws Exception {
    }

    @Test
    public void getPiles() throws Exception {
    }

    @Test
    public void onPile() throws Exception {
    }

    @Test
    public void hasFinished() throws Exception {
    }

}