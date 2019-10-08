package xyz.anythings.base.event;

/**
 * 작업 관련 Root 이벤트 
 * @author yang
 *
 */
public class EventConstants {
	/** 인터페이스 타입 ================      **/
	/**
	 * 프로시저 호출 
	 *  : dblink 를 사용하는 경우도 프로시저 작성 
	 */
	public static final short IF_TYPE_PROC = 1;
	
	/**
	 * 데이터 소스 
	 *  TODO : column 매치 ? 
	 */
	public static final short IF_TYPE_DSRC = 2;
	
	
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
	
	
	
}
