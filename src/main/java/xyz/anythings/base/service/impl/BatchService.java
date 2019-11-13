package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.util.LogisBaseUtil;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.service.AbstractQueryService;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.util.ValueUtil;

/**
 * IBatchService 기본 구현
 * 
 * @author shortstop
 */
@Component
public class BatchService extends AbstractQueryService implements IBatchService {
	
	@Override
	public String newJobBatchId(Long domainId, String stageCd, Object... params) {
		return LogisBaseUtil.newJobBatchId(domainId);
	}

	@Override
	public BatchProgressRate dailyProgressRate(Long domainId, String stageCd, String jobDate) {
		// 1. 조회 조건 
		Map<String, Object> params = ValueUtil.newMap("P_IN_DOMAIN_ID,P_IN_JOB_DATE,P_IN_STAGE_CD", Domain.currentDomainId(), jobDate, stageCd);
		// 2. 프로시져 콜 
		Map<?, ?> progress = this.queryManager.callReturnProcedure("SP_DAILY_JOB_PROGRESS", params, Map.class);
		// 3. 최종 결과 리턴 
		BatchProgressRate progressRage = new BatchProgressRate();
		progressRage.parseResult(progress);
		return progressRage;
	}

	@Override
	public JobBatch findRunningBatch(Long domainId, String stageCd, String equipType, String equipCd) {
		String filterNames = "domainId,stageCd,equipType,status";
		List<Object> filterValues = ValueUtil.newList(domainId, stageCd, equipType, JobBatch.STATUS_RUNNING);
		
		if(ValueUtil.isNotEmpty(equipCd)) {
			filterNames += "equipCd";
			filterValues.add(equipCd);
		}
		
		return LogisEntityUtil.findEntityBy(domainId, false, JobBatch.class, filterNames, filterValues.toArray());
	}

	@Override
	public List<JobBatch> searchRunningBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		return LogisEntityUtil.searchEntitiesBy(domainId, false, JobBatch.class, "stageCd,jobType,jobDate", stageCd, jobType, jobDate);
	}

	@Override
	public List<JobBatch> searchRunningMainBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		String sql = "select * from job_batches where domain_id = :domainId and stage_cd = :stageCd and job_type = :jobType and job_date = :jobDate and id = batch_group_id";
		return LogisEntityUtil.searchItems(domainId, false, JobBatch.class, sql, "domainId,stageCd,jobType,jobDate", domainId, stageCd, jobType, jobDate);
	}
	
	@Override
	public void isPossibleCloseBatch(JobBatch batch, boolean closeForcibly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int closeBatch(JobBatch batch, boolean forcibly) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void isPossibleCloseBatchGroup(Long domainId, String batchGroupId, boolean closeForcibly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int closeBatchGroup(Long domainId, String batchGroupId, boolean forcibly) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void isPossibleCancelBatch(JobBatch batch) {
		// TODO Auto-generated method stub
		
	}

}
