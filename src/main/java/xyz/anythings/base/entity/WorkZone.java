package xyz.anythings.base.entity;

import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@Table(name = "tb_work_zone", idStrategy = GenerationRule.MEANINGFUL, uniqueFields = "domainId,regionCd,zoneCd", meaningfulFields = "regionCd,zoneCd", indexes = {
	@Index(name = "ix_tb_work_zone_0", columnList = "region_cd,zone_cd", unique = true),
	@Index(name = "ix_tb_work_zone_1", columnList = "region_cd")
})
public class WorkZone extends xyz.elidom.orm.entity.basic.DomainTimeStampHook {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2445017869695267341L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "zone_cd", nullable = false, length = 30)
	private String zoneCd;
	
	@Column(name = "region_cd", nullable = false, length = 30)
	private String regionCd;	
	
	@Column(name = "todo_count", length = 19)
	private Integer todoCount;
	
	@Column(name = "done_count", length = 19)
	private Integer doneCount;
	
	@Column(name = "box_id", length = 40)
	private String boxId;
	
	@Column(name = "process_seq", length = 10)
	private Integer processSeq;
	
	@Column(name = "status", length = 10)
	private String status;
	
	public WorkZone() {
	}
	
	public WorkZone(String zoneCd) {
		this.zoneCd = zoneCd;
	}
	
	public WorkZone(String regionCd, String zoneCd) {
		this.regionCd = regionCd;
		this.zoneCd = zoneCd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public Integer getTodoCount() {
		return todoCount;
	}

	public void setTodoCount(Integer todoCount) {
		this.todoCount = todoCount;
	}

	public Integer getDoneCount() {
		return doneCount;
	}

	public void setDoneCount(Integer doneCount) {
		this.doneCount = doneCount;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public Integer getProcessSeq() {
		return processSeq;
	}

	public void setProcessSeq(Integer processSeq) {
		this.processSeq = processSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 작업 존 상태 업데이트
	 * 
	 * @param status
	 */
	public void updateStatus(String status) {
		this.setStatus(status);
		BeanUtil.get(IQueryManager.class).update(this, OrmConstants.ENTITY_FIELD_STATUS);
	}
	
	/**
	 * 작업 존 조회
	 * 
	 * @param domainId
	 * @param workZoneCd
	 * @return
	 */
	public static WorkZone findWithLock(Long domainId, String workZoneCd) {
		return find(domainId, null, workZoneCd, true, false);
	}
	
	/**
	 * 작업 존 조회 
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param workZoneCd
	 * @return
	 */
	public static WorkZone findWithLock(Long domainId, String regionCd, String workZoneCd) {
		return find(domainId, regionCd, workZoneCd, true, false);
	}
	
	/**
	 * 작업 존 조회
	 * 
	 * @param domainId
	 * @param workZoneCd
	 * @return
	 */
	public static WorkZone find(Long domainId, String workZoneCd) {
		return find(domainId, null, workZoneCd, false, false);
	}
	
	/**
	 * 작업 존 조회 
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param workZoneCd
	 * @return
	 */
	public static WorkZone find(Long domainId, String regionCd, String workZoneCd) {
		return find(domainId, null, workZoneCd, false, false);
	}
	
	/**
	 * 작업 존 조회
	 * 
	 * @param domainId
	 * @param regionCd
	 * @param workZoneCd
	 * @param withLock
	 * @param insertIfEmpty
	 * @return
	 */
	public static WorkZone find(Long domainId, String regionCd, String workZoneCd, boolean withLock, boolean insertIfEmpty) {
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		if(ValueUtil.isNotEmpty(regionCd)) condition.setFilter("regionCd", regionCd);
		if(ValueUtil.isNotEmpty(workZoneCd)) condition.setFilter("zoneCd", workZoneCd);
		WorkZone workZone = withLock ? queryMgr.selectByConditionWithLock(WorkZone.class, condition) : queryMgr.selectByCondition(WorkZone.class, condition);
		
		if(workZone == null && insertIfEmpty) {
			workZone = new WorkZone(regionCd, workZoneCd);
			workZone.setDomainId(domainId);
			workZone.setTodoCount(0);
			workZone.setDoneCount(0);
			queryMgr.insert(workZone);
		}
		
		return workZone;
	}

}
