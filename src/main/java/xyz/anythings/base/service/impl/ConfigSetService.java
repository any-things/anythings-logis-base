package xyz.anythings.base.service.impl;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.IndConfigSet;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.service.api.IConfigSetService;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * IConfigSetService 구현
 * 
 * @author shortstop
 */
@Component
public class ConfigSetService extends AbstractExecutionService implements IConfigSetService {

	@Override
	public JobConfigSet copyJobConfigSet(String templateConfigSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobConfigSet buildJobConfigSet(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJobConfigValue(JobBatch batch, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearJobConfigSet(JobBatch batch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IndConfigSet copyIndConfigSet(String templateConfigSetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndConfigSet buildIndConfigSet(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIndConfigValue(JobBatch batch, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearIndConfigSet(JobBatch batch) {
		// TODO Auto-generated method stub
		
	}

}
