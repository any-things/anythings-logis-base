package xyz.anythings.base.event.main;

import java.util.List;

/**
 * 작업 지시 이벤트
 * 
 * @author yang
 */
public class BatchInstructEvent extends BatchRootEvent {
	/**
	 * 작업 시작 이벤트 타입 
	 * 10 : EVENT_INSTRUCT_TYPE_ORDER_TYPE
	 * 20 : EVENT_INSTRUCT_TYPE_INSTRUCT
	 * 30 : EVENT_INSTRUCT_TYPE_BOX_REQ
	 */
	private short eventType;
	/**
	 * 할당 대상 설비 리스트 
	 */
	private List<?> equipList;
	
	/**
	 * 생성자 1
	 * 
	 * @param domainId
	 * @param eventType
	 * @param eventStep
	 */
	public BatchInstructEvent(long domainId, short eventType, short eventStep) {
		super(domainId, eventStep);
		this.setEventType(eventType);
	}

	public short getEventType() {
		return eventType;
	}

	public void setEventType(short eventType) {
		this.eventType = eventType;
	}
	
	public List<?> getEquipList() {
		return this.equipList;
	}
	
	public void setEquipList(List<?> equipList) {
		this.equipList = equipList;
	}

}
