package xyz.anythings.base.event.main;

/**
 * 작업 시작 이벤트 
 * @author yang
 *
 */
public class BatchInstructEvent extends BatchRootEvent {

	/**
	 * 작업 시작 이벤트 타입 
	 * 10 : EVENT_INSTRUCT_TYPE_START
	 * 20 : EVENT_INSTRUCT_TYPE_ORDER_TYPE
	 */
	private short eventType ;
	

	public BatchInstructEvent(short eventStep) {
		super(eventStep);
	}

	public short getEventType() {
		return eventType;
	}

	public void setEventType(short eventType) {
		this.eventType = eventType;
	}
}
