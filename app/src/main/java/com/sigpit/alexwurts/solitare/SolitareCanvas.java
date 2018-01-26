package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.Collection;

import model.Card;
import model.Deck;

/**
 * Created by Sigpit on 1/21/2018.
 */

public class SolitareCanvas extends SurfaceView implements SurfaceHolder.Callback {

    private Deck deck = new Deck();

    private Movement moving;
    private boolean down = false;
    private float[] initXY = new float[2];
    private float[] originXY = new float[2];

    private static final int BACKGROUND_COLOR = 0xFF196636;


    public SolitareCanvas(Context context) {
        super(context);
    }

    public SolitareCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SolitareCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup() {
        getHolder().addCallback(this);
        Card c;
        for (int s = 0; s < 4; s++) {
            for (int i = 0; i <= 12; i++) {
                c = deck.getCard(s, i);
                c.setXY(400, 400);
                deck.addCard(c);
            }
        }
        deck.shuffle();
    }

    public boolean onTouchEvent(MotionEvent event) {
        Movement c;

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!down && !deck.onPile(7, new float[] {event.getX(), event.getY()})) {
                    down = true;
                    c = deck.getMovement(event.getX(), event.getY());
                    if (c != null && !c.getBase().isFlipped()) {
                        moving = c;
                        initXY[0] = event.getX();
                        initXY[1] = event.getY();
                        originXY[0] = event.getX();
                        originXY[1] = event.getY();
                    } else if (c != null ){
                        deck.addToDeck(c, c.getOrigPileIndex());
                    }
                }  else if ( deck.onPile(7, new float[] {event.getX(), event.getY()})) {
                    deck.incDeckCards();
                } else if (moving != null) {
                    float diffX = event.getX() - initXY[0];
                    float diffY = event.getY() - initXY[1];
                    float[] old = moving.getBase().getXY();
                    moving.move(old[0] + diffX, old[1] + diffY);
                    initXY = old;
                    deck.updateCards(moving);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (down && moving != null) {
                    float diffX = event.getX() - initXY[0];
                    float diffY = event.getY() - initXY[1];
                    moving.move(diffX, diffY);
                    initXY[0] = event.getX();
                    initXY[1] = event.getY();
                    deck.updateCards(moving);
                }
                break;
            case MotionEvent.ACTION_UP:

                if (down && moving != null) {
                    int i = deck.getClosestValidPile(event.getX(),
                            event.getY(),
                            moving.getBase());

                    if (i == -1 || (8 <= i && i <= 11)) {
                        if (i == -1)
                            i = 7;
                        else
                            deck.flipLastCard(moving.getOrigPileIndex());
                        deck.addToDeck(moving, i);
                    } else {
                        deck.addToPile(moving, i);
                        if (i != moving.getOrigPileIndex())
                            deck.flipLastCard(moving.getOrigPileIndex());
                    }
                }



                // For manual flipping
//                if (moving != null && Math.abs(event.getX() - originXY[0]) + Math.abs(event.getY() - originXY[1]) < 5) {
//                    moving.flip();
//                    moving.setXY(shapeOriginXY);
//                }
                down = false;
                moving = null;
                initXY[0] = 0;
                initXY[1] = 0;
                break;
        }

        drawCards();
        return true;
    }

    public void drawCards() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(0xFF196636);
        drawPiles(canvas);

        for (Card c : deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    public void drawCards(Canvas canvas) {

        for (Card c : deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
    }

    public void drawPiles(Canvas canvas) {
        for (Pile p: deck.getPiles()) {
            p.drawSelf(canvas);
        }
    }

    public void initCanvas() {
        Canvas canvas = getHolder().lockCanvas();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Card.SIZE_X = width * 0.10f;
        Card.SIZE_Y = Card.SIZE_X * 1.4f;

        Card.HALF_X = Card.SIZE_X / 2;
        Card.HALF_Y = Card.SIZE_Y / 2;

        canvas.drawColor(0xFF196636);

//        deck.loadSolitare(width / 2, height / 2 - height * 0.30f);

        drawCards(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }

    public void drawSolitare() {
        Canvas canvas = getHolder().lockCanvas();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Card.SIZE_X = width * 0.10f;
        Card.SIZE_Y = Card.SIZE_X * 1.4f;

        Card.HALF_X = Card.SIZE_X / 2;
        Card.HALF_Y = Card.SIZE_Y / 2;

        canvas.drawColor(0xFF196636);

        deck.loadSolitare(width / 2, height / 2 - height * 0.30f);
        drawPiles(canvas);
        drawCards(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initCanvas();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
