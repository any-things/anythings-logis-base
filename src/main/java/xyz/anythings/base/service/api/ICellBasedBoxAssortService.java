package xyz.anythings.base.service.api;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.entity.WorkCell;
import xyz.anythings.base.event.IClassifyInEvent;

/**
 * 셀 기반의 박스 처리 포함된 분류 처리 서비스 트랜잭션 API 
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
 * 		5) 검수 투입
 * 	3. 소분류 처리
 * 		1) 분류 작업 처리
 * 		2) 분류 작업 취소
 * 		3) 수량 분할 작업 처리
 * 		4) 분류 확정 취소
 * 		5) 박싱 처리
 * 		6) 수량 조정 후 박싱 처리
 * 		7) 박싱 취소
 * 		8) 박스 처리를 위한 작업 수량 분할 처리
 * 		9) 스테이션 영역에 투입된 작업 분류 처리 완료 여부 체크
 * 		10) 셀 분류 완료 여부 체크
 * 		11) 셀 분류 최종 완료 처리
 * 
 * @author shortstop
 */
public interface ICellBasedBoxAssortService extends IBoxAssortService {

	/**
	 * 2-5. 검수 투입
	 * 
	 * @param inputEvent
	 * @return
	 */
	public Object inputForInspection(IClassifyInEvent inputEvent);
	
	/**
	 * 3-9. 소분류 : 스테이션 영역에 투입된 작업 분류 처리 완료 여부 체크
	 * 
	 * @param batch
	 * @param stationCd
	 * @param job
	 * @return
	 */
	public boolean checkStationJobsEnd(JobBatch batch, String stationCd, JobInstance job);
	
	/**
	 * 3-10. 소분류 : 셀 분류 완료 여부 체크
	 * 
	 * @param batch
	 * @param stationCd
	 * @param job
	 * @return
	 */
	public boolean checkCellAssortEnd(JobBatch batch, String stationCd, JobInstance job);

	/**
	 * 3-11. 소분류 : 셀 별 분류 작업에 대한 최종 완료 처리
	 * 
	 * @param job
	 * @param workCell
	 * @param finalEndFlag 최종 완료인 지 (true) 아니면 셀의 분류는 종료되었지만 최종 완료 버튼을 눌러야 할 상황의 완료(false)인 지 여부 
	 * @return
	 */
	public boolean finishAssortCell(JobInstance job, WorkCell workCell, boolean finalEndFlag);

}
