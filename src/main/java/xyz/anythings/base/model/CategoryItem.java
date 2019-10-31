package xyz.anythings.base.model;

/**
 * 중분류 아이템
 * 
 * @author shortstop
 */
public class CategoryItem {
	/**
	 * 배치 시퀀스 - 반품 중분류에서 사용
	 */
	private Integer jobSeq;
	/**
	 * 설비 유형
	 */
	private String equipType;
	/**
	 * 설비 코드 
	 */
	private String equipCd;
	/**
	 * 설비 명 
	 */
	private String equipNm;
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
	
	public CategoryItem() {
	}
	
	public CategoryItem(Integer jobSeq, String equipType, String equipCd, String equipNm, int palletQty, int boxQty, Integer pcsQty) {
		this.jobSeq = jobSeq;
		this.equipType = equipType;
		this.equipCd = equipCd;
		this.equipNm = equipNm;
		this.palletQty = palletQty;
		this.boxQty = boxQty;
		this.pcsQty = pcsQty;
	}
	
	public Integer getJobSeq() {
		return jobSeq;
	}

	public void setJobSeq(Integer jobSeq) {
		this.jobSeq = jobSeq;
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

	public String getEquipNm() {
		return equipNm;
	}

	public void setEquipNm(String equipNm) {
		this.equipNm = equipNm;
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
