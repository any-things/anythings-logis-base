package xyz.anythings.base.service.model;

/**
 * 호기별 거래처 할당 현황 정보 
 * 
 * @author shortstop
 */
public class RegionCell {
	
	/**
	 * 슈트 번호
	 */
	private String chuteNo;
	/**
	 * 호기 코드 
	 */
	private String regionCd;
	/**
	 * 호기 명 
	 */
	private String regionNm;
	/**
	 * 호기 타입
	 */
	private String regionType;
	/**
	 * 할당되지 않은 셀 개수 
	 */
	private Integer remainCells;
	/**
	 * 할당된 셀 개수
	 */
	private Integer assignedCells;
	/**
	 * 할당된 상품 개수 
	 */
	private Integer assignedSku;
	/**
	 * 할당된 개수 PCS 
	 */
	private Integer assignedPcs;
	
	/**
	 * 기본 생성자 
	 */
	public RegionCell() {
	}
	
	/**
	 * 생성자
	 * 
	 * @param chuteNo
	 * @param regionCd
	 * @param regionNm
	 * @param regionType
	 * @param remainCells
	 * @param assignedCells
	 * @param assignedSku
	 * @param assignedPcs
	 */
	public RegionCell(String chuteNo, String regionCd, String regionNm, String regionType, Integer remainCells, Integer assignedCells, Integer assignedSku, Integer assignedPcs) {
		this.chuteNo = chuteNo;
		this.regionCd = regionCd;
		this.regionNm = regionNm;
		this.regionType = regionType;
		this.remainCells = remainCells;
		this.assignedCells = assignedCells;
		this.assignedSku = assignedSku;
		this.assignedPcs = assignedPcs;
	}
	
	public String getChuteNo() {
		return chuteNo;
	}

	public void setChuteNo(String chuteNo) {
		this.chuteNo = chuteNo;
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
	
	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public Integer getRemainCells() {
		return remainCells;
	}
	
	public void setRemainCells(Integer remainCells) {
		this.remainCells = remainCells;
	}
	
	public Integer getAssignedCells() {
		return assignedCells;
	}
	
	public void setAssignedCells(Integer assignedCells) {
		this.assignedCells = assignedCells;
	}

	public Integer getAssignedSku() {
		return assignedSku;
	}

	public void setAssignedSku(Integer assignedSku) {
		this.assignedSku = assignedSku;
	}

	public Integer getAssignedPcs() {
		return assignedPcs;
	}

	public void setAssignedPcs(Integer assignedPcs) {
		this.assignedPcs = assignedPcs;
	}

}
