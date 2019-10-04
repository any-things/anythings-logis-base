package xyz.anythings.base.event.sorter;

import xyz.anythings.base.event.main.BatchRootEvent;

/**
 * SORTER 주문가공 이벤트 
 * @author yang
 *
 */
public class SorterPreprocessEvent extends BatchRootEvent {
	
	/**
	 * 가공 타입 
	 */
	private String type;
	

	public SorterPreprocessEvent(short eventStep) {
		super(eventStep);
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}
