package xyz.anythings.base.event.classfy;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.IClassifyOutEvent;

/**
 * 소분류 Out 이벤트 구현
 * 
 * @author shortstop
 */
public class ClassifyOutEvent extends ClassifyEvent implements IClassifyOutEvent {

	/**
	 * 소분류 Out 이벤트 생성자 1
	 * 
	 * @param batch
	 * @param eventStep
	 * @param result
	 */
	public ClassifyOutEvent(JobBatch batch, short eventStep, Object result) {
		super(batch, eventStep);
		this.setResult(result);
	}

}
