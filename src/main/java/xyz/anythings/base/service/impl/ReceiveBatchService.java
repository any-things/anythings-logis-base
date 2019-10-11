package xyz.anythings.base.service.impl;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.service.api.IReceiveBatchService;
import xyz.anythings.sys.service.AbstractExecutionService;

/**
 * 배치 수신 서비스
 * 
 * @author shortstop
 */
@Component
public class ReceiveBatchService extends AbstractExecutionService implements IReceiveBatchService {

	/**
	 * 상위 시스템으로 부터 구역, 스테이지, 고객사, 작업 일자로 배치 및 주문 수신을 위한 정보를 조회하여 리턴 
	 * - 사용자가 수신 받을 배치가 있는지 확인한 후 수신하도록 하기 위함  
	 * 
	 * @param domainId 도메인 ID
	 * @param areaCd 구역 코드 
	 * @param stageCd 스테이지 코드 
	 * @param comCd 고객사 코
	 * @param jobDate 작업 일자
	 * @param params 기타 파라미터
	 * @return
	 */
	public BatchReceipt readyToReceive(Long domainId, String areaCd, String stageCd, String comCd, String jobDate, Object ... params) {
		// TODO
		return null;
	}
	
	/**
	 * 상위 시스템으로 부터 배치, 주문을 수신
	 * 
	 * @param receiptSummary
	 * @return
	 */
	public BatchReceipt startToReceive(BatchReceipt receiptSummary) {
		// TODO
		return null;
	}
	
	/**
	 * 배치 수신 취소
	 * 
	 * @param batch
	 * @return
	 */
	public int cancelBatch(JobBatch batch) {
		// TODO
		return 0;
	}

}
