package xyz.anythings.base.service.api;

/**
 * 송장 번호 관리 서비스
 * 	> 송장 번호 범위 수신
 * 	> 송장 번호 생성
 * 	> 송장 번호 부여
 *  
 * @author shortstop
 */
public interface IInvoiceService {

	/**
	 * 송장 번호 대역을 받아 개별 송장 번호를 생성하는 서비스
	 * 
	 * @param domainId
	 * @param comCd
	 * @param fromNo
	 * @param toNo
	 * @return 생성한 송장 번호 개수 리턴
	 */
	public int generateInvoiceNoByRange(Long domainId, String comCd, long fromNo, long toNo);
	
	/**
	 * 사용 가능한 다음 송장 번호를 추출하여 리턴.
	 * 
	 * @param domainId
	 * @param comCd
	 * @return
	 */
	public String nextInvoiceId(Long domainId, String comCd);
	
}
