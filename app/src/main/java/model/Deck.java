package model;

import com.sigpit.alexwurts.solitare.Movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static model.Card.SIZE_X;
import static model.Card.SIZE_Y;

/**
 * Created by Sigpit on 1/21/2018.
 */

public class Deck {

    public static final char[] SUITS = {'h', 's', 'd', 'c'};
    public static final int ACE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    public static final int HEARTS = 0;
    public static final int SPADES = 1;
    public static final int DIAMONDS = 2;
    public static final int CLUBS = 3;

    private ArrayList<Card> cards = new ArrayList<Card>();
    private LinkedList<Card> drawOrder = new LinkedList<>();

    private ArrayList<ArrayList<Card>> piles = new ArrayList<ArrayList<Card>>();
    private ArrayList<Card> movementHolder = new ArrayList<>();

    public Deck() {
        for (char s: SUITS) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(s, i, true));
            }
        }

        for (int i = 0; i < 7; i++) {
            piles.add(new ArrayList<Card>());
        }

    }

    public void shuffle() {
        Collections.shuffle(drawOrder);
    }

    public Card getCard(char s, int num) {
        switch ((int)s) {
            case HEARTS:
                return cards.get(num);
            case SPADES:
                return cards.get(13 * SPADES + num);
            case DIAMONDS:
                return cards.get(13 * DIAMONDS + num);
            case CLUBS:
                return cards.get(13 * CLUBS + num);
        }
        return cards.get(0);
    }

    public int getClosestPile(float x, float y) {
        float minDist = -1, dist;
        int closeIndex = 0;
        Card last;
        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i).size() != 0) {
                    last = piles.get(i).get(piles.get(i).size() - 1);
                dist = (float) Math.hypot(last.getX() - x, last.getY() - y);

                if (minDist == -1) {
                    minDist = dist;
                    closeIndex = i;
                } else if (dist < minDist) {
                    closeIndex = i;
                    minDist = dist;
                }
            }
        }
        return closeIndex;
    }

    public Movement getMovement(float x, float y) {

        for (int i = drawOrder.size() - 1; i >= 0; i--) {

            if (drawOrder.get(i).wasTouched(x, y)) {
                return getMovement(drawOrder.get(i));
            }
        }
        return null;
    }

    private Movement getMovement(Card c) {
        movementHolder.clear();
        int i;
        boolean collecting = false;
        for (i = 0; i < piles.size(); i++) {
            if (piles.get(i).contains(c)) {
                for (int j = 0; j < piles.get(i).size(); j++) {
                    if (collecting || piles.get(i).get(j).equals(c)) {
                        collecting = true;
                        movementHolder.add(piles.get(i).get(j));
                    }
                }
                break;
            }
        }
        piles.get(i).removeAll(movementHolder);
        return new Movement(c, (ArrayList<Card>)movementHolder.clone(), i);
    }

    public void resetPile(int i) {
        float x = -1;
        float y = -1;
        for (Card c: piles.get(i)) {
            if (x == -1) {
                x = c.getX();
                y = c.getY();
            }
            c.setX(x); c.setY(y);
            y += SIZE_Y * 0.2;
        }
    }

    public Card getCard(int s, int num) {
        return cards.get(13 * s + num);
    }

    public void flipLastCard(int i) {
        if (piles.get(i).size() > 0)
            piles.get(i).get(piles.get(i).size() - 1).setFlipped(false);
    }

    public void addToPile(Movement m, int pileNum) {
        ArrayList<Card> pile = piles.get(pileNum);
        Card last = pile.get(pile.size() - 1);
        m.setXY(last.getX(), last.getY());
        piles.get(pileNum).addAll(m.getBelow());
    }

    public void updateCard(Card c) {
        if (drawOrder.remove(c)) {
            drawOrder.addLast(c);
        }
    }

    public void updateCards(Movement m) {
        for (Card c: m.getBelow()) {
            updateCard(c);
        }
    }

    public LinkedList<Card> getDrawOrder() {
        return drawOrder;
    }

    public void loadSolitare(float cX, float cY) {
        shuffle();
        float GAP = 1.4f;
        float x = cX - (SIZE_X * (GAP * 3f));
        float y = cY;
        Card c;

        int i = 0;
        for (int col = 1; col < 8; col++) {
            y = cY;
            for (int num = 0; num < col; num++) {
                c = drawOrder.get(i++);
                c.setXY(x, y);
                c.updateTextSize();
                piles.get(col - 1).add(c);
                if (num == col - 1)
                    c.setFlipped(false);
                else
                    c.setFlipped(true);

                y += Card.SIZE_Y * 0.3;
            }

            x += SIZE_X * GAP;
        }

        for (; i < drawOrder.size(); i++) {
            c = drawOrder.get(i);
            c.setXY(cX - (SIZE_X * (GAP * 4f)) + (SIZE_X * (GAP * 7f)),
                    cY - Card.SIZE_Y * 1.5f);
            c.setFlipped(true);
            c.updateTextSize();
        }
    }

    public void addCard(Card c) {
        drawOrder.addLast(c);
    }
}
