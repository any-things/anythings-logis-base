package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "racks", idStrategy = GenerationRule.UUID, uniqueFields = "domainId,rackCd", indexes = {
		@Index(name = "ix_racks_0", columnList = "domain_id,rack_cd", unique = true),
		@Index(name = "ix_racks_1", columnList = "domain_id,stage_cd") })
public class Rack extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 201583884471917056L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "area_cd", length = 30)
	private String areaCd;

	@Column(name = "stage_cd", length = 30)
	private String stageCd;

	@Column(name = "equip_group", nullable = false, length = 30)
	private String equipGroup;

	@Column(name = "rack_cd", nullable = false, length = 30)
	private String rackCd;

	@Column(name = "rack_nm", nullable = false, length = 100)
	private String rackNm;

	@Column(name = "rack_type", length = 20)
	private String rackType;

	@Column(name = "sorter_cd", length = 30)
	private String sorterCd;

	@Column(name = "chute_no", length = 40)
	private String chuteNo;

	@Column(name = "job_type", length = 20)
	private String jobType;

	@Column(name = "batch_id", length = 40)
	private String batchId;

	@Column(name = "std_workers", length = 19)
	private Float stdWorkers;

	@Column(name = "std_uph", length = 19)
	private Float stdUph;

	@Column(name = "avg_uph", length = 19)
	private Float avgUph;

	@Column(name = "status", length = 10)
	private String status;

	@Column(name = "active_flag", length = 1)
	private Boolean activeFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}
	
	public String getEquipGroup() {
		return equipGroup;
	}
	
	public void setEquipGroup(String equipGroup) {
		this.equipGroup = equipGroup;
	}

	public String getRackCd() {
		return rackCd;
	}

	public void setRackCd(String rackCd) {
		this.rackCd = rackCd;
	}

	public String getRackNm() {
		return rackNm;
	}

	public void setRackNm(String rackNm) {
		this.rackNm = rackNm;
	}

	public String getRackType() {
		return rackType;
	}

	public void setRackType(String rackType) {
		this.rackType = rackType;
	}

	public String getSorterCd() {
		return sorterCd;
	}

	public void setSorterCd(String sorterCd) {
		this.sorterCd = sorterCd;
	}

	public String getChuteNo() {
		return chuteNo;
	}

	public void setChuteNo(String chuteNo) {
		this.chuteNo = chuteNo;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Float getStdWorkers() {
		return stdWorkers;
	}

	public void setStdWorkers(Float stdWorkers) {
		this.stdWorkers = stdWorkers;
	}

	public Float getStdUph() {
		return stdUph;
	}

	public void setStdUph(Float stdUph) {
		this.stdUph = stdUph;
	}

	public Float getAvgUph() {
		return avgUph;
	}

	public void setAvgUph(Float avgUph) {
		this.avgUph = avgUph;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
}
