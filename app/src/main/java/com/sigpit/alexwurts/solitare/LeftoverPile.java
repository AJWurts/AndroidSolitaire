package com.sigpit.alexwurts.solitare;

import java.util.ArrayList;
import java.util.Collection;

import model.Card;

/**
 * Created by Sigpit on 1/25/2018.
 */

public class LeftoverPile extends Pile {

    private int numLeft;

    public LeftoverPile(int id) {
        super(id);
        numLeft = size();
    }

    public Card incPile() {
        if (numLeft == 0) {
            numLeft = size();
            resetPile();
            incPile();
        } else {
            Card c = cards.get(numLeft - 1);
            c.setFlipped(false);
            c.setXY(c.getX() - Card.SIZE_X * 1.5f, c.getY());
            numLeft--;
            return c;
        }
        return null;
    }

    public void resetPile() {
        for (Card c: cards) {
            c.setFlipped(true);
            float[] xy = getXY();
            c.setX(xy[0]);
            c.setY(xy[1]);
        }
    }

    public void addToDeck(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0] - Card.SIZE_X * 1.5f, base[1]);
            addCard(c);
        }
    }

    public void flipLast() {
        //incPile();
    }

    public ArrayList<Card> getAfter(Card c) {
        ArrayList<Card> out = new ArrayList<Card>();
        out.add(c);
        return out;
    }

}
