package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Collection;

import static model.Card.SIZE_Y;

/**
 * Created by Sigpit on 1/24/2018.
 */

abstract public class Pile {
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected ArrayList<Card> below = new ArrayList<>();
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

    abstract public ArrayList<Card> getAfter(Card c);

    public int size() {
        return cards.size();
    }

    abstract public void flipLast();

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

    abstract public boolean validNextCard(Card c);

    public float distFrom(float x, float y) {
        return (float) Math.hypot(getLastCoords()[0] - x, getLastCoords()[1]- y);
    }

    public void clear() {
        cards.clear();
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawRect(area, p);
    }

    abstract public void addCards(Movement m);

    protected float[] getNextOpenCoords() {
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
