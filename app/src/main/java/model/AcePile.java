package model;

import java.util.ArrayList;


public class AcePile extends Pile {

    AcePile() {
        super();
    }

    AcePile(int testVar) {
        super(testVar);
    }

    public boolean validNextCard(Movement m) {
        Card last = getLast();
        Card c = m.getBase();
        return ((last == null && c.num == Deck.ACE) ||
                (last != null && c.suit == last.suit && c.num - 1 == last.num))
                && m.getBelow().size() == 1;
    }

    public void addCards(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            addCard(c);
        }
    }

    public ArrayList<Card> getAfter(Card c) {
        below.clear();
        below.add(c);
        return below;
    }

    public void flipLast() {
        // do nothing
    }

    public boolean isComplete() {
        Card last = getLast();
        return last != null && getLast().num == Deck.KING;
    }
}

