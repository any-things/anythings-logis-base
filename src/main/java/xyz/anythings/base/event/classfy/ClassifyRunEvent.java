package xyz.anythings.base.event.classfy;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.event.IClassifyRunEvent;

/**
 * 소분류 분류 이벤트 구현
 * 
 * @author shortstop
 */
public class ClassifyRunEvent extends ClassifyEvent implements IClassifyRunEvent {
	/**
	 * 분류 장비
	 */
	protected String classfyDevice;
	/**
	 * 분류 액션
	 */
	protected String classifyAction;
	/**
	 * 작업 인스턴스
	 */
	protected JobInstance jobInstance;
	/**
	 * 작업 인스턴스 ID
	 */
	protected String jobInstanceId;
	/**
	 * 작업이 일어난 셀 코드
	 */
	protected String cellCd;
	/**
	 * 처리 요청 수량
	 */
	protected int reqQty;
	/**
	 * 처리 결과 수량
	 */
	protected int resQty;
	
	/**
	 * 소분류 분류 이벤트 생성자 1
	 * 
	 * @param batch
	 * @param eventStep
	 * @param classifyDevice
	 * @param classifyAction
	 * @param job
	 * @param reqQty
	 * @param resQty
	 */
	public ClassifyRunEvent(JobBatch batch, short eventStep, String classifyDevice, String classifyAction, JobInstance job, int reqQty, int resQty) {
		super(batch, eventStep);
	
		this.setClassifyDevice(classifyDevice);
		this.setClassifyAction(classifyAction);
		this.setJobInstance(job);
		this.setReqQty(reqQty);
		this.setResQty(resQty);
	}
	
	/**
	 * 소분류 분류 이벤트 생성자 1
	 * 
	 * @param batch
	 * @param eventStep
	 * @param classifyDevice
	 * @param classifyAction
	 * @param job
	 * @param reqQty
	 * @param resQty
	 */
	public ClassifyRunEvent(JobBatch batch, short eventStep, String classifyDevice, String classifyAction, JobInstance job) {
		super(batch, eventStep);
	
		this.setClassifyDevice(classifyDevice);
		this.setClassifyAction(classifyAction);
		this.setJobInstance(job);
		this.setReqQty(reqQty);
		this.setResQty(resQty);
	}

	@Override
	public String getClassifyDevice() {
		return this.classfyDevice;
	}

	@Override
	public void setClassifyDevice(String classifyDevice) {
		this.classfyDevice = classifyDevice;
	}

	@Override
	public String getCellCd() {
		return this.cellCd;
	}

	@Override
	public void setCellCd(String cellCd) {
		this.cellCd = cellCd;
	}

	@Override
	public String getClassifyAction() {
		return this.classifyAction;
	}

	@Override
	public void setClassifyAction(String classifyAction) {
		this.classifyAction = classifyAction;
	}

	@Override
	public JobInstance getJobInstance() {
		return jobInstance;
	}

	@Override
	public void setJobInstance(JobInstance jobInstance) {
		this.jobInstance = jobInstance;
		
		if(jobInstance != null) {
			this.jobInstanceId = jobInstance.getId();
			this.cellCd = jobInstance.getSubEquipCd();
		}
	}

	@Override
	public String getJobInstanceId() {
		return this.jobInstanceId;
	}

	@Override
	public void setJobInstanceId(String jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	@Override
	public int getReqQty() {
		return this.reqQty;
	}

	@Override
	public void setReqQty(int reqQty) {
		this.reqQty = reqQty;
	}

	@Override
	public int getResQty() {
		return this.resQty;
	}

	@Override
	public void setResQty(int resQty) {
		this.resQty = resQty;
	}

}
