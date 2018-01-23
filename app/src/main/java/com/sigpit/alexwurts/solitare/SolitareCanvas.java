package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.Card;
import model.Deck;

/**
 * Created by Sigpit on 1/21/2018.
 */

public class SolitareCanvas extends SurfaceView implements SurfaceHolder.Callback {

    private Deck deck = new Deck();
    private ArrayList<Deck> piles = new ArrayList<>();
    private Card moving;
    private boolean down = false;
    private float[] initXY = new float[2];
    private float[] originXY = new float[2];
    private float[] shapeOriginXY = new float[2];

//    Handler handler = new Handler(Looper.getMainLooper());
//    Runnable updateCards = new Runnable() {
//        public void run() {
//            drawCards();
//            handler.postDelayed(this, 25);
//        }
//    };

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
        Card c;

        int action = event.getActionMasked();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if (!down) {
                    down = true;
                    c = deck.anyTouched(event.getX(), event.getY());
                    if (c != null) {
                        moving = c;
                        initXY[0] = event.getX();
                        initXY[1] = event.getY();
                        originXY[0] = event.getX();
                        originXY[1] = event.getY();
                        shapeOriginXY = c.getXY();
                    }
                } else if (moving != null) {
                    float diffX = event.getX() - initXY[0];
                    float diffY = event.getY() - initXY[1];
                    float[] old = moving.getXY();
                    moving.setXY(old[0] + diffX, old[1] + diffY);
                    initXY = old;
                    deck.updateCard(moving);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (down && moving != null) {
                    float diffX = event.getX() - initXY[0];
                    float diffY = event.getY() - initXY[1];
                    float[] old = moving.getXY();
                    moving.setXY(old[0] + diffX, old[1] + diffY);
                    initXY[0] = event.getX();
                    initXY[1] = event.getY();
                    deck.updateCard(moving);
                }
                break;
            case MotionEvent.ACTION_UP:
                down = false;

                if (moving != null && Math.abs(event.getX() - originXY[0]) + Math.abs(event.getY() - originXY[1]) < 5) {
                    moving.flip();
                    moving.setXY(shapeOriginXY);
                }
                moving = null;
                initXY[0] = 0; initXY[1] = 0;
                break;
        }
        drawCards();
        return true;
    }

    public void drawCards() {
        Canvas canvas = getHolder().lockCanvas();

        canvas.drawColor(0xFF196636);
        for (Card c: deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    public void drawCards(Canvas canvas) {
        canvas.drawColor(0xFF196636);
        for (Card c: deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
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
        drawCards(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawSolitare();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
