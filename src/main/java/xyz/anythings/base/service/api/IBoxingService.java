package xyz.anythings.base.service.api;

import java.util.List;

import xyz.anythings.base.entity.BoxResult;
import xyz.anythings.base.entity.JobProcess;
import xyz.anythings.base.entity.Location;

/**
 * 풀 박스 처리 서비스 API
 * 
 * @author shortstop
 */
public interface IBoxingService {
	
	/**
	 * 사이트, 고객사 별 fullbox 후 액션 처리
	 * 
	 * @param domainId
	 * @param boxResultId
	 */
	public void fullboxAction(Long domainId, String boxResultId);
	
	/**
	 * 사이트, 고객사 별 fullbox 후 액션 처리
	 * 
	 * @param box
	 */
	public void fullboxAction(BoxResult box);

	/**
	 * 박싱 처리
	 * 
	 * @param assortService
	 * @param job
	 * @param location
	 * @return
	 */
	public BoxResult startBoxing(IAssortService assortService, JobProcess job, Location location);
	
	/**
	 * 수량 조절 후 박싱 처리
	 * 
	 * @param assortService
	 * @param job
	 * @param location
	 * @param boxQty
	 * @param eaQty
	 * @return
	 */
	public BoxResult startBoxingByModifyQty(IAssortService assortService, JobProcess job, Location location, Integer boxQty, Integer eaQty);
	
	/**
	 * 박싱 완료
	 * 
	 * @param assortService
	 * @param box
	 * @param location
	 * @return
	 */
	public void endBoxing(IAssortService assortService, BoxResult box, Location location);
	
	/**
	 * 작업 배치에 대해서 박싱 작업이 안 된 모든 박스의 박싱을 완료한다.
	 * 
	 * @param assortService
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param sideCd
	 * @return
	 */
	public List<BoxResult> batchBoxing(IAssortService assortService, Long domainId, String batchId, String regionCd, String sideCd);
	
	/**
	 * 박싱 취소
	 * 
	 * @param box
	 * @return
	 */
	public BoxResult cancelBoxing(BoxResult box);

}
