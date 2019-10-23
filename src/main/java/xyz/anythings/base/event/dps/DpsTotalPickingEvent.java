package xyz.anythings.base.event.dps;

import xyz.anythings.base.event.main.BatchRootEvent;

/**
 * DPS 토탈 피킹 지시 이벤트 
 * @author yang
 *
 */
public class DpsTotalPickingEvent extends BatchRootEvent {

	/**
	 * 가공 타입 
	 */
	private String type;

	
	public DpsTotalPickingEvent(short eventStep) {
		super(eventStep);
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
