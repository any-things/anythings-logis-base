package xyz.anythings.base.entity.ifc;


/**
 * Tray or Box Interface
 * @author yang
 *
 */
public interface IBucket{
	
	/**
	 * bucket Type : tray / box
	 */
	public String getBucketType();
	
	
	/**
	 * PK ID 정보 
	 * @return
	 */
	public String getId();
	
	/**
	 * TRAY_CD or BOX_ID
	 * @return
	 */
	public String getBucketCd();
	
	/**
	 * TRAY_TYPE or BOX_TYPE_CD
	 * @return
	 */
	public String getBucketTypeCd();
	
	/**
	 * TRAY_COLOR or BOX_COLOR
	 * @return
	 */
	public String getBucketColor();
	
	/**
	 * 
	 * @return
	 */
	public String getStatus();
	
	
}