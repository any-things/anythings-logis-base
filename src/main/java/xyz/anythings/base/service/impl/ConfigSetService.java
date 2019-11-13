package xyz.anythings.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.IndConfig;
import xyz.anythings.base.entity.IndConfigSet;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfig;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.service.api.IConfigSetService;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.util.ValueUtil;

/**
 * IConfigSetService 구현
 * 
 * @author shortstop
 */
@Component
public class ConfigSetService extends AbstractExecutionService implements IConfigSetService {
	
	/**
	 * 작업 프로파일 셋 Copy Fields
	 */
	private static final String[] JOB_CONFIG_SET_COPY_FIELDS = new String[] { "stageCd", "jobType", "equipType", "equipCd", "comCd", "confSetCd", "confSetNm", "remark" };
	/**
	 * 표시기 프로파일 셋 Copy Fields
	 */
	private static final String[] IND_CONFIG_SET_COPY_FIELDS = new String[] { "stageCd", "indType", "jobType", "equipType", "equipCd", "comCd", "confSetCd", "confSetNm", "remark" };
	/**
	 * ConfigSet 
	 */
	private static final String[] CONFIG_COPY_FIELDS = new String[] { "category", "name", "description", "value", "remark", "config" };
	
	/**
	 * 배치 ID - 작업 설정 셋
	 */
	private Map<String, JobConfigSet> batchJobConfigSet = new HashMap<String, JobConfigSet>();
	/**
	 * 배치 ID - 표시기 설정 셋
	 */
	private Map<String, IndConfigSet> batchIndConfigSet = new HashMap<String, IndConfigSet>();

	@Override
	public int buildStageJobConfigSet(Long domainId) {
		String sql = "select id,domain_id,conf_set_cd,conf_set_nm,stage_cd from job_config_set where domain_id = :domainId and default_flag = :defaultFlag and stage_cd is not null and equip_type is null and equip_cd is null and job_type is null and com_cd is null";
		List<JobConfigSet> confSetList = LogisEntityUtil.searchItems(domainId, false, JobConfigSet.class, sql, "domainId,defaultFlag", domainId, true);
		
		if(ValueUtil.isNotEmpty(confSetList)) {
			for(JobConfigSet confSet : confSetList) {
				this.buildStageJobConfigSet(confSet);
			}
		}
		
		return confSetList.size();
	}

	@Override
	public JobConfigSet buildStageJobConfigSet(JobConfigSet configSet) {
		List<JobConfig> items = LogisEntityUtil.searchDetails(configSet.getDomainId(), JobConfig.class, "jobConfigSetId", configSet.getId());
		configSet.setItems(items);
		this.batchJobConfigSet.put(configSet.getStageCd(), configSet);
		return configSet;
	}
	
	@Override
	public JobConfigSet copyJobConfigSet(Long domainId, String templateConfigSetId, String targetSetCd, String targetSetNm) {
		// 1. templateConfigSetId로 템플릿 설정을 조회 
		JobConfigSet sourceSet = LogisEntityUtil.findEntityById(true, JobConfigSet.class, templateConfigSetId);
		JobConfigSet targetSet = AnyValueUtil.populate(sourceSet, new JobConfigSet(), JOB_CONFIG_SET_COPY_FIELDS);
		targetSet.setConfSetCd(targetSetCd);
		targetSet.setConfSetNm(targetSetNm);
		this.queryManager.insert(targetSet);
		// 2. 템플릿 설정 생성
		this.cloneSourceJobConfigItems(sourceSet, targetSet);
		// 3. 복사한 JobConfigSet 리턴
		return targetSet;
	}

	@Override
	public JobConfigSet buildJobConfigSet(JobBatch batch) {
//		// 1. 파라미터 생성
//		Map<String, Object> params = ValueUtil.newMap("P_IN_DOMAIN_ID,P_IN_BATCH_ID", batch.getDomainId(), batch.getId());
//		// 2. 프로시져 콜 
//		Map<?, ?> result = this.queryManager.callReturnProcedure("OP_FIND_JOB_CONFIG_SET", params, Map.class);
//		// 3. 결과 
//		String jobConfigSetId = (String)result.get("P_OUT_JOB_CONFIG_SET_ID");
//		
//		if(ValueUtil.isNotEmpty(jobConfigSetId)) {
//			JobConfigSet sourceSet = LogisEntityUtil.findEntityById(true, JobConfigSet.class, jobConfigSetId);
//			List<JobConfig> sourceItems = LogisEntityUtil.searchDetails(sourceSet.getDomainId(), JobConfig.class, "jobConfigSetId", sourceSet.getId());
//			sourceSet.setItems(sourceItems);
//			this.batchJobConfigSet.put(batch.getId(), sourceSet);
//			return sourceSet;
//		} else {
//			throw new ElidomRuntimeException("배치 ID [" + batch.getId() + "]와 매치되는 작업 설정 셋을 찾지 못했습니다.");
//		}
		
		if(ValueUtil.isNotEmpty(batch.getJobConfigSetId())) {
			JobConfigSet configSet = LogisEntityUtil.findEntityById(true, JobConfigSet.class, batch.getJobConfigSetId());
			List<JobConfig> sourceItems = LogisEntityUtil.searchDetails(configSet.getDomainId(), JobConfig.class, "jobConfigSetId", configSet.getId());
			configSet.setItems(sourceItems);
			this.batchJobConfigSet.put(batch.getId(), configSet);
			return configSet;
		} else {
			throw new ElidomRuntimeException("작업 배치 [" + batch.getId() + "]에 작업 설정 프로파일이 설정되지 않았습니다.");
		}
	}

	@Override
	public String getJobConfigValue(JobBatch batch, String key) {
		JobConfigSet configSet = this.batchJobConfigSet.get(batch.getId());

		if(configSet == null) {
			configSet = LogisEntityUtil.findEntityById(true, JobConfigSet.class, batch.getJobConfigSetId());
			configSet.setItems(LogisEntityUtil.searchDetails(batch.getDomainId(), JobConfig.class, "jobConfigSetId", batch.getJobConfigSetId()));
			this.batchJobConfigSet.put(batch.getId(), configSet);
		}
		
		return configSet != null ? configSet.findValue(key) : null;
	}
	
	@Override
	public String getJobConfigValue(String batchId, String key) {
		JobConfigSet configSet = this.batchJobConfigSet.get(batchId);
		
		if(configSet == null) {
			JobBatch batch = LogisEntityUtil.findEntityBy(Domain.currentDomainId(), true, false, JobBatch.class, "id,job_config_set_id", "id", batchId);
			configSet = LogisEntityUtil.findEntityById(true, JobConfigSet.class, batch.getJobConfigSetId());
			configSet.setItems(LogisEntityUtil.searchDetails(batch.getDomainId(), JobConfig.class, "jobConfigSetId", batch.getJobConfigSetId()));
			this.batchJobConfigSet.put(batchId, configSet);
		}
		
		return configSet != null ? configSet.findValue(key) : null;
	}

	@Override
	public String getJobConfigValue(JobBatch batch, String key, String defaultValue) {
		String value = this.getJobConfigValue(batch, key);
		return ValueUtil.isEmpty(value) ? defaultValue : value;
	}

	@Override
	public String getJobConfigValue(String batchId, String key, String defaultValue) {
		String value = this.getJobConfigValue(batchId, key);
		return ValueUtil.isEmpty(value) ? defaultValue : value;
	}

	@Override
	public void clearJobConfigSet(String batchId) {
		this.batchJobConfigSet.remove(batchId);
	}
	
	@Override
	public int buildStageIndConfigSet(Long domainId) {
		String sql = "select id,domain_id,conf_set_cd,conf_set_nm,stage_cd,ind_type from ind_config_set where domain_id = :domainId and default_flag = :defaultFlag and stage_cd is not null and equip_type is null and equip_cd is null and job_type is null and com_cd is null";
		List<IndConfigSet> confSetList = LogisEntityUtil.searchItems(domainId, false, IndConfigSet.class, sql, "domainId,defaultFlag", domainId, true);
		
		if(ValueUtil.isNotEmpty(confSetList)) {
			for(IndConfigSet confSet : confSetList) {
				this.buildStageIndConfigSet(confSet);
			}
		}
		
		return confSetList.size();
	}

	@Override
	public IndConfigSet buildStageIndConfigSet(IndConfigSet configSet) {
		List<IndConfig> items = LogisEntityUtil.searchDetails(configSet.getDomainId(), IndConfig.class, "indConfigSetId", configSet.getId());
		configSet.setItems(items);
		this.batchIndConfigSet.put(configSet.getStageCd(), configSet);
		return configSet;
	}
	
	@Override
	public IndConfigSet copyIndConfigSet(Long domainId, String templateConfigSetId, String targetSetCd, String targetSetNm) {
		// 1. templateConfigSetId로 템플릿 설정을 조회 
		IndConfigSet sourceSet = LogisEntityUtil.findEntityById(true, IndConfigSet.class, templateConfigSetId);
		IndConfigSet targetSet = AnyValueUtil.populate(sourceSet, new IndConfigSet(), IND_CONFIG_SET_COPY_FIELDS);
		targetSet.setConfSetCd(targetSetCd);
		targetSet.setConfSetNm(targetSetNm);
		this.queryManager.insert(targetSet);
		// 2. 템플릿 설정 생성
		this.cloneSourceIndConfigItems(sourceSet, targetSet);
		// 3. 복사한 JobConfigSet 리턴
		return targetSet;
	}

	@Override
	public IndConfigSet buildIndConfigSet(JobBatch batch) {
//		// 1. 파라미터 생성
//		Map<String, Object> params = ValueUtil.newMap("P_IN_DOMAIN_ID,P_IN_BATCH_ID", batch.getDomainId(), batch.getId());
//		// 2. 프로시져 콜 
//		Map<?, ?> result = this.queryManager.callReturnProcedure("OP_FIND_IND_CONFIG_SET", params, Map.class);
//		// 3. 결과 
//		String jobConfigSetId = (String)result.get("P_OUT_IND_CONFIG_SET_ID");
//		
//		if(ValueUtil.isNotEmpty(jobConfigSetId)) {
//			IndConfigSet sourceSet = LogisEntityUtil.findEntityById(true, IndConfigSet.class, jobConfigSetId);
//			List<IndConfig> sourceItems = LogisEntityUtil.searchDetails(sourceSet.getDomainId(), IndConfig.class, "indConfigSetId", sourceSet.getId());
//			sourceSet.setItems(sourceItems);
//			this.batchIndConfigSet.put(batch.getId(), sourceSet);
//			return sourceSet;
//		} else {
//			throw new ElidomRuntimeException("배치 ID [" + batch.getId() + "]와 매치되는 표시기 설정 셋을 찾지 못했습니다.");
//		}
		
		if(ValueUtil.isNotEmpty(batch.getIndConfigSetId())) {
			IndConfigSet configSet = LogisEntityUtil.findEntityById(true, IndConfigSet.class, batch.getIndConfigSetId());
			List<IndConfig> sourceItems = LogisEntityUtil.searchDetails(configSet.getDomainId(), IndConfig.class, "indConfigSetId", configSet.getId());
			configSet.setItems(sourceItems);
			this.batchIndConfigSet.put(batch.getId(), configSet);
			return configSet;
		} else {
			throw new ElidomRuntimeException("작업 배치 [" + batch.getId() + "]에 표시기 설정 프로파일이 설정되지 않았습니다.");
		}		
	}

	@Override
	public String getIndConfigValue(JobBatch batch, String key) {
		IndConfigSet configSet = this.batchIndConfigSet.get(batch.getId());
		
		if(configSet == null) {
			configSet = LogisEntityUtil.findEntityById(true, IndConfigSet.class, batch.getIndConfigSetId());
			configSet.setItems(LogisEntityUtil.searchDetails(batch.getDomainId(), IndConfig.class, "indConfigSetId", batch.getIndConfigSetId()));
			this.batchIndConfigSet.put(batch.getId(), configSet);
		}
		
		return configSet == null ? null : configSet.findValue(key);
	}
	
	@Override
	public String getIndConfigValue(String batchId, String key) {
		IndConfigSet configSet = this.batchIndConfigSet.get(batchId);
		
		if(configSet == null) {
			JobBatch batch = LogisEntityUtil.findEntityBy(Domain.currentDomainId(), true, false, JobBatch.class, "id,indConfigSetId", "id", batchId);
			configSet = LogisEntityUtil.findEntityById(true, IndConfigSet.class, batch.getIndConfigSetId());
			configSet.setItems(LogisEntityUtil.searchDetails(batch.getDomainId(), IndConfig.class, "indConfigSetId", batch.getIndConfigSetId()));
			this.batchIndConfigSet.put(batchId, configSet);
		}
		
		return configSet == null ? null : configSet.findValue(key);
	}

	@Override
	public String getIndConfigValue(JobBatch batch, String key, String defaultValue) {
		String value = this.getIndConfigValue(batch, key);
		return ValueUtil.isEmpty(value) ? defaultValue : value;
	}

	@Override
	public String getIndConfigValue(String batchId, String key, String defaultValue) {
		String value = this.getIndConfigValue(batchId, key);
		return ValueUtil.isEmpty(value) ? defaultValue : value;
	}

	@Override
	public void clearIndConfigSet(String batchId) {
		this.batchIndConfigSet.remove(batchId);
	}

	/**
	 * sourceSet의 설정 항목을 targetSet의 설정 항목으로 복사
	 * 
	 * @param sourceSet
	 * @param targetSet
	 */
	protected void cloneSourceJobConfigItems(JobConfigSet sourceSet, JobConfigSet targetSet) {
		List<JobConfig> sourceItems = LogisEntityUtil.searchDetails(sourceSet.getDomainId(), JobConfig.class, "jobConfigSetId", sourceSet.getId());
		
		if(ValueUtil.isNotEmpty(sourceItems)) {
			List<JobConfig> targetItems = new ArrayList<JobConfig>(sourceItems.size());
						
			for(JobConfig sourceItem : sourceItems) {
				JobConfig targetItem = AnyValueUtil.populate(sourceItem, new JobConfig(), CONFIG_COPY_FIELDS);
				targetItem.setJobConfigSetId(targetSet.getId());
				targetItems.add(targetItem);
			}
			
			this.queryManager.insertBatch(targetItems);
			targetSet.setItems(targetItems);
		}		
	}
	
	/**
	 * sourceSet의 설정 항목을 targetSet의 설정 항목으로 복사
	 * 
	 * @param sourceSet
	 * @param targetSet
	 */
	protected void cloneSourceIndConfigItems(IndConfigSet sourceSet, IndConfigSet targetSet) {
		List<IndConfig> sourceItems = LogisEntityUtil.searchDetails(sourceSet.getDomainId(), IndConfig.class, "indConfigSetId", sourceSet.getId());
		
		if(ValueUtil.isNotEmpty(sourceItems)) {
			List<IndConfig> targetItems = new ArrayList<IndConfig>(sourceItems.size());
						
			for(IndConfig sourceItem : sourceItems) {
				IndConfig targetItem = AnyValueUtil.populate(sourceItem, new IndConfig(), CONFIG_COPY_FIELDS);
				targetItem.setIndConfigSetId(targetSet.getId());
				targetItems.add(targetItem);
			}
			
			this.queryManager.insertBatch(targetItems);
			targetSet.setItems(targetItems);
		}
	}

}
