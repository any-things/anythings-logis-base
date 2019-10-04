package xyz.anythings.base.event.main;

/**
 * 작업마감 이벤트 
 * @author yang
 *
 */
public class BatchEndEvent extends BatchRootEvent {

	public BatchEndEvent(short eventStep) {
		super(eventStep);
	}
}
