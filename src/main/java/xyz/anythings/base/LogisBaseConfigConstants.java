package xyz.anythings.base;

/**
 * 물류 마스터 모듈 설정 관련 키 상수 정의
 *
 * @author shortstop
 */
public class LogisBaseConfigConstants extends xyz.anythings.sys.ConfigConstants {

	/**
	 * MPS 주문 수신 유형 - service / procedure 
	 */
	public static final String MPS_RECEIVE_ORDER_TYPE = "mps.receive.order.type";
	/**
	 * Mobile 장비의 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
	 */
	public static final String MPS_MOBILE_SIDE_CD_ENABLED = "mps.mobile.device.side-cd.enabled";
	/**
	 * Mobile 장비의 작업 존 정보를 사용할 지 여부
	 */
	public static final String MPS_MOBILE_ZONE_CD_ENABLED = "mps.mobile.device.zone-cd.enabled";	
	
}
