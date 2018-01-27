package com.sigpit.alexwurts.solitare;

import model.Card;

/**
 * Created by Sigpit on 1/26/2018.
 */

public class AcePile extends Pile {

    public AcePile(int id) {
        super(id);
    }

    public boolean isValidNextCard(Card c) {
        Card last = getLast();
        return (last == null) || (c.suit == last.suit && c.num - 1 == last.num);
    }


}
