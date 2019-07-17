package xyz.anythings.base.service.api;

import java.util.List;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.SKU;

/**
 * SKU 검색 / 조회 서비스 API
 * 
 * @author shortstop
 */
public interface ISkuSearchService {
	
	/**
	 * 스캔한 상품 코드로 상품 정보 조회시 어떤 필드들을 대상으로 조회할 것인지 SKU 필드명 배열 - 설정으로 부터 읽는다.
	 * 
	 * @param domainId
	 * @return
	 */
	public String[] getSkuFieldsToFind(Long domainId);
	
	/**
	 * 상품 코드, 상품 바코드, 박스 바코드 정보를 validation check
	 * 
	 * @param domainId
	 * @param skuCd
	 * @return
	 */
	public String validateSkuCd(Long domainId, String skuCd);
	
	/**
	 * skuCd로 SKU 정보를 상위 시스템으로 부터 수신
	 *  
	 * @param domainId
	 * @param skuCd
	 * @param comCdList
	 * @return
	 */
	// public int receiveSkuBySkuCd(Long domainId, String skuCd, List<String> comCdList);

	/**
	 * 소분류를 위한 상품 리스트 조회
	 * 
	 * @param domainId
	 * @param batchGroupId
	 * @param skuCd
	 * @return
	 */
	public List<SKU> searchSkuListForPicking(Long domainId, String comCd, String batchId,String zoneCd,String skuCd);
	
	/**
	 * 중분류를 위한 상품 리스트 조회
	 * 
	 * @param domainId
	 * @param batchGroupId
	 * @param skuCd
	 * @return
	 */
	public List<SKU> searchSkuListForMiddleAssorting(Long domainId, String batchGroupId, String skuCd);
	
	/**
	 * 고객사 코드, skuCd로 SKU 조회
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param exceptionFlag
	 * @return
	 */
	public SKU findByCode(String comCd, String skuCd, boolean exceptionFlag);
	
	/**
	 * 고객사 코드, skuCd, skuBarcd로 SKU 조회
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param skuBarcd
	 * @param exceptionFlag
	 * @return
	 */
	public SKU findByCode(String comCd, String skuCd, String skuBarcd, boolean exceptionFlag);
	
	/**
	 * 고객사 코드, 박스 바코드로 SKU 조회
	 * 
	 * @param comCd
	 * @param boxBarcd
	 * @param exceptionFlag
	 * @return
	 */
	public SKU findByBoxBarcd(String comCd, String boxBarcd, boolean exceptionFlag);

	/**
	 * domainId, batch, skuCd로 부터 상품 조회
	 * 보통 조회 결과가 하나이겠지만 두 개 이상도 가능하다.  
	 * 
	 * @param batch
	 * @param skuCd
	 * @return
	 */
	public List<SKU> searchSkuList(JobBatch batch, String skuCd);
	
	/**
	 * 작업배치가 할당되지 않은 경우 domainId, skuCd로 부터 상품 조회
	 * 보통 조회 결과가 하나이겠지만 두 개 이상도 가능하다.  
	 * 
	 * @param batch
	 * @param skuCd
	 * @return
	 */
	public List<SKU> searchSkuList(String skuCd);
	
	/**
	 * 고객사 코드없이 skuCd로만 조회 
	 * 
	 * @param batch
	 * @param skuCd
	 * @param exceptionFlag
	 * @return
	 */
	public List<SKU> searchByCode(JobBatch batch, String skuCd, boolean exceptionFlag);
	
	/**
	 * 고객사 코드, skuCd로 상품 리스트 조회
	 * 
	 * @param domainId
	 * @param comCd
	 * @param skuCd
	 * @param exceptionFlag
	 * @return
	 */
	public List<SKU> searchByCode(Long domainId, String comCd, String skuCd, boolean exceptionFlag);
	
	/**
	 * SKU 중량 조회 
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param exceptionFlag
	 * @return
	 */
	public Float findSkuWeight(String comCd, String skuCd, boolean exceptionFlag);
	
	/**
	 * SKU 중량 kg/g 형태로 조회
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param toUnit
	 * @param exceptionFlag
	 * @return
	 */
	public Float findSkuWeight(String comCd, String skuCd, String toUnit, boolean exceptionFlag);
	
	/**
	 * SKU 중량을 조회하여 toUnit (kg/g) 형태로 리턴
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param toUnit
	 * @param exceptionFlag
	 * @return
	 */
	public Float findSkuRealWeight(String comCd, String skuCd, String toUnit, boolean exceptionFlag);
	
	/**
	 * SKU 계측 중량과 마스터 중량을 조회하여 SKU 형태로 리턴
	 * 
	 * @param comCd
	 * @param skuCd
	 * @param toUnit
	 * @param exceptionFlag
	 * @return
	 */
	public SKU findSkuWtAndRealWt(String comCd, String skuCd, String toUnit, boolean exceptionFlag);
	
	/**
	 * 상품 정보 조회
	 * 
	 * @param exceptionFlag
	 * @param selectFields
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	public SKU findSKU(boolean exceptionFlag, String selectFields, String paramNames, Object ... paramValues);
}
