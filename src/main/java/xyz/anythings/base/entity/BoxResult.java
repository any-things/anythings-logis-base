package xyz.anythings.base.entity;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import xyz.anythings.base.service.model.InspectionItem;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Ignore;
import xyz.elidom.dbist.annotation.Index;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.Table;
import xyz.elidom.dbist.dml.Filter;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.exception.server.ElidomValidationException;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@Table(name = "tb_if_box_result", idStrategy = GenerationRule.UUID, indexes = {
	@Index(name = "ix_tb_if_box_result_0", columnList = "order_id,batch_id,domain_id"),
	@Index(name = "ix_tb_if_box_result_1", columnList = "invoice_id"),
	@Index(name = "ix_tb_if_box_result_2", columnList = "box_id"),
	@Index(name = "ix_tb_if_box_result_3", columnList = "cust_cd,com_cd,batch_id"),
	@Index(name = "ix_tb_if_box_result_4", columnList = "loc_cd,region_cd,status,batch_id")
})
public class BoxResult extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 722537874338634425L;

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
	 * 박스 상태 - 중량 검수 리젝
	 */
	public static final String STATUS_WEIGHT_REJECT = "R";
	/**
	 * 박스 상태 - 포장 완료
	 */
	public static final String STATUS_PACKED = "P";
	/**
	 * 박스 상태 - 권역분 완료
	 */
	public static final String STATUS_SORTED = "S";
	/**
	 * 박스 상태 - 실적 전송 완료
	 */
	public static final String STATUS_REPORTED = "R";
	/**
	 * 박스 상태 - 박싱 취소 (삭제)
	 */
	public static final String STATUS_DELETED = "D";

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "batch_id", length = 40)
	private String batchId;

	@Column (name = "job_id", length = 40)
	private String jobId;

	@Column (name = "job_type", length = 20)
	private String jobType;

	/**
	 * 작업(출고) 일자
	 */
	@Column (name = "job_date", nullable = false, length = 10)
	private String jobDate;

	@Column (name = "job_batch_seq", nullable = false, length = 10)
	private Integer jobBatchSeq;

	/**
	 * WCS I/F 용 : 센터 코드 (동탄 : DT, 양지 : YJ ...)
	 */
	@Column (name = "center_cd", length = 30)
	private String centerCd;

	@Column (name = "dc_cd", nullable = false, length = 30)
	private String dcCd;

	@Column (name = "com_cd", nullable = false, length = 32)
	private String comCd;

	@Column (name = "cust_cd", length = 32)
	private String custCd;

	@Column (name = "cust_nm", length = 150)
	private String custNm;

	/**
	 * WCS I/F용 : 주문일
	 */
	@Column (name = "order_date", length = 10)
	private String orderDate;

	@Column (name = "order_id", nullable = false, length = 40)
	private String orderId;

	@Column (name = "cust_order_id", length = 40)
	private String custOrderId;

	@Column (name = "invoice_id", length = 40)
	private String invoiceId;

	@Column (name = "box_id", length = 40)
	private String boxId;

	/**
	 * WCS I/F용 : WCS 배치 순번 (BTCH_SEQ)
	 */
	@Column (name = "wcs_work_seq", length = 10)
	private Integer wcsWorkSeq;

	/**
	 * WCS I/F용 : WCS 배치작업구분코드 (WRK_SPR_CD)
	 */
	@Column (name = "wcs_work_type", length = 50)
	private String wcsWorkType;
	/**
	 * WCS I/F용 : WCS 작업번호 (WRK_NO)
	 */
	@Column (name = "wcs_work_no", length = 40)
	private String wcsWorkNo;

	/**
	 * WCS I/F용 : WCS 작업상세순번 (WRK_DTL_SEQ)
	 */
	@Column (name = "wcs_work_detail_no", length = 10)
	private Integer wcsWorkDetailNo;

	@Column (name = "equip_id", length = 40)
	private String equipId;

	@Column (name = "chute_no", length = 40)
	private String chuteNo;

	@Column (name = "region_cd", length = 30)
	private String regionCd;

	@Column (name = "loc_cd", length = 30)
	private String locCd;

	@Column (name = "box_type_cd", length = 30)
	private String boxTypeCd;

	/**
	 * WCS I/F 용 : 택배로 나가는 박스는 T, B2B  소분박스는 S, 1SKU 한박스인 경우 W
	 */
	@Column (name = "wms_box_type", length = 1)
	private String wmsBoxType;

	@Column (name = "order_group", length = 100)
	private String orderGroup;

	@Column (name = "total_box_qty", length = 19)
	private Integer totalBoxQty;

	@Column (name = "box_seq", length = 10)
	private Integer boxSeq;

	/**
	 * WCS I/F 용 : 해당 박스내 상품 종류 수
	 */
	@Column (name = "box_sku_cnt", length = 19)
	private Integer boxSkuCnt;

	/**
	 * I-Nex I/F용 : 박스 무게
	 */
	@Column (name = "box_wt", length = 23)
	private Float boxWt;

	/**
	 * ECS I/F용 : 박스 최소 무게
	 */
	@Column (name = "box_wt_min", length = 23)
	private Float boxWtMin;

	/**
	 * ECS I/F용 : 박스 최대 무게
	 */
	@Column (name = "box_wt_max", length = 23)
	private Float boxWtMax;
	/**
	 * ECS I/F용 : 박스 표준 무게
	 */
	@Column (name = "std_box_wt", length = 23)
	private Float stdBoxWt;

	/**
	 * I-Nex I/F용 : 포장 유형
	 */
	@Column (name = "pack_type", length = 20)
	private String packType;

	/**
	 * I-Nex I/F용 : 검수 유형
	 */
	@Column (name = "insp_type", length = 20)
	private String inspType;

	@Column (name = "pick_qty", length = 19)
	private Integer pickQty;

	@Column (name = "picked_qty", length = 19)
	private Integer pickedQty;

	@Column (name = "status", length = 10)
	private String status;

	/**
	 * I-Nex I/F용 : 박스 전체 검수 완료 여부
	 */
	@Column (name = "insp_result")
	private Boolean inspResult;

	/**
	 * I-Nex I/F용 : 세관 검사 완료 여부
	 */
	@Column (name = "cstm_flag", length = 1)
	private Boolean cstmFlag;

	/**
	 * WCS I/F용 : 주문 전체 취소 여부
	 */
	@Column (name = "cancel_flag", length = 1)
	private Boolean cancelFlag;

	@Column (name = "inspector_id", length = 32)
	private String inspectorId;

	@Column (name = "insp_started_at", length = 22)
	private String inspStartedAt;

	@Column (name = "insp_ended_at", length = 22)
	private String inspEndedAt;

	@Column (name = "final_ended_at", length = 22)
	private String finalEndedAt;

	@Column (name = "if_reported_at", length = 22)
	private String ifReportedAt;
	
	@Ignore
	private List<BoxDetail> boxDetails;
	
	@Ignore
	private boolean isLastBox;

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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(String custOrderId) {
		this.custOrderId = custOrderId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
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

	public String getRegionCd() {
		return regionCd;
	}

	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}

	public String getLocCd() {
		return locCd;
	}

	public void setLocCd(String locCd) {
		this.locCd = locCd;
	}

	public String getBoxTypeCd() {
		return boxTypeCd;
	}

	public void setBoxTypeCd(String boxTypeCd) {
		this.boxTypeCd = boxTypeCd;
	}

	public String getWmsBoxType() {
		return wmsBoxType;
	}

	public void setWmsBoxType(String wmsBoxType) {
		this.wmsBoxType = wmsBoxType;
	}

	public String getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(String orderGroup) {
		this.orderGroup = orderGroup;
	}

	public Integer getTotalBoxQty() {
		return totalBoxQty;
	}

	public void setTotalBoxQty(Integer totalBoxQty) {
		this.totalBoxQty = totalBoxQty;
	}

	public Integer getBoxSeq() {
		return boxSeq;
	}

	public void setBoxSeq(Integer boxSeq) {
		this.boxSeq = boxSeq;
	}

	public Integer getBoxSkuCnt() {
		return boxSkuCnt;
	}

	public void setBoxSkuCnt(Integer boxSkuCnt) {
		this.boxSkuCnt = boxSkuCnt;
	}

	public Float getBoxWt() {
		return boxWt;
	}

	public void setBoxWt(Float boxWt) {
		this.boxWt = boxWt;
	}

	public Float getBoxWtMin() {
		return boxWtMin;
	}

	public void setBoxWtMin(Float boxWtMin) {
		this.boxWtMin = boxWtMin;
	}

	public Float getBoxWtMax() {
		return boxWtMax;
	}

	public void setBoxWtMax(Float boxWtMax) {
		this.boxWtMax = boxWtMax;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getInspType() {
		return inspType;
	}

	public void setInspType(String inspType) {
		this.inspType = inspType;
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

	public Boolean getCstmFlag() {
		return cstmFlag;
	}

	public void setCstmFlag(Boolean cstmFlag) {
		this.cstmFlag = cstmFlag;
	}

	public Boolean getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(Boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(String inspectorId) {
		this.inspectorId = inspectorId;
	}

	public String getInspStartedAt() {
		return inspStartedAt;
	}

	public void setInspStartedAt(String inspStartedAt) {
		this.inspStartedAt = inspStartedAt;
	}

	public String getInspEndedAt() {
		return inspEndedAt;
	}

	public void setInspEndedAt(String inspEndedAt) {
		this.inspEndedAt = inspEndedAt;
	}

	public String getFinalEndedAt() {
		return finalEndedAt;
	}

	public void setFinalEndedAt(String finalEndedAt) {
		this.finalEndedAt = finalEndedAt;
	}

	public String getIfReportedAt() {
		return ifReportedAt;
	}

	public void setIfReportedAt(String ifReportedAt) {
		this.ifReportedAt = ifReportedAt;
	}

	public Float getStdBoxWt() {
		return stdBoxWt;
	}

	public void setStdBoxWt(Float stdBoxWt) {
		this.stdBoxWt = stdBoxWt;
	}

	public boolean getIsLastBox() {
		return isLastBox;
	}

	public void setIsLastBox(boolean isLastBox) {
		this.isLastBox = isLastBox;
	}

	public List<BoxDetail> getBoxDetails() {
		return boxDetails;
	}

	public void setBoxDetails(List<BoxDetail> boxDetails) {
		this.boxDetails = boxDetails;
	}

	/**
	 * 박스 내품 내역 조회
	 *
	 * @return
	 */
	public List<BoxDetail> searchBoxDetails() {
		Query condition = AnyOrmUtil.newConditionForExecution(this.domainId);
		condition.addFilter(new Filter("boxResultId", this.id));
		return BeanUtil.get(IQueryManager.class).selectList(BoxDetail.class, condition);
	}

	/**
	 * 수량 검수를 위한 내품 내역 조회
	 *
	 * @param byWeight
	 * @return
	 */
	public List<InspectionItem> inspectionItems(boolean byWeight) {
		StringBuffer sql = new StringBuffer();
		sql.append("select item.sku_cd, sku.sku_nm, sku.sku_barcd, item.pick_qty as item_qty");

		if(byWeight) {
			sql.append(", sku.sku_real_wt as weight");
		}

		sql.append(" from tb_if_box_detail item left outer join tb_sku sku on sku.com_cd = :comCd and sku.sku_cd = item.sku_cd")
		   .append(" where item.domain_id = :domainId and item.box_result_id = :boxResultId");
		Map<String, Object> condition = ValueUtil.newMap("domainId,boxResultId,comCd", this.domainId, this.id, this.comCd);
		return BeanUtil.get(IQueryManager.class).selectListBySql(sql.toString(), condition, InspectionItem.class, 0, 0);
	}

	/**
	 * id로 BoxResult 조회
	 *
	 * @param domainId
	 * @param id
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static BoxResult find(Long domainId, String id, boolean exceptionWhenEmpty) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter(SysConstants.ENTITY_FIELD_ID, id);
		BoxResult box = BeanUtil.get(IQueryManager.class).select(BoxResult.class, condition);

		if(box == null && exceptionWhenEmpty) {
			throw ThrowUtil.newNotFoundRecord("terms.menu.BoxResult", id);
		}

		return box;
	}

	/**
	 * invoiceId가 unique하다는 가정을 해야한다. 그렇지 않으면 이 API를 사용하면 안된다. 
	 * invoiceId로 BoxResult를 조회
	 *
	 * @param domainId
	 * @param invoiceId
	 * @param exceptionWhenEmpty invoiceId로 박스 조회 실패시 에러 발생 여부
	 * @return
	 */
	public static BoxResult findByInvoiceId(Long domainId, String invoiceId, boolean exceptionWhenEmpty) {
		return BoxResult.findBy(domainId, exceptionWhenEmpty, null, "invoiceId", invoiceId);
	}

	/**
	 * orderId가 unique하다는 가정을 해야한다. 그렇지 않으면 이 API를 사용하면 안된다.
	 * orderId로 BoxResult를 조회
	 *
	 * @param domainId
	 * @param batchId
	 * @param orderId
	 * @param exceptionWhenEmpty orderId로 박스 조회 실패시 에러 발생 여부
	 * @return
	 */
	public static BoxResult findByOrderId(Long domainId, String orderId, boolean exceptionWhenEmpty) {
		return BoxResult.findBy(domainId, exceptionWhenEmpty, null, "orderId", orderId);
	}

	/**
	 * 박스 ID로 박스 정보 조회
	 *
	 * @param domainId
	 * @param batchId
	 * @param boxId
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static BoxResult findByBoxId(Long domainId, String batchId, String boxId, boolean exceptionWhenEmpty) {
		return BoxResult.findBy(domainId, exceptionWhenEmpty, null, "batchId,boxId", batchId, boxId);
	}
	
	/**
	 * 작업 유형, 박스 ID로 박스 정보 조회
	 * 
	 * @param domainId
	 * @param jobType
	 * @param boxId
	 * @param exceptionWhenEmpty
	 * @return
	 */
	public static BoxResult findByBoxIdJobType(Long domainId, String jobType, String boxId, boolean exceptionWhenEmpty) {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		sql.add("SELECT * FROM TB_IF_BOX_RESULT")
		   .add("WHERE domain_id = :domainId AND BOX_ID = :boxId")
		   .add("	AND batch_id in (SELECT ID FROM TB_JOB_BATCH WHERE DOMAIN_ID = :domainId AND STATUS = 'RUN' AND JOB_TYPE = :jobType)");
		
		Map<String, Object> params = ValueUtil.newMap("domainId,jobType,boxId", domainId, jobType.toUpperCase(), boxId);
		return BeanUtil.get(IQueryManager.class).selectBySql(sql.toString(), params, BoxResult.class);
	}

	/**
	 * fieldName, fieldValue으로 BoxResult 조회
	 *
	 * @param domainId
	 * @param exceptionWhenEmpty
	 * @param selectFields
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public static BoxResult findBy(Long domainId, boolean exceptionWhenEmpty, String selectFields, String fieldNames, Object ... fieldValues) {
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);

		if(ValueUtil.isNotEmpty(selectFields)) {
			condition.addSelect(selectFields.split(SysConstants.COMMA));
		}

 		String[] keyArr = fieldNames.split(SysConstants.COMMA);

		if (keyArr.length != fieldValues.length) {
			throw new IllegalArgumentException("keys count and values count mismatch!");
		}

		for (int i = 0; i < keyArr.length; i++) {
			condition.addFilter(keyArr[i], fieldValues[i]);
		}
		
		BoxResult box = BeanUtil.get(IQueryManager.class).selectByCondition(BoxResult.class, condition);
		
		if(box == null && exceptionWhenEmpty) {
			throw new ElidomValidationException("박스를 찾을 수 없습니다.");
		}

		return box;
	}
	
	/**
	 * 중량 검수 시에 사용하기 위한 박스 중량 범위 
	 * 
	 * @return
	 */
	public List<Float> boxTypeWeightRanges() {
		return ValueUtil.toList(this.getBoxWtMin() == null ? 0f : this.getBoxWtMin(),this.getBoxWt() == null ? 0f : this.getBoxWt(), this.getBoxWtMax() == null ? 0f : this.getBoxWtMax());
	}
}
