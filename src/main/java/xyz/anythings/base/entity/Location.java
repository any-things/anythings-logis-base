package xyz.anythings.base.entity;

import java.util.List;
import java.util.Map;

import xyz.anythings.sys.util.OrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.exception.server.ElidomValidationException;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_location", idStrategy = GenerationRule.UUID, uniqueFields = "domainId,locCd", indexes = {
	@Index(name = "ix_tb_location_0", columnList = "loc_cd,domain_id", unique = true),
	@Index(name = "ix_tb_location_1", columnList = "mpi_cd"),
	@Index(name = "ix_tb_location_2", columnList = "region_cd"),
	@Index(name = "ix_tb_location_3", columnList = "sku_cd"),
	@Index(name = "ix_tb_location_4", columnList = "cust_cd")
})
public class Location extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 183773553369683014L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "loc_cd", nullable = false, length = 30)
	private String locCd;

	@Column(name = "mpi_cd", length = 30)
	private String mpiCd;

	@Column(name = "channel_no", length = 40)
	private String channelNo;

	@Column(name = "pan_no", length = 40)
	private String panNo;

	@Column(name = "region_cd", length = 30)
	private String regionCd;

	@Column(name = "side_cd", length = 30)
	private String sideCd;

	@Column(name = "loc_seq", length = 10)
	private Integer locSeq;

	@Column(name = "zone_cd", length = 30)
	private String zoneCd;

	@Column(name = "zone_seq", length = 10)
	private Integer zoneSeq;

	@Column(name = "equip_zone_cd", length = 30)
	private String equipZoneCd;

	@Column(name = "gw_zone_cd", length = 30)
	private String gwZoneCd;

	@Column(name = "printer_cd", length = 30)
	private String printerCd;

	@Column(name = "com_cd", length = 32)
	private String comCd;
	
	@Column(name = "cust_cd", length = 32)
	private String custCd;

	@Column(name = "sku_cd", length = 50)
	private String skuCd;

	@Column(name = "box_id", length = 40)
	private String boxId;

	@Column(name = "min_stock_qty", length = 19)
	private Integer minStockQty;
	
	@Column(name = "max_stock_qty", length = 19)
	private Integer maxStockQty;

	@Column(name = "fixed_flag", length = 1)
	private Boolean fixedFlag;

	@Column(name = "active_flag", length = 1)
	private Boolean activeFlag;

	@Column(name = "assort_seq", length = 10)
	private Integer assortSeq;

	@Column(name = "job_process_id", length = 40)
	private String jobProcessId;

	@Column(name = "job_status", length = 10)
	private String jobStatus;
	
	@Column(name = "div_cd", length = 10)
	private String divCd;
	
	@Column(name = "wms_stage", length = 30)
	private String wmsStage;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getSideCd() {
		return sideCd;
	}

	public void setSideCd(String sideCd) {
		this.sideCd = sideCd;
	}

	public String getDivCd() {
		return divCd;
	}
	
	public void setDivCd(String divCd) {
		this.divCd = divCd;
	}
	
	public Integer getLocSeq() {
		return locSeq;
	}

	public void setLocSeq(Integer locSeq) {
		this.locSeq = locSeq;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public Integer getZoneSeq() {
		return zoneSeq;
	}

	public void setZoneSeq(Integer zoneSeq) {
		this.zoneSeq = zoneSeq;
	}

	public String getEquipZoneCd() {
		return equipZoneCd;
	}

	public void setEquipZoneCd(String equipZoneCd) {
		this.equipZoneCd = equipZoneCd;
	}

	public String getGwZoneCd() {
		return gwZoneCd;
	}

	public void setGwZoneCd(String gwZoneCd) {
		this.gwZoneCd = gwZoneCd;
	}

	public String getPrinterCd() {
		return printerCd;
	}

	public void setPrinterCd(String printerCd) {
		this.printerCd = printerCd;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
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

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public Integer getMinStockQty() {
		return minStockQty;
	}

	public void setMinStockQty(Integer minStockQty) {
		this.minStockQty = minStockQty;
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

	public Integer getAssortSeq() {
		return assortSeq;
	}

	public void setAssortSeq(Integer assortSeq) {
		this.assortSeq = assortSeq;
	}

	public String getJobProcessId() {
		return jobProcessId;
	}

	public void setJobProcessId(String jobProcessId) {
		this.jobProcessId = jobProcessId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Integer getMaxStockQty() {
		return maxStockQty;
	}

	public void setMaxStockQty(Integer maxStockQty) {
		this.maxStockQty = maxStockQty;
	}


	public String getWmsStage() {
		return wmsStage;
	}

	public void setWmsStage(String wmsStage) {
		this.wmsStage = wmsStage;
	}

	/**
	 * 호기별 MPI 검색
	 *
	 * @param domainId
	 * @param regionCd
	 * @return
	 */
	public static List<String> searchMpiByRegion(Long domainId, String regionCd) {
		String sql = "select mpi_cd from tb_location where domain_id = :domainId and region_cd = :regionCd";
		Map<String, Object> params = ValueUtil.newMap("domainId,regionCd", domainId, regionCd);
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql, params, String.class, 0, 0);
	}

	/**
	 * 활성화된 로케이션 정보 조회
	 *
	 * @param domainId
	 * @param locCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Location findByLocCdWithLock(Long domainId, String locCd, boolean exceptionWhenEmpty) {
		Query condition = OrmUtil.newConditionForExecution(domainId);
		condition.addFilter("locCd", locCd);
		Location loc = BeanUtil.get(IQueryManager.class).selectByConditionWithLock(Location.class, condition);

		if(exceptionWhenEmpty && loc == null) {
			ThrowUtil.newNotFoundRecord("terms.menu.Location", locCd);
		}

		return loc;
	}
	
	/**
	 * 활성화된 로케이션 정보 조회
	 *
	 * @param domainId
	 * @param locCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Location findByLocCd(Long domainId, String locCd, boolean exceptionWhenEmpty) {
		Query condition = OrmUtil.newConditionForExecution(domainId);
		condition.addFilter("locCd", locCd);
		Location loc = BeanUtil.get(IQueryManager.class).selectByCondition(Location.class, condition);

		if(exceptionWhenEmpty && loc == null) {
			//ThrowUtil.newNotFoundRecord("terms.menu.Location", locCd);
			throw new ElidomValidationException("로케이션 코드[" + locCd + "]가 존재하지 않습니다.");
		}

		return loc;
	}

	/**
	 * 앞/뒤 매치 되는 로케이션 정보 조회
	 *
	 * @param domainId
	 * @param locCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Location findByMatchLocCd(Long domainId, String locCd, boolean exceptionWhenEmpty) {
		Location sourceLoc = Location.findByLocCd(domainId, locCd, exceptionWhenEmpty);

		String qry = "select * from tb_location where domain_id = :domainId and region_cd = :regionCd and substr(loc_cd, -2) = substr(:locCd , -2) and side_cd != :sideCd";
		Map<String, Object> params = ValueUtil.newMap("domainId,regionCd,locCd,sideCd", domainId, sourceLoc.getRegionCd(), sourceLoc.getLocCd(), sourceLoc.getSideCd());
		Location loc = BeanUtil.get(IQueryManager.class).selectBySql(qry, params, Location.class);

		if(exceptionWhenEmpty && loc == null) {
			ThrowUtil.newNotFoundRecord("terms.menu.Location", locCd);
		}

		return loc;
	}

	/**
	 * 활성화된 로케이션 정보 조회
	 *
	 * @param domainId
	 * @param locCd
	 * @return
	 */
	public static Location findEnabledLoc(Long domainId, String locCd) {
		Location loc = findByLocCd(domainId, locCd, true);

		if(!loc.getActiveFlag()) {
			throw new ElidomRuntimeException("로케이션이 비활성화 상태입니다");
		}

		return loc;
	}

	/**
	 * MPI 코드로 활성화된 로케이션 정보 조회
	 *
	 * @param domainId
	 * @param mpiCd
	 * @param withLock
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Location findByMpiCd(Long domainId, String mpiCd, boolean withLock, boolean exceptionWhenEmpty) {
		Query condition = OrmUtil.newConditionForExecution(domainId);
		condition.addFilter("mpiCd", mpiCd);
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		Location loc = withLock ? 
				queryMgr.selectByConditionWithLock(Location.class, condition) : 
				queryMgr.selectByCondition(Location.class, condition);

		if(loc == null && exceptionWhenEmpty) {
			ThrowUtil.newNotFoundRecord("terms.menu.Location", mpiCd);
		}

		if(loc != null && !loc.getActiveFlag() && exceptionWhenEmpty) {
			throw new ElidomRuntimeException("Location [" + mpiCd + "] is disabled!");
		}

		return loc;
	}

}
