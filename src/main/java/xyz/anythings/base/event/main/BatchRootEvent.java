package xyz.anythings.base.event.main;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.EventConstants;
import xyz.anythings.base.event.IBatchBasedEvent;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.event.model.SysEvent;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 관련 최상위 이벤트 구현
 *  
 * @author yang
 */
public class BatchRootEvent extends SysEvent implements IBatchBasedEvent {
	/**
	 * 이벤트 스텝 - 1 : 전 처리 , 2 : 후 처리
	 */
	protected short eventStep;
	/**
	 * 다음 이벤트를 계속 발생 할건지 여부, DEFAULT => false
	 */
	protected boolean isAfterEventCancel;
	/**
	 * 작업 배치
	 */
	protected JobBatch jobBatch;
	/**
	 * 스테이지 코드
	 */
	protected String stageCd;
	/**
	 * 작업 유형
	 */
	protected String jobType;
	/**
	 * 설비 유형
	 */
	protected String equipType;
	/**
	 * 이벤트 소스 
	 */
	protected String eventSource;
	/**
	 * 이벤트 타겟 
	 */
	protected String eventTarget;
	
	/**
	 * 생성자 1 
	 * 
	 * @param eventStep
	 */
	public BatchRootEvent(short eventStep) {
		super();
		
		this.setEventStep(eventStep);
		this.setAfterEventCancel(false);
	}
	
	/**
	 * 생성자 2
	 * 
	 * @param domainId
	 * @param eventStep
	 */
	public BatchRootEvent(long domainId, short eventStep) {
		super(domainId);
		
		this.setEventStep(eventStep);
		this.setAfterEventCancel(false);
	}
	
	/**
	 * 생성자 3 
	 * 
	 * @param batch
	 * @param eventStep
	 */
	public BatchRootEvent(JobBatch batch, short eventStep) {
		super(batch.getDomainId());
		
		this.setJobBatch(batch);
		this.setEventStep(eventStep);
		this.setAfterEventCancel(false);
	}
	
	public short getEventStep() {
		return eventStep;
	}

	public void setEventStep(short eventStep) {
		this.eventStep = eventStep;
	}

	public boolean isAfterEventCancel() {
		return isAfterEventCancel;
	}

	public void setAfterEventCancel(boolean isAfterEventCancel) {
		this.isAfterEventCancel = isAfterEventCancel;
	}
	
	public JobBatch getJobBatch() {
		return this.jobBatch;
	}
	
	public void setJobBatch(JobBatch jobBatch) {
		this.jobBatch = jobBatch;
		
		if(jobBatch != null) {
			this.stageCd = jobBatch.getStageCd();
			this.equipType = jobBatch.getEquipType();
			this.jobType = jobBatch.getJobType();
		}
	}
	
	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getJobType() {
		return this.jobType;
	}
	
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	public String getEquipType() {
		return this.equipType;
	}
	
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	public String getEventTarget() {
		return eventTarget;
	}

	public void setEventTarget(String eventTarget) {
		this.eventTarget = eventTarget;
	}

	/**
	 * 이벤트 전/후 처리 결과 셋 리턴
	 * 
	 * @return 이벤트 전/후 처리 결과 셋
	 */
	public EventResultSet getEventResultSet() {
		EventResultSet resultSet = new EventResultSet();
		
		// 단독 이벤트
		if(ValueUtil.isEqual(this.getEventStep(), EventConstants.EVENT_STEP_ALONE)) {
			resultSet.setExecuted(this.isExecuted());
			resultSet.setResult(this.getResult());
		
		// 후 처리 이벤트
		} else if(ValueUtil.isEqual(this.getEventStep(), EventConstants.EVENT_STEP_AFTER)) {
			resultSet.setExecuted(this.isExecuted());
			resultSet.setResult(this.getResult());
		
		// 전 처리 이벤트
		} else {
			resultSet.setAfterEventCancel(this.isExecuted() && this.isAfterEventCancel());
			resultSet.setExecuted(this.isExecuted());
			resultSet.setResult(this.getResult());
		}
		
		return resultSet;
	}
}
