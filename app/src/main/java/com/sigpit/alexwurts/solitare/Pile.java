package com.sigpit.alexwurts.solitare;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Collection;

import model.Card;

import static model.Card.SIZE_Y;

/**
 * Created by Sigpit on 1/24/2018.
 */

public class Pile {
    protected ArrayList<Card> cards = new ArrayList<Card>();
    private float x = -1;
    private float y = -1;
    private int id;
    private Paint p;
    private RectF area;

    public Pile(int id) {
        this.id = id;
        p = new Paint();
        p.setColor(0x7719eab3);
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
        area = new RectF(x - Card.HALF_X, y - Card.HALF_Y,
                        x + Card.HALF_X, y + Card.HALF_Y);
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

    public boolean contains(float[] xy) {
        return area.contains(xy[0], xy[1]);
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
            y += SIZE_Y * 0.3;
        }
    }

    public boolean validNextCard(Card c) {
        Card last = getLast();
        if (last != null && last.isFlipped()) {
            return true;
        } else if (last != null) {
            return ((((c.suit == 'c' || c.suit == 's')
                    && (last.suit == 'h' || last.suit == 'd'))
                    || ((c.suit == 'h' || c.suit == 'd') &&
                    (last.suit == 'c' || last.suit == 's')))
                    && (last.num - 1 == c.num) && (0 <= id && id <= 6))

                    || (8 <= id && id <= 11 && c.suit == last.suit
                    && c.num - 1 == last.num);
        }
        return false;
    }



    public float distFrom(float x, float y) {
        return (float) Math.hypot(getLastCoords()[0] - x, getLastCoords()[1]- y);
    }

    public void clear() {
        cards.clear();
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawRect(area, p);
    }

    public void addCards(Movement m) {
        float[] base = getNextOpenCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            base[1] += SIZE_Y * 0.3f;
            addCard(c);
        }
    }

    public void addToDeck(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0], base[1]);
            addCard(c);
        }
    }

    private float[] getNextOpenCoords() {
        if (size() == 0) {
            return new float[] {x, y};
        } else {
            float[] temp = getLastCoords();
            temp[1] += Card.SIZE_Y * 0.3;
            return temp;
        }
    }

    public float[] getXY() {

        return new float[] {x, y};
    }
}
