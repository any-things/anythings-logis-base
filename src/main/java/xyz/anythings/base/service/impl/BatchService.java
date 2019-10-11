package xyz.anythings.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.sys.service.AbstractQueryService;

/**
 * IBatchService 기본 구현
 * 
 * @author shortstop
 */
@Component
public class BatchService extends AbstractQueryService implements IBatchService {

	@Override
	public String newJobBatchId(Long domainId, String stageCd, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BatchProgressRate dailyProgressRate(Long domainId, String stageCd, String jobDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BatchProgressRate batchProgressRate(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobBatch findRunningBatch(Long domainId, String stageCd, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobBatch> searchRunningBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobBatch> searchRunningMainBatchList(Long domainId, String stageCd, String jobType, String jobDate) {
		// TODO Auto-generated method stub
		return null;
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
