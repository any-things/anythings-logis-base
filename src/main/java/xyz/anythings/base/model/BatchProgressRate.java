package xyz.anythings.base.model;

import java.util.Map;

/**
 * 작업 배치 진행율
 * 
 * @author shortstop
 */
public class BatchProgressRate {
	/**
	 * ORDER 예정량
	 */
	private Integer planOrder;
	/**
	 * ORDER 처리량
	 */
	private Integer actualOrder;
	/**
	 * ORDER 진행율
	 */
	private Integer rateOrder;
	/**
	 * SKU 예정량
	 */
	private Integer planSku;
	/**
	 * SKU 처리량
	 */
	private Integer actualSku;
	/**
	 * SKU 진행율
	 */
	private Integer rateSku;
	/**
	 * PCS 예정량
	 */
	private Integer planPcs;
	/**
	 * PCS 처리량
	 */
	private Integer actualPcs;
	/**
	 * PCS 진행율
	 */
	private Integer ratePcs;
	
	public Integer getPlanOrder() {
		return planOrder;
	}
	
	public void setPlanOrder(Integer planOrder) {
		this.planOrder = planOrder;
	}
	
	public Integer getActualOrder() {
		return actualOrder;
	}
	
	public void setActualOrder(Integer actualOrder) {
		this.actualOrder = actualOrder;
	}
	
	public Integer getRateOrder() {
		return rateOrder;
	}
	
	public void setRateOrder(Integer rateOrder) {
		this.rateOrder = rateOrder;
	}
	
	public Integer getPlanSku() {
		return planSku;
	}
	
	public void setPlanSku(Integer planSku) {
		this.planSku = planSku;
	}
	
	public Integer getActualSku() {
		return actualSku;
	}
	
	public void setActualSku(Integer actualSku) {
		this.actualSku = actualSku;
	}
	
	public Integer getRateSku() {
		return rateSku;
	}
	
	public void setRateSku(Integer rateSku) {
		this.rateSku = rateSku;
	}
	
	public Integer getPlanPcs() {
		return planPcs;
	}
	
	public void setPlanPcs(Integer planPcs) {
		this.planPcs = planPcs;
	}
	
	public Integer getActualPcs() {
		return actualPcs;
	}
	
	public void setActualPcs(Integer actualPcs) {
		this.actualPcs = actualPcs;
	}
	
	public Integer getRatePcs() {
		return ratePcs;
	}
	
	public void setRatePcs(Integer ratePcs) {
		this.ratePcs = ratePcs;
	}

	public void parseResult(Map<?, ?> progress) {
		// TODO 결과 셋 파싱 -> 값 설정
	}

}
