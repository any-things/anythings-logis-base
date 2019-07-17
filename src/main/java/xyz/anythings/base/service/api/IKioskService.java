package xyz.anythings.base.service.api;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BoxResult;
import xyz.anythings.base.entity.Bucket;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInputSeq;
import xyz.anythings.base.entity.Region;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.service.model.BatchInputDetail;
import xyz.anythings.base.service.model.BatchSummary;
import xyz.anythings.base.service.model.InspectionByWeight;
import xyz.anythings.base.service.model.MiddleClass;
import xyz.elidom.dbist.dml.Page;

/**
 * KIOSK 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IKioskService {
	
	/**
	 * 표시기 호기 재점등
	 * 
	 * @param batch
	 * @param region
	 */
	public void restoreMpiOn(JobBatch batch, Region region);
	
	/**
	 * 작업 진행 중인 배치 리스트 조회
	 * 
	 * @param domainId
	 * @param dcCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningBatchList(Long domainId, String dcCd, String jobType, String jobDate);
	
	/**
	 * 중분류를 위한 진행 중인 작업 배치 그룹 리스트 조회
	 * 
	 * @param domainId
	 * @param dcCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	public List<JobBatch> searchRunningBatchGroupList(Long domainId, String dcCd, String jobType, String jobDate);

	/**
	 * 작업 배치 정보 진행율 요약 정보 조회 
	 * 
	 * @param batch
	 * @param page
	 * @param limit
	 * @return
	 */
	public BatchSummary searchBatchSummary(JobBatch batch, int page, int limit);
	
	/**
	 * 작업 배치 정보 진행율 요약 정보 조회 
	 * 
	 * @param batch
	 * @param page
	 * @param limit
	 * @param status F : 완료, U : 미완료
	 * @return
	 */
	public BatchSummary searchBatchSummary(JobBatch batch, int page, int limit, String status);
	
	/**
	 * 작업 배치의 투입 리스트 페이지네이션 조회
	 * 
	 * @param batch
	 * @param page
	 * @param limit
	 * @param status F : 완료, U : 미완료
	 * @return
	 */
	public Page<JobInputSeq> searchInputList(JobBatch batch, int page, int limit, String status);
	
	/**
	 * 작업 배치의 투입 상세 정보 조회 
	 * 
	 * @param batch
	 * @param inputSeq
	 * @return
	 */
	public List<BatchInputDetail> searchInputDetailList(JobBatch batch, int inputSeq);
	
	/**
	 * DAS용 배치 작업에 상품 투입 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param sku
	 * @return
	 */
	public Object dasInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
	
	/**
	 * DAS용 배치 작업에 상품 투입 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public Object dasInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
	
	/**
	 * DPS용 버킷 투입 
	 * 
	 * @param batch
	 * @param bucket
	 * @return
	 */
	public Object dpsInputBucket(JobBatch batch, Bucket bucket);
	
	/**
	 * DPS용 박스 투입
	 * 
	 * @param batch
	 * @param boxId
	 * @param boxTypeCd
	 * @return
	 */
	public Object dpsInputBox(JobBatch batch, String boxId, String boxTypeCd);
	
	/**
	 * 작업 배치 그룹 정보와 상품 코드로 중분류를 실행한다.
	 * 
	 * @param domainId
	 * @param jobType
	 * @param batchGroupId
	 * @parma comCd
	 * @param skuCd
	 * @param variableQtyFlag 중분류시에 분류 수량을 제외한 수량을 보여줄 지 여부 
	 * @return
	 */
	public MiddleClass middleClassing(Long domainId, String jobType, String batchGroupId, String comCd, String skuCd, boolean variableQtyFlag);
	
	/**
	 * 중분류 - 중량 업데이트
	 * 
	 * @param domainId
	 * @param batchGroup
	 * @param skuInfo
	 */
	public SKU updateSkuWeight(Long domainId, String batchGroup, SKU skuInfo);
	
	/**
	 * 송장 번호로 검수 정보 조회
	 * 
	 * @param box
	 * @param weightInspection 중량 검수 여부
	 * @return
	 */
	public InspectionByWeight findInspection(BoxResult box, boolean weightInspection);

	/**
	 * 송장 번호로 검수 완료 처리
	 * 
	 * @param box
	 * @param weight 박스 중량
	 * @param printerId
	 * @return
	 */
	public BoxResult finishInspection(BoxResult box, Float weight, String printerId);
	
	/**
	 * 송장 번호로 송장 라벨을 인쇄
	 * 
	 * @param box
	 * @param printerId
	 */
	public void printInvoiceLabel(BoxResult box, String printerId);
		
	/**
	 * 표시기 소등
	 * 
	 * @param region
	 */
	public void lightOffAllMpi(Region region);
	
	/**
	 * 표시기 점등 
	 * 
	 * @param region
	 */
	public void lightOnAllMpi(Region region);
	
	/**
	 * 호기의 모든 Location Fullbox
	 *  
	 * @param region
	 * @param sideCd
	 * @return
	 */
	public int fullboxAll(Region region, String sideCd);
	
}
