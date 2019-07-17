package xyz.anythings.base.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.anythings.base.entity.JobInputSeq;
import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.util.FormatUtil;

/**
 * 작업 배치 요약 정보 
 * 
 * @author shortstop
 */
public class BatchSummary {

	/**
	 * 투입가능 버킷 수
	 */
	private Integer inputableBuckets;
	/**
	 * 주문 예정 수량 
	 */
	private Integer orderPickingQty;
	/**
	 * 주문 확정 수량 
	 */
	private Integer orderPickedQty;
	/**
	 * SKU 예정 수량 
	 */
	private Integer skuPickingQty;
	/**
	 * SKU 확정 수량 
	 */
	private Integer skuPickedQty;
	/**
	 * PCS 예정 수량 
	 */
	private Integer pcsPickingQty;
	/**
	 * PCS 확정 수량 
	 */
	private Integer pcsPickedQty;
	
	/**
	 * 전체 호기 대상 주문 예정 수량 
	 */
	private Integer totOrderPickingQty;
	/**
	 * 전체 호기 대상 주문 확정 수량 
	 */
	private Integer totOrderPickedQty;
	/**
	 * 전체 호기 대상 SKU 예정 수량 
	 */
	private Integer totSkuPickingQty;
	/**
	 * 전체 호기 대상 SKU 확정 수량 
	 */
	private Integer totSkuPickedQty;
	/**
	 * 전체 호기 대상 PCS 예정 수량 
	 */
	private Integer totPcsPickingQty;
	/**
	 * 전체 호기 대상 PCS 확정 수량 
	 */
	private Integer totPcsPickedQty;
	
	
	/**
	 * 총 배치 투입 개수
	 */
	private Integer total;	
	/**
	 * 배치 투입 리스트 Items 
	 */
	private List<JobInputSeq> items;
	
	public BatchSummary() {
	}
	
	public BatchSummary(Integer inputableBuckets
			, Integer orderPickingQty, Integer orderPickedQty, Integer skuPickingQty, Integer skuPickedQty, Integer pcsPickingQty, Integer pcsPickedQty
			, Integer totOrderPickingQty, Integer totOrderPickedQty, Integer totSkuPickingQty, Integer totSkuPickedQty, Integer totPcsPickingQty, Integer totPcsPickedQty) {
		
		this.inputableBuckets = inputableBuckets;
		this.orderPickingQty = orderPickingQty;
		this.orderPickedQty = orderPickedQty;
		this.skuPickingQty = skuPickingQty;
		this.skuPickedQty = skuPickedQty;
		this.pcsPickingQty = pcsPickingQty;
		this.pcsPickedQty = pcsPickedQty;
	}
	
	public BatchSummary(Integer inputableBuckets
			, Integer orderPickingQty, Integer orderPickedQty, Integer skuPickingQty, Integer skuPickedQty, Integer pcsPickingQty
			, Integer totOrderPickingQty, Integer totOrderPickedQty, Integer totSkuPickingQty, Integer totSkuPickedQty, Integer totPcsPickingQty, Integer totPcsPickedQty
			, Integer pcsPickedQty, Page<JobInputSeq> page) {
		this(inputableBuckets, orderPickingQty, orderPickedQty, skuPickingQty, skuPickedQty, pcsPickingQty, pcsPickedQty,totOrderPickingQty,totOrderPickedQty,totSkuPickingQty,totSkuPickedQty,totPcsPickingQty,totPcsPickedQty);
		this.total = page.getTotalSize();
		this.items = page.getList();
	}
	
	public BatchSummary(Integer inputableBuckets
			, Integer orderPickingQty, Integer orderPickedQty, Integer skuPickingQty, Integer skuPickedQty, Integer pcsPickingQty, Integer pcsPickedQty
			, Integer totOrderPickingQty, Integer totOrderPickedQty, Integer totSkuPickingQty, Integer totSkuPickedQty, Integer totPcsPickingQty, Integer totPcsPickedQty
			, Integer total, List<JobInputSeq> items) {
		this(inputableBuckets, orderPickingQty, orderPickedQty, skuPickingQty, skuPickedQty, pcsPickingQty, pcsPickedQty,totOrderPickingQty,totOrderPickedQty,totSkuPickingQty,totSkuPickedQty,totPcsPickingQty,totPcsPickedQty);
		this.total = total;
		this.items = items;
	}
	
	public BatchSummary(Map<?, ?> result) {
		this.parseQtys(result);
	}
	
	public Integer getInputableBuckets() {
		return inputableBuckets;
	}
	
	public void setInputableBuckets(Integer inputableBuckets) {
		this.inputableBuckets = inputableBuckets;
	}
	
	public Integer getOrderPickingQty() {
		return orderPickingQty;
	}
	
	public void setOrderPickingQty(Integer orderPickingQty) {
		this.orderPickingQty = orderPickingQty;
	}
	
	public Integer getOrderPickedQty() {
		return orderPickedQty;
	}
	
	public void setOrderPickedQty(Integer orderPickedQty) {
		this.orderPickedQty = orderPickedQty;
	}
	
	public Integer getSkuPickingQty() {
		return skuPickingQty;
	}
	
	public void setSkuPickingQty(Integer skuPickingQty) {
		this.skuPickingQty = skuPickingQty;
	}
	
	public Integer getSkuPickedQty() {
		return skuPickedQty;
	}
	
	public void setSkuPickedQty(Integer skuPickedQty) {
		this.skuPickedQty = skuPickedQty;
	}
	
	public Integer getPcsPickingQty() {
		return pcsPickingQty;
	}
	
	public void setPcsPickingQty(Integer pcsPickingQty) {
		this.pcsPickingQty = pcsPickingQty;
	}
	
	public Integer getPcsPickedQty() {
		return pcsPickedQty;
	}
	
	public void setPcsPickedQty(Integer pcsPickedQty) {
		this.pcsPickedQty = pcsPickedQty;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<JobInputSeq> getItems() {
		return items;
	}
	
	public void addItem(JobInputSeq inputSeq) {
		if(this.items == null) {
			this.items = new ArrayList<JobInputSeq>();
		}
		
		this.items.add(inputSeq);
	}
	
	public void setItems(List<JobInputSeq> items) {
		this.items = items;
	}
	
	public void parseQtys(Map<?, ?> result) {
		if(result != null) {
			this.orderPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_ORDER_PICKING_QTY");
			this.orderPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_ORDER_PICKED_QTY");
			this.skuPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_SKU_PICKING_QTY");
			this.skuPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_SKU_PICKED_QTY");
			this.pcsPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_PCS_PICKING_QTY");
			this.pcsPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_PCS_PICKED_QTY");
			this.inputableBuckets = AnyValueUtil.parseInteger(result, "P_OUT_INPUTABLE_BUCKETS");
			
			this.totOrderPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_ORDER_PICKING_QTY");
			this.totOrderPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_ORDER_PICKED_QTY");
			this.totSkuPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_SKU_PICKING_QTY");
			this.totSkuPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_SKU_PICKED_QTY");
			this.totPcsPickingQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_PCS_PICKING_QTY");
			this.totPcsPickedQty = AnyValueUtil.parseInteger(result, "P_OUT_TOT_PCS_PICKED_QTY");			
		}
	}
	
	public String toString() {
		return FormatUtil.toUnderScoreJsonString(this);
	}

	public Integer getTotOrderPickingQty() {
		return totOrderPickingQty;
	}

	public void setTotOrderPickingQty(Integer totOrderPickingQty) {
		this.totOrderPickingQty = totOrderPickingQty;
	}

	public Integer getTotOrderPickedQty() {
		return totOrderPickedQty;
	}

	public void setTotOrderPickedQty(Integer totOrderPickedQty) {
		this.totOrderPickedQty = totOrderPickedQty;
	}

	public Integer getTotSkuPickingQty() {
		return totSkuPickingQty;
	}

	public void setTotSkuPickingQty(Integer totSkuPickingQty) {
		this.totSkuPickingQty = totSkuPickingQty;
	}

	public Integer getTotSkuPickedQty() {
		return totSkuPickedQty;
	}

	public void setTotSkuPickedQty(Integer totSkuPickedQty) {
		this.totSkuPickedQty = totSkuPickedQty;
	}

	public Integer getTotPcsPickingQty() {
		return totPcsPickingQty;
	}

	public void setTotPcsPickingQty(Integer totPcsPickingQty) {
		this.totPcsPickingQty = totPcsPickingQty;
	}

	public Integer getTotPcsPickedQty() {
		return totPcsPickedQty;
	}

	public void setTotPcsPickedQty(Integer totPcsPickedQty) {
		this.totPcsPickedQty = totPcsPickedQty;
	}

}
