package xyz.anythings.base.service.api;

import java.util.List;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.model.BatchProgressRate;

/**
 * 배치 작업 서비스 API
 *  1. 배치 작업 ID 생성
 *  2. 스테이지 별, 배치별 진행률 조회 
 *  3. 스테이지, 일자, 설비별 등의 조건으로 진행 중인 배치 조회
 *  4. 배치 마감, 배치 그룹 마감
 * 
 * @author shortstop
 */
public interface IBatchService {
	
	/**
	 * 새로운 배치 ID를 생성
	 * 
	 * @param domainId 도메인 ID
	 * @param stageCd 구역 코드
	 * @param params 기타 파라미터
	 * @return
	 */
	public String newJobBatchId(Long domainId, String stageCd, Object ... params);
		
	/**
	 * 해당 일자의 현재 작업 진행율을 모두 조회
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param jobDate
	 * @return
	 */
	public BatchProgressRate dailyProgressRate(Long domainId, String stageCd, String jobDate);
	
	/**
	 * 작업 배치에 대한 진행율을 조회한다.
	 * 
	 * @param batch
	 */
	public BatchProgressRate batchProgressRate(JobBatch batch);
	
	/**
	 * 스테이지 내 설비 별로 진행 중인 작업 배치 찾기
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public JobBatch findRunningBatch(Long domainId, String stageCd, String equipType, String equipCd);
	
	/**
	 * 스테이지 내 일자내 진행 중인 작업 배치 리스트 조회 
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningBatchList(Long domainId, String stageCd, String jobType, String jobDate);
	
	/**
	 * 스테이지 내 일자내 진행 중인 메인 배치 리스트 조회 
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningMainBatchList(Long domainId, String stageCd, String jobType, String jobDate);
	
	/**
	 * 작업 배치 마감이 가능한 지 여부 체크 
	 * 
	 * @param batch 작업 배치
	 * @param closeForcibly 강제 마감 여부
	 * @return 작업 배치 마감 가능 여부
	 */
	public void isPossibleCloseBatch(JobBatch batch, boolean closeForcibly);
	
	/**
	 * 작업 배치 작업 마감 
	 * 
	 * @param batch 작업 배치
	 * @param forcibly 강제 종료 여부
	 * @return 
	 */
	public int closeBatch(JobBatch batch, boolean forcibly);
	
	/**
	 * 배치 그룹 마감이 가능한 지 여부 체크 
	 * 
	 * @param domainId 도메인 ID
	 * @param batchGroupId 작업 배치 그룹 ID
	 * @param closeForcibly 강제 마감 여부
	 * @return 작업 배치 마감 가능 여부
	 */
	public void isPossibleCloseBatchGroup(Long domainId, String batchGroupId, boolean closeForcibly);
	
	/**
	 * 배치 그룹 마감 
	 * 
	 * @param domainId 도메인 ID
	 * @param batchGroupId 작업 배치 그룹 ID
	 * @param forcibly 강제 종료 여부
	 * @return 
	 */
	public int closeBatchGroup(Long domainId, String batchGroupId, boolean forcibly);
	
	/**
	 * 작업 배치 취소가 가능한 지 여부 체크 
	 * 
	 * @param batch 작업 배치
	 * @return 작업 배치 마감 가능 여부
	 */
	public void isPossibleCancelBatch(JobBatch batch);
	
}
