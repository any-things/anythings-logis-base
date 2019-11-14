package xyz.anythings.base.query.store;

import org.springframework.stereotype.Component;

import xyz.elidom.sys.SysConstants;

/**
 * 게이트웨이 쿼리 스토어
 * 
 * @author shortstop
 */
@Component
public class IndicatorQueryStore extends LogisBaseQueryStore {

	@Override
	public void initQueryStore(String databaseType) {
		this.databaseType = databaseType;
		this.basePath = "xyz/anythings/base/query/" + this.databaseType + SysConstants.SLASH;
		this.defaultBasePath = "xyz/anythings/base/query/ansi/"; 
	}
	
	/**
	 * 게이트웨이 표시기 코드 리스트 쿼리
	 * 
	 * @return
	 */
	public String getIndCdListQuery() {
		return this.getQueryByPath("ind/GatewayIndCdList");
	}
	
	/**
	 * 표시기 코드 리스트 조회 쿼리
	 * 
	 * @return
	 */
	public String getSearchIndicatorsQuery() {
		return this.getQueryByPath("ind/SearchIndicators");
	}

	/**
	 * 게이트웨이 소속 표시기를 사용하는 모든 설비 리스트를 조회
	 *  
	 * @return
	 */
	public String getEquipListByGateway() {
		return this.getQueryByPath("ind/EquipListByGateway");
	}

	/**
	 * 라우터 리부팅을 위한 표시기 리스트를 조회
	 *  
	 * @return
	 */
	public String getSearchIndListForGwInitQuery() {
		return this.getQueryByPath("ind/SearchIndListForGwInit");
	}

}
