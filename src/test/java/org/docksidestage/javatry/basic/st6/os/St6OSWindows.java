package org.docksidestage.javatry.basic.st6.os;

public class St6OSWindows extends St6OperationSystem {
    private static final String OS_TYPE_WINDOWS = "Windows";

    public St6OSWindows(String loginId) {
        super(OS_TYPE_WINDOWS, loginId);
    }

    @Override
    protected String getFileSeparator() {
        if (OS_TYPE_WINDOWS.equalsIgnoreCase(osType)) {
            return "\\";
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }

    @Override
    protected String getUserDirectory() {
        if (OS_TYPE_WINDOWS.equalsIgnoreCase(osType)) {
            return "/Users/" + loginId;
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }
}
