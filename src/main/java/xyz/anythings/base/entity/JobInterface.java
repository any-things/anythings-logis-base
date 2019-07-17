package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_interface", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_interface_0", columnList = "batch_id"),
	@Index(name = "ix_tb_interface_1", columnList = "job_id"),
	@Index(name = "ix_tb_interface_2", columnList = "com_cd,job_date,job_batch_seq,domain_id"),
})
public class JobInterface extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 247737495458269192L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "batch_id", length = 40)
	private String batchId;

	@Column (name = "job_id", nullable = false, length = 40)
	private String jobId;

	@Column (name = "job_date", nullable = false, length = 10)
	private String jobDate;

	@Column (name = "job_batch_seq", nullable = false, length = 10)
	private Integer jobBatchSeq;

	@Column (name = "sub_batch_seq", length = 10)
	private Integer subBatchSeq;

	@Column (name = "order_id", length = 40)
	private String orderId;

	@Column (name = "order_line_no", length = 40)
	private String orderLineNo;

	@Column (name = "job_type", nullable = false, length = 20)
	private String jobType;

	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "cust_cd", length = 32)
	private String custCd;

	@Column (name = "cust_nm", length = 150)
	private String custNm;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column(name = "clone_group_id", length = 40)
	private String cloneGroupId;

	@Column (name = "region_cd", nullable = false, length = 30)
	private String regionCd;

	@Column (name = "region_nm", length = 50)
	private String regionNm;

	@Column (name = "loc_cd", nullable = false, length = 30)
	private String locCd;

	@Column (name = "mpi_cd", length = 30)
	private String mpiCd;

	@Column (name = "invoice_id", length = 40)
	private String invoiceId;

	@Column (name = "order_group", length = 100)
	private String orderGroup;

	@Column (name = "mps_order_group", length = 100)
	private String mpsOrderGroup;

	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;

	@Column (name = "qty_unit", length = 5)
	private String qtyUnit;

	@Column (name = "box_in_qty", length = 18)
	private Integer boxInQty;

	@Column (name = "status", length = 10)
	private String status;

	@Column (name = "pick_qty", nullable = false, length = 19)
	private Integer pickQty;

	@Column (name = "picked_qty", length = 19)
	private Integer pickedQty;

	@Column (name = "if_received_at", length = 22)
	private String ifReceivedAt;

	@Column (name = "if_reported_at", length = 22)
	private String ifReportedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public Integer getJobBatchSeq() {
		return jobBatchSeq;
	}

	public void setJobBatchSeq(Integer jobBatchSeq) {
		this.jobBatchSeq = jobBatchSeq;
	}

	public Integer getSubBatchSeq() {
		return subBatchSeq;
	}

	public void setSubBatchSeq(Integer subBatchSeq) {
		this.subBatchSeq = subBatchSeq;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getCloneGroupId() {
		return cloneGroupId;
	}

	public void setCloneGroupId(String cloneGroupId) {
		this.cloneGroupId = cloneGroupId;
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

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getMpiCd() {
		return mpiCd;
	}

	public void setMpiCd(String mpiCd) {
		this.mpiCd = mpiCd;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(String orderGroup) {
		this.orderGroup = orderGroup;
	}

	public String getMpsOrderGroup() {
		return mpsOrderGroup;
	}

	public void setMpsOrderGroup(String mpsOrderGroup) {
		this.mpsOrderGroup = mpsOrderGroup;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getIfReceivedAt() {
		return ifReceivedAt;
	}

	public void setIfReceivedAt(String ifReceivedAt) {
		this.ifReceivedAt = ifReceivedAt;
	}

	public String getIfReportedAt() {
		return ifReportedAt;
	}

	public void setIfReportedAt(String ifReportedAt) {
		this.ifReportedAt = ifReportedAt;
	}

}
