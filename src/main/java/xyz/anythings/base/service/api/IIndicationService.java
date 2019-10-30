package xyz.anythings.base.service.api;

import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.Gateway;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;

/**
 * 표시기 관련 서비스
 * 
 *  1. 표시기 관련
 *  	1) 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
 *  	2) 작업 리스트로 표시기 점등
 *  	3) 작업 처리 후 표시기에 작업 내용 표시
 *  	4) 투입 이벤트로 표시기 점등 
 *  	5) 설비별, 작업 존 별 작업 리스트 조회 후 표시기 재점등 - KIOSK 화면에서 재점등 
 *  	6) 작업 배치별, 게이트웨이 별 작업 리스트 조회 후 표시기 재점등 - 게이트웨이 리부팅 시
 *  	7) 투입 정보, 장비 별 작업 리스트 조회 후 표시기 재점등 - 모바일 장비 화면에서 재점등
 *  	8) 이전 표시기 버튼 색상으로 다음 표시기 색상을 결정
 *  
 * @author shortstop
 */
public interface IIndicationService {

	/**
	 * 1-1. 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
	 * 
	 * @param condition
	 * @return
	 */
	public List<JobInstance> searchJobList(Map<String, Object> condition);
	
	/**
	 * 1-2. 작업 리스트로 표시기 점등
	 * 
	 * @param domainId
	 * @param relight 재점등 여부
	 * @param jobList 작업 리스트
	 * @return 표시기 점등된 작업 리스트
	 */
	public List<JobInstance> indicatorsOn(boolean relight, List<JobInstance> jobList);
	
	/**
	 * 1-3. 작업 처리 후 표시기에 작업 내용 표시
	 * 
	 * @param job
	 * @param boxQty
	 * @param eaQty
	 * @param totalPickedQty
	 */
	public void displayJobStatus(JobInstance job, Integer boxQty, Integer eaQty, int totalPickedQty);
		
	/**
	 * 1-4. 투입 이벤트로 표시기 점등
	 * 
	 * @param batch
	 * @param input
	 * @param jobList
	 */
	public void indicatorsOnByInput(JobBatch batch, JobInput input, List<JobInstance> jobList);
	
	/**
	 * 1-5. 설비별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 */
	public void restoreIndicatorsOn(Long domainId, String stageCd, String equipType, String equipCd, String equipZone);
	
	/**
	 * 1-6. 작업 배치별, 게이트웨이 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param batch
	 * @param gw
	 */
	public void restoreIndicatorsOn(JobBatch batch, Gateway gw);
		
	/**
	 * 1-7. 표시기 : 투입 정보, 장비 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param input 투입 정보
	 * @param equipZone 장비 존 코드
	 * @param mode todo : 작업 처리 할 표시기, done : 이미 처리한 표시기 
	 */
	public void restoreIndicatorsOn(JobInput input, String equipZone, String mode);
	
	/**
	 * 1-8. 표시기 : 이전 표시기 버튼 색상으로 다음 표시기 색상을 결정
	 * 
	 * @param batchId
	 * @param prevColor
	 * @return
	 */
	public String nextIndicatorColor(String batchId, String prevColor);
	
}
