package model;

import java.util.ArrayList;

import static model.Card.SIZE_Y;

/**
 * Created by Sigpit on 1/27/2018.
 */

public class PlayPile extends Pile {


    public PlayPile(int id) {
        super(id);
    }

    @Override
    public boolean validNextCard(Card c) {
        Card last = getLast();
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
       return false;  // Shouldn't happen but probably will
    }

    public void addCards(Movement m) {
        float[] base = getNextOpenCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            base[1] += SIZE_Y * 0.3f;
            addCard(c);
        }
    }
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

    public void flipLast() {
        Card last = getLast();
        if (last != null)
            getLast().setFlipped(false);
    }
}
