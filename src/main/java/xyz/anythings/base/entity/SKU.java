package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "sku", idStrategy = GenerationRule.UUID, uniqueFields="domainId,comCd,skuCd", indexes = {
	@Index(name = "ix_sku_0", columnList = "domain_id,com_cd,sku_cd", unique = true),
	@Index(name = "ix_sku_1", columnList = "domain_id,sku_barcd"),
	@Index(name = "ix_sku_2", columnList = "domain_id,box_barcd")
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

	@Column (name = "sku_nm", nullable = false, length = 200)
	private String skuNm;
	
	@Column (name = "sku_desc", length = 200)
	private String skuDesc;
	
	@Column (name = "sku_type", length = 20)
	private String skuType;
	
	@Column (name = "sku_class", length = 40)
	private String skuClass;
	
	@Column (name = "sku_barcd", length = 30)
	private String skuBarcd;
	
	@Column (name = "box_barcd", length = 30)
	private String boxBarcd;

	@Column (name = "sku_barcd2", length = 30)
	private String skuBarcd2;

	@Column (name = "sku_barcd3", length = 30)
	private String skuBarcd3;
	
	@Column (name = "barcd_type", length = 20)
	private String barcdType;
	
	@Column (name = "box_in_qty", length = 12)
	private Integer boxInQty;

	@Column (name = "plt_box_qty", length = 12)
	private Integer pltBoxQty;

	@Column (name = "season_cd", length = 30)
	private String seasonCd;
	
	@Column (name = "brand_cd", length = 30)
	private String brandCd;
	
	@Column (name = "style_cd", length = 30)
	private String styleCd;

	@Column (name = "size_cd", length = 30)
	private String sizeCd;

	@Column (name = "color_cd", length = 30)
	private String colorCd;
	
	@Column (name = "expire_period", length = 12)
	private Integer expirePeriod;
	
	@Column (name = "supplier_cd", length = 30)
	private String supplierCd;

	@Column (name = "supplier_nm", length = 100)
	private String supplierNm;

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
	
	@Column (name = "sku_wt", length = 19)
	private Float skuWt;
	
	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;

	@Column (name = "wt_unit", length = 6)
	private String wtUnit;

	@Column (name = "len_unit", length = 6)
	private String lenUnit;

	@Column (name = "vol_unit", length = 6)
	private String volUnit;

	@Column (name = "cell_cd", length = 30)
	private String cellCd;

	@Column (name = "image_url", length = 255)
	private String imageUrl;

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

	public String getSkuNm() {
		return skuNm;
	}

	public void setSkuNm(String skuNm) {
		this.skuNm = skuNm;
	}

	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}

	public String getSkuType() {
		return skuType;
	}

	public void setSkuType(String skuType) {
		this.skuType = skuType;
	}

	public String getSkuClass() {
		return skuClass;
	}

	public void setSkuClass(String skuClass) {
		this.skuClass = skuClass;
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

	public String getBarcdType() {
		return barcdType;
	}

	public void setBarcdType(String barcdType) {
		this.barcdType = barcdType;
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

	public String getSeasonCd() {
		return seasonCd;
	}

	public void setSeasonCd(String seasonCd) {
		this.seasonCd = seasonCd;
	}

	public String getBrandCd() {
		return brandCd;
	}

	public void setBrandCd(String brandCd) {
		this.brandCd = brandCd;
	}

	public String getStyleCd() {
		return styleCd;
	}

	public void setStyleCd(String styleCd) {
		this.styleCd = styleCd;
	}

	public String getSizeCd() {
		return sizeCd;
	}

	public void setSizeCd(String sizeCd) {
		this.sizeCd = sizeCd;
	}

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public Integer getExpirePeriod() {
		return expirePeriod;
	}

	public void setExpirePeriod(Integer expirePeriod) {
		this.expirePeriod = expirePeriod;
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

	public Float getSkuWt() {
		return skuWt;
	}

	public void setSkuWt(Float skuWt) {
		this.skuWt = skuWt;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

}
