package xyz.anythings.base.service.impl;

import java.util.List;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Rack;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.main.BatchInstructEvent;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 지시 최상위 서비스 
 * @author yang
 *
 */
public class AbstractInstructionService extends AbstractExecutionService{

	/**
	 * 배치 데이터에 대해 설비 정보 여부 를 찾아 대상 설비 리스트를 리턴
	 * @param jobBatch
	 * @param equipIdList
	 * @return
	 */
	protected List<?> getBatchEquipList(JobBatch jobBatch, List<String> equipIdList){
		
		Class<?> masterEntity = null;
		
		//1. 설비 타입에 대한 마스터 엔티티 구분 s
		if(ValueUtil.isEqual(jobBatch.getEquipType(), "Rack")) {
			masterEntity = Rack.class;
		} else {
			// TODO : 소터 등등등 추가 
			return null;
		}
		
		// 1. 작업 대상 설비가 있으면 그대로 return 
		if(ValueUtil.isNotEmpty(equipIdList)){
			return this.searchEquipByIds(jobBatch.getDomainId(), masterEntity, equipIdList);
		}
		
		// 2. jobBatch 에 작업 대상 설비 타입 및 코드 가 지정 되어 있으면 
		if(ValueUtil.isNotEmpty(jobBatch.getEquipCd())) {
			return this.searchEquipByJobBatchEquipCd(masterEntity, jobBatch);
		}
		
		// 3. jobBatch 에 작업 대상 설비 타입만 지정되어 있으면 
		return this.searchEquipByJobBatchEquipGroup(masterEntity, jobBatch);
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
	
	
	/***** 작업 대상 설비 마스터 조회  ******/
	/**
	 * ID 리스트로 설비 마스터 조회 
	 * @param domainId
	 * @param clazz
	 * @param equipIdList
	 * @return
	 */
	private <T> List<T> searchEquipByIds(long domainId, Class<T> clazz, List<String> equipIdList){
		return LogisEntityUtil.searchEntitiesBy(domainId, false, clazz, null, "id", equipIdList);
	}
	
	/**
	 * jobBatch equipCd 정보로 설비 마스터 리스트 조회 
	 * @param clazz
	 * @param jobBatch
	 * @return
	 */
	private <T> List<T> searchEquipByJobBatchEquipCd(Class<T> clazz, JobBatch jobBatch){
		return LogisEntityUtil.searchEntitiesBy(jobBatch.getDomainId(), false, clazz, null
				, "areaCd,stageCd,equipGroup,equipCd"
				, jobBatch.getAreaCd(), jobBatch.getStageCd(), jobBatch.getEquipGroup(), jobBatch.getEquipCd());	
	}
	
	/**
	 * jobBatch equipGroup 정보로 설비 마스터 리스트 조회 
	 * @param clazz
	 * @param jobBatch
	 * @return
	 */
	private <T> List<T> searchEquipByJobBatchEquipGroup(Class<T> clazz, JobBatch jobBatch){
		return LogisEntityUtil.searchEntitiesBy(jobBatch.getDomainId(), false, clazz, null
				, "areaCd,stageCd,equipGroup"
				, jobBatch.getAreaCd(), jobBatch.getStageCd(), jobBatch.getEquipGroup());	
	}
	
}
