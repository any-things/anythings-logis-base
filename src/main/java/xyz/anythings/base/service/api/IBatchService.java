package xyz.anythings.base.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;

/**
 * 배치 작업 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IBatchService {
	
	/**
	 * 새로운 배치 ID를 생성
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param params
	 * @return
	 */
	public String newJobBatchId(Long domainId, String stageCd, Object ... params);
		
	/**
	 * 해당 일자의 현재 작업 진행율을 모두 조회
	 * 
	 * @param domainId
	 * @param jobDate
	 * @return
	 */
	public Map<String, Object> dailyProgressRate(Long domainId, String stageCd, String jobDate);
	
	/**
	 * 작업 배치에 대한 진행율을 조회한다.
	 * 
	 * @param batch
	 */
	public Map<String, Object> batchProgressRate(JobBatch batch);
	
	/**
	 * 작업 중인 Batch에서 메인 배치 또는 작업배치 찾기
	 * 
	 * @param domainId
	 * @param stageCd
	 * @return
	 */
	public JobBatch findRunningBatch(Long domainId, String stageCd);
	
	/**
	 * 진행 중인 작업 배치 리스트 조회 
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningBatchList(Long domainId, String stageCd, String jobType, String jobDate);
	
	/**
	 * 진행 중인 작업 대표 배치 리스트 조회 
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningMainBatchList(Long domainId, String stageCd, String jobType, String jobDate);
	
	/**
	 * 작업 배치 작업 마감 
	 * 
	 * @param batch 작업 배치
	 * @param forcibly 강제 종료 여부
	 * @return 
	 */
	public int closeBatch(JobBatch batch, boolean forcibly);
	
	/**
	 * 작업 배치 마감이 가능한 지 여부 체크 
	 * 
	 * @param batch 작업 배치
	 * @param closeForcibly 강제 종료 여부
	 * @return 작업 배치 마감 가능 여부
	 */
	public void isPossibleCloseBatch(JobBatch batch, boolean closeForcibly);
	
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
	 * 배치 그룹 마감이 가능한 지 여부 체크 
	 * 
	 * @param domainId 도메인 ID
	 * @param batchGroupId 작업 배치 그룹 ID
	 * @param closeForcibly 강제 종료 여부
	 * @return 작업 배치 마감 가능 여부
	 */
	public void isPossibleCloseBatchGroup(Long domainId, String batchGroupId, boolean closeForcibly);
	
	/**
	 * 배치 취소
	 * 
	 * @param batch
	 * @return 취소된 주문 건수
	 */
	public int cancelBatch(JobBatch batch);
	
}
