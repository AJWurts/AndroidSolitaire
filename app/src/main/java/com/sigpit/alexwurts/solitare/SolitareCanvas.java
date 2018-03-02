package com.sigpit.alexwurts.solitare;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import model.Card;
import model.Deck;
import model.Movement;
import model.Pile;


public class SolitareCanvas extends SurfaceView implements SurfaceHolder.Callback {

    private static final int BACKGROUND_COLOR = 0xFF196636;
    MainActivity main;
    private Deck deck = new Deck();
    private Movement moving;
    private boolean isSolitareLoaded = false;
    private boolean down = false;
    private float[] initXY = new float[2];
    private float[] originXY = new float[2];
    private Statistics stats = new Statistics();


    public SolitareCanvas(Context context) {
        super(context);
        stats = new Statistics();
    }

    public SolitareCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        isSolitareLoaded = false;
    }

    public SolitareCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    /***
     * Handle Touch Events
     * @param event
     * @return true if event not handled, false if handled.
     */
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        Movement c;

        if (!isSolitareLoaded) {
            return false;
        }

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
                        deck.addToPile(c, c.getOrigPileIndex());
                    }
                }  else if ( deck.onPile(7, new float[] {event.getX(), event.getY()})) {
                    deck.incDeckCards();
                    stats.incMoves();
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
                            moving);

                    deck.addToPile(moving, i);
                    if (i != moving.getOrigPileIndex()) {
                        deck.flipLastCard(moving.getOrigPileIndex());
                        stats.incMoves();
                    }
                    if (deck.hasFinished()) {
                        isSolitareLoaded = false;
                        stats.endTimer();
                        main.openFinishedWindow(new View(getContext()));
                    }
                }

                down = false;
                moving = null;
                initXY[0] = 0;
                initXY[1] = 0;

                break;
        }

        drawCards();
        return true;
    }

    /**
     * Set up main variables for SolitareCanvas
     *
     * @param main Connection to main activity
     */
    public void setup(MainActivity main) {
        this.main = main;
        getHolder().addCallback(this);
        Card c;
        for (int s = 0; s < 4; s++) {
            for (int i = 0; i <= 12; i++) {
                c = deck.getCard(s, i);
                c.setXY(400, 400); // move all cards to 400,400 to start out
                deck.addCard(c);
            }
        }
        deck.shuffle();
    }

    /***
     * Draws cards directly the canvas
     */
    public void drawCards() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(getResources().getColor(R.color.cardTable));
        drawPiles(canvas);

        for (Card c : deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

    /***
     * Takes a canvas and draws the cards on the canvas
     * @param canvas canvas to draw on
     */
    public void drawCards(Canvas canvas) {

        for (Card c : deck.getDrawOrder()) {
            c.drawCard(canvas);
        }
    }

    /***
     * Draws the translucent boxes for the card piles
     * @param canvas canvas to draw on
     */
    public void drawPiles(Canvas canvas) {
        for (Pile p: deck.getPiles()) {
            p.drawSelf(canvas);
        }
    }

    /***
     * Setup for canvas object by getting screen size, setting card size based
     * on screen size, and drawing cards.
     */
    public void initCanvas() {
        Canvas canvas = getHolder().lockCanvas();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Card.SIZE_X = width * 0.10f;
        Card.SIZE_Y = Card.SIZE_X * 1.4f;

        Card.HALF_X = Card.SIZE_X / 2;
        Card.HALF_Y = Card.SIZE_Y / 2;

        canvas.drawColor(getResources().getColor(R.color.cardTable));

//        deck.loadSolitare(width / 2, height / 2 - height * 0.30f);

        drawCards(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }

    /***
     * Draws solitaire on the canvas
     * Starts the statistics timer
     * Enables touch interactions
     */
    public void drawSolitare() {
        stats.startTimer();
        isSolitareLoaded = true;
        Canvas canvas = getHolder().lockCanvas();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Card.SIZE_X = width * 0.10f;
        Card.SIZE_Y = Card.SIZE_X * 1.4f;

        Card.HALF_X = Card.SIZE_X / 2;
        Card.HALF_Y = Card.SIZE_Y / 2;

        canvas.drawColor(getResources().getColor(R.color.cardTable));

        deck.loadSolitare(width / 2, height / 2 - height * 0.26f);
        drawPiles(canvas);
        drawCards(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }

    /***
     * Returns Statistics Object
     * @return Statistics Object with currentTime and currentMoves set from most recent
     * finished game
     */
    public Statistics getStats() {
        return stats;
    }

}
