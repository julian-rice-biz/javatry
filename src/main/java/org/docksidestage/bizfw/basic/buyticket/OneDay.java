package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */

public class OneDay implements Ticket {
    int displayPrice = 0;
    boolean alreadyIn = false;
    public OneDay(int displayPrice) {
        this.displayPrice = displayPrice;
    }

    public void doInPark() {
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        alreadyIn = true;
    }

    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() {
        return alreadyIn;
    }
}

