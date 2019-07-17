package xyz.anythings.base.entity;

import java.util.Map;
import java.util.StringJoiner;

import xyz.anythings.sys.AnyConstants;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.User;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@Table(name = "tb_job_input_seq", idStrategy = GenerationRule.UUID, uniqueFields="domainId,inputSeq,zoneCd,regionCd,batchId", indexes = {
	@Index(name = "ix_tb_job_input_seq_0", columnList = "input_seq,zone_cd,region_cd,batch_id", unique = true),
	@Index(name = "ix_tb_job_input_seq_1", columnList = "region_cd,batch_id"),
	@Index(name = "ix_tb_job_input_seq_2", columnList = "sku_cd,batch_id"),
	@Index(name = "ix_tb_job_input_seq_3", columnList = "bucket_cd,batch_id")
})
public class JobInputSeq extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 287596365580809702L;
	/**
	 * 작업 투입 상태
	 */
	public static final String INPUT_STATUS_INPUT = "I";
	/**
	 * 작업 대기 상태
	 */
	public static final String INPUT_STATUS_WAIT = "W";
	/**
	 * 작업 실행 상태
	 */
	public static final String INPUT_STATUS_RUNNING = "R";
	/**
	 * 작업 완료 상태
	 */
	public static final String INPUT_STATUS_FINISHED = "F";
	/**
	 * 작업 미처리 상태
	 */
	public static final String INPUT_STATUS_UNFINISHED = "U";
	/**
	 * 검수 완료 상태
	 */
	public static final String INPUT_STATUS_EXAMMED = "E";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "batch_id", nullable = false, length = 40)
	private String batchId;

	@Column (name = "job_id", length = 40)
	private String jobId;

	@Column (name = "clone_group_id", length = 40)
	private String cloneGroupId;

	@Column (name = "region_cd", length = 30)
	private String regionCd;

	@Column (name = "side_cd", length = 30)
	private String sideCd;

	@Column (name = "equip_zone_cd", length = 30)
	private String equipZoneCd;

	@Column (name = "zone_cd", length = 30)
	private String zoneCd;

	@Column (name = "zone_seq", length = 10)
	private Integer zoneSeq;

	@Column (name = "input_seq", nullable = false, length = 10)
	private Integer inputSeq;

	@Column (name = "com_cd", length = 32)
	private String comCd;
	
	@Column (name = "sku_cd", length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column (name = "bucket_cd", length = 30)
	private String bucketCd;

	@Column (name = "order_id", length = 40)
	private String orderId;

	@Column (name = "mpi_color", length = 10)
	private String mpiColor;

	@Column (name = "item_count", length = 19)
	private Integer itemCount;

	@Column (name = "status", length = 10)
	private String status;

	@Ignore
	private int pickQty;

	@Ignore
	private int pickedQty;

	@Ignore
	private String boxTypeCd;

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

	public String getSideCd() {
		return sideCd;
	}

	public void setSideCd(String sideCd) {
		this.sideCd = sideCd;
	}

	public String getEquipZoneCd() {
		return equipZoneCd;
	}

	public void setEquipZoneCd(String equipZoneCd) {
		this.equipZoneCd = equipZoneCd;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public Integer getZoneSeq() {
		return zoneSeq;
	}

	public void setZoneSeq(Integer zoneSeq) {
		this.zoneSeq = zoneSeq;
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

	public String getBucketCd() {
		return bucketCd;
	}

	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMpiColor() {
		return mpiColor;
	}

	public void setMpiColor(String mpiColor) {
		this.mpiColor = mpiColor;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPickQty() {
		return pickQty;
	}

	public void setPickQty(int pickQty) {
		this.pickQty = pickQty;
	}

	public int getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(int pickedQty) {
		this.pickedQty = pickedQty;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	/**
	 * id로 JobInputSeq 조회
	 *
	 * @parma domainId
	 * @param id
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobInputSeq find(Long domainId, String id, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter(SysConstants.ENTITY_FIELD_ID, id);
		JobInputSeq inputSeq = BeanUtil.get(IQueryManager.class).select(JobInputSeq.class, condition);

		if(inputSeq == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobInputSeq", id);
		}

		return inputSeq;
	}

	/**
	 * 다음 투입 정보를 조회
	 *
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param processSeq
	 * @return
	 */
	public static JobInputSeq findBy(Long domainId, String batchId, String regionCd, int inputSeq) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("batchId", batchId);
		condition.addFilter("inputSeq", inputSeq);
		return BeanUtil.get(IQueryManager.class).selectByCondition(JobInputSeq.class, condition);
	}

	/**
	 * 작업 투입 정보 작업 시작 처리
	 *
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param inputSeq
	 * @return
	 */
	public static int start(Long domainId, String batchId, String regionCd, int inputSeq) {
		return changeStatus(domainId, batchId, regionCd, inputSeq, JobInputSeq.INPUT_STATUS_RUNNING);
	}

	/**
	 * 작업 투입 정보 종료 처리
	 *
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param inputSeq
	 * @param inputStatus
	 * @return
	 */
	public static int finish(Long domainId, String batchId, String regionCd, int inputSeq, String inputStatus) {
		return changeStatus(domainId, batchId, regionCd, inputSeq, inputStatus);
	}

	/**
	 * 투입 정보 상태 변경
	 *
	 * @param domainId
	 * @param batchId
	 * @param regionCd
	 * @param inputSeq
	 * @param inputStatus
	 * @return
	 */
	public static int changeStatus(Long domainId, String batchId, String regionCd, int inputSeq, String inputStatus) {
		// 파라미터 설정
		Map<String, Object> params =
			ValueUtil.newMap("domainId,batchId,regionCd,inputSeq,status", domainId, batchId, regionCd, inputSeq, inputStatus);

		if(User.currentUser() != null) {
			params.put("updaterId", User.currentUser().getId());
		}

		// 현재 투입 정보 상태 업데이트
		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
	    sql.add("UPDATE")
		   .add(" 	TB_JOB_INPUT_SEQ")
		   .add("SET")
		   .add("	STATUS = :status, UPDATED_AT = sysdate")
		   .add("#if($updaterId)")
		   .add("	, UPDATER_ID = :updaterId")
		   .add("#end")
		   .add("WHERE")
		   .add("  	DOMAIN_ID = :domainId AND BATCH_ID = :batchId AND INPUT_SEQ = :inputSeq")
		   .add("#if($regionCd)")
		   .add("   AND REGION_CD = :regionCd")
		   .add("#end");

		if(ValueUtil.isEqualIgnoreCase(inputStatus, JobInputSeq.INPUT_STATUS_RUNNING)) {
			sql.add(" AND STATUS = '" + JobInputSeq.INPUT_STATUS_WAIT + "'");

		} else if(ValueUtil.isEqualIgnoreCase(inputStatus, JobInputSeq.INPUT_STATUS_FINISHED)) {
			sql.add(" AND STATUS = '" + JobInputSeq.INPUT_STATUS_RUNNING + "'");

		} else if(ValueUtil.isEqualIgnoreCase(inputStatus, JobInputSeq.INPUT_STATUS_UNFINISHED)) {
			sql.add(" AND STATUS = '" + JobInputSeq.INPUT_STATUS_RUNNING + "'");
		}

		return BeanUtil.get(IQueryManager.class).executeBySql(sql.toString(), params);
	}

}
