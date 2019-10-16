package xyz.anythings.base.event;

/**
 * 작업 관련 Root 이벤트 
 * @author yang
 *
 */
public class EventConstants {
	
	
	/** BatchRootEvent ================       **/
	/**
	 * 이벤트 전 처리 
	 */
	public static final short EVENT_STEP_BEFORE = 1;
	/**
	 * 이벤트 후 처리 
	 */
	public static final short EVENT_STEP_AFTER = 2;
	
	
	
	
	/** BatchReceiveEvent ================       **/
	
	/**
	 * 배치 수신 서머리 정보 수집 타입
	 */
	public static final short EVENT_RECEIVE_TYPE_RECEIPT = 10;
	
	/**
	 * 배치 수신 타입
	 */
	public static final short EVENT_RECEIVE_TYPE_RECEIVE = 20;
	
	/**
	 * 배치 수신 취소 타입 
	 */
	public static final short EVENT_RECEIVE_TYPE_CANCEL = 30;
	
}
