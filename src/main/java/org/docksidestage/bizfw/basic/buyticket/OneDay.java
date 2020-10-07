package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */

public class OneDay implements Ticket {
    int displayPrice = 0;
    int dayCount = 0;
    boolean alreadyIn = false;
    public OneDay() {
        this.displayPrice = TicketBooth.GetPrice(1);
        this.dayCount = 1;
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
    public int getDayCount() { return dayCount; }
    public boolean isAlreadyIn() {
        return alreadyIn;
    }
}

