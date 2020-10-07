package org.docksidestage.bizfw.basic.buyticket;

public class PluralDays implements Ticket {
    int displayPrice = 0;
    int dayCount;
    boolean alreadyIn = false;
    public PluralDays(int days) {
        this.displayPrice = TicketBooth.GetPrice(days);
        this.dayCount = days;
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
    public int getDayCount() {
        return dayCount;
    }
    public boolean isAlreadyIn() {
        return alreadyIn;
    }
}
