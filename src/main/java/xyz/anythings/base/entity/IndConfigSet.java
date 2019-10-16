package xyz.anythings.base.entity;

import java.util.List;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.util.ValueUtil;

/**
 * 표시기 설정 셋
 * 
 * @author shortstop
 */
@Table(name = "ind_config_set", idStrategy = GenerationRule.UUID, uniqueFields="domainId,comCd,stageCd,jobType,equipType,equipCd,confSetCd", indexes = {
	@Index(name = "ix_ind_config_set_0", columnList = "domain_id,com_cd,stage_cd,job_type,equip_type,equip_cd,conf_set_cd", unique = true)
})
public class IndConfigSet extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 854265517216781150L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "com_cd", length = 30)
	private String comCd;

	@Column (name = "stage_cd", length = 30)
	private String stageCd;

	@Column (name = "job_type", length = 20)
	private String jobType;

	@Column (name = "equip_type", length = 20)
	private String equipType;

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
	
	@Ignore
	private List<IndConfig> items;
  
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

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public List<IndConfig> getItems() {
		return items;
	}

	public void setItems(List<IndConfig> items) {
		this.items = items;
	}
	
	/**
	 * key로 값을 찾아 리턴
	 * 
	 * @param key
	 * @return
	 */
	public String findValue(String key) {
		if(ValueUtil.isEmpty(this.items)) {
			for(IndConfig item : items) {
				if(ValueUtil.isEqualIgnoreCase(key, item.getName())) {
					return item.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * key로 값을 찾아 리턴
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String findValue(String key, String defaultValue) {
		String value = this.findValue(key);
		return ValueUtil.isEmpty(value) ? defaultValue : value;
	}

}
