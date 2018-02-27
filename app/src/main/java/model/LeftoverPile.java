package model;

import java.util.ArrayList;



public class LeftoverPile extends Pile {

    private int numLeft;

    public LeftoverPile() {
        super();
        numLeft = size();
    }

    public LeftoverPile(int testVar) {
        super(testVar);
        numLeft = size();
    }

    public void resetPile() {
        for (Card c: cards) {
            c.setFlipped(true);
            float[] xy = getXY();
            c.setX(xy[0]);
            c.setY(xy[1]);
        }
    }

    public void addCards(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0] - Card.SIZE_X * 1.5f, base[1]);
            addCard(c);
        }
    }

    public void flipLast() {
        incPile();

    }

    public ArrayList<Card> getAfter(Card c) {
        below.clear();
        below.add(c);
        return below;
    }

    public boolean validNextCard(Movement m) {

        return below.contains(m.getBase());
    }

    public Card incPile() {
        if (numLeft == 0) {
            numLeft = size();
            resetPile();
//            incPile();
        } else {
            Card c = cards.get(numLeft - 1);
            c.setFlipped(false);
            c.setXY(c.getX() - Card.SIZE_X * 1.5f, c.getY());
            numLeft--;
            return c;
        }
        return null;
    }


}
