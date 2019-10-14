package xyz.anythings.base;

/**
 * 물류 마스터 모듈 설정 관련 키 상수 정의
 *
 * @author shortstop
 */
public class LogisConfigConstants extends xyz.anythings.sys.ConfigConstants {	
	
	/**********************************************************************
	 * 								1. 기본 Prefix, Suffix
	 **********************************************************************/
	/**
	 * 작업 공통 프리픽스
	 */
	public static final String JOB_COMMON_PREFIX = "job.cmm.";
	/**
	 * 출고 작업 프리픽스
	 */
	public static final String JOB_DAS_PREFIX = "job.das.";
	/**
	 * 반품 작업 프리픽스
	 */
	public static final String JOB_RTN_PREFIX = "job.rtn.";
	/**
	 * B2C 작업 프리픽스
	 */
	public static final String JOB_DPS_PREFIX = "job.dps.";
		
	/**********************************************************************
	 * 								2. 인터페이스 관련 설정
	 **********************************************************************/
	/**
	 * 상위 시스템 인터페이스 유형
	 */
	public static final String IF_IF_TYPE = "if.iftype";
	/**
	 * 상위 시스템 DB Link 명
	 */
	public static final String IF_DBLINK = "if.dblink.name";
	/**
	 * 상위 시스템 데이터소스 명
	 */
	public static final String IF_DS_NAME = "if.datasource.name";
	/**
	 * 고객사 I/F 테이블 명
	 */
	public static final String IF_COMPANY_TABLE = "if.company.table";
	/**
	 * 상품 I/F 테이블 명
	 */
	public static final String IF_SKU_TABLE = "if.sku.table";
	/**
	 * 매장 I/F 테이블 명
	 */
	public static final String IF_SHOP_TABLE = "if.shop.table";
	/**
	 * 박스 유형 I/F 테이블 명
	 */
	public static final String IF_BOXTYPE_TABLE = "if.boxtype.table";
	/**
	 * 주문 I/F 테이블 명
	 */
	public static final String IF_ORDER_TABLE = "if.order.table";
	/**
	 * 피킹 실적  I/F 테이블 명
	 */
	public static final String IF_PICK_RESULT_TABLE = "if.pick-result.table";
	/**
	 * 박스 실적 I/F 테이블 명
	 */
	public static final String IF_BOX_RESULT_TABLE = "if.box-result.table";
	/**
	 * 토털 피킹 I/F 테이블 명
	 */
	public static final String IF_TOTAL_PICKING_TABLE = "if.totalpicking.table";
	/**
	 * 고객사 I/F 프로시져 명
	 */
	public static final String IF_COMPANY_PROCEDURE = "if.company.procedure";
	/**
	 * 상품 I/F 프로시져 명
	 */
	public static final String IF_SKU_PROCEDURE = "if.sku.procedure";
	/**
	 * 매장 I/F 프로시져 명
	 */
	public static final String IF_SHOP_PROCEDURE = "if.shop.procedure";
	/**
	 * 박스 유형 I/F 프로시져 명
	 */
	public static final String IF_BOXTYPE_PROCEDURE = "if.boxtype.procedure";
	/**
	 * 주문 I/F 프로시져 명
	 */
	public static final String IF_ORDER_PROCEDURE = "if.order.procedure";
	/**
	 * 피킹 실적  I/F 프로시져 명
	 */
	public static final String IF_PICK_RESULT_PROCEDURE = "if.pick-result.procedure";
	/**
	 * 박스 실적 I/F 프로시져 명
	 */
	public static final String IF_BOX_RESULT_PROCEDURE = "if.box-result.procedure";
	/**
	 * 토털 피킹 I/F 프로시져 명
	 */
	public static final String IF_TOTAL_PICKING_PROCEDURE = "if.totalpicking.procedure";
	/**
	 * 주문 수신유형 (datasource/procedure)
	 */
	public static final String IF_RECEIVE_ORDER_TYPE = "if.receive.order.type";
	
	/**********************************************************************
	 * 								3. SKU 조회 관련 설정 
	 **********************************************************************/
	/**
	 * 바코드 최대 길이 - 상품 스캔시 최대 입력 길이
	 */
	public static final String SKU_BARCODE_MAX_LENGTH = "sku.barcode.max.length";
	/**
	 * SKU 조회를 위한 코드 필드명 리스트
	 */
	public static final String SKU_CONDITION_FIELDS_TO_SEARCH = "sku.search.condition.fields";
	/**
	 * SKU 조회를 위한 조회 필드명 리스트
	 */
	public static final String SKU_SELECT_FIELDS_TO_SEARCH = "sku.search.select.fields";
	/**
	 * 서버 사이드에서 상품 유효성 체크 여부
	 */
	public static final String VALIDATION_SKUCD_ENABLED = "sku.skucd.validation.enabled";
	/**
	 * SKU 중량 단위 - g/kg
	 */
	public static final String SKU_WEIGHT_UNIT = "sku.weight.unit";
	
	/**********************************************************************
	 * 								4. 각종 스캔 코드 유효성 체크 
	 **********************************************************************/
	/**
	 * 서버 사이드에서 상품 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_SKUCD = "server.validate.sku_cd.rule";	
	/**
	 * 서버 사이드에서 박스 ID 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_BOXID = "server.validate.box_id.rule";
	/**
	 * 서버 사이드에서 로케이션 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_CELLCD = "server.validate.cell_cd.rule";
	/**
	 * 서버 사이드에서 표시기 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_INDCD = "server.validate.ind_cd.rule";
	/**
	 * 서버 사이드에서 랙 코드 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_RACKCD = "server.validate.rack_cd.rule";
	/**
	 * 서버 사이드에서 송장번호 유효성 체크를 위한 룰
	 */
	public static final String VALIDATION_RULE_INVNO = "server.validate.invoice_no.rule";
	
	/**********************************************************************
	 * 								5. 디바이스 설정 
	 **********************************************************************/
	/**
	 * 작업 장비에서 장비 리스트
	 */
	public static final String DEVICE_DEVICE_LIST = "device.list";
	/**
	 * 작업 장비에서 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
	 */
	public static final String DEVICE_SIDE_ENABLED = "device.side.enabled";
	/**
	 * 작업 장비에서 작업 스테이션 정보를 사용할 지 여부
	 */
	public static final String DEVICE_STATION_ENABLED = "device.station.enabled";
	/**
	 * 작업지시 시점에 표시기에 할당 셀 표시 활성화 여부
	 */
	public static final String ASSIGNED_CELL_INDICATOR_ENABLED = "assigned-cell.indicator.enabled";
	
		
	/**********************************************************************
	 * 								99. Functions 
	 **********************************************************************/

	/**
	 * 작업 공통 Configuration Key를 리턴 
	 * 
	 * @param configKey
	 * @return
	 */
	public static final String getJobCommonConfigKey(String configKey) {
		return JOB_COMMON_PREFIX + configKey;
	}
	
	/**
	 * 출고 작업 Configuration Key를 리턴
	 * 
	 * @param configKey
	 * @return
	 */
	public static final String getJobDasConfigKey(String configKey) {
		return JOB_DAS_PREFIX + configKey;
	}
	
	/**
	 * 반품 작업 Configuration Key를 리턴
	 * 
	 * @param configKey
	 * @return
	 */
	public static final String getJobRtnConfigKey(String configKey) {
		return JOB_RTN_PREFIX + configKey;
	}
	
	/**
	 * B2C 작업 Configuration Key를 리턴
	 * 
	 * @param configKey
	 * @return
	 */
	public static final String getJobDpsConfigKey(String configKey) {
		return JOB_DPS_PREFIX + configKey;
	}

}
