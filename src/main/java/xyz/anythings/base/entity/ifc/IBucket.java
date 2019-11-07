package xyz.anythings.base.entity.ifc;


/**
 * Tray or Box Interface
 * @author yang
 *
 */
public interface IBucket{
	
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
	public String getBucketType();
	
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