package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;



public class LeftoverPile extends Pile {

    private int numLeft;
    private Paint resetLogo;
    private Paint center;
    private Path wallpath = new Path();
    private float size;

    public LeftoverPile() {
        super();
        numLeft = size();
        resetLogo = new Paint();
        resetLogo.setColor(0xFF196636);
        resetLogo.setStyle(Paint.Style.FILL);
        center = new Paint();
        center.setColor(0xFF14A26B);

    }


    public LeftoverPile(int testVar) {
        super(testVar);
        numLeft = size();
    }

    /**
     * Set x, y coordinates of pile
     *
     * @param x coordinate
     * @param y coordinate
     */
    public void setXY(float x, float y) {
        super.setXY(x, y);
        size = Card.size_x * 0.25f;

        // I have no idea what this does, but apparently its something
        wallpath.moveTo(x + (float) ((size * 0.4f) * Math.cos(-Math.PI / 4)),
                y + (float) ((size * 0.4f) * Math.sin(-Math.PI / 4)));
        wallpath.lineTo(x + (float) ((size * 1.25f) * Math.cos(-Math.PI / 4)),
                y + (float) ((size * 1.25f) * Math.sin(-Math.PI / 4)));
        wallpath.lineTo(x + (float) (size * Math.cos(-Math.PI / 9)),
                y + (float) (size * Math.sin(-Math.PI / 9)));
        wallpath.lineTo(x + (float) ((size * 0.4f) * Math.cos(-Math.PI / 4)),
                y + (float) ((size * 0.4f) * Math.sin(-Math.PI / 4)));
    }

    /**
     * Reset cards in leftover pile back to upside down and aligned
     */
    public void resetPile() {
        for (Card c: cards) {
            c.setFlipped(true);
            float[] xy = getXY();
            c.setX(xy[0]);
            c.setY(xy[1]);
        }
    }

    /**
     * Add cards to leftover deck. unused
     * @param m movement that has cards to be added
     */
    public void addCards(Movement m) {
        float[] base = getCoords();
        for (Card c: m.getBelow()) {
            c.setXY(base[0] - Card.size_x * 1.5f, base[1]);
            addCard(c);
        }
    }

    /**
     * Alias for incPile
     */
    public void flipLast() {
        incPile();

    }

    /**
     * Clears below, adds c to below and returns below
     * @param c card to add to list
     * @return ArrayList with only card c in it
     */
    public ArrayList<Card> getAfter(Card c) {
        below.clear();
        below.add(c);
        return below;
    }

    /**
     * Checks to see if movement contains card to know if it can be placed back on the pile
     * @param m
     * @return if card is in below returns true, else false
     */
    public boolean validNextCard(Movement m) {

        return below.contains(m.getBase());
    }

    /**
     * Draws the pile background plus a little refresh arrow
     * @param canvas canvas background is drawn to
     */
    public void drawSelf(Canvas canvas) {
        super.drawSelf(canvas);

        // Draws the refresh arrow
        float size = Card.size_x * 0.25f;
        canvas.drawArc(area.centerX() - size,
                area.centerY() - size,
                area.centerX() + size,
                area.centerY() + size,
                45, 270, true, resetLogo);
        canvas.drawCircle(area.centerX(), area.centerY(), size * 0.7f, center);
        canvas.drawPath(wallpath, resetLogo);
    }

    /**
     * Moves one card to the left and flips it over. Has a bug where it does not go through the
     * entire pile before resetting
     * @return the card that was just moved
     */
    public Card incPile() {
        if (numLeft == 0) {
            // If pile empty reset pile
            numLeft = size();
            resetPile();
//            incPile();
        } else {
            // Move card off top and flip
            Card c = cards.get(numLeft - 1);
            c.setFlipped(false);
            c.setXY(c.getX() - Card.size_x * 1.5f, c.getY());
            numLeft--;
            return c;
        }
        return null;
    }

}
