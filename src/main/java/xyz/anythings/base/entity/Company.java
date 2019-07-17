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

@Table(name = "tb_company", idStrategy = GenerationRule.UUID, uniqueFields = "comCd", indexes = {
	@Index(name = "ix_tb_company_0", columnList = "com_cd", unique = true) 
})
public class Company extends xyz.elidom.orm.entity.basic.UserTimeStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 181579229847089672L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column(name = "com_nm", length = 300)
	private String comNm;
	
	@Column(name = "eng_com_nm", length = 300)
	private String engComNm;	

	@Column(name = "com_tel_no", length = 100)
	private String comTelNo;

	@Column(name = "com_zip_cd", length = 50)
	private String comZipCd;

	@Column(name = "com_addr", length = 400)
	private String comAddr;
	
	@Column(name = "biz_lic_no", length = 30)
	private String bizLicNo;
	
	@Column(name = "rep_per_nm", length = 100)
	private String repPerNm;	
	
	@Column(name = "biz_con_nm", length = 255)
	private String bizConNm;
	
	@Column(name = "biz_item_nm", length = 255)
	private String bizItemNm;
	
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

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getComNm() {
		return comNm;
	}

	public void setComNm(String comNm) {
		this.comNm = comNm;
	}

	public String getEngComNm() {
		return engComNm;
	}

	public void setEngComNm(String engComNm) {
		this.engComNm = engComNm;
	}

	public String getComTelNo() {
		return comTelNo;
	}

	public void setComTelNo(String comTelNo) {
		this.comTelNo = comTelNo;
	}

	public String getComZipCd() {
		return comZipCd;
	}

	public void setComZipCd(String comZipCd) {
		this.comZipCd = comZipCd;
	}

	public String getComAddr() {
		return comAddr;
	}

	public void setComAddr(String comAddr) {
		this.comAddr = comAddr;
	}

	public String getBizLicNo() {
		return bizLicNo;
	}

	public void setBizLicNo(String bizLicNo) {
		this.bizLicNo = bizLicNo;
	}

	public String getRepPerNm() {
		return repPerNm;
	}

	public void setRepPerNm(String repPerNm) {
		this.repPerNm = repPerNm;
	}

	public String getBizConNm() {
		return bizConNm;
	}

	public void setBizConNm(String bizConNm) {
		this.bizConNm = bizConNm;
	}

	public String getBizItemNm() {
		return bizItemNm;
	}

	public void setBizItemNm(String bizItemNm) {
		this.bizItemNm = bizItemNm;
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
			DomainCompany dc = new DomainCompany();
			dc.setComCd(this.comCd);
			
			if(this.belongsDomainId == -1) {
				// 도메인 - 고객사 관계 삭제
				dc.setDomainId(Domain.currentDomainId());
				queryMgr.deleteByCondition(DomainCompany.class, dc);
				
			} else {
				// 도메인 - 고객사 관계 생성
				dc.setDomainId(Domain.currentDomainId());
				if(queryMgr.selectSize(DomainCompany.class, dc) == 0) {
					queryMgr.insert(dc);
				}
			}
		}
	}
	
	/**
	 * 고객사 코드로 고객사 조회
	 * 
	 * @param comCd
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static Company findByCode(String comCd, boolean exceptionWhenEmpty) {
		Query query = OrmUtil.newConditionForExecution();
		query.addFilter("comCd", comCd);
		Company company = BeanUtil.get(IQueryManager.class).selectByCondition(Company.class, query);

		if (company == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.Company", comCd);
		}

		return company;
	}

}
