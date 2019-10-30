package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInstance;

/**
 * 작업 스테이션 기반의 박스 처리 포함한 피킹 서비스 트랜잭션 API 
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
 * 		8) 스테이션에 투입된 주문별 피킹 작업 완료 여부 체크
 */
public interface IStationBasedBoxPickingService extends IBoxPickingService {

	/**
	 * 3-8. 소분류 : 스테이션에 투입된 주문별 피킹 작업 완료 여부 체크
	 * 
	 * @param batch
	 * @param stationCd
	 * @param job
	 * @return
	 */
	public boolean checkStationJobsEnd(JobBatch batch, String stationCd, JobInstance job);
	
}
