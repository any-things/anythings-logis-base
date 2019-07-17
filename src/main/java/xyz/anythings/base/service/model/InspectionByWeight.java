package xyz.anythings.base.service.model;

import java.util.ArrayList;
import java.util.List;

import xyz.elidom.util.ValueUtil;

/**
 * 중량 검수 조회 모델 
 * 
 * @author shortstop
 */
public class InspectionByWeight {
	/**
	 * 송장 번호 
	 */
	private String invoiceId;
	/**
	 * 주문 번호 
	 */
	private String orderId;
	/**
	 * 고객 주문 번호 
	 */
	private String custOrderId;
	
	/**
	 * 작업유형
	 */
	private String jobType;
	/**
	 * 박스 번호 
	 */
	private String boxId;	
	/**
	 * 거래처 코드 
	 */
	private String custCd;
	/**
	 * 거래처 명 
	 */
	private String custNm;
	
	/**
	 * 상태  
	 */
	private String status;
	/**
	 * 예상 중량 
	 */
	private Float expectedWeight;
	
	/**
	 * 하한 중량 
	 */
	private Float minWeight;
	
	/**
	 * 상한 중량 
	 */
	private Float maxWeight;
	
	/**
	 * 박스 중량 리스트 
	 */
	private List<Float> boxWeightList;
	/**
	 * 오차 범위 리스트 
	 */
	private List<Float> errorRangeList;
	/**
	 * 검수 항목 리스트
	 */
	private List<InspectionItem> items;
	
	public InspectionByWeight() {
	}
	
	/**
	 * 추후 삭제
	 * 
	 * @param invoiceId
	 * @param custCd
	 * @param custNm
	 * @param status
	 * @param boxWeightList
	 * @param errorRangeList
	 * @param items
	 */
	public InspectionByWeight(String invoiceId, 
			String custCd, 
			String custNm, 
			String status, 
			List<Float> boxWeightList, 
			List<Float> errorRangeList, 
			List<InspectionItem> items) {
		
		this.invoiceId = invoiceId;
		this.custCd = custCd;
		this.custNm = custNm;
		this.status = status;
		this.boxWeightList = boxWeightList;
		this.errorRangeList = errorRangeList;
		this.setItems(items);
	}
	
	/**
	 * DAS, DPS 모두 포함한 검수 생성자
	 * 
	 * @param orderId
	 * @param boxId
	 * @param invoiceId
	 * @param custCd
	 * @param custNm
	 * @param status
	 * @param boxWeightList
	 * @param errorRangeList
	 * @param items
	 */
	public InspectionByWeight(String orderId, 
			String boxId, 
			String invoiceId, 
			String custCd, 
			String custNm, 
			String status, 
			String custOrderId,
			String jobType,
			List<Float> boxWeightList, 
			List<Float> errorRangeList, 
			List<InspectionItem> items) {
		
		this.orderId = orderId;
		this.boxId = boxId;
		this.invoiceId = invoiceId;
		this.custCd = custCd;
		this.custCd = custNm;
		this.status = status;
		this.jobType = jobType;
		this.custOrderId = custOrderId;
		this.boxWeightList = boxWeightList;
		this.errorRangeList = errorRangeList;
		this.setItems(items);
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getExpectedWeight() {
		return expectedWeight;
	}

	public void setExpectedWeight(Float expectedWeight) {
		this.expectedWeight = expectedWeight;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(String custOrderId) {
		this.custOrderId = custOrderId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Float getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Float minWeight) {
		this.minWeight = minWeight;
	}

	public Float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public List<Float> getBoxWeightList() {
		return boxWeightList;
	}

	public void setBoxWeightList(List<Float> boxWeightList) {
		this.boxWeightList = boxWeightList;
	}

	public List<Float> getErrorRangeList() {
		return errorRangeList;
	}

	public void setErrorRangeList(List<Float> errorRangeList) {
		this.errorRangeList = errorRangeList;
	}

	public List<InspectionItem> getItems() {
		return items;
	}

	public void setItems(List<InspectionItem> items) {
		this.items = items;
		
		if(ValueUtil.isNotEmpty(this.items)) {
			this.expectedWeight = 0f;
			for(InspectionItem item : items) {
				this.expectedWeight += item.getTotalWeight();
			}
		}
	}
	
	public void addItem(String skuCd, String skuNm, String skuBarcd, Integer itemQty, Float weight) {
		if(this.items == null) {
			this.items = new ArrayList<InspectionItem>();
		}
		
		this.items.add(new InspectionItem(skuCd, skuNm, skuBarcd, itemQty, weight));
	}
	
}
