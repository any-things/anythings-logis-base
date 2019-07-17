package xyz.anythings.base.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BoxResult;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobProcess;
import xyz.anythings.base.entity.Location;
import xyz.anythings.base.entity.Region;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;

/**
 * PDA 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IPdaService {
	
	/**
	 * 작업 유형별로 스캔 유형, 투입 ID를 유효한 지 체크
	 *  
	 * @param domainId
	 * @param jobType
	 * @param scanType
	 * @param inputId
	 * @return
	 */
	public boolean validateInputId(Long domainId, String jobType, String scanType, String inputId);
	
	/**
	 * 작업 유형별로 투입 ID가 어떤 스캔 타입인지 찾아서 리턴
	 * 
	 * @param domainId
	 * @param jobType
	 * @param inputId
	 * @return
	 */
	public String findScanTypeByInputId(Long domainId, String jobType, String inputId);

	/**
	 * 공통 SKU 투입
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
	 * DAS SKU 투입
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param sku
	 * @return
	 */
	public Map<String, Object> dasInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
	
	/**
	 * DAS SKU 투입
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public Map<String, Object> dasInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
		
	/**
	 * 표시기를 이용한 검수 기능 : 상품을 재투입해서 분류 처리한 내용을 다시 한 번 체크한다. 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public Map<String, Object> dasInspection(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
	
	/**
	 * 반품 SKU 투입
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param sku
	 * @return
	 */
	public Object rtnInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, SKU sku);
	
	/**
	 * 반품 SKU 투입
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public Object rtnInputSku(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
	
	/**
	 * 반품 해당 작업 존에서의 가장 최근에 투입된 정보 조회
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @return
	 */
	public Object findLatestRtnInput(JobBatch batch, String regionCd, String sideCd, String equipZoneCd);
	
	/**
	 * 작업 리스트 조회
	 * 
	 * @param batch
	 * @param regionCd
	 * @param sideCd
	 * @param equipZoneCd
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public List<JobProcess> searchJobList(JobBatch batch, String regionCd, String sideCd, String equipZoneCd, String comCd, String skuCd);
	
	/**
	 * 로케이션과 박스 ID를 매핑
	 * 
	 * @param batch
	 * @param location
	 * @param boxId
	 * @param onlyCheckBoxAssignable
	 * @return
	 */
	public Object assignBoxToLocation(JobBatch batch, Location location, String boxId, boolean onlyCheckBoxAssignable);
		
	/**
	 * 로케이션에 매핑된 박스 ID를 매핑 해제
	 * 
	 * @param domainId
	 * @param locCd
	 * @return
	 */
	public Object unassignBoxToLocation(JobBatch batch, String locCd);
	
	/**
	 * 작업 유형별로 박스 ID로 박스가 존재하는지 체크
	 * 
	 * @param domainId
	 * @param jobType
	 * @param boxId
	 * @param boxDetailFlag
	 * @return
	 */
	public BoxResult findBox(Long domainId, String jobType, String boxId, boolean boxDetailFlag);
	
	/**
	 * 반품용 로케이션 정보 조회 리턴
	 * 
	 * @param domainId
	 * @param jobType
	 * @param inputType
	 * @param inputId
	 * @return
	 */
	public Map<String, Object> findLocationInfoForRtn(Long domainId, String jobType, String inputType, String inputId);
	
	/**
	 * 로케이션내 확정 수량 조회
	 * 
	 * @param domainId
	 * @param batchId
	 * @param locCd
	 * @return
	 */
	public int getPickedQtyOfLocation(Long domainId, String batchId, String locCd);
	
	/**
	 * 호기 코드와 로케이션 코드로 재고 조회
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param locCd
	 * @return
	 */
	public Stock findStock(Long domainId, String regionCd, String locCd);
	
	/**
	 * 주문 & 재고 정보를 조회
	 * 
	 * @param batch
	 * @param regionCd
	 * @param locCd
	 * @param skuCd
	 * @return
	 */
	public Stock findOrderStock(JobBatch batch, String regionCd, String locCd, String skuCd);
	
	/**
	 * 추천 로케이션 조회
	 * 
	 * @param region
	 * @param sku
	 * @return
	 */
	public List<String> searchRecommendLocations(Region region, SKU sku);
	
	/**
	 * 재고 적치
	 * 
	 * @param domainId
	 * @param locCd
	 * @param sku
	 * @param loadQty
	 * @param qtyUnit
	 * @return
	 */
	public Stock loadStock(Long domainId, String locCd, SKU sku, Integer loadQty, String qtyUnit);
	
}
