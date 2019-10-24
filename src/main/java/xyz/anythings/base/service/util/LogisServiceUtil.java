package xyz.anythings.base.service.util;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Rack;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.SysMessageConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;

/**
 * 물류 서비스 유틸리티
 * 
 * @author shortstop
 */
public class LogisServiceUtil {
	
	/**
	 * 작업 배치 
	 * 
	 * @param domainId
	 * @param batchId
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobBatch findBatch(Long domainId, String batchId, boolean withLock, boolean exceptionWhenEmpty) {
		JobBatch batch = LogisEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, JobBatch.class, SysConstants.ENTITY_FIELD_ID, batchId);

		if(batch == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", batchId);
		}

		return batch;
	}

	/**
	 * regionCd로 호기 정보를 조회하고 현재 실행 가능한 상태인지 체크한 후 리턴  
	 * 
	 * @param domainId
	 * @param rackCd
	 * @return
	 */
	public static Rack checkValidRack(Long domainId, String rackCd) {
		Rack rack = LogisEntityUtil.findEntityBy(domainId, true, Rack.class, "domainId,equipType,equipCd", domainId, "Rack", rackCd);
		
		if(ValueUtil.isEmpty(rack.getBatchId())) {
			// 호기에 작업 할당이 안되어 있습니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "MPS_A_NOT_ASSIGNED_TO", "terms.label.rack", "terms.label.job");
		}
		
		return rack;
	}
	
	/**
	 * B2B 유형의 작업 배치 체크
	 * 
	 * @param domainId
	 * @param batchId
	 * @return
	 */
	public static JobBatch checkB2BBatch(Long domainId, String batchId) {
		JobBatch batch = findBatch(domainId, batchId, false, true);
		String jobType = batch.getJobType();
		
		if(!LogisConstants.isB2BJobType(jobType)) {
			// 작업 유형은 지원하지 않습니다.
			throw ThrowUtil.newValidationErrorWithNoLog(true, "NOT_SUPPORTED_A", "terms.label.job_type");
		}
		
		return batch;
	}
	
	/**
	 * 반품 유형의 작업 배치 체크
	 * 
	 * @param domainId
	 * @param batchId
	 * @return
	 */
	public static JobBatch checkRtnBatch(Long domainId, String batchId) {
		JobBatch batch = findBatch(domainId, batchId, false, true);
		String jobType = batch.getJobType();
		
		if(!LogisConstants.isRtnJobType(jobType) && !LogisConstants.isRtn3JobType(jobType)) {
			// 지원하지 않는 작업 유형입니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "NOT_SUPPORTED_A", "terms.label.job_type");
		}
		
		return batch;
	}
	
	/**
	 * B2C 유형의 작업 배치 체크
	 * 
	 * @param domainId
	 * @param batchId
	 * @return
	 */
	public static JobBatch checkB2CBatch(Long domainId, String batchId) {
		JobBatch batch = findBatch(domainId, batchId, false, true);
		String jobType = batch.getJobType();
		
		if(!LogisConstants.isB2CJobType(jobType)) {
			// 지원하지 않는 작업 유형입니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "NOT_SUPPORTED_A", "terms.label.job_type");
		}
		
		return batch;
	}
	
	/**
	 * id로 조회한 작업 배치가 jobType이 일치하고 실행 중인지 체크
	 * 
	 * @param domainId
	 * @param jobType
	 * @param id
	 * @return
	 */
	public static JobBatch checkRunningBatchById(Long domainId, String jobType, String id) {
		JobBatch batch = findBatch(domainId, id, false, true);
		
		// 작업 타입 체크 
		if(ValueUtil.isNotEqual(jobType, batch.getJobType())) {
			// 작업 유형이(가) 유효하지 않습니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "A_IS_INVALID", "terms.label.job_type");
		}
		
		// 배치 상태 체크
		if(ValueUtil.isNotEqual(JobBatch.STATUS_RUNNING, batch.getStatus())) {
			// 진행 중인 작업 배치(이)가 아닙니다.
			throw ThrowUtil.newValidationErrorWithNoLog(true, SysMessageConstants.DOES_NOT_PROCEED, "terms.label.job_batch");
		}
		
		return batch;		
	}
	
	/**
	 * id로 조회한 작업 배치가 jobType이 일치한 지 체크
	 * 
	 * @param domainId
	 * @param jobType
	 * @param batchId
	 * @return
	 */
	public static JobBatch checkValidBatchById(Long domainId, String jobType, String batchId) {
		JobBatch batch = findBatch(domainId, batchId, false, true);
		
		// 작업 타입 체크 
		if(ValueUtil.isNotEqual(jobType, batch.getJobType())) {
			// 작업 유형이(가) 유효하지 않습니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "A_IS_INVALID", "terms.label.job_type");
		}
		
		return batch;
	}

}