package xyz.anythings.base.entity;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@Table(name = "tb_gateway", idStrategy = GenerationRule.UUID, uniqueFields = "domainId,gwCd", indexes = {
	@Index(name = "ix_tb_gateway_0", columnList = "domain_id,gw_cd", unique = true),
	@Index(name = "ix_tb_gateway_1", columnList = "gw_ip", unique = true),
	@Index(name = "ix_tb_gateway_2", columnList = "gw_nm", unique = true),
	@Index(name = "ix_tb_gateway_3", columnList = "zone_cd")
})
public class Gateway extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 260781395148473360L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "gw_cd", nullable = false, length = 30)
	private String gwCd;

	@Column(name = "gw_nm", length = 100)
	private String gwNm;

	@Column(name = "gw_ip", length = 16)
	private String gwIp;

	@Column(name = "channel_no", length = 40)
	private String channelNo;

	@Column(name = "pan_no", length = 40)
	private String panNo;

	@Column(name = "version", length = 15)
	private String version;

	@Column(name = "status", length = 10)
	private String status;

	@Column(name = "zone_cd", length = 30)
	private String zoneCd;

	@Column(name = "remark", length = 1000)
	private String remark;
	
	// TODO 삭제 예정
	@Ignore
	private String regionCd;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGwCd() {
		return gwCd;
	}

	public void setGwCd(String gwCd) {
		this.gwCd = gwCd;
	}

	public String getGwNm() {
		return gwNm;
	}

	public void setGwNm(String gwNm) {
		this.gwNm = gwNm;
	}

	public String getGwIp() {
		return gwIp;
	}

	public void setGwIp(String gwIp) {
		this.gwIp = gwIp;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZoneCd() {
		return zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	
	/**
	 * Gateway에 소속된 표시기 리스트 
	 * 
	 * @return
	 */
	public List<MPI> mpiList() {
		Query condition = new Query();
		condition.addFilter("domainId", this.domainId);
		condition.addFilter("gwCd", this.gwCd);
		condition.addUnselect(OrmConstants.ENTITY_FIELD_CREATOR, OrmConstants.ENTITY_FIELD_UPDATER, OrmConstants.ENTITY_FIELD_CREATED_AT, OrmConstants.ENTITY_FIELD_UPDATED_AT);
		return BeanUtil.get(IQueryManager.class).selectList(MPI.class, condition);
	}
	
	/**
	 * Gateway에 소속된 표시기 코드 리스트 
	 * 
	 * @return
	 */
	public List<String> mpiCdList() {
		return Gateway.mpiCdList(this.domainId, this.gwNm);
	}
	
	/**
	 * Gateway에 소속된 표시기 코드 리스트 
	 * 
	 * @param domainId
	 * @param gwPath
	 * @return
	 */
	public static List<String> mpiCdList(Long domainId, String gwPath) {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		sql.add("select x.mpi_cd")
		   .add("from (")
		   .add(" 	select loc_cd, mpi_cd")
		   .add(" 	from tb_location")
		   .add(" 	where domain_id = :domainId AND active_flag = 1")
		   .add(") x, (")
		   .add(" 	select gw_cd, mpi_cd")
		   .add(" 	from tb_mpi")
		   .add(" 	where domain_id = :domainId")
		   .add("   and gw_cd = (select gw_cd from tb_gateway where domain_id = :domainId and gw_nm = :gwPath)")
		   .add(") y")
		   .add("where x.mpi_cd = y.mpi_cd")
		   .add("order by x.loc_cd");
		
		Map<String, Object> params = ValueUtil.newMap("domainId,gwPath", domainId, gwPath);
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql.toString(), params, String.class, 0, 0);
	}
	
	/**
	 * 게이트웨이 명으로 게이트웨이 조회
	 * 
	 * @param domainId
	 * @param gwNm
	 * @return
	 */
	public static Gateway findByName(Long domainId, String gwNm) {
		Map<String, Object> params = ValueUtil.newMap("domainId,gwNm", domainId, gwNm);
		return BeanUtil.get(IQueryManager.class).selectByCondition(Gateway.class, params);
	}
	
}
