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
	/**
	 * 이벤트 단독
	 */
	public static final short EVENT_STEP_ALONE = 3;
	
	
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
	
	
	/** BatchInstructEvent ================       **/
	
	/**
	 * 배치 작업 지시 
	 */
	public static final short EVENT_INSTRUCT_TYPE_INSTRUCT = 10;
	
	/**
	 * 배치 작업 지시 취소
	 */
	public static final short EVENT_INSTRUCT_TYPE_INSTRUCT_CANCEL = 20;
	
	/**
	 * 배치 작업 병합 
	 */
	public static final short EVENT_INSTRUCT_TYPE_MERGE = 30;
	
	/**
	 * 배치 대상 분류 
	 */
	public static final short EVENT_INSTRUCT_TYPE_CLASSIFICATION = 40;
	
	/**
	 * 배치 작업 지시 후 박스 요청  
	 */
	public static final short EVENT_INSTRUCT_TYPE_BOX_REQ = 50;
	
	/**
	 * 토털 피킹  
	 */
	public static final short EVENT_INSTRUCT_TYPE_TOTAL_PICKING = 60;
	
	/**
	 * 추천 로케이션 
	 */
	public static final short EVENT_INSTRUCT_TYPE_RECOMMEND_CELLS = 70;	
	
	
}
