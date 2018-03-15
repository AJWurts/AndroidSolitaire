package model;

import java.util.ArrayList;
import java.util.Collection;


public class Movement {
    private Card orig;
    private ArrayList<Card> below = new ArrayList<>();
    private int origPileIndex;

    Movement(Card orig, ArrayList<Card> below, int origPileIndex) {
        this.orig = orig;
        this.below = below;
        this.origPileIndex = origPileIndex;
    }

    public void move(float xDiff, float yDiff) {
        float[] old;
        for (Card c: below) {
            old = c.getXY();
            c.setXY(old[0] + xDiff, old[1] + yDiff);
        }
    }

    public Collection<Card> getBelow() {
        return below;
    }

    // Getters and Setters
    public int getOrigPileIndex() {
        return origPileIndex;
    }

    public void setOrigPileIndex(int origPileIndex) {
        this.origPileIndex = origPileIndex;
    }

    public void setXY(float x, float y) {
        for (Card c : below) {
            y += Card.size_y * 0.3;
            c.setXY(x, y);
        }
    }

    public Card getBase() {
        return orig;
    }
}
