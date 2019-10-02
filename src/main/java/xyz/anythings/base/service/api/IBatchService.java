//package xyz.anythings.base.service.api;
//
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import xyz.anythings.base.entity.JobBatch;
//
///**
// * 배치 작업 서비스 API
// * 
// * @author shortstop
// */
//@Component
//public interface IBatchService {
//	
//	/**
//	 * 새로운 배치 ID를 생성
//	 * 
//	 * @param domainId
//	 * @param params
//	 * @return
//	 */
//	public String newJobBatchId(Long domainId, Object ... params);
//	
//	/**
//	 * 복사된 배치인지 체크 -> TODO JobBatch.isCloneBatch 대체 API
//	 * 
//	 * @param batch
//	 * @return
//	 */
//	public boolean isClonedBatch(JobBatch batch);
//	
//	/**
//	 * 복사된 작업에 대한 마스터 배치인지 체크 -> TODO JobBatch.isCloneBatchMaster 대체 API
//	 * 
//	 * @param batch
//	 * @return
//	 */
//	public boolean isCloneBatchMaster(JobBatch batch);
//	
//	/**
//	 * 해당 일자의 현재 작업 진행율을 모두 조회 --> TODO JobBatchController.dailyProgressRate 대체 API
//	 * 
//	 * @param domainId
//	 * @param jobDate
//	 * @return
//	 */
//	//public Map<String, Object> progressRate(Long domainId, String jobDate);
//	
//	/**
//	 * 작업 배치에 대한 진행율을 조회한다. -> TODO JobBatch.selectBatchProgress 대체 API
//	 * 
//	 * @param batch
//	 */
//	public void selectBatchProgress(JobBatch batch);
//	
//	/**
//	 * DPS인 경우 작업 배치당 호기가 여러 개 매핑이 가능하므로 작업 배치당 호기 정보를 설정한다. -> TODO JobBatch.setJobRegionInfo 대체 API
//	 * 
//	 * @param batch
//	 */
//	public void setJobRegionInfo(JobBatch batch);
//	
//	/**
//	 * 작업 중인 Batch에서 메인 배치 또는 작업배치 찾기 -> TODO JobBatch.getRunningBatch 대체 API
//	 * 
//	 * @param domainId
//	 * @return
//	 */
//	public JobBatch getRunningBatch(Long domainId);
//	
//	/**
//	 * MPS로 부터 진행 중인 작업 배치 리스트 조회 
//	 * 
//	 * @param domainId
//	 * @param dcCd
//	 * @param jobType
//	 * @param jobDate
//	 * @return
//	 */
//	public List<JobBatch> runningBatchList(Long domainId, String dcCd, String jobType, String jobDate);
//	
//	/**
//	 * MPS로 부터 진행 중인 작업 대표 배치 리스트 조회 
//	 * 
//	 * @param domainId
//	 * @param dcCd
//	 * @param jobType
//	 * @param jobDate
//	 * @return
//	 */
//	public List<JobBatch> runningBatchGroupList(Long domainId, String dcCd, String jobType, String jobDate);
//	
//	/**
//	 * MPS로 부터 작업 배치 작업 마감 
//	 * 
//	 * @param batch 작업 배치
//	 * @param forcibly 강제 종료 여부
//	 * @return 
//	 */
//	public int closeBatch(JobBatch batch, boolean forcibly);
//	
//	/**
//	 * MPS로 부터 작업 배치 마감이 가능한 지 여부 체크 
//	 * 
//	 * @param batch 작업 배치
//	 * @param closeForcibly 강제 종료 여부
//	 * @return 작업 배치 마감 가능 여부
//	 */
//	public void isPossibleCloseBatch(JobBatch batch, boolean closeForcibly);
//	
//	/**
//	 * 배치 취소
//	 * 
//	 * @param batch
//	 * @return 취소된 주문 건수
//	 */
//	public int cancelBatch(JobBatch batch);
//	
//}
