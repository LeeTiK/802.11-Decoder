package leetik.w80211.protocol.wlan.frame.management.element.impl.vendorSpecific;

public enum EVendorSpecificElementType {

    RSN_PMKID("RSN PMKID"),
    NOT_DECODER ("UNKNOWN");

    String name;

    EVendorSpecificElementType(String name)
    {
        this.name = name;
    }
}
