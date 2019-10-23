package xyz.anythings.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.BatchReceiptItem;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Order;
import xyz.anythings.base.entity.OrderPreprocess;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.main.BatchReceiveEvent;
import xyz.anythings.base.query.store.BatchQueryStore;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.base.util.LogisBaseUtil;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.dbist.util.StringJoiner;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.context.DomainContext;
import xyz.elidom.util.BeanUtil;
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
		
		// 3. receipt 상태 확인 
		String status = receiptSummary.getCurrentStatus();
		
		// 3.1 WAIT 이 아니면 불가 return
		if(ValueUtil.isNotEqual(status, LogisConstants.COMMON_STATUS_WAIT)) {
			return receiptSummary;
		}

		// 4. 비동기 실행 
		BeanUtil.get(this.getClass()).asyncReceiveOrderData(receiptSummary);
		
		return receiptSummary;
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
		
		// 1. 대기 상태 이거나 진행 중인 수신이 있는지 확인 
		BatchReceipt runBatchReceipt = this.checkRunningOrderReceipt(domainId, areaCd, stageCd, comCd, jobDate);
		if(runBatchReceipt != null) return runBatchReceipt;
		
		// 2. WMS IF 테이블에서 수신 대상 데이터 확인 
		List<BatchReceiptItem> receiptItems = this.getWmfIfToReceiptItems(domainId, areaCd, stageCd, comCd, jobDate);
		
		// 2.1 수신 대상 데이터가 없으면 리턴 
		if(ValueUtil.isEmpty(receiptItems)) {
			throw new ElidomRuntimeException("수신할 주문 정보가 없습니다.");
		}
		
		// 3.1 데이터가 있으면 BatchReceipt JobSeq 데이터 구하기 
		int jobSeq = BatchReceipt.newBatchReceiptJobSeq(domainId, areaCd, stageCd, comCd, jobDate);
		
		// 3.2  BatchReceipt데이터 생성 
		BatchReceipt batchReceipt = new BatchReceipt();
		batchReceipt.setComCd(comCd);
		batchReceipt.setAreaCd(areaCd);
		batchReceipt.setStageCd(stageCd);
		batchReceipt.setJobDate(jobDate);
		batchReceipt.setJobSeq(ValueUtil.toString(jobSeq));
		batchReceipt.setStatus(LogisConstants.COMMON_STATUS_WAIT);
	
		this.queryManager.insert(batchReceipt);
		
		// 3.3 BatchReceiptItem 데이터 생성 
		for(BatchReceiptItem item : receiptItems) {
			item.setBatchId(LogisBaseUtil.newReceiptJobBatchId(batchReceipt.getDomainId()));
			item.setBatchReceiptId(batchReceipt.getId());
			
			this.queryManager.insert(item);
		}
		
		batchReceipt.setItems(receiptItems);
		
		return batchReceipt;
	}
	

	
	/**
	 * 대기 상태 이거나 진행 중인 수신이 있는지 확인 
	 * @param domainId
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @return
	 */
	private BatchReceipt checkRunningOrderReceipt(Long domainId, String areaCd, String stageCd, String comCd, String jobDate) {
		Map<String,Object> paramMap = ValueUtil.newMap("domainId,comCd,areaCd,stageCd,jobDate,status"
				, domainId,comCd,areaCd,stageCd,jobDate
				,ValueUtil.newStringList(LogisConstants.COMMON_STATUS_WAIT, LogisConstants.COMMON_STATUS_RUNNING));
				
		
		BatchReceipt receiptData = 
				this.queryManager.selectBySql(this.batchQueryStore.getBatchReceiptOrderTypeStatusQuery(), paramMap, BatchReceipt.class);
		
		// 대기중 또는 진행중 인 수신 정보 리턴 
		if(receiptData != null) {
			receiptData.setItems(LogisEntityUtil.searchDetails(domainId, BatchReceiptItem.class, "batchReceiptId", receiptData.getId()));
			return receiptData;
		}
		
		return null;
	}
	
	/**
	 * WMS IF 테이블에서 수신 대상 데이터 확인
	 * @param domainId
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @return
	 */
	private List<BatchReceiptItem> getWmfIfToReceiptItems(Long domainId, String areaCd, String stageCd, String comCd, String jobDate){
		Map<String,Object> paramMap = ValueUtil.newMap("domainId,comCd,areaCd,stageCd,jobDate"
				, domainId,comCd,areaCd,stageCd,jobDate);
		
		return this.queryManager.selectListBySql(this.batchQueryStore.getWmsIfToReceiptDataQuery(), paramMap, BatchReceiptItem.class,0,0);
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
	
	
	@Async
	public void asyncReceiveOrderData(BatchReceipt batchReceipt) {
		
		Domain domain = Domain.find(batchReceipt.getDomainId());
		DomainContext.setCurrentDomain(domain);
		
		try {
			// 4.배치 주문 수신.
			BatchReceipt receiptData = this.startToReceiveData(batchReceipt);
			
			// 5. 후처리 이벤트 
			this.startToReceiveEvent(EventConstants.EVENT_STEP_AFTER, receiptData);
			
		}finally {
			DomainContext.unsetAll();
		}
	}
	
	/**
	 * 배치, 작업  수신 
	 * @param batchReceipt
	 * @param params
	 * @return
	 */
	private BatchReceipt startToReceiveData(BatchReceipt batchReceipt, Object ... params) {
		// 1. 수신 시작
		// 1.1 상태 업데이트 - 진행중 
		batchReceipt.updateStatusImmediately(LogisConstants.COMMON_STATUS_RUNNING);
		
		// TODO : 데이터 복사 방식 / 컬럼 설정에서 가져오기 
		String[] sourceFields = {"WMS_BATCH_NO", "WCS_BATCH_NO", "JOB_DATE", "JOB_SEQ", "JOB_TYPE", "ORDER_DATE", "ORDER_NO", "ORDER_LINE_NO", "ORDER_DETAIL_ID", "CUST_ORDER_NO", "CUST_ORDER_LINE_NO", "COM_CD", "AREA_CD", "STAGE_CD", "EQUIP_TYPE", "EQUIP_CD", "EQUIP_NM", "SUB_EQUIP_CD", "SHOP_CD", "SHOP_NM", "SKU_CD", "SKU_BARCD", "SKU_NM", "BOX_TYPE_CD", "BOX_IN_QTY", "ORDER_QTY", "PICKED_QTY", "BOXED_QTY", "CANCEL_QTY", "BOX_ID", "INVOICE_ID", "ORDER_TYPE", "CLASS_CD", "PACK_TYPE", "VEHICLE_NO", "LOT_NO", "FROM_ZONE_CD", "FROM_CELL_CD", "TO_ZONE_CD", "TO_CELL_CD"};
		String[] targetFields = {"WMS_BATCH_NO", "WCS_BATCH_NO", "JOB_DATE", "JOB_SEQ", "JOB_TYPE", "ORDER_DATE", "ORDER_NO", "ORDER_LINE_NO", "ORDER_DETAIL_ID", "CUST_ORDER_NO", "CUST_ORDER_LINE_NO", "COM_CD", "AREA_CD", "STAGE_CD", "EQUIP_TYPE", "EQUIP_CD", "EQUIP_NM", "SUB_EQUIP_CD", "SHOP_CD", "SHOP_NM", "SKU_CD", "SKU_BARCD", "SKU_NM", "BOX_TYPE_CD", "BOX_IN_QTY", "ORDER_QTY", "PICKED_QTY", "BOXED_QTY", "CANCEL_QTY", "BOX_ID", "INVOICE_ID", "ORDER_TYPE", "CLASS_CD", "PACK_TYPE", "VEHICLE_NO", "LOT_NO", "FROM_ZONE_CD", "FROM_CELL_CD", "TO_ZONE_CD", "TO_CELL_CD"};
		
		String fieldNames = "COM_CD,AREA_CD,STAGE_CD,WMS_BATCH_NO,IF_FLAG";
		
		int jobSeq = JobBatch.getMaxJobSeq(batchReceipt.getDomainId(), batchReceipt.getComCd(), batchReceipt.getAreaCd(), batchReceipt.getAreaCd(), batchReceipt.getJobDate());
		
		boolean isExeptProcess = false;
		
		// 2.1 상세 리스트 loop
		for(BatchReceiptItem item : batchReceipt.getItems()) {
			// 2.2 skip 이면 pass
			if(item.getSkipFlag()) {
				item.updateStatusImmediately(LogisConstants.COMMON_STATUS_SKIPPED, null);
				continue;
			}
			
			// 2.3 배치ID 생성 asd
			
			// 2.4 jobSeq 발번 
			++jobSeq;
			
			// 2.5 BatchReceiptItem 상태 업데이트  - 진행 중 
			item.updateStatusImmediately(LogisConstants.COMMON_STATUS_RUNNING, null);
			
			// 2.6 JobBatch 생성 
			JobBatch batch = JobBatch.createJobBatch(item.getBatchId(), jobSeq, batchReceipt, item);
			
			try {
				// 2.7 데이터 복사  
				this.cloneData(item.getBatchId(),jobSeq, "wms_if_orders", sourceFields, targetFields, fieldNames, item.getComCd(),item.getAreaCd(),item.getStageCd(),item.getWmsBatchNo(),"N");
				
				// 2.8 JobBatch 상태 변경  
				batch.updateStatusImmediately(JobBatch.STATUS_WAIT);
				
				// 2.9 batchReceiptItem 상태 업데이트 
				item.updateStatusImmediately(LogisConstants.COMMON_STATUS_FINISHED, null);
			} catch(Exception e) {
				isExeptProcess = true;
				item.updateStatusImmediately(LogisConstants.COMMON_STATUS_ERROR, e.getCause().getMessage());
			}
		}
		
		// 3. 수신 결과 update
		batchReceipt.updateStatusImmediately(isExeptProcess ? LogisConstants.COMMON_STATUS_ERROR : LogisConstants.COMMON_STATUS_FINISHED);
		
		return batchReceipt;
	}

	
	/************** 배치 취소  **************/
	/**
	 * 배치 취소 이벤트 처리 
	 * @param eventStep
	 * @param jobBatch
	 * @param params
	 * @return
	 */
	private EventResultSet cancelBatchEvent(short eventStep, JobBatch jobBatch, Object ... params) {
		return this.publishBatchReceiveEvent(EventConstants.EVENT_RECEIVE_TYPE_CANCEL, eventStep, jobBatch.getDomainId()
				, jobBatch.getAreaCd(), jobBatch.getStageCd(), jobBatch.getComCd(), jobBatch.getJobDate(), null, jobBatch, params);	}
	
	/**
	 * 배치 취소 
	 * @param jobBatch
	 * @param params
	 * @return
	 */
	private int cancelBatchData(JobBatch jobBatch, Object... params) {
		
		// 1. 배치 상태 확인 
		String currentStatus = jobBatch.getCurrentStatus();
		
		// 1.1  작업 지시 전 취소 가능 
		if((ValueUtil.isEqual(currentStatus, JobBatch.STATUS_WAIT) 
				|| ValueUtil.isEqual(currentStatus, JobBatch.STATUS_READY)) == false ) {
			throw new ElidomRuntimeException("작업 대기 상태에서만 취소가 가능 합니다.");
		}
		
		// 2. 설정 값 조회  == 주문 취소시 데이터 유지 여부
		// TODO : 설정 에서 조회 하도록 수정 job.cmm.delete.order.when.order_cancel  
		boolean isKeepData = false;
		
		
		if(isKeepData) {
			return this.cancelOrderKeepData(jobBatch);
		} else {
			return this.cancelOrderDeleteData(jobBatch);
		}
	}
	
	/**
	 * 주문 데이터 삭제 update
	 * seq = 0
	 * @param jobBatch
	 * @return
	 */
	private int cancelOrderKeepData(JobBatch jobBatch) {
		int cnt = 0;
		
		// 1. 배치 상태  update 
		jobBatch.updateStatus(JobBatch.STATUS_CANCEL);
		
		// 2. 주문 조회 
		List<Order> orderList = LogisEntityUtil.searchEntitiesBy(jobBatch.getDomainId(), false, Order.class, "id", "domainId,batchId", jobBatch.getDomainId(), jobBatch.getId());
		
		// 3. 취소 상태 , seq = 0 셋팅 
		for(Order order : orderList) {
			order.setStatus(LogisConstants.JOB_STATUS_CANCEL);
			order.setJobSeq(0);
		}
		
		// 4. 배치 update
		this.queryManager.updateBatch(orderList, "jobSeq","status");
		
		cnt += orderList.size();
		
		// 5. 주문 가공 데이터 삭제  
		cnt += this.deleteBatchPreprocessData(jobBatch);
		return cnt;
	}
	
	/**
	 * 주문 데이터 삭제 
	 * @param jobBatch
	 * @return
	 */
	private int cancelOrderDeleteData(JobBatch jobBatch) {
		int cnt = 0;
		
		// 1. 삭제 조건 생성 
		Query condition = AnyOrmUtil.newConditionForExecution(jobBatch.getDomainId());
		condition.addFilter("batchId", jobBatch.getId());
		
		// 2. 삭제 실행
		cnt+= this.queryManager.deleteList(Order.class, condition);
		
		// 3. 주문 가공 데이터 삭제 
		cnt += this.deleteBatchPreprocessData(jobBatch);
		
		// 4. 배치 삭제 
		this.queryManager.delete(jobBatch);
		
		return cnt;
	}
	
	/**
	 * 주문 가공 데이터 삭제 
	 * @param jobBatch
	 * @return
	 */
	private int deleteBatchPreprocessData(JobBatch jobBatch) {
		// 1. 삭제 조건 생성 
		Query condition = AnyOrmUtil.newConditionForExecution(jobBatch.getDomainId());
		condition.addFilter("batchId", jobBatch.getId());
		
		// 2. 삭제 실행
		return this.queryManager.deleteList(OrderPreprocess.class, condition);
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

	
	/************** 데이터 복사  **************/
	/**
	 * 데이터 복제
	 * @param sourceTable
	 * @param targetTable
	 * @param sourceFields
	 * @param targetFields
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	private void cloneData(String batchId, int jobSeq
								, String sourceTable
								, String[] sourceFields, String[] targetFields
								, String fieldNames, Object ... fieldValues) throws Exception{
		
		// 1. 조회 쿼리 생성  
		StringJoiner qry = new StringJoiner(SysConstants.LINE_SEPARATOR);
		
		// 1.1 select 필드 셋팅 
		qry.add("select 1 ");
		for(int i = 0 ; i < sourceFields.length ;i++) {
			qry.add(" , " + sourceFields[i] + " as " + targetFields[i]);
		}
		
		// 1.2 테이블 
		qry.add("  from " + sourceTable);
		
		// 1.3 where 조건 생성 
		StringJoiner whereStr = new StringJoiner(SysConstants.LINE_SEPARATOR);
		String[] keyArr = fieldNames.split(SysConstants.COMMA);
		
		
		// 1.3.1 치환 가능 하도록 쿼리문 생성 
		whereStr.add("where 1 = 1 ");
		for(String key : keyArr) {
			whereStr.add(" and " + key + " = :" + key);
		}
		qry.add(whereStr.toString());
		
		// 2. 조회  
		Map<String,Object> params = ValueUtil.newMap(fieldNames, fieldValues);
		List<Order> sourceList = this.queryManager.selectListBySql(qry.toString(), params, Order.class, 0, 0);

		List<Order> targetList = new ArrayList<Order>(sourceList.size());
		// 3. target 데이터 생성 
		for(Order sourceItem : sourceList) {
			Order targetItem = AnyValueUtil.populate(sourceItem, new Order());
			
			targetItem.setBatchId(batchId);
			targetItem.setJobSeq(jobSeq);
			targetItem.setStatus(LogisConstants.JOB_STATUS_WAIT);
			targetList.add(targetItem);
		}
		
		// 4. 데이터 insert 
		this.queryManager.insertBatch(targetList);
	}
	
}
