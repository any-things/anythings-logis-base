package xyz.anythings.base.util;

/**
 * 물류 베이스 유틸리티
 * 
 * @author shortstop
 */
public class LogisBaseUtil {

	/**
	 * 배치 ID 생성을 위한 날짜 포맷
	 */
	//private static final String DATE_FORMAT_FOR_BATCH_ID = "yyMMddHHmmssSSS";
	/**
	 * 배치 ID 중복 체크를 위한 쿼리
	 */
	//private static final String BATCH_ID_CHECK_QEURY = "select id from tb_job_batch where id = :batchId";

	/**
	 * 작업 배치 ID 
	 * 
	 * @domainId
	 * @return
	 */
	/*public static synchronized String newJobBatchId(Long domainId) {
		String newBatchId = null;
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		int count = 1;
		
		while(count > 0) {
			String currentTime = DateUtil.dateTimeStr(new Date(), DATE_FORMAT_FOR_BATCH_ID);
			newBatchId = domainId + SysConstants.DASH + currentTime;	
			Map<String, Object> params = ValueUtil.newMap("batchId", newBatchId);
			count = queryMgr.selectSizeBySql(BATCH_ID_CHECK_QEURY, params);
			
			if(count > 0) {
				ThreadUtil.sleep(1);
			}
		}
		
		return newBatchId;
	}*/
}
