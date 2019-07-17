package xyz.anythings.base.service.model;

/**
 * 작업 배치별 투입 정보 
 * 
 * @author shortstop
 */
public class BatchInput {

    /**
     * 투입 순서
     */
    private Integer inputSeq;
	/**
	 * 고객사 코드 
	 */
    private String comCd;
	/**
	 * 상품 코드
	 */
    private String skuCd;
    /**
     * 상품 명 
     */
    private String skuNm;
    /**
     * SKU 바코드
     */
    private String skuBarcd;
    /**
     * 거래처 수
     */
    private Integer custCnt;
    /**
     * 로케이션 코드 
     */
    private String locCd; 
    /**
     * 예정 수량 
     */
    private Integer pickQty;
    /**
     * 취소 수량 
     */
    private Integer cancelQty;    
    /**
     * 확정 수량 
     */
    private Integer pickedQty;
    /**
     * 남은 (피킹할) 수량 
     */
    private Integer leftQty;
    /**
     * 상태
     */
    private String status;
    
    public BatchInput() {
    }
    
    public BatchInput(Integer inputSeq, String comCd, String skuCd, String skuNm, Integer custCnt, Integer pickQty, Integer pickedQty, Integer cancelQty) {
    	this.inputSeq = inputSeq;
    	this.comCd = comCd;
    	this.skuCd = skuCd;
    	this.skuNm = skuNm;
    	this.custCnt = custCnt;
    	this.pickQty = pickQty;
    	this.pickedQty = pickedQty;
    	this.cancelQty = cancelQty;
    	this.leftQty = (pickQty - pickedQty);
    	this.leftQty = this.leftQty > 0 ? this.leftQty : 0;
    	this.applyStatus();
    }
    
	public Integer getInputSeq() {
		return inputSeq;
	}

	public void setInputSeq(Integer inputSeq) {
		this.inputSeq = inputSeq;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getSkuCd() {
		return skuCd;
	}
	
	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}
	
	public String getSkuNm() {
		return skuNm;
	}
	
	public void setSkuNm(String skuNm) {
		this.skuNm = skuNm;
	}
	
	public String getSkuBarcd() {
		return skuBarcd;
	}
	
	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}
	
	public Integer getCustCnt() {
		return custCnt;
	}
	
	public void setCustCnt(Integer custCnt) {
		this.custCnt = custCnt;
	}
	
	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public Integer getPickQty() {
		return pickQty;
	}
	
	public void setPickQty(Integer pickQty) {
		this.pickQty = pickQty;
	}
	
	public Integer getCancelQty() {
		return cancelQty;
	}

	public void setCancelQty(Integer cancelQty) {
		this.cancelQty = cancelQty;
	}

	public Integer getPickedQty() {
		return pickedQty;
	}
	
	public void setPickedQty(Integer pickedQty) {
		this.pickedQty = pickedQty;
	}
	
	public Integer getLeftQty() {
		if(this.leftQty == null) {
			this.applyStatus();
		}
		
		return leftQty;
	}
	
	public void setLeftQty(Integer leftQty) {
		this.leftQty = leftQty;
	}
	
	public String getStatus() {
		if(this.status == null) {
			this.applyStatus();
		}
		
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
    	
	public void applyStatus() {
		if(this.leftQty == null && this.pickQty != null && this.pickedQty != null) {
			this.leftQty = (this.pickQty - this.pickedQty);
		}
		
		if(this.leftQty == 0) {
			this.status = "F";
		} else {
			if(this.cancelQty == null || this.cancelQty == 0) {
				this.status = "R";
			} else {
				if(this.pickQty == this.pickedQty + this.cancelQty) {
					this.status = "U";
				} else {
					this.status = "R";
				}
			}
		}
	}
}
