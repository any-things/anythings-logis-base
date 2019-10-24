package xyz.anythings.base.service.impl.dps;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.service.api.IInstructionService;
import xyz.anythings.base.service.impl.AbstractInstructionService;
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
	 * @jobBatch
	 * @equipList
	 * @params
	 * @return
	 */
	@Override
	public int instructBatch(JobBatch jobBatch, List<String> equipIdList, Object... params) {
		// 1. batch 의 equip_type, group 에 따라 설비 list 를 가져온다 .
		List<?> equipList = this.getBatchEquipList(jobBatch, equipIdList);
		
		int retCnt = 0;
		
		// 2. 대상 분류 
		retCnt += this.instructBatchOrderType(jobBatch, equipList, params);
		
		// 3. 작업 지시 
		retCnt += this.instructBatchStart(jobBatch, equipList, params);
		
		// 4. 작업 지시 후 박스 요청 
		retCnt += this.instructBatchBoxReq(jobBatch, equipList, params);
		
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
	 * @param jobBatch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchOrderType(JobBatch jobBatch, List<?> equipList, Object... params) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.instructBatchOrderTypeEvent(EventConstants.EVENT_STEP_BEFORE, jobBatch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return ValueUtil.toInteger(befResult.getResult());
		}
		
		int resultCnt = 0;
		
		// 3. 대상 분류 
		// TODO : ㅁㄴㅇㅁㄴㅇ
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.instructBatchOrderTypeEvent(EventConstants.EVENT_STEP_AFTER, jobBatch, equipList, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				resultCnt += ValueUtil.toInteger(aftResult.getResult());
			}
		}

		return resultCnt;
	}
	
	/**
	 * 작업 지시 
	 * @param jobBatch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchStart(JobBatch jobBatch, List<?> equipList, Object... params) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.instructBatchStartEvent(EventConstants.EVENT_STEP_BEFORE, jobBatch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return ValueUtil.toInteger(befResult.getResult());
		}
		
		int resultCnt = 0;
		
		// 3. 작업 지시 
		// TODO : 설정에 따라
		//        전체 호기 대상 작업 지시 
		//        호기별 균등 분할 ( 비율 설정 )
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.instructBatchStartEvent(EventConstants.EVENT_STEP_AFTER, jobBatch, equipList, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				resultCnt += ValueUtil.toInteger(aftResult.getResult());
			}
		}

		return resultCnt;
	}
	
	/**
	 * 박스 요청 
	 * @param jobBatch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private int instructBatchBoxReq(JobBatch jobBatch, List<?> equipList, Object... params) {
		// 1. 전처리 이벤트   
		EventResultSet befResult = this.instructBatchBoxReqTypeEvent(EventConstants.EVENT_STEP_BEFORE, jobBatch, equipList, params);
		
		// 2. 다음 처리 취소 일 경우 결과 리턴 
		if(befResult.isAfterEventCancel()) {
			return ValueUtil.toInteger(befResult.getResult());
		}
		
		int resultCnt = 0;
		
		// 3. 박스 요청  
		// TODO : 
		
		// 4. 후처리 이벤트 
		EventResultSet aftResult = this.instructBatchBoxReqTypeEvent(EventConstants.EVENT_STEP_AFTER, jobBatch, equipList, params);
		
		// 5. 후처리 이벤트가 실행 되고 리턴 결과가 있으면 해당 결과 리턴 
		if(aftResult.isExecuted()) {
			if(aftResult.getResult() != null ) { 
				resultCnt += ValueUtil.toInteger(aftResult.getResult());
			}
		}

		return resultCnt;
	}
	
	
	/** 전 / 후 처리 이벤트 처리  **/
	
	/**
	 * 대상 분류 이벤트 처리 
	 * @param eventStep
	 * @param jobBatch
	 * @return
	 */
	private EventResultSet instructBatchOrderTypeEvent(short eventStep, JobBatch jobBatch, List<?> equipList, Object... params) {
		return this.instructBatchEvent(jobBatch.getDomainId(), EventConstants.EVENT_INSTRUCT_TYPE_ORDER_TYPE, eventStep, jobBatch, equipList, params);
	}
	/**
	 * 박스 요청 이벤트 처리 
	 * @param domainId
	 * @param eventStep
	 * @param jobBatch
	 * @param equipList
	 * @param params
	 * @return
	 */
	private EventResultSet instructBatchBoxReqTypeEvent(short eventStep, JobBatch jobBatch, List<?> equipList, Object... params) {
		return this.instructBatchEvent(jobBatch.getDomainId(), EventConstants.EVENT_INSTRUCT_TYPE_BOX_REQ, eventStep, jobBatch, equipList, params);
	}
}
