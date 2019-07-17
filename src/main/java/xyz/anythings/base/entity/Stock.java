package xyz.anythings.base.entity;

import java.util.Map;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_stock", idStrategy = GenerationRule.UUID, uniqueFields="domainId,regionCd,locCd", indexes = {
	@Index(name = "ix_tb_stock_0", columnList = "loc_cd,region_cd,domain_id", unique = true),
	@Index(name = "ix_tb_stock_1", columnList = "loc_cd,sku_cd")
})
public class Stock extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 327900178634324745L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "region_cd", nullable = false, length = 30)
	private String regionCd;

	@Column (name = "loc_cd", nullable = false, length = 30)
	private String locCd;

	@Column (name = "sku_cd", length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column (name = "box_barcd", length = 60)
	private String boxBarcd;

	@Column (name = "stock_qty", length = 19)
	private Integer stockQty;

	@Column (name = "load_qty", length = 19)
	private Integer loadQty;

	@Column (name = "alloc_qty", length = 19)
	private Integer allocQty;

	@Column (name = "picked_qty", length = 19)
	private Integer pickedQty;

	@Column (name = "led_bar_on")
	private Boolean ledBarOn;

	@Ignore
	private Integer inputQty;
	
	@Ignore
	private String skuBarcd;
	
	@Ignore
	private Boolean fixedFlag;
	
	@Ignore
	private Integer maxStockQty;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getBoxBarcd() {
		return boxBarcd;
	}

	public void setBoxBarcd(String boxBarcd) {
		this.boxBarcd = boxBarcd;
	}

	public Integer getStockQty() {
		this.stockQty = (this.loadQty == null ? 0 : this.loadQty ) + (this.allocQty == null ? 0 : this.allocQty);
		return this.stockQty;
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

	public Integer getInputQty() {
		return inputQty;
	}

	public void setInputQty(Integer inputQty) {
		this.inputQty = inputQty;
	}

	public Boolean getLedBarOn() {
		return ledBarOn;
	}

	public void setLedBarOn(Boolean ledBarOn) {
		this.ledBarOn = ledBarOn;
	}

	public Boolean getFixedFlag() {
		return fixedFlag;
	}

	public void setFixedFlag(Boolean fixedFlag) {
		this.fixedFlag = fixedFlag;
	}

	public Integer getMaxStockQty() {
		return maxStockQty;
	}

	public void setMaxStockQty(Integer maxStockQty) {
		this.maxStockQty = maxStockQty;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	/**
	 * 로케이션 코드로 재고 조회
	 *
	 * @param domainId
	 * @param locCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Stock findByLocCd(Long domainId, String locCd, boolean exceptionWhenEmpty) {
		Query query = AnyOrmUtil.newConditionForExecution(domainId);
		query.addFilter("locCd", locCd);
		Stock stock = BeanUtil.get(IQueryManager.class).selectByCondition(Stock.class, query);

		if(stock == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Stock", locCd);
		}

		return stock;
	}

	/**
	 * 표시기 코드로 재고 조회
	 *
	 * @param domainId
	 * @param mpiCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Stock findByMpiCd(Long domainId, String mpiCd, boolean exceptionWhenEmpty) {
		String sql = "select * from tb_stock where domain_id = :domainId and loc_cd = (select loc_cd from tb_location where domain_id = :domainId and mpi_cd = :mpiCd)";
		Stock stock = BeanUtil.get(IQueryManager.class).selectBySql(sql, ValueUtil.newMap("domainId,mpiCd", domainId, mpiCd), Stock.class);

		if(stock == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Stock", mpiCd);
		}

		return stock;
	}

	/**
	 * 재고 업데이트
	 *
	 * @param domainId
	 * @param comCd
	 * @param locCd
	 * @param skuCd
	 * @param pickedQty
	 */
	public static void updateStock(Long domainId, String comCd, String locCd, String skuCd, int pickedQty) {
		// 1. 재고 조정 stock의 alloc_qty를 빼주고 picked_qty를 더해줌, TODO Lock을 걸어서 동시 처리를 제어해야 함
		String sql = "update tb_stock set updated_at = sysdate, alloc_qty = alloc_qty - :pickedQty, picked_qty = nvl(picked_qty, 0) + :pickedQty where domain_id = :domainId and com_cd = :comCd and loc_cd = :locCd and sku_cd = :skuCd";
		Map<String, Object> params = ValueUtil.newMap("domainId,comCd,locCd,skuCd,pickedQty", domainId, comCd, locCd, skuCd, pickedQty);
		BeanUtil.get(IQueryManager.class).executeBySql(sql, params);

		// 2. TODO 재고 이력 관리
	}

}
