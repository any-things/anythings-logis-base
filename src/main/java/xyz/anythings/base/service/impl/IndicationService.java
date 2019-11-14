package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.gw.entity.Gateway;

/**
 * 표시기 점,소등 서비스 Facade
 * 
 * @author shortstop
 */
@Component
public class IndicationService extends AbstractLogisService {
	
	/**
	 * 1-1. 작업 배치로 게이트웨이 리스트를 조회
	 * 
	 * @param batch
	 * @return
	 */
	public List<Gateway> searchGateways(JobBatch batch) {
		return this.serviceDispatcher.getIndicationService(batch).searchGateways(batch);
	}
	
	/**
	 * 1-2. 조회 조건으로 표시기 점등을 위한 작업 리스트 조회
	 * 
	 * @param batch
	 * @param condition
	 * @return
	 */
	public List<JobInstance> searchJobsForIndOn(JobBatch batch, Map<String, Object> condition) {
		return this.serviceDispatcher.getIndicationService(batch).searchJobsForIndOn(batch, condition);
	}
	
	/**
	 * 1-3. 작업 리스트로 표시기 점등
	 * 
	 * @param batch
	 * @param relight 재점등 여부
	 * @param jobList 작업 리스트
	 * @return 표시기 점등된 작업 리스트
	 */
	public List<JobInstance> indicatorsOn(JobBatch batch, boolean relight, List<JobInstance> jobList) {
		return this.serviceDispatcher.getIndicationService(batch).indicatorsOn(batch, relight, jobList);
	}
	
	/**
	 * 1-4. 표시기에 피킹 정보 점등
	 * 
	 * @param job
	 * @param firstQty
	 * @param secondQty
	 * @param thirdQty
	 */
	public void indicatorOnForPick(JobInstance job, Integer firstQty, Integer secondQty, Integer thirdQty) {
		this.serviceDispatcher.getIndicationService(job.getJobType()).indicatorOnForPick(job, firstQty, secondQty, thirdQty);
	}
	
	/**
	 * 1-5. 표시기에 풀 박스 점등 요청
	 * 
	 * @param job
	 */
	public void indicatorOnForFullbox(JobInstance job) {
		this.serviceDispatcher.getIndicationService(job.getJobType()).indicatorOnForFullbox(job);
	}
	
	/**
	 * 1-6. 표시기에 분류 완료 표시 요청
	 * 
	 * @param job
	 * @param finalEnd
	 */
	public void indicatorOnForPickEnd(JobInstance job, boolean finalEnd) {
		this.serviceDispatcher.getIndicationService(job.getJobType()).indicatorOnForPickEnd(job, finalEnd);
	}
	
	/**
	 * 1-7. 표시기 소등 요청
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param gwCd
	 * @param indCd
	 */
	public void indicatorOff(Long domainId, String batchId, String jobType, String gwCd, String indCd) {
		this.serviceDispatcher.getIndicationService(jobType).indicatorOff(domainId, batchId, jobType, gwCd, indCd);
	}
	
	/**
	 * 1-8. 표시기에 박스 매핑 점등 요청
	 * 
	 * @param batch
	 * @param gwCd
	 * @param indCd
	 */
	public void displayForBoxMapping(JobBatch batch, String gwCd, String indCd) {
		this.serviceDispatcher.getIndicationService(batch).displayForBoxMapping(batch, gwCd, indCd);
	}
	
	/**
	 * 1-8. 표시기에 박스 매핑 점등 요청
	 * 
	 * @param batch
	 * @param indCd
	 */
	public void displayForBoxMapping(JobBatch batch, String indCd) {
		this.serviceDispatcher.getIndicationService(batch).displayForBoxMapping(batch, indCd);
	}
	
	/**
	 * 1-9. 표시기에 박스 매핑이 안 된 에러
	 * 
	 * @param batch
	 * @param gwCd
	 * @param indCd
	 */
	public void displayForNoBoxError(JobBatch batch, String gwCd, String indCd) {
		this.serviceDispatcher.getIndicationService(batch).displayForNoBoxError(batch, gwCd, indCd);
	}
	
	/**
	 * 1-9. 표시기에 박스 매핑이 안 된 에러
	 * 
	 * @param batch
	 * @param indCd
	 */
	public void displayForNoBoxError(JobBatch batch, String indCd) {
		this.serviceDispatcher.getIndicationService(batch).displayForNoBoxError(batch, indCd);
	}
	
	/**
	 * 1-10. 표시기에 문자열 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param gwCd
	 * @param indCd
	 * @param firstSegStr
	 * @param secondSegStr
	 * @param thirdSegStr
	 */
	public void displayForString(Long domainId, String batchId, String jobType, String gwCd, String indCd, String firstSegStr, String secondSegStr, String thirdSegStr) {
		this.serviceDispatcher.getIndicationService(jobType).displayForString(domainId, batchId, jobType, gwCd, indCd, firstSegStr, secondSegStr, thirdSegStr);
	}
	
	/**
	 * 1-10. 표시기에 문자열 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param indCd
	 * @param firstSegStr
	 * @param secondSegStr
	 * @param thirdSegStr
	 */
	public void displayForString(Long domainId, String batchId, String jobType, String indCd, String firstSegStr, String secondSegStr, String thirdSegStr) {
		this.serviceDispatcher.getIndicationService(jobType).displayForString(domainId, batchId, jobType, indCd, firstSegStr, secondSegStr, thirdSegStr);
	}
	
	/**
	 * 1-11. 표시기에 셀 코드 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param gwCd
	 * @param indCd
	 * @param cellCd
	 */
	public void displayForCellCd(Long domainId, String batchId, String jobType, String gwCd, String indCd, String cellCd) {
		this.serviceDispatcher.getIndicationService(jobType).displayForCellCd(domainId, batchId, jobType, gwCd, indCd, cellCd);
	}
	
	/**
	 * 1-11. 표시기에 셀 코드 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param indCd
	 * @param cellCd
	 */
	public void displayForCellCd(Long domainId, String batchId, String jobType, String indCd, String cellCd) {
		this.serviceDispatcher.getIndicationService(jobType).displayForCellCd(domainId, batchId, jobType, indCd, cellCd);
	}
	
	/**
	 * 1-12. 표시기에 표시기 코드 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param jobType
	 * @param gwCd
	 * @param indCd
	 * @param cellCd
	 */
	public void displayForIndCd(Long domainId, String batchId, String jobType, String gwCd, String indCd, String cellCd) {
		this.serviceDispatcher.getIndicationService(jobType).displayForIndCd(domainId, batchId, jobType, gwCd, indCd, cellCd);
	}
	
	/**
	 * 1-12. 표시기에 표시기 코드 표시
	 * 
	 * @param domainId
	 * @param batchId
	 * @param indCd
	 * @param cellCd
	 */
	public void displayForIndCd(Long domainId, String batchId, String jobType, String indCd, String cellCd) {
		this.serviceDispatcher.getIndicationService(jobType).displayForIndCd(domainId, batchId, jobType, indCd, cellCd);
	}
	
	/**
	 * 1-13. 투입 이벤트로 표시기 점등
	 * 
	 * @param batch
	 * @param input
	 * @param jobList
	 */
	public void indicatorsOnByInput(JobBatch batch, JobInput input, List<JobInstance> jobList) {
		this.serviceDispatcher.getIndicationService(batch).indicatorsOnByInput(batch, input, jobList);
	}
	
	/**
	 * 1-14. 설비별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param batch
	 * @param equipZone
	 */
	public void restoreIndicatorsOn(JobBatch batch, String equipZone) {
		this.serviceDispatcher.getIndicationService(batch).restoreIndicatorsOn(batch, equipZone);
	}
	
	/**
	 * 1-15. 작업 배치별, 게이트웨이 별 작업 리스트 조회 후 표시기 재점등
	 * 
	 * @param batch
	 * @param gw
	 */
	public void restoreIndicatorsOn(JobBatch batch, Gateway gw) {
		this.serviceDispatcher.getIndicationService(batch).restoreIndicatorsOn(batch, gw);
	}
	
	/**
	 * 1-16. 표시기 : 작업 배치, 투입 시퀀스, 장비 존 별 작업 리스트 조회 후 모드에 따라서 표시기 재점등
	 * 
	 * @param batch 작업 배치
	 * @param inputSeq 투입 순서
	 * @param equipZone 장비 존 코드
	 * @param mode todo : 작업 처리 할 표시기, done : 이미 처리한 표시기 
	 */
	public void restoreIndicatorsOn(JobBatch batch, int inputSeq, String equipZone, String mode) {
		this.serviceDispatcher.getIndicationService(batch).restoreIndicatorsOn(batch, inputSeq, equipZone, mode);
	}
	
	/**
	 * 1-17. 표시기 : 이전 표시기 버튼 색상으로 다음 표시기 색상을 결정
	 * 
	 * @param job
	 * @param prevColor
	 * @return
	 */
	public String nextIndicatorColor(JobInstance job, String prevColor) {
		return this.serviceDispatcher.getIndicationService(job.getJobType()).nextIndicatorColor(job, prevColor);
	}

}
