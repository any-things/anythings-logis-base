//package xyz.anythings.base.service.api;
//
//import java.util.List;
//import java.util.Map;
//
//import xyz.anythings.base.entity.BoxDetail;
//import xyz.anythings.base.entity.BoxResult;
//import xyz.anythings.base.entity.JobInputSeq;
//import xyz.anythings.base.entity.JobProcess;
//import xyz.anythings.base.service.model.BatchSummary;
//import xyz.anythings.base.service.model.JobParams;
//
///**
// * 기본 서비스 API
// * 
// * 	1. 배치 작업 조회
// * 		1) 배치별 작업 진행율 조회  
// * 	2. 투입 리스트 조회
// * 		1) 배치별, 호기별, 사이드 별 투입 리스트 조회
// * 		2) 배치별, 사이드 별 최근 투입 정보 조회
// * 		3) 다음 처리할 릴레이 시퀀스 조회
// * 		4) 배치별, 호기별, 작업 존 별 다음 처리할 릴레이 시퀀스 조회
// * 	3. 작업 조회 
// * 		1) 조회 조건으로 작업 리스트 조회
// * 		2) 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
// *  4. 처리 박스 조회
// * 		1) 조회 조건으로 처리한 박스 리스트 조회
// * 		2) 박스 내 내품 내역 조회
// * 
// * @author shortstop
// */
//public interface ILogisBaseService {
//	
//	/**
//	 * 작업 배치 작업 진행 요약 정보
//	 * 
//	 * @param domainId
//	 * @param jobType
//	 * @param batchId
//	 * @return
//	 */
//	public BatchSummary getBatchProgressSummary(Long domainId, String jobType, String batchId);
//	
//	/**
//	 * 투입 리스트 조회 
//	 * 
//	 * @param domainId
//	 * @param batchId
//	 * @param regionCd
//	 * @param sideCd
//	 * @param page
//	 * @param limit
//	 * @return
//	 */
//	public List<JobInputSeq> searchInputList(Long domainId, String batchId, String regionCd, String sideCd, int page, int limit);
//	
//	/**
//	 * 가장 최근에 투입된 투입 정보 조회
//	 * 
//	 * @param domainId
//	 * @param batchId
//	 * @param sideCd
//	 * @return
//	 */
//	public JobInputSeq findLatestInputSeq(Long domainId, String batchId, String sideCd);
//	
//	/**
//	 * 다음 처리할 릴레이 시퀀스를 조회
//	 * 
//	 * @param domainId
//	 * @param batchId
//	 * @param processSeq
//	 * @param regionCd
//	 * @param zoneCd
//	 * @return
//	 */
//	public Integer findNextRelaySeq(Long domainId, String batchId, Integer processSeq, String regionCd, String zoneCd);
//	
//	/**
//	 * 투입 정보에 매핑된 작업 리스트 조회
//	 * 
//	 * @param domainId
//	 * @param jobInputSeqId
//	 * @return
//	 */
//	public List<JobProcess> searchInputJobList(Long domainId, String jobInputSeqId);
//	
//	/**
//	 * 로케이션의 Fullbox 박싱 처리를 위해 분류 처리 후 박싱이 안 된 마지막 작업 데이터 조회
//	 * 
//	 * @param domainId
//	 * @param regionCd
//	 * @param locCd
//	 * @return
//	 */
//	public JobProcess findLatestJobForBoxing(Long domainId, String regionCd, String locCd);
//	
//	/**
//	 * 로케이션에 박싱 처리된 마지막 박스 조회
//	 * 
//	 * @param domainId
//	 * @param regionCd
//	 * @param locCd
//	 * @return
//	 */
//	public BoxResult findLatestBox(Long domainId, String regionCd, String locCd);
//	
//	/**
//	 * 조회 조건으로 박스 리스트 조회 
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	public List<BoxResult> searchBoxList(Map<String, Object> condition);
//	
//	/**
//	 * 박스 ID로 박스 내품 내역 조회
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @return
//	 */
//	public List<BoxDetail> searchBoxDetailList(Long domainId, String boxResultId);
//	
//	/**
//	 * 조회 조건으로 작업 리스트 조회
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	public List<JobProcess> searchJobList(JobParams condition);
//	
//	/**
//	 * 조회 조건으로 작업 리스트 조회
//	 * 
//	 * @param params
//	 * @return
//	 */
//	public List<JobProcess> searchJobList(Map<String, Object> params);
//	
//	/**
//	 * 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	public List<JobProcess> searchJobListForMpiOn(JobParams condition);
//	
//	/**
//	 * 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	public List<JobProcess> searchJobListForMpiOn(Map<String, Object> condition);
//	
//	/**
//	 * 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산 
//	 * 
//	 * @param boxInQty
//	 * @param boxQty 박스 수량 
//	 * @param pcsQty 낱개 수량
//	 * @return
//	 */
//	public int toPcsQty(Integer boxInQty, Integer boxQty, Integer pcsQty);
//	
//	/**
//	 * 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산
//	 * 
//	 * @param job
//	 * @param boxQty
//	 * @param pcsQty
//	 * @return
//	 */
//	public int toPcsQty(JobProcess job, Integer boxQty, Integer pcsQty);
//	
//	/**
//	 * 모바일 장비에 메시지 전송
//	 * 
//	 * @param domainId
//	 * @param jobType
//	 * @param regionCd
//	 * @param equipZoneCd
//	 * @param notiType
//	 * @param message
//	 */
//	public void sendMessageToMobileDevice(Long domainId, String jobType, String regionCd, String equipZoneCd, String notiType, String message);
//
//}
