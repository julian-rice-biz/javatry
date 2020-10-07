package org.docksidestage.javatry.basic.st6.os;

public class St6OSMac extends St6OperationSystem {
    private static final String OS_TYPE_MAC = "Mac";

    public St6OSMac(String loginId) {
        super(OS_TYPE_MAC, loginId);
    }

    @Override
    protected String getFileSeparator() {
        if (OS_TYPE_MAC.equalsIgnoreCase(osType)) {
            return "/";
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }

    @Override
    protected String getUserDirectory() {
        if (OS_TYPE_MAC.equalsIgnoreCase(osType)) {
            return "/Users/" + loginId;
        } else {
            throw new IllegalStateException("Unknown osType: " + osType);
        }
    }
}
