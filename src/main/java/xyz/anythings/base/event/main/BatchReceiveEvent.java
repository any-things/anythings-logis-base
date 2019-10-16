package xyz.anythings.base.event.main;

import xyz.anythings.base.entity.BatchReceipt;

/**
 * 작업 수신 
 * @author yang
 *
 */
public class BatchReceiveEvent extends BatchRootEvent {
	
	/**
	 * 10 : EVENT_RECEIVE_TYPE_RECEIPT
	 * 20 : EVENT_RECEIVE_TYPE_RECEIVE
	 * 30 : EVENT_RECEIVE_TYPE_CANCEL
	 */
	private short eventType ;
	
	
	/**
	 * 리셉트 데이터 
	 */
	private BatchReceipt receiptData;
	
	/**
	 * 이벤트 생성자 
	 * @param domainId
	 * @param eventType
	 * @param eventStep
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 */
	public BatchReceiveEvent(long domainId, short eventType, short eventStep, String areaCd, String stageCd, String comCd, String jobDate) {
		super(domainId, eventStep);
		this.setEventType(eventType);
		this.setAreaCd(areaCd);
		this.setStageCd(stageCd);
		this.setComCd(comCd);
		this.setJobDate(jobDate);
	}

	public short getEventType() {
		return eventType;
	}

	public void setEventType(short eventType) {
		this.eventType = eventType;
	}
	
	public void setReceiptData(BatchReceipt receiptData) {
		this.receiptData = receiptData;
	}
	
	public BatchReceipt getReceiptData() {
		return receiptData;
	}
}
