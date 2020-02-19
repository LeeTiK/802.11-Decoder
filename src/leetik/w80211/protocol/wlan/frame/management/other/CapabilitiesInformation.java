package leetik.w80211.protocol.wlan.frame.management.other;

import leetik.w80211.protocol.radiotap.inter.IRadiotapFlags;

public class CapabilitiesInformation{

    private short capabilitiesinfo =0x00;

    private boolean essCapabilities = false;
    private boolean ibssStatus= false;
    private boolean cf= false;
    private boolean cfPoll = false;
    private boolean privacy= false;
    private boolean shortPreamble= false;
    private boolean pbcc= false;
    private boolean channelAgility= false;
    private boolean spectrumManagement= false;
    private boolean qos = false;
    private boolean shortSlotTime= false;
    private boolean automaticPowerSaveDelivery= false;
    private boolean radioMeasurement= false;
    private boolean dsss_ofdm= false;
    private boolean delayedBlockAck= false;
    private boolean immediateBlockAck= false;

    public CapabilitiesInformation(short capabilitiesinfo)
    {
        this.capabilitiesinfo = capabilitiesinfo;

        if ((capabilitiesinfo & 0x0001) != 0) {
            essCapabilities = true;
        }
        if ((capabilitiesinfo & 0x0002) != 0) {
            ibssStatus= true;
        }
        if ((capabilitiesinfo & 0x0004) != 0) {
            cf = true;
        }
        if ((capabilitiesinfo & 0x0008) != 0) {
            cfPoll = true;
        }
        if ((capabilitiesinfo & 0x0010) != 0) {
            privacy = true;
        }

        if ((capabilitiesinfo & 0x0020) != 0) {
            shortPreamble = true;
        }

        if ((capabilitiesinfo & 0x0040) != 0) {
            pbcc = true;
        }

        if ((capabilitiesinfo & 0x0080) != 0) {
            channelAgility = true;
        }

        if ((capabilitiesinfo & 0x0100) != 0) {
            spectrumManagement = true;
        }

        if ((capabilitiesinfo & 0x0200) != 0) {
            qos = true;
        }

        if ((capabilitiesinfo & 0x0400) != 0) {
            shortSlotTime = true;
        }

        if ((capabilitiesinfo & 0x0800) != 0) {
            automaticPowerSaveDelivery = true;
        }

        if ((capabilitiesinfo & 0x1000) != 0) {
            radioMeasurement = true;
        }

        if ((capabilitiesinfo & 0x2000) != 0) {
            dsss_ofdm = true;
        }

        if ((capabilitiesinfo & 0x4000) != 0) {
            delayedBlockAck = true;
        }

        if ((capabilitiesinfo & 0x8000) != 0) {
            immediateBlockAck = true;
        }
    }

    public boolean isAutomaticPowerSaveDelivery() {
        return automaticPowerSaveDelivery;
    }

    public boolean isCf() {
        return cf;
    }

    public boolean isCfPoll() {
        return cfPoll;
    }

    public boolean isChannelAgility() {
        return channelAgility;
    }

    public boolean isDelayedBlockAck() {
        return delayedBlockAck;
    }

    public boolean isDsss_ofdm() {
        return dsss_ofdm;
    }

    public boolean isEssCapabilities() {
        return essCapabilities;
    }

    public boolean isIbssStatus() {
        return ibssStatus;
    }

    public boolean isPbcc() {
        return pbcc;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public boolean isImmediateBlockAck() {
        return immediateBlockAck;
    }

    public boolean isQos() {
        return qos;
    }

    public boolean isRadioMeasurement() {
        return radioMeasurement;
    }

    public boolean isShortPreamble() {
        return shortPreamble;
    }

    public boolean isShortSlotTime() {
        return shortSlotTime;
    }

    public boolean isSpectrumManagement() {
        return spectrumManagement;
    }

    public void setIbssStatus(boolean ibssStatus) {
        this.ibssStatus = ibssStatus;
    }

    public short getCapabilitiesinfo() {
        return capabilitiesinfo;
    }
}

