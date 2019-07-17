package xyz.anythings.base.service.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.ClassUtil;

/**
 * 작업 조회시 파라미터
 * 
 * @author shortstop
 */
public class JobParams {

	/**
	 * 도메인 ID
	 */
	private Long domainId;
	/**
	 * 배치 ID
	 */
	private String batchId;
	/**
	 * 작업 유형 - DAS/DPS/RTN
	 */
	private String jobType;
	/**
	 * 고객사 코드
	 */
	private String comCd;
	/**
	 * 처리 순서
	 */
	private Integer processSeq;
	/**
	 * 호기 코드
	 */
	private String regionCd;
	/**
	 * 호기 사이드 코드
	 */
	private String sideCd;
	/**
	 * 게이트웨이 코드
	 */
	private String gwCd;
	/**
	 * 장비 존 코드
	 */
	private String equipZoneCd;
	/**
	 * 작업 존 코드
	 */
	private String zoneCd;
	/**
	 * 복제 그룹 ID
	 */
	private String cloneGroupId;
	/**
	 * 로케이션 코드
	 */
	private String locCd;
	/**
	 * 버킷 (혹은 박스) 코드
	 */
	private String bucketCd;	
	/**
	 * 주문 번호
	 */
	private String orderId;
	/**
	 * 상품 코드
	 */
	private String skuCd;
	/**
	 * 상태
	 */
	private String status;
	/**
	 * 상태 리스트
	 */
	private List<String> statuses;
	/**
	 * 처리 순서가 비어 있는지 여부
	 */
	private Boolean processSeqIsBlank;
	/**
	 * 처리 순서가 비어 있는지 여부
	 */
	private Boolean latestRunning;
	
	public JobParams() {
	}
	
	public JobParams(Long domainId, String jobType, String batchId, String regionCd, String sideCd, String equipZoneCd, String zoneCd) {
		this.domainId = domainId;
		this.jobType = jobType;
		this.batchId = batchId;
		this.regionCd = regionCd;
		this.sideCd = sideCd;
		this.equipZoneCd = equipZoneCd;
		this.zoneCd = zoneCd;
	}
	
	public JobParams(Long domainId, String jobType, String batchId, Integer processSeq, String status, String locCd, String skuCd) {
		this.domainId = domainId;
		this.jobType = jobType;
		this.batchId = batchId;
		this.processSeq = processSeq;
		this.status = status;
		this.locCd = locCd;
		this.skuCd = skuCd;
	}
	
	public Long getDomainId() {
		return domainId;
	}
	
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}
	
	public String getBatchId() {
		return batchId;
	}
	
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getComCd() {
		return comCd;
	}
	
	public void setComCd(String comCd) {
		this.comCd = comCd;
	}
	
	public Integer getProcessSeq() {
		return processSeq;
	}
	
	public void setProcessSeq(Integer processSeq) {
		this.processSeq = processSeq;
	}
	
	public String getRegionCd() {
		return regionCd;
	}
	
	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	
	public String getSideCd() {
		return sideCd;
	}
	
	public void setSideCd(String sideCd) {
		this.sideCd = sideCd;
	}
	
	public String getGwCd() {
		return gwCd;
	}
	
	public void setGwCd(String gwCd) {
		this.gwCd = gwCd;
	}

	public String getEquipZoneCd() {
		return equipZoneCd;
	}

	public void setEquipZoneCd(String equipZoneCd) {
		this.equipZoneCd = equipZoneCd;
	}
	
	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public String getCloneGroupId() {
		return cloneGroupId;
	}

	public void setCloneGroupId(String cloneGroupId) {
		this.cloneGroupId = cloneGroupId;
	}

	public String getLocCd() {
		return locCd;
	}
	
	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}
	
	public String getBucketCd() {
		return bucketCd;
	}

	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSkuCd() {
		return skuCd;
	}
	
	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getStatuses() {
		return statuses;
	}
	
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	
	public Boolean getProcessSeqIsBlank() {
		return processSeqIsBlank;
	}

	public void setProcessSeqIsBlank(Boolean processSeqIsBlank) {
		this.processSeqIsBlank = processSeqIsBlank;
	}

	public Boolean getLatestRunning() {
		return latestRunning;
	}

	public void setLatestRunning(Boolean latestRunning) {
		this.latestRunning = latestRunning;
	}

	/**
	 * JobParam 인스턴스를 Map 형태로 변환
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> params = new HashMap<String, Object>(5);
		List<Field> fieldList = ClassUtil.getAllFields(this.getClass());
		
		for(Field field : fieldList) {
			Object value = ClassUtil.getFieldValue(this, field);
			if(ValueUtil.isNotEmpty(value)) {
				params.put(field.getName(), value);
			}
		}
		
		return params;
	}
	
	/**
	 * JobParam 인스턴스를 Query 형태로 변환
	 * 
	 * @return
	 */
	public Query toQuery() {
		Query query = AnyOrmUtil.newConditionForExecution();
		List<Field> fieldList = ClassUtil.getAllFields(this.getClass());
		
		for(Field field : fieldList) {
			Object value = ClassUtil.getFieldValue(this, field);
			if(ValueUtil.isNotEmpty(value)) {
				query.addFilter(field.getName(), value);
			}
		}
		
		return query;
	}

}
