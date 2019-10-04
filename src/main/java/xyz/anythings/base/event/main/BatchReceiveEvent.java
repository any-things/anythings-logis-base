package xyz.anythings.base.event.main;

/**
 * 작업 수신 
 * @author yang
 *
 */
public class BatchReceiveEvent extends BatchRootEvent {
	
	
	public BatchReceiveEvent(short eventStep) {
		super(eventStep);
	}

}
