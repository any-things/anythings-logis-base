package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "sku", idStrategy = GenerationRule.UUID, uniqueFields="domainId,comCd,skuCd", indexes = {
	@Index(name = "ix_sku_0", columnList = "domain_id,com_cd,sku_cd", unique = true)
})
public class SKU extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 479169494461449914L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "com_cd", nullable = false, length = 30)
	private String comCd;

	@Column (name = "sku_cd", nullable = false, length = 30)
	private String skuCd;

	@Column (name = "sku_class", length = 20)
	private String skuClass;

	@Column (name = "sku_nm", nullable = false, length = 200)
	private String skuNm;

	@Column (name = "eng_sku_nm", length = 200)
	private String engSkuNm;

	@Column (name = "sku_type", length = 20)
	private String skuType;

	@Column (name = "box_in_qty", length = 12)
	private Integer boxInQty;

	@Column (name = "sku_barcd", length = 30)
	private String skuBarcd;

	@Column (name = "plt_box_qty", length = 12)
	private Integer pltBoxQty;

	@Column (name = "box_barcd", length = 30)
	private String boxBarcd;

	@Column (name = "box_wt", length = 19)
	private Float boxWt;

	@Column (name = "sku_wt", length = 19)
	private Float skuWt;

	@Column (name = "expire_period", length = 12)
	private Integer expirePeriod;

	@Column (name = "cell_cd", length = 30)
	private String cellCd;

	@Column (name = "image_url")
	private String imageUrl;

	@Column (name = "supplier_cd", length = 30)
	private String supplierCd;

	@Column (name = "supplier_nm", length = 100)
	private String supplierNm;

	@Column (name = "style_cd", length = 30)
	private String styleCd;

	@Column (name = "style_nm", length = 40)
	private String styleNm;

	@Column (name = "size_cd", length = 30)
	private String sizeCd;

	@Column (name = "size_nm", length = 40)
	private String sizeNm;

	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;

	@Column (name = "color_cd", length = 30)
	private String colorCd;

	@Column (name = "color_nm", length = 40)
	private String colorNm;

	@Column (name = "brand_cd", length = 30)
	private String brandCd;

	@Column (name = "brand_nm", length = 100)
	private String brandNm;

	@Column (name = "sku_price", length = 19)
	private Float skuPrice;

	@Column (name = "sku_len", length = 19)
	private Float skuLen;

	@Column (name = "sku_wd", length = 19)
	private Float skuWd;

	@Column (name = "sku_ht", length = 19)
	private Float skuHt;

	@Column (name = "sku_vol", length = 19)
	private Float skuVol;

	@Column (name = "box_len", length = 19)
	private Float boxLen;

	@Column (name = "box_wd", length = 19)
	private Float boxWd;

	@Column (name = "box_ht", length = 19)
	private Float boxHt;

	@Column (name = "box_vol", length = 19)
	private Float boxVol;

	@Column (name = "barcd_type", length = 20)
	private String barcdType;

	@Column (name = "sku_barcd2", length = 30)
	private String skuBarcd2;

	@Column (name = "sku_barcd3", length = 30)
	private String skuBarcd3;

	@Column (name = "wt_unit", length = 6)
	private String wtUnit;

	@Column (name = "len_unit", length = 6)
	private String lenUnit;

	@Column (name = "vol_unit", length = 6)
	private String volUnit;

	@Column (name = "sku_rank", length = 12)
	private Integer skuRank;

	@Column (name = "del_flag", length = 1)
	private Boolean delFlag;
  
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

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	public Integer getPltBoxQty() {
		return pltBoxQty;
	}

	public void setPltBoxQty(Integer pltBoxQty) {
		this.pltBoxQty = pltBoxQty;
	}

	public String getBoxBarcd() {
		return boxBarcd;
	}

	public void setBoxBarcd(String boxBarcd) {
		this.boxBarcd = boxBarcd;
	}

	public Float getBoxWt() {
		return boxWt;
	}

	public void setBoxWt(Float boxWt) {
		this.boxWt = boxWt;
	}

	public Float getSkuWt() {
		return skuWt;
	}

	public void setSkuWt(Float skuWt) {
		this.skuWt = skuWt;
	}

	public Integer getExpirePeriod() {
		return expirePeriod;
	}

	public void setExpirePeriod(Integer expirePeriod) {
		this.expirePeriod = expirePeriod;
	}

	public String getCellCd() {
		return cellCd;
	}

	public void setCellCd(String cellCd) {
		this.cellCd = cellCd;
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

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
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

	public Integer getSkuRank() {
		return skuRank;
	}

	public void setSkuRank(Integer skuRank) {
		this.skuRank = skuRank;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}	
}
