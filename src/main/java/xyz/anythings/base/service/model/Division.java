package xyz.anythings.base.service.model;

/**
 * 중분류 정보
 * 
 * @author shortstop
 */
public class Division {
	/**
	 * 작업 배치 시퀀스
	 */
	private Integer jobBatchSeq;
	/**
	 * 호기 코드 
	 */
	private String regionCd;
	/**
	 * 호기 명 
	 */
	private String regionNm;
	/**
	 * 팔레트 수량 
	 */
	private int palletQty;
	/**
	 * 박스 수량 
	 */
	private int boxQty;
	/**
	 * 낱개 수량 
	 */
	private int pcsQty;
	
	public Division() {
	}
	
	public Division(Integer jobBatchSeq, String regionCd, String regionNm, int palletQty, int boxQty, Integer pcsQty) {
		this.jobBatchSeq = jobBatchSeq;
		this.regionCd = regionCd;
		this.regionNm = regionNm;
		this.palletQty = palletQty;
		this.boxQty = boxQty;
		this.pcsQty = pcsQty;
	}
	
	public Integer getJobBatchSeq() {
		return jobBatchSeq;
	}

	public void setJobBatchSeq(Integer jobBatchSeq) {
		this.jobBatchSeq = jobBatchSeq;
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
	
	public Integer getPalletQty() {
		return palletQty;
	}
	
	public void setPalletQty(Integer palletQty) {
		this.palletQty = palletQty;
	}
	
	public Integer getBoxQty() {
		return boxQty;
	}
	
	public void setBoxQty(Integer boxQty) {
		this.boxQty = boxQty;
	}
	
	public Integer getPcsQty() {
		return pcsQty;
	}
	
	public void setPcsQty(Integer pcsQty) {
		this.pcsQty = pcsQty;
	}

}
