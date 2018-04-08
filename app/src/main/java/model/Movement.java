package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Used for moving groups of cards between piles
 */
public class Movement {
    private Card orig;
    private ArrayList<Card> below = new ArrayList<>();
    private int origPileIndex;

    Movement(Card orig, ArrayList<Card> below, int origPileIndex) {
        this.orig = orig;
        this.below = below;
        this.origPileIndex = origPileIndex;
    }

    /**
     * Moves the selected cards
     *
     * @param xDiff x distance moved
     * @param yDiff y distance moved
     */
    public void move(float xDiff, float yDiff) {
        float[] old;
        for (Card c: below) {
            old = c.getXY();
            c.setXY(old[0] + xDiff, old[1] + yDiff);
        }
    }

    /**
     * Gets the cards below the top card
     * @return returns collection of cards below
     */
    public Collection<Card> getBelow() {
        return below;
    }

    /**
     * Returns original pile index
     * @return int of original pile
     */
    public int getOrigPileIndex() {
        return origPileIndex;
    }

    /**
     * Sets original pile index
     * @param origPileIndex new original pile index
     */
    public void setOrigPileIndex(int origPileIndex) {
        this.origPileIndex = origPileIndex;
    }

    /**
     * Sets XY coordinates of movement while giving adjusting placement
     * @param x new x coordinate
     * @param y new y coordinate
     */
    public void setXY(float x, float y) {
        for (Card c : below) {
            y += Card.size_y * 0.3;
            c.setXY(x, y);
        }
    }

    /**
     * Returns base card
     * @return base card
     */
    public Card getBase() {
        return orig;
    }
}
