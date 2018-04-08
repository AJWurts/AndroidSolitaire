package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Collection;

import static model.Card.size_y;


abstract public class Pile {
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected ArrayList<Card> below = new ArrayList<>();
    protected Paint p;
    protected RectF area;
    Paint resetLogo;
    private float x = -1;
    private float y = -1;

    public Pile() {
        p = new Paint();
        p.setColor(0x7719eab3);

        resetLogo = new Paint();
        resetLogo.setColor(0xFF04b745);
    }

    public Pile(int testVar) {
    }

    /**
     * Get cards after selected card
     *
     * @param c Card to look for
     * @return list of cards after current, including current
     */
    abstract public ArrayList<Card> getAfter(Card c);

    /**
     * Flip last card in pile
     */
    abstract public void flipLast();

    /**
     * Checks if movement can be placed on pile
     * @param m movement to check
     * @return true if can be placed, false otherwise
     */
    abstract public boolean validNextCard(Movement m);

    /**
     * Add cards from movement onto deck
     * @param m movement
     */
    abstract public void addCards(Movement m);

    /**
     * Resets pile to original coordinates based on starting card
     * Needs to be changed because it is based off PlayPile
     */
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
            y += size_y * 0.3;
        }
    }

    /**
     * Adds a card to the pile
     * @param c card to be added
     */
    public void addCard(Card c) {
        cards.add(c);
    }

    /**
     * Removes all cards from pile that are present in c
     * @param c collection of cards that will be removed
     */
    public void removeCards(Collection<Card> c) {
        cards.removeAll(c);
    }

    /**
     * Sets XY Coordinates of Pile
     * @param x new x coordinates
     * @param y new y coordinates
     */
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
        area = new RectF(x - Card.half_x, y - Card.half_y,
                x + Card.half_x, y + Card.half_y);
    }

    /**
     * Returns the last card if the pile size is greater than 0. null otherwise
     * @return card if pile size greater than one, null otherwise
     */
    public Card getLast() {
        if (size() == 0) {
            return null;
        } else
            return cards.get(cards.size() - 1);
    }

    /**
     * Gets coordinates of last card, or pile xy coords if pile size is 0
     * @return xy coordinates of last item in pile
     */
    public float[] getLastCoords() {
        if (size() > 0) {
            return new float[] {getLast().getX(), getLast().getY()};
        }
        return getCoords();
    }

    /**
     * Gets xy coordinates of pile
     * @return xy coordinates of pile
     */
    public float[] getCoords() {
        if (x == -1) {
            x = cards.get(0).getX();
            y = cards.get(0).getY();
        }
        return new float[] {x, y};
    }

    /**
     * CHecks to see if given card c is in pile
     * @param c card to look for
     * @return true if found, false if not
     */
    public boolean contains(Card c) {
        return cards.contains(c);
    }

    /**
     * Checks to see if xy coordinate is on top of pile location. Does not consider cards
     * @param xy xy coordinates it looks at
     * @return true of on top of pile, false otherwise
     */
    public boolean contains(float[] xy) {
        return area.contains(xy[0], xy[1]);
    }

    /**
     * Returns the size of the pile
     * @return size of pile
     */
    public int size() {
        return cards.size();
    }

    /**
     * Returns the distance from the pile to a given X Y coordinate
     * @param x x coordinate
     * @param y y coordinate
     * @return distance between (this.x, this.y) and (x, y)
     */
    public float distFrom(float x, float y) {
        return (float) Math.hypot(getLastCoords()[0] - x, getLastCoords()[1]- y);
    }

    /**
     * Clears Pile
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Draws self on canvas
     * @param canvas canvas to draw on
     */
    public void drawSelf(Canvas canvas) {

        canvas.drawRect(area, p);
//        canvas.drawCircle(area.centerX(), area.centerY(), 20, resetLogo);
    }

    /**
     * Gets XY coordinates of pile
     * @return x y coordinates
     */
    public float[] getXY() {
        return new float[]{x, y};
    }

    /**
     * Gets coords of next open place in pile
     * @return x y coords of new place
     */
    protected float[] getNextOpenCoords() {
        if (size() == 0) {
            return new float[] {x, y};
        } else {
            float[] temp = getLastCoords();
            temp[1] += Card.size_y * PlayPile.OFFSET;
            return temp;
        }
    }
}
