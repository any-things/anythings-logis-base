package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_receipt_summary_item", idStrategy = GenerationRule.UUID, uniqueFields="receiptSummaryId,itemNm,itemCd", indexes = {
	@Index(name = "ix_tb_receipt_summary_item_0", columnList = "receipt_summary_id,item_nm,item_cd")
})
public class ReceiptSummaryItem extends xyz.elidom.orm.entity.basic.DomainStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 234759915585875608L;
	
	/**
	 * 주문 수신 항목 센터 - Center
	 */
	public static final String RECEIPT_ITEM_CENTER = "Center";
	
	/**
	 * 주문 수신 항목 화주 - Company
	 */
	public static final String RECEIPT_ITEM_COMPANY = "Company";
	
	/**
	 * 주문 수신 항목 박스 유형 - Box Type
	 */
	public static final String RECEIPT_ITEM_BOX_TYPE = "Box Type";
	
	/**
	 * 주문 수신 항목 거래처 - Customer
	 */
	public static final String RECEIPT_ITEM_CUSTOMER = "Customer";
	/**
	 * 주문 수신 항목 SKU - SKU
	 */
	public static final String RECEIPT_ITEM_SKU = "SKU";
	/**
	 * 주문 수신 항목 SKU Barcode - SKU Barcode
	 */
	public static final String RECEIPT_ITEM_SKU_BARCODE = "SKU Barcode";
	/**
	 * 주문 수신 항목 SKU Weight - SKU 중량
	 */
	public static final String RECEIPT_ITEM_SKU_WEIGHT = "SKU Weight";
	/**
	 * 주문 수신 항목 Batch - Batch
	 */
	public static final String RECEIPT_ITEM_BATCH = "Batch";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "receipt_summary_id", nullable = false, length = 40)
	private String receiptSummaryId;

	@Column (name = "item_nm", nullable = false, length = 40)
	private String itemNm;

	@Column (name = "item_cd", length = 100)
	private String itemCd;

	@Column (name = "wms_order_cnt", length = 19)
	private Integer wmsOrderCnt;

	@Column (name = "wms_total_cnt", length = 19)
	private Integer wmsTotalCnt;
	
	@Column (name = "expected_cnt", length = 19)
	private Integer expectedCnt;

	@Column (name = "received_cnt", length = 19)
	private Integer receivedCnt;
	
	@Column (name = "err_cnt", length = 19)
	private Integer errCnt;

	@Column (name = "status", length = 10)
	private String status;

	@Column (name = "err_msg", length = 4000)
	private String errMsg;

	@Column (name = "started_at", length = 22)
	private String startedAt;

	@Column (name = "finished_at", length = 22)
	private String finishedAt;
	
	@Column (name = "skip_flag")
	private boolean skipFlag;
	
	@Ignore
	private String dcCd;
	
	@Ignore
	private String comCd;
	
	@Ignore
	private String jobDate;
	
	@Ignore
	private String wavNo;
	
	@Ignore
	private String orderDate;
	
	@Ignore
	private JobBatch batch;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiptSummaryId() {
		return receiptSummaryId;
	}

	public void setReceiptSummaryId(String receiptSummaryId) {
		this.receiptSummaryId = receiptSummaryId;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public Integer getExpectedCnt() {
		return expectedCnt;
	}

	public void setExpectedCnt(Integer expectedCnt) {
		this.expectedCnt = expectedCnt;
	}

	public Integer getReceivedCnt() {
		return receivedCnt;
	}

	public void setReceivedCnt(Integer receivedCnt) {
		this.receivedCnt = receivedCnt;
	}

	public Integer getErrCnt() {
		return errCnt;
	}

	public void setErrCnt(Integer errCnt) {
		this.errCnt = errCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}

	public String getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(String finishedAt) {
		this.finishedAt = finishedAt;
	}

	public JobBatch getBatch() {
		return batch;
	}

	public void setBatch(JobBatch batch) {
		this.batch = batch;
		
		if(batch != null) {
			this.dcCd = batch.getDcCd();
			this.jobDate = batch.getJobDate();
			if(!batch.isRtnBatch() && !batch.isRtn3Batch()) {
				this.wavNo = batch.getJobId();
			}
		}
	}

	public boolean getSkipFlag() {
		return skipFlag;
	}

	public void setSkipFlag(boolean skipFlag) {
		this.skipFlag = skipFlag;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public String getWavNo() {
		return wavNo;
	}

	public void setWavNo(String wavNo) {
		this.wavNo = wavNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getWmsOrderCnt() {
		return wmsOrderCnt;
	}

	public void setWmsOrderCnt(Integer wmsOrderCnt) {
		this.wmsOrderCnt = wmsOrderCnt;
	}

	public Integer getWmsTotalCnt() {
		return wmsTotalCnt;
	}

	public void setWmsTotalCnt(Integer wmsTotalCnt) {
		this.wmsTotalCnt = wmsTotalCnt;
	}

}
