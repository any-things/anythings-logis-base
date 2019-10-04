package xyz.anythings.base.event.sorter;

import xyz.anythings.sys.event.model.SysOutEvent;

/**
 * SORTER 모듈 OUT 이벤트 
 * @author yang
 *
 */
public class SorterOutEvent extends SysOutEvent {

	public SorterOutEvent(Long domainId, String from, String to, Object message) {
		super(domainId, from, to, message);
	}
}
