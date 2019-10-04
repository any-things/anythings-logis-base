package xyz.anythings.base.event.main;

/**
 * 작업 시작 이벤트 
 * @author yang
 *
 */
public class BatchStartEvent extends BatchRootEvent {

	public BatchStartEvent(short eventStep) {
		super(eventStep);
	}
}
