package xyz.anythings.base.service.impl.dps;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Order;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.service.api.IInstructionService;
import xyz.anythings.base.service.impl.AbstractInstructionService;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.elidom.util.ValueUtil;

/**
 * dps 작업 지시 서비스 
 * @author yang
 *
 */
@Component("dpsInstructionService")
public class DpsInstructionService extends AbstractInstructionService implements IInstructionService{

	@Override
	public Map<String, Object> searchInstructionData(JobBatch batch, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	/**
	 * DPS 작업 지시 
	 * 
	 * @batch
	 * @equipList
	 * @params
	 * @return
	 */
	@Override
	public int instructBatch(JobBatch batch, List<String> equipIdList, Object... params) {
		// 1. batch 의 equip_type, group 에 따라 설비 list 를 가져온다 .
		List<?> equipList = this.getBatchEquipList(batch, equipIdList);
		
		int retCnt = 0;
		
		// 2. 대상 분류 
		retCnt += this.instructBatchOrderType(batch, equipList, params);
		
		// 3. 작업 지시 
		retCnt += this.instructBatchStart(batch, equipList, params);
		
		// 4. 작업 지시 후 박스 요청 
		retCnt += this.instructBatchBoxReq(batch, equipList, params);
		
		return retCnt;
	}

	@Override
	public int instructTotalpicking(JobBatch batch, List<String> equipIdList, Object... params) {
//		long domainId = batch.getDomainId();
		
		// 1. 대상 분류 전처리 이벤트 
//		this.instructBatchOrderTypeEvent(domainId , EventConstants.EVENT_STEP_BEFORE, batch, equipList, params);
		
		// 2. 대상 분류
		
		// 3. 대상 분류 후처리 이벤트
//		this.instructBatchOrderTypeEvent(domainId , EventConstants.EVENT_STEP_AFTER, batch, equipList, params);

		
		return 0;
	}

	@Override
	public int mergeBatch(JobBatch mainBatch, JobBatch newBatch, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int cancelInstructionBatch(JobBatch batch) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/******** 작업 지시 ************/
	/**
	 * 대상 분류 
	 * @param batch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchOrderType(JobBatch batch, List<?> equipList, Object... params) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.instructBatchOrderTypeEvent(EventConstants.EVENT_STEP_BEFORE, batch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return ValueUtil.toInteger(befResult.getResult());
		}
		
		int resultCnt = 0;
		
		// 3. 대상 분류 프로시저 호출 
		resultCnt += this.instructBatchOrderTypeCallProc(batch, equipList, params);
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.instructBatchOrderTypeEvent(EventConstants.EVENT_STEP_AFTER, batch, equipList, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				resultCnt += ValueUtil.toInteger(aftResult.getResult());
			}
		}

		return resultCnt;
	}
	
	/**
	 * 대상 분류 프로시저 호출 
	 * @param batch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchOrderTypeCallProc(JobBatch batch, List<?> equipList, Object... params) {
		int resultCnt = 0;
		
		// 1. 파라미터 생성
		Map<String, Object> paramMap = ValueUtil.newMap("P_IN_DOMAIN_ID,P_IN_BATCH_ID", batch.getDomainId(), batch.getId());
		// 2. 프로시져 콜 
		Map<?, ?> result = this.queryManager.callReturnProcedure("OP_SET_BATCH_ORDER_TYPE", paramMap, Map.class);
		// 3. 결과 
		resultCnt += ValueUtil.toInteger(result.get("P_OUT_MT_COUNT"));
		resultCnt += ValueUtil.toInteger(result.get("P_OUT_OT_COUNT"));
		
		return resultCnt;
	}
	
	/**
	 * 작업 지시 
	 * @param batch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchStart(JobBatch batch, List<?> equipList, Object... params) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.instructBatchStartEvent(EventConstants.EVENT_STEP_BEFORE, batch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return ValueUtil.toInteger(befResult.getResult());
		}
		
		int resultCnt = 0;
		
		// 3. 작업 지시 
		resultCnt += this.instructBatchStartProcess(batch, equipList, params);
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.instructBatchStartEvent(EventConstants.EVENT_STEP_AFTER, batch, equipList, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				resultCnt += ValueUtil.toInteger(aftResult.getResult());
			}
		}

		return resultCnt;
	}
	
	private int instructBatchStartProcess(JobBatch batch, List<?> equipList, Object...params) {

		// 1. 전체 주문 조회 
		List<Order> orderList = LogisEntityUtil.searchEntitiesBy(batch.getDomainId(), false, Order.class, "id,orderNo,orderType", "batchId", batch.getId());
		
		if(ValueUtil.isEmpty(batch.getEquipCd())){
			boolean useDivideOptions = false;
			if(useDivideOptions) {
				// 1.1. 호기별 분할 ( 비율 설정 ) 
				// TODO 
			} else {
				// 1.2. 전체 호기 대상 작업 지시
				
				// 1.2.1 상태 변경 
				for(Order order : orderList) {
					order.setStatus(Order.STATUS_INSTRUCT);
				}
				
				// 1.2.2 배치 update
				this.queryManager.updateBatch(orderList, "status");
				
				// 1.2.3. 작업 배치 상태 업데이트 
				batch.setEquipCd("ALL");
				batch.setStatus(JobBatch.STATUS_RUNNING);
				batch.setInstructedAt(new Date());
				
				this.queryManager.update(batch, "equipCd","status","instructedAt");
			}
			
		} else {
			// 3. 하나의 호기에 작업 지시 
			// 3.1. 상태 변경 
			for(Order order : orderList) {
				order.setStatus(Order.STATUS_INSTRUCT);
			}
			
			// 3.2. 배치 update
			batch.setStatus(JobBatch.STATUS_RUNNING);
			batch.setInstructedAt(new Date());
			
			this.queryManager.update(batch, "status","instructedAt");
			
			// 3.3. 작업 배치 상태 업데이트 
			batch.updateStatus(JobBatch.STATUS_RUNNING);
		}
		
		return 1;
	}

	
	/**
	 * 박스 요청 
	 * @param batch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchBoxReq(JobBatch batch, List<?> equipList, Object... params) {
		// 1. 단독 처리 이벤트   
		EventResultSet eventResult = this.instructBatchBoxReqTypeEvent(batch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(eventResult.isExecuted()) {
			return ValueUtil.toInteger(eventResult.getResult());
		}
		return 0;
	}
	
	
	/** 전 / 후 처리 이벤트 처리  **/
	
	/**
	 * 대상 분류 이벤트 처리 
	 * @param eventStep
	 * @param batch
	 * @return
	 */
	private EventResultSet instructBatchOrderTypeEvent(short eventStep, JobBatch batch, List<?> equipList, Object... params) {
		return this.instructBatchEvent(batch.getDomainId(), EventConstants.EVENT_INSTRUCT_TYPE_ORDER_TYPE, eventStep, batch, equipList, params);
	}
	/**
	 * 박스 요청 이벤트 처리 
	 * @param domainId
	 * @param eventStep
	 * @param batch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private EventResultSet instructBatchBoxReqTypeEvent(JobBatch batch, List<?> equipList, Object... params) {
		return this.instructBatchEvent(batch.getDomainId(), EventConstants.EVENT_INSTRUCT_TYPE_BOX_REQ, EventConstants.EVENT_STEP_ALONE, batch, equipList, params);
	}
}
