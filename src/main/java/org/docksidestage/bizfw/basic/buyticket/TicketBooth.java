/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

import java.util.HashMap;
import java.util.Enumeration;

public class TicketBooth {
    //Better refactoring for one day: store this information in a file and get via IO or in a database
    private static HashMap<Integer, Integer> DayToPrice = new HashMap<Integer, Integer>() {{
        put(1, 7400); put(2, 13200); put(4, 22400);
    }};
    private static HashMap<Integer, Integer> DayToQuantity = new HashMap<Integer, Integer>() {{
        put(1, 10); put(2, 10); put(4, 5);
    }};
    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private int twoDayQuantity = MAX_QUANTITY;
    private boolean debug = true;
    private Integer salesProceeds;
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public static int GetPrice(int day) {
        if (DayToPrice.containsKey(day)) {
            return DayToPrice.get(day);
        }
        System.out.println("Error! Did not find " + day + " in our price dictionary! Defaulting to One Day");
        return 1;
    }

    public Ticket buyOneDayPassport(int handedMoney) {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ONE_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        --quantity;
        if (salesProceeds != null) {
            salesProceeds += ONE_DAY_PRICE;
        } else {
            salesProceeds = ONE_DAY_PRICE;
        }
        return new OneDay();
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        if (twoDayQuantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < TWO_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        twoDayQuantity -= 1;
        if (salesProceeds != null) {
            salesProceeds += TWO_DAY_PRICE;
        } else {
            salesProceeds = TWO_DAY_PRICE;
        }

        int change = Math.max(handedMoney - TWO_DAY_PRICE, 0);
        return new TicketBuyResult(change, new PluralDays(2));
    }

    //Julian's Version -- Buy a passport (1,2,or 4) and get change/ticket in one function
    public TicketBuyResult buyPassport(int days, int payment) {
        //Edge Cases
        if (!DayToPrice.containsKey(days) || !DayToQuantity.containsKey(days)) {
            throw new TicketNotSold("Ticket not available for sale!");
        }

        int cost = DayToPrice.get(days);
        int currentQuantity = DayToQuantity.get(days);

        if (payment < cost) {
            throw new TicketShortMoneyException("Not enough money to buy a " + days + " day passport!");
        }
        if (currentQuantity <= 0) {
            throw new TicketSoldOutException("We're sold out of " + days + " day passports!");
        }

        //Passed Cases. Now reduce the quantity by one because we're buying a passport
        DayToQuantity.put(days, DayToQuantity.get(days)-1);

        if (salesProceeds != null) {
            salesProceeds += cost;
        } else {
            salesProceeds = cost;
        }

        int change = payment - cost;

        //Friendly comment in the case that there is change
        if (change != 0 && debug) {
            System.out.println("Hey! Here's your change: " + change + "円");
        }
        return new TicketBuyResult(change, new PluralDays(days));
    }

    public static class TicketSoldOutException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    //Refactored Version
    public static class TicketNotSold extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public TicketNotSold(String msg) { super(msg); }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public int getTwoDayQuantity() {
        return twoDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
