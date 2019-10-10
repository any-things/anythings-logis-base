package xyz.anythings.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.query.store.EtcQueryStore;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.DateUtil;
import xyz.elidom.util.ThreadUtil;

/**
 * 물류 베이스 유틸리티
 * 
 * @author shortstop
 */
public class LogisBaseUtil {
	
	/**
	 * 배치 ID 생성을 위한 날짜 포맷
	 */
	private static final String DATE_FORMAT_FOR_BATCH_ID = "yyMMddHHmmssSSS";
	
	/**
	 * 32 바이트 길이의 GUID를 생성하여 리턴
	 * 
	 * @return
	 */
	public static String newUuid32() {
		return UUID.randomUUID().toString().replace(SysConstants.DASH, SysConstants.EMPTY_STRING);
	}
	
	/**
	 * 36 바이트 길이의 GUID를 생성하여 리턴
	 * 
	 * @return
	 */
	public static String newUuid36() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 데이터베이스 현재 시간
	 * 
	 * @return
	 */
	public static Date currentDbTime() {
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		String query = BeanUtil.get(EtcQueryStore.class).getCurrentTimeQuery();
		return queryMgr.selectBySql(query, new HashMap<String, Object>(1), Date.class);
	}
	
	/**
	 * 작업 배치 ID 
	 * 
	 * @domainId
	 * @return
	 */
	public static synchronized String newJobBatchId(Long domainId) {
		String newBatchId = null;
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		int count = 1;
		
		while(count > 0) {
			String currentTime = DateUtil.dateTimeStr(new Date(), DATE_FORMAT_FOR_BATCH_ID);
			newBatchId = domainId + SysConstants.DASH + currentTime;	
			Query condition = AnyOrmUtil.newConditionForExecution(domainId, SysConstants.ENTITY_FIELD_ID);
			condition.addFilter("batchId", newBatchId);
			count = queryMgr.selectSize(JobBatch.class, condition);
			
			if(count > 0) {
				ThreadUtil.sleep(10);
			}
		}
		
		return newBatchId;
	}
	
}
