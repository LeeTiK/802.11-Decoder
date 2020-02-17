/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Bertrand Martel
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package leetik.w80211.protocol.radiotap;

import java.nio.ByteBuffer;
import java.util.Arrays;

import leetik.w80211.protocol.radiotap.inter.IRadiotapFlags;
import leetik.w80211.utils.ByteUtils;
import leetik.w80211.protocol.radiotap.inter.IRadiotapChannel;
import leetik.w80211.protocol.radiotap.inter.IRadiotapData;

/**
 * Object containing all implementations of various prperties that may be
 * decoded from radio tap header
 * 
 * @author Bertrand Martel
 * 
 */
public class RadioTapData implements IRadiotapData {

	/**
	 * Value in microseconds of the MAC's 64-bit 802.11 Time Synchronization
	 * Function timer when the first bit of the MPDU arrived at the MAC. For
	 * received frames only.
	 */
	private long TFST = -1l;

	/** Properties of transmitted and received frames. */
	private RadioTapFlags flags = null;

	/** TX/RX data rate in Mbps */
	private int dataRate = 0;

	/** The hop set and pattern for frequency-hopping radios. */
	private byte FHSS = 0x00;

	/**
	 * RF signal power at the antenna. This field contains a single signed 8-bit
	 * value, which indicates the RF signal power at the antenna, in decibels
	 * difference from 1mW.
	 */
	private byte dbmAntSignal = 0x00;

	/**
	 * RF noise power at the antenna. This field contains a single signed 8-bit
	 * value, which indicates the RF signal power at the antenna, in decibels
	 * difference from 1mW.
	 */
	private byte dbmAntNoise = 0x00;

	/**
	 * Quality of Barker code lock. Unitless. Monotonically nondecreasing with
	 * "better" lock strength. Called "Signal Quality" in datasheets
	 */
	private int lockQuality = 0;

	/**
	 * Transmit power expressed as decibel distance from max power set at
	 * factory calibration. 0 is max power. Monotonically nondecreasing with
	 * lower power levels.
	 */
	private int txAttenuation = 0;

	/**
	 * Transmit power expressed as decibel distance from max power set at
	 * factory calibration. 0 is max power. Monotonically nondecreasing with
	 * lower power levels.
	 */
	private int dbTxAttenuation = 0;

	/**
	 * Transmit power expressed as dBm (decibels from a 1 milliwatt reference).
	 * This is the absolute power level measured at the antenna port.
	 */
	private byte dbmTxPower = 0;

	/**
	 * Unitless indication of the Rx/Tx antenna for this packet. The first
	 * antenna is antenna 0.
	 */
	private byte antenna = 0x00;

	/**
	 * RF signal power at the antenna, decibel difference from an arbitrary,
	 * fixed reference. This field contains a single unsigned 8-bit value.
	 */
	private byte dbAntennaSignal = 0x00;

	/**
	 * RF noise power at the antenna, decibel difference from an arbitrary,
	 * fixed reference. This field contains a single unsigned 8-bit value.
	 */
	private byte dbAntennaNoise = 0x00;

	/** Properties of received frames. */
	private short rxFlags = 0x0000;

	/**
	 * Modulation coding scheme
	 */
	private RadioTapMCS mcs = null;

	/** Tx/Rx frequency in MHz */
	private leetik.w80211.protocol.radiotap.inter.IRadiotapChannel channel = null;

	/**
	 * Define if a PLCP (Physical Layer Convergence Protocol) CRC error was detected on this frame
	 * @return
	 */
	private boolean isPlcpCrcErrors= false;
	
	/**
	 * Very High Throughput
	 */
	private RadioTapVHT vht = null;

	/** data payload extracted from radio tap header */
	private byte[] payload = new byte[] {};

	/**
	 * define flag field
	 */
	private boolean flagsFieldDefined = false;

	/**
	 * Define current position in payload data
	 */
	private int currentIndex = 0;

	public RadioTapData() {
	}

	public RadioTapData(byte[] payload) {
		this.payload = payload;
	}

	/**
	 * TFST is the first 8 bytes
	 */
	public void setTFST() {
		this.TFST = ByteUtils.convertByteArrayToLong(ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+4)));
		currentIndex = currentIndex + 4;
	}

	public void setTFST(long tfst) {
		this.TFST = tfst;
	}

	public void setFlags() {
		this.flags = new RadioTapFlags(payload[currentIndex]);
		// this is strange but a bit is added in Radiotap when flags is added
		// without data rate following
		currentIndex = currentIndex + 2;
		flagsFieldDefined = true;
	}

	public void setFlags(byte flags) {
		this.flags = new RadioTapFlags(flags);
		flagsFieldDefined = true;
	}

	public void setDataRate() {
		if (flagsFieldDefined) {
			currentIndex = currentIndex - 1;
		}
		this.dataRate = (payload[currentIndex] & 0xFF) * 500;
		currentIndex++;
	}

	public void setDataRate(byte dataRate) {
		this.dataRate = (dataRate & 0xFF) * 500;
	}

	public void setChannel() {
		byte[] frequency = ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+2));
		byte[] bitmask = ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex+2, currentIndex+4));
		this.channel = new RadioTapChannel(frequency,bitmask);
		
		currentIndex = currentIndex + 4;
	}

	public void setChannel(short frequency, short bitmask) {
		this.channel = new RadioTapChannel(frequency,bitmask);

		//currentIndex = currentIndex + 4;
	}

	public void setFHSS() {
		this.FHSS = payload[currentIndex];
		// pattern can be extracted as an additionnal byte
		currentIndex = currentIndex + 2;
	}

	public void setFHSS(byte FHSS) {
		this.FHSS = FHSS;
	}

	public void setDBMAntSignal() {
		dbmAntSignal = payload[currentIndex];
		currentIndex++;
	}

	public void setDBMAntSignal(byte dbmAntSignal) {
		this.dbmAntSignal = dbmAntSignal;
	}

	public void setDBMAntNoise() {
		dbmAntNoise = payload[currentIndex];
		currentIndex++;
	}

	public void setDBMAntNoise(byte dbmAntNoise) {
		this.dbmAntNoise = dbmAntNoise;
	}

	public void setLockQuality() {
		lockQuality = ByteUtils.convertByteArrayToInt(ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+2)));
		currentIndex = currentIndex + 2;
	}

	public void setLockQuality(short lockQuality) {
		this.lockQuality = lockQuality;
	}

	public void setTxAttenuation() {
		txAttenuation = ByteUtils.convertByteArrayToInt(ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+2)));
		currentIndex = currentIndex + 2;
	}

	public void setTxAttenuation(short txAttenuation) {
		this.txAttenuation = txAttenuation;
	}

	public void setDbTxAttenuation() {
		dbTxAttenuation = ByteUtils.convertByteArrayToInt(ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+2)));
		currentIndex = currentIndex + 2;
	}

	public void setDbTxAttenuation(short dbTxAttenuation) {
		this.dbTxAttenuation = dbTxAttenuation;
	}

	public void setDbmTxPower() {
		dbmTxPower = payload[currentIndex];
		currentIndex++;
	}

	public void setDbmTxPower(byte dbmTxPower) {
		this.dbmTxPower = dbmTxPower;
	}

	public void setAntenna() {
		antenna = payload[currentIndex];
		currentIndex++;
	}

	public void setAntenna(byte antenna) {
		this.antenna = antenna;
	}

	public void setDbAntennaSignal() {
		dbAntennaSignal = payload[currentIndex];
		currentIndex++;
	}

	public void setDbAntennaSignal(byte dbAntennaSignal) {
		this.dbAntennaSignal = dbAntennaSignal;
	}

	public void setDbAntennaNoise() {
		dbAntennaNoise = payload[currentIndex];
		currentIndex++;
	}

	public void setDbAntennaNoise(byte dbAntennaNoise) {
		this.dbAntennaNoise = dbAntennaNoise;
	}

	public void setRxFlags() {
		rxFlags = (short) ByteUtils.convertByteArrayToInt(ByteUtils.convertLeToBe(Arrays.copyOfRange(payload, currentIndex, currentIndex+2)));
		if (rxFlags==0x02)
		{
			isPlcpCrcErrors=true;
		}
		currentIndex = currentIndex + 2;
	}

	public void setRxFlags(short rxFlags) {
		this.rxFlags = rxFlags;
		if (rxFlags==0x02)
		{
			isPlcpCrcErrors=true;
		}
	}

	public void setMcs() {
		mcs = new RadioTapMCS(payload[currentIndex + 2], payload[currentIndex + 1],payload[currentIndex]);
		currentIndex = currentIndex + 3;
	}

	public void setMcs(byte knownField, byte flagsField, byte mcsField ) {
		mcs = new RadioTapMCS(knownField, flagsField,mcsField);
		//currentIndex = currentIndex + 3;
	}

	public void setAmpduStatus() {
		System.out.println("AMPDU status received... Not treating...");
		currentIndex = currentIndex + 8;
	}

	public void setAmpduStatus(long ampdu) {
		System.out.println("AMPDU status received... Not treating...");
	}

	public void setVHT() {
		vht = new RadioTapVHT(new byte[] { payload[currentIndex + 1],
				payload[currentIndex] }, payload[currentIndex + 2],
				payload[currentIndex + 3], payload[currentIndex + 4],
				payload[currentIndex + 5], payload[currentIndex + 6],
				new byte[] { payload[currentIndex + 8],
						payload[currentIndex + 7] });
		currentIndex = currentIndex + 9;
	}

	public void setVHT(byte[] known, byte flags, byte bandwith, byte mcs_nss,
					   byte coding, byte groupId, byte[] partialAid) {
		vht = new RadioTapVHT(known,flags,bandwith,mcs_nss,coding,groupId,partialAid);
		currentIndex = currentIndex + 9;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	@Override
	public byte[] getPayload() {
		return payload;
	}
	@Override
	public Long getTFST() {
		return TFST;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	@Override
	public IRadiotapFlags getFlags() {
		return flags;
	}
	@Override
	public int getDataRate() {
		return dataRate;
	}
	@Override
	public IRadiotapChannel getChannel() {
		return channel;
	}
	@Override
	public byte getFHSS() {
		return FHSS;
	}
	@Override
	public byte getDbmAntSignal() {
		return dbmAntSignal;
	}
	@Override
	public byte getDbmAntNoise() {
		return dbmAntNoise;
	}
	@Override
	public int getLockQuality() {
		return lockQuality;
	}
	@Override
	public int getDbTxAttenuation() {
		return dbTxAttenuation;
	}

	public void setDbTxAttenuation(int dbTxAttenuation) {
		this.dbTxAttenuation = dbTxAttenuation;
	}
	@Override
	public byte getDbmTxPower() {
		return dbmTxPower;
	}
	@Override
	public byte getAntenna() {
		return antenna;
	}
	@Override
	public byte getDbAntennaSignal() {
		return dbAntennaSignal;
	}
	@Override
	public byte getDbAntennaNoise() {
		return dbAntennaNoise;
	}

	@Override
	public RadioTapMCS getMcs() {
		return mcs;
	}
	@Override
	public RadioTapVHT getVht() {
		return vht;
	}

	public void setFlagsFieldDefined(boolean flagsFieldDefined) {
		this.flagsFieldDefined = flagsFieldDefined;
	}

	@Override
	public int getTxAttenuation() {
		return txAttenuation;
	}

	@Override
	public boolean isPlcpCrcErrors() {
		return isPlcpCrcErrors;
	}

	public boolean isFlagsFieldDefined() {
		return flagsFieldDefined;
	}
}
