package xyz.anythings.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.service.api.IClassificationService;
import xyz.anythings.sys.service.AbstractExecutionService;


/**
 * 분류 공통 (Picking & Assorting) 트랜잭션 서비스 기본 구현
 *  
 * @author yang
 */
public abstract class AbstractClassificationService extends AbstractExecutionService implements IClassificationService {
	
	/**
	 * 서비스 디스패처
	 */
	@Autowired
	protected LogisServiceDispatcher serviceDispatcher;
	
	@Override
	public JobConfigSet getJobConfigSet(String batchId) {
		return this.serviceDispatcher.getConfigSetService().getConfigSet(batchId);
	}

}
