package xyz.anythings.base.service.model;

/**
 * 각 모듈별 실행 관련 설정 관리 모델
 * 
 * @author shortstop
 */
public interface IAssortSetting {

	/**
	 * 초기화 
	 */
	public void initialize();
	
	/**
	 * 설정 값 모두 저장 
	 */
	public void save();
	
	/**
	 * 작업 유형 리턴
	 * 
	 * @return
	 */
	public String getJobType();
	
	/**
	 * 작업 유형 설정
	 * 
	 * @param jobType
	 */
	public void setJobType(String jobType);
	
	/**
	 * 설정 값 리턴
	 * 
	 * @param settingKey
	 * @return
	 */
	public String getSettingValue(String settingKey);
	
	/**
	 * 설정 값 설정
	 * 
	 * @param settingKey
	 * @param settingVal
	 */
	public void setSettingValue(String settingKey, String settingVal);
}
