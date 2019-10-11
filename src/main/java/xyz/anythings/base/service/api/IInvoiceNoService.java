package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;

/**
 * 송장 번호 관리 서비스
 * 	1. 송장 번호 생성
 * 	2. 송장 번호 부여
 *  
 * @author shortstop
 */
public interface IInvoiceNoService {

	/**
	 * 송장 번호 파라미터를 받아 송장 번호를 생성
	 * 
	 * @param domainId
	 * @param stageCd
	 * @param comCd
	 * @param params
	 * @return 생성한 송장 번호 개수 리턴
	 */
	public int generateInvoiceNo(Long domainId, String stageCd, String comCd, Object ... params);
	
	/**
	 * 사용 가능한 다음 송장 번호를 추출하여 리턴.
	 * 
	 * @param batch
	 * @return
	 */
	public String nextInvoiceId(JobBatch batch);
	
	/**
	 * 사용 가능한 다음 송장 번호를 추출하여 리턴.
	 * 
	 * @param box
	 * @return
	 */
	public String nextInvoiceId(BoxPack box);
	
}
