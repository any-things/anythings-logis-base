package xyz.anythings.base.rest;

import java.util.List;
import java.util.Map;

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

import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.service.api.IBatchService;
import xyz.anythings.base.service.impl.LogisServiceFinder;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/job_batches")
@ServiceDesc(description = "JobBatch Service API")
public class JobBatchController extends AbstractRestService {

	/**
	 * 물류 서비스 파인더
	 */
	@Autowired
	protected LogisServiceFinder logisServiceFinder;
	/**
	 * 작업 배치 서비스
	 */
	@Autowired
	private IBatchService batchService;
	
	@Override
	protected Class<?> entityClass() {
		return JobBatch.class;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search (Pagination) By Search Conditions")
	public Page<?> index(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "select", required = false) String select,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "query", required = false) String query) {
		return this.search(this.entityClass(), page, limit, select, sort, query);
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
	 * 스테이지 별 작업 일자 별 작업 전체 진행 상황 
	 * 
	 * @param stageCd
	 * @param jobDate
	 * @return
	 */
	@RequestMapping(value = "/daily_progress_rate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Daily progress rate")
	public BatchProgressRate dailyProgressRate(
			@RequestParam(name = "stage_cd", required = true) String stageCd, 
			@RequestParam(name = "job_date", required = true) String jobDate) {
		
		return this.batchService.dailyProgressRate(Domain.currentDomainId(), stageCd, jobDate);
	}
	
	/**
	 * 작업 배치 별 진행 상황
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/progress_rate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Progress rate of job batch")
	public BatchProgressRate batchProgressRate(@RequestParam(name = "id", required = true) String id) {
		
		JobBatch batch = LogisEntityUtil.findEntityById(true, JobBatch.class, id);
		return this.batchService.batchProgressRate(batch);
	}
	
	/**
	 * 설비에서 진행 중인 작업 배치 조회
	 * 
	 * @param stageCd
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/running_batch/{stage_cd}/{equip_type}/{equip_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find running batch of job batch")
	public JobBatch findRunningBatch(
			@RequestParam(name = "stage_cd", required = true) String stageCd, 
			@RequestParam(name = "equip_type", required = true) String equipType,
			@RequestParam(name = "equip_cd", required = true) String equipCd) {
		
		return this.batchService.findRunningBatch(Domain.currentDomainId(), stageCd, equipType, equipCd);
	}
	
	/**
	 * 설비에서 진행 중인 메인 작업 배치 리스트 조회
	 * 
	 * @param stageCd
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/running_batch/{stage_cd}/{equip_type}/{equip_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find running batch of job batch")
	public List<JobBatch> findRunningMainBatches(
			@RequestParam(name = "stage_cd", required = true) String stageCd, 
			@RequestParam(name = "equip_type", required = true) String equipType,
			@RequestParam(name = "equip_cd", required = true) String equipCd) {
		
		return this.batchService.searchRunningMainBatchList(Domain.currentDomainId(), stageCd, equipType, equipCd);
	}
	
	/**
	 * 설비에서 진행 중인 작업 배치 조회
	 * 
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	@RequestMapping(value = "/running_batches/{stage_cd}/{job_type}/{job_date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search running batches of job batch")
	public List<JobBatch> searchRunningBatchList(
			@RequestParam(name = "stage_cd", required = true) String stageCd, 
			@RequestParam(name = "job_type", required = true) String jobType,
			@RequestParam(name = "job_date", required = true) String jobDate) {
		
		return this.batchService.searchRunningBatchList(Domain.currentDomainId(), stageCd, jobType, jobDate);
	}
	
	/**
	 * 주문 수신 준비
	 * 
	 * @param areaCd
	 * @param stageCd
	 * @param comCd
	 * @param jobDate
	 * @return
	 */
	@RequestMapping(value = "/receive_batches/ready/{area_cd}/{stage_cd}/{com_cd}/{job_date}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Ready to receive batch orders")
	public BatchReceipt readyToReceiveOrders(
			@PathVariable("area_cd") String areaCd,
			@PathVariable("stage_cd") String stageCd,
			@PathVariable("com_cd") String comCd, 
			@PathVariable("job_date") String jobDate) {
		
		return this.batchService.readyToReceive(Domain.currentDomainId(), areaCd, stageCd, comCd, jobDate);
	}
	
	/**
	 * 배치 수신 시작
	 * 
	 * @param summary
	 * @return
	 */
	@RequestMapping(value = "/receive_batches/start", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Start to receive batch orders")
	public BatchReceipt startReceivingOrders(@RequestBody BatchReceipt summary) {

		return this.batchService.startToReceive(summary);
	}
	
	/**
	 * 작업 병합
	 * 
	 * @param sourceBatchId
	 * @param mainBatchId
	 * @return
	 */
	@RequestMapping(value = "/merge_batch/{source_batch_id}/{main_batch_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Merge batch")
	public Map<String, Object> mergeBatch(
			@PathVariable("source_batch_id") String sourceBatchId,
			@PathVariable("main_batch_id") String mainBatchId) {
		
		// 1. 병합할 메인 배치 정보 조회 
		// JobBatch mainBatch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, mainBatchId);	
		// 2. 병합될 배치 정보 조회 
		// JobBatch sourceBatch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, sourceBatchId);	
		// 3. 작업 배치 병합
		int mergedCnt = 0; //this.instructionService.mergeBatch(mainBatch, sourceBatch);
		// 4. 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, mergedCnt);
	}
	
	/**
	 * 배치 마감
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/close_batch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Close job batch")
	public Map<String, Object> closeBatch(@RequestParam(name = "id", required = true) String id) {

		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, id);
		int count = this.batchService.closeBatch(batch, false);
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);
	}
	
	/**
	 * 강제 작업 배치 마감 
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/close_batch_forcibly", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Close job batch Forcibly")
	public Map<String, Object> closeBatchForcibly(@PathVariable("id") String id) {
		
		// 1. JobBatch 조회 
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, id);
		// 2. 작업 배치 마감
		int count = this.batchService.closeBatch(batch, true);
		// 3. 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);
	}
	
	/**
	 * 작업 배치 수신 취소 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{id}/cancel_batch", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Cancel Received Batch")
	public Map<String, Object> cancelBatch(@PathVariable("id") String id) {
		
		// 1. JobBatch 조회 
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, id);
		// 2. 작업 배치 마감
		int count = this.batchService.cancelBatch(batch);
		// 3. 작업 배치 수신 취소
		return ValueUtil.newMap("result,cancel_count", SysConstants.OK_STRING, count);
	}
	
	/**
	 * 배치 그룹 ID로 배치 작업을 모두 찾아 배치 마감
	 * 
	 * @param batchGroupId
	 * @param forcibly
	 * @return
	 */
	@RequestMapping(value = "/{batch_group_id}/close_batches/by_group", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Complete JobBatch Group")
	public Map<String, Object> closeBatchesByGroup(
			@PathVariable("batch_group_id") String batchGroupId, 
			@RequestParam(name = "forcibly", required = false) boolean forcibly) {
		
		int count = this.batchService.closeBatchGroup(Domain.currentDomainId(), batchGroupId, forcibly);
		return ValueUtil.newMap("result,msg,count", SysConstants.OK_STRING, SysConstants.OK_STRING, count);
	}

}