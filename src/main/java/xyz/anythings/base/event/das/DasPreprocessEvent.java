package xyz.anythings.base.event.das;

import xyz.anythings.base.event.main.BatchRootEvent;

/**
 * DPS 주문가공 이벤트 
 * @author yang
 *
 */
public class DasPreprocessEvent extends BatchRootEvent {

	
	/**
	 * 가공 타입 
	 */
	private String type;
	
	public DasPreprocessEvent(short eventStep) {
		super(eventStep);
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

}
