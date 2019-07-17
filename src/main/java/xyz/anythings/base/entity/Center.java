package xyz.anythings.base.entity;

import xyz.anythings.sys.util.OrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_center", idStrategy = GenerationRule.UUID, uniqueFields = "dcCd", indexes = {
	@Index(name = "ix_tb_center_0", columnList = "dc_cd", unique = true) 
})
public class Center extends xyz.elidom.orm.entity.basic.UserTimeStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 268018001291902216L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column(name = "dc_nm", nullable = false, length = 300)
	private String dcNm;
	
	@Column(name = "eng_dc_nm", length = 300)
	private String engDcNm;

	@Column(name = "dc_tel_no", length = 100)
	private String dcTelNo;

	@Column(name = "dc_zip_cd", length = 50)
	private String dcZipCd;

	@Column (name = "dc_addr", length = 400)
	private String dcAddr;
	
	@Column(name = "del_flag", length = 1)
	private Boolean delFlag;
	
	@Column(name = "attr01", length = 100)
	private String attr01;
	
	@Column(name = "attr02", length = 100)
	private String attr02;
	
	@Column(name = "attr03", length = 100)
	private String attr03;
	
	@Column(name = "attr04", length = 100)
	private String attr04;
	
	@Column(name = "attr05", length = 100)
	private String attr05;
	
	@Column(name = "attr06", length = 100)
	private String attr06;
	
	@Column(name = "attr07", length = 100)
	private String attr07;
	
	@Column(name = "attr08", length = 100)
	private String attr08;
	
	@Column(name = "attr09", length = 100)
	private String attr09;
	
	@Column(name = "attr10", length = 100)
	private String attr10;
	
	/**
	 * 센터가 소속된 도메인 ID
	 */
	@Ignore
	private Long belongsDomainId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}

	public String getDcNm() {
		return dcNm;
	}

	public void setDcNm(String dcNm) {
		this.dcNm = dcNm;
	}

	public String getEngDcNm() {
		return engDcNm;
	}

	public void setEngDcNm(String engDcNm) {
		this.engDcNm = engDcNm;
	}

	public String getDcTelNo() {
		return dcTelNo;
	}

	public void setDcTelNo(String dcTelNo) {
		this.dcTelNo = dcTelNo;
	}

	public String getDcZipCd() {
		return dcZipCd;
	}

	public void setDcZipCd(String dcZipCd) {
		this.dcZipCd = dcZipCd;
	}

	public String getDcAddr() {
		return dcAddr;
	}

	public void setDcAddr(String dcAddr) {
		this.dcAddr = dcAddr;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getAttr01() {
		return attr01;
	}

	public void setAttr01(String attr01) {
		this.attr01 = attr01;
	}

	public String getAttr02() {
		return attr02;
	}

	public void setAttr02(String attr02) {
		this.attr02 = attr02;
	}

	public String getAttr03() {
		return attr03;
	}

	public void setAttr03(String attr03) {
		this.attr03 = attr03;
	}

	public String getAttr04() {
		return attr04;
	}

	public void setAttr04(String attr04) {
		this.attr04 = attr04;
	}

	public String getAttr05() {
		return attr05;
	}

	public void setAttr05(String attr05) {
		this.attr05 = attr05;
	}

	public String getAttr06() {
		return attr06;
	}

	public void setAttr06(String attr06) {
		this.attr06 = attr06;
	}

	public String getAttr07() {
		return attr07;
	}

	public void setAttr07(String attr07) {
		this.attr07 = attr07;
	}

	public String getAttr08() {
		return attr08;
	}

	public void setAttr08(String attr08) {
		this.attr08 = attr08;
	}

	public String getAttr09() {
		return attr09;
	}

	public void setAttr09(String attr09) {
		this.attr09 = attr09;
	}

	public String getAttr10() {
		return attr10;
	}

	public void setAttr10(String attr10) {
		this.attr10 = attr10;
	}

	public Long getBelongsDomainId() {
		return belongsDomainId;
	}

	public void setBelongsDomainId(Long belongsDomainId) {
		this.belongsDomainId = belongsDomainId;
	}
	
	@Override
	public void afterUpdate() {
		if(this.belongsDomainId != null) {
			IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
			DomainCenter dc = new DomainCenter();
			dc.setDcCd(this.dcCd);
			
			if(this.belongsDomainId == -1) {
				// 도메인 - 센터 관계 삭제
				dc.setDomainId(Domain.currentDomainId());
				queryMgr.deleteByCondition(DomainCenter.class, dc);
				
			} else {
				// 도메인 - 센터 관계 생성
				dc.setDomainId(Domain.currentDomainId());
				if(queryMgr.selectSize(DomainCenter.class, dc) == 0) {
					queryMgr.insert(dc);
				}
			}
		}
	}

	/**
	 * 센터 코드로 센터 조회
	 * 
	 * @param dcCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Center findByCode(String dcCd, boolean exceptionWhenEmpty) {
		Query query = OrmUtil.newConditionForExecution();
		query.addFilter("dcCd", dcCd);
		Center center = BeanUtil.get(IQueryManager.class).selectByCondition(Center.class, query);

		if (center == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Center", dcCd);
		}

		return center;
	}

}
