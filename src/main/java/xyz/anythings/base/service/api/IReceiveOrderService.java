package xyz.anythings.base.service.api;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.ReceiptSummary;
import xyz.elidom.sys.entity.Domain;

/**
 * 주문 수신을 위한 서비스 인터페이스
 * 
 * @author shortstop
 */
@Component
public interface IReceiveOrderService {

	/**
	 * 상위 시스템으로 부터 센터 코드, 고객사 코드, 작업 일자로 주문 수신 서머리 정보 생성 
	 * 
	 * @param domainId
	 * @param receiptType
	 * @param dcCd
	 * @param comCd
	 * @param jobDate
	 * @param batchSeq
	 * @return
	 */
	public ReceiptSummary createReceiptSummary(Long domainId, String receiptType, String dcCd, String comCd, String jobDate, Integer batchSeq);
	
	/**
	 * 현재 진행 중인 공통 마스터 수신이 있는지 체크
	 * 
	 * @param domainId
	 */
	public void checkReceiptSummaryRunnable(ReceiptSummary summary);
		
	/**
	 * 수신 주문 서머리 상태 변경
	 * 
	 * @param domainId
	 * @param summary
	 * @param status
	 */
	public void changeStatusReceiptSummary(Long domainId, ReceiptSummary summary, String status);
	
	/**
	 * 상위 시스템으로 부터 센터 코드, 고객사 코드, 작업 일자로 주문 정보 수신 
	 * 
	 * @param domain
	 * @param summary
	 * @return
	 */
	public Object receiveOrders(Domain domain, ReceiptSummary summary);
	
	/**
	 * 상위 시스템으로 부터 서머리 정보로 주문 정보 수신
	 * 
	 * @param summary
	 * @return
	 */
	public Object receiveOrdersFastly(ReceiptSummary summary);
	
	/**
	 * 상위 시스템으로 부터 센터 코드, 고객사 코드, 작업 일자로 주문 정보 수신 
	 * 
	 * @param domainId
	 * @param dcCd
	 * @param comCd
	 * @param jobDate
	 * @param batchSeq
	 * @return
	 */
	public Object receiveOrders(Long domainId, String dcCd, String comCd, String jobDate, Integer batchSeq);
	
	/**
	 * 상위 시스템으로 부터 작업 배치별 주문 정보 수신
	 * 
	 * @param batch
	 * @return
	 */
	public Object receiveOrder(JobBatch batch);
	
}
