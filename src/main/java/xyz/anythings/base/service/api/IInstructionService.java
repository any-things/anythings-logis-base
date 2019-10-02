//package xyz.anythings.base.service.api;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.stereotype.Component;
//
//import xyz.anythings.base.entity.JobBatch;
//import xyz.anythings.base.entity.Region;
//
///**
// * 작업 지시 서비스 API
// * 	1. 작업 지시를 위한 거래처 별 호기/로케이션 할당 정보 조회
// *  2. 작업 지시 - 주문 가공 정보로 부터 작업 지시 정보 생성
// *  3. 호기 복사를 위한 호기 리스트 조회
// *  4. 호기 복사 - 특정 호기의 로케이션 할당 정보를 다른 호기에 그대로 복사
// *  5. 작업 병합 - 메인 작업 배치에서 선택한 작업 배치를 배치 병합
// *  6. 작업 취소 - 작업 지시한 내용을 취소
// * 
// * @author shortstop
// */
//@Component
//public interface IInstructionService {
//
//	/**
//	 * 작업 지시를 위한 거래처 별 호기/로케이션 할당 정보 조회 - DAS 출고, 반품용 API
//	 * 
//	 * @param batch
//	 * @return
//	 */
//	public Map<String, Object> searchInstructionData(JobBatch batch);
//	
//	/**
//	 * 호기 복사를 위한 호기 리스트 조회
//	 * 
//	 * @param batch
//	 * @param region
//	 * @return
//	 */
//	public Map<String, Object> searchRegionListForClone(JobBatch batch, Region region);
//	
//	/**
//	 * 작업 배치에 대한 작업 지시 생성 
//	 * 
//	 * @param batch
//	 * @param regionList
//	 * @return
//	 */
//	public int instructBatch(JobBatch batch, List<Region> regionList);
//	
//	/**
//	 * 토털 피킹 지시
//	 * 
//	 * @param batch
//	 * @param regionList
//	 */
//	public void instructTotalpicking(JobBatch batch, List<Region> regionList);
//	
//	/**
//	 * 호기 복사 - 특정 호기의 로케이션 할당 정보를 다른 호기에 그대로 복사 (소스 호기에서 타겟 호기로 할당 정보 복사) 
//	 * 
//	 * @param batch
//	 * @param regionList
//	 */
//	public void cloneBatch(JobBatch batch, List<Region> regionList);
//	
//	/**
//	 * 작업 병합 - 메인 작업 배치에서 선택한 작업 배치를 배치 병합 
//	 * 
//	 * @param mainBatch
//	 * @param newBatch
//	 * @return
//	 */
//	public int mergeBatch(JobBatch mainBatch, JobBatch newBatch);		
//	
//	/**
//	 * 작업 지시 취소 
//	 * 
//	 * @param batch
//	 * @return
//	 */
//	public int cancelInstructionBatch(JobBatch batch);
//}
