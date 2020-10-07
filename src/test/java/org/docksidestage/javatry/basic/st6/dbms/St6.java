package org.docksidestage.javatry.basic.st6.dbms;

public abstract class St6 {
    public String buildPagingQuery(int pageSize, int pageNumber) {
        return pageSize + " | " + pageNumber;
    }
}
