package model;

import java.util.ArrayList;

/**
 * Created by Sigpit on 1/26/2018.
 */

public class AcePile extends Pile {

    public AcePile(int id) {
        super(id);
    }

    public boolean validNextCard(Card c) {
        Card last = getLast();
        return (last == null && c.num == Deck.ACE) ||
                (last != null && c.suit == last.suit && c.num - 1 == last.num);
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

}
