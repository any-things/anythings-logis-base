package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.entity.WorkCell;
import xyz.anythings.base.event.IClassifyRunEvent;

/**
 * 박스 처리 포함된 분류 처리 서비스 트랜잭션 API 
 * 
 * 	1. 분류 모듈 정보
 * 		1) 작업 유형 
 * 		2) 작업 배치별 표시기 설정 정보
 * 		3) 작업 배치별 작업 설정 정보
 * 		4) 박스 처리 서비스 조회
 * 	2. 투입
 * 		1) 스캔한 ID가 투입 유형에 따라 유효한 지 체크 및 어떤 투입 유형인 지 판단
 * 		2) 단건 상품 투입
 * 		3) 묶음 상품 투입
 * 		4) 박스 단위 상품 투입
 * 	3. 소분류 처리
 * 		1) 분류 작업 처리
 * 		2) 분류 작업 취소
 * 		3) 수량 분할 작업 처리
 * 		4) 분류 확정 취소
 * 		5) 박싱 처리
 * 		6) 수량 조정 후 박싱 처리
 * 		7) 박싱 취소
 * 		8) 박스 처리를 위한 작업 수량 분할 처리
 * 
 * @author shortstop
 */
public interface IBoxAssortService extends IAssortService {

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
	 * 3-6. 소분류 : 수량 조정 후 박스 처리  
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 * @return 박스 처리 결과
	 */
	public BoxPack partialFullboxing(IClassifyRunEvent exeEvent);
	
	/**
	 * 3-7. 소분류 : Boxing 취소
	 * 
	 * @param domainId
	 * @param boxPackId
	 * @return
	 */
	public BoxPack cancelBoxing(Long domainId, String boxPackId);
	
	/**
	 * 3-8. 기타 : 작업 정보의 처리 수량을 splitQty 수량으로 분할 처리 후 분할 처리한 작업을 리턴
	 * 
	 * @param job
	 * @param location
	 * @param splitQty
	 * @return
	 */
	public JobInstance splitJob(JobInstance job, WorkCell workCell, int splitQty);
	
}
