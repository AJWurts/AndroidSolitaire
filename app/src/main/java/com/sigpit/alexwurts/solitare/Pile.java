package com.sigpit.alexwurts.solitare;

import java.util.ArrayList;
import java.util.Collection;

import model.Card;

import static model.Card.SIZE_Y;

/**
 * Created by Sigpit on 1/24/2018.
 */

public class Pile {
    private ArrayList<Card> cards = new ArrayList<Card>();
    private float x = -1;
    private float y = -1;
    private int id;

    public Pile(int id) {
        this.id = id;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public void removeCards(Collection<Card> c) {
        cards.removeAll(c);
    }

    public void addCards(Collection<Card> c) {
        cards.addAll(c);
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Card getLast() {
        if (size() == 0) {
            return null;
        } else
           return cards.get(cards.size() - 1);
    }

    public float[] getLastCoords() {
        if (size() > 0) {
            return new float[] {getLast().getX(), getLast().getY()};
        }
        return getCoords();
    }

    public float[] getCoords() {
        if (x == -1) {
            x = cards.get(0).getX();
            y = cards.get(0).getY();
        }
        return new float[] {x, y};
    }

    public boolean contains(Card c) {
        return cards.contains(c);
    }

    public ArrayList<Card> getAfter(Card c) {
        ArrayList<Card> out = new ArrayList<Card>();
        boolean collecting = false;
        for (int i = 0; i < cards.size(); i++) {
            if (collecting || cards.get(i).equals(c)) {
                collecting = true;
                out.add(cards.get(i));
            }
        }
        return out;
    }

    public int size() {
        return cards.size();
    }

    public void flipLast() {
        Card last = getLast();
        if (last != null)
           getLast().setFlipped(false);
    }

    public void resetPile() {
        float x = -1;
        float y = -1;
        for (Card c : cards) {
            if (x == -1) {
                x = c.getX();
                y = c.getY();
            }
            c.setX(x);
            c.setY(y);
            y += SIZE_Y * 0.2;
        }
    }

    public boolean validNextCard(Card c) {
        Card last = getLast();
        if (last.isFlipped()) {
            return true;
        } else
          return (((c.suit == 'c' || c.suit == 's')
                    && (last.suit == 'h' || last.suit == 'd'))
                    || ((c.suit == 'h' || c.suit == 'd') &&
                        (last.suit == 'c' || last.suit == 's')))
                    && (last.num - 1 == c.num)

                  || (8 <= id && id <= 11 && c.suit == last.suit
                        && c.num - 1 == last.num);
    }

    public float distFrom(float x, float y) {
        return (float) Math.hypot(getLastCoords()[0] - x, getLastCoords()[1]- y);
    }

    public void clear() {
        cards.clear();
    }
}
