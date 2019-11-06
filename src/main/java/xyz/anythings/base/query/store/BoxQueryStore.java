package xyz.anythings.base.query.store;

import org.springframework.stereotype.Component;

/**
 * 박스 관련 쿼리 스토어 
 * 
 * @author yang
 */
@Component
public class BoxQueryStore extends AbstractQueryStore {
	
	/**
	 * JobInput 기준으로 박스 ID 유니크 여부를 확인 하는 쿼리   
	 * 
	 * @return
	 */
	public String getBoxIdUniqueCheckQuery() {
		return this.getQueryByPath("box/BoxIdUniqueCheck");
	}
}
