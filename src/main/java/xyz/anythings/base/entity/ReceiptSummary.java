package xyz.anythings.base.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.anythings.base.LogisBaseConstants;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@Table(name = "tb_receipt_summary", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_receipt_summary_0", columnList = "domain_id,com_cd,job_date,job_batch_seq"),
	@Index(name = "ix_tb_receipt_summary_1", columnList = "job_date,domain_id")
})
public class ReceiptSummary extends xyz.elidom.orm.entity.basic.DomainStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 482072708762433214L;
	
	/**
	 * 주문 수신 타입 - order
	 */
	public static final String RECEIPT_TYPE_ORDER = "order";
	/**
	 * 주문 수신 타입 - common
	 */
	public static final String RECEIPT_TYPE_COMMON = "common";
	
	/**
	 * 주문 수신 모드 - service
	 */
	public static final String RECEIPT_MODE_SERVICE = "service";
	/**
	 * 주문 수신 모드 - procedure
	 */
	public static final String RECEIPT_MODE_PROCEDURE = "procedure";	

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "receipt_type", length = 10)
	private String receiptType;
	
	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "job_date", nullable = false, length = 10)
	private String jobDate;

	@Column (name = "job_batch_seq", nullable = false, length = 4)
	private String jobBatchSeq;

	@Column (name = "status", nullable = false, length = 10)
	private String status;

	@Column (name = "started_at", nullable = false, length = 22)
	private String startedAt;

	@Column (name = "finished_at", length = 22)
	private String finishedAt;

	@Column (name = "receiver_id", length = 32)
	private String receiverId;

	@Ignore
	private String receiveMode;
	
	@Ignore
	private Integer totalExpected;

	@Ignore
	private Integer totalReceived;

	@Ignore
	private Integer totalErrored;

	@Ignore
	private Float progressRate;

	@Ignore
	private List<ReceiptSummaryItem> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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

	public String getJobBatchSeq() {
		return jobBatchSeq;
	}

	public void setJobBatchSeq(String jobBatchSeq) {
		this.jobBatchSeq = jobBatchSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(String receiveMode) {
		this.receiveMode = receiveMode;
	}

	public Integer getTotalExpected() {
		return totalExpected;
	}

	public void setTotalExpected(Integer totalExpected) {
		this.totalExpected = totalExpected;
	}

	public Integer getTotalReceived() {
		return totalReceived;
	}

	public void setTotalReceived(Integer totalReceived) {
		this.totalReceived = totalReceived;
	}

	public Float getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(Float progressRate) {
		this.progressRate = progressRate;
	}

	public List<ReceiptSummaryItem> getItems() {
		return items;
	}

	public void setItems(List<ReceiptSummaryItem> items) {
		this.items = items;
	}

	public void addItem(ReceiptSummaryItem item) {
		if(this.items == null) {
			this.items = new ArrayList<ReceiptSummaryItem>();
		}

		this.items.add(item);
	}
	
	/**
	 * 주문 수신 유형인지 체크
	 * 
	 * @return
	 */
	public boolean isOrderReceiptType() {
		return ValueUtil.isEqualIgnoreCase(this.receiptType, ReceiptSummary.RECEIPT_TYPE_ORDER);
	}
	
	/**
	 * 수신 모드 : 서비스 수신 모드
	 * 
	 * @return
	 */
	public boolean isServiceReceiptMode() {
		return ValueUtil.isEqualIgnoreCase(this.receiveMode, ReceiptSummary.RECEIPT_MODE_SERVICE);
	}

	/**
	 * Progress Rate 업데이트
	 */
	public void updateProgressRate() {
		if(ValueUtil.isEmpty(this.items)) {
			Map<String, Object> params = ValueUtil.newMap("domainId,receiptSummaryId", Domain.currentDomainId(), this.id);
			this.items = BeanUtil.get(IQueryManager.class).selectList(ReceiptSummaryItem.class, params);
		}

		if(this.totalExpected == null) this.totalExpected = 0;
		if(this.totalReceived == null) this.totalReceived = 0;
		if(this.totalErrored == null) this.totalErrored = 0;

		if(ValueUtil.isNotEmpty(this.items)) {
			for(ReceiptSummaryItem item : this.items) {
				if(!item.getSkipFlag()) {
					this.totalExpected += item.getExpectedCnt();
					this.totalReceived += item.getReceivedCnt();
					this.totalErrored += item.getErrCnt();
				}
			}
		}

		if(this.totalExpected == 0) {
			this.progressRate = 0f;
			this.status = LogisBaseConstants.COMMON_STATUS_FINISHED;
			BeanUtil.get(IQueryManager.class).update(this, OrmConstants.ENTITY_FIELD_STATUS);

		} else if(this.totalExpected == (this.totalReceived + this.totalErrored)) {
			this.progressRate = 100f;

		} else {
			this.progressRate = (ValueUtil.toFloat(this.totalReceived + this.totalErrored) / ValueUtil.toFloat(this.totalExpected)) * 100;
		}
	}
}
