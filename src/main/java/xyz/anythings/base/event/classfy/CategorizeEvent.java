package xyz.anythings.base.event.classfy;

import xyz.anythings.base.event.ICategorizeEvent;
import xyz.anythings.base.event.main.BatchRootEvent;

/**
 * 중분류 이벤트 구현
 * 
 * @author shortstop
 */
public class CategorizeEvent extends BatchRootEvent implements ICategorizeEvent {
	/**
	 * 배치 그룹 ID
	 */
	private String batchGroupId;
	/**
	 * 투입 유형 
	 */
	private String inputType;
	/**
	 * 투입 코드
	 */
	private String inputCode;
	
	/**
	 * 중분류 이벤트 생성자 
	 * 
	 * @param domainId
	 * @param eventStep
	 * @param stageCd
	 * @param batchGroupId
	 * @param jobType
	 * @param inputType
	 * @param inputCode
	 */
	public CategorizeEvent(long domainId, short eventStep, String stageCd, String batchGroupId, String jobType, String inputType, String inputCode) {
		super(domainId, eventStep);
		
		this.setStageCd(stageCd);
		this.setBatchGroupId(batchGroupId);
		this.setJobType(jobType);
		this.setInputType(inputType);
		this.setInputCode(inputCode);
	}

	@Override
	public String getBatchGroupId() {
		return this.batchGroupId;
	}

	@Override
	public void setBatchGroupId(String batchGroupId) {
		this.batchGroupId = batchGroupId;
	}

	@Override
	public String getInputType() {
		return this.inputType;
	}

	@Override
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Override
	public String getInputCode() {
		return this.inputCode;
	}

	@Override
	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

}
