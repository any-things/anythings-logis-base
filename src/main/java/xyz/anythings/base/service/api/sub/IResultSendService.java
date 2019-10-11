package xyz.anythings.base.service.api.sub;
//package xyz.anythings.base.service.api;
//
//import java.util.List;
//
//import xyz.anythings.base.entity.JobProcess;
//
///**
// * 실적 전송 서비스
// * 
// * @author shortstop
// */
//public interface IResultSendService {
//	
//	/**
//	 * 피킹 실적 전송 여부 리턴
//	 * 
//	 * @param domainId
//	 * @return
//	 */
//	public boolean isPickResultSend(Long domainId);
//
//	/**
//	 * 사이트 별, 고객사 별 체크 포인트 별 박스 실적 전송 포인트 여부 조회
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @param checkPoint
//	 * @return
//	 */
//	public boolean isBoxResultSendPoint(Long domainId, String comCd, String checkPoint);
//	
//	/**
//	 * 주문 라인별 실적 전송
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object sendPickResult(Long domainId, Object ... parameters);
//	
//	/**
//	 * 박스별 실적 전송
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object sendBoxResult(Long domainId, Object ... parameters);
//	
//	/**
//	 * 피킹 확정 실적 후 처리
//	 * 
//	 * @param job
//	 * @param totalPickedQty
//	 * @return
//	 */
//	public Object handlePickData(JobProcess job, int totalPickedQty);
//	
//	/**
//	 * 피킹 실적 데이터 상위 시스템으로 전송
//	 *  
//	 * @param domainId
//	 * @param pickResults
//	 * @return
//	 */
//	public Object sendPickData(Long domainId, List<?> pickResults);
//	
//}
