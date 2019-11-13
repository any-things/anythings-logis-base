package xyz.anythings.base.event.main;

/**
 * 배치 작업 마감 이벤트
 * 
 * @author yang
 */
public class BatchCloseEvent extends BatchRootEvent {

	public BatchCloseEvent(short eventStep) {
		super(eventStep);
	}
}
