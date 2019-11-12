package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.IndConfigSet;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfigSet;

/**
 * 설정 셋 서비스 API 정의
 * 
 * @author shortstop
 */
public interface IConfigSetService {
	
	/**
	 * 템플릿 작업 설정 셋 생성
	 * 
	 * @param domainId
	 * @return
	 */
	public JobConfigSet buildTemplateJobConfigSet(Long domainId);
	
	/**
	 * templateConfigSetId로 작업 설정 셋 복사
	 * 
	 * @param domainId
	 * @param templateConfigSetId
	 * @param targetSetCd
	 * @param targetSetNm
	 * @return
	 */
	public JobConfigSet copyJobConfigSet(Long domainId, String templateConfigSetId, String targetSetCd, String targetSetNm);
 
	/**
	 * 작업 배치 정보로 작업 설정 셋 생성
	 * 
	 * @param batch
	 * @return
	 */
	public JobConfigSet buildJobConfigSet(JobBatch batch);
	
	/**
	 * 작업 배치와 설정 키로 작업 설정 값 조회
	 * 
	 * @param batch
	 * @param key
	 * @return
	 */
	public String getJobConfigValue(JobBatch batch, String key);
	
	/**
	 * 작업 배치와 설정 키로 작업 설정 값 조회
	 * 
	 * @param batchId
	 * @param key
	 * @return
	 */
	public String getJobConfigValue(String batchId, String key);
	
	/**
	 * 작업 배치와 설정 키로 작업 설정 값 조회, 값이 없으면 기본 값 리턴
	 * 
	 * @param batch
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getJobConfigValue(JobBatch batch, String key, String defaultValue);
	
	/**
	 * 작업 배치와 설정 키로 작업 설정 값 조회, 값이 없으면 기본 값 리턴
	 * 
	 * @param batchId
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getJobConfigValue(String batchId, String key, String defaultValue);
	
	/**
	 * 작업 배치 정보로 작업 설정 셋 리셋 (캐쉬 리셋)
	 * 
	 * @param batch
	 */
	public void clearJobConfigSet(JobBatch batch);
	
	/**
	 * 템플릿 표시기 설정 셋 생성
	 * 
	 * @param domainId
	 * @return
	 */
	public IndConfigSet buildTemplateIndConfigSet(Long domainId);
	
	/**
	 * templateConfigSetId로 표시기 설정 셋 복사
	 * 
	 * @param domainId
	 * @param templateConfigSetId
	 * @param targetSetCd
	 * @param targetSetNm
	 * @return
	 */
	public IndConfigSet copyIndConfigSet(Long domainId, String templateConfigSetId, String targetSetCd, String targetSetNm);
	
	/**
	 * 작업 배치 정보로 표시기 설정 셋 생성
	 * 
	 * @param batch
	 * @return
	 */
	public IndConfigSet buildIndConfigSet(JobBatch batch);
	
	/**
	 * 작업 배치와 설정 키로 표시기 설정 값 조회
	 * 
	 * @param batch
	 * @param key
	 * @return
	 */
	public String getIndConfigValue(JobBatch batch, String key);
	
	/**
	 * 작업 배치와 설정 키로 표시기 설정 값 조회
	 * 
	 * @param batchId
	 * @param key
	 * @return
	 */
	public String getIndConfigValue(String batchId, String key);
	
	/**
	 * 작업 배치와 설정 키로 표시기 설정 값 조회
	 * 
	 * @param batch
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getIndConfigValue(JobBatch batch, String key, String defaultValue);
	
	/**
	 * 작업 배치와 설정 키로 표시기 설정 값 조회
	 * 
	 * @param batchId
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getIndConfigValue(String batchId, String key, String defaultValue);
	
	/**
	 * 작업 배치 정보로 표시기 설정 셋 리셋 (캐쉬 리셋)
	 * 
	 * @param batch
	 */
	public void clearIndConfigSet(JobBatch batch);

}
