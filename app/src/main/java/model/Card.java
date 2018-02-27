package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;



public class Card {
    public static float SIZE_X = 250 / 2;
    public static float HALF_X = SIZE_X / 2;
    public static float SIZE_Y = 350 / 2;
    public static float HALF_Y = SIZE_Y / 2;

    public final char suit;
    public final int num;
    float x, y;
    private Typeface consolas;
    private Paint suitColor;
    private Paint backgroundColor;
    private Paint borderColor;
    private Paint backColor;
    private RectF area;
    private boolean isFlipped = false;

    public Card(char suit, int num) {
        this.suit = suit;
        this.num = num;
        consolas = Typeface.create("consolas", Typeface.NORMAL);

        // Colors
        suitColor = new Paint();
        suitColor.setColor(getColor());
        suitColor.setTypeface(consolas);
        suitColor.setTextSize(SIZE_Y * 0.25f);
        suitColor.setStyle(Paint.Style.FILL);

        backgroundColor = new Paint();
        backgroundColor.setColor(0xFFFFFFFF);
        backgroundColor.setStyle(Paint.Style.FILL);

        borderColor = new Paint();
        borderColor.setColor(0xFF000000);
        borderColor.setStyle(Paint.Style.STROKE);
        borderColor.setStrokeWidth(4);

        backColor = new Paint();
        backColor.setColor(0xFF000088);
        backColor.setStyle(Paint.Style.FILL);

        area = new RectF(0, 0, SIZE_X, SIZE_Y);
    }

    public Card(char suit, int num, boolean isFlipped) {
        this(suit, num);
        this.isFlipped = isFlipped;
    }

    public Card(char suit, int num, int testVar) {
        this.suit = suit;
        this.num = num;
    }

    public Card(char suit, int num, boolean isFlipped, int testVar) {
        this(suit, num, testVar);
        this.isFlipped = isFlipped;
    }

    public boolean equals(Object other) {
        return toString().equals(other.toString());
    }

    public String toString() {
        return String.format("%c%d", suit, num);
    }

    public void drawCard(Canvas canvas, float centerX, float centerY) {
        int r = 10; // corners radius
        area = new RectF(centerX - HALF_X, centerY - HALF_Y,
                centerX + HALF_X, centerY + HALF_Y);
        canvas.drawRoundRect(area, r, r, backgroundColor);
        canvas.drawRoundRect(area, r, r, borderColor);
        if (!isFlipped) {
            if (num == 10) {
                canvas.drawText(getChar(), centerX - SIZE_X * 0.47f, centerY - SIZE_Y * 0.28f, suitColor);
                canvas.drawText(getChar(), centerX + SIZE_X * 0.08f, centerY + SIZE_Y * 0.45f, suitColor);
            } else {
                canvas.drawText(getChar(), centerX - SIZE_X * 0.44f, centerY - SIZE_Y * 0.28f, suitColor);
                canvas.drawText(getChar(), centerX + SIZE_X * 0.20f, centerY + SIZE_Y * 0.45f, suitColor);
            }
            drawIcon(canvas, centerX + SIZE_X * 0.33f, centerY - SIZE_Y * 0.38f);
            drawIcon(canvas, centerX - SIZE_X * 0.33f, centerY + SIZE_Y * 0.35f);
        } else {
            canvas.drawRect(centerX - (SIZE_X * 0.45f), centerY - (SIZE_Y * 0.45f),
                    centerX + (SIZE_X * 0.45f), centerY + (SIZE_Y * 0.45f), backColor);

        }
    }

    public void drawCard(Canvas canvas) {
        drawCard(canvas, x, y);
    }

    public String getChar() {
        if (2 <= num && num <= 10) {
            return Integer.toString(num);
        }
        switch (num) {
            case Deck.ACE:
                return "A";
            case Deck.JACK:
                return "J";
            case Deck.QUEEN:
                return "Q";
            case Deck.KING:
                return "K";
        }
        return null;
    }

    public void flip() {
        isFlipped = !isFlipped;
    }

    public boolean wasTouched(float x, float y) {
        return !isFlipped && area.contains(x, y);
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float[] getXY() {
        return new float[]{x, y};
    }

    public void setXY(float[] xy) {
        x = xy[0];
        y = xy[1];
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public void updateTextSize() {
        suitColor.setTextSize(SIZE_Y * 0.25f);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    private void drawIcon(Canvas canvas, float centerX, float centerY) {
        Path wallpath = new Path();
        switch (suit) {
            case 'h':
                wallpath.moveTo(centerX + (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + SIZE_Y * 0.1f);
                wallpath.lineTo(centerX - (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - SIZE_Y * 0.05f);
                wallpath.lineTo(centerX + (SIZE_X * 0.1f), centerY);
                canvas.drawPath(wallpath, suitColor);

                float r;
                r = (float)Math.hypot(SIZE_X * 0.1,  SIZE_Y * 0.05) / 2;
                canvas.drawCircle(centerX - (SIZE_X * 0.05f), centerY - (SIZE_Y * 0.025f),
                        r, suitColor);
                canvas.drawCircle(centerX + (SIZE_X * 0.05f), centerY - (SIZE_Y * 0.025f),
                        r, suitColor);
                break;
            case 's':
                wallpath = new Path();

                wallpath.moveTo(centerX, centerY + SIZE_Y * 0.05f);
                wallpath.lineTo(centerX + (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - SIZE_Y * 0.1f);
                wallpath.lineTo(centerX - (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + SIZE_Y * 0.05f);
                wallpath.lineTo(centerX - (SIZE_X * 0.07f), centerY + (SIZE_Y * 0.1f));
                wallpath.lineTo(centerX + (SIZE_X * 0.07f), centerY + (SIZE_Y * 0.1f));
                wallpath.lineTo(centerX, centerY + SIZE_Y * 0.05f);
                canvas.drawPath(wallpath, suitColor);

                float r1;
                r1 = (float)Math.hypot(SIZE_X * 0.1,  SIZE_Y * 0.05) / 2;
                canvas.drawCircle(centerX - (SIZE_X * 0.05f), centerY + (SIZE_Y * 0.025f),
                        r1, suitColor);
                canvas.drawCircle(centerX + (SIZE_X * 0.05f), centerY + (SIZE_Y * 0.025f),
                        r1, suitColor);
                break;
            case 'd':
                wallpath = new Path();
                wallpath.moveTo(centerX, centerY + SIZE_Y * 0.1f);
                wallpath.lineTo(centerX + (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - SIZE_Y * 0.1f);
                wallpath.lineTo(centerX - (SIZE_X * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + SIZE_Y * 0.1f);

                canvas.drawPath(wallpath, suitColor);
                break;
            case 'c':
                wallpath = new Path();
                wallpath.moveTo(centerX, centerY);
                wallpath.lineTo(centerX - (SIZE_X * 0.07f), centerY + (SIZE_Y * 0.08f));
                wallpath.lineTo(centerX + (SIZE_X * 0.07f), centerY + (SIZE_Y * 0.08f));
                wallpath.lineTo(centerX, centerY);
                canvas.drawPath(wallpath, suitColor);

                float r3;
                r3 = SIZE_X * 0.055f;
                canvas.drawCircle(centerX + (SIZE_X * 0.07f), centerY , r3, suitColor );
                canvas.drawCircle(centerX - (SIZE_X * 0.07f), centerY, r3, suitColor);
                canvas.drawCircle(centerX, centerY - SIZE_Y * 0.06f, r3, suitColor);
                canvas.drawCircle(centerX, centerY, r3, suitColor);

        }

    }

    private int getColor() {
        if (suit == 's' || suit == 'c') {
            return 0xFF000000;
        } else {
            return 0xFFFF0000;
        }
    }
}
