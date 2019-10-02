//package xyz.anythings.base.service.api;
//
//import java.util.List;
//import java.util.Map;
//
//import xyz.anythings.base.entity.JobBatch;
//import xyz.elidom.dbist.dml.Query;
//
///**
// * 주문 가공 서비스 API
// * 	1. 작업 지시를 위한 거래처 별 호기/로케이션 할당 정보 조회
// *  2. 작업 지시 - 주문 가공 정보로 부터 작업 지시 정보 생성
// *  3. 호기 복사를 위한 호기 리스트 조회
// *  4. 호기 복사 - 특정 호기의 로케이션 할당 정보를 다른 호기에 그대로 복사
// *  5. 작업 병합 - 메인 작업 배치에서 선택한 작업 배치를 배치 병합
// *  6. 작업 취소 - 작업 지시한 내용을 취소
// * 
// * @author shortstop
// */
//public interface IPreprocessService {
//
//	/**
//	 * 작업 배치별 주문 가공 셋을 조회한다.
//	 * 
//	 * @param batch
//	 * @param query
//	 * @return 작업 배치의 거래처 리스트, 주문 그룹 리스트, 호기 리스트, 거래처 별 물량 요약 정보, 호기별 물량 요약 정보 
//	 */
//	public Map<String, ?> searchPreprocessSet(JobBatch batch, Query query);
//	
//	/**
//	 * 작업 배치별 주문 가공 리스트 조회 
//	 * 
//	 * @param batch
//	 * @return
//	 */
//	public List<?> searchPreprocessList(JobBatch batch);
//	
//	/**
//	 * 할당 정보 리셋
//	 * 
//	 * @param batch
//	 * @param isRegionReset 로케이션 할당 정보만 리셋할 것인지 (false), 로케이션 할당 및 호기 할당 정보까지 리셋할 것인지 (true)
//	 */
//	public void resetAssignmentAll(JobBatch batch, boolean isRegionReset);
//	
//	/**
//	 * 주문 가공 정보를 생성한다.
//	 * 
//	 * @param batch
//	 * @return 주문 가공 생성 개수
//	 */
//	public int generatePreprocess(JobBatch batch);
//	
//	/**
//	 * 주문 가공 정보를 삭제한다.
//	 * 
//	 * @param batch
//	 * @return 삭제된 주문 가공 정보 개수
//	 */
//	public int deletePreprocess(JobBatch batch);
//	
//	/**
//	 * 주문 가공 완료
//	 * 
//	 * @param batch
//	 */
//	public List<JobBatch> completePreprocessing(JobBatch batch);
//	
//}
