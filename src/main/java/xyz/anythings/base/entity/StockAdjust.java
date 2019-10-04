package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "stock_adjusts", idStrategy = GenerationRule.UUID)
public class StockAdjust extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 165727410692846478L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "stocktaking_id", nullable = false, length = 40)
	private String stocktakingId;

	@Column (name = "rack_cd", nullable = false, length = 30)
	private String rackCd;

	@Column (name = "cell_cd", nullable = false, length = 30)
	private String cellCd;

	@Column (name = "com_cd", nullable = false, length = 30)
	private String comCd;

	@Column (name = "sku_cd", nullable = false, length = 30)
	private String skuCd;

	@Column (name = "prev_stock_qty", nullable = false, length = 12)
	private Integer prevStockQty;

	@Column (name = "after_stock_qty", nullable = false, length = 12)
	private Integer afterStockQty;

	@Column (name = "adjust_qty", length = 12)
	private Integer adjustQty;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStocktakingId() {
		return stocktakingId;
	}

	public void setStocktakingId(String stocktakingId) {
		this.stocktakingId = stocktakingId;
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

	public Integer getPrevStockQty() {
		return prevStockQty;
	}

	public void setPrevStockQty(Integer prevStockQty) {
		this.prevStockQty = prevStockQty;
	}

	public Integer getAfterStockQty() {
		return afterStockQty;
	}

	public void setAfterStockQty(Integer afterStockQty) {
		this.afterStockQty = afterStockQty;
	}

	public Integer getAdjustQty() {
		return adjustQty;
	}

	public void setAdjustQty(Integer adjustQty) {
		this.adjustQty = adjustQty;
	}	
}
