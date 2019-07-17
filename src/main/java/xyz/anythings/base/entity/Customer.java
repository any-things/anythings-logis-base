package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_customer", idStrategy = GenerationRule.UUID, uniqueFields = "comCd,custCd", indexes = {
	@Index(name = "ix_tb_customer_0", columnList = "cust_cd,com_cd", unique = true) 
})
public class Customer extends xyz.elidom.orm.entity.basic.UserTimeStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 298067397271347183L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column(name = "cust_cd", nullable = false, length = 32)
	private String custCd;

	@Column(name = "cust_nm", length = 300)
	private String custNm;
	
	@Column(name = "eng_cust_nm", length = 300)
	private String engCustNm;

	@Column(name = "cust_tel_no", length = 100)
	private String custTelNo;

	@Column(name = "cust_zip_cd", length = 50)
	private String custZipCd;

	@Column(name = "cust_addr", length = 400)
	private String custAddr;
	
	@Column(name = "biz_lic_no", length = 30)
	private String bizLicNo;
	
	@Column(name = "rep_per_nm", length = 100)
	private String repPerNm;	
	
	@Column(name = "biz_con_nm", length = 255)
	private String bizConNm;
	
	@Column(name = "biz_item_nm", length = 255)
	private String bizItemNm;
	
	@Column(name = "area_cd", length = 30)
	private String areaCd;
	
	@Column(name = "area_nm", length = 100)
	private String areaNm;
	
	@Column(name = "cust_type", length = 20)
	private String custType;
	
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

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getEngCustNm() {
		return engCustNm;
	}

	public void setEngCustNm(String engCustNm) {
		this.engCustNm = engCustNm;
	}

	public String getCustTelNo() {
		return custTelNo;
	}

	public void setCustTelNo(String custTelNo) {
		this.custTelNo = custTelNo;
	}

	public String getCustZipCd() {
		return custZipCd;
	}

	public void setCustZipCd(String custZipCd) {
		this.custZipCd = custZipCd;
	}

	public String getCustAddr() {
		return custAddr;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
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

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getAreaNm() {
		return areaNm;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
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

}
