package xyz.anythings.base.service.api;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BoxResult;
import xyz.anythings.base.entity.Gateway;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInputSeq;
import xyz.anythings.base.entity.JobProcess;
import xyz.anythings.base.entity.Location;
import xyz.anythings.base.entity.Region;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.service.model.JobParams;
import xyz.anythings.base.service.model.MiddleClass;
import xyz.anythings.base.service.model.TabletInputSeq;

/**
 * 분류 서비스 공통 API
 * 
 * 	1. 분류 모듈 정보
 * 		1) 작업 유형 
 * 		2) 작업 유형에 따른 분류 모듈별 설정 Configuration (고객사 별 설정)
 * 	2. 중분류
 * 		1) 중분류
 * 	3. 작업 준비
 * 		1) 작업 유형에 따른 거래처, 상품, 주문 등이 매핑된 로케이션 리스트 조회
 * 		2) 로케이션 - 박스 매핑을 분류 처리 작업 전에 하는 경우 작업 할당된 로케이션의 박스 ID를 표시기에 표시하는 기능 (공박스 매핑이 안 된 로케이션은 nobox로 표시)
 * 		3) 로케이션에 박스를 매핑
 * 		4) 로케이션에 박스 매핑 클리어
 * 		5) 로케이션 - 박스 매핑 체크
 * 	4. 투입
 * 		1) 스캔한 ID가 스캔 유형에 따라 유효한 지 체크
 * 		2) 투입 ID를 체크하여 어떤 스캔 유형인 지 판단
 * 		3) 단건 상품 투입
 * 		4) 박스 단위 상품 투입
 * 		5) 묶음으로 상품 투입
 * 		6) 검수를 위한 상품 투입 
 * 	5. 소분류 처리
 * 		1) 작업 처리
 * 		2) 작업 취소
 * 		3) 작업 분할
 * 		4) 박싱 처리 : Fullboxing (B2B), 주문별 분류 처리 완료 (B2C)
 * 		5) 박싱 취소 (사용하지 않음)
 * 		6) 배치내 박싱 처리 안 된 박스 일괄 박싱 처리 : 배치별 Start Boxing
 * 		7) 박스(송장) 라벨 재발행
 * 		8) 로케이션 별 박스 최종 완료 처리
 * 		9) 릴레이 처리
 * 		10) 작업 존 별 작업 처리 완료 여부 체크
 * 		11) 투입한 상품 작업 처리 완료 여부 체크
 * 		12) 로케이션 별 작업 처리 완료 여부 체크
 *  6. 표시기 관련
 *  	1) 작업 리스트로 표시기 점등
 *  	2) 작업 처리 후 표시기에 작업 내용 표시
 *  	3) 작업 존에 분류 처리를 위한 표시기 점등 
 *  	4) 호기별 작업 리스트 조회 후 표시기 재점등
 *  	5) 호기별, 게이트웨이 별 작업 리스트 조회 후 표시기 재점등
 *  	6) 호기별, 작업 존 별 작업 리스트 조회 후 표시기 재점등
 *  	7) 투입 정보, 장비 별 작업 리스트 조회 후 표시기 재점등
 *  	8) 이전 표시기 버튼 색상으로 다음 표시기 색상을 결정
 *  7. 모바일 장비에 대한 작업 상태 조회
 *  	1) 태블릿 / PDA 등 장비에서 사용할 옵션 리스트 조회
 *  	2) 태블릿 / PDA 등 장비의 작업 화면에서 작업 리스트 새로 고침
 *  	3) 태블릿 / PDA 등 장비의 상품 투입 리스트 조회
 *  	4) 태블릿 / PDA 등 장비의 작업 리스트 조회
 *  	5) 태블릿 / PDA 등 장비의 Fullbox 리스트 조회
 *  8. 기타
 *  	1) 작업 마감 처리
 *  	2) 예외 처리
 *  	3) 모바일 장비에 메시지 전송
 *  	4) 배치별 상품 투입을 위한 다음 투입 순서 번호 리턴
 *  	5) 배치별 상품별 분류 처리할 로케이션에 남은 총 수량 계산
 *  	6) 배치별 상품별 분류 처리할 현재 박스에 분류 처리한 누적 분류 수량
 *  	7) 배치별 상품별 분류 처리할 현재 박스에 주문 처리할 남은 주문 수량 계산
 * 
 * @author shortstop
 */
@Component
public interface IAssortService extends ILogisBaseService {
	
	/**
	 * 1-1. 분류 모듈 정보 : 분류 서비스 모듈의 작업 유형 (DAS, DAS2, DAS3, RTN, RTN2, RTN3, DPS, DPS2, QPS) 리턴 
	 * 
	 * @return
	 */
	public String getJobType();
	
	/**
	 * 1-2. 분류 모듈 정보 : 작업 유형에 따른 분류 모듈별 설정 Configuration (고객사 별 설정) - 설정에 따른 실행 옵션 처리
	 * 
	 * @return
	 */
	public ILogisConfigurableService getLogisConfigurableService();
	
	/**
	 * 2. 중분류 
	 * 
	 * @param domainId
	 * @param batchGroupId
	 * @parma comCd
	 * @param skuCd
	 * @param variableQtyFlag
	 * @return
	 */
	public MiddleClass middleClassing(Long domainId, String batchGroupId, String comCd, String skuCd, boolean variableQtyFlag);
	
	/**
	 * 3-3. 작업 준비 : 로케이션에 공 박스 매핑이 가능한 지 체크
	 * 
	 * @param batch
	 * @param location
	 * @param boxId
	 * @return
	 */
	public Boolean beforeBoxCellMapping(JobBatch batch, Location location, String boxId);
	
	/**
	 * 3-3. 작업 준비 : 로케이션에 공 박스를 매핑
	 * 
	 * @param batch
	 * @param location
	 * @param boxId
	 * @return
	 */
	public Object boxCellMapping(JobBatch batch, Location location, String boxId);
	
	/**
	 * 3-4. 작업 준비 : 로케이션에 매핑된 박스 매핑 해제
	 * 
	 * @param batch
	 * @param locCd
	 * @return
	 */
	public Object resetBoxCellMapping(JobBatch batch, String locCd);
	
	/**
	 * 4-1. 스캔 유형에 따라 투입 ID가 유효한 지 체크
	 * 
	 * @param domainId
	 * @param scanType
	 * @param inputId
	 * @return
	 */
	public boolean validateInputId(Long domainId, String scanType, String inputId);

	/**
	 * 4-2. 투입 ID가 어떤 스캔 유형인지 판단
	 * 
	 * @param domainId
	 * @param inputId
	 * @return MpsConstants.SCAN_TYPE_ 로 시작 ...
	 */
	public String findScanTypeByInputId(Long domainId, String inputId);
	
	/**
	 * 4-3. 투입 : 배치 작업에 단건 / 묶음 단위 상품 투입 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public Object inputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);

	/**
	 * 4-4. 투입 : 배치 작업에 박스 단위 상품 투입 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param sku
	 * @return
	 */
	public Object inputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
	
	/**
	 * 4-5. 투입 : 표시기를 이용한 검수를 위해 단건 상품 투입
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param sku
	 * @return
	 */
	public Object inspectByMpi(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
	
	/**
	 * 5. 소분류 : 처리 작업 (공통)
	 * 
	 * @param mpiProcess
	 * @param domainId
	 * @param bizFlag
	 * @param jobProcessId
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public JobProcess assort(boolean mpiProcess, Long domainId, String bizFlag, String jobProcessId, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5. 소분류 : 작업 처리 (공통)
	 * 
	 * @param mpiProcess
	 * @param bizFlag
	 * @param job
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public JobProcess assort(boolean mpiProcess, String bizFlag, JobProcess job, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-1. 소분류 : 작업 확정 처리 (표시기 버튼을 터치하여 확정 처리)
	 * 
	 * @param mpiProcess 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param location
	 * @param resBoxQty
	 * @param resEaQty
	 * @return 확정 처리 수량 리턴
	 */
	public int processAssorting(boolean mpiProcess, JobProcess job, Location location, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-2. 소분류 : 작업 취소 (예정 수량보다 분류 처리할 실물이 작아서 처리할 수 없는 경우 취소 처리)
	 * 
	 * @param mpiProcess 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param location
	 */
	public void cancelAssorting(boolean mpiProcess, JobProcess job, Location location);
	
	/**
	 * 5-3. 소분류 : 작업 분할 (표시기에서 처리 수량을 변경하여 확정 처리)
	 * 
	 * @param mpiProcess 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param location
	 * @param reqBoxQty
	 * @param reqEaQty
	 * @param resBoxQty
	 * @param resEaQty
	 * @return
	 */
	public int splitAssorting(boolean mpiProcess, JobProcess job, Location location, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
	
	/**
	 * 5-4. 소분류 : Fullbox 처리 (표시기에서 'F' 버튼에 대한 처리 - Fullboxing (B2B), 주문별 분류 처리 완료 (B2C))
	 * 
	 * @param mpiProcess 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param job
	 * @param location
	 * @return
	 */
	public BoxResult startBoxing(boolean mpiProcess, JobProcess job, Location location);
	
	/**
	 * 5-4. 소분류 : Fullbox 처리 (표시기에서 표시기 코드 정보만을 넘겨주면서 'F' 처리를 요청하는 경우 처리)
	 * 
	 * @param mpiProcess
	 * @param domainId
	 * @param mpiCd
	 * @return
	 */
	public BoxResult startBoxing(boolean mpiProcess, Long domainId, String mpiCd);
	
	/**
	 * 5-4-1. 소분류 : 박싱 처리 (startBoxing) 후 마무리 처리  
	 * 
	 * @param mpiProcess 표시기에서 처리하는 경우 true, Tablet, PDA 등에서 처리하는 경우 false
	 * @param box
	 */
	public void endBoxing(boolean mpiProcess, BoxResult box);
	
	/**
	 * 5-5. 소분류 : Boxing 취소
	 * 
	 * @param box
	 */
	public BoxResult cancelBoxing(BoxResult box);
	
	/**
	 * 5-5-2. 소분류 : 피킹 확정 취소 - 확정된 피킹 내용 취소
	 * 
	 * @param domainId
	 * @param batchId
	 * @param locCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public int cancelPicked(Long domainId, String batchId, String locCd, String comCd, String skuCd);
	
	/**
	 * 5-6. 소분류 : 일괄 Fullbox (배치 작업 내 모든 로케이션내에서 Boxing 처리되지 않은 로케이션에 대한 Boxing 처리)
	 * 
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param sideCd
	 * @return 배치 내 Fullbox 처리 개수
	 */
	public int batchBoxing(Long domainId, String batchId, String regionCd, String sideCd);
	
	/**
	 * 5-7. 소분류 : 박스 라벨 (송장) 재발행
	 * 
	 * @param box
	 * @param printerIdOrName
	 */
	public void printInvoiceLabel(BoxResult box, String printerIdOrName);
	
	/**
	 * 5-8. 소분류 : 로케이션 분류 작업에 대한 최종 완료 처리
	 * 
	 * @param job
	 * @param location
	 * @param finalEndFlag
	 * @return
	 */
	public boolean endAssorting(JobProcess job, Location location, boolean finalEndFlag);
	
	/**
	 * 5-9. 소분류 : 릴레이 처리 (해당 로케이션에 다음 처리할 분류 작업을 이어서 처리하기 위해 다음 분류 작업을 조회하여 표시기 점등)
	 * 
	 * @param job
	 * @param location
	 * @param finalEndFlag
	 * @return
	 */
	public boolean executeRelay(JobProcess job, Location location, boolean finalEndFlag);

	/**
	 * 5-10. 소분류 : 작업 존 분류 처리 완료 여부 체크
	 * 
	 * @param job
	 * @param zoneCd
	 * @return
	 */
	public boolean checkWorkZoneEnd(JobProcess job, String zoneCd);
	
	/**
	 * 5-11. 소분류 : 작업 배치 ID, 작업 투입 순서 정보로 해당 투입 순서에 투입된 항목이 모두 완료되었는지 체크
	 * 
	 * @param domainId
	 * @param batchId
	 * @param processSeq
	 * @return 작업 취소가 하나라도 있으면 미처리(U), 모두 완료 처리되었다면 완료(F), 진행 중이거나 대기 상태이면 null을 리턴 
	 */
	public String checkInputSeqEnd(Long domainId, String batchId, Integer processSeq);
	
	/**
	 * 5-12. 소분류 : 로케이션에 해당하는 주문 분류 처리가 모두 완료되었는지 체크 
	 * 
	 * @param job
	 * @return
	 */
	public boolean checkOrderEnd(JobProcess job);
	
	/**
	 * 6-1. 표시기 : 작업 리스트로 표시기 점등
	 * 
	 * @param domainId
	 * @param relight 재점등 여부
	 * @param params 파라미터
	 * @return 표시기 점등을 위해 조회된 작업 리스트
	 */
	public List<JobProcess> lightOnMpis(Long domainId, boolean relight, JobParams params);
	
	/**
	 * 6-2. 표시기 : 작업 처리 후 표시기에 작업 내용 표시
	 * 
	 * @param job
	 * @param boxQty
	 * @param eaQty
	 * @param totalPickedQty
	 */
	public void displayMpiAfterAssort(JobProcess job, Integer boxQty, Integer eaQty, int totalPickedQty);
	
	/**
	 * 6-3. 표시기 : 작업 존에 분류 처리를 위한 표시기 점등
	 * 
	 * @param batch
	 * @param input
	 * @param jobList
	 */
	public void startAssorting(JobBatch batch, JobInputSeq input, List<JobProcess> jobList);
	
	/**
	 * 6-4. 표시기 : 호기별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param region
	 */
	public void restoreMpiOn(Region region);
	
	/**
	 * 6-5 표시기 : 호기별, 게이트웨이 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param batch
	 * @param gatewayCd
	 */
	public void restoreMpiOn(JobBatch batch, Gateway gw);
	
	/**
	 * 6-6. 표시기 : 호기별, 작업 존 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 */
	public void restoreMpiOn(JobBatch batch, String regionCd, String sideCd, String equipZoneCd);
	
	/**
	 * 6-7. 표시기 : 투입 정보, 장비 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param input 투입 정보
	 * @param equipZoneCd 장비 존 코드
	 * @param mode todo : 작업 처리 할 표시기, done : 이미 처리한 표시기 
	 */
	public void restoreMpiOn(JobInputSeq input, String equipZoneCd, String mode);
	
	/**
	 * 6-8. 표시기 : 이전 표시기 버튼 색상으로 다음 표시기 색상을 결정
	 * 
	 * @param domainId
	 * @param prevMpiColor
	 * @return
	 */
	public String nextMpiColor(Long domainId, String prevMpiColor);

	/**
	 * 7-2. 모바일 장비에 대한 작업 상태 조회 : 태블릿에 표시할 투입 리스트를 조회
	 * 
	 * @param region
	 * @param sideCd
	 * @param equipZoneCd
	 * @param showOthersOrder
	 * @return
	 */
	public List<TabletInputSeq> searchTabletInputList(Region region, String sideCd, String equipZoneCd, boolean showOthersOrder);

	/**
	 * 7-2. 모바일 장비에 대한 작업 상태 조회 : 태블릿에 표시할 투입 이력 리스트를 페이지네이션하여 조회
	 * 
	 * @param region
	 * @param sideCd
	 * @param equipZoneCd
	 * @param showOthersOrder
	 * @param upDownMode
	 * @param startInputSeq
	 * @return
	 */
	public List<TabletInputSeq> searchTabletInputHistories(Region region, String sideCd, String equipZoneCd, boolean showOthersOrder, String upDownMode, Integer startInputSeq);
	
	/**
	 * 7-3. 모바일 장비에 대한 작업 상태 조회 : 투입 정보의 작업 리스트 조회
	 * 
	 * @param input
	 * @param sideCd
	 * @param equipZoneCd
	 * @return
	 */
	public List<JobProcess> searchInputJobList(JobInputSeq input, String sideCd, String equipZoneCd);
	
	/**
	 * 7-4. 모바일 장비에 대한 작업 상태 조회 : 배치에 현재 진행 중인 작업 리스트를 조회
	 *  
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public List<JobProcess> searchRunningJobList(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
	
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
	public void handleAssortException(String bizFlag, JobProcess job, Location location, String errMsg);
	
	/**
	 * 8-4. 기타 : 배치별 상품 투입을 위한 다음 투입 순서 번호 리턴
	 * 
	 * @param domainId
	 * @param batchId
	 * @return
	 */
	public int nextInputSeqNo(Long domainId, String batchId);
	
	/**
	 * 8-5. 기타 : 배치별 상품별 분류 처리할 로케이션에 남은 총 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalOrderQtyByJob(JobProcess job);
	
	/**
	 * 8-6. 기타 : 배치별 상품별 분류 처리할 현재 박스에 분류 처리한 누적 분류 수량
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickedQtyByJob(JobProcess job);
	
	/**
	 * 8-7. 기타 : 배치별 상품별 분류 처리할 현재 박스에 주문 처리할 남은 주문 수량 계산
	 * 
	 * @param job
	 * @return
	 */
	public int totalPickQtyByJob(JobProcess job);
	
	/**
	 * 8-8. 기타 : 작업 정보의 처리 수량을 splitQty 수량으로 분할 처리 후 분할 처리한 작업을 리턴
	 * 
	 * @param job
	 * @param location
	 * @param splitQty
	 * @return
	 */
	public JobProcess splitJob(JobProcess job, Location location, int splitQty);
	
	/**
	 * 8-9. 기타 : 배치내 전송되지 않은 피킹 확정 실적을 상위 시스템 (WCS, WMS, ERP 등)에 재전송
	 * 
	 * @param batch
	 * @return
	 */
	public int resendPickResult(JobBatch batch);
	
	/**
	 * 8-9. 기타 : 배치내 전송되지 않은 박스 확정 실적을 상위 시스템 (WCS, WMS, ERP 등)에 재전송
	 * 
	 * @param batch
	 * @return
	 */
	public int resendBoxResult(JobBatch batch);

	/**
	 * 작업 유형에 따른 거래처, 상품, 주문 등이 매핑된 로케이션 리스트 조회
	 * 
	 * @param domainId
	 * @param regionCd
	 * @return
	 */
	//public List<Location> searchLocationBoxMappingList(Long domainId, String regionCd);
	
	/**
	 * 로케이션 - 박스 매핑을 분류 처리 작업 전에 하는 경우 작업 할당된 로케이션의 박스 ID를 표시기에 표시하는 기능 (공박스 매핑이 안 된 로케이션은 nobox로 표시)
	 * 
	 * @param domainId
	 * @param regionCd
	 */
	//public void showLocationBoxMappingByMpi(Long domainId, String regionCd);
	
	/**
	 * 로케이션에 박스 매핑이 되어 있는지 체크
	 * 
	 * @param job
	 * @param location
	 * @param sendMessageEvent
	 * @return
	 */
	//public boolean checkLocationBoxAssigned(JobProcess job, Location location, boolean sendMessageEvent);
	
	/**
	 * 배치 내에 박스 매핑이 안 된 로케이션이 하나라도 있는지 체크
	 *  
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 */
	//public void checkLocationsBoxAssigned(JobBatch batch, String regionCd, String sideCd, String equipZoneCd);
	
	/**
	 * 릴레이 기능을 사용하는지 여부 조회
	 * 
	 * @param domainId
	 * @return
	 */
	//public boolean isEnabledRelay(Long domainId);
}
