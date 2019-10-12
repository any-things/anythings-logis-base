package xyz.anythings.base;

/**
 * 물류 마스터 모듈 설정 관련 키 상수 정의
 *
 * @author shortstop
 */
public class LogisConfigConstants extends xyz.anythings.sys.ConfigConstants {	
	
	/**********************************************************************
	 * 								1. SKU 조회 관련 설정 
	 **********************************************************************/
	/**
	 * 바코드 최대 길이 - 상품 스캔시 최대 입력 길이
	 */
	public static final String SKU_BARCODE_MAX_LENGTH = "logis.job.common.sku.barcode.max.length";
	/**
	 * SKU 조회를 위한 코드 필드명 리스트
	 */
	public static final String SKU_CONDITION_FIELDS_TO_SEARCH = "logis.job.common.sku.search.condition.fields";
	/**
	 * SKU 조회를 위한 조회 필드명 리스트
	 */
	public static final String SKU_SELECT_FIELDS_TO_SEARCH = "logis.job.common.sku.search.select.fields";
	/**
	 * SKU 중량 단위 - g/kg
	 */
	public static final String SKU_WEIGHT_UNIT = "logis.job.common.sku.weight.unit";
	
	/**********************************************************************
	 * 								2. 각종 스캔 코드 유효성 체크 
	 **********************************************************************/
	/**
	 * 서버 사이드에서 상품 유효성 체크 여부
	 */
	public static final String VALIDATION_SKUCD_ENABLED = "logis.job.common.sku.skucd.validation.enabled";
	/**
	 * 서버 사이드에서 상품 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_SKUCD = "logis.job.common.server.validate.sku_cd.rule";	
	/**
	 * 서버 사이드에서 박스 ID 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_BOXID = "logis.job.common.server.validate.box_id.rule";
	/**
	 * 서버 사이드에서 로케이션 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_CELLCD = "logis.job.common.server.validate.cell_cd.rule";
	/**
	 * 서버 사이드에서 표시기 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_INDCD = "logis.job.common.server.validate.ind_cd.rule";
	/**
	 * 서버 사이드에서 랙 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_RACKCD = "logis.job.common.server.validate.rack_cd.rule";
	/**
	 * 서버 사이드에서 송장번호 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_INVNO = "logis.job.common.server.validate.invoice_no.rule";
	
	/**********************************************************************
	 * 								3. 장비 관련 설정 
	 **********************************************************************/
	
	/**
	 * 작업 장비에서 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
	 */
	public static final String DEVICE_SIDE_ENABLED = "logis.job.common.device.side.enabled";
	/**
	 * 작업 장비에서 작업 스테이션 정보를 사용할 지 여부
	 */
	public static final String DEVICE_STATION_ENABLED = "logis.job.common.device.station.enabled";
	/**
	 * 박스 처리 순서 생성 조건 (즉 박스 ID가 Unique할 조건)
	 *  > T : Globally Unique
	 *  > D : 날짜별
	 *  > B : 작업 배치별
	 *  > G : 배치 그룹별
	 */
	public static final String BOX_SEQ_CREATION_GROUP = "logis.job.common.box.box_seq.creation.group";
	/**
	 * 박스 투입시 투입 범위
	 *  > station : 작업 존 별로 투입
	 *  > rack : 호기 전체에 투입
	 */
	public static final String ASSORT_INPUT_SCOPE = "logis.job.common.input.scope";

}
