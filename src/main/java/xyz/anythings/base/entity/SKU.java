package xyz.anythings.base.entity;

import java.util.Map;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

@Table(name = "tb_sku", idStrategy = GenerationRule.UUID, uniqueFields = "comCd,skuCd", indexes = {
	@Index(name = "ix_tb_sku_0", columnList = "sku_cd,com_cd", unique = true),
	@Index(name = "ix_tb_sku_1", columnList = "sku_barcd,com_cd"),
	@Index(name = "ix_tb_sku_2", columnList = "box_barcd,com_cd"),
	@Index(name = "ix_tb_sku_3", columnList = "case_barcd,com_cd"),
	@Index(name = "ix_tb_sku_4", columnList = "sku_barcd2,com_cd"),
	@Index(name = "ix_tb_sku_5", columnList = "sku_barcd3,com_cd")
})
public class SKU extends xyz.elidom.orm.entity.basic.UserTimeStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 495771100550209274L;

	@PrimaryKey
	@Column(name = "id", nullable = false, length = 40)
	private String id;

	@Column(name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column(name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column(name = "sku_class", length = 20)
	private String skuClass;

	@Column(name = "sku_nm", nullable = false, length = 385)
	private String skuNm;

	@Column(name = "eng_sku_nm", length = 300)
	private String engSkuNm;

	@Column(name = "sku_type", length = 20)
	private String skuType;

	@Column(name = "sku_barcd", length = 60)
	private String skuBarcd;

	@Column(name = "box_barcd", length = 60)
	private String boxBarcd;

	@Column(name = "case_barcd", length = 60)
	private String caseBarcd;

	@Column(name = "box_in_qty", length = 18)
	private Integer boxInQty;

	@Column(name = "plt_box_qty", length = 18)
	private Integer pltBoxQty;

	@Column(name = "expire_period", length = 4)
	private Integer expirePeriod;

	@Column(name = "sku_rank", length = 10)
	private Integer skuRank;

	@Column(name = "loc_cd", length = 30)
	private String locCd;

	@Column(name = "image_url", length = 255)
	private String imageUrl;

	@Column(name = "supplier_cd", length = 30)
	private String supplierCd;

	@Column(name = "supplier_nm", length = 100)
	private String supplierNm;

	@Column(name = "case_type", length = 20)
	private String caseType;

	@Column(name = "box_type_cd", length = 30)
	private String boxTypeCd;

	@Column(name = "style_cd", length = 60)
	private String styleCd;

	@Column(name = "style_nm", length = 150)
	private String styleNm;

	@Column(name = "size_cd", length = 30)
	private String sizeCd;

	@Column(name = "size_nm", length = 150)
	private String sizeNm;

	@Column(name = "color_cd", length = 60)
	private String colorCd;

	@Column(name = "color_nm", length = 150)
	private String colorNm;

	@Column(name = "brand_cd", length = 30)
	private String brandCd;

	@Column(name = "brand_nm", length = 150)
	private String brandNm;

	@Column(name = "sku_price", length = 19)
	private Float skuPrice;

	@Column(name = "wt_unit", length = 6)
	private String wtUnit;

	@Column(name = "len_unit", length = 6)
	private String lenUnit;

	@Column(name = "vol_unit", length = 6)
	private String volUnit;

	@Column(name = "sku_len", length = 19)
	private Float skuLen;

	@Column(name = "sku_wd", length = 19)
	private Float skuWd;

	@Column(name = "sku_ht", length = 19)
	private Float skuHt;

	@Column(name = "sku_vol", length = 19)
	private Float skuVol;

	@Column(name = "sku_wt", length = 23)
	private Float skuWt;

	@Column(name = "sku_real_wt", length = 23)
	private Float skuRealWt;

	@Column(name = "case_yn", length = 1)
	private String caseYn;

	@Column(name = "case_len", length = 19)
	private Float caseLen;

	@Column(name = "case_wd", length = 19)
	private Float caseWd;

	@Column(name = "case_ht", length = 19)
	private Float caseHt;

	@Column(name = "case_vol", length = 19)
	private Float caseVol;

	@Column(name = "case_wt", length = 23)
	private Float caseWt;

	@Column(name = "box_len", length = 19)
	private Float boxLen;

	@Column(name = "box_wd", length = 19)
	private Float boxWd;

	@Column(name = "box_ht", length = 19)
	private Float boxHt;

	@Column(name = "box_vol", length = 19)
	private Float boxVol;

	@Column(name = "box_wt", length = 23)
	private Float boxWt;

	@Column(name = "box_real_wt", length = 23)
	private Float boxRealWt;

	@Column(name = "barcd_type", length = 20)
	private String barcdType;

	@Column(name = "sku_barcd2", length = 60)
	private String skuBarcd2;

	@Column(name = "sku_barcd3", length = 60)
	private String skuBarcd3;
	
	@Column(name = "front_image_url", length = 300)
	private String frontImageUrl;

	@Column(name = "del_flag")
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

	public String getSkuCd() {
		return skuCd;
	}

	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}

	public String getSkuClass() {
		return skuClass;
	}

	public void setSkuClass(String skuClass) {
		this.skuClass = skuClass;
	}

	public String getSkuNm() {
		return skuNm;
	}

	public void setSkuNm(String skuNm) {
		this.skuNm = skuNm;
	}

	public String getEngSkuNm() {
		return engSkuNm;
	}

	public void setEngSkuNm(String engSkuNm) {
		this.engSkuNm = engSkuNm;
	}

	public String getSkuType() {
		return skuType;
	}

	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	public String getBoxBarcd() {
		return boxBarcd;
	}

	public void setBoxBarcd(String boxBarcd) {
		this.boxBarcd = boxBarcd;
	}

	public String getCaseBarcd() {
		return caseBarcd;
	}

	public void setCaseBarcd(String caseBarcd) {
		this.caseBarcd = caseBarcd;
	}

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public Integer getPltBoxQty() {
		return pltBoxQty;
	}

	public void setPltBoxQty(Integer pltBoxQty) {
		this.pltBoxQty = pltBoxQty;
	}

	public Integer getExpirePeriod() {
		return expirePeriod;
	}

	public void setExpirePeriod(Integer expirePeriod) {
		this.expirePeriod = expirePeriod;
	}

	public Integer getSkuRank() {
		return skuRank;
	}

	public void setSkuRank(Integer skuRank) {
		this.skuRank = skuRank;
	}

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSupplierCd() {
		return supplierCd;
	}

	public void setSupplierCd(String supplierCd) {
		this.supplierCd = supplierCd;
	}

	public String getSupplierNm() {
		return supplierNm;
	}

	public void setSupplierNm(String supplierNm) {
		this.supplierNm = supplierNm;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	public String getStyleCd() {
		return styleCd;
	}

	public void setStyleCd(String styleCd) {
		this.styleCd = styleCd;
	}

	public String getStyleNm() {
		return styleNm;
	}

	public void setStyleNm(String styleNm) {
		this.styleNm = styleNm;
	}

	public String getSizeCd() {
		return sizeCd;
	}

	public void setSizeCd(String sizeCd) {
		this.sizeCd = sizeCd;
	}

	public String getSizeNm() {
		return sizeNm;
	}

	public void setSizeNm(String sizeNm) {
		this.sizeNm = sizeNm;
	}

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getColorNm() {
		return colorNm;
	}

	public void setColorNm(String colorNm) {
		this.colorNm = colorNm;
	}

	public String getBrandCd() {
		return brandCd;
	}

	public void setBrandCd(String brandCd) {
		this.brandCd = brandCd;
	}

	public String getBrandNm() {
		return brandNm;
	}

	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	public Float getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(Float skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getWtUnit() {
		return wtUnit;
	}

	public void setWtUnit(String wtUnit) {
		this.wtUnit = wtUnit;
	}

	public String getLenUnit() {
		return lenUnit;
	}

	public void setLenUnit(String lenUnit) {
		this.lenUnit = lenUnit;
	}

	public String getVolUnit() {
		return volUnit;
	}

	public void setVolUnit(String volUnit) {
		this.volUnit = volUnit;
	}

	public Float getSkuLen() {
		return skuLen;
	}

	public void setSkuLen(Float skuLen) {
		this.skuLen = skuLen;
	}

	public Float getSkuWd() {
		return skuWd;
	}

	public void setSkuWd(Float skuWd) {
		this.skuWd = skuWd;
	}

	public Float getSkuHt() {
		return skuHt;
	}

	public void setSkuHt(Float skuHt) {
		this.skuHt = skuHt;
	}

	public Float getSkuVol() {
		return skuVol;
	}

	public void setSkuVol(Float skuVol) {
		this.skuVol = skuVol;
	}

	public Float getSkuWt() {
		return skuWt;
	}

	public void setSkuWt(Float skuWt) {
		this.skuWt = skuWt;
	}

	public Float getSkuRealWt() {
		return skuRealWt;
	}

	public void setSkuRealWt(Float skuRealWt) {
		this.skuRealWt = skuRealWt;
	}

	public String getCaseYn() {
		return caseYn;
	}

	public void setCaseYn(String caseYn) {
		this.caseYn = caseYn;
	}

	public Float getCaseLen() {
		return caseLen;
	}

	public void setCaseLen(Float caseLen) {
		this.caseLen = caseLen;
	}

	public Float getCaseWd() {
		return caseWd;
	}

	public void setCaseWd(Float caseWd) {
		this.caseWd = caseWd;
	}

	public Float getCaseHt() {
		return caseHt;
	}

	public void setCaseHt(Float caseHt) {
		this.caseHt = caseHt;
	}

	public Float getCaseVol() {
		return caseVol;
	}

	public void setCaseVol(Float caseVol) {
		this.caseVol = caseVol;
	}

	public Float getCaseWt() {
		return caseWt;
	}

	public void setCaseWt(Float caseWt) {
		this.caseWt = caseWt;
	}

	public Float getBoxLen() {
		return boxLen;
	}

	public void setBoxLen(Float boxLen) {
		this.boxLen = boxLen;
	}

	public Float getBoxWd() {
		return boxWd;
	}

	public void setBoxWd(Float boxWd) {
		this.boxWd = boxWd;
	}

	public Float getBoxHt() {
		return boxHt;
	}

	public void setBoxHt(Float boxHt) {
		this.boxHt = boxHt;
	}

	public Float getBoxVol() {
		return boxVol;
	}

	public void setBoxVol(Float boxVol) {
		this.boxVol = boxVol;
	}

	public Float getBoxWt() {
		return boxWt;
	}

	public void setBoxWt(Float boxWt) {
		this.boxWt = boxWt;
	}

	public Float getBoxRealWt() {
		return boxRealWt;
	}

	public void setBoxRealWt(Float boxRealWt) {
		this.boxRealWt = boxRealWt;
	}

	public String getBarcdType() {
		return barcdType;
	}

	public void setBarcdType(String barcdType) {
		this.barcdType = barcdType;
	}

	public String getSkuBarcd2() {
		return skuBarcd2;
	}

	public void setSkuBarcd2(String skuBarcd2) {
		this.skuBarcd2 = skuBarcd2;
	}

	public String getSkuBarcd3() {
		return skuBarcd3;
	}

	public void setSkuBarcd3(String skuBarcd3) {
		this.skuBarcd3 = skuBarcd3;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getFrontImageUrl() {
		return frontImageUrl;
	}

	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
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

	/**
	 * comCd, skuCd로 SKU 조회
	 *
	 * @param comCd
	 * @param skuCd
	 * @param exceptionFlag
	 * @return
	 */
	public static SKU findByCode(String comCd, String skuCd, boolean exceptionFlag) {
		Map<String, Object> condition = ValueUtil.newMap("comCd,skuCd", comCd, skuCd);
		IQueryManager queryMgr = BeanUtil.get(IQueryManager.class);
		SKU sku = queryMgr.selectByCondition(SKU.class, condition);

		if(sku == null && exceptionFlag) {
			throw new ElidomRuntimeException("SKU를 찾을 수 없습니다");
		}

		return sku;
	}

}
