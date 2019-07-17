package xyz.anythings.base.service.model;

/**
 * 주문 가공 상태 정보 
 * 
 * @author shortstop
 */
public class PreprocessStatus {
	/**
	 * 거래처 코드 
	 */
	private String custCd;
	/**
	 * 상품 코드 
	 */
	private String skuCd;
	/**
	 * 호기 코드 
	 */
	private String regionCd;
	/**
	 * 주문 상품 수량 
	 */
	private int orderSkuQty;
	/**
	 * 주문 수량 총 PCS 
	 */
	private int orderPcsQty;
	
	public PreprocessStatus() {
	}
	
	public PreprocessStatus(String custCd, String skuCd, String regionCd, Integer orderSkuQty, Integer orderPcsQty) {
		this.custCd = custCd;
		this.skuCd = skuCd;
		this.regionCd = regionCd;
		this.orderSkuQty = orderSkuQty;
		this.orderPcsQty = orderPcsQty;
	}
	
	public String getCustCd() {
		return custCd;
	}
	
	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}
	
	public String getSkuCd() {
		return skuCd;
	}
	
	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}
	
	public String getRegionCd() {
		return regionCd;
	}
	
	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	
	public int getOrderSkuQty() {
		return orderSkuQty;
	}
	
	public void setOrderSkuQty(int orderSkuQty) {
		this.orderSkuQty = orderSkuQty;
	}
	
	public Integer getOrderPcsQty() {
		return orderPcsQty;
	}
	
	public void setOrderPcsQty(Integer orderPcsQty) {
		this.orderPcsQty = orderPcsQty;
	}
	
}
