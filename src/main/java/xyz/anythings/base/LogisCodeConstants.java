package xyz.anythings.base;

/**
 * 각종 코드 관련 상수 정의
 * 
 * @author shortstop
 */
public class LogisCodeConstants {

	/**
	 * 무게 단위 : g
	 */
	public static final String WEIGHT_UNIT_G = "g";
	/**
	 * 무게 단위 : kg
	 */
	public static final String WEIGHT_UNIT_KG = "kg";
	
	/**
	 * 분류 투입 유형 - 상품 낱개 (IClassifyExeEvent에서 사용)
	 */
	public static final String CLASSIFICATION_INPUT_TYPE_SKU = "sku";
	/**
	 * 분류 투입 유형 - 상품 완박스
	 */
	public static final String CLASSIFICATION_INPUT_TYPE_SKU_BOX = "sku_box";
	/**
	 * 분류 투입 유형 - 상품 묶음
	 */
	public static final String CLASSIFICATION_INPUT_TYPE_SKU_BUNDLE = "sku_bundle";
	/**
	 * 분류 투입 유형 - 박스
	 */
	public static final String CLASSIFICATION_INPUT_TYPE_BOX = "box";
	/**
	 * 분류 투입 유형 - Tray
	 */
	public static final String CLASSIFICATION_INPUT_TYPE_TRAY = "tray";
	
	/**
	 * 분류 장비 유형 - 표시기를 통한 분류 처리 (IClassifyExeEvent에서 사용)
	 */
	public static final String CLASSIFICATION_DEVICE_INDICATOR = "indicator";
	/**
	 * 분류 장비 유형 - KIOSK를 통한 분류 처리
	 */
	public static final String CLASSIFICATION_DEVICE_KIOSK = "kiosk";
	/**
	 * 분류 장비 유형 - PDA를 통한 분류 처리
	 */
	public static final String CLASSIFICATION_DEVICE_PDA = "pda";
	/**
	 * 분류 장비 유형 - Tablet을 통한 분류 처리
	 */
	public static final String CLASSIFICATION_DEVICE_TABLET = "tablet";
	/**
	 * 분류 장비 유형 - 기타 설비 I/F를 통한 분류 처리
	 */
	public static final String CLASSIFICATION_DEVICE_MACHINE = "machine";
	
	/**
	 * 분류 처리 액션 - 확정 처리
	 */
	public static final String CLASSIFICATION_ACTION_CONFIRM = "ok";
	/**
	 * 분류 처리 액션 - 수정 처리 
	 */
	public static final String CLASSIFICATION_ACTION_MODIFY = "modify";
	/**
	 * Indicator의 Cancel 기능 버튼을 눌러서 처리
	 */
	public static final String CLASSIFICATION_ACTION_CANCEL = "cancel";
	/**
	 * 분류 처리 액션 - Fullbox
	 */
	public static final String CLASSIFICATION_ACTION_FULL = "full";
	/**
	 * 분류 처리 액션 - 수량 조절 후 Fullbox
	 */
	public static final String CLASSIFICATION_ACTION_FULL_MODIFY = "full_modify";
	/**
	 * 분류 처리 액션 - Full 문자가 깜빡이는 시점에 버튼 확정 처리 (현재 사용 안 함)
	 */
	public static final String CLASSIFICATION_ACTION_CONFIRM_FULL = "confirm-full";	
	/**
	 * Indicator의 End 기능 버튼을 눌러서 처리 (현재 사용 안 함)
	 */
	public static final String CLASSIFICATION_ACTION_END = "end";
	
}
