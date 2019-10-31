package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.Gateway;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.service.api.IIndicationService;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * 표시기 점,소등 서비스 기본 구현 - 각 분류 설비 모듈별로 이 클래스를 확장해서 구현
 * 
 * @author shortstop
 */
public class AbstractIndicationService extends AbstractExecutionService implements IIndicationService {

	@Override
	public List<Gateway> searchGateways(JobBatch batch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobInstance> searchJobList(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobInstance> indicatorsOn(boolean relight, List<JobInstance> jobList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void indicatorOnForPick(JobInstance job, Integer firstQty, Integer secondQty, Integer thirdQty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicatorOnForFullbox(JobInstance job) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicatorOnForPickEnd(JobInstance job, boolean finalEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicatorOff(Long domainId, String gwCd, String indCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForBoxMapping(Long domainId, String gwCd, String indCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForBoxMapping(Long domainId, String indCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForNoBoxError(Long domainId, String gwCd, String indCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForNoBoxError(Long domainId, String indCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForString(Long domainId, String gwCd, String indCd, String firstSegStr, String secondSegStr,
			String thirdSegStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForString(Long domainId, String indCd, String firstSegStr, String secondSegStr,
			String thirdSegStr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForCellCd(Long domainId, String gwCd, String indCd, String cellCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForCellCd(Long domainId, String indCd, String cellCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForIndCd(Long domainId, String gwCd, String indCd, String cellCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayForIndCd(Long domainId, String indCd, String cellCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void indicatorsOnByInput(JobInput input, List<JobInstance> jobList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreIndicatorsOn(JobBatch batch, String equipZone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreIndicatorsOn(JobBatch batch, Gateway gw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreIndicatorsOn(JobBatch batch, int inputSeq, String equipZone, String mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String nextIndicatorColor(JobInstance job, String prevColor) {
		// TODO Auto-generated method stub
		return null;
	}

}
