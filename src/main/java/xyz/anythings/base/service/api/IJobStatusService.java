package xyz.anythings.base.service.api;

import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.BoxItem;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobConfig;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.elidom.dbist.dml.Page;

/**
 * 작업 상태 서비스 API
 * 
 * 	1. 배치 작업 조회
 * 		1) 배치별 작업 진행율 조회  
 * 	2. 투입 정보 조회
 * 		1) 배치별, 호기별, 사이드 별 투입 리스트 조회
 * 		2) 배치별, 사이드 별 최근 투입 정보 조회
 * 		3) 배치별 다음 처리할 릴레이 시퀀스 조회
 * 	3. 작업 조회 
 * 		1) 조회 조건으로 작업 리스트 조회
 * 		2) 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
 *  4. 처리 박스 조회
 * 		1) 조회 조건으로 처리한 박스 리스트 조회
 * 		2) 박스 내 내품 내역 조회
 *  5. 모바일 작업자 장비
 *  	1) KIOSK / 태블릿 / PDA 등 장비에서 사용할 설정 리스트 조회
 *  	2) 모바일 장비에 메시지 전송
 *  	3) 모바일 장비 업데이트 내역 보기
 *  	4) 모바일 장비 업데이트 메시지 전송
 *  6. 기타
 *  	1) 배치별 (B2B - 상품별, B2C - 주문별) 셀에 남은 총 처리할 수량 계산
 *  	2) 배치별 (B2B - 상품별, B2C - 주문별) 셀에 박싱 되지 않은 분류 처리한 누적 분류 수량
 *  	3) 배치별 (B2B - 상품별, B2C - 주문별) 셀에 박싱 되지 않은 분류 처리할 남은 수량 계산
 * 
 * @author shortstop
 */
public interface IJobStatusService {

	/**
	 * 1-1. 배치 작업 조회 - 작업 배치 작업 진행 요약 정보
	 * 
	 * @param batchId
	 * @return
	 */
	public BatchProgressRate getBatchProgressSummary(String batchId);
	
	/**
	 * 2-1. 투입 정보 조회 (리스트)
	 * 
	 * @param batchId
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<JobInput> searchInputList(String batchId, String equipType, String equipCd, String stationCd);
	
	/**
	 * 2-1. 투입 정보 조회 (페이지네이션)
	 * 
	 * @param batchId
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<JobInput> paginateInputList(String batchId, String equipType, String equipCd, String stationCd, int page, int limit);
	
	/**
	 * 2-2. 투입 정보 조회 - 가장 최근에 투입된 투입 정보 조회
	 * 
	 * @param batchId
	 * @return
	 */
	public JobInput findLatestInput(String batchId);
	
	/**
	 * 2-3. 투입 정보 조회 - 다음 처리할 릴레이 시퀀스를 조회 - 락을 걸고 처리해야 함 ...
	 * 
	 * @param batchId
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @return
	 */
	public Integer findNextInputSeq(String batchId, String equipType, String equipCd, String stationCd);
	
	/**
	 * 3-1. 작업 조회 - 설비, 작업 존 별 투입 정보에 매핑된 작업 리스트 조회
	 * 
	 * @param batchId
	 * @param inputSeq
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @return
	 */
	public List<JobInstance> searchInputJobList(String batchId, int inputSeq, String equipType, String equipCd, String stationCd);
	
	/**
	 * 3-1. 작업 조회 - 조회 조건으로 작업 리스트 조회
	 * 
	 * @param condition
	 * @return
	 */
	public List<JobInstance> searchJobList(Map<String, Object> condition);
	
	/**
	 * 4-1. 처리 박스 조회 - 셀의 풀 박스 처리를 위해 분류 처리 후 박싱이 안 된 마지막 작업 데이터 조회
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param subEquipCd
	 * @return
	 */
	public JobInstance findUnboxedJob(Long domainId, String equipType, String equipCd, String subEquipCd);
	
	/**
	 * 4-1. 처리 박스 조회 - 셀에 박싱 처리된 마지막 박스 조회
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param subEquipCd
	 * @return
	 */
	public BoxPack findLatestBox(Long domainId, String equipType, String equipCd, String subEquipCd);
	
	/**
	 * 4-2. 처리 박스 조회 - 조회 조건으로 박스 페이지네이션 조회 
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	public Page<BoxPack> paginateBoxList(Map<String, Object> condition, int page, int limit);
	
	/**
	 * 4-2. 처리 박스 조회 - 조회 조건으로 박스 리스트 조회 
	 * 
	 * @param condition
	 * @return
	 */
	public List<BoxPack> searchBoxList(Map<String, Object> condition);
	
	/**
	 * 4-2. 처리 박스 조회 - 박스 ID로 박스 내품 내역 조회
	 * 
	 * @param domainId
	 * @param boxPackId
	 * @return
	 */
	public List<BoxItem> searchBoxItems(Long domainId, String boxPackId);
	
	/**
	 * 5-1. KIOSK, 태블릿, PDA 등 작업자 장비 설정 정보 조회
	 * 
	 * @param domainId
	 * @param deviceType
	 * @param deviceId
	 * @return
	 */
	public List<JobConfig> searchWorkerDeviceSettings(Long domainId, String deviceType, String deviceId);
	
	/**
	 * 5-2. 작업자 모바일 장비에 메시지 전송
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @param notiType
	 * @param message
	 */
	public void sendMessageToWorkerDevice(Long domainId, String equipType, String equipCd, String equipZone, String notiType, String message);
		
	/**
	 * 5-3. 모바일 장비 업데이트 내역 보기
	 * 
	 * @param domainId
	 * @param deviceType
	 * @return
	 */
	public List<String> searchUpdateItems(Long domainId, String deviceType);
	
	/**
	 * 5-4. 모바일 장비 업데이트 메시지 전송
	 * 
	 * @param domainId
	 * @param deviceType
	 */
	public void sendDeviceUpdateMessage(Long domainId, String deviceType);
		
	/**
	 * 6-1. 기타 : 배치별 (B2B - 상품별, B2C - 주문별) 셀에 남은 총 처리할 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalOrderQtyByJob(JobInstance job);
	
	/**
	 * 6-2. 기타 : 배치별 (B2B - 상품별, B2C - 주문별) 셀에 박싱 되지 않은 분류 처리한 누적 분류 수량
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickedQtyByJob(JobInstance job);
	
	/**
	 * 6-3. 기타 : 배치별 (B2B - 상품별, B2C - 주문별) 셀에 박싱 되지 않은 분류 처리할 남은 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickQtyByJob(JobInstance job);
	
	/**
	 * 6-4. 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산 
	 * 
	 * @param boxInQty
	 * @param boxQty 박스 수량 
	 * @param pcsQty 낱개 수량
	 * @return
	 */
	public int toPcsQty(Integer boxInQty, Integer boxQty, Integer pcsQty);
	
	/**
	 * 6-5. 박스 입수 수량, 박스 수량, 낱개 수량으로 총 낱개 수량을 계산
	 * 
	 * @param job
	 * @param boxQty
	 * @param pcsQty
	 * @return
	 */
	public int toPcsQty(JobInstance job, Integer boxQty, Integer pcsQty);
	
}
