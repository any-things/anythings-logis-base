package xyz.anythings.base.service.api;

import xyz.anythings.base.event.IClassifyInEvent;
import xyz.anythings.base.event.IClassifyRunEvent;

/**
 * 기본 분류 (Assorting) 서비스 트랜잭션 API 
 * 
 * 	1. 분류 모듈 정보
 * 		1) 작업 유형 
 * 		2) 작업 배치별 표시기 설정 정보
 * 		3) 작업 배치별 작업 설정 정보
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
 * 
 * @author shortstop
 */
public interface IAssortService extends IClassificationService {
	
	/**
	 * 2-2. 투입 : 배치 작업에 단건 상품 투입 
	 * 
	 * @param inputEvent
	 * @return
	 */
	public Object inputSkuSingle(IClassifyInEvent inputEvent);

	/**
	 * 2-3. 투입 : 배치 작업에 묶음 단위 상품 투입 
	 * 
	 * @param inputEvent
	 * @return
	 */
	public Object inputSkuBundle(IClassifyInEvent inputEvent);
	
	/**
	 * 2-4. 투입 : 배치 작업에 완박스 단위 상품 투입 
	 * 
	 * @param inputEvent
	 * @return
	 */
	public Object inputSkuBox(IClassifyInEvent inputEvent);
	
	/**
	 * 3-1. 소분류 : 분류 작업 확정 처리
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 */
	public void confirmAssort(IClassifyRunEvent exeEvent);
	
	/**
	 * 3-2. 소분류 : 작업 취소 (예정 수량보다 분류 처리할 실물이 작아서 처리할 수 없는 경우 취소 처리)
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 */
	public void cancelAssort(IClassifyRunEvent exeEvent);
	
	/**
	 * 3-3. 소분류 : 수량을 조정하여 분할 확정 처리
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 * @return
	 */
	public int splitAssort(IClassifyRunEvent exeEvent);
	
	/**
	 * 3-4. 소분류 : 피킹 확정 처리된 작업 취소
	 * 
	 * @param exeEvent 분류 작업 이벤트
	 * @return
	 */
	public int undoAssort(IClassifyRunEvent exeEvent);

}
