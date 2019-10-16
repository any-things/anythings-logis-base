package xyz.anythings.base.event.main;

import java.util.Map;

import xyz.anythings.base.event.EventConstants;
import xyz.anythings.sys.event.model.EventResultSet;
import xyz.anythings.sys.event.model.SysEvent;
import xyz.elidom.util.ValueUtil;

/**
 * 작업 관련 Root 이벤트 
 * @author yang
 *
 */
public class BatchRootEvent extends SysEvent{
	
	/**
	 * Area 코드 
	 */
	protected String areaCd;
	
	/**
	 * 스테이지 코드 
	 */
	protected String stageCd;
	
	/**
	 * 화주 코드 
	 */
	protected String comCd;
	
	/**
	 * 작업 일자 
	 */
	protected String jobDate;
	
	/**
	 * 1 . BEFORE 
	 * 2 . AFTER
	 */
	protected short eventStep;
	
	/**
	 * DAS / DPS / SMS / ...... 
	 */
	protected String bizType;
	
	/**
	 * 작업 유형 
	 * DAS : DAS / RTN .....
	 * DPS : DPS / QPS .....
	 * SMS : .......
	 * .....
	 */
	protected String jobType;
	
	/**
	 * 다음 이벤트를 계속 발생 할건지 flag
	 * DEFAULT = false
	 */
	protected boolean isAfterEventCancel;
	
	public BatchRootEvent(short eventStep) {
		super();
		this.setEventStep(eventStep);
		this.setAfterEventCancel(false);
	}
	
	public BatchRootEvent(long domainId, short eventStep) {
		super(domainId);
		this.setEventStep(eventStep);
		this.setAfterEventCancel(false);
	}
	
	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getStageCd() {
		return stageCd;
	}

	public void setStageCd(String stageCd) {
		this.stageCd = stageCd;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public short getEventStep() {
		return eventStep;
	}

	public void setEventStep(short eventStep) {
		this.eventStep = eventStep;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public boolean isAfterEventCancel() {
		return isAfterEventCancel;
	}

	public void setAfterEventCancel(boolean isAfterEventCancel) {
		this.isAfterEventCancel = isAfterEventCancel;
	}
	
	/**
	 * 배치별 셋팅값 불러오기 
	 * 설비, 화주 , 호기 stack 구조 값 
	 * @return
	 */
	public Map<String,Object> getBatchSetting() {
		return ValueUtil.newMap("", "");
	}
	
	/**
	 * 이벤트 전/후 처리 결과 셋 가져오기 
	 * @return
	 */
	public EventResultSet getEventResultSet() {
		EventResultSet resultSet = new EventResultSet();
		
		if(ValueUtil.isEqual(this.getEventStep(), EventConstants.EVENT_STEP_AFTER)) {
			resultSet.setExecuted(this.isExecuted());
			resultSet.setResult(this.getResult());
		} else {
			resultSet.setAfterEventCancel(this.isExecuted() && this.isAfterEventCancel() ? true:false);
			resultSet.setExecuted(this.isExecuted());
			resultSet.setResult(this.getResult());
		}
		
		return resultSet;
	}
}
