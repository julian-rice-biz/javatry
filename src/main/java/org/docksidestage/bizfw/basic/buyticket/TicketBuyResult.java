package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBuyResult {
    private int change;
    private Ticket ticket;
    public TicketBuyResult(int c, Ticket t) {
        change = c;
        ticket = t;
    }

    public Ticket getTicket() { return ticket; }
    public int getChange() { return change; }
}
