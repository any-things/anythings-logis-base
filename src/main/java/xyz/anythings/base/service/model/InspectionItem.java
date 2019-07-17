package xyz.anythings.base.service.model;

import xyz.anythings.base.entity.SKU;

/**
 * 검수 항목 
 * 
 * @author shortstop
 */
public class InspectionItem {
	/**
	 * 상품 코드 
	 */
	private String skuCd;
	/**
	 * 상품 명 
	 */
	private String skuNm;
	/**
	 * 상품 바코드 
	 */
	private String skuBarcd;
	/**
	 * 상품 수량 
	 */
	private Integer itemQty;
	/**
	 * 검수 확인 수량
	 */
	private Integer confirmQty;
	/**
	 * 상품 중량 
	 */
	private Float weight;
	/**
	 * 상품 총 중량
	 */
	private Float totalWeight;
	
	public InspectionItem() {
	}
	
	public InspectionItem(String skuCd, String skuNm, String skuBarcd, Integer itemQty, Float weight) {
		this.skuCd = skuCd;
		this.skuNm = skuNm;
		this.skuBarcd = skuBarcd;
		this.itemQty = itemQty;
		this.setWeight(weight);
	}
	
	public InspectionItem(String skuCd, String skuNm, String skuBarcd, Integer itemQty) {
		this.skuCd = skuCd;
		this.skuNm = skuNm;
		this.skuBarcd = skuBarcd;
		this.itemQty = itemQty;
	}
	
	public InspectionItem(SKU sku, Integer itemQty) {
		this.skuCd = sku.getSkuCd();
		this.skuNm = sku.getSkuNm();
		this.skuBarcd = sku.getSkuBarcd();
		this.itemQty = itemQty;
	}	

	public String getSkuCd() {
		return skuCd;
	}

	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}

	public String getSkuNm() {
		return skuNm;
	}

	public void setSkuNm(String skuNm) {
		this.skuNm = skuNm;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	public Integer getItemQty() {
		return itemQty;
	}

	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

	public Integer getConfirmQty() {
		return confirmQty;
	}

	public void setConfirmQty(Integer confirmQty) {
		this.confirmQty = confirmQty;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getTotalWeight() {
		if(this.totalWeight == null || this.totalWeight == 0f) {
			this.totalWeight = (this.itemQty == null || this.weight == null || this.itemQty == 0 || this.weight == 0f) ? 0f : this.itemQty * this.weight;
		}
		
		return totalWeight;
	}

	public void setTotalWeight(Float totalWeight) {
		this.totalWeight = totalWeight;
	}
	
}
