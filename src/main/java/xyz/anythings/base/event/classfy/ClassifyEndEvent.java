package xyz.anythings.base.event.classfy;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.IClassifyEndEvent;
import xyz.anythings.base.event.IClassifyEvent;

/**
 * 소분류 분류 완료 이벤트 구현
 * 
 * @author shortstop
 */
public class ClassifyEndEvent extends ClassifyEvent implements IClassifyEndEvent {
	/**
	 * 분류 처리 이벤트
	 */
	private IClassifyEvent classifyEvent;
	
	/**
	 * 생성자 1
	 * 
	 * @param batch
	 * @param eventStep
	 */
	public ClassifyEndEvent(JobBatch batch, short eventStep) {
		super(batch, eventStep);
	}
	
	/**
	 * 생성자 2
	 * 
	 * @param classifyEvent
	 */
	public ClassifyEndEvent(IClassifyEvent classifyEvent) {
		this(classifyEvent, EVENT_STEP_ALONE);
	}
	
	/**
	 * 생성자 3
	 * 
	 * @param classifyEvent
	 * @param eventStep
	 */
	public ClassifyEndEvent(IClassifyEvent classifyEvent, short eventStep) {
		super(eventStep);
		this.classifyEvent = classifyEvent;
		this.jobBatch = classifyEvent.getJobBatch();
	}

	@Override
	public IClassifyEvent getClassifyEvent() {
		return this.classifyEvent;
	}
	
	public void setClassifyEvent(IClassifyEvent classifyEvent) {
		this.classifyEvent = classifyEvent;
	}

}
