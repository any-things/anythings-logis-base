package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.BoxItem;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.service.api.IJobStatusService;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.elidom.dbist.dml.Page;

/**
 * 작업 상태 서비스 기본 구현 - 각 분류 설비 모듈별로 이 클래스를 확장해서 구현
 * 
 * @author shortstop
 */
public class AbstractJobStatusService extends AbstractExecutionService implements IJobStatusService {

	@Override
	public BatchProgressRate getBatchProgressSummary(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobInput> searchInputList(JobBatch batch, String stationCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<JobInput> paginateInputList(JobBatch batch, String stationCd, int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobInput findLatestInput(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer findNextInputSeq(JobBatch batch, String stationCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobInstance> searchInputJobList(JobBatch batch, int inputSeq, String stationCd) {
		// TODO Auto-generated method stub
		return null;
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
