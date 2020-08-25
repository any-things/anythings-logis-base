package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.query.store.BatchQueryStore;
import xyz.anythings.base.util.LogisBaseUtil;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.ValueUtil;

/**
 * 배치 서비스 Facade
 * 
 * @author shortstop
 */
@Component
public class BatchService extends AbstractLogisService {
	
	/**
	 * 쿼리 스토어
	 */
	@Autowired
	private BatchQueryStore batchQueryStore;
	
	public String newJobBatchId(Long domainId, String stageCd, Object... params) {
		return LogisBaseUtil.newJobBatchId(domainId, stageCd);
	}

	public BatchProgressRate dailyProgressRate(Long domainId, String stageCd, String jobDate) {
		String sql = this.batchQueryStore.getDailyProgressRateQuery();
		Map<String, Object> params = ValueUtil.newMap("domainId,jobDate,stageCd", domainId, jobDate, stageCd);
		return this.queryManager.selectBySql(sql, params, BatchProgressRate.class);
	}

	public JobBatch findRunningBatch(Long domainId, String stageCd, String equipType, String equipCd) {
		String filterNames = "domainId,stageCd,equipType,status";
		List<Object> filterValues = ValueUtil.newList(domainId, stageCd, equipType, JobBatch.STATUS_RUNNING);
		
		if(ValueUtil.isNotEmpty(equipCd)) {
			filterNames += ",equipCd";
			filterValues.add(equipCd);
		}
		
		return AnyEntityUtil.findEntityBy(domainId, false, JobBatch.class, filterNames, filterValues.toArray());
	}

	public List<JobBatch> searchRunningBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		return AnyEntityUtil.searchEntitiesBy(domainId, false, JobBatch.class, "stageCd,jobType,jobDate", stageCd, jobType, jobDate);
	}

	public List<JobBatch> searchRunningMainBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		String sql = "select * from job_batches where domain_id = :domainId and stage_cd = :stageCd and job_type = :jobType and job_date = :jobDate and id = batch_group_id";
		return AnyEntityUtil.searchItems(domainId, false, JobBatch.class, sql, "domainId,stageCd,jobType,jobDate", domainId, stageCd, jobType, jobDate);
	}
	
	public void isPossibleCloseBatch(JobBatch batch, boolean closeForcibly) {
		this.serviceDispatcher.getBatchService(batch).isPossibleCloseBatch(batch, closeForcibly);
	}

	public void closeBatch(JobBatch batch, boolean forcibly) {
		this.serviceDispatcher.getBatchService(batch).closeBatch(batch, forcibly);
	}

	public void isPossibleCloseBatchGroup(Long domainId, String batchGroupId, boolean closeForcibly) {
		JobBatch batch = this.findByBatchGroupId(domainId, batchGroupId);
		this.serviceDispatcher.getBatchService(batch).isPossibleCloseBatchGroup(domainId, batchGroupId, closeForcibly);
	}

	public int closeBatchGroup(Long domainId, String batchGroupId, boolean forcibly) {
		JobBatch batch = this.findByBatchGroupId(domainId, batchGroupId);
		return this.serviceDispatcher.getBatchService(batch).closeBatchGroup(domainId, batchGroupId, forcibly);
	}

	public void isPossibleCancelBatch(JobBatch batch) {
		this.serviceDispatcher.getBatchService(batch).isPossibleCancelBatch(batch);
	}

	private JobBatch findByBatchGroupId(Long domainId, String batchGroupId) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId, 1, 1);
		condition.addFilter("batchGroupId", batchGroupId);
		condition.addSelect("id", "job_type");
		List<JobBatch> batches = this.queryManager.selectList(JobBatch.class, condition);
		if(batches.isEmpty()) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.JobBatch");
		}
		
		return batches.get(0);
	}

}
