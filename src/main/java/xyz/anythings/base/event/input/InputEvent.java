package xyz.anythings.base.event.input;

import xyz.anythings.base.entity.JobInput;
import xyz.anythings.sys.event.model.SysEvent;

/**
 * 투입 이벤트
 * 
 * @author shortstop
 */
public class InputEvent extends SysEvent {
	
	/**
	 * 투입 정보
	 */
	private JobInput jobInput;
	/**
	 * 작업 유형
	 */
	private String jobType;

	/**
	 * 생성자 1
	 */
	public InputEvent() {
		super();
	}
	
	/**
	 * 생성자 2
	 * 
	 * @param domainId
	 */
	public InputEvent(Long domainId) {
		super(domainId);
	}
	
	/**
	 * 생성자 3
	 * 
	 * @param jobInput
	 * @param jobType
	 */
	public InputEvent(JobInput jobInput, String jobType) {
		this.domainId = jobInput.getDomainId();
		this.jobInput = jobInput;
		this.jobType = jobType;
	}

	public JobInput getJobInput() {
		return jobInput;
	}

	public void setJobInput(JobInput jobInput) {
		this.jobInput = jobInput;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

}
