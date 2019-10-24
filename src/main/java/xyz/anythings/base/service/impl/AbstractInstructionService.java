package xyz.anythings.base.service.impl;

import java.util.List;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.main.BatchInstructEvent;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * 작업 지시 최상위 서비스 
 * @author yang
 *
 */
public class AbstractInstructionService extends AbstractExecutionService{

	protected List<?> getBatchEquipList(JobBatch jobBatch, List<?> equipList){
		
		return equipList;
	}
	
	/************** 배치 작업 지시 이벤트 처리  **************/
	
	/**
	 * 작업 지시 시작 이벤트 처리 
	 * @param eventStep
	 * @param jobBatch
	 * @return
	 */
	protected EventResultSet instructBatchStartEvent(short eventStep, JobBatch jobBatch, List<?> equipList, Object... params) {
		return this.instructBatchEvent(jobBatch.getDomainId(), EventConstants.EVENT_INSTRUCT_TYPE_INSTRUCT, eventStep, jobBatch, equipList, params);
	}
	
	/**
	 * 작업 지시 이벤트 처리 
	 * @param eventType
	 * @param eventStep
	 * @param jobBatch
	 * @return
	 */
	protected EventResultSet instructBatchEvent(long domainId, short eventType, short eventStep, JobBatch jobBatch, List<?> equipList, Object... params) {
		// 1. 이벤트 생성 
		BatchInstructEvent event = new BatchInstructEvent(domainId, eventType, eventStep);
		event.setJobBatch(jobBatch);
		event.setJobType(jobBatch.getJobType());
		event.setEquipList(equipList);
		event.setPayLoad(params);
		
		// 2. event publish
		event = (BatchInstructEvent)this.eventPublisher.publishEvent(event);
		
		return event.getEventResultSet();
	}
	
	
}
