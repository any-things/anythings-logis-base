package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

/**
 * 재고 관리
 * 
 * @author shortstop
 */
@Table(name = "stocks", idStrategy = GenerationRule.UUID, uniqueFields="domainId,equipType,equipCd,cellCd,binIndex,comCd,skuCd", indexes = {
	@Index(name = "ix_stocks_0", columnList = "domain_id,equip_type,equip_cd,cell_cd,bin_index,com_cd,sku_cd", unique = true),
	@Index(name = "ix_stocks_1", columnList = "domain_id,cell_cd")
})
public class Stock extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 196960417590117264L;
	
	/**
	 * 트랜잭션 - 재고 생성 (create) 
	 */
	public static final String TRX_CREATE = "create";
	/**
	 * 트랜잭션 - 재고 투입 (in) 
	 */
	public static final String TRX_IN = "in";
	/**
	 * 트랜잭션 - 재고 빼기 (out) 
	 */
	public static final String TRX_OUT = "out";
	/**
	 * 트랜잭션 - 재고 조정 (adjust) 
	 */
	public static final String TRX_ADJUST = "adjust";
	/**
	 * 트랜잭션 - 재고 보충 (supply) 
	 */
	public static final String TRX_SUPPLEMENT = "supply";
	/**
	 * 트랜잭션 - 피킹 (pick) 
	 */
	public static final String TRX_PICK = "pick";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "equip_type", nullable = false, length = 20)
	private String equipType;

	@Column(name = "equip_cd", nullable = false, length = 30)
	private String equipCd;

	@Column (name = "cell_cd", nullable = false, length = 30)
	private String cellCd;
	
	@Column (name = "bin_index", length = 5)
	private Integer binIndex;

	@Column (name = "com_cd", length = 30)
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
	
	@Column (name = "last_tran_cd", length = 30)
	private String lastTranCd;

	@Column (name = "status", length = 10)
	private String status;
	
	@Ignore
	private Integer prevStockQty;
	
	@Ignore
	private Integer prevLoadQty;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getEquipCd() {
		return equipCd;
	}

	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
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
		this.prevStockQty = this.stockQty;
		this.stockQty = stockQty;
	}

	public Integer getLoadQty() {
		return loadQty;
	}

	public void setLoadQty(Integer loadQty) {
		this.prevLoadQty = this.loadQty;
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

	public String getLastTranCd() {
		return lastTranCd;
	}

	public void setLastTranCd(String lastTranCd) {
		this.lastTranCd = lastTranCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public void beforeCreate() {
		super.beforeCreate();
		
		if(binIndex == null || binIndex == 0) {
			binIndex = 1;
		}
	}
	
	@Override
	public void afterCreate() {
		super.afterCreate();
		
		StockHist hist = new StockHist();
		hist.setCellCd(this.cellCd);
		hist.setBinIndex(this.binIndex);
		hist.setComCd(this.comCd);
		hist.setSkuCd(this.skuCd);
		hist.setTranCd(ValueUtil.isEmpty(this.lastTranCd) ? Stock.TRX_CREATE : this.lastTranCd);
		hist.setPrevStockQty(0);
		hist.setStockQty(this.stockQty);
		hist.setInQty(this.stockQty);
		BeanUtil.get(IQueryManager.class).insert(hist);
	}

	@Override
	public void beforeUpdate() {
		super.beforeUpdate();
		
		if(binIndex == null || binIndex == 0) {
			binIndex = 1;
		}
	}

	@Override
	public void afterUpdate() {
		super.afterUpdate();
		
		StockHist hist = new StockHist();
		hist.setCellCd(this.cellCd);
		hist.setBinIndex(this.binIndex);
		hist.setComCd(this.comCd);
		hist.setSkuCd(this.skuCd);
		hist.setTranCd(this.lastTranCd);
		hist.setPrevStockQty(this.prevStockQty);
		hist.setStockQty(this.stockQty);
		int inOutQty = ValueUtil.toInteger(this.stockQty, 0) - ValueUtil.toInteger(this.prevStockQty, 0);
		
		if(inOutQty > 0) {
			hist.setInQty(inOutQty);
		} else if(inOutQty < 0) {
			hist.setOutQty(inOutQty);
		}
		
		BeanUtil.get(IQueryManager.class).insert(hist);
	}

}
