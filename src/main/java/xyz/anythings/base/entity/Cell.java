package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "cells", idStrategy = GenerationRule.UUID, uniqueFields="domainId,cellCd", indexes = {
	@Index(name = "ix_cells_0", columnList = "domain_id,cell_cd", unique = true)
})
public class Cell extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 838774393921017730L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "rack_cd", nullable = false, length = 30)
	private String rackCd;

	@Column (name = "cell_nm", length = 40)
	private String cellNm;

	@Column (name = "cell_cd", nullable = false, length = 30)
	private String cellCd;

	@Column (name = "wms_cell_cd", length = 30)
	private String wmsCellCd;
	
	@Column (name = "bin_count", length = 12)
	private Integer binCount;

	@Column (name = "station_cd", length = 30)
	private String stationCd;

	@Column (name = "equip_zone", length = 30)
	private String equipZone;

	@Column (name = "ind_cd", length = 30)
	private String indCd;

	@Column (name = "channel_no", length = 40)
	private String channelNo;

	@Column (name = "side_cd", length = 30)
	private String sideCd;

	@Column (name = "cell_seq", length = 12)
	private Integer cellSeq;

	@Column (name = "printer_cd", length = 30)
	private String printerCd;

	@Column (name = "active_flag", length = 1)
	private Boolean activeFlag;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRackCd() {
		return rackCd;
	}

	public void setRackCd(String rackCd) {
		this.rackCd = rackCd;
	}

	public String getCellNm() {
		return cellNm;
	}

	public void setCellNm(String cellNm) {
		this.cellNm = cellNm;
	}

	public String getCellCd() {
		return cellCd;
	}

	public void setCellCd(String cellCd) {
		this.cellCd = cellCd;
	}

	public String getWmsCellCd() {
		return wmsCellCd;
	}

	public void setWmsCellCd(String wmsCellCd) {
		this.wmsCellCd = wmsCellCd;
	}

	public Integer getBinCount() {
		return binCount;
	}

	public void setBinCount(Integer binCount) {
		this.binCount = binCount;
	}

	public String getStationCd() {
		return stationCd;
	}

	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	public String getEquipZone() {
		return equipZone;
	}

	public void setEquipZone(String equipZone) {
		this.equipZone = equipZone;
	}

	public String getIndCd() {
		return indCd;
	}

	public void setIndCd(String indCd) {
		this.indCd = indCd;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getSideCd() {
		return sideCd;
	}

	public void setSideCd(String sideCd) {
		this.sideCd = sideCd;
	}

	public Integer getCellSeq() {
		return cellSeq;
	}

	public void setCellSeq(Integer cellSeq) {
		this.cellSeq = cellSeq;
	}

	public String getPrinterCd() {
		return printerCd;
	}

	public void setPrinterCd(String printerCd) {
		this.printerCd = printerCd;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}	
}
