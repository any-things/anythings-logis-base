package xyz.anythings.base.service.api;

import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.BoxItem;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.model.BatchProgressRate;

/**
 * 작업 상태 서비스 API
 * 
 * 	1. 배치 작업 조회
 * 		1) 배치별 작업 진행율 조회  
 * 	2. 투입 리스트 조회
 * 		1) 배치별, 호기별, 사이드 별 투입 리스트 조회
 * 		2) 배치별, 사이드 별 최근 투입 정보 조회
 * 		3) 다음 처리할 릴레이 시퀀스 조회
 * 		4) 배치별, 호기별, 작업 존 별 다음 처리할 릴레이 시퀀스 조회
 * 	3. 작업 조회 
 * 		1) 조회 조건으로 작업 리스트 조회
 * 		2) 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
 *  4. 처리 박스 조회
 * 		1) 조회 조건으로 처리한 박스 리스트 조회
 * 		2) 박스 내 내품 내역 조회
 *  5. 모바일 장비에 대한 작업 상태 조회
 *  	1) 태블릿 / PDA 등 장비에서 사용할 옵션 리스트 조회
 *  	2) 태블릿 / PDA 등 장비의 작업 화면에서 작업 리스트 새로 고침
 *  	3) 태블릿 / PDA 등 장비의 상품 투입 리스트 조회
 *  	4) 태블릿 / PDA 등 장비의 작업 리스트 조회
 *  	5) 태블릿 / PDA 등 장비의 Fullbox 리스트 조회
 *  6. 기타
 *  	1) 모바일 장비에 메시지 전송
 *  	2) 배치별 상품 투입을 위한 다음 투입 순서 번호 리턴
 *  	3) 배치별 상품별 분류 처리할 로케이션에 남은 총 수량 계산
 *  	4) 배치별 상품별 분류 처리할 현재 박스에 분류 처리한 누적 분류 수량
 *  	5) 배치별 상품별 분류 처리할 현재 박스에 주문 처리할 남은 주문 수량 계산
 * 
 * @author shortstop
 */
public interface IJobStatusService {

	/**
	 * 작업 배치 작업 진행 요약 정보
	 * 
	 * @param batchId
	 * @return
	 */
	public BatchProgressRate getBatchProgressSummary(String batchId);
	
	/**
	 * 투입 리스트 조회 
	 * 
	 * @param batchId
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<JobInput> searchInputList(String batchId, String equipType, String equipCd, String stationCd, int page, int limit);
	
	/**
	 * 가장 최근에 투입된 투입 정보 조회
	 * 
	 * @param batchId
	 * @return
	 */
	public JobInput findLatestInput(String batchId);
	
	/**
	 * 다음 처리할 릴레이 시퀀스를 조회
	 * 
	 * @param batchId
	 * @param inputSeq
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @return
	 */
	public Integer findNextInputSeq(String batchId, Integer inputSeq, String equipType, String equipCd, String stationCd);
	
	/**
	 * 투입 정보에 매핑된 작업 리스트 조회
	 * 
	 * @param jobInputId
	 * @return
	 */
	public List<JobInstance> searchInputJobList(String jobInputId);
	
	/**
	 * 조회 조건으로 작업 리스트 조회
	 * 
	 * @param condition
	 * @return
	 */
	public List<JobInstance> searchJobList(Map<String, Object> condition);
	
	/**
	 * 셀의 풀 박스 처리를 위해 분류 처리 후 박싱이 안 된 마지막 작업 데이터 조회
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param cellCd
	 * @return
	 */
	public JobInstance findJobInstanceId(Long domainId, String equipType, String equipCd, String cellCd);
	
	/**
	 * 셀에 박싱 처리된 마지막 박스 조회
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param cellCd
	 * @return
	 */
	public BoxPack findLatestBox(Long domainId, String equipType, String equipCd, String cellCd);
	
	/**
	 * 조회 조건으로 박스 리스트 조회 
	 * 
	 * @param condition
	 * @return
	 */
	public List<BoxPack> searchBoxList(Map<String, Object> condition);
	
	/**
	 * 박스 ID로 박스 내품 내역 조회
	 * 
	 * @param domainId
	 * @param boxPackId
	 * @return
	 */
	public List<BoxItem> searchBoxItems(Long domainId, String boxPackId);
		
	/**
	 * 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산 
	 * 
	 * @param boxInQty
	 * @param boxQty 박스 수량 
	 * @param pcsQty 낱개 수량
	 * @return
	 */
	public int toPcsQty(Integer boxInQty, Integer boxQty, Integer pcsQty);
	
	/**
	 * 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산
	 * 
	 * @param job
	 * @param boxQty
	 * @param pcsQty
	 * @return
	 */
	public int toPcsQty(JobInstance job, Integer boxQty, Integer pcsQty);
	
	/**
	 * 8-4. 기타 : 배치별 상품 투입을 위한 다음 투입 순서 번호 리턴
	 * 
	 * @param batchId
	 * @return
	 */
	public int nextInputSeq(String batchId);
	
	/**
	 * 8-5. 기타 : 배치별 상품별 분류 처리할 셀에 남은 총 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalOrderQtyByJob(JobInstance job);
	
	/**
	 * 8-6. 기타 : 배치별 상품별 분류 처리할 현재 박스에 분류 처리한 누적 분류 수량
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickedQtyByJob(JobInstance job);
	
	/**
	 * 8-7. 기타 : 배치별 상품별 분류 처리할 현재 박스에 주문 처리할 남은 주문 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickQtyByJob(JobInstance job);
	
	/**
	 * 작업자 모바일 장비에 메시지 전송
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @param notiType
	 * @param message
	 */
	public void sendMessageToWorkerDevice(Long domainId, String equipType, String equipCd, String equipZone, String notiType, String message);
	
}
