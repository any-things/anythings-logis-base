package xyz.anythings.base.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import xyz.anythings.base.entity.JobBatch;
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
	 * 기본 unselect fields
	 */
	public static final String[] DEFAULT_UNSELECT_QUERY_FIELDS = {
		SysConstants.ENTITY_FIELD_CREATOR, 
		SysConstants.ENTITY_FIELD_UPDATER, 
		SysConstants.ENTITY_FIELD_CREATOR_ID, 
		SysConstants.ENTITY_FIELD_UPDATER_ID, 
		SysConstants.ENTITY_FIELD_CREATED_AT, 
		SysConstants.ENTITY_FIELD_UPDATED_AT
	};
	
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
		// TODO 멀티 데이터베이스 지원
		return queryMgr.selectBySql("select sysdate from dual", new HashMap<String, Object>(1), Date.class);
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
			Query condition = newConditionForExecution(domainId, SysConstants.ENTITY_FIELD_ID);
			condition.addFilter("batchId", newBatchId);
			count = queryMgr.selectSize(JobBatch.class, condition);
			
			if(count > 0) {
				ThreadUtil.sleep(1);
			}
		}
		
		return newBatchId;
	}
	
	/**
	 * 실행을 위한 기본 컨디션을 리턴 
	 * 
	 * @return
	 */
	public static Query newConditionForExecution() {
		Query condition = new Query();
		condition.addUnselect(DEFAULT_UNSELECT_QUERY_FIELDS);
		return condition;
	}
	
	/**
	 * domainId 필터가 포함된 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param domainId
	 * @return
	 */
	public static Query newConditionForExecution(Long domainId) {
		Query condition = newConditionForExecution();
		condition.addFilter(SysConstants.ENTITY_FIELD_DOMAIN_ID, domainId);
		return condition;
	}
	
	/**
	 * 페이지네이션 정보가 포함된 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	public static Query newConditionForExecution(int page, int limit) {
		Query condition = newConditionForExecution();
		condition.setPageIndex(page);
		condition.setPageSize(limit);
		return condition;
	}
	
	/**
	 * domainId 필터, 페이지네이션 정보가 포함된 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param domainId
	 * @param page
	 * @param limit
	 * @return
	 */
	public static Query newConditionForExecution(Long domainId, int page, int limit) {
		Query condition = newConditionForExecution(domainId);
		condition.setPageIndex(page);
		condition.setPageSize(limit);
		return condition;
	}
	
	/**
	 * 페이지네이션 정보와 조회 필드가 포함된 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param page
	 * @param limit
	 * @param selectFields
	 * @return
	 */
	public static Query newConditionForExecution(int page, int limit, String... selectFields) {
		Query condition = newConditionForExecution();
		condition.addSelect(selectFields);
		condition.setPageIndex(page);
		condition.setPageSize(limit);
		return condition;
	}
	
	/**
	 * selectFields 필드로 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param domainId
	 * @param selectFields
	 * @return
	 */
	public static Query newConditionForExecution(Long domainId, String... selectFields) {
		Query condition = newConditionForExecution(domainId);
		condition.addSelect(selectFields);
		return condition;
	}
	
	/**
	 * domainId 필터, 페이지네이션 조건, 검색 필드가 포함된 검색을 위한 기본 컨디션을 리턴 
	 * 
	 * @param domainId
	 * @param page
	 * @param limit
	 * @param selectFields
	 * @return
	 */
	public static Query newConditionForExecution(Long domainId, int page, int limit, String... selectFields) {
		Query condition = newConditionForExecution(domainId, page, limit);
		condition.addSelect(selectFields);
		return condition;
	}
	
	/**
	 * batchCount 건수 별로 배치 생성 
	 * 
	 * @param insertList
	 * @param batchCount
	 */
	public static void insertBatch(List<?> insertList, int batchCount) {
		IQueryManager queryManager = BeanUtil.get(IQueryManager.class);
		List<Object> batchList = new ArrayList<Object>(batchCount);
		int count = 0;
		
		for(Object obj : insertList) {
			count++;
			batchList.add(obj);
			
			if(count == batchCount) {
				queryManager.insertBatch(batchList);
				count = 0;
			}
			
			if(count == 0) {
				batchList.clear();
			}
		}
		
		if(!batchList.isEmpty()) {
			queryManager.insertBatch(batchList);
		}
	}
	
	/**
	 * batchCount 건수 별로 업데이트 
	 * 
	 * @param updateList
	 * @param batchCount
	 * @param fields
	 */
	public static void updateBatch(List<?> updateList, int batchCount, String... fields) {
		IQueryManager queryManager = BeanUtil.get(IQueryManager.class);
		List<Object> batchList = new ArrayList<Object>(batchCount);
		int count = 0;
		
		for(Object obj : updateList) {
			count++;
			batchList.add(obj);
			
			if(count == batchCount) {
				queryManager.updateBatch(batchList, fields);
				count = 0;
			}
			
			if(count == 0) {
				batchList.clear();
			}
		}
		
		if(!batchList.isEmpty()) {
			queryManager.updateBatch(batchList, fields);
		}
	}
	
}
