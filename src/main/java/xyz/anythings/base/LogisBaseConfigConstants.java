package xyz.anythings.base;

/**
 * 물류 마스터 모듈 설정 관련 키 상수 정의
 *
 * @author shortstop
 */
public class LogisBaseConfigConstants extends xyz.anythings.sys.ConfigConstants {

	/**
	 * Mobile 장비의 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
	 */
	public static final String DEVICE_SIDE_CD_ENABLED = "logis.device.side-cd.enabled";
	/**
	 * Mobile 장비의 작업 존 정보를 사용할 지 여부
	 */
	public static final String DEVICE_ZONE_CD_ENABLED = "logis.device.station.enabled";	
	
	/**
	 * 유효성 에러를 로깅할 지 여부
	 */
	public static final String VALIDATION_ERROR_LOGGING_ENABLED = "logis.log.validation.error.enabled";
	/**
	 * MPS 바코드 최대 길이 - 상품 스캔시 최대 입력 길이
	 */
	public static final String SKU_BARCODE_MAX_LENGTH = "logis.job.common.sku.barcode.max.length";
	
	/**
	 * MPS SKU 조회를 위한 코드 필드명 리스트
	 */
	public static final String SKU_FIELDS_TO_FIND = "mps.sku.search.code.fields";
}
