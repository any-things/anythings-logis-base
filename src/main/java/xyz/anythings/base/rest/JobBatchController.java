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

import xyz.anythings.base.entity.Area;
import xyz.anythings.base.entity.BatchReceipt;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Stage;
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
import xyz.elidom.util.BeanUtil;
import xyz.elidom.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/job_batches")
@ServiceDesc(description = "JobBatch Service API")
public class JobBatchController extends AbstractRestService {

	/**
	 * 서비스 파인더
	 */
	@Autowired
	private LogisServiceFinder logisServiceFinder;
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
	@RequestMapping(value = "/running_batches/{stage_cd}/{equip_type}/{equip_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
		
		Area area = BeanUtil.get(AreaController.class).findOne(areaCd);
		areaCd = area.getAreaCd();
		
		Stage stage = BeanUtil.get(StageController.class).findOne(stageCd);
		stageCd = stage.getStageCd();
		
		return this.logisServiceFinder.getReceiveBatchService().readyToReceive(Domain.currentDomainId(), areaCd, stageCd, comCd, jobDate);
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

		return this.logisServiceFinder.getReceiveBatchService().startToReceive(summary);
	}
	
	/**
	 * 작업 지시 팝업 시 팝업 화면에 표시를 위해 호출하는 데이터
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{id}/instruction_data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Select batch instruction data")
	public Map<String, Object> searchInstructionData(@PathVariable("id") String batchId) {
		
		// 1. 작업 배치 조회 
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, batchId);
		// 2. 작업 지시 데이터 조회
		return this.logisServiceFinder.getInstructionService(batch).searchInstructionData(batch);
	}
	
	/**
	 * 작업 지시 처리
	 * 
	 * @param batchId
	 * @param equipList
	 * @return
	 */
	@RequestMapping(value = "/{id}/instruct/batch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructBatch(
			@PathVariable("id") String batchId, 
			@RequestBody(required = false) List<?> equipList) {
		
		// 1. 작업 배치 조회
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, batchId);
		// 2. 작업 지시 
		int createdCount = this.logisServiceFinder.getInstructionService(batch).instructBatch(batch, equipList);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, createdCount);
	}
	
	/**
	 * 작업 지시 여러 건 처리 
	 * 
	 * @param batchList
	 * @return
	 */
	@RequestMapping(value = "/instruct/batches", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructBatches(@RequestBody(required = true) List<JobBatch> batchList) {
		// 1. 작업 지시 처리
		for(JobBatch batch : batchList) {
			this.instructBatch(batch.getId(), null);
		}
		
		// 2. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, batchList.size());
	}
	
	/**
	 * 토탈 피킹 지시 처리
	 * 
	 * @param batchId
	 * @param equipList
	 * @return
	 */
	@RequestMapping(value = "/{id}/instruct/total_picking", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructTotalPicking(
			@PathVariable("id") String batchId, 
			@RequestBody(required = false) List<?> equipList) {
		
		// 1. 작업 배치 조회
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, batchId);
		// 2. 작업 지시 
		int count = this.logisServiceFinder.getInstructionService(batch).instructTotalpicking(batch, equipList);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);
	}
	
	/**
	 * 작업 지시 여러 건 처리 
	 * 
	 * @param batchList
	 * @return
	 */
	@RequestMapping(value = "/instruct/total_pickings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> instructTotalPickings(@RequestBody(required = true) List<JobBatch> batchList) {
		// 1. 작업 지시 처리
		for(JobBatch batch : batchList) {
			this.instructTotalPicking(batch.getId(), null);
		}
		
		// 2. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, batchList.size());
	}
	
	/**
	 * 작업 병합
	 * 
	 * @param sourceBatchId
	 * @param mainBatchId
	 * @return
	 */
	@RequestMapping(value = "/{source_batch_id}/instruct/merge_batch/{main_batch_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Merge batch")
	public Map<String, Object> mergeBatch(
			@PathVariable("source_batch_id") String sourceBatchId,
			@PathVariable("main_batch_id") String mainBatchId) {
		
		// 1. 병합할 메인 배치 정보 조회 
		JobBatch mainBatch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, mainBatchId);	
		// 2. 병합될 배치 정보 조회 
		JobBatch sourceBatch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, sourceBatchId);	
		// 3. 작업 배치 병합
		int mergedCnt = this.logisServiceFinder.getInstructionService(mainBatch).mergeBatch(mainBatch, sourceBatch);
		// 4. 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, mergedCnt);
	}
	
	/**
	 * 작업 지시 취소 
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "/{id}/instruct/cancel", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Cancel batch instruction")
	public Map<String, Object> cancelInstructionBatch(@PathVariable("id") String batchId) {
		
		// 1. 작업 배치 조회 
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, batchId);
		// 2. 작업 지시 취소
		int count = this.logisServiceFinder.getInstructionService(batch).cancelInstructionBatch(batch);
		// 3. 작업 지시 결과 리턴
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);		
	}
	
	/**
	 * 배치 마감
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/close_batch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Close batch")
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
	@ApiDesc(description = "Close batch forcibly")
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
	@ApiDesc(description = "Cancel received batch")
	public Map<String, Object> cancelBatch(@PathVariable("id") String id) {
		
		// 1. JobBatch 조회 
		JobBatch batch = LogisEntityUtil.findEntityByIdWithLock(true, JobBatch.class, id);
		// 2. 작업 배치 마감
		int count = this.logisServiceFinder.getReceiveBatchService().cancelBatch(batch);
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
	@ApiDesc(description = "Complete batch by batch group id")
	public Map<String, Object> closeBatchesByGroup(
			@PathVariable("batch_group_id") String batchGroupId, 
			@RequestParam(name = "forcibly", required = false) boolean forcibly) {
		
		int count = this.batchService.closeBatchGroup(Domain.currentDomainId(), batchGroupId, forcibly);
		return ValueUtil.newMap("result,msg,count", SysConstants.OK_STRING, SysConstants.OK_STRING, count);
	}

}