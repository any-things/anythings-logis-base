package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.IndConfigSet;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.WorkCell;

/**
 * 분류 서비스 트랜잭션 API
 * 
 * 	1. 분류 모듈 정보
 * 		1) 작업 유형 
 * 		2) 작업 배치별 표시기 설정 정보
 * 		3) 작업 배치별 작업 설정 정보
 * 	2. 투입
 * 		1) 스캔한 ID가 스캔 유형에 따라 유효한 지 체크
 * 		2) 투입 ID를 체크하여 어떤 스캔 유형인 지 판단
 * 		3) 수량 단위 (단건 / 묶음) 상품 투입
 * 		4) 박스 단위 상품 투입
 * 		5) 검수를 위한 상품 투입
 * 		6) 공박스 투입 (B2C)  
 * 	3. 소분류 처리
 * 		1) 분류 작업 처리
 * 		2) 분류 작업 취소
 * 		3) 수량 분할 작업 처리
 * 		4) 분류 취소
 * 		5) 박싱 처리
 * 		6) 셀 별 작업 최종 완료 처리
 * 		7) 릴레이 처리
 * 		8) 작업 존 별 작업 처리 완료 여부 체크
 * 		9) 투입한 상품 작업 처리 완료 여부 체크
 * 		10) 셀 별 작업 처리 완료 여부 체크
 *  6. 기타
 *  	1) 작업 마감 시 추가 처리
 *  	2) 예외 처리
 * 
 * @author shortstop
 */
public interface IAssortService {
	
	/**
	 * 1-1. 분류 모듈 정보 : 분류 서비스 모듈의 작업 유형 (DAS, RTN, DPS, QPS) 리턴 
	 * 
	 * @return
	 */
	public String getJobType();
	
	/**
	 * 1-2. 분류 모듈 정보 : 작업 배치별 작업 설정 정보
	 * 
	 * @param batchId
	 * @return
	 */
	public JobConfigSet getJobConfigSet(String batchId);
	
	/**
	 * 1-3. 분류 모듈 정보 : 작업 배치별 표시기 설정 정보
	 * 
	 * @param batchId
	 * @return
	 */
	public IndConfigSet getIndConfigSet(String batchId);
	
	/**
	 * 1-3. 모듈별 박싱 처리 서비스
	 * 
	 * @param params
	 * @return
	 */
	public IBoxingService getBoxingService(Object ... params);
	
	/**
	 * 2-1. 스캔 유형에 따라 투입 ID가 유효한 지 체크
	 * 
	 * @param domainId
	 * @param inputType
	 * @param inputId
	 * @param params
	 * @return
	 */
	public boolean validateInputId(Long domainId, String scanType, String inputId, Object ... params);

	/**
	 * 2-2. 투입 ID가 어떤 투입 유형인지 판단 - 상품, 박스, 호기, 셀 등을 판단  
	 * 
	 * @param domainId
	 * @param inputId
	 * @param params
	 * @return LogisConstants.INPUT_TYPE_ 로 시작 ...
	 */
	public String checkInputType(Long domainId, String inputId, Object ... params);
	
	/**
	 * 2-3. 투입 : 배치 작업에 단건 / 묶음 단위 상품 투입 
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param comCd
	 * @param skuCd
	 * @param inputQty
	 * @param params
	 * @return
	 */
	public Object inputSkuByQty(JobBatch batch, String equipType, String equipCd, String comCd, String skuCd, int inputQty, Object ... params);

	/**
	 * 2-3. 투입 : 배치 작업에 단건 / 묶음 단위 상품 투입 
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param sku
	 * @param inputQty
	 * @param params
	 * @return
	 */
	public Object inputSkuByQty(JobBatch batch, String equipType, String equipCd, SKU sku, int inputQty, Object ... params);
	
	/**
	 * 2-4. 투입 : 배치 작업에 박스 단위 상품 투입 
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param comCd
	 * @param skuCd
	 * @param params
	 * @return
	 */
	public Object inputSkuByBox(JobBatch batch, String equipType, String equipCd, String comCd, String skuCd, Object ... params);

	/**
	 * 2-4. 투입 : 배치 작업에 박스 단위 상품 투입 
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param sku
	 * @param params
	 * @return
	 */
	public Object inputSkuByBox(JobBatch batch, String equipType, String equipCd, SKU sku, Object ... params);
	
	/**
	 * 2-5. 투입 : 표시기를 이용한 검수를 위한 상품 투입
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param sku
	 * @param params
	 * @return
	 */
	public Object inspectByIndicator(JobBatch batch, String equipType, String equipCd, SKU sku, Object ... params);
	
	/**
	 * 2-5. 공박스 투입 : B2C 공박스 투입 
	 * 
	 * @param batch
	 * @param equipType
	 * @param equipCd
	 * @param boxClass 박스 유형 - 트레이 / 박스
	 * @param boxTypeCd 박스 타입 - 1,2,3 호 박스 
	 * @param boxId
	 * @param params
	 * @return
	 */
	public Object inputEmptyBox(JobBatch batch, String equipType, String equipCd, String boxClass, String boxTypeCd, String boxId, Object ... params);
	
	/**
	 * 5. 소분류 : 처리 작업 (공통)
	 * 
	 * @param assortByInd 표시기로 처리하는지 여부, KIOSK, Tablet, PDA 등에서 처리하는 경우 false
	 * @param domainId
	 * @param bizFlag
	 * @param jobInstanceId
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public JobInstance assort(boolean assortByInd, Long domainId, String bizFlag, String jobInstanceId, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5. 소분류 : 작업 처리 (공통)
	 * 
	 * @param assortByInd 표시기로 처리하는지 여부, KIOSK, Tablet, PDA 등에서 처리하는 경우 false
	 * @param bizFlag
	 * @param job
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public JobInstance assort(boolean assortByInd, String bizFlag, JobInstance job, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-1. 소분류 : 분류 작업 확정 처리 (표시기 버튼을 터치하여 확정 처리)
	 * 
	 * @param assortByInd 표시기로 처리하는지 여부, KIOSK, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param workCell
	 * @param resBoxQty
	 * @param resEaQty
	 * @return 확정 처리 수량 리턴
	 */
	public int confirmAssorting(boolean assortByInd, JobInstance job, WorkCell workCell, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-2. 소분류 : 작업 취소 (예정 수량보다 분류 처리할 실물이 작아서 처리할 수 없는 경우 취소 처리)
	 * 
	 * @param assortByInd 표시기로 처리하는지 여부, KIOSK, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param workCell
	 */
	public void undoAssorting(boolean assortByInd, JobInstance job, WorkCell workCell);
	
	/**
	 * 5-3. 소분류 : 작업 분할 (표시기에서 처리 수량을 변경하여 확정 처리)
	 * 
	 * @param assortByInd 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param workCell
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public int splitAssorting(boolean assortByInd, JobInstance job, WorkCell workCell, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-5-2. 소분류 : 피킹 확정 취소 - 확정된 피킹 내용 취소
	 * 
	 * @param batchId
	 * @param cellCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public int cancelAssorting(String batchId, String cellCd, String comCd, String skuCd);
	
	/**
	 * 5-4. 소분류 : 박스 처리 (표시기에서 'F' 버튼에 대한 처리 - Fullboxing (B2B), 주문별 분류 처리 완료 (B2C))
	 * 
	 * @param assortByInd 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param workCell
	 * @return
	 */
	public BoxPack fullBoxing(boolean assortByInd, JobInstance job, WorkCell workCell);
	
	/**
	 * 5-4. 소분류 : 박스 처리 (표시기에서 표시기 코드 정보만을 넘겨주면서 'F' 처리를 요청하는 경우 처리)
	 * 
	 * @param assortByInd 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param domainId
	 * @param indCd
	 * @return
	 */
	public BoxPack fullBoxing(boolean assortByInd, Long domainId, String indCd);
	
	/**
	 * 5-4-1. 소분류 : 수량 조정 후 박스 처리  
	 * 
	 * @param assortByInd 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param domainId
	 * @param indCd
	 * @param fullQty
	 * @return 박스 처리 결과
	 */
	public BoxPack partialFullboxing(boolean assortByInd, Long domainId, String indCd, int fullQty);
	
	/**
	 * 5-5. 소분류 : Boxing 취소
	 * 
	 * @param domainId
	 * @param boxPackId
	 * @return
	 */
	public BoxPack cancelBoxing(Long domainId, String boxPackId);
	
	/**
	 * 8-8. 기타 : 작업 정보의 처리 수량을 splitQty 수량으로 분할 처리 후 분할 처리한 작업을 리턴
	 * 
	 * @param job
	 * @param location
	 * @param splitQty
	 * @return
	 */
	public JobInstance splitJob(JobInstance job, WorkCell workCell, int splitQty);
	
	/**
	 * 5-8. 소분류 : 로케이션 분류 작업에 대한 최종 완료 처리
	 * 
	 * @param job
	 * @param workCell
	 * @param finalEndFlag
	 * @return
	 */
	//public boolean endCellAssorting(JobInstance job, WorkCell workCell, boolean finalEndFlag);
	
	/**
	 * 5-9. 소분류 : 릴레이 처리 (해당 로케이션에 다음 처리할 분류 작업을 이어서 처리하기 위해 다음 분류 작업을 조회하여 표시기 점등)
	 * 
	 * @param job
	 * @param workCell
	 * @param finalEndFlag
	 * @return
	 */
	//public boolean executeRelay(JobInstance job, WorkCell workCell, boolean finalEndFlag);

	/**
	 * 5-10. 소분류 : 스테이션 영역 분류 처리 완료 여부 체크
	 * 
	 * @param job
	 * @param stationCd
	 * @return
	 */
	//public boolean checkStationEnd(JobInstance job, String stationCd);
	
	/**
	 * 5-11. 소분류 : 작업 배치 ID, 작업 투입 순서 정보로 해당 투입 순서에 투입된 항목이 모두 완료되었는지 체크
	 * 
	 * @param domainId
	 * @param batchId
	 * @param inputSeq
	 * @return 작업 취소가 하나라도 있으면 미처리(U), 모두 완료 처리되었다면 완료(F), 진행 중이거나 대기 상태이면 null을 리턴
	 */
	//public String checkInputSeqEnd(Long domainId, String batchId, Integer inputSeq);
		
	/**
	 * 8-1. 기타 : 분류 서비스 모듈별 작업 시작 처리
	 * 
	 * @param batch
	 */
	public void batchStartAction(JobBatch batch);
	
	/**
	 * 8-2. 기타 : 분류 서비스 모듈별 작업 마감 처리
	 * 
	 * @param batch
	 */
	public void batchCloseAction(JobBatch batch);
	
	/**
	 * 8-3. 기타 : 분류 작업 처리시 에러 핸들링
	 * 
	 * @param bizFlag
	 * @param job
	 * @param location
	 * @param errMsg
	 */
	public void handleAssortException(String bizFlag, JobInstance job, WorkCell workCell, String errMsg);
	
	/**
	 * 8-9. 기타 : 배치내 전송되지 않은 피킹 확정 실적을 상위 시스템 (WCS, WMS, ERP 등)에 재전송
	 * 
	 * @param batch
	 * @return
	 */
	//public int resendPickResult(JobBatch batch);
	
	/**
	 * 8-9. 기타 : 배치내 전송되지 않은 박스 확정 실적을 상위 시스템 (WCS, WMS, ERP 등)에 재전송
	 * 
	 * @param batch
	 * @return
	 */
	//public int resendBoxResult(JobBatch batch);

}
