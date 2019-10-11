package xyz.anythings.base.model;

/**
 * 작업 배치 진행율
 * 
 * @author shortstop
 */
public class BatchProgressRate {

	/**
	 * ORDER 예정 / 실적 / 진행율
	 */
	private PlanActual pcs;
	/**
	 * SKU 예정 / 실적 / 진행율
	 */
	private PlanActual sku;
	/**
	 * PCS 예정 / 실적 / 진행율
	 */
	private PlanActual order;
	/**
	 * TOTAL 예정 / 실적 / 진행율
	 */
	private PlanActual total;
	
	public PlanActual getPcs() {
		return pcs;
	}
	
	public void setPcs(Integer plan, Integer actual, Integer rate) {
		this.pcs = new PlanActual(plan, actual, rate);
	}
	
	public void setPcs(PlanActual pcs) {
		this.pcs = pcs;
	}
	
	public PlanActual getSku() {
		return sku;
	}
	
	public void setSku(Integer plan, Integer actual, Integer rate) {
		this.sku = new PlanActual(plan, actual, rate);
	}
	
	public void setSku(PlanActual sku) {
		this.sku = sku;
	}
	
	public PlanActual getOrder() {
		return order;
	}
	
	public void setOrder(Integer plan, Integer actual, Integer rate) {
		this.order = new PlanActual(plan, actual, rate);
	}
	
	public void setOrder(PlanActual order) {
		this.order = order;
	}
	
	public PlanActual getTotal() {
		return total;
	}
	
	public void setTotal(PlanActual total) {
		this.total = total;
	}
	
	public void setTotal(Integer plan, Integer actual, Integer rate) {
		this.total = new PlanActual(plan, actual, rate);
	}
}
