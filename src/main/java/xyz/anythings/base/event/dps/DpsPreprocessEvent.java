package xyz.anythings.base.event.dps;

import xyz.anythings.base.event.main.BatchRootEvent;

/**
 * DPS 주문가공 이벤트 
 * @author yang
 *
 */
public class DpsPreprocessEvent extends BatchRootEvent {

	/**
	 * 가공 타입 
	 */
	private String type;

	
	public DpsPreprocessEvent(short eventStep) {
		super(eventStep);
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
