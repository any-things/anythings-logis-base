package xyz.anythings.base.service.model;

import java.util.List;

import xyz.anythings.base.entity.JobOrder;

public class PickingInfo {
	//private String boxResultId;
	private String boxId;
	private String locCd;
	private String skuCd;
	private String skuNm;
	private Integer totalPickedQty;
	private List<JobOrder> list;
	
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public String getLocCd() {
		return locCd;
	}
	public void setLocCd(String locCd) {
		this.locCd = locCd;
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
	public Integer getTotalPickedQty() {
		return totalPickedQty;
	}
	public void setTotalPickedQty(Integer totalPickedQty) {
		this.totalPickedQty = totalPickedQty;
	}
	public List<JobOrder> getList() {
		return list;
	}
	public void setList(List<JobOrder> list) {
		this.list = list;
	}
}
