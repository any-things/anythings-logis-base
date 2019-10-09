package xyz.anythings.base.query.store;

import org.springframework.stereotype.Component;

/**
 * 작업 배치 관련 쿼리 스토어 
 * - 주문 수신, 배치 작업 현황, 배치 마감 관련 쿼리 관리
 * 
 * @author shortstop
 */
@Component
public class BatchQueryStore extends AbstractQueryStore {

	/**
	 * 스테이지 별 일일 작업 진행율 쿼리
	 * 
	 * @return
	 */
	public String getDailyProgressRateQuery() {
		return this.getQueryByPath("batch/DailyPrograssRate");
	}
	
	/**
	 * 배치 그룹내 배치에 대해서 끝나지 않은 정보가 있는지 체크
	 * 
	 * @return
	 */
	public String getCheckCloseBatchGroupQuery() {
		return this.getQueryByPath("batch/CheckCloseBatchGroup");
	}
}
