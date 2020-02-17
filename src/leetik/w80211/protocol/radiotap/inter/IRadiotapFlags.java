package leetik.w80211.protocol.radiotap.inter;

/**
 * Template for detecting presence or not of data type in radiotap header
 * 
 * @author Bertrand Martel
 *
 */
public interface IRadiotapFlags {

	public boolean isCFP();
	
	public boolean isPreamble();
	
	public boolean isWEP();
	
	public boolean isFragmentation();
	
	public boolean isFCSatEnd();
	
	public boolean isDataPad();
	
	public boolean isBadFCS();
	
	public boolean isShortGI();
}
