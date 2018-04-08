package model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;



public class Card {
    public static float size_x = 250 / 2;
    public static float half_x = size_x / 2;
    public static float size_y = 350 / 2;
    public static float half_y = size_y / 2;

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
        suitColor.setTextSize(size_y * 0.25f);
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

        area = new RectF(0, 0, size_x, size_y);
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

    /**
     * Sets global card size
     *
     * @param x x size
     * @param y y size
     */
    public static void setCardSize(float x, float y) {
        size_x = x;
        half_x = size_x / 2;
        size_y = y;
        half_y = size_y / 2;
    }

    /**
     * Checks equality based on suit and number
     * @param other other card
     * @return true if same suit and number, false otherwise
     */
    public boolean equals(Object other) {
        return toString().equals(other.toString());
    }

    /**
     * String of card suit and number combined "%c%d", suit, num
     * @return card string
     */
    public String toString() {
        return String.format("%c%d", suit, num);
    }

    /**
     * Draws card on canvas at centerX and centerY
     * @param canvas canvas to draw on
     * @param centerX center X coordinate
     * @param centerY center Y coordinate
     */
    public void drawCard(Canvas canvas, float centerX, float centerY) {
        int r = 10; // corners radius
        area = new RectF(centerX - half_x, centerY - half_y,
                centerX + half_x, centerY + half_y);
        canvas.drawRoundRect(area, r, r, backgroundColor);
        canvas.drawRoundRect(area, r, r, borderColor);
        // Some number shifting based on double digit number 10
        if (!isFlipped) {
            if (num == 10) {
                canvas.drawText(getChar(), centerX - size_x * 0.47f, centerY - size_y * 0.28f, suitColor);
                canvas.drawText(getChar(), centerX + size_x * 0.08f, centerY + size_y * 0.45f, suitColor);
            } else {
                canvas.drawText(getChar(), centerX - size_x * 0.44f, centerY - size_y * 0.28f, suitColor);
                canvas.drawText(getChar(), centerX + size_x * 0.20f, centerY + size_y * 0.45f, suitColor);
            }
            drawIcon(canvas, centerX + size_x * 0.33f, centerY - size_y * 0.38f);
            drawIcon(canvas, centerX - size_x * 0.33f, centerY + size_y * 0.35f);
        } else {
            canvas.drawRect(centerX - (size_x * 0.45f), centerY - (size_y * 0.45f),
                    centerX + (size_x * 0.45f), centerY + (size_y * 0.45f), backColor);

        }
    }

    /**
     * Draws Card on Canvas
     * @param canvas canvas to draw on
     */
    public void drawCard(Canvas canvas) {
        drawCard(canvas, x, y);
    }

    /**
     * Gets character designation of num
     * @return string for num
     */
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

    /**
     * Flips Card
     */
    public void flip() {
        isFlipped = !isFlipped;
    }

    /**
     * Checks to see if it is face up and touched
     * @param x x coordinate of test
     * @param y y coordinate of test
     * @return
     */
    public boolean wasTouched(float x, float y) {
        return !isFlipped && area.contains(x, y);
    }

    /**
     * Sets XY Coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets XY Coordinates
     * @return float array of x y coordinates
     */
    public float[] getXY() {
        return new float[]{x, y};
    }

    /**
     * Sets XY Coordinates using a float array
     * @param xy
     */
    public void setXY(float[] xy) {
        x = xy[0];
        y = xy[1];
    }

    /**
     * checks if flipped
     * @return true if flipped (face down), false if not flipped (face up)
     */
    public boolean isFlipped() {
        return isFlipped;
    }

    /**
     * Sets Flip orientation
     * @param flipped
     */
    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    /**
     * Enlarges text size by 25%
     */
    public void updateTextSize() {
        suitColor.setTextSize(size_y * 0.25f);
    }

    /**
     * Gets Y coordinate
     * @return y coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Set Y coordinate
     * @param y new y coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get X Coordinate
     * @return x coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Set X Coordinate
     * @param x new x coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Draws icon of card based on suit
     * @param canvas canvas to draw on
     * @param centerX x coord of card
     * @param centerY y coord of card
     */
    private void drawIcon(Canvas canvas, float centerX, float centerY) {
        Path wallpath = new Path();
        switch (suit) {
            case 'h':
                // Draws bottom triangle
                wallpath.moveTo(centerX + (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + size_y * 0.1f);
                wallpath.lineTo(centerX - (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - size_y * 0.05f);
                wallpath.lineTo(centerX + (size_x * 0.1f), centerY);
                canvas.drawPath(wallpath, suitColor);

                // Draws semi-circles ontop of heart
                float r;
                r = (float) Math.hypot(size_x * 0.1, size_y * 0.05) / 2;
                canvas.drawCircle(centerX - (size_x * 0.05f), centerY - (size_y * 0.025f),
                        r, suitColor);
                canvas.drawCircle(centerX + (size_x * 0.05f), centerY - (size_y * 0.025f),
                        r, suitColor);
                break;
            case 's':
                wallpath = new Path();
                // Draws square with bottom bit
                wallpath.moveTo(centerX, centerY + size_y * 0.05f);
                wallpath.lineTo(centerX + (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - size_y * 0.1f);
                wallpath.lineTo(centerX - (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + size_y * 0.05f);
                wallpath.lineTo(centerX - (size_x * 0.07f), centerY + (size_y * 0.1f));
                wallpath.lineTo(centerX + (size_x * 0.07f), centerY + (size_y * 0.1f));
                wallpath.lineTo(centerX, centerY + size_y * 0.05f);
                canvas.drawPath(wallpath, suitColor);

                // Draws semi-circles upside from heart
                float r1;
                r1 = (float) Math.hypot(size_x * 0.1, size_y * 0.05) / 2;
                canvas.drawCircle(centerX - (size_x * 0.05f), centerY + (size_y * 0.025f),
                        r1, suitColor);
                canvas.drawCircle(centerX + (size_x * 0.05f), centerY + (size_y * 0.025f),
                        r1, suitColor);
                break;
            case 'd':
                // Draws a nice and easy diamond
                wallpath = new Path();
                wallpath.moveTo(centerX, centerY + size_y * 0.1f);
                wallpath.lineTo(centerX + (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY - size_y * 0.1f);
                wallpath.lineTo(centerX - (size_x * 0.1f), centerY);
                wallpath.lineTo(centerX, centerY + size_y * 0.1f);

                canvas.drawPath(wallpath, suitColor);
                break;
            case 'c':
                // Draws a 4 direction thing then adds the circles on the outside
                wallpath = new Path();
                wallpath.moveTo(centerX, centerY);
                wallpath.lineTo(centerX - (size_x * 0.07f), centerY + (size_y * 0.08f));
                wallpath.lineTo(centerX + (size_x * 0.07f), centerY + (size_y * 0.08f));
                wallpath.lineTo(centerX, centerY);
                canvas.drawPath(wallpath, suitColor);

                // The circles
                float r3;
                r3 = size_x * 0.055f;
                canvas.drawCircle(centerX + (size_x * 0.07f), centerY, r3, suitColor);
                canvas.drawCircle(centerX - (size_x * 0.07f), centerY, r3, suitColor);
                canvas.drawCircle(centerX, centerY - size_y * 0.06f, r3, suitColor);
                canvas.drawCircle(centerX, centerY, r3, suitColor);

        }

    }

    /**
     * Returns the color of the card
     * @return RGBA in Hex
     */
    private int getColor() {
        if (suit == 's' || suit == 'c') {
            return 0xFF000000;
        } else {
            return 0xFFFF0000;
        }
    }
}
