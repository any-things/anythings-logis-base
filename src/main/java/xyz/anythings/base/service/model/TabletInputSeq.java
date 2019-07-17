package xyz.anythings.base.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.ValueUtil;

/**
 * 태블릿 하단에 표시되는 투입 정보 모델 
 * 
 * @author shortstop
 */
public class TabletInputSeq {
	/**
	 * JobInputSeq id
	 */
	private String id;
	/**
	 * Batch ID
	 */
	private String batchId;	
	/**
	 * Job ID
	 */
	private String jobId;
	/**
	 * 투입 순서 
	 */
	private Integer inputSeq;
	/**
	 * 고객사 코드
	 */
	private String comCd;
	/**
	 * 상품 코드 - DAS, RTN ONLY
	 */
	private String skuCd;
	/**
	 * 상품 명 - DAS, RTN ONLY 
	 */
	private String skuNm;
	/**
	 * 버킷 코드 - DPS ONLY
	 */
	private String bucketCd;
	/**
	 * 표시기 색상 
	 */
	private String mpiColor;
	/**
	 * 상태 
	 */
	private String status;
	/**
	 * 작업자의 존과 관련이 있는지 (즉 투입 정보가 작업자의 존에 피킹 작업이 있는지) 여부
	 */
	private Boolean hasMyJobs;
	/**
	 * 작업자가 처리하면 작업이 완료되는지 여부
	 */
	private Boolean isMyZoneIsLast;
	/**
	 * 투입 작업 진행율
	 */
	private Integer progressRate;
	
	/**
	 * 장비 존에 해당하는 투입 작업 진행율
	 */
	private Integer myZoneProgressRate;
	
	/**
	 * 해당 탭의 주문 아이디 
	 */
	private String orderId;
	
	public TabletInputSeq() {
	}
	
	@SuppressWarnings("rawtypes")
	public TabletInputSeq(Map input) {
		this.setId(AnyValueUtil.parseString(input, "id"));
		this.setBatchId(AnyValueUtil.parseString(input, "batch_id"));
		this.setJobId(AnyValueUtil.parseString(input, "job_id"));
		this.setStatus(AnyValueUtil.parseString(input, "status"));
		this.setBucketCd(AnyValueUtil.parseString(input, "bucket_cd"));
		this.setComCd(AnyValueUtil.parseString(input, "com_cd"));
		this.setSkuCd(AnyValueUtil.parseString(input, "sku_cd"));
		this.setSkuNm(AnyValueUtil.parseString(input, "sku_nm"));
		this.setInputSeq(AnyValueUtil.parseInteger(input, "input_seq"));
		this.setMpiColor(AnyValueUtil.parseString(input, "mpi_color"));
		this.setHasMyJobs(ValueUtil.isEqualIgnoreCase(SysConstants.Y_STRING, AnyValueUtil.parseString(input, "rel_flag")));
		this.setIsMyZoneIsLast(ValueUtil.isEqualIgnoreCase(SysConstants.Y_STRING, AnyValueUtil.parseString(input, "my_flag")));
		this.setProgressRate(AnyValueUtil.parseInteger(input, "proc_rate"));
		this.setMyZoneProgressRate(AnyValueUtil.parseInteger(input, "my_proc_rate"));
		this.setOrderId(AnyValueUtil.parseString(input, "order_id"));
	}
	
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
	
	public Integer getInputSeq() {
		return inputSeq;
	}
	
	public void setInputSeq(Integer inputSeq) {
		this.inputSeq = inputSeq;
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
	
	public String getBucketCd() {
		return bucketCd;
	}
	
	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}
	
	public String getMpiColor() {
		return mpiColor;
	}
	
	public void setMpiColor(String mpiColor) {
		this.mpiColor = mpiColor;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Boolean getHasMyJobs() {
		return hasMyJobs;
	}

	public void setHasMyJobs(Boolean hasMyJobs) {
		this.hasMyJobs = hasMyJobs;
	}

	public Boolean getIsMyZoneIsLast() {
		return isMyZoneIsLast;
	}

	public void setIsMyZoneIsLast(Boolean isMyZoneIsLast) {
		this.isMyZoneIsLast = isMyZoneIsLast;
	}

	public Integer getProgressRate() {
		return progressRate;
	}
	
	public void setProgressRate(Integer progressRate) {
		this.progressRate = progressRate;
	}

	public Integer getMyZoneProgressRate() {
		return myZoneProgressRate;
	}

	public void setMyZoneProgressRate(Integer myZoneProgressRate) {
		this.myZoneProgressRate = myZoneProgressRate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * 태블릿 투입 리스트 조회 
	 * 
	 * @param inputList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<TabletInputSeq> toTabletInputList(List<Map> inputList) {
		List<TabletInputSeq> inputSeqList = new ArrayList<TabletInputSeq>(inputList.size());
		
		for(Map input : inputList) {
			inputSeqList.add(new TabletInputSeq(input));
		}

		return inputSeqList;
	}	
	
	/**
	 * 프로시져 결과 파싱 
	 * 
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<TabletInputSeq> parseTableInputResult(Map<?, ?> result) {
		// 1. 결과 코드 추출 
		Integer resultCode = ValueUtil.toInteger(result.get("P_OUT_RESULT"));
		
		// 2. 조회 결과 없음
		if(resultCode == 0 || resultCode == -2) {
			return null;
		
		// 3. 조회 결과 있음
		} else if(resultCode > 0) {
			// 결과 리스트 추출
			List<Map> inputList = (List<Map>)result.get("P_OUT_INPUT_LIST");
			// 결과 변환 
			return TabletInputSeq.toTabletInputList(inputList);
			
		// 4. 에러 
		} else {
			if(resultCode == -1) {
				throw ThrowUtil.newValidationErrorWithNoLog("호기에 매핑된 작업 배치가 없습니다 : " + result.get("P_OUT_MSG"));
			}
			
			return null;
		}
	}
}
