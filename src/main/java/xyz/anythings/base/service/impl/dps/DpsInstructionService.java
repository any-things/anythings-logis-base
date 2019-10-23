package xyz.anythings.base.service.impl.dps;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.service.api.IInstructionService;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * dps 작업 지시 서비스 
 * @author yang
 *
 */
@Component("dpsInstructionService")
public class DpsInstructionService extends AbstractExecutionService implements IInstructionService{

	@Override
	public Map<String, Object> searchInstructionData(JobBatch batch, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int instructBatch(JobBatch batch, List<?> equipList, Object... params) {
		return 0;
	}

	@Override
	public int instructTotalpicking(JobBatch batch, List<?> equipList, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int mergeBatch(JobBatch mainBatch, JobBatch newBatch, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int cancelInstructionBatch(JobBatch batch) {
		// TODO Auto-generated method stub
		return 0;
	}

}
