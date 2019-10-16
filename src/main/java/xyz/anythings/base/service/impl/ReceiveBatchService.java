package xyz.anythings.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.main.BatchReceiveEvent;
import xyz.anythings.base.query.store.BatchQueryStore;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.elidom.util.ValueUtil;

/**
 * 배치 수신 서비스
 * 
 * @author shortstop
 */
@Component
public class ReceiveBatchService extends AbstractExecutionService implements IReceiveBatchService {

	/**
	 * 배치 관련 쿼리 스토어 
	 */
	@Autowired
	BatchQueryStore batchQueryStore;
	
	/**
	 * 상위 시스템으로 부터 구역, 스테이지, 고객사, 작업 일자로 배치 및 주문 수신을 위한 정보를 조회하여 리턴 
	 * - 사용자가 수신 받을 배치가 있는지 확인한 후 수신하도록 하기 위함  
	 * 
	 * @param domainId 도메인 ID
	 * @param areaCd 구역 코드 
	 * @param stageCd 스테이지 코드 
	 * @param comCd 고객사 코
	 * @param jobDate 작업 일자
	 * @param params 기타 파라미터
	 * @return
	 */
	public BatchReceipt readyToReceive(Long domainId, String areaCd, String stageCd, String comCd, String jobDate, Object ... params) {
		
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.readyToReceiveEvent(EventConstants.EVENT_STEP_BEFORE, domainId, areaCd, stageCd, comCd, jobDate,null, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return (BatchReceipt)befResult.getResult();
		}
		
		// 3. receipt데이터 생성 
		BatchReceipt receiptData = this.createReadyToReceiveData(domainId, areaCd, stageCd, comCd, jobDate, params);
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.readyToReceiveEvent(EventConstants.EVENT_STEP_AFTER, domainId, areaCd, stageCd, comCd, jobDate,receiptData, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				return (BatchReceipt)aftResult.getResult();
			}
		}
		return receiptData;
	}
	
	/**
	 * 상위 시스템으로 부터 배치, 주문을 수신
	 * 
	 * @param receiptSummary
	 * @return
	 */
	public BatchReceipt startToReceive(BatchReceipt receiptSummary) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.startToReceiveEvent(EventConstants.EVENT_STEP_BEFORE, receiptSummary);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return (BatchReceipt)befResult.getResult();
		}
		
		// 3.배치 주문 수신.
		BatchReceipt receiptData = this.startToReceiveData(receiptSummary);
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.startToReceiveEvent(EventConstants.EVENT_STEP_AFTER, receiptSummary);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				return (BatchReceipt)aftResult.getResult();
			}
		}
		return receiptData;	
	}
	
	/**
	 * 배치 수신 취소
	 * 
	 * @param batch
	 * @return
	 */
	public int cancelBatch(JobBatch batch) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.cancelBatchEvent(EventConstants.EVENT_STEP_BEFORE, batch);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return (int)befResult.getResult();
		}
		
		// 3.배치 주문 수신.
		int result = this.cancelBatchData(batch);
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.cancelBatchEvent(EventConstants.EVENT_STEP_AFTER, batch);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				return (int)aftResult.getResult();
			}
		}
		return result;	
	}
	
	
	/************** 배치 수신 준비  **************/
	
	/**
	 * 배치 수신 준비 이벤트 처리  
	 * @param domainId
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @param params
	 * @return BatchReceiveEvent
	 */
	private EventResultSet readyToReceiveEvent(short eventStep, Long domainId, String areaCd, String stageCd, String comCd, String jobDate, BatchReceipt receiptData, Object ... params) {
		return this.publishBatchReceiveEvent(EventConstants.EVENT_RECEIVE_TYPE_RECEIPT, eventStep, domainId, areaCd, stageCd, comCd, jobDate, receiptData, null, params);
	}
	
	/**
	 * 배치 수신 준비 데이터 생성 
	 * @param domainId
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @param params
	 * @return
	 */
	private BatchReceipt createReadyToReceiveData(Long domainId, String areaCd, String stageCd, String comCd, String jobDate, Object ... params) {
		
		// 1. 대기 상태 이거나 진행 중인 수신이 있으면 return
		BatchReceipt receiptData = LogisEntityUtil.findEntityBy(domainId, false, BatchReceipt.class, 
									"areaCd,stageCd,comCd,jobDate,status", areaCd,stageCd,comCd,jobDate,
									ValueUtil.newStringList(LogisConstants.COMMON_STATUS_WAIT, LogisConstants.COMMON_STATUS_RUNNING));
		
		if(receiptData != null) {
			return receiptData;
		}
		
		
		// 2.
		
		
		// 3.
		
		
		// TODO
		
		
		// LogisConstants
		// public static final String COMMON_STATUS_WAIT = "W";
		// public static final String COMMON_STATUS_FINISHED = "F";
		// public static final String COMMON_STATUS_RUNNING = "R";
		// public static final String COMMON_STATUS_ERROR = "E";
		// public static final String COMMON_STATUS_CANCEL = "C";
		
		
		
		return null;
	}
	
	
	/************** 배치 수신  **************/
	
	/**
	 * 배치 수신 이벤트 처리 
	 * @param eventStep
	 * @param batchReceipt
	 * @param params
	 * @return
	 */
	private EventResultSet startToReceiveEvent(short eventStep, BatchReceipt batchReceipt, Object ... params) {
		return this.publishBatchReceiveEvent(EventConstants.EVENT_RECEIVE_TYPE_RECEIVE, eventStep, batchReceipt.getDomainId()
				, batchReceipt.getAreaCd(), batchReceipt.getStageCd(), batchReceipt.getComCd(), batchReceipt.getJobDate(), batchReceipt, null, params);
	}
	
	/**
	 * 배치, 작업  수신 
	 * @param batchReceipt
	 * @param params
	 * @return
	 */
	private BatchReceipt startToReceiveData(BatchReceipt batchReceipt, Object ... params) {
		return null;
	}
	

	/************** 배치 취소  **************/
	
	private EventResultSet cancelBatchEvent(short eventStep, JobBatch jobBatch, Object ... params) {
		return this.publishBatchReceiveEvent(EventConstants.EVENT_RECEIVE_TYPE_CANCEL, eventStep, jobBatch.getDomainId()
				, jobBatch.getAreaCd(), jobBatch.getStageCd(), jobBatch.getComCd(), jobBatch.getJobDate(), null, jobBatch, params);	}
	
	private int cancelBatchData(JobBatch jobBatch, Object... params) {
		// TODO
		return 0;
	}
	
	
	/************** 배치 수신 이벤트 처리  **************/
	private EventResultSet publishBatchReceiveEvent(short eventType, short eventStep, Long domainId, String areaCd, String stageCd, String comCd, String jobDate, BatchReceipt receiptData, JobBatch jobBatch, Object ... params) {
		// 1. 이벤트 생성 
		BatchReceiveEvent receiptEvent 
				= new BatchReceiveEvent(domainId, eventType, eventStep);
		receiptEvent.setComCd(comCd);
		receiptEvent.setAreaCd(areaCd);
		receiptEvent.setStageCd(stageCd);
		receiptEvent.setJobDate(jobDate);
		receiptEvent.setJobBatch(jobBatch);
		receiptEvent.setReceiptData(receiptData);
		receiptEvent.setPayLoad(params);
		
		// 2. event publish
		receiptEvent = (BatchReceiveEvent)this.eventPublisher.publishEvent(receiptEvent);
		return receiptEvent.getEventResultSet();
	}

}
