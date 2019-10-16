package xyz.anythings.base.service.impl;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.main.BatchReceiveEvent;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * 배치 수신 서비스
 * 
 * @author shortstop
 */
@Component
public class ReceiveBatchService extends AbstractExecutionService implements IReceiveBatchService {

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
		BatchReceipt receiptData = this.createBatchReceiptData(domainId, areaCd, stageCd, comCd, jobDate, params);
		
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
		// TODO
		return null;
	}
	
	/**
	 * 배치 수신 취소
	 * 
	 * @param batch
	 * @return
	 */
	public int cancelBatch(JobBatch batch) {
		// TODO
		return 0;
	}
	
	
	/************** 배치 수신 준비  **************/
	
	/**
	 * 배치 수신 관련 이벤트 처리 
	 * @param domainId
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @param params
	 * @return BatchReceiveEvent
	 */
	private EventResultSet readyToReceiveEvent(short eventStep, Long domainId, String areaCd, String stageCd, String comCd, String jobDate, BatchReceipt receiptData, Object ... params) {
		// 1. receipt 이벤트 생성 
		BatchReceiveEvent receiptEvent 
				= new BatchReceiveEvent(domainId, EventConstants.EVENT_RECEIVE_TYPE_RECEIPT, eventStep, areaCd, stageCd, comCd, jobDate);
		receiptEvent.setPayLoad(params);
		receiptEvent.setReceiptData(receiptData);
		
		// 2. event publish
		receiptEvent = (BatchReceiveEvent)this.eventPublisher.publishEvent(receiptEvent);
		return receiptEvent.getEventResultSet();
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
	private BatchReceipt createBatchReceiptData(Long domainId, String areaCd, String stageCd, String comCd, String jobDate, Object ... params) {
		
		return null;
	}
	
	
	/************** 배치 수신  **************/

	/************** 배치 취소  **************/
}
