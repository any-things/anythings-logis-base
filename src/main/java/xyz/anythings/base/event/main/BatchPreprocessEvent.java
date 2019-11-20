package xyz.anythings.base.event.main;

import xyz.anythings.base.entity.JobBatch;

/**
 * 주문 가공 이벤트
 * 
 * @author shortstop
 */
public class BatchPreprocessEvent extends BatchRootEvent {

	/**
	 * 주문 가공 액션 - 호기 수동 할당
	 */
	public static final String ACTION_EQUIP_MANUAL_ASSIGN = "equip_manual_assign";
	/**
	 * 주문 가공 액션 - 호기 자동 할당
	 */
	public static final String ACTION_EQUIP_AUTO_ASSIGN = "equip_auto_assign";
	/**
	 * 주문 가공 액션 - 셀 할당
	 */
	public static final String ACTION_SUB_EQUIP_ASSIGN = "subequip_assign";
	/**
	 * 주문 가공 액션 - 주문 가공 완료
	 */
	public static final String ACTION_COMPLETE_PREPROCESS = "complete_preprocess";
	
	/**
	 * 주문 가공 액션 - 호기 수동 할당 / 호기 자동 할당 / 셀 자동 할당 / 주문 가공 완료
	 */
	private String preprocessAction;
	
	/**
	 * 주문 가공 이벤트 생성자 1 
	 * 
	 * @param batch
	 * @param eventStep
	 * @param preprocessAction
	 */
	public BatchPreprocessEvent(JobBatch batch, short eventStep, String preprocessAction) {
		super(batch, eventStep);
		this.setPreprocessAction(preprocessAction);
	}

	public String getPreprocessAction() {
		return preprocessAction;
	}

	public void setPreprocessAction(String preprocessAction) {
		this.preprocessAction = preprocessAction;
	}

}
