package xyz.anythings.base.event.dps;

import xyz.anythings.sys.event.model.SysOutEvent;

/**
 * DPS 모듈 Out 이벤트 
 * @author yang
 *
 */
public class DpsOutEvent extends SysOutEvent {

	public DpsOutEvent(Long domainId, String from, String to, Object message) {
		super(domainId, from, to, message);
	}
}
