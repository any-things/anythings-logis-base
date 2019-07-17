package xyz.anythings.base.entity;

import java.util.List;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_region", idStrategy = GenerationRule.UUID, uniqueFields = "domainId,regionCd", indexes = {
	@Index(name = "ix_tb_region_0", columnList = "region_cd,domain_id", unique = true),
	@Index(name = "ix_tb_region_1", columnList = "job_type,domain_id"),
	@Index(name = "ix_tb_region_2", columnList = "batch_id"),
	@Index(name = "ix_tb_region_3", columnList = "chute_no,domain_id")
})
public class Region extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	
	/**
	 * 기타 유형은 각 사이트 필요에 따라 설정한다. 
	 */
	/**
	 * 호기 유형 - ONE SKU
	 */
	public static final String TYPE_ONE_SKU = "U";
	/**
	 * 호기 유형 - 단수,단포
	 */
	public static final String TYPE_SINGLE = "O";

	
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 187607272318671620L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "region_cd", nullable = false, length = 30)
	private String regionCd;

	@Column(name = "region_nm", nullable = false, length = 100)
	private String regionNm;

	@Column(name = "relay_seq", length = 10)
	private Integer relaySeq;

	@Column(name = "job_type", length = 20)
	private String jobType;

	@Column(name = "assign_loc_type", length = 20)
	private String assignLocType;

	@Column(name = "region_type", length = 20)
	private String regionType;

	@Column(name = "virtual_flag", length = 1)
	private Boolean virtualFlag;
	
	@Column(name = "batch_id", length = 40)
	private String batchId;
	
	@Column(name = "clone_group_id", length = 40)
	private String cloneGroupId;
	
	@Column(name = "sorter_id", length = 40)
	private String sorterId;
	
	@Column(name = "chute_no", length = 40)
	private String chuteNo;
	
	@Column(name = "region_setting", length = 100)
	private String regionSetting;
	
	@Column(name = "max_box_inline_qty", length = 10)
	private Integer maxBoxInlineQty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getRegionNm() {
		return regionNm;
	}

	public void setRegionNm(String regionNm) {
		this.regionNm = regionNm;
	}

	public Integer getRelaySeq() {
		return relaySeq;
	}

	public void setRelaySeq(Integer relaySeq) {
		this.relaySeq = relaySeq;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getAssignLocType() {
		return assignLocType;
	}

	public void setAssignLocType(String assignLocType) {
		this.assignLocType = assignLocType;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public Boolean getVirtualFlag() {
		return virtualFlag;
	}

	public void setVirtualFlag(Boolean virtualFlag) {
		this.virtualFlag = virtualFlag;
	}
	
	public String getSorterId() {
		return sorterId;
	}

	public void setSorterId(String sorterId) {
		this.sorterId = sorterId;
	}

	public String getChuteNo() {
		return chuteNo;
	}

	public void setChuteNo(String chuteNo) {
		this.chuteNo = chuteNo;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getCloneGroupId() {
		return cloneGroupId;
	}

	public void setCloneGroupId(String cloneGroupId) {
		this.cloneGroupId = cloneGroupId;
	}

	public String getRegionSetting() {
		return regionSetting;
	}

	public void setRegionSetting(String regionSetting) {
		this.regionSetting = regionSetting;
	}

	public Integer getMaxBoxInlineQty() {
		return maxBoxInlineQty;
	}

	public void setMaxBoxInlineQty(Integer maxBoxInlineQty) {
		this.maxBoxInlineQty = maxBoxInlineQty;
	}

	/**
	 * 호기에서 사용 가능한 로케이션 (셀) 개수 
	 * 
	 * @return
	 */
	public int validLocationCount() {
		Location condition = new Location();
		condition.setDomainId(this.domainId);
		condition.setRegionCd(this.regionCd);
		condition.setActiveFlag(true);
		return BeanUtil.get(IQueryManager.class).selectSize(Location.class, condition);
	}
	
	/**
	 * 병렬 지원 여부
	 * 
	 * @return
	 */
	public boolean isPararellSupport() {
		return ValueUtil.isEqualIgnoreCase("P", this.regionType);
	}
	
	/**
	 * regionCd로 호기를 조회 
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Region findByRegionCd(Long domainId, String regionCd, boolean exceptionWhenEmpty) {
		Query query = AnyOrmUtil.newConditionForExecution(domainId);
		Region region = null;
		
		if(ValueUtil.isNotEqual(regionCd, "NA")) {
			query.addFilter("regionCd", regionCd);
			region = BeanUtil.get(IQueryManager.class).selectByCondition(Region.class, query);
		} else {
			query.setPageIndex(1);
			query.setPageSize(1);
			List<Region> regionList = BeanUtil.get(IQueryManager.class).selectList(Region.class, query);
			region = regionList.isEmpty() ? null : regionList.get(0);
		}
		
		if(region == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Region", regionCd);
		}
		
		return region;
	}
	
	/**
	 * chuteNo로 호기 코드를 찾아 리턴
	 * 
	 * @param domainId
	 * @param chuteNo
	 * @return
	 */
	public static Region findRegionByChuteNo(Long domainId, String chuteNo) {
		Query query = AnyOrmUtil.newConditionForExecution(domainId);
		query.addFilter("chuteNo", chuteNo);
		return BeanUtil.get(IQueryManager.class).selectByCondition(Region.class, query);
	}
	
	/**
	 * 호기 코드로 부터 슈트 번호를 찾아 리턴
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static String findChuteNoByRegionCd(Long domainId, String regionCd, boolean exceptionWhenEmpty) {
		Query query = AnyOrmUtil.newConditionForExecution(domainId, 1, 1, "chute_no");
		query.addFilter("regionCd", regionCd);
		Region region = BeanUtil.get(IQueryManager.class).selectByCondition(Region.class, query);
		
		if(region == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Region", regionCd);
		}
		
		return region.getChuteNo();	
	}

	/**
	 * 락을 걸고 호기 조회
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Region findWithLock(Long domainId, String regionCd, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("regionCd", regionCd);
		Region region = BeanUtil.get(IQueryManager.class).selectByConditionWithLock(Region.class, condition);

		if(exceptionWhenEmpty && region == null) {
			ThrowUtil.newNotFoundRecord("terms.menu.Region", regionCd);
		}

		return region;
	}
}
