package xyz.anythings.base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.query.store.BoxQueryStore;
import xyz.anythings.base.service.api.IBoxingService;
import xyz.anythings.sys.service.AbstractExecutionService;
import xyz.elidom.sys.entity.User;
import xyz.elidom.util.ValueUtil;

/**
 * 박스 처리 서비스 기본 구현  
 * @author yang
 *
 */
public abstract class AbstractBoxingService extends AbstractExecutionService implements IBoxingService {

	@Autowired
	BoxQueryStore boxQueryStore;
	
	/**
	 * 배치, 주문 번호 로 BoxItem 데이터를 생성 한다.
	 * @param domainId
	 * @param batch
	 * @param orderNo
	 * @param boxPackId
	 */
	public void createBoxItemsDataByOrder(Long domainId, JobBatch batch, String orderNo, String boxPackId) {
		String qry = this.boxQueryStore.getCreateBoxItemsDataByOrderQuery();
		Map<String,Object> param = ValueUtil.newMap("domainId,batchId,orderNo,userId,boxPackId", domainId,batch.getId(), orderNo,User.currentUser().getId(), boxPackId);
		this.queryManager.executeBySql(qry, param);
	}
	
	/**
	 * 배치, 주문 번호 로 BoxItem 데이터를 생성 한다.
	 * @param domainId
	 * @param batch
	 * @param orderNo
	 * @param boxType
	 * @param boxTypeCd
	 * @param boxPackId
	 */
	public void createBoxPackDataByBoxItems(Long domainId, JobBatch batch, String orderNo, String boxType, String boxTypeCd, String boxPackId) {
		String qry = this.boxQueryStore.getCreateBoxPackDataByBoxItemsQuery();
		Map<String,Object> param = ValueUtil.newMap("domainId,batchId,orderNo,userId,boxPackId,boxType,boxTypeCd"
				, domainId,batch.getId(), orderNo,User.currentUser().getId(), boxPackId,boxType,boxTypeCd);
		this.queryManager.executeBySql(qry, param);
	}

	
	
}
