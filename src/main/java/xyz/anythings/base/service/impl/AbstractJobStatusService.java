package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xyz.anythings.base.entity.BoxItem;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.query.store.BatchQueryStore;
import xyz.anythings.base.service.api.IJobStatusService;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 상태 서비스 기본 구현 - 각 분류 설비 모듈별로 이 클래스를 확장해서 구현
 * 
 * @author shortstop
 */
public abstract class AbstractJobStatusService extends AbstractExecutionService implements IJobStatusService {

	@Autowired
	protected BatchQueryStore batchQueryStore;
	
	@Override
	public BatchProgressRate getBatchProgressSummary(JobBatch batch) {
		String qry = this.batchQueryStore.getRackBatchProgressRateQuery();
		Map<String,Object> params = ValueUtil.newMap("domainId,batchId,equipType", batch.getDomainId(), batch.getId(), batch.getEquipType());
		
		// 배치에 호기가 지정되어 있으면 지정 된 호기에 대한 진행율 
		if(ValueUtil.isNotEmpty(batch.getEquipCd())) {
			params.put("equipCd", batch.getEquipCd());
		}
		
		return this.queryManager.selectBySql(qry, params, BatchProgressRate.class);
	}

	@Override
	public JobInput findLatestInput(JobBatch batch) {
		String qry = this.batchQueryStore.getLatestJobInputQuery();
		Map<String,Object> paramMap = ValueUtil.newMap("domainId,batchId,equipType", batch.getDomainId(), batch.getId(), batch.getEquipType());
		
		if(ValueUtil.isNotEmpty(batch.getEquipCd())) {
			paramMap.put("equipCd", batch.getEquipCd());
		}
		
		return this.queryManager.selectBySql(qry, paramMap, JobInput.class);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Integer findNextInputSeq(JobBatch batch) {
		// 작업 배치의 마지막 투입 시퀀스를 조회 후 하나 올려서 리턴
		JobBatch findBatch = AnyEntityUtil.findEntityByIdWithLock(true, JobBatch.class, batch.getId());
		int lastInputSeq = (findBatch.getLastInputSeq() == null) ? 1 : findBatch.getLastInputSeq() + 1;
		batch.setLastInputSeq(lastInputSeq);
		this.queryManager.update(batch, "lastInputSeq");
		return lastInputSeq;
	}

	@Override
	public List<JobInstance> searchJobList(JobBatch batch, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobInstance findUnboxedJob(JobBatch batch, String subEquipCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoxPack findLatestBox(JobBatch batch, String subEquipCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<BoxPack> paginateBoxList(JobBatch batch, Map<String, Object> condition, int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoxPack> searchBoxList(JobBatch batch, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoxItem> searchBoxItems(Long domainId, String boxPackId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalOrderQtyByJob(JobInstance job) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalPickedQtyByJob(JobInstance job) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalPickQtyByJob(JobInstance job) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int toPcsQty(Integer boxInQty, Integer boxQty, Integer pcsQty) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int toPcsQty(JobInstance job, Integer boxQty, Integer pcsQty) {
		// TODO Auto-generated method stub
		return 0;
	}

}
