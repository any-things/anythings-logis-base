package xyz.anythings.base.service.model;

import java.util.List;

import xyz.anythings.base.entity.Region;
import xyz.anythings.base.entity.SKU;
import xyz.elidom.sys.util.ValueUtil;

/**
 * 중분류 모델 
 * 
 * @author shortstop
 */
public class MiddleClass {
	/**
	 * 상품 코드 
	 */
	private String skuCd;
	/**
	 * 상품 명 
	 */
	private String skuNm;
	/**
	 * 박스 입수 수량
	 */
	private Integer boxInQty;
	/**
	 * pallet 입수 박스 
	 */
	private Integer pltBoxQty;
	/**
	 * 상품 무게
	 */
	private Float skuWt;
	/**
	 * 호기별 분류 수량 정보 
	 */
	private List<Division> items;
	
	/**
	 * 기본 생성자 
	 */
	public MiddleClass() {
	}
	
	/**
	 * 중분류 생성자 
	 * 
	 * @param sku
	 * @param items
	 */
	public MiddleClass(SKU sku) {
		this.skuCd = sku.getSkuCd();
		this.skuNm = sku.getSkuNm();
		this.boxInQty = sku.getBoxInQty();
		this.skuWt = sku.getSkuWt();
		this.pltBoxQty = sku.getPltBoxQty();
	}
	
	/**
	 * 중분류 생성자 
	 * 
	 * @param sku
	 * @param items
	 */
	public MiddleClass(SKU sku, List<Division> items) {
		this.skuCd = sku.getSkuCd();
		this.skuNm = sku.getSkuNm();
		this.boxInQty = sku.getBoxInQty();
		this.skuWt = sku.getSkuWt();
		this.pltBoxQty = sku.getPltBoxQty();
		this.setItems(items);
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

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public Float getSkuWt() {
		return skuWt;
	}

	public void setSkuWt(Float skuWt) {
		this.skuWt = skuWt;
	}

	public List<Division> getItems() {
		return items;
	}

	public void setItems(List<Division> items) {
		this.items = items;
	}

	public Integer getPltBoxQty() {
		return pltBoxQty;
	}

	public void setPltBoxQty(Integer pltBoxQty) {
		this.pltBoxQty = pltBoxQty;
	}

	/**
	 * 중분류 결과를 호기 순으로 재배치 
	 * 
	 * @param sku
	 * @param regions
	 * @param divisions
	 * @param regSortAsc 
	 * @return
	 */
	public static MiddleClass arrangeMiddleClassing(SKU sku, List<Region> regions, List<Division> divisions, boolean regSortAsc) {
		MiddleClass middleClass = new MiddleClass(sku);
		
		for(Region region : regions) {
			boolean exist = false;
			
			for(Division division : divisions) {
				String dRegionCd = division.getRegionCd();
				if(dRegionCd != null && ValueUtil.isEqual(dRegionCd, region.getRegionCd())) {
					exist = true;
					break;
				}
			}
			
			if(!exist) {
				Division emptyDiv = new Division();
				emptyDiv.setRegionCd(region.getRegionCd());
				emptyDiv.setRegionNm(region.getRegionNm());
				divisions.add(emptyDiv);
			}
		}
		
		if(regSortAsc) {
			divisions.sort((m1, m2) -> { return m1.getRegionCd().compareTo(m2.getRegionCd()); });
		} else {
			divisions.sort((m1, m2) -> { return m2.getRegionCd().compareTo(m1.getRegionCd()); });
		}
		
		middleClass.setItems(divisions);
		return middleClass;
	}

}
