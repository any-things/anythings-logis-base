package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "equip_groups", idStrategy = GenerationRule.UUID, uniqueFields="domainId,equipGroupCd", indexes = {
	@Index(name = "ix_equip_groups_0", columnList = "domain_id,equip_group_cd", unique = true)
})
public class EquipGroup extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 304444492646549193L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "equip_group_cd", length = 30)
	private String equipGroupCd;

	@Column (name = "equip_group_nm", length = 100)
	private String equipGroupNm;

	@Column (name = "equip_type", length = 20)
	private String equipType;

	@Column (name = "area_cd", length = 30)
	private String areaCd;
	
	@Column (name = "stage_cd", length = 30)
	private String stageCd;

	@Column (name = "category", length = 20)
	private String category;

	@Column (name = "remarks", length = 1000)
	private String remarks;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEquipGroupCd() {
		return equipGroupCd;
	}

	public void setEquipGroupCd(String equipGroupCd) {
		this.equipGroupCd = equipGroupCd;
	}

	public String getEquipGroupNm() {
		return equipGroupNm;
	}

	public void setEquipGroupNm(String equipGroupNm) {
		this.equipGroupNm = equipGroupNm;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
}
