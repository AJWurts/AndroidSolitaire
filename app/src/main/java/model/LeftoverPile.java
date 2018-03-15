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

    public void setXY(float x, float y) {
        super.setXY(x, y);
        size = Card.size_x * 0.25f;

        wallpath.moveTo(x + (float) ((size * 0.4f) * Math.cos(-Math.PI / 4)),////-Math.PI / 4)),
                y + (float) ((size * 0.4f) * Math.sin(-Math.PI / 4)));//-Math.PI / 4)));
        wallpath.lineTo(x + (float) ((size * 1.25f) * Math.cos(-Math.PI / 4)),
                y + (float) ((size * 1.25f) * Math.sin(-Math.PI / 4)));
        wallpath.lineTo(x + (float) (size * Math.cos(-Math.PI / 9)),
                y + (float) (size * Math.sin(-Math.PI / 9)));
        wallpath.lineTo(x + (float) ((size * 0.4f) * Math.cos(-Math.PI / 4)),
                y + (float) ((size * 0.4f) * Math.sin(-Math.PI / 4)));
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
            c.setXY(base[0] - Card.size_x * 1.5f, base[1]);
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

    public void drawSelf(Canvas canvas) {
        super.drawSelf(canvas);

        float size = Card.size_x * 0.25f;
        canvas.drawArc(area.centerX() - size,
                area.centerY() - size,
                area.centerX() + size,
                area.centerY() + size,
                45, 270, true, resetLogo);
        canvas.drawCircle(area.centerX(), area.centerY(), size * 0.7f, center);
        canvas.drawPath(wallpath, resetLogo);
    }

    public Card incPile() {
        if (numLeft == 0) {
            numLeft = size();
            resetPile();
//            incPile();
        } else {
            Card c = cards.get(numLeft - 1);
            c.setFlipped(false);
            c.setXY(c.getX() - Card.size_x * 1.5f, c.getY());
            numLeft--;
            return c;
        }
        return null;
    }


}
