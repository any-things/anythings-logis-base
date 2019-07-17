package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_if_order", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_if_order_0", columnList = "batch_id"),
	@Index(name = "ix_tb_if_order_1", columnList = "com_cd,job_date,job_batch_seq,domain_id"),
	@Index(name = "ix_tb_if_order_2", columnList = "order_id,order_line_no,order_detail_id"),
	@Index(name = "ix_tb_if_order_3", columnList = "invoice_id"),
	@Index(name = "ix_tb_if_order_4", columnList = "region_cd"),
	@Index(name = "ix_tb_if_order_5", columnList = "sku_cd")
})
public class JobOrder extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 454654554810518700L;

	/**
	 * 주문 수신 상태 : 작업 투입
	 */
	public static final String ORDER_STATUS_INPUT = "I";
	/**
	 * 주문 수신 상태 : 작업 대기
	 */
	public static final String ORDER_STATUS_WAIT = "W";
	/**
	 * 주문 수신 상태 : 작업 진행
	 */
	public static final String ORDER_STATUS_RUNNING = "R";
	/**
	 * 주문 수신 상태 : 작업 완료
	 */
	public static final String ORDER_STATUS_FINISHED = "F";	

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "batch_id", length = 40)
	private String batchId;
	
	@Column (name = "job_id", nullable = false, length = 40)
	private String jobId;
	
	@Column (name = "job_date", nullable = false, length = 10)
	private String jobDate;

	@Column (name = "job_batch_seq", nullable = false, length = 10)
	private Integer jobBatchSeq;

	@Column (name = "order_date", length = 10)
	private String orderDate;
	
	@Column (name = "order_id", nullable = false, length = 40)
	private String orderId;

	@Column (name = "order_line_no", nullable = false, length = 40)
	private String orderLineNo;

	@Column (name = "order_detail_id", length = 40)
	private String orderDetailId;
	
	@Column (name = "cust_order_id", length = 40)
	private String custOrderId;

	@Column (name = "cust_order_line_no", length = 40)
	private String custOrderLineNo;	

	@Column (name = "wcs_work_seq", length = 10)
	private Integer wcsWorkSeq;
	
	@Column (name = "wcs_work_type", length = 50)
	private String wcsWorkType;
	
	@Column (name = "wcs_work_no", length = 50)
	private String wcsWorkNo;
	
	@Column (name = "wcs_work_detail_no", length = 10)
	private Integer wcsWorkDetailNo;	
	
	@Column (name = "center_cd", length = 30)
	private String centerCd;
	
	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;
	
	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "cust_cd", length = 32)
	private String custCd;

	@Column (name = "cust_nm", length = 300)
	private String custNm;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_barcd", length = 60)
	private String skuBarcd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;
	
	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;
	
	@Column (name = "box_id", length = 50)
	private String boxId;
	
	@Column (name = "box_in_qty", length = 18)
	private Integer boxInQty;
	
	@Column(name = "residual_vol", length = 19)
	private Float residualVol;
	
	@Column (name = "qty_unit", length = 6)
	private String qtyUnit;	
	
	@Column (name = "sku_expire_date", length = 10)
	private String skuExpireDate;	

	@Column (name = "store_loc_cd", length = 30)
	private String storeLocCd;
	
	@Column (name = "store_zone_cd", length = 30)
	private String storeZoneCd;
	
	@Column (name = "to_loc_cd", length = 30)
	private String toLocCd;	

	@Column (name = "region_cd", length = 30)
	private String regionCd;
	
	@Column (name = "equip_id", length = 40)
	private String equipId;
	
	@Column (name = "chute_no", length = 40)
	private String chuteNo;

	@Column (name = "order_qty", nullable = false, length = 19)
	private Integer orderQty;
	
	@Column (name = "picked_qty", length = 19)
	private Integer pickedQty;
	
	@Column (name = "boxed_qty", length = 19)
	private Integer boxedQty;
	
	@Column (name = "cancel_qty", length = 19)
	private Integer cancelQty;

	@Column (name = "invoice_id", length = 40)
	private String invoiceId;
	
	@Column (name = "wms_pick_yn", length = 1)
	private String wmsPickYn;
	
	/**
	 * INEX에서는 ORDER_GROUP = AREA_CD
	 */
	@Column (name = "order_group", length = 100)
	private String orderGroup;
	
	@Column (name = "alloc_no", length = 40)
	private String allocNo;
	
	@Column (name = "vehicle_no", length = 40)
	private String vehicleNo;	
	
	@Column (name = "status", length = 10)
	private String status;

	@Column (name = "order_type", length = 20)
	private String orderType;
	
	@Column (name = "order_class", length = 10)
	private String orderClass;

	@Column (name = "pack_type", length = 20)
	private String packType;
	
	@Column (name = "pass_stop_cd", length = 30)
	private String passStopCd;

	@Column (name = "pass_stop_nm", length = 100)
	private String passStopNm;
	
	@Column (name = "lot_no", length = 40)
	private String lotNo;
	
	@Column (name = "mps_order_type", length = 20)
	private String mpsOrderType;
	
	@Column(name = "attr01", length = 100)
	private String attr01;
	
	@Column(name = "attr02", length = 100)
	private String attr02;
	
	@Column(name = "attr03", length = 100)
	private String attr03;
	
	@Column(name = "attr04", length = 200)
	private String attr04;
	
	@Column(name = "attr05", length = 100)
	private String attr05;

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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public Integer getJobBatchSeq() {
		return jobBatchSeq;
	}

	public void setJobBatchSeq(Integer jobBatchSeq) {
		this.jobBatchSeq = jobBatchSeq;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(String custOrderId) {
		this.custOrderId = custOrderId;
	}

	public String getCustOrderLineNo() {
		return custOrderLineNo;
	}

	public void setCustOrderLineNo(String custOrderLineNo) {
		this.custOrderLineNo = custOrderLineNo;
	}

	public Integer getWcsWorkSeq() {
		return wcsWorkSeq;
	}

	public void setWcsWorkSeq(Integer wcsWorkSeq) {
		this.wcsWorkSeq = wcsWorkSeq;
	}
	
	public String getWcsWorkType() {
		return wcsWorkType;
	}

	public void setWcsWorkType(String wcsWorkType) {
		this.wcsWorkType = wcsWorkType;
	}

	public String getWcsWorkNo() {
		return wcsWorkNo;
	}

	public void setWcsWorkNo(String wcsWorkNo) {
		this.wcsWorkNo = wcsWorkNo;
	}

	public Integer getWcsWorkDetailNo() {
		return wcsWorkDetailNo;
	}

	public void setWcsWorkDetailNo(Integer wcsWorkDetailNo) {
		this.wcsWorkDetailNo = wcsWorkDetailNo;
	}

	public String getCenterCd() {
		return centerCd;
	}

	public void setCenterCd(String centerCd) {
		this.centerCd = centerCd;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
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

	public String getSkuCd() {
		return skuCd;
	}

	public void setSkuCd(String skuCd) {
		this.skuCd = skuCd;
	}

	public String getSkuBarcd() {
		return skuBarcd;
	}

	public void setSkuBarcd(String skuBarcd) {
		this.skuBarcd = skuBarcd;
	}

	public String getSkuNm() {
		return skuNm;
	}

	public void setSkuNm(String skuNm) {
		this.skuNm = skuNm;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public Integer getBoxInQty() {
		return boxInQty;
	}

	public void setBoxInQty(Integer boxInQty) {
		this.boxInQty = boxInQty;
	}

	public Float getResidualVol() {
		return residualVol;
	}

	public void setResidualVol(Float residualVol) {
		this.residualVol = residualVol;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public String getSkuExpireDate() {
		return skuExpireDate;
	}

	public void setSkuExpireDate(String skuExpireDate) {
		this.skuExpireDate = skuExpireDate;
	}

	public String getStoreLocCd() {
		return storeLocCd;
	}

	public void setStoreLocCd(String storeLocCd) {
		this.storeLocCd = storeLocCd;
	}

	public String getStoreZoneCd() {
		return storeZoneCd;
	}

	public void setStoreZoneCd(String storeZoneCd) {
		this.storeZoneCd = storeZoneCd;
	}

	public String getToLocCd() {
		return toLocCd;
	}

	public void setToLocCd(String toLocCd) {
		this.toLocCd = toLocCd;
	}

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getChuteNo() {
		return chuteNo;
	}

	public void setChuteNo(String chuteNo) {
		this.chuteNo = chuteNo;
	}

	public Integer getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Integer orderQty) {
		this.orderQty = orderQty;
	}

	public Integer getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(Integer pickedQty) {
		this.pickedQty = pickedQty;
	}

	public Integer getBoxedQty() {
		return boxedQty;
	}

	public void setBoxedQty(Integer boxedQty) {
		this.boxedQty = boxedQty;
	}

	public Integer getCancelQty() {
		return cancelQty;
	}

	public void setCancelQty(Integer cancelQty) {
		this.cancelQty = cancelQty;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(String orderGroup) {
		this.orderGroup = orderGroup;
	}

	public String getAllocNo() {
		return allocNo;
	}

	public void setAllocNo(String allocNo) {
		this.allocNo = allocNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderClass() {
		return orderClass;
	}

	public void setOrderClass(String orderClass) {
		this.orderClass = orderClass;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getPassStopCd() {
		return passStopCd;
	}

	public void setPassStopCd(String passStopCd) {
		this.passStopCd = passStopCd;
	}

	public String getPassStopNm() {
		return passStopNm;
	}

	public void setPassStopNm(String passStopNm) {
		this.passStopNm = passStopNm;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getMpsOrderType() {
		return mpsOrderType;
	}

	public void setMpsOrderType(String mpsOrderType) {
		this.mpsOrderType = mpsOrderType;
	}

	public String getWmsPickYn() {
		return wmsPickYn;
	}

	public void setWmsPickYn(String wmsPickYn) {
		this.wmsPickYn = wmsPickYn;
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

}
