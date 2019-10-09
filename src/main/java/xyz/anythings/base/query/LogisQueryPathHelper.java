//package xyz.anythings.base.query;
//
//import org.springframework.stereotype.Component;
//
//import xyz.anythings.base.LogisBaseConstants;
//import xyz.elidom.orm.IQueryManager;
//import xyz.elidom.util.BeanUtil;
//
///**
// * QueryPathHelper 기본 구현
// * 
// * @author shortstop
// */
//@Component
//public class LogisQueryPathHelper implements IQueryPathHelper {
//	
//	/**
//	 * 데이터베이스 유형 
//	 */
//	private String databaseType;
//	/**
//	 * 쿼리 기본 경로
//	 */
//	private String basePath;
//	
//	public void initQueryStore() {
//		this.databaseType = BeanUtil.get(IQueryManager.class).getDbType();
//		this.basePath = "xyz.anythings.base.query." + this.databaseType;
//	}
//
//	@Override
//	public String getDatabaseType() {		
//		return this.databaseType;
//	}
//	
//	@Override
//	public String getBasePath() {		
//		return this.basePath;
//	}
//
//	@Override
//	public String getQueryPath(String queryPath) {
//		return this.basePath + LogisBaseConstants.DOT + queryPath;
//	}
//
//}
