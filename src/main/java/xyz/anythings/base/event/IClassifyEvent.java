package xyz.anythings.base.event;

import xyz.anythings.base.entity.JobBatch;

/**
 * 소분류(In, 분류, Out)를 위한 공통 이벤트
 * 
 * @author shortstop
 */
public interface IClassifyEvent {

	/**
	 * 작업 배치 리턴 
	 * 
	 * @return
	 */
	public JobBatch getJobBatch();
	
	/**
	 * 작업 배치 설정
	 * 
	 * @param batch
	 */
	public void setJobBatch(JobBatch batch);
	
	/**
	 * 이벤트 소스 리턴 - 이벤트 발생 모듈명 (TODO 모듈명 룰 확정 필요)
	 * 
	 * @return
	 */
	public String getEventSource();
	
	/**
	 * 이벤트 소스 설정
	 * 
	 * @param eventSource
	 */
	public void setEventSource(String eventSource);
	
	/**
	 * 이벤트 타겟 리턴 - 이벤트를 수신할 모듈명
	 * 
	 * @return
	 */
	public String getEventTarget();
	
	/**
	 * 이벤트 타겟 설정
	 * 
	 * @param eventTarget
	 */
	public void setEventTarget(String eventTarget);

}
