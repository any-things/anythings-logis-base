package xyz.anythings.base.service.api.sub;
//package xyz.anythings.base.service.api;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import xyz.anythings.base.entity.BoxResult;
//import xyz.anythings.base.entity.JobBatch;
//import xyz.anythings.base.entity.JobInputSeq;
//import xyz.anythings.base.entity.JobProcess;
//import xyz.anythings.base.entity.Region;
//import xyz.anythings.base.entity.SKU;
//import xyz.anythings.base.service.model.BatchInput;
//import xyz.anythings.base.service.model.BatchInputDetail;
//import xyz.anythings.base.service.model.TabletInputSeq;
//
///**
// * 태블릿 서비스 API 
// * 
// * @author shortstop
// */
//@Component
//public interface ITabletService {
//	
//	/**
//	 * 태블릿 작업 존의 표시기의 이전 상태 재점등 
//	 * 
//	 * @param batch
//	 * @param inputSeq
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param mode
//	 */
//	public void restoreMpiOnByInputZone(JobBatch batch, JobInputSeq inputSeq, String sideCd, String equipZoneCd, String mode);
//	
//	/**
//	 * 상품으로 상품 투입
//	 * 
//	 * @param batch
//	 * @param regionCd
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param sku
//	 * @return
//	 */
//	public Object inputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
//	
//	/**
//	 * 상품 코드로 상품 투입
//	 * 
//	 * @param batch
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param comCd
//	 * @param skuCd
//	 * @return
//	 */
//	public Object inputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
//	
//	/**
//	 * 호기, 사이드, 장비 존, 내 존만 보기 옵션으로 투입 리스트 조회 (태블릿 투입 리스트 리프레쉬) 
//	 * 
//	 * @param batch
//	 * @param region
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param showOthersOrder
//	 * @return
//	 */
//	public List<TabletInputSeq> searchInputListForTablet(JobBatch batch, Region region, String sideCd, String equipZoneCd, boolean showOthersOrder); 
//	
//	/**
//	 * 호기, 사이드, 장비 존, 시작 순서로 투입 이력 리스트 조회 (태블릿 투입 리스트 네비게이션)
//	 * 
//	 * @param batch
//	 * @param region
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param showOthersOrder
//	 * @param upDownMode
//	 * @param startInputSeq
//	 * @return
//	 */
//	public List<TabletInputSeq> searchInputHistoriesForTablet(JobBatch batch, Region region, String sideCd, String equipZoneCd, boolean showOthersOrder, String upDownMode, Integer startInputSeq);
//
//	/**
//	 * 작업 투입 ID, 사이드 코드, 장비 존 코드로 작업 리스트 조회
//	 * 
//	 * @param batch
//	 * @param jobInputSeqId
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param mpiOnFlag 표시기 점등 여부
//	 * @return
//	 */
//	public Map<String, List<JobProcess>> searchInputJobList(JobBatch batch, String jobInputSeqId, String sideCd, String equipZoneCd, boolean mpiOnFlag);
//	
//	/**
//	 * QPS 수동 박스 투입
//	 * 
//	 * @param batch
//	 * @param zoneCd
//	 * @param boxId
//	 */
//	public void qpsBoxArriveByScanBox(JobBatch batch, String zoneCd, String boxId);
//	
//	/**
//	 * DPS 박스 ID 스캔하여 표시기 점등
//	 * 
//	 * @param batch
//	 * @param zoneCd
//	 * @param boxId
//	 */
//	public void dpsLightOnMpiByScanBox(JobBatch batch, String zoneCd, String boxId);
//	
//	/**
//	 * 분류 처리
//	 * 
//	 * @param domainId
//	 * @param jobProcessId
//	 * @return
//	 */
//	public JobProcess processAssort(Long domainId, String jobProcessId);
//	
//	/**
//	 * 작업 분할 처리
//	 * 
//	 * @param domainId
//	 * @param jobProcessId
//	 * @param reqBoxQty
//	 * @param reqEaQty
//	 * @param resBoxQty
//	 * @param resEaQty
//	 * @return
//	 */
//	public JobProcess splitAssort(Long domainId, String jobProcessId, Integer reqBoxQty, Integer reqEaQty, Integer resBoxQty, Integer resEaQty);
//	
//	/**
//	 * 웨어러블 검수
//	 * 
//	 * @param batch
//	 * @param region
//	 * @param input
//	 * @param zoneCd
//	 * @param sku
//	 */
//	public void inspectionWithPicking(JobBatch batch, Region region, JobInputSeq input, String zoneCd, SKU sku, String mpiCd);
//	
//	/**
//	 * 분류 작업 취소
//	 * 
//	 * @param jobProcessId
//	 * @return
//	 */
//	public JobProcess cancelAssort(Long domainId, String jobProcessId);
//	
//	/**
//	 * Fullboxing 작업
//	 * 
//	 * @param domainId
//	 * @param jobProcessId
//	 * @return 송장 번호 리턴
//	 */
//	public String fullboxing(Long domainId, String jobProcessId);
//	
//	/**
//	 * 피킹 확정 취소
//	 * 
//	 * @param batch
//	 * @param locCd
//	 * @param comCd
//	 * @param skuCd
//	 * @return 피킹 확정 취소 수량
//	 */
//	public int cancelPicked(JobBatch batch, String locCd, String comCd, String skuCd);
//	
//	/**
//	 * 풀 박싱 취소
//	 * 
//	 * @param batch
//	 * @param locCd
//	 * @param boxId
//	 * @return
//	 */
//	public BoxResult cancelFullboxing(JobBatch batch, String locCd, String boxId);
//	
//	/**
//	 * 박스 라벨 재발행
//	 * 
//	 * @param box
//	 * @param printerId
//	 */
//	public void reprintBoxLabel(BoxResult box, String printerId);
//	
//	/**
//	 * 해당 호기, 호기 사이드, 장비 존에서 투입한 투입 현황 리스트
//	 * 
//	 * @param batch
//	 * @param regionCd
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @return
//	 */
//	public List<BatchInput> searchDasInputStatusList(JobBatch batch, String regionCd, String sideCd, String equipZoneCd);
//	
//	/**
//	 * 투입 시퀀스 별 상세 작업 현황 리스트 (투입 관리[tb_job_input_seq]를 하는 경우 투입 상세 정보 조회시 사용)
//	 * 
//	 * @param batch
//	 * @param regionCd
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param inputSeq
//	 * @return
//	 */
//	public List<BatchInputDetail> searchDasInputStatusDetails(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, int inputSeq);
//	
//	/**
//	 * 호기 별 상세 작업 리스트를 투입 상품 코드로 조회 (투입 관리[tb_job_input_seq]를 하지 않은 경우 투입 상세 정보 조회시 사용)
//	 * 
//	 * @param batch
//	 * @param regionCd
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param comCd
//	 * @param skuCd
//	 * @return
//	 */
//	public List<BatchInputDetail> searchDasInputStatusDetails(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
//	
//	/**
//	 * 작업 배치, 호기 사이드, 장비 존 별 박스 처리 리스트 조회
//	 * 
//	 * @param batch
//	 * @param regionCd
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @return
//	 */
//	public List<BoxResult> searchBoxList(JobBatch batch, String regionCd, String sideCd, String equipZoneCd);
//	
//	/**
//	 * 투입 ID, 호기 사이드, 장비 존 별 표시기 소등
//	 * 
//	 * @param domainId
//	 * @param inputSeqId
//	 * @param sideCd
//	 * @param equipZoneCd
//	 */
//	public void lightOffByZone(Long domainId, String inputSeqId, String sideCd, String equipZoneCd);
//	
//	/**
//	 * 투입 ID, 호기 사이드, 장비 존 별 표시기 점등
//	 * 
//	 * @param domainId
//	 * @param inputSeqId
//	 * @param sideCd
//	 * @param equipZoneCd
//	 * @param mode todo : 분류 처리할 작업 리스트, done : 분류 처리 완료한 작업 리스트 
//	 */
//	public void lightOnByZone(Long domainId, String inputSeqId, String sideCd, String equipZoneCd, String mode);
//	
//}
