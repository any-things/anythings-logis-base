package xyz.anythings.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.anythings.sys.service.AbstractQueryService;

/**
 * 물류 최상위 서비스
 *  
 * @author shortstop
 */
public class AbstractLogisService extends AbstractQueryService {

	/**
	 * 물류 서비스 파인더
	 */
	@Autowired
	protected LogisServiceFinder logisServiceFinder;
	
}
