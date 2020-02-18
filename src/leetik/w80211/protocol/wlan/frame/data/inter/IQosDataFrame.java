package leetik.w80211.protocol.wlan.frame.data.inter;

/**
 * QOS data frame decoder
 * 
 * @author Bertrand Martel
 * 
 */
public interface IQosDataFrame {

	public byte[] getQosControl() ;

	public byte[] getData();
}
