package xyz.anythings.base.entity;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_process", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_process_0", columnList = "batch_id"),
	@Index(name = "ix_tb_process_1", columnList = "order_id"),
	@Index(name = "ix_tb_process_2", columnList = "bucket_cd"),
	@Index(name = "ix_tb_process_3", columnList = "region_cd"),
	@Index(name = "ix_tb_process_4", columnList = "loc_cd"),
	@Index(name = "ix_tb_process_5", columnList = "mpi_cd"),
	@Index(name = "ix_tb_process_6", columnList = "process_seq,loc_cd,sku_cd,status,batch_id")
})
public class JobProcess extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 490553959188177510L;
	
	/**
	 * 작업 대기 상태 : Waiting
	 */
	public static final String JOB_STATUS_WAIT = "W";
	/**
	 * 작업 투입 상태 : Input
	 */
	public static final String JOB_STATUS_INPUT = "I";	
	/**
	 * 작업 피킹 상태 : Picking
	 */
	public static final String JOB_STATUS_PICKING = "P";
	/**
	 * 작업 완료 상태 : Finished
	 */
	public static final String JOB_STATUS_FINISH = "F";
	/**
	 * 박싱 완료 상태 : Boxed
	 */
	public static final String JOB_STATUS_BOXED = "B";
	/**
	 * 검수 완료 상태 : Examinated
	 */
	public static final String JOB_STATUS_EXAMINATED = "E";	
	/**
	 * 실적 보고 완료 상태 : Reported
	 */
	public static final String JOB_STATUS_REPORTED = "R";
	/**
	 * 작업 취소 상태 : Canceled
	 */
	public static final String JOB_STATUS_CANCEL = "C";
	/**
	 * 주문 취소 상태 : Deleted
	 */
	public static final String JOB_STATUS_DELETED = "D";

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

	@Column (name = "job_type", nullable = false, length = 20)
	private String jobType;

	@Column (name = "process_seq", length = 10)
	private Integer processSeq;

	@Column (name = "order_id", nullable = false, length = 40)
	private String orderId;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column (name = "cust_cd", nullable = false, length = 32)
	private String custCd;

	@Column (name = "cust_nm", length = 150)
	private String custNm;

	@Column (name = "clone_group_id", length = 40)
	private String cloneGroupId;

	@Column (name = "region_cd", nullable = false, length = 30)
	private String regionCd;

	@Column (name = "region_nm", length = 100)
	private String regionNm;

	@Column (name = "loc_cd", length = 30)
	private String locCd;

	@Column (name = "mpi_cd", length = 30)
	private String mpiCd;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column (name = "bucket_cd", length = 40)
	private String bucketCd;

	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;

	@Column (name = "invoice_id", length = 40)
	private String invoiceId;

	@Column (name = "invoice_group_id", length = 40)
	private String invoiceGroupId;

	@Column (name = "box_result_id", length = 40)
	private String boxResultId;

	@Column (name = "order_box_qty", length = 19)
	private Integer orderBoxQty;

	@Column (name = "box_in_qty", length = 19)
	private Integer boxInQty;

	@Column (name = "qty_unit", length = 6)
	private String qtyUnit;

	@Column (name = "status", length = 10)
	private String status;

	@Column (name = "order_type", length = 20)
	private String orderType;

	@Column (name = "order_class", length = 10)
	private String orderClass;

	@Column (name = "pick_qty", length = 19)
	private Integer pickQty;

	@Column (name = "picking_qty", length = 19)
	private Integer pickingQty;

	@Column (name = "picked_qty", length = 19)
	private Integer pickedQty;

	@Column (name = "real_picked_qty", length = 19)
	private Integer realPickedQty;

	@Column (name = "mpi_color", length = 10)
	private String mpiColor;

	@Column (name = "inspector_id", length = 32)
	private String inspectorId;

	@Column (name = "box_wt", length = 23)
	private Float boxWt;

	@Column (name = "box_input_at", length = 22)
	private String boxInputAt;

	@Column (name = "pick_started_at", length = 22)
	private String pickStartedAt;

	@Column (name = "pick_ended_at", length = 22)
	private String pickEndedAt;

	@Column (name = "boxed_at", length = 22)
	private String boxedAt;

	@Column (name = "insp_started_at", length = 22)
	private String inspStartedAt;

	@Column (name = "insp_ended_at", length = 22)
	private String inspEndedAt;

	@Column (name = "reported_at", length = 22)
	private String reportedAt;

	@Column (name = "job_hold_flag", length = 1)
	private Boolean jobHoldFlag;

	@Ignore
	private String gwPath;
	@Ignore
	private String sideCd;	
	@Ignore
	private String divCd;	
	@Ignore
	private String zoneCd;
	@Ignore
	private String equipZoneCd;
	@Ignore
	private String skuBarcd;

	public JobProcess() {
	}

	public JobProcess(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getProcessSeq() {
		return processSeq;
	}

	public void setProcessSeq(Integer processSeq) {
		this.processSeq = processSeq;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
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

	public String getBucketCd() {
		return bucketCd;
	}

	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceGroupId() {
		return invoiceGroupId;
	}

	public void setInvoiceGroupId(String invoiceGroupId) {
		this.invoiceGroupId = invoiceGroupId;
	}

	public String getBoxResultId() {
		return boxResultId;
	}

	public void setBoxResultId(String boxResultId) {
		this.boxResultId = boxResultId;
	}

	public Integer getOrderBoxQty() {
		return orderBoxQty;
	}

	public void setOrderBoxQty(Integer orderBoxQty) {
		this.orderBoxQty = orderBoxQty;
	}

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderClass() {
		return orderClass;
	}

	public void setOrderClass(String orderClass) {
		this.orderClass = orderClass;
	}

	public Integer getPickQty() {
		return pickQty;
	}

	public void setPickQty(Integer pickQty) {
		this.pickQty = pickQty;
	}

	public Integer getPickingQty() {
		return pickingQty;
	}

	public void setPickingQty(Integer pickingQty) {
		this.pickingQty = pickingQty;
	}

	public Integer getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(Integer pickedQty) {
		this.pickedQty = pickedQty;
	}

	public Integer getRealPickedQty() {
		return realPickedQty;
	}

	public void setRealPickedQty(Integer realPickedQty) {
		this.realPickedQty = realPickedQty;
	}

	public String getMpiColor() {
		return mpiColor;
	}

	public void setMpiColor(String mpiColor) {
		this.mpiColor = mpiColor;
	}

	public String getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(String inspectorId) {
		this.inspectorId = inspectorId;
	}

	public Float getBoxWt() {
		return boxWt;
	}

	public void setBoxWt(Float boxWt) {
		this.boxWt = boxWt;
	}

	public String getBoxInputAt() {
		return boxInputAt;
	}

	public void setBoxInputAt(String boxInputAt) {
		this.boxInputAt = boxInputAt;
	}

	public String getPickStartedAt() {
		return pickStartedAt;
	}

	public void setPickStartedAt(String pickStartedAt) {
		this.pickStartedAt = pickStartedAt;
	}

	public String getPickEndedAt() {
		return pickEndedAt;
	}

	public void setPickEndedAt(String pickEndedAt) {
		this.pickEndedAt = pickEndedAt;
	}

	public String getBoxedAt() {
		return boxedAt;
	}

	public void setBoxedAt(String boxedAt) {
		this.boxedAt = boxedAt;
	}

	public String getInspStartedAt() {
		return inspStartedAt;
	}

	public void setInspStartedAt(String inspStartedAt) {
		this.inspStartedAt = inspStartedAt;
	}

	public String getInspEndedAt() {
		return inspEndedAt;
	}

	public void setInspEndedAt(String inspEndedAt) {
		this.inspEndedAt = inspEndedAt;
	}

	public String getReportedAt() {
		return reportedAt;
	}

	public void setReportedAt(String reportedAt) {
		this.reportedAt = reportedAt;
	}

	public Boolean getJobHoldFlag() {
		return jobHoldFlag;
	}

	public void setJobHoldFlag(Boolean jobHoldFlag) {
		this.jobHoldFlag = jobHoldFlag;
	}

	public String getGwPath() {
		return gwPath;
	}

	public void setGwPath(String gwPath) {
		this.gwPath = gwPath;
	}

	public String getSideCd() {
		return sideCd;
	}

	public void setSideCd(String sideCd) {
		this.sideCd = sideCd;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public String getEquipZoneCd() {
		return equipZoneCd;
	}

	public void setEquipZoneCd(String equipZoneCd) {
		this.equipZoneCd = equipZoneCd;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	public String getDivCd() {
		return divCd;
	}

	public void setDivCd(String divCd) {
		this.divCd = divCd;
	}

	/**
	 * 아직 완료되지 않은 작업인지 체크
	 * 
	 * @return
	 */
	public boolean isTodoJob() {
		return (ValueUtil.isEmpty(this.status) || ValueUtil.isEqual(this.status, JOB_STATUS_CANCEL) || ValueUtil.isEqual(this.status, JOB_STATUS_PICKING) || ValueUtil.isEqual(this.status, JOB_STATUS_WAIT) || ValueUtil.isEqual(this.status, JOB_STATUS_INPUT));
	}
	
	/**
	 * 이미 완료된 작업인지 체크
	 * 
	 * @return
	 */
	public boolean isDoneJob() {
		return !this.isTodoJob();
	}
	
	/**
	 * id로 JobProcess 조회
	 *
	 * @param domainId
	 * @param id
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobProcess find(Long domainId, String id, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.setFilter(OrmConstants.ENTITY_FIELD_ID, id);
		JobProcess job = BeanUtil.get(IQueryManager.class).select(JobProcess.class, condition);

		if(job == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobProcess", id);
		}

		if(job != null && job.getJobHoldFlag()) {
			throw new ElidomRuntimeException("작업이 홀드 상태여서 처리할 수 없습니다");
		}

		return job;
	}

	/**
	 * id로 작업 데이터 조회시 레코드 락을 걸면서 조회
	 *
	 * @param domainId
	 * @param id
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobProcess findWithLock(Long domainId, String id, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.setFilter(OrmConstants.ENTITY_FIELD_ID, id);
		JobProcess job = BeanUtil.get(IQueryManager.class).selectWithLock(JobProcess.class, condition);

		if(job == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobProcess", id);
		}

		return job;
	}

}
