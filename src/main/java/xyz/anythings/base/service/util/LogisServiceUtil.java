package xyz.anythings.base.service.util;

import java.util.Map;

import xyz.anythings.base.LogisConfigConstants;
import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BoxType;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Rack;
import xyz.anythings.base.entity.TrayBox;
import xyz.anythings.base.model.EquipBatchSet;
import xyz.anythings.base.query.store.BoxQueryStore;
import xyz.anythings.base.service.impl.ConfigSetService;
import xyz.anythings.base.util.LogisEntityUtil;
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
	 * 작업 배치 
	 * 
	 * @param domainId
	 * @param batchId
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobBatch findBatch(Long domainId, String batchId, boolean withLock, boolean exceptionWhenEmpty) {
		JobBatch batch = LogisEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, JobBatch.class, null, SysConstants.ENTITY_FIELD_ID, batchId);

		if(batch == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", batchId);
		}

		return batch;
	}
	
	/**
	 * 설비 타입 / 코드로 설비 및 배치 찾기   
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 */
	public static EquipBatchSet findBatchByEquip(Long domainId, String equipType, String equipCd) {
		
		EquipBatchSet equipBatchSet = new EquipBatchSet();
		
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			// 1. Rack Type 
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
	 * regionCd로 호기 정보를 조회하고 현재 실행 가능한 상태인지 체크한 후 리턴  
	 * 
	 * @param domainId
	 * @param rackCd
	 * @return
	 */
	public static Rack checkValidRack(Long domainId, String rackCd) {
		Rack rack = LogisEntityUtil.findEntityBy(domainId, true, Rack.class, null, "rackCd", rackCd);
		
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
	
	
	
	/***********************************************************************************************/
	/*   버킷 ( BOX , TRAY ) 엔티티 조회    */
	/***********************************************************************************************/

	
	/**
	 * 투입 가능한 트레이 박스인지 확인 후 리턴 
	 * @param domainId
	 * @param trayCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static TrayBox checkVaildTray(Long domainId, String trayCd, boolean withLock) {

		TrayBox trayBox = findTrayBox(domainId, trayCd, withLock, true);
		
		// 상태 체크 
		if(ValueUtil.isNotEqual(LogisConstants.COMMON_STATUS_WAIT, trayBox.getStatus())) {
			// 상태 유형이(가) 유효하지 않습니다
			throw ThrowUtil.newValidationErrorWithNoLog(true, "A_IS_INVALID", "terms.label.status");
		}
		return trayBox;
	}
	
	/**
	 * 트레이 박스 검색 
	 * @param domainId
	 * @param trayCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static TrayBox findTrayBox(Long domainId, String trayCd, boolean withLock, boolean exceptionWhenEmpty) {
		TrayBox trayBox = LogisEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, TrayBox.class, null, "trayCd", trayCd);

		if(trayBox == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.TrayBox", trayCd);
		}
		return trayBox;
	}

	/**
	 * 박스 유형 검색 
	 * @param domainId
	 * @param trayCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static BoxType findBoxType(Long domainId, String boxTypeCd, boolean withLock, boolean exceptionWhenEmpty) {
		BoxType boxType = LogisEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, withLock, BoxType.class, null, "boxTypeCd", boxTypeCd);

		if(boxType == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.BoxType", boxTypeCd);
		}
		return boxType;
	}
	
	/***********************************************************************************************/
	/*   버킷 유효성 체크   */
	/***********************************************************************************************/
	
	/**
	 * 배치 설정에 박스 아이디 유니크 범위로 중복 여부 확인 
	 * TODO : 현재는 JobInput 기준 추후 박싱 결과 등 필요시 추가  
	 * @param domainId
	 * @param batch
	 * @param boxType
	 * @param boxId
	 * @return
	 */
	public static boolean checkUniqueBoxId(Long domainId, JobBatch batch, String boxType, String boxId) {
		// 1. 박스 아이디 유니크 범위 설정 
		String uniqueScope = BeanUtil.get(ConfigSetService.class).getJobConfigValue(batch, LogisConfigConstants.BOX_ID_UNIQE_SCOPE);
		
		// 1.1. 설정 값이 없으면 기본 GLOBAL
		if(ValueUtil.isEmpty(uniqueScope)) {
			uniqueScope = LogisConstants.BOX_ID_UNIQUE_SCOPE_GLOBAL;
		}
		
		// 2. 파라미터 셋팅 
		Map<String,Object> params = ValueUtil.newMap("domainId,boxId,batchId,uniqueScope,boxType"
				, domainId, boxId, batch.getId(), uniqueScope, boxType);
		
		// 3. 쿼리 
		String qry = BeanUtil.get(BoxQueryStore.class).getBoxIdUniqueCheckQuery();
		
		// 4. 조회 dup Cnt == 0  중복 없음 
		int dupCnt = BeanUtil.get(IQueryManager.class).selectBySql(qry, params, Integer.class);

		return dupCnt == 0 ? false : true;
	}
	
	
	/**
	 * boxId 에서 박스 타입 구하기 
	 * @param batch
	 * @param boxId
	 * @return
	 */
	public static String getBoxTypeByBoxId(JobBatch batch, String boxId) {
		// 1. 박스 ID 에 박스 타입 split 기준  
		String boxTypeSplit = BeanUtil.get(ConfigSetService.class).getJobConfigValue(batch, LogisConfigConstants.DPS_INPUT_BOX_TYPE_SPLIT_INDEX);
		
		// 1.1. 설정 값이 없으면 기본 GLOBAL
		if(ValueUtil.isEmpty(boxTypeSplit) || boxTypeSplit.length() < 3) {
			boxTypeSplit = "0,1";
		}
		// 2. 기준에 따라 박스 타입 분할 
		String[] splitIndex = boxTypeSplit.split(SysConstants.COMMA);
		String boxType = boxId.substring(ValueUtil.toInteger(splitIndex[0]), ValueUtil.toInteger(splitIndex[1]));
		return boxType;
	}
}
