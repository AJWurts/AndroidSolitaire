package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static model.Card.SIZE_X;

/**
 * Created by Sigpit on 1/21/2018.
 */

public class Deck {

    private ArrayList<Card> cards = new ArrayList<Card>();
    private LinkedList<Card> drawOrder = new LinkedList<>();
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

    public Deck() {
        for (char s: SUITS) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(s, i, true));
            }
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

    public Card anyTouched(float x, float y) {
        Card temp;
        for (int i = drawOrder.size() - 1; i >= 0; i--) {
            temp = drawOrder.get(i);

            if (drawOrder.get(i).wasTouched(x, y)) {
                return temp;
            }
        }
        return null;
    }

    public Card getCard(int s, int num) {
        return cards.get(13 * s + num);
    }

    public void updateCard(Card c) {
        if (drawOrder.remove(c)) {
            drawOrder.addLast(c);
        }
    }

    public LinkedList<Card> getDrawOrder() {
        return drawOrder;
    }

    public void loadSolitare(float cX, float cY) {
        shuffle();
        float GAP = 1.4f;
        float x = cX - (SIZE_X * (GAP * 4f));
        float y = cY;
        Card c;

        int i = 0;
        for (int col = 0; col <= 7; col++) {
            y = cY;
            for (int num = 0; num < col; num++) {
                c = drawOrder.get(i++);
                c.setXY(x, y);
                c.updateTextSize();
                if (num == col - 1)
                    c.setFlipped(false);
                else
                    c.setFlipped(true);

                y += Card.SIZE_Y * 0.2;
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
