package xyz.anythings.base.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import xyz.anythings.base.LogisBaseConfigConstants;
import xyz.anythings.base.LogisBaseConstants;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.ReceiptSummary;
import xyz.anythings.base.entity.ReceiptSummaryItem;
import xyz.anythings.base.entity.Region;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.service.api.IReceiveOrderService;
import xyz.anythings.base.service.impl.LogisServiceFinder;
import xyz.anythings.sys.AnyConstants;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.dbist.dml.Filter;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.exception.server.ElidomRuntimeException;
import xyz.elidom.exception.server.ElidomValidationException;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.sys.util.SettingUtil;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.DateUtil;
import xyz.elidom.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/job_batch")
@ServiceDesc(description = "JobBatch Service API")
public class JobBatchController extends AbstractRestService {
	
	/**
	 * 작업 배치 서비스
	 */
	@Autowired
	private LogisServiceFinder logisServiceFinder;

	@Override
	protected Class<?> entityClass() {
		return JobBatch.class;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search (Pagination) By Search Conditions")
	public Page<?> index(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "select", required = false) String select,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "query", required = false) String query) {
		
		Page<?> resultPage = this.search(this.entityClass(), page, limit, select, sort, query);
		List<JobBatch> resultList = (List<JobBatch>)resultPage.getList();
		
		for(JobBatch batch : resultList) {
			// DPS, QPS 일때 할당 호기를 검색 한다. 
			if(batch.isDpsBatch() || batch.isQpsBatch()) {
				batch.setJobRegionInfo();
			}
			
			// 작업 배치에 대한 진행율을 조회 한다. 
			if(ValueUtil.isEqualIgnoreCase(batch.getStatus(), JobBatch.STATUS_RUNNING) || 
			   ValueUtil.isEqualIgnoreCase(batch.getStatus(), JobBatch.STATUS_END)) {
				batch.selectBatchProgress();
			}
		}
		
		return resultPage;
	}
	
	@ApiDesc(description = "Search For Middle Classing")
	@RequestMapping(value = "/search/middle_class", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<?> searchForMiddleClassing(
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "select", required = false) String select,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "query", required = false) String query) {
		
		Filter[] filters = ValueUtil.isEmpty(query) ? null : this.jsonParser.parse(query, Filter[].class);
		String jobDate = null;
		
		if(filters != null && filters.length > 0) {
			for(Filter filter : filters) {
				if(ValueUtil.isEqualIgnoreCase("job_date", filter.getName())) {
					jobDate = ValueUtil.toString(filter.getValue());
					break;
				}
			}
		}
		
		if(ValueUtil.isEmpty(jobDate)) {
			throw new ElidomValidationException("작업일자를 선택하세요.");
		}

		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
		sql.add("SELECT")
		   .add(" 	dc_cd, com_cd, job_date, job_id, batch_group_id, job_batch_seq")
		   .add("FROM")
		   .add("	TB_JOB_BATCH")
		   .add("WHERE")
		   .add("  	domain_id = :domainId")
		   .add("  	and job_date = :jobDate")
		   .add("  	#if($statuses)")
		   .add("  	and status in (:statuses)")
		   .add("  	#end")
		   .add("GROUP BY")
		   .add("  	dc_cd, com_cd, job_date, job_id, batch_group_id, job_batch_seq")
		   .add("ORDER BY")
		   .add("  	dc_cd, com_cd, job_date, job_id desc");
		
		List<String> statuses = ValueUtil.newStringList(JobBatch.STATUS_WAIT, JobBatch.STATUS_READY, JobBatch.STATUS_RUNNING);
		Map<String, Object> params = ValueUtil.newMap("domainId,jobDate,statuses", Domain.currentDomainId(), jobDate, statuses);
		return this.queryManager.selectPageBySql(sql.toString(), params, JobBatch.class, 0, 0);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find one by ID")
	public JobBatch findOne(@PathVariable("id") String id) {
		return this.getOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/{id}/exist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Check exists By ID")
	public Boolean isExist(@PathVariable("id") String id) {
		return this.isExistOne(this.entityClass(), id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiDesc(description = "Create")
	public JobBatch create(@RequestBody JobBatch input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public JobBatch update(@PathVariable("id") String id, @RequestBody JobBatch input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<JobBatch> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}
	
	/**
	 * 작업 지시 전 가상호기 여부 확인
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batchId}/check_region", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Job Instruct Virtual Region Check")
	public Region checkInstructRegion(@PathVariable("batchId") String batchId) {
		Long domainId = Domain.currentDomainId();
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		return !batch.isRtn3Batch() ? Region.findByRegionCd(domainId, batch.getRegionCd(), true) : null;
	}
	
	/**
	 * 작업 지시 팝업 시 팝업 화면에 표시를 위해 호출하는 데이터
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batchId}/preprocess", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Select batch Preprocess All Data")
	public Map<String, Object> searchPreprocess(@PathVariable("batchId") String batchId) {
		// 1. JobBatch 조회 
		JobBatch batch = JobBatch.find(Domain.currentDomainId(), batchId, true);
		// 2. 작업 지시 데이터 조회
		return this.logisServiceFinder.getInstructionService(batch).searchInstructionData(batch);
	}
	
	/**
	 * 호기 복사 데이터 조회 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batchId}/clone_region", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Select Region List For Clone Region")
	public Map<String, Object> searchRegionListForClone(@PathVariable("batchId") String batchId) {
		Long domainId = Domain.currentDomainId();
		
		// 1. 배지 조회 
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		if(batch.isDpsBatch() || batch.isQpsBatch()) {
			throw new ElidomValidationException("B2C 작업은 호기복사를 지원하지 않습니다.");
		}
		// 2. 호기 조회 
		Region region = Region.findByRegionCd(domainId, batch.getRegionCd(), true);
		// 3. 호기 복사 데이터 조회
		return this.logisServiceFinder.getInstructionService(batch).searchRegionListForClone(batch, region);
	}
	
	/**
	 * 작업 일자 별 작업 전체 진행 상황 
	 * 
	 * @param jobDate
	 * @return
	 */
	@RequestMapping(value = "/progress_rate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Progress rate")
	public Map<String, Object> dailyProgressRate(@RequestParam(name = "job_date", required = false) String jobDate) {
		// 1. 작업 일자 체크 - 야간 작업시 12시가 넘어가면 다음날로 넘어가서 데이터가 나오지 않으므로 jobDate로 조회해서 데이터가 없으면 하루를 뺀 날짜로 조회
		String sql = "SELECT ID FROM TB_JOB_BATCH WHERE DOMAIN_ID = :domainId AND JOB_DATE = :jobDate";
		int count = this.queryManager.selectSizeBySql(sql, ValueUtil.newMap("domainId,jobDate", Domain.currentDomainId(), jobDate));
		if(count == 0) {
			Date date = DateUtil.parse(jobDate, "yyyy-MM-dd");
			jobDate = DateUtil.addDateToStr(date, -1);
		}
		
		// 2. 투입 리스트 조건 생성
		Map<String, Object> params = ValueUtil.newMap("P_IN_DOMAIN_ID,P_IN_JOB_DATE", Domain.currentDomainId(), jobDate);
		// 3. 프로시져 콜 
		Map<?, ?> progress = this.queryManager.callReturnProcedure("SP_JOB_PROGRESS", params, Map.class);
		// 4. 최종 결과 리턴 
		Map<String, Object> result = new HashMap<String, Object>(4);
		Float totalPlan = AnyValueUtil.parseFloat(progress, "P_OUT_PCS_PICKING_QTY");
		Float totalActual = AnyValueUtil.parseFloat(progress, "P_OUT_PCS_PICKED_QTY");
		Float totalRate = (totalPlan == 0) ? 0F : (totalActual/ totalPlan) * 100;
		result.put("total", ValueUtil.newMap("rate,plan,actual", totalRate, totalPlan, totalActual));
		result.put("order", ValueUtil.newMap("plan,actual", AnyValueUtil.parseInteger(progress, "P_OUT_ORDER_PICKING_QTY"), AnyValueUtil.parseInteger(progress, "P_OUT_ORDER_PICKED_QTY")));
		result.put("sku", ValueUtil.newMap("plan,actual", AnyValueUtil.parseInteger(progress, "P_OUT_SKU_PICKING_QTY"), AnyValueUtil.parseInteger(progress, "P_OUT_SKU_PICKED_QTY")));
		result.put("pcs", ValueUtil.newMap("plan,actual", AnyValueUtil.parseInteger(progress, "P_OUT_PCS_PICKING_QTY"), AnyValueUtil.parseInteger(progress, "P_OUT_PCS_PICKED_QTY")));
		return result;
	}
	
	/**
	 * 센터, 고객사, 작업 일자 기준으로 주문 수신 (I/F) 
	 * 주문 수신할 내용에 대한 서머리 정보를 리턴한다.
	 * 
	 * @param receiptType order / common
	 * @param dcCd
	 * @param comCd
	 * @param jobDate
	 * @param batchSeqStr
	 * @return
	 */
	@RequestMapping(value = "/receive_orders/ready/{receipt_type}/{dc_cd}/{com_cd}/{job_date}/{batch_seq}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Ready Receive Orders")
	public ReceiptSummary readyReceivingOrders(
			@PathVariable("receipt_type") String receiptType, 
			@PathVariable("dc_cd") String dcCd, 
			@PathVariable("com_cd") String comCd, 
			@PathVariable("job_date") String jobDate,
			@PathVariable("batch_seq") String batchSeqStr) {
		
		// 1. 파라미터 체크
		Long domainId = Domain.currentDomainId();
		
		if(ValueUtil.isEqualIgnoreCase(dcCd, AnyConstants.NOT_AVAILABLE_STRING)) {
			dcCd = null;
		}
		
		if(ValueUtil.isEqualIgnoreCase(comCd, AnyConstants.NOT_AVAILABLE_STRING)) {
			comCd = null;
		}
		
		if(ValueUtil.isEqualIgnoreCase(jobDate, AnyConstants.NOT_AVAILABLE_STRING)) {
			jobDate = null;
		}
		
		Integer batchSeq = null;
		if(!ValueUtil.isEqualIgnoreCase(batchSeqStr, AnyConstants.NOT_AVAILABLE_STRING)) {
			batchSeq = ValueUtil.toInteger(batchSeqStr);
		}
		
		// 2. 주문 수신 유형 
		String receiveType = SettingUtil.getValue(LogisBaseConfigConstants.MPS_RECEIVE_ORDER_TYPE, ReceiptSummary.RECEIPT_MODE_SERVICE);
		ReceiptSummary summary = this.findReceiveOrderService().createReceiptSummary(domainId, receiptType, dcCd, comCd, jobDate, batchSeq);
		summary.updateProgressRate();
		
		// 3. 주문 수신 모드 설정
		if(summary.isOrderReceiptType() && ValueUtil.isEqualIgnoreCase(receiveType, ReceiptSummary.RECEIPT_MODE_PROCEDURE)) {
			summary.setReceiveMode(ReceiptSummary.RECEIPT_MODE_PROCEDURE);
		} else {
			summary.setReceiveMode(ReceiptSummary.RECEIPT_MODE_SERVICE);
		}
		
		// 4. 결과 리턴
		return summary;
	}
	
	/**
	 * 설정값에 따라 주문 수신 서비스 컴포넌트를 찾아 리턴
	 * 
	 * @return
	 */
	private IReceiveOrderService findReceiveOrderService() {
		String receiveType = SettingUtil.getValue(LogisBaseConfigConstants.MPS_RECEIVE_ORDER_TYPE, ReceiptSummary.RECEIPT_MODE_SERVICE);
		return this.logisServiceFinder.getReceiveOrderService(receiveType);
	}
	
	@RequestMapping(value = "/receive_orders/start", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Start Receiving Orders")
	public ReceiptSummary startReceivingOrders(@RequestBody ReceiptSummary summary) {
		// 1. 파라미터 체크 
		Long domainId = Domain.currentDomainId();
		String receiveType = summary.getReceiptType();
		String receiveMode = summary.getReceiveMode();
		IReceiveOrderService orderSvc = this.findReceiveOrderService();
		
		// 2. 주문 수신 처리 - 프로시져 기반의 빠른 주문 수신 처리
		if(ValueUtil.isEqualIgnoreCase(receiveType, ReceiptSummary.RECEIPT_TYPE_ORDER) && 
		   ValueUtil.isEqualIgnoreCase(receiveMode, ReceiptSummary.RECEIPT_MODE_PROCEDURE)) {
			orderSvc.receiveOrdersFastly(summary);
			
		// 3. 주문 수신 처리 - 프로그레스 바 지원하는 팝업을 띄워 비동기로 처리
		} else {
			// 3.1 주문 수신 체크
			orderSvc.checkReceiptSummaryRunnable(summary);
			// 3.2 주문 수신 서머리 상태 변경
			orderSvc.changeStatusReceiptSummary(domainId, summary, LogisBaseConstants.COMMON_STATUS_RUNNING);
			// 3.3 항목 리스트 SKIP Flag 업데이트
			for(ReceiptSummaryItem item : summary.getItems()) {
				if(item.getSkipFlag()) {
					item.setStatus(LogisBaseConstants.COMMON_STATUS_CANCEL);
				}
			}
			
			this.queryManager.updateBatch(summary.getItems(), "status", "skipFlag");	
			orderSvc.receiveOrders(Domain.currentDomain(), summary);
		}
		
		// 4. 결과 리턴
		return summary;
	}
	
	@RequestMapping(value = "/receive_orders/cancel/{receipt_summary_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Cancel Receive Order")
	public Map<String, Object> cancelReceivingOrders(@PathVariable("receipt_summary_id") String receiptSummaryId) {
		// 주문 수신 취소 - ReceiptSummary 상태를 '취소'로 변경한다. 
		ReceiptSummary summary = this.queryManager.select(ReceiptSummary.class, receiptSummaryId);
		
		if(summary == null) {
			throw new ElidomRuntimeException("해당 수신 서머리 정보를 찾을 수 없습니다.");
			
		} else if(ValueUtil.isEqualIgnoreCase(summary.getStatus(), LogisBaseConstants.COMMON_STATUS_ERROR)) {
			return ValueUtil.newMap("success,msg", false, "해당 수신 주문은 오류가 발생해서 이미 중지되었습니다");
			
		} else if(ValueUtil.isEqualIgnoreCase(summary.getStatus(), LogisBaseConstants.COMMON_STATUS_FINISHED)) {
			return ValueUtil.newMap("success,msg", false, "해당 수신 주문은 이미 수신이 완료되었습니다");
			
		} else {
			summary.setStatus(LogisBaseConstants.COMMON_STATUS_CANCEL);
			this.queryManager.update(summary, OrmConstants.ENTITY_FIELD_STATUS);
			return ValueUtil.newMap("success", true);
		}
	}
	
	/**
	 * 주문 수신 진행 상태 정보 조회
	 * 
	 * @param receiptSummaryId
	 * @return
	 */
	@RequestMapping(value = "/receive_orders/rate/{receipt_summary_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Progressing information By Receipt Summary ID")
	public ReceiptSummary progressStatusOfReceiveOrders(@PathVariable("receipt_summary_id") String receiptSummaryId) {
		
		ReceiptSummary summary = this.queryManager.select(ReceiptSummary.class, receiptSummaryId);
		summary.updateProgressRate();
		return summary;
	}
	
	/**
	 * 작업 배치 기준으로 주문 수신 (I/F) 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/receive_order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Receive Order")
	public Map<String, Object> receiveOrder(@PathVariable("batch_id") String batchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. JobId로 작업 배치 조회
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		// 2. 주문 수신
		Object retVal = this.findReceiveOrderService().receiveOrder(batch);
		// 3. DAS, DAS2, DPS2, 반품인 경우 수신 주문 초기화 
		if (batch.isDasBatch() || batch.isDas2Batch() || batch.isRtnBatch() || batch.isDps2Batch()) {
			this.logisServiceFinder.getPreprocessService(batch).generatePreprocess(batch);
		}
		// 4. 결과 리턴 
		return ValueUtil.newMap("result,return", SysConstants.OK_STRING, retVal);
	}
	
	/**
	 * 작업 배치 수신 취소 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/cancel_batch", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Cancel Received Batch")
	public Map<String, Object> cancelBatch(@PathVariable("batch_id") String batchId) {
		Long domainId = Domain.currentDomainId();
		// 1. 작업 배치 조회
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		// 2. 작업 배치 취소
		int cancelCount = this.logisServiceFinder.getBatchService(batch).cancelBatch(batch);
		/*String status = batch.getStatus();
		
		if(ValueUtil.isEqualIgnoreCase(status, JobBatch.STATUS_CANCEL)) {
			throw ThrowUtil.newValidationErrorWithNoLog("이미 취소된 배치입니다.");
			
		} else if(ValueUtil.isEqualIgnoreCase(status, JobBatch.STATUS_END)) {
			throw ThrowUtil.newValidationErrorWithNoLog("이미 종료된 배치입니다.");
			
		} else if(ValueUtil.isEqualIgnoreCase(status, JobBatch.STATUS_RUNNING)) {
			throw ThrowUtil.newValidationErrorWithNoLog("이미 작업 진행 중인 배치는 취소할 수 없습니다.");
			
		} else if(ValueUtil.isEqualIgnoreCase(status, JobBatch.STATUS_READY)) {
			throw ThrowUtil.newValidationErrorWithNoLog("주문 가공이 완료된 배치는 취소할 수 없습니다.");
			
		} else if(ValueUtil.isNotEqual(status, JobBatch.STATUS_RECEIVE) && ValueUtil.isNotEqual(status, JobBatch.STATUS_WAIT)) {
			throw ThrowUtil.newValidationErrorWithNoLog("작업 배치가 취소할 수 있는 상태가 아닙니다.");
		}
		
		// 2. 작업 배치의 배치 그룹과 동일한 배치 그룹의 다른 배치가 존재하는 경우 이미 작업이 진행 중인 배치가 있으므로 취소할 수 없다.
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("batchGroupId", batch.getBatchGroupId());
		int batchGroupCount = this.queryManager.selectSize(JobBatch.class, condition);
		if(batchGroupCount > 1) {
			throw ThrowUtil.newValidationErrorWithNoLog("주문 가공이 완료된 배치는 취소할 수 없습니다.");
		}
		
		// 3. 상태를 CANCEL로 업데이트 
		batch.setStatus(JobBatch.STATUS_CANCEL);
		batch.setJobBatchSeq(0);
		batch.setFinishedAt(new Date());
		this.queryManager.update(batch, "status", "jobBatchSeq", "finishedAt");
		
		// 4. 주문 정보 작업 차수 0으로 업데이트 
		String sql = "UPDATE TB_IF_ORDER SET JOB_BATCH_SEQ = 0 WHERE DOMAIN_ID = :domainId AND BATCH_ID = :batchId";
		Map<String, Object> params = ValueUtil.newMap("domainId,batchId", domainId, batch.getId());
		int cancelCount = this.queryManager.executeBySql(sql, params);*/
		
		// 5. 결과 리턴 
		return ValueUtil.newMap("result", cancelCount > 0 ? AnyConstants.OK_STRING : AnyConstants.NG_STRING);
	}
	
	/**
	 * 작업 지시 처리
	 * 
	 * @param batchId
	 * @param regionList
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/instruct_job", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructJob(
			@PathVariable("batch_id") String batchId, 
			@RequestBody(required = false) List<Region> regionList) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 작업 배치 조회
		JobBatch batch = JobBatch.findWithLock(domainId, batchId, true);
		// 2. 작업 지시 
		int createdCount = this.logisServiceFinder.getInstructionService(batch).instructBatch(batch, regionList);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, createdCount);
	}
	
	/**
	 * 토탈 피킹 지시 처리
	 * 
	 * @param batchId
	 * @param regionList
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/instruct_total_picking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructTotalPicking(
			@PathVariable("batch_id") String batchId, 
			@RequestBody(required = false) List<Region> regionList) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 작업 배치 조회
		JobBatch batch = JobBatch.findByJobId(domainId, batchId, true);
		// 2. 작업 지시 
		this.logisServiceFinder.getInstructionService(batch).instructTotalpicking(batch, regionList);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result", SysConstants.OK_STRING);
	}
	
	/**
	 * B2B 작업 지시 여러 건 처리 
	 * 
	 * @param batchList
	 * @return
	 */
	@RequestMapping(value = "/b2b/instruct_jobs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructJob(@RequestBody(required = false) List<JobBatch> batchList) {
		// 1. 작업 지시 처리
		for(JobBatch b : batchList) {
			JobBatch batch = JobBatch.findWithLock(b.getDomainId(), b.getId(), true);
			this.logisServiceFinder.getInstructionService(batch).instructBatch(batch, null);
		}
		
		// 2. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, batchList.size());
	}
	
	/**
	 * 작업 지시 취소 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/cancel_job_instruction", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> resetJobInstruction(@PathVariable("batch_id") String batchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 작업 배치 조회 
		JobBatch batch = JobBatch.findWithLock(domainId, batchId, true);
		// 2. 작업 지시 취소
		int count = this.logisServiceFinder.getInstructionService(batch).cancelInstructionBatch(batch);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);		
	}
	
	/**
	 * 작업 지시 진행률 조회 - 작업 지시 처리가 오래 걸리므로 진행 상황을 사용자에게 보여주기 위한 API
	 * 
	 * @param batchId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{batch_id}/instruct_job/progress", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Job instruction prgress")
	public List instructJobProgress(@PathVariable("batch_id") String batchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 작업 배치 조회 
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		// 2. 작업 지시 진행율 조회
		return this.logisServiceFinder.getPreprocessService(batch).searchPreprocessList(batch);
	}
	
	/**
	 * 호기 복사 
	 * 
	 * @param batchId
	 * @param regionList
	 * @return
	 */
	@RequestMapping(value = "/{batchId}/clone_region", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Copy Region assignment informations to another Region")
	public Map<String, Object> cloneRegionAssignments(
			@PathVariable("batchId") String batchId, 
			@RequestBody List<Region> regionList) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 메인 배치 조회 
		JobBatch batch = JobBatch.findWithLock(domainId, batchId, true);
		// 2. 호기 복제 처리 
		this.logisServiceFinder.getInstructionService(batch).cloneBatch(batch, regionList);
		// 3. 결과 리턴
		return ValueUtil.newMap("result", SysConstants.OK_STRING);
	}
	
	/**
	 * 작업 배치 병합 
	 * 
	 * @param sourceBatchId
	 * @param mainBatchId
	 * @return
	 */
	@RequestMapping(value = "/{source_batch_id}/{main_batch_id}/merge_batch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Copy Region assignment informations to another Region")
	public Map<String, Object> mergeBatch(
			@PathVariable("source_batch_id") String sourceBatchId,
			@PathVariable("main_batch_id") String mainBatchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 병합할 메인 배치 정보 조회 
		JobBatch mainBatch = JobBatch.findWithLock(domainId, mainBatchId, true);		
		// 2. 병합될 배치 정보 조회 
		JobBatch sourceBatch = JobBatch.find(domainId, sourceBatchId, true);
		// 3. 작업 배치 병합
		int mergeCnt = this.logisServiceFinder.getInstructionService(mainBatch).mergeBatch(mainBatch, sourceBatch);
		// 4. 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, mergeCnt);
	}
	
	/**
	 * 작업 배치 마감 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/complete_batch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Complete JobBatch")
	public Map<String, Object> completeBatch(@PathVariable("batch_id") String batchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. JobBatch 조회 
		JobBatch batch = JobBatch.findWithLock(domainId, batchId, true);
		// 2. 작업 배치 마감
		int count = 0;
		try {
			count = this.logisServiceFinder.getBatchService(batch).closeBatch(batch, false);
		} catch (ElidomValidationException eve) {
			return ValueUtil.newMap("result,msg,count", AnyConstants.NG_STRING, eve.getMessage(), 0); 
		}
		// 3. 결과 리턴
		return ValueUtil.newMap("result,msg,count", AnyConstants.OK_STRING, AnyConstants.OK_STRING, count);
	}
	
	/**
	 * 강제 작업 배치 마감 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/force_complete_batch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Complete JobBatch Forcibly")
	public Map<String, Object> completeJobForcibly(@PathVariable("batch_id") String batchId) {
		
		Long domainId = Domain.currentDomainId();
		// 1. JobBatch 조회 
		JobBatch batch = JobBatch.findWithLock(domainId, batchId, true);
		// 2. 작업 배치 마감
		int count = this.logisServiceFinder.getBatchService(batch).closeBatch(batch, true);
		// 3. 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);
	}

	/**
	 * 작업 배치 그룹 모두 마감 
	 * 
	 * @param batchId
	 * @param forceFlag
	 * @return
	 */
	@RequestMapping(value = "/{batch_id}/complete_batch_group", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Complete JobBatch Group")
	public Map<String, Object> completeBatchGroup(@PathVariable("batch_id") String batchId, @RequestParam(name = "force_flag", required = false) boolean forceFlag) {
		// 1. JobBatch 조회
		Long domainId = Domain.currentDomainId();
		JobBatch batch = JobBatch.find(domainId, batchId, true);
		
		// 2. 모든 배치에 대해서 끝나지 않은 정보가 있는지 체크하여 끝나지 않은 정보가 있으면 NG로 결과 및 메시지 리턴
		if(!forceFlag) {
			String sql = "SELECT REGION_CD FROM (SELECT REGION_CD, COUNT(*) AS COUNT FROM TB_PROCESS WHERE DOMAIN_ID = :domainId AND BATCH_ID IN (SELECT ID FROM TB_JOB_BATCH WHERE DOMAIN_ID = :domainId AND BATCH_GROUP_ID = :batchGroupId) AND STATUS IN ('W', 'I', 'P', 'C') GROUP BY REGION_CD ORDER BY REGION_CD) WHERE COUNT > 0";
			Map<String, Object> params = ValueUtil.newMap("domainId,batchGroupId", domainId, batch.getBatchGroupId());
			List<String> resultList = this.queryManager.selectListBySql(sql, params, String.class, 0, 0);
			
			if(ValueUtil.isNotEmpty(resultList)) {
				String regionCd = resultList.get(0);
				String msg = "[" + regionCd + "] 등 [" + resultList.size() + "]개의 호기에서 작업이 끝나지 않았습니다.";
				return ValueUtil.newMap("result,msg", AnyConstants.NG_STRING, msg);
			}
		}
		
		// 3. 작업 배치의 배치 그룹 ID로 작업 배치 모두 조회
		Query condition = AnyOrmUtil.newConditionForExecution("id");
		condition.addFilter("domainId", domainId);
		condition.addFilter("batchGroupId", batch.getBatchGroupId());
		condition.addFilter("status", JobBatch.STATUS_RUNNING);
		List<JobBatch> batchList = this.queryManager.selectList(JobBatch.class, condition);
		
		if(ValueUtil.isEmpty(batchList)) {
			throw ThrowUtil.newValidationErrorWithNoLog("배치 그룹 [" + batch.getBatchGroupId() + "]내에 마감 처리할 배치가 없습니다.");
		}
		
		IBatchService batchSvc = this.logisServiceFinder.getBatchService(batch);
		// 4. 루프 돌면서 마감되지 않은 배치만 강제 마감 처리
		for(JobBatch b : batchList) {
			JobBatch jb = JobBatch.findWithLock(domainId, b.getId(), true);
			if(ValueUtil.isEqualIgnoreCase(jb.getStatus(), JobBatch.STATUS_RUNNING)) {
				batchSvc.closeBatch(jb, forceFlag);
			}
		}
		
		// 5. 결과 리턴
		return ValueUtil.newMap("result,msg,count", AnyConstants.OK_STRING, AnyConstants.OK_STRING, batchList.size());
	}

}