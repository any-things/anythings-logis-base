package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "ind_config_set", idStrategy = GenerationRule.UUID, uniqueFields="domainId,comCd,jobType,equipCd,confSetCd", indexes = {
	@Index(name = "ix_ind_config_set_0", columnList = "domain_id,com_cd,job_type,equip_cd,conf_set_cd", unique = true)
})
public class IndConfigSet extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 665293314478940571L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "com_cd", length = 30)
	private String comCd;

	@Column (name = "job_type", length = 20)
	private String jobType;

	@Column (name = "equip_cd", length = 30)
	private String equipCd;

	@Column (name = "conf_set_cd", nullable = false, length = 30)
	private String confSetCd;

	@Column (name = "conf_set_nm", nullable = false, length = 100)
	private String confSetNm;

	@Column (name = "default_flag", length = 1)
	private Boolean defaultFlag;

	@Column (name = "remark", length = 1000)
	private String remark;
  
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getEquipCd() {
		return equipCd;
	}

	public void setEquipCd(String equipCd) {
		this.equipCd = equipCd;
	}

	public String getConfSetCd() {
		return confSetCd;
	}

	public void setConfSetCd(String confSetCd) {
		this.confSetCd = confSetCd;
	}

	public String getConfSetNm() {
		return confSetNm;
	}

	public void setConfSetNm(String confSetNm) {
		this.confSetNm = confSetNm;
	}

	public Boolean getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	
}
