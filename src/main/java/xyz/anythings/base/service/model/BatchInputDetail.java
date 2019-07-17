package xyz.anythings.base.service.model;

import java.util.StringJoiner;

import xyz.elidom.sys.SysConstants;

/**
 * 작업 배치별 투입에 대한 상세 정보 
 * 
 * @author shortstop
 */
public class BatchInputDetail {
	
    /**
     * 로케이션 코드
     */
    private String locCd;    
    /**
     * 거래처 코드
     */
    private String custCd;
    /**
     * 거래처 명
     */
    private String custNm;
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
    
    public BatchInputDetail() {
    }
    
    public BatchInputDetail(String comCd, String custCd, String custNm, String locCd, Integer pickQty, Integer pickedQty, Integer cancelQty) {
    	this.comCd = comCd;
    	this.custCd = custCd;
    	this.custNm = custNm;
    	this.locCd = locCd;
    	this.pickQty = pickQty;
    	this.pickedQty = pickedQty;
    	this.cancelQty = cancelQty;
    	this.applyStatus();
    }

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
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

	public Integer getPickQty() {
		return pickQty;
	}

	public void setPickQty(Integer pickQty) {
		this.pickQty = pickQty;
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
		
		return this.status;
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
	
	/**
	 * DAS용 - 작업 배치내 투입 상품에 대한 상세 거래처 주문 정보 조회  
	 * 
	 * @return
	 */
	public static String dasInputDetailsQuery() {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		return
			sql.add("SELECT")
			   .add("	PROCESS_SEQ, LOC_CD, COM_CD, CUST_CD, CUST_NM, SUM(PICK_QTY) AS PICK_QTY, NVL(SUM(REAL_PICKED_QTY), 0) AS PICKED_QTY")
			   .add("FROM")
			   .add("	TB_PROCESS")
			   .add("WHERE")
			   .add("	DOMAIN_ID = :domainId AND BATCH_ID = :batchId AND PROCESS_SEQ = :processSeq")
			   .add("GROUP BY")
			   .add("	PROCESS_SEQ, LOC_CD, COM_CD, CUST_CD, CUST_NM")
			   .add("ORDER BY")
			   .add("	LOC_CD, COM_CD, CUST_CD").toString();
	}
	
	/**
	 * DPS용 - 작업 배치내 특정 주문에 대한 상세 상품 주문 정보 조회  
	 * 
	 * @return
	 */
	public static String dpsInputDetailsQuery() {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		return
			sql.add("SELECT")
			   .add("	PROCESS_SEQ, COM_CD, LOC_CD, SKU_CD, SKU_NM, SUM(PICK_QTY) AS PICK_QTY, NVL(SUM(REAL_PICKED_QTY), 0) AS PICKED_QTY")
			   .add("FROM")
			   .add("	TB_PROCESS")
			   .add("WHERE")
			   .add("	BATCH_ID = :batchId AND ORDER_ID = :orderId AND (PROCESS_SEQ IS NOT NULL AND PROCESS_SEQ > 0)")
			   .add("GROUP BY")
			   .add("	PROCESS_SEQ, LOC_CD, COM_CD, SKU_CD, SKU_NM")
			   .add("ORDER BY")
			   .add("	LOC_CD, COM_CD, SKU_CD").toString();
	}

}
