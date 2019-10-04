package xyz.anythings.base.event.das;

import xyz.anythings.sys.event.model.SysOutEvent;

/**
 * DAS 모듈 Out 이벤트 
 * @author yang
 *
 */
public class DasOutEvent extends SysOutEvent {

	public DasOutEvent(Long domainId, String from, String to, Object message) {
		super(domainId, from, to, message);
	}
}
