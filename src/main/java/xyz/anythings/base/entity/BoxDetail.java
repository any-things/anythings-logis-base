package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "tb_if_box_detail", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_if_box_detail_0", columnList = "box_result_id,sku_cd"),
	@Index(name = "ix_tb_if_box_detail_1", columnList = "order_id,order_line_no"),
	@Index(name = "ix_tb_if_box_detail_2", columnList = "order_detail_id,domain_id"),
	@Index(name = "ix_tb_if_box_detail_3", columnList = "wcs_work_no,wcs_work_detail_no")
})
public class BoxDetail extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 812989388011916280L;

	/**
	 * 박스 상태 - 박싱 대기 (피킹 중)
	 */
	public static final String STATUS_WAIT = "W";
	/**
	 * 박스 상태 - 박싱 완료
	 */
	public static final String STATUS_BOXED = "B";
	/**
	 * 박스 상태 - 검수 완료
	 */
	public static final String STATUS_EXAMED = "E";
	/**
	 * 박스 상태 - 포장 완료
	 */
	public static final String STATUS_PACKED = "P";
	/**
	 * 박스 상태 - 실적 전송 완료
	 */
	public static final String STATUS_REPORTED = "R";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "box_result_id", nullable = false, length = 40)
	private String boxResultId;
	
	/**
	 * WCS I/F용 : 센터 코드
	 */	
	@Column (name = "dc_cd", length = 30)
	private String dcCd;
	
	/**
	 * WCS I/F용 : WAVE No.
	 */	
	@Column (name = "job_id", length = 40)
	private String jobId;

	@Column (name = "order_id", nullable = false, length = 40)
	private String orderId;
	
	@Column (name = "order_date", length = 10)
	private String orderDate;

	@Column (name = "order_line_no", length = 40)
	private String orderLineNo;

	@Column (name = "order_detail_id", length = 40)
	private String orderDetailId;

	/**
	 * WCS I/F용 : WCS 배치 순번 (BTCH_SEQ)
	 */
	@Column (name = "wcs_work_seq", length = 10)
	private Integer wcsWorkSeq;
	
	/**
	 * WCS I/F용 : WCS 작업번호 (WRK_NO)
	 */
	@Column (name = "wcs_work_no", length = 50)
	private String wcsWorkNo;

	/**
	 * WCS I/F용 : WCS 작업상세순번 (WRK_DTL_SEQ)
	 */
	@Column (name = "wcs_work_detail_no", length = 10)
	private Integer wcsWorkDetailNo;
	
	@Column (name = "com_cd", length = 32)
	private String comCd;
	
	@Column (name = "cust_cd", length = 32)
	private String custCd;

	@Column (name = "sku_cd", nullable = false, length = 50)
	private String skuCd;

	@Column (name = "sku_nm", length = 385)
	private String skuNm;

	/**
	 * WCS I/F 용 : SKU 낱개 중량 - 검수 기준
	 */
	@Column (name = "sku_wt", length = 23)
	private Float skuWt;

	/**
	 * I-Nex I/F용 : 상품별 포장 유형
	 */
	@Column (name = "pack_type", length = 20)
	private String packType;

	/**
	 * 피킹 예정 수량 - B2C에서만 의미가 있음
	 */
	@Column (name = "pick_qty", length = 19)
	private Integer pickQty;
	/**
	 * 피킹 확정 수량
	 */
	@Column (name = "picked_qty", nullable = false, length = 19)
	private Integer pickedQty;

	/**
	 * WCS I/F 용 : 상품별 주문 취소 개수
	 */
	@Column (name = "cancel_qty", length = 19)
	private Integer cancelQty;

	/**
	 * 박스 상태 - W: 박싱 대기 (피킹 중), B: 박싱 완료, E: 검수 완료, P: 포장 완료, R: 실적 전송 완료
	 */
	@Column (name = "status", length = 10)
	private String status;

	/**
	 * I-Nex I/F용 : 상품별 검수 완료 여부
	 */
	@Column (name = "insp_result")
	private Boolean inspResult;

	/**
	 * WCS I/F 용 : 주문 라인별 인터페이스 완료 여부
	 */
	@Column (name = "if_reported_at", length = 22)
	private String ifReportedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBoxResultId() {
		return boxResultId;
	}

	public void setBoxResultId(String boxResultId) {
		this.boxResultId = boxResultId;
	}

	public String getDcCd() {
		return dcCd;
	}

	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
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

	public Integer getWcsWorkSeq() {
		return wcsWorkSeq;
	}

	public void setWcsWorkSeq(Integer wcsWorkSeq) {
		this.wcsWorkSeq = wcsWorkSeq;
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

	public Float getSkuWt() {
		return skuWt;
	}

	public void setSkuWt(Float skuWt) {
		this.skuWt = skuWt;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public Integer getPickQty() {
		return pickQty;
	}

	public void setPickQty(Integer pickQty) {
		this.pickQty = pickQty;
	}

	public Integer getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(Integer pickedQty) {
		this.pickedQty = pickedQty;
	}

	public Integer getCancelQty() {
		return cancelQty;
	}

	public void setCancelQty(Integer cancelQty) {
		this.cancelQty = cancelQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getInspResult() {
		return inspResult;
	}

	public void setInspResult(Boolean inspResult) {
		this.inspResult = inspResult;
	}

	public String getIfReportedAt() {
		return ifReportedAt;
	}

	public void setIfReportedAt(String ifReportedAt) {
		this.ifReportedAt = ifReportedAt;
	}

}
