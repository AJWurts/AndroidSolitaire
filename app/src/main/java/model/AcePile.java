package model;

import java.util.ArrayList;


public class AcePile extends Pile {

    AcePile() {
        super();
    }

    AcePile(int testVar) {
        super(testVar);
    }

    /**
     * Valid next card is an Ace if empty, and a card with same suit but one greater number if not empty
     *
     * @param m movement to check
     * @return true if can be placed, false otherwise
     */
    public boolean validNextCard(Movement m) {
        Card last = getLast();
        Card c = m.getBase();
        return ((last == null && c.num == Deck.ACE) ||
                (last != null && c.suit == last.suit && c.num - 1 == last.num))
                && m.getBelow().size() == 1;
    }

    /**
     * Add cards to deck from movement. Adds ontop of other cards
     * @param m movement
     */
    public void addCards(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            addCard(c);
        }
    }

    /**
     * Gets the top card in the deck
     * @param c Card to look for
     * @return
     */
    public ArrayList<Card> getAfter(Card c) {
        below.clear();
        below.add(c);
        return below;
    }

    /**
     * Does nothing because ace piles cannot be flipped
     */
    public void flipLast() {
        // do nothing
    }

    /**
     * Checks to see if King is last card in deck
     * @return true of king is last card, false otherwise
     */
    public boolean isComplete() {
        Card last = getLast();
        return last != null && getLast().num == Deck.KING;
    }
}

