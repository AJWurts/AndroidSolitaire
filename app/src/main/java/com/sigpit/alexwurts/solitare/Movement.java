package com.sigpit.alexwurts.solitare;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collection;

import model.Card;

/**
 * Created by Sigpit on 1/24/2018.
 */

public class Movement {
    Card orig;
    ArrayList<Card> below = new ArrayList<Card>();
    int origPileIndex;

    public Movement(Card orig, ArrayList<Card> below, int origPileIndex) {
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

    public void setXY(float x, float y) {
        for (Card c: below) {
            y += Card.SIZE_Y * 0.3;
            c.setXY(x, y);
        }
    }

    public Collection<Card> getBelow() {
        return below;
    }

    public Card getBase() {
        return orig;
    }

    public int getOrigPileIndex() {
        return origPileIndex;
    }

    public void setOrigPileIndex(int origPileIndex) {
        this.origPileIndex = origPileIndex;
    }
}
