package xyz.anythings.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 물류 분류 최상위 서비스
 * 
 * @author shortstop
 */
public class AbstractAssortService extends AbstractLogisService {

	/**
	 * Event Publisher
	 */
	@Autowired
	protected ApplicationEventPublisher eventPublisher;
	
}
