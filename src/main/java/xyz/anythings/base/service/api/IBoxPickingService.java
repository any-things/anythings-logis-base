package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.event.IClassifyRunEvent;

/**
 * 박스 처리 포함한 피킹 서비스 트랜잭션 API 
 * 
 * 	1. 분류 모듈 정보
 * 		1) 작업 유형 
 * 		2) 작업 배치별 표시기 설정 정보
 * 		3) 작업 배치별 작업 설정 정보
 * 		4) 박스 처리 서비스 조회
 * 	2. 투입
 * 		1) 스캔한 ID가 투입 유형에 따라 유효한 지 체크 및 어떤 투입 유형인 지 판단
 * 		2) 공 박스 투입
 * 		3) 공 트레이 투입
 * 	3. 소분류 처리
 * 		1) 피킹 작업 처리
 * 		2) 피킹 작업 취소
 * 		3) 피킹 분할 작업 처리
 * 		4) 피킹 취소
 * 		5) 박싱 처리
 * 		6) 박싱 취소
 * 		7) 주문별 박스별 피킹 완료 여부 체크
 */
public interface IBoxPickingService extends IPickingService {

	/**
	 * 1-4. 모듈별 박싱 처리 서비스
	 * 
	 * @param params
	 * @return
	 */
	public IBoxingService getBoxingService(Object ... params);
	
	/**
	 * 3-5. 소분류 : 박스 처리
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 * @return
	 */
	public BoxPack fullBoxing(IClassifyRunEvent exeEvent);
		
	/**
	 * 3-6. 소분류 : Boxing 취소
	 * 
	 * @param domainId
	 * @param boxPackId
	 * @return
	 */
	public BoxPack cancelBoxing(Long domainId, String boxPackId);
	
	/**
	 * 3-7. 소분류 : 주문별 박스별 피킹 완료 여부 체크
	 * 
	 * @param batch
	 * @param orderId
	 * @param boxId
	 * @return
	 */
	public boolean checkBoxingEnd(JobBatch batch, String orderId, String boxId);
	
}
