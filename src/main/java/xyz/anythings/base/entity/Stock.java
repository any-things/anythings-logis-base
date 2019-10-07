package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "stocks", idStrategy = GenerationRule.UUID, uniqueFields="domainId,rackCd,cellCd,comCd,skuCd", indexes = {
	@Index(name = "ix_stocks_0", columnList = "domain_id,rack_cd,cell_cd,com_cd,sku_cd", unique = true)
})
public class Stock extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 196960417590117264L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "rack_cd", nullable = false, length = 30)
	private String rackCd;

	@Column (name = "cell_cd", nullable = false, length = 30)
	private String cellCd;
	
	@Column (name = "bin_index", length = 5)
	private Integer binIndex;

	@Column (name = "com_cd", nullable = false, length = 30)
	private String comCd;

	@Column (name = "sku_cd", length = 30)
	private String skuCd;

	@Column (name = "sku_nm", length = 200)
	private String skuNm;

	@Column (name = "stock_qty", length = 12)
	private Integer stockQty;

	@Column (name = "load_qty", length = 12)
	private Integer loadQty;

	@Column (name = "alloc_qty", length = 12)
	private Integer allocQty;

	@Column (name = "picked_qty", length = 12)
	private Integer pickedQty;

	@Column (name = "min_stock_qty", length = 12)
	private Integer minStockQty;

	@Column (name = "max_stock_qty", length = 12)
	private Integer maxStockQty;

	@Column (name = "fixed_flag", length = 1)
	private Boolean fixedFlag;

	@Column (name = "active_flag", length = 1)
	private Boolean activeFlag;
	
	@Column (name = "expired_at", length = 20)
	private String expiredAt;

	@Column (name = "status", length = 10)
	private String status;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRackCd() {
		return rackCd;
	}

	public void setRackCd(String rackCd) {
		this.rackCd = rackCd;
	}

	public String getCellCd() {
		return cellCd;
	}

	public void setCellCd(String cellCd) {
		this.cellCd = cellCd;
	}
	
	public Integer getBinIndex() {
		return binIndex;
	}

	public void setBinIndex(Integer binIndex) {
		this.binIndex = binIndex;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
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

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getLoadQty() {
		return loadQty;
	}

	public void setLoadQty(Integer loadQty) {
		this.loadQty = loadQty;
	}

	public Integer getAllocQty() {
		return allocQty;
	}

	public void setAllocQty(Integer allocQty) {
		this.allocQty = allocQty;
	}

	public Integer getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(Integer pickedQty) {
		this.pickedQty = pickedQty;
	}

	public Integer getMinStockQty() {
		return minStockQty;
	}

	public void setMinStockQty(Integer minStockQty) {
		this.minStockQty = minStockQty;
	}

	public Integer getMaxStockQty() {
		return maxStockQty;
	}

	public void setMaxStockQty(Integer maxStockQty) {
		this.maxStockQty = maxStockQty;
	}

	public Boolean getFixedFlag() {
		return fixedFlag;
	}

	public void setFixedFlag(Boolean fixedFlag) {
		this.fixedFlag = fixedFlag;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
