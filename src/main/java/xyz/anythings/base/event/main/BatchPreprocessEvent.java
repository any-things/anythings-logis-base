package xyz.anythings.base.event.main;

import xyz.anythings.base.entity.JobBatch;

/**
 * 주문 가공 이벤트
 * 
 * @author shortstop
 */
public class BatchPreprocessEvent extends BatchRootEvent {

	/**
	 * 주문 가공 액션 - 호기 수동 할당 / 호기 자동 할당 / 셀 자동 할당
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
