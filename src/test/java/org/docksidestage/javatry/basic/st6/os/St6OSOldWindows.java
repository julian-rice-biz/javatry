package org.docksidestage.javatry.basic.st6.os;

public class St6OSOldWindows extends St6OperationSystem {
    private static final String OS_TYPE_OLD_WINDOWS = "OldWindows";

    public St6OSOldWindows(String loginId) {
        super(OS_TYPE_OLD_WINDOWS, loginId);
    }

    @Override
    protected String getFileSeparator() {
        if (OS_TYPE_OLD_WINDOWS.equalsIgnoreCase(osType)) {
            return "\\";
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }

    @Override
    protected String getUserDirectory() {
        if (OS_TYPE_OLD_WINDOWS.equalsIgnoreCase(osType)) {
            return "/Documents and Settings/" + loginId;
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }
}
