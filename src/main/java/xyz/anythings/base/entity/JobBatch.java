package xyz.anythings.base.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import xyz.anythings.base.LogisBaseConstants;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Filter;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 배치
 *
 * @author shortstop
 */
@Table(name = "tb_job_batch", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_job_batch_0", columnList = "status,com_cd,domain_id"),
	@Index(name = "ix_tb_job_batch_1", columnList = "com_cd,job_date,job_batch_seq,domain_id"),
	@Index(name = "ix_tb_job_batch_2", columnList = "batch_group_id"),
	@Index(name = "ix_tb_job_batch_3", columnList = "clone_group_id"),
	@Index(name = "ix_tb_job_batch_4", columnList = "region_cd"),
})
public class JobBatch extends xyz.elidom.orm.entity.basic.DomainStampHook {

	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 479347610981416990L;

	/**
	 * I/F 데이터 수신 중 상태 : RECEIVING
	 */
	public static final String STATUS_RECEIVE = "RECEIVING";
	/**
	 * 대기 상태 : WAIT
	 */
	public static final String STATUS_WAIT = "WAIT";
	/**
	 * 작업 지시 가능 상태 : READY
	 */
	public static final String STATUS_READY = "READY";
	/**
	 * 작업 지시 가능 상태 : READY
	 */
	public static final String STATUS_MERGED = "MERGED";
	/**
	 * 진행 상태 : RUNNING
	 */
	public static final String STATUS_RUNNING = "RUN";
	/**
	 * 완료 상태 : END
	 */
	public static final String STATUS_END = "END";
	/**
	 * 주문 취소 상태 : CANCEL
	 */
	public static final String STATUS_CANCEL = "CANCEL";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column (name = "com_cd", length = 32)
	private String comCd;

	@Column (name = "job_type", nullable = false, length = 20)
	private String jobType;

	@Column (name = "job_date", nullable = false, length = 10)
	private String jobDate;

	@Column (name = "job_batch_seq", nullable = false, length = 10)
	private Integer jobBatchSeq;

	@Column (name = "batch_group_id", nullable = false, length = 40)
	private String batchGroupId;

	@Column (name = "job_id", nullable = false, length = 40)
	private String jobId;

	@Column (name = "clone_group_id", length = 40)
	private String cloneGroupId;

	@Column (name = "region_cd", length = 30)
	private String regionCd;

	@Column (name = "region_nm", length = 100)
	private String regionNm;

	@Column (name = "wms_order_cnt", length = 19)
	private Integer wmsOrderCnt;

	@Column (name = "mps_order_cnt", length = 19)
	private Integer mpsOrderCnt;

	@Column (name = "wms_total_cnt", length = 19)
	private Integer wmsTotalCnt;

	@Column (name = "mps_total_cnt", length = 19)
	private Integer mpsTotalCnt;

	@Column (name = "status", length = 10)
	private String status;

	@Column (name = "last_input_seq", length = 10)
	private Integer lastInputSeq;

	@Column (name = "instructed_at", type = xyz.elidom.dbist.annotation.ColumnType.DATETIME)
	private Date instructedAt;

	@Column (name = "finished_at", type = xyz.elidom.dbist.annotation.ColumnType.DATETIME)
	private Date finishedAt;

	@Ignore
	private Float progressRate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getBatchGroupId() {
		return batchGroupId;
	}

	public void setBatchGroupId(String batchGroupId) {
		this.batchGroupId = batchGroupId;
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

	public String getRegionNm() {
		return regionNm;
	}

	public void setRegionNm(String regionNm) {
		this.regionNm = regionNm;
	}

	public Integer getWmsOrderCnt() {
		return wmsOrderCnt;
	}

	public void setWmsOrderCnt(Integer wmsOrderCnt) {
		this.wmsOrderCnt = wmsOrderCnt;
	}

	public Integer getMpsOrderCnt() {
		return mpsOrderCnt;
	}

	public void setMpsOrderCnt(Integer mpsOrderCnt) {
		this.mpsOrderCnt = mpsOrderCnt;
	}

	public Integer getWmsTotalCnt() {
		return wmsTotalCnt;
	}

	public void setWmsTotalCnt(Integer wmsTotalCnt) {
		this.wmsTotalCnt = wmsTotalCnt;
	}

	public Integer getMpsTotalCnt() {
		return mpsTotalCnt;
	}

	public void setMpsTotalCnt(Integer mpsTotalCnt) {
		this.mpsTotalCnt = mpsTotalCnt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getLastInputSeq() {
		return lastInputSeq;
	}

	public void setLastInputSeq(Integer lastInputSeq) {
		this.lastInputSeq = lastInputSeq;
	}

	public Date getInstructedAt() {
		return instructedAt;
	}

	public void setInstructedAt(Date instructedAt) {
		this.instructedAt = instructedAt;
	}

	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	public Float getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(Float progressRate) {
		this.progressRate = progressRate;
	}

	/**
	 * DAS용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isDasBatch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_DAS, this.jobType);
	}

	/**
	 * DAS 2차 분류용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isDas2Batch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_DAS2, this.jobType);
	}

	/**
	 * DAS Single SKU로만 이뤄진 배치 인지 체크 
	 *
	 * @return
	 */
	public boolean isDas3Batch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_DAS3, this.jobType);
	}
	
	/**
	 * 반품용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isRtnBatch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_RTN, this.jobType);
	}
	
	/**
	 * 무오더 반품용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isRtn3Batch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_RTN3, this.jobType);
	}

	/**
	 * DPS용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isDpsBatch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_DPS, this.jobType);
	}
	
	/**
	 * DPS 2차 분류용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isDps2Batch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_DPS2, this.jobType);
	}

	/**
	 * QPS용 작업 배치인지 체크
	 *
	 * @return
	 */
	public boolean isQpsBatch() {
		return ValueUtil.isEqualIgnoreCase(LogisBaseConstants.JOB_TYPE_QPS, this.jobType);
	}

	/**
	 * jobType용 작업 배치인지 체크
	 *
	 * @param jobType
	 * @return
	 */
	public boolean isJobTypeOf(String jobType) {
		return ValueUtil.isEqualIgnoreCase(jobType, this.jobType);
	}

	/**
	 * 복사된 배치인지 체크
	 *
	 * @return
	 */
	public boolean isCloneBatch() {
		if(this.isDpsBatch() || this.isQpsBatch()) {
			return false;
		}

		// 클론 아이디가 없으면 false
		if(ValueUtil.isEmpty(this.getCloneGroupId())) {
			return false;

		// 배치 아이디와 클론 아이디가 같으면 false
		} else if(ValueUtil.isEqualIgnoreCase(this.getId(), this.getCloneGroupId())) {
			return false;

		// 주문 테이블을 조회
		} else {
			String sql = "select count(1) from tb_if_order where domain_id = :domainId and batch_id = :batchId and rownum = 1";
			Map<String,Object> paramMap = ValueUtil.newMap("domainId,batchId", this.domainId, this.id);
			return BeanUtil.get(IQueryManager.class).selectBySql(sql, paramMap, Integer.class) == 0;
		}
	}

	/**
	 * 복사된 작업에 대한 마스터 배치인지 체크
	 *
	 * @return
	 */
	public boolean isCloneBatchMaster() {
		if(this.isDpsBatch() || this.isQpsBatch()) {
			return false;
		}

		// 클론 아이디가 없으면 false
		if(ValueUtil.isEmpty(this.getCloneGroupId())) {
			return false;

		// 배치 아이디와 클론 아이디가 같으면 true
		} else if(ValueUtil.isEqualIgnoreCase(this.getId(), this.getCloneGroupId())) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * 작업 배치에 대한 진행율을 조회한다.
	 */
	public void selectBatchProgress() {
		String sql = "select nvl(sum(real_picked_qty), 0) as progress_cnt from tb_process where domain_id = :domainId and (13 = :domainId or job_batch_seq = :jobBatchSeq) and batch_id = :batchId";
		Map<String,Object> paramMap = ValueUtil.newMap("domainId,jobBatchSeq,batchId", this.domainId, this.jobBatchSeq, this.id);
		int progressCnt = BeanUtil.get(IQueryManager.class).selectBySql(sql, paramMap, Integer.class);

		if(progressCnt == 0 || this.mpsTotalCnt == 0) {
			this.setProgressRate(0f);
		} else {
			this.setProgressRate(Float.parseFloat(String.format("%.2f",(((float)progressCnt / (float)this.mpsTotalCnt) * 100))));
		}
	}

	/**
	 * DPS인 경우 작업 배치당 호기가 여러 개 매핑이 가능하므로 작업 배치당 호기 정보를 설정한다.
	 */
	public void setJobRegionInfo() {
		String sql = "select region_nm from tb_job_batch_region where domain_id = :domainId and batch_id = :batchId";
		Map<String,Object> paramMap = ValueUtil.newMap("domainId,batchId", this.domainId, this.id);
		List<String> assignRegions = BeanUtil.get(IQueryManager.class).selectListBySql(sql, paramMap, String.class, 0, 0);
		this.setRegionNm(ValueUtil.listToString(assignRegions));
	}

	/**
	 * ID로 JobBatch 조회 후 리턴
	 *
	 * @parma domainId
	 * @param id 배치 ID
	 * @param exceptionWhenEmpty 조회 결과가 null이면 예외 발생 여부
	 * @return
	 */
	public static JobBatch findWithLock(Long domainId, String id, boolean exceptionWhenEmpty) {
		return find(domainId, id, true, exceptionWhenEmpty);
	}

	/**
	 * ID로 작업 배치 조회
	 *
	 * @param domainId 도메인 ID
	 * @param id 배치 ID
	 * @param withLock 테이블 락을 걸지 여부
	 * @param exceptionWhenEmpty 조회 결과가 null이면 예외 발생 여부
	 * @return
	 */
	public static JobBatch find(Long domainId, String id, boolean withLock, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter(new Filter(SysConstants.ENTITY_FIELD_ID, id));
		JobBatch jobBatch = withLock ?
				BeanUtil.get(IQueryManager.class).selectByConditionWithLock(JobBatch.class, condition) :
				BeanUtil.get(IQueryManager.class).selectByCondition(JobBatch.class, condition);

		if(jobBatch == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", id);
		}

		return jobBatch;
	}

	/**
	 * ID로 작업 배치 조회
	 *
	 * @param domainId 도메인 ID
	 * @param id 배치 ID
	 * @param exceptionWhenEmpty 조회 결과가 null이면 예외 발생 여부
	 * @return
	 */
	public static JobBatch find(Long domainId, String id, boolean exceptionWhenEmpty) {
		return find(domainId, id, false, exceptionWhenEmpty);
	}

	/**
	 * 상위 시스템의 배치 ID로 작업 배치 조회
	 *
	 * @param domainId
	 * @param jobId
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static JobBatch findByJobId(Long domainId, String jobId, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("jobId", jobId);
		JobBatch jobBatch = BeanUtil.get(IQueryManager.class).selectByCondition(JobBatch.class, condition);

		if(jobBatch == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch", jobId);
		}

		return jobBatch;
	}

	/**
	 * 작업 배치 id로 같은 배치 그룹에 해당하는 리스트 조회
	 *
	 * @param id
	 * @return
	 */
	public static List<JobBatch> findJobBatchList(String id) {
		String sql = "select * from tb_job_batch where batch_group_id = (select batch_group_id from tb_job_batch where id = :id)";
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql, ValueUtil.newMap("id", id), JobBatch.class, 0, 0);
	}

	/**
	 * batchGroupList에서 메인 배치 찾기
	 *
	 * @param batchGroupList
	 * @return
	 */
	public static JobBatch getMainBatch(List<JobBatch> batchGroupList) {
		JobBatch mainBatch = null;

		for(JobBatch batch : batchGroupList) {
			if(ValueUtil.isEqualIgnoreCase(batch.getBatchGroupId(), batch.getId())) {
				mainBatch = batch;
				break;
			}
		}

		return mainBatch;
	}

	/**
	 * 작업 중인 Batch에서 메인 배치 또는 작업배치 찾기
	 *
	 * @param domainId
	 * @return
	 */
	public static JobBatch getRunningBatch(Long domainId) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("status", JobBatch.STATUS_RUNNING);
		List<JobBatch> result = BeanUtil.get(IQueryManager.class).selectList(JobBatch.class, condition);
		
		if(result.size() > 1) {
			return JobBatch.getMainBatch(result);
		} else if(result.size() == 1) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 조건에 따른 주문 가공 데이터 건수를 조회하여 리턴
	 *
	 * @param filterNames
	 * @param filterOpers
	 * @param filterValues
	 * @return
	 */
	/*public int preprocessCountByCondition(String filterNames, String filterOpers, String filterValues) {
		Query condition = AnyOrmUtil.newConditionForExecution(this.domainId);
		condition.addFilter("batchId", this.id);

		if(ValueUtil.isNotEmpty(filterNames)) {
			String[] names = filterNames.split(SysConstants.COMMA);
			String[] opers = ValueUtil.isNotEmpty(filterOpers) ? filterOpers.split(SysConstants.COMMA) : SysConstants.EMPTY_STRING.split(SysConstants.COMMA);
			String[] values = ValueUtil.isNotEmpty(filterValues) ? filterValues.split(SysConstants.COMMA) : SysConstants.EMPTY_STRING.split(SysConstants.COMMA);

			for(int i = 0 ; i < names.length ; i++) {
				condition.addFilter(new Filter(names[i], opers[i], values[i]));
			}
		}

		if(this.isDasBatch() || this.isDas2Batch()) {
			return BeanUtil.get(IQueryManager.class).selectSize(DasPreprocess.class, condition);

		} else if(this.isRtnBatch()) {
			return BeanUtil.get(IQueryManager.class).selectSize(RtnPreprocess.class, condition);

		} else {
			return -1;
		}
	}*/

	/**
	 * 주문 정보(JobBatch)의 거래처 별 총 주문 개수와 주문 가공 정보(DasPreprocess)의 거래처 별 총 주문 개수를
	 * 거래처 별로 비교하여 같지 않은 거래처의 정보만 조회
	 *
	 * @param diffStandard
	 * @return
	 */
	/*public List<PreprocessStatus> dasOrderPreprocessDiffStatus(String diffStandard) {
		String sql = QueryStore.getDasPreprocessDiffStatusQuery(diffStandard);
		Map<String, Object> params = ValueUtil.newMap("domainId,batchId", this.domainId, this.id);
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql, params, PreprocessStatus.class, 0, 0);
	}*/

	/**
	 * 주문 정보(JobBatch)의 SKU 별 총 주문 개수와 주문 가공 정보(RtnPreprocess)의 SKU 별 총 주문 개수를
	 * SKU 별로 비교하여 같지 않은 거래처의 정보만 조회
	 *
	 * @param diffStandard
	 * @return
	 */
	/*public List<PreprocessStatus> rtnOrderPreprocessDiffStatus(String diffStandard) {
		String sql = QueryStore.getRtnPreprocessDiffStatusQuery(diffStandard);
		Map<String, Object> params = ValueUtil.newMap("domainId,batchId", this.domainId, this.id);
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql, params, PreprocessStatus.class, 0, 0);
	}*/

}
