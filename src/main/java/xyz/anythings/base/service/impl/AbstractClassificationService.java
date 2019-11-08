package xyz.anythings.base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.anythings.base.LogisConfigConstants;
import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BoxType;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.ifc.IBucket;
import xyz.anythings.base.query.store.BoxQueryStore;
import xyz.anythings.base.service.api.IClassificationService;
import xyz.anythings.base.service.util.LogisServiceUtil;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;


/**
 * 분류 공통 (Picking & Assorting) 트랜잭션 서비스 기본 구현 
 * @author yang
 *
 */
public abstract class AbstractClassificationService extends AbstractExecutionService implements IClassificationService{

	
	@Autowired
	ConfigSetService configSetService;
	
	/**
	 * 버킷이 투입 가능한 버킷인 지 확인 및 해당 버킷에 Locking
	 * @param domainId
	 * @param batch
	 * @param bucketCd
	 * @param inputType
	 */
	public IBucket vaildInputBucketByBucketCd(Long domainId, JobBatch batch, String bucketCd, boolean isBox, boolean withLock) {
		
		// 1. 박스 타입이면 박스 에서 조회 
		if(isBox) {
			String boxTypeCd = getBoxTypeByBoxId(batch, bucketCd);
			BoxType boxType = LogisServiceUtil.findBoxType(domainId, boxTypeCd, withLock, true);
			boxType.setBoxId(bucketCd);
			return boxType;
			
		// 2. 트레이 타입이면 트레이에서 조회 
		} else {
			return LogisServiceUtil.findTrayBox(domainId, bucketCd, withLock, true);
		}
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
	public boolean checkUniqueBoxId(Long domainId, JobBatch batch, String boxId) {
		// 1. 박스 아이디 유니크 범위 설정 
		String uniqueScope = this.configSetService.getJobConfigValue(batch, LogisConfigConstants.BOX_ID_UNIQE_SCOPE);
		
		// 1.1. 설정 값이 없으면 기본 GLOBAL
		if(ValueUtil.isEmpty(uniqueScope)) {
			uniqueScope = LogisConstants.BOX_ID_UNIQUE_SCOPE_GLOBAL;
		}
		
		// 2. 파라미터 셋팅 
		Map<String,Object> params = ValueUtil.newMap("domainId,boxId,batchId,uniqueScope"
				, domainId, boxId, batch.getId(), uniqueScope);
		
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
	public String getBoxTypeByBoxId(JobBatch batch, String boxId) {
		// 1. 박스 ID 에 박스 타입 split 기준  
		String boxTypeSplit = this.configSetService.getJobConfigValue(batch, LogisConfigConstants.DPS_INPUT_BOX_TYPE_SPLIT_INDEX);
		
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
