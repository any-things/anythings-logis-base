package xyz.anythings.base.event.main;

import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;

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
	 * Area 코드 
	 */
	protected String areaCd;
	
	/**
	 * 스테이지 코드 
	 */
	protected String stageCd;
	
	/**
	 * 화주 코드 
	 */
	protected String comCd;
	
	/**
	 * 작업 일자 
	 */
	protected String jobDate;
	
	
	/**
	 * 리셉트 데이터 
	 */
	private BatchReceipt receiptData;
	
	
	/**
	 * 배치 데이터 
	 */
	private JobBatch jobBatch;
	
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
	public BatchReceiveEvent(long domainId, short eventType, short eventStep) {
		super(domainId, eventStep);
		this.setEventType(eventType);
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

	public JobBatch getJobBatch() {
		return jobBatch;
	}

	public void setJobBatch(JobBatch jobBatch) {
		this.jobBatch = jobBatch;
	}
	
	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}
}
