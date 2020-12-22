package xyz.anythings.base.service.util;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BoxType;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.entity.Rack;
import xyz.anythings.base.entity.TrayBox;
import xyz.anythings.base.model.EquipBatchSet;
import xyz.anythings.gw.entity.IndConfigSet;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.SysMessageConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

/**
 * 물류 서비스 유틸리티
 * 
 * @author shortstop
 */
public class LogisServiceUtil {
	
	/**
	 * 작업 배치 조회
	 * 
	 * @param domainId
	 * @param batchId
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobBatch findBatch(Long domainId, String batchId, boolean withLock, boolean exceptionWhenEmpty) {
		JobBatch batch = null;
		
		if(withLock) {
			batch = findBatchWithLock(domainId, batchId, exceptionWhenEmpty, true);
			
		} else {
			batch = AnyEntityUtil.findEntityBy(domainId, false, withLock, JobBatch.class, null, SysConstants.ENTITY_FIELD_ID, batchId);
		}
		
		if(batch == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", batchId);
		}

		return batch;
	}
	
	/**
	 * 작업 배치를 락을 걸면서 조회
	 * 
	 * @param domainId
	 * @param batchId
	 * @param exceptionWhenEmpty
	 * @param findConfigSet
	 * @return
	 */
	public static JobBatch findBatchWithLock(Long domainId, String batchId, boolean exceptionWhenEmpty, boolean findConfigSet) {
		JobBatch batch = AnyEntityUtil.findEntityByIdByUnselectedWithLock(false, JobBatch.class, batchId, "jobConfigSet", "indConfigSet");
		
		if(batch == null) {
			if(exceptionWhenEmpty) {
				throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", batchId);
			}
		} else {
			if(findConfigSet) {
				if(ValueUtil.isNotEmpty(batch.getIndConfigSetId())) {
					batch.setIndConfigSet(AnyEntityUtil.findEntityById(false, IndConfigSet.class, batch.getIndConfigSetId()));
				}
				
				if(ValueUtil.isNotEmpty(batch.getJobConfigSetId())) {
					batch.setJobConfigSet(AnyEntityUtil.findEntityById(false, JobConfigSet.class, batch.getJobConfigSetId()));
				}
			}
		}
		
		return batch;
	}
	
	/**
	 * 설비 타입 / 코드로 설비 및 배치 찾기
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 */
	public static EquipBatchSet findBatchByEquip(Long domainId, String equipType, String equipCd) {
		EquipBatchSet equipBatchSet = new EquipBatchSet();
		
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			Rack rack = checkValidRack(domainId, equipCd);
			JobBatch batch = findBatch(domainId, rack.getBatchId(), false, true);
			equipBatchSet.setEquipEntity(rack);
			equipBatchSet.setBatch(batch);
			
		} else {
			// TODO 기타 설비 추가 필요 함 .
		}
		
		return equipBatchSet;
	}
	
	/**
	 * 설비 타입 / 코드로 설비 및 진행 중인 배치 찾기
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public static EquipBatchSet checkRunningBatch(Long domainId, String equipType, String equipCd) {
		EquipBatchSet equipBatchSet = new EquipBatchSet();
		
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			Rack rack = checkValidRack(domainId, equipCd);
			JobBatch batch = findBatch(domainId, rack.getBatchId(), false, true);
			if(ValueUtil.isNotEqual(batch.getStatus(), JobBatch.STATUS_RUNNING)) {
				// 진행 중인 배치가 아닙니다.
				throw ThrowUtil.newValidationErrorWithNoLog(true, SysMessageConstants.DOES_NOT_PROCEED, "terms.label.job_batch"); 
			}
			
			equipBatchSet.setEquipEntity(rack);
			equipBatchSet.setBatch(batch);
			
		} else {
			// TODO 기타 설비 추가 필요함
		}
		
		return equipBatchSet;
	}
	
	/**
	 * 설비 타입 / 코드로 설비 및 진행 중인 배치 찾기
	 * 
	 * @param domainId
	 * @param batchId
	 * @return
	 */
	public static JobBatch checkRunningBatch(Long domainId, String batchId) {
		JobBatch batch = findBatch(domainId, batchId, false, true);
		
		if(ValueUtil.isNotEqual(batch.getStatus(), JobBatch.STATUS_RUNNING)) {
			// 진행 중인 배치가 아닙니다.
			throw ThrowUtil.newValidationErrorWithNoLog(true, SysMessageConstants.DOES_NOT_PROCEED, "terms.label.job_batch"); 
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
		Rack rack = AnyEntityUtil.findEntityBy(domainId, true, Rack.class, null, "rackCd", rackCd);
		
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
		
		if(!LogisConstants.isRtnJobType(jobType)) {
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
	
	/**
	 * 진행 중인 배치가 존재하는 지 판단만 한다. 존재하지 않아도 에러가 발생하지 않는다.
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public static boolean isRunningBatchExist(Long domainId, String equipType, String equipCd) {
		String sql = "select id from job_batches where domain_id = :domainId and status = :status and equip_type = :equipType and equip_cd = :equipCd";
		int runBatchCount = BeanUtil.get(IQueryManager.class).selectSizeBySql(sql, ValueUtil.newMap("domainId,status,equipType,equipCd", domainId, JobBatch.STATUS_RUNNING, equipType, equipCd));
		return runBatchCount > 0;
	}
	
	/***********************************************************************************************/
	/*									버킷 ( BOX , TRAY ) 엔티티 조회								   */
	/***********************************************************************************************/

	/**
	 * 트레이 박스 검색
	 * 
	 * @param domainId
	 * @param trayCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static TrayBox findTrayBox(Long domainId, String trayCd, boolean withLock, boolean exceptionWhenEmpty) {
		TrayBox trayBox = AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, TrayBox.class, null, "trayCd", trayCd);

		if(trayBox == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.TrayBox", trayCd);
		}
		return trayBox;
	}

	/**
	 * 박스 유형 검색
	 * 
	 * @param domainId
	 * @param trayCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static BoxType findBoxType(Long domainId, String boxTypeCd, boolean withLock, boolean exceptionWhenEmpty) {
		BoxType boxType = AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, BoxType.class, null, "boxTypeCd", boxTypeCd);

		if(boxType == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.BoxType", boxTypeCd);
		}
		return boxType;
	}

}
