package xyz.anythings.base.util;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import xyz.elidom.dbist.dml.Page;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.exception.server.ElidomValidationException;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.EntityUtil;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

/**
 * Entity 관련 유틸리티
 * - 모든 엔티티에 대해서 단 건 조회, 리스트 조회, 페이지네이션, 마스터 디테일 구조의 디테일 리스트 조회 공통 기능 
 * 
 * @author shortstop
 */
public class LogisEntityUtil extends EntityUtil {

	/**
	 * id로 엔티티 조회
	 * 
	 * @param exceptionWhenEmpty 존재하지 않으면 exception
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static T findEntityById(boolean exceptionWhenEmpty, Class<T> clazz, String id) {
		T obj = BeanUtil.get(IQueryManager.class).select(clazz, id);
		
		if(obj == null) {
			throw ThrowUtil.newNotFoundRecord("terms.menu." + clazz.getName(), id);
		} else {
			return obj;
		}
	}
	
	/**
	 * id로 락을 걸면서 엔티티 조회
	 * 
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static T findEntityByIdWithLock(boolean exceptionWhenEmpty, Class<T> clazz, String id) {
		T obj = BeanUtil.get(IQueryManager.class).selectWithLock(clazz, id);
		
		if(obj == null) {
			throw ThrowUtil.newNotFoundRecord("terms.menu." + clazz.getName(), id);
		} else {
			return obj;
		}
	}
	
	/**
	 * code로 엔티티 조회
	 * 
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param codeName
	 * @param codeValue
	 * @return
	 */
	public static T findEntityByCode(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String codeName, String codeValue) {
		Query query = LogisBaseUtil.newConditionForExecution(domainId);
		query.addFilter(codeName, codeValue);
		T obj = BeanUtil.get(IQueryManager.class).selectByCondition(clazz, query);
		
		if(obj == null) {
			throw ThrowUtil.newNotFoundRecord("terms.menu." + clazz.getName(), codeValue);
		} else {
			return obj;
		}
	}
	
	/**
	 * code로 락을 걸면서 엔티티 조회
	 * 
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param codeName
	 * @param codeValue
	 * @return
	 */
	public static T findEntityByCodeWithLock(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String codeName, String codeValue) {
		Query query = LogisBaseUtil.newConditionForExecution(domainId);
		query.addFilter(codeName, codeValue);
		T obj = BeanUtil.get(IQueryManager.class).selectByConditionWithLock(clazz, query);
		
		if(obj == null) {
			throw ThrowUtil.newNotFoundRecord("terms.menu." + clazz.getName(), codeValue);
		} else {
			return obj;
		}
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 조회
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static T findEntityBy(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String fieldNames, Object ... fieldValues) {
		return findEntityBy(domainId, exceptionWhenEmpty, clazz, null, fieldNames, fieldValues);
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 조회
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param selectFields
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static T findEntityBy(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String selectFields, String fieldNames, Object ... fieldValues) {
		Query condition = LogisBaseUtil.newConditionForExecution(domainId);

		if(ValueUtil.isNotEmpty(selectFields)) {
			condition.addSelect(selectFields.split(SysConstants.COMMA));
		}

 		String[] keyArr = fieldNames.split(SysConstants.COMMA);

		if (keyArr.length != fieldValues.length) {
			throw new IllegalArgumentException("keys count and values count mismatch!");
		}

		for (int i = 0; i < keyArr.length; i++) {
			condition.addFilter(keyArr[i], fieldValues[i]);
		}
		
		T obj = BeanUtil.get(IQueryManager.class).selectByCondition(clazz, condition);
		
		if(obj == null && exceptionWhenEmpty) {
			throw new ElidomValidationException("Not found record!");
		}

		return obj;
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 검색
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static List<T> searchEntitiesBy(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String fieldNames, Object ... fieldValues) {
		return searchEntitiesBy(domainId, exceptionWhenEmpty, clazz, null, fieldNames, fieldValues);
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 검색
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param selectFields
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static List<T> searchEntitiesBy(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, String selectFields, String fieldNames, Object ... fieldValues) {
		Query condition = LogisBaseUtil.newConditionForExecution(domainId);

		if(ValueUtil.isNotEmpty(selectFields)) {
			condition.addSelect(selectFields.split(SysConstants.COMMA));
		}

 		String[] keyArr = fieldNames.split(SysConstants.COMMA);

		if (keyArr.length != fieldValues.length) {
			throw new IllegalArgumentException("keys count and values count mismatch!");
		}

		for (int i = 0; i < keyArr.length; i++) {
			condition.addFilter(keyArr[i], fieldValues[i]);
		}
		
		List<T> list = BeanUtil.get(IQueryManager.class).selectList(clazz, condition);
		
		if(ValueUtil.isEmpty(list) && exceptionWhenEmpty) {
			throw new ElidomValidationException("Not found record!");
		}

		return list;
	}
	
	/**
	 * 마스터의 상세 리스트 조회
	 * 
	 * @param domainId
	 * @param clazz
	 * @param masterField
	 * @param masterId
	 * @return
	 */
	public List<T> searchDetails(Long domainId, Class<T> clazz, String masterField, String masterId) {
		Query condition = LogisBaseUtil.newConditionForExecution(domainId);
		condition.addFilter(masterField, masterId);
		return BeanUtil.get(IQueryManager.class).selectList(clazz, condition);
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 페이지네이션 검색
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param limit
	 * @param page
	 * @param selectFields
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static Page<T> searchPagesBy(Long domainId, boolean exceptionWhenEmpty, Class<T> clazz, int limit, int page, String fieldNames, Object ... fieldValues) {
		return searchPagesBy(domainId, exceptionWhenEmpty, clazz, limit, page, null, fieldNames, fieldValues);
	}
	
	/**
	 * clazz 엔티티에 대해서 fieldName, fieldValue으로 엔티티 페이지네이션 검색
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param clazz
	 * @param limit
	 * @param page
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static Page<T> searchPagesBy(Long domainId, Class<T> clazz, int limit, int page, String selectFields, String fieldNames, Object ... fieldValues) {
		Query condition = LogisBaseUtil.newConditionForExecution(domainId, limit, page);

		if(ValueUtil.isNotEmpty(selectFields)) {
			condition.addSelect(selectFields.split(SysConstants.COMMA));
		}

 		String[] keyArr = fieldNames.split(SysConstants.COMMA);

		if (keyArr.length != fieldValues.length) {
			throw new IllegalArgumentException("keys count and values count mismatch!");
		}

		for (int i = 0; i < keyArr.length; i++) {
			condition.addFilter(keyArr[i], fieldValues[i]);
		}
		
		return BeanUtil.get(IQueryManager.class).selectPage(clazz, condition);
	}
}
