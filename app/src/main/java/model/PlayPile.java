package model;

import java.util.ArrayList;

import static model.Card.size_y;


public class PlayPile extends Pile {

    public static float OFFSET = 0.25f;

    PlayPile() {
        super();
    }

    /**
     * Used for testing
     *
     * @param testVar unused
     */
    PlayPile(int testVar) {
        super(testVar);
    }


    /**
     * checks if base card from movement m can be placed on pile
     * @param m movement to be checked
     * @return true if can be placed, false if cannot
     */
    @Override
    public boolean validNextCard(Movement m) {
        Card last = getLast();
        Card c = m.getBase();
        boolean contains = below.contains(c);
        if (contains) {
            return true; // Allow placement of card that was previously there
        } else if (last == null && c.num == Deck.KING) { // If pile is empty allow a king to be placed
            return true;
        } else if (last != null) { // If a pile has cards only let cards of opposite color with one less get placed
            return (((c.suit == 'c' || c.suit == 's') && (last.suit == 'h' || last.suit == 'd'))
                    || ((c.suit == 'h' || c.suit == 'd') && (last.suit == 'c' || last.suit == 's')))
                    && (last.num - 1 == c.num) ;
        }
        return false;
    }

    /**
     * Adds cards from movement to pile giving them the correct offset on screen
     * @param m movement to be added to pile
     */
    @Override
    public void addCards(Movement m) {
        float[] base = getNextOpenCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            base[1] += size_y * OFFSET;
            addCard(c);
        }
    }

    /**
     * Retrieves a list of card after the given card c
     * @param c card it looks for
     * @return ArrayList of cards after card c
     */
    @Override
    public ArrayList<Card> getAfter(Card c) {
        below.clear();
        boolean collecting = false;
        for (int i = 0; i < cards.size(); i++) {
            if (collecting || cards.get(i).equals(c)) {
                collecting = true;
                below.add(cards.get(i));
            }
        }
        return below;
    }

    /**
     * Flips the last card in the pile
     */
    @Override
    public void flipLast() {
        Card last = getLast();
        if (last != null)
            getLast().setFlipped(false);
    }
}
