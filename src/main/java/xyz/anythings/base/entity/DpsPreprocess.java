package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_dps_preprocess", idStrategy = GenerationRule.UUID, uniqueFields="domainId,batchId,skuCd,locCd", indexes = {
	@Index(name = "ix_tb_dps_preprocess_0", columnList = "loc_cd,sku_cd,batch_id,domain_id", unique = true),
	@Index(name = "ix_tb_dps_preprocess_1", columnList = "batch_id")
})
public class DpsPreprocess extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 655151388700965282L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "batch_id", nullable = false, length = 40)
	private String batchId;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	@Column (name = "region_cd", length = 30)
	private String regionCd;

	@Column (name = "region_nm", length = 100)
	private String regionNm;

	@Column (name = "loc_cd", length = 30)
	private String locCd;

	@Column (name = "order_qty", nullable = false, length = 19)
	private Integer orderQty;

	@Column (name = "order_pcs_qty", nullable = false, length = 19)
	private Integer orderPcsQty;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
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

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getRegionNm() {
		return regionNm;
	}

	public void setRegionNm(String regionNm) {
		this.regionNm = regionNm;
	}

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public Integer getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}

	public Integer getOrderPcsQty() {
		return orderPcsQty;
	}

	public void setOrderPcsQty(Integer orderPcsQty) {
		this.orderPcsQty = orderPcsQty;
	}
}
