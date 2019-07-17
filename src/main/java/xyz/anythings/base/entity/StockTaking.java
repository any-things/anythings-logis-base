package xyz.anythings.base.entity;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.ChildEntity;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.DetailRemovalStrategy;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.MasterDetailType;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_stock_taking", idStrategy = GenerationRule.UUID, uniqueFields="domainId,stockTakingDate,stockTakingSeq,regionCd", indexes = {
	@Index(name = "ix_tb_stock_taking_0", columnList = "stock_taking_date,stock_taking_seq,region_cd", unique = true)
}, childEntities = {
	@ChildEntity(entityClass = StockAdjustment.class, type = MasterDetailType.ONE_TO_MANY, refFields = "stockTakingId", dataProperty = "adjustments", deleteStrategy = DetailRemovalStrategy.DESTROY)
})
public class StockTaking extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 181509381603340647L;
	
	/**
	 * 재고 실사 상태 - 진행 중 
	 */
	public static final String STATUS_RUNNING = "R";
	/**
	 * 재고 실사 상태 - 완료 
	 */
	public static final String STATUS_FINISHED = "F";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "stock_taking_date", length = 10)
	private String stockTakingDate;

	@Column (name = "stock_taking_seq", length = 10)
	private Integer stockTakingSeq;

	@Column (name = "region_cd", length = 30, nullable = false)
	private String regionCd;

	@Column (name = "status", length = 10, nullable = false)
	private String status;
	
	@Column (name = "remark", length = 1000)
	private String remark;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockTakingDate() {
		return stockTakingDate;
	}

	public void setStockTakingDate(String stockTakingDate) {
		this.stockTakingDate = stockTakingDate;
	}

	public Integer getStockTakingSeq() {
		return stockTakingSeq;
	}

	public void setStockTakingSeq(Integer stockTakingSeq) {
		this.stockTakingSeq = stockTakingSeq;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * id로 재고 실사 조회 
	 * 
	 * @param domainId
	 * @param id
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static StockTaking find(Long domainId, String id, boolean exceptionWhenEmpty) {
		Query query = AnyOrmUtil.newConditionForExecution(domainId);
		query.addFilter(SysConstants.ENTITY_FIELD_ID, id);
		StockTaking stockTaking = BeanUtil.get(IQueryManager.class).selectByCondition(StockTaking.class, query);
		
		if(stockTaking == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.StockTaking", id);
		}
		
		return stockTaking;
	}
	
}
