package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_stock_adjustment", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_stock_adjustment_0", columnList = "stock_taking_id"),
	@Index(name = "ix_tb_stock_adjustment_1", columnList = "stock_taking_id,region_cd")
})
public class StockAdjustment extends xyz.elidom.orm.entity.basic.DomainCreateStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 198505344579076895L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "stock_taking_id", nullable = false, length = 40)
	private String stockTakingId;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "region_cd", nullable = false, length = 30)
	private String regionCd;

	@Column (name = "loc_cd", nullable = false, length = 30)
	private String locCd;

	@Column (name = "mpi_cd", nullable = false, length = 30)
	private String mpiCd;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column (name = "prev_stock_qty", nullable = false, length = 19)
	private Integer prevStockQty;

	@Column (name = "after_stock_qty", nullable = false, length = 19)
	private Integer afterStockQty;

	@Column (name = "adjustment_qty", length = 19)
	private Integer adjustmentQty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockTakingId() {
		return stockTakingId;
	}

	public void setStockTakingId(String stockTakingId) {
		this.stockTakingId = stockTakingId;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getMpiCd() {
		return mpiCd;
	}

	public void setMpiCd(String mpiCd) {
		this.mpiCd = mpiCd;
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

	public Integer getAdjustmentQty() {
		return adjustmentQty;
	}

	public void setAdjustmentQty(Integer adjustmentQty) {
		this.adjustmentQty = adjustmentQty;
	}
}
