package xyz.anythings.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.IndConfigSet;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.entity.Stage;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.util.LogisBaseUtil;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.service.AbstractQueryService;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobBatch findRunningBatch(Long domainId, String stageCd, String equipType, String equipCd) {
		// equipCd 가 지정 된 경우에는 해당 equip 에 대한 작업 상태를 확인
		// equipCd 가 없는 경우에는 equip_type 까지만 작업 상태를 확인 
		
		// 1. stage 조회 
		Stage stage = LogisEntityUtil.findEntityBy(domainId, false, Stage.class, null, "stageCd", stageCd);
		
		String filterNames = "areaCd,stageCd,equipType,status";
		List<Object> filterValues = ValueUtil.newList(stage.getAreaCd(), stageCd, equipType, JobBatch.STATUS_RUNNING);
		
		// 2. equipCd가 지정된 경우 조건이 다름 
		if(ValueUtil.isNotEmpty(equipCd)) {
			filterNames += "equipCd";
			filterValues.add(equipCd);
		}
		
		return LogisEntityUtil.findEntityBy(domainId, false, JobBatch.class, filterNames, filterValues.toArray());
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
	public JobConfigSet findJobConfigSet(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndConfigSet findIndConfigSet(JobBatch batch) {
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
