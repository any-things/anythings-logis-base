package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "stations", idStrategy = GenerationRule.UUID, uniqueFields="domainId,rackCd,stationCd", indexes = {
	@Index(name = "ix_stations_0", columnList = "domain_id,rack_cd,station_cd", unique = true)
})
public class Station extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 230927217325229224L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "rack_cd", nullable = false, length = 30)
	private String rackCd;

	@Column (name = "station_cd", nullable = false, length = 30)
	private String stationCd;

	@Column (name = "station_nm", length = 40)
	private String stationNm;

	@Column (name = "station_type", length = 20)
	private String stationType;

	@Column (name = "printer_cd", length = 30)
	private String printerCd;
	
	@Column (name = "worker_id", length = 32)
	private String workerId;

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

	public String getStationCd() {
		return stationCd;
	}

	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	public String getStationNm() {
		return stationNm;
	}

	public void setStationNm(String stationNm) {
		this.stationNm = stationNm;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	public String getPrinterCd() {
		return printerCd;
	}

	public void setPrinterCd(String printerCd) {
		this.printerCd = printerCd;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

}
