package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static model.Card.size_x;
import static model.Card.size_y;



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
    protected ArrayList<Pile> piles = new ArrayList<>();
    private ArrayList<Card> cards = new ArrayList<>();
    private LinkedList<Card> drawOrder = new LinkedList<>();
    // Decks 0-6 for rows, 7 is leftover, 8-11 is aces positions
    private ArrayList<Card> movementHolder = new ArrayList<>();

    public Deck() {
        for (char s: SUITS) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(s, i, true));
            }
        }

        for (int i = 0; i < 12; i++) {
            if (0 <= i && i <= 6) {
                piles.add(new PlayPile());
            } else if (i == 7) {
                piles.add(new LeftoverPile());
            } else if (8 <= i && i <= 11) {
                piles.add(new AcePile());
            }
        }

    }

    public Deck(int testVar) {
        for (char s : SUITS) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(s, i, true, 0));
            }
        }

        for (int i = 0; i < 12; i++) {
            if (0 <= i && i <= 6) {
                piles.add(new PlayPile(i));
            } else if (i == 7) {
                piles.add(new LeftoverPile(i));
            } else if (8 <= i && i <= 11) {
                piles.add(new AcePile(i));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(drawOrder);
    }

    public int getClosestValidPile(float x, float y, Movement m) {
        float minDist = -1, dist;
        int closeIndex = -1;
        for (int i = 0; i < piles.size(); i++) {
            if (piles.get(i).validNextCard(m)) {
                dist = piles.get(i).distFrom(x, y);

                if ((minDist == -1 || dist < minDist)) {
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

    public Card getCard(int s, int num) {
        return cards.get(13 * s + num);
    }

    public void flipLastCard(int i) {
        if (piles.get(i).size() > 0)
            piles.get(i).flipLast();
    }

    public void addToPile(Movement m, int pileNum) {
        piles.get(pileNum).addCards(m);
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
        clearPiles();
        float GAP = 1.1f;
        float x = cX - (size_x * (GAP * 3f));
        float y = cY;
        Card c;


        int i = 0;
        for (int col = 1; col < 8; col++) {
            y = cY;
            for (int num = 0; num < col; num++) {
                c = drawOrder.get(i++);
                c.setXY(x, y);
                c.updateTextSize();
                piles.get(col - 1).addCard(c);
                if (num == 0) {
                    piles.get(col - 1).setXY(x, y);
                }

                if (num == col - 1)
                    c.setFlipped(false);
                else
                    c.setFlipped(true);

                y += Card.size_y * PlayPile.OFFSET;
            }

            x += size_x * GAP;
        }

        for (; i < drawOrder.size(); i++) {
            c = drawOrder.get(i);
            piles.get(7).addCard(c);
            piles.get(7).setXY(cX - (size_x * (GAP * 3f)) + (size_x * (GAP * 6f)),
                    cY - Card.size_y * 1.5f);
            c.setXY(cX - (size_x * (GAP * 3f)) + (size_x * (GAP * 6f)),
                    cY - Card.size_y * 1.5f);
            c.setFlipped(true);
            c.updateTextSize();
        }

        for (int j = 8; j <= 11; j++) {
            piles.get(j).setXY(cX - (size_x * (GAP * 3f)) + (size_x * GAP * (j - 8f)),
                    cY - size_y * 1.5f);
        }
        ((LeftoverPile)piles.get(7)).incPile();

    }

    public void addCard(Card c) {
        drawOrder.addLast(c);
    }

    public void incDeckCards() {
        Card c = ((LeftoverPile)piles.get(7)).incPile();
        if (c != null)
            updateCard(c);
    }

    public ArrayList<Pile> getPiles() {
        return piles;
    }

    public boolean onPile(int pileNum, float[] xy) {
        return piles.get(pileNum).contains(xy);
    }

    private Movement getMovement(Card c) {
        movementHolder.clear();
        int i;
        boolean found = false;
        for (i = 0; i < piles.size(); i++) {
            if (piles.get(i).contains(c)) {
                movementHolder = piles.get(i).getAfter(c);
                found = true;
                break;
            }
        }
        if (found) {
            piles.get(i).removeCards(movementHolder);
            return new Movement(c, (ArrayList<Card>) movementHolder.clone(), i);
        } else
            return null;
    }

    private void clearPiles() {
        for (Pile p : piles) {
            p.clear();
        }
    }

    public boolean hasFinished() {
        boolean isFinished = true;

        for (int i = 8; i <= 11; i++) {
            isFinished &= ((AcePile) piles.get(i)).isComplete();
        }

        return isFinished;
    }

}
