package xyz.anythings.base.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.BoxItem;
import xyz.anythings.base.entity.BoxPack;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobInput;
import xyz.anythings.base.entity.JobInstance;
import xyz.anythings.base.entity.Rack;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.event.rest.DeviceProcessRestEvent;
import xyz.anythings.base.model.BaseResponse;
import xyz.anythings.base.model.BatchProgressRate;
import xyz.anythings.base.model.Category;
import xyz.anythings.base.query.store.BatchQueryStore;
import xyz.anythings.base.service.util.LogisServiceUtil;
import xyz.anythings.sys.event.EventPublisher;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.IQueryManager;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.util.ValueUtil;
import xyz.elidom.util.BeanUtil;

/**
 * 작업자 디바이스와의 인터페이스 API
 * 
 * @author shortstop
 */
@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/device_process")
@ServiceDesc(description = "Device Process Controller API")
public class DeviceProcessController {

	@Autowired
	BatchQueryStore batchQueryStore;
	
	@Autowired
	IQueryManager queryManager;
	
	/**********************************************************************
	 * 								공통 API  q
	 **********************************************************************/
	/**
	 * 장비 업데이트 하라는 메시지를 장비 타입별로 publish
	 * 
	 * @param deviceType
	 * @return
	 */
	@RequestMapping(value = "/publish/device_update/{device_type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Publish device update message")
	public BaseResponse publishDeviceUpdate(@PathVariable("device_type") String deviceType) {
		// TODO
		return new BaseResponse(true, null);
	}
	
	/**
	 * 디바이스 업데이트 릴리즈 노트를 조회
	 * 
	 * @param deviceType
	 * @return
	 */
	@RequestMapping(value = "/release_notes/{device_type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Release notes of device type")
	public List<Map<String, Object>> releaseNotesOfDevice(@PathVariable("device_type") String deviceType) {
		// TODO 
		List<Map<String, Object>> releaseNotes = new ArrayList<Map<String, Object>>(5);
		releaseNotes.add(ValueUtil.newMap("seq,content", 1, "디자인 테마 변경"));
		releaseNotes.add(ValueUtil.newMap("seq,content", 2, "메뉴 아이콘 변경"));
		releaseNotes.add(ValueUtil.newMap("seq,content", 3, "모든 엔티티에 대해서 단 건 조회, 리스트 조회, 페이지네이션, 마스터 디테일 구조의 디테일 리스트 조회 공통 유틸리티 추가"));
		releaseNotes.add(ValueUtil.newMap("seq,content", 4, "Fixed : 디바이스 업데이트 시 오류 제거"));
		return releaseNotes;
	}

	/**
	 * 장비 타입별로 전달할 메시지를 publish
	 * 
	 * @param deviceType
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/publish/message/{device_type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Publish device update message")
	public BaseResponse publishDeviceMessage(@PathVariable("device_type") String deviceType, @RequestBody String message) {
		// TODO 
		return new BaseResponse(true, null);
	}
	
	/**
	 * 작업 배치의 주문 요약 정보
	 * 
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/batch_progress_rate/{equip_type}/{equip_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Batch Progress Rate")
	public BatchProgressRate batchProgressRate(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd) {
		
		BatchProgressRate rate = new BatchProgressRate();
		
		// Rack 타입 공통 처리 
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			String qry = this.batchQueryStore.getRackBatchProgressRateQuery();
			rate = this.queryManager.selectBySql(qry, ValueUtil.newMap("domainId,rackCd", Domain.currentDomainId(), equipCd), BatchProgressRate.class);
		} else {
			// TODO 다른 설비 추가 필요  
		}
		
		return rate;
	}
	/**
	 * 고객사 코드 및 상품 코드로 상품 조회
	 * 
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	@RequestMapping(value = "/sku/find/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find sku for cliassification")
	public SKU findSkuForClassify(@PathVariable("com_cd") String comCd, @PathVariable("sku_cd") String skuCd) {
		// TODO 
		return null;
	}

	/**
	 * 분류 처리를 위한 설비 유형, 설비 코드 및 상품 코드로 like 검색해서 상품 리스트 조회
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param skuCd
	 * @return
	 */
	@RequestMapping(value = "/sku/search_by_like/{equip_type}/{equip_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search sku for cliassification")
	public List<SKU> searchSkuCandidates(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd, @PathVariable("sku_cd") String skuCd) {
		// TODO 
		return null;
	}
	
	/**
	 * 배치 그룹 ID 내에서 상품 코드로 like 검색해서 상품 리스트 조회
	 * 
	 * @param batchId
	 * @param skuCd
	 * @return
	 */
	@RequestMapping(value = "/sku/search_by_batch/{batch_id}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search SKU List For Middle Classing")
	public List<SKU> searchSkuListByBatch(@PathVariable("batch_id") String batchId, @PathVariable("sku_cd") String skuCd) {
		// TODO
		return null;
	}
	
	/**
	 * 배치 그룹 ID 내에서 상품 코드로 like 검색해서 상품 리스트 조회
	 * 
	 * @param batchGroupId
	 * @param skuCd
	 * @return
	 */
	@RequestMapping(value = "/sku/search_by_batch_group/{batch_group_id}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search SKU List For Middle Classing")
	public List<SKU> searchSkuListByBatchGroup(@PathVariable("batch_group_id") String batchGroupId, @PathVariable("sku_cd") String skuCd) {
		// TODO
		return null;
	}
	
	/**********************************************************************
	 * 								표시기 점/소등 API  
	 **********************************************************************/
	
	/**
	 * 설비 소속 모든 표시기 OFF
	 * 
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/indicators/off/{equip_type}/{equip_cd}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Indicators off all of equipment")
	public BaseResponse indicatorsOffAll(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd) {
		// TODO
		return null;
	}
	
	/**
	 * 장비 존 소속 모든 표시기 OFF
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @return
	 */
	@RequestMapping(value = "/indicators/off/{equip_type}/{equip_cd}/{equip_zone}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Indicators off all of zone")
	public BaseResponse indicatorsOff(
			@PathVariable("equip_type") String equipType, 
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("equip_zone") String equipZone) {
		// TODO
		return null;
	}

	/**
	 * 설비 별로 이전 작업 상태로 표시기 점등 상태 복원
	 * 
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/indicators/restore/{equip_type}/{equip_cd}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Indicators off all of zone")
	public BaseResponse restoreIndicators(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd) {
		// TODO
		return null;
	}

	/**
	 * 투입 시퀀스, 장비 존 별 처리할 작업 / 처리한 작업 표시기 점등  
	 * 
	 * @param jobInputId
	 * @param equipZone
	 * @param todoOrDone
	 * @return
	 */
	@RequestMapping(value = "/indicators/restore/{job_input_id}/{equip_zone}/{mode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Indicators off all of zone")
	public BaseResponse restoreIndicators(
			@PathVariable("job_input_id") String jobInputId, 
			@PathVariable("equip_zone") String equipZone, 
			@PathVariable("mode") String todoOrDone) {
		// TODO
		return null;
	}
	
	/**********************************************************************
	 * 								중분류 API  
	 **********************************************************************/
	
	/**
	 * 중분류 화면에서 배치 그룹 ID 하나를 선택하기 위해 스테이지 내 진행 중인 WMS 배치 ID 리스트를 조회
	 * 스테이지 내에 진행 중인 작업 배치 리스트를 스테이지 / 설비 그룹 / 작업 유형 / WMS 배치 ID로 그루핑하여 조회 
	 * 
	 * @param stageCd
	 * @param jobType
	 * @param jobDate
	 * @return
	 */
	@RequestMapping(value = "/running_batches/{stage_cd}/{job_type}/{job_date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search running batch list of stage")
	public List<JobBatch> searchRunningBatchGroups(
			@PathVariable("stage_cd") String stageCd, 
			@PathVariable("job_type") String jobType, 
			@PathVariable("job_date") String jobDate) {
		
		// TODO 
		return null;
	}

	/**
	 * 중분류 처리
	 * 
	 * @param batchGroupId
	 * @param comCd
	 * @param skuCd
	 * @param weightFlag
	 * @return
	 */
	@RequestMapping(value = "/categorize/{batch_group_id}/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Categorize by batchGroupId, comCd, skuCd")
	public Category categorize(
			@PathVariable("batch_group_id") String batchGroupId,
			@PathVariable("com_cd") String comCd, 
			@PathVariable("sku_cd") String skuCd, 
			@RequestParam(name = "weight", required = false) Boolean weightFlag,
			@RequestParam(name = "var_qty_flag", required = false) Boolean varQtyFlag) {
		
		// TODO 
		return null;
	}
	
	/**
	 * 중분류 처리 - 중량 업데이트 
	 * 
	 * @param batchGroupId
	 * @param skuInfo
	 * @return
	 */
	@RequestMapping(value = "/categorize/{batch_group_id}/apply_weight", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Categorize - apply SKU Weight!")
	public Object updateSkuWeight(@PathVariable("batch_group_id") String batchGroupId, @RequestBody SKU skuInfo) {
		
		// TODO 
		return null;
	}
	
	/**********************************************************************
	 * 								소분류 처리 API  
	 **********************************************************************/
	
	/**
	 * 작업 처리 ID (jobInstanceId)로 소분류 작업 처리 
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param jobProcessId
	 * @return
	 */
	@RequestMapping(value = "/classify/confirm/{equip_type}/{equip_cd}/{job_instance_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Confirm classification")
	public BaseResponse confirmClassification(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("job_instance_id") String jobInstanceId) {
		// TODO
		return null;
	}

	/**
	 * 작업 ID (jobInstanceId)로 소분류 작업 분할 처리
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param jobInstanceId
	 * @param reqQty
	 * @param resQty
	 * @return
	 */
	@RequestMapping(value = "/classify/split/{equip_type}/{equip_cd}/{job_instance_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Confirm classification")
	public BaseResponse splitClassification(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("job_instance_id") String jobInstanceId, 
			@RequestParam(name = "req_qty", required = true) Integer reqQty,
			@RequestParam(name = "res_qty", required = true) Integer resQty) {
		// TODO
		return null;
	}
	
	/**
	 * 작업 ID (jobInstanceId)로 소분류 작업 취소 처리
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param jobInstanceId
	 * @return
	 */
	@RequestMapping(value = "/classify/cancel/{equip_type}/{equip_cd}/{job_instance_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Cancel classification")
	public BaseResponse cancelClassification(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("job_instance_id") String jobInstanceId) {
		// TODO
		return null;
	}	
	
	/**
	 * 소분류 확정 취소
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param jobInstanceId
	 * @return
	 */
	@RequestMapping(value = "/classify/undo/{equip_type}/{equip_cd}/{job_instance_id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Undo classification")
	public BaseResponse undoClassification(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("job_instance_id") String jobInstanceId) {
		// TODO
		return null;
	}
	
	/**
	 * 풀 박스
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param jobInstanceId
	 * @return
	 */
	@RequestMapping(value = "/fullbox/{equip_type}/{equip_cd}/{job_instance_id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Fullbox")
	public BaseResponse fullboxing(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("job_instance_id") String jobInstanceId) {
		// TODO
		return null;
	}
	
	/**
	 * 일괄 풀 박스
	 * 
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	@RequestMapping(value = "/fullbox_all/{equip_type}/{equip_cd}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Batch fullbox")
	public BaseResponse batchFullbox(@PathVariable("equip_type") String equipType,  @PathVariable("equip_cd") String equipCd) {
		// TODO
		return null;
	}
	
	/**
	 * 풀 박스 취소
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param cellCd
	 * @param boxId
	 * @return
	 */
	@RequestMapping(value = "/fullbox/undo/{equip_type}/{equip_cd}/{cell_cd}/{box_id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Undo fullbox")
	public BaseResponse undoFullboxing(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("cell_cd") String cellCd, 
			@PathVariable("box_id") String boxId) {
		// TODO
		return null;
	}
	
	/**********************************************************************
	 * 								작업 데이터 조회 API  
	 **********************************************************************/
	
	/**
	 * 투입 리스트를 조회 (페이지네이션) 
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param page
	 * @param limit
	 * @param status 상태 - F: 완료인 것 만 보기, U: 미완료인 것만 보기 , A : 전체 보기 
	 * @return
	 */
	@RequestMapping(value = "/search/input_list/{equip_type}/{equip_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Input list")
	public Page<JobInput> searchInputList(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "status", required = false) String status) {
		
		Long domainId = Domain.currentDomainId();
		
		Map<String,Object> params = ValueUtil.newMap("domainId,equipType,equipCd", domainId, equipType, equipCd);
		
		// Rack 타입 공통 처리 
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			
			// 1. RACK 조회 
			Rack rack = LogisServiceUtil.checkValidRack(domainId, equipCd);
			String qry = "";
			
			params.put("batchId", rack.getBatchId());
			
			
			// 2. DPS 일때 쿼리 
			if(LogisConstants.isDpsJobType(rack.getJobType())){
				qry = this.batchQueryStore.getRackDpsBatchInputListQuery();
			} else {
				// TODO 다른 job type 쿼리.. 
			}
			
			return this.queryManager.selectPageBySql(qry, params, JobInput.class, page, limit);
		} else {
			// TODO 다른 설비 추가 필요  
		}
		return null;
	}
	
	
	/**
	 * 투입 리스트를 상세 조회 
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param detailId
	 * @return
	 */
	@RequestMapping(value = "/search/input_list/{equip_type}/{equip_cd}/{detail_id}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Input list Details")
	public Page<JobInput> searchInputListDetails(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("detail_id") String detailId) {
		
//		Long domainId = Domain.currentDomainId();
		
//		Map<String,Object> params = ValueUtil.newMap("domainId,equipType,equipCd", domainId, equipType, equipCd);
		
		// Rack 타입 공통 처리 
		if(ValueUtil.isEqualIgnoreCase(LogisConstants.EQUIP_TYPE_RACK, equipType)) {
			
		} else {
			// TODO 다른 설비 추가 필요  
		}
		return null;
	}
	
	/**
	 * 투입 리스트를 조회 (리스트) 
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @return
	 */
	@RequestMapping(value = "/search/input_list/{equip_type}/{equip_cd}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Input list")
	public List<JobInput> searchInputList(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("equip_zone") String equipZone) {
		
		// TODO 
		return null;
	}
	
	/**
	 * 장비 존 별 투입 작업 리스트를 조회 (리스트) 
	 * 
	 * @param jobInputId
	 * @param equipZone
	 * @return
	 */
	@RequestMapping(value = "/search/input_jobs/{job_input_id}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Input Job list")
	public List<JobInstance> searchInputJobs(
			@PathVariable("job_input_id") String jobInputId, 
			@PathVariable("equip_zone") String equipZone) {
		
		// TODO 
		return null;
	}
	
	/**
	 * 상품 코드 스캔으로 상품 투입
	 * 
	 * @param equipType 설비 유형
	 * @param equipCd 설비 코드
	 * @param comCd 고객사 코드
	 * @param skuCd 상품 코드
	 * @param page 현재 페이지
	 * @param limit 페이지 당 레코드 수
	 * @return
	 */
	@RequestMapping(value = "/input/sku/{equip_type}/{equip_cd}/{com_cd}/{sku_cd}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Input SKU")
	public Object inputSKU(
			@PathVariable("equip_type") String equipType, 
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "status", required = false) String status) {
		
		// TODO
		return null;
	}
	
	/**********************************************************************
	 * 								박스 관련 API  
	 **********************************************************************/
	
	/**
	 * 설비에서 분류 처리된 박스 리스트 조회 (페이지네이션)
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/paginate/box_list/{equip_type}/{equip_cd}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Paginate box list")
	public Page<BoxPack> paginateBoxList(
			@PathVariable("equip_type") String equipType, 
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("equip_zone") String equipZone,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		
		// TODO
		return null;
	}
	
	/**
	 * 설비에서 분류 처리된 박스 리스트 조회 (페이지네이션)
	 * 
	 * @param batchId
	 * @param equipZone
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/paginate/box_list/{batch_id}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Paginate box list")
	public Page<BoxPack> paginateBoxList(
			@PathVariable("batch_id") String batchId, 
			@PathVariable("equip_zone") String equipZone, 
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit) {
		
		// TODO
		return null;
	}
	
	/**
	 * 설비에서 분류 처리된 박스 리스트 조회 (리스트)
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @return
	 */
	@RequestMapping(value = "/search/box_list/{equip_type}/{equip_cd}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search box list")
	public List<BoxPack> searchBoxList(
			@PathVariable("equip_type") String equipType, 
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("equip_zone") String equipZone) {
		
		// TODO
		return null;
	}
	
	/**
	 * 설비에서 분류 처리된 박스 리스트 조회 (페이지네이션)
	 * 
	 * @param batchId
	 * @param equipZone
	 * @return
	 */
	@RequestMapping(value = "/search/box_list/{batch_id}/{equip_zone}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search box list")
	public List<BoxPack> searchBoxList(
			@PathVariable("batch_id") String batchId, 
			@PathVariable("equip_zone") String equipZone) {
		
		// TODO
		return null;
	}
	
	/**
	 * 박스 처리 ID로 박스 내품 내역 리스트 조회
	 * 
	 * @param boxPackId
	 * @return
	 */
	@RequestMapping(value = "/search/box_items/{box_pack_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search box items")
	public List<BoxItem> searchBoxItems(@PathVariable("box_pack_id") String boxPackId) {
		
		// TODO
		return null;
	}

	/**
	 * 박스 라벨 재발행
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param boxPackId
	 * @param printerId
	 * @return
	 */
	@RequestMapping(value = "/search/box_list/{equip_type}/{equip_cd}/{box_pack_id}/{printer_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search box list")
	public BaseResponse reprintBoxLabel(
			@PathVariable("equip_type") String equipType, 
			@PathVariable("equip_cd") String equipCd, 
			@PathVariable("equip_zone") String equipZone, 
			@PathVariable("box_pack_id") String boxPackId, 
			@PathVariable("printer_id") String printerId) {
		
		// TODO
		return null;	
	}


	/**
	 * 디바이스 관련 각 모듈에 특화된 REST GET 서비스 
	 * DeviceProcessRestEvent 이벤트를 발생시켜 각 모듈에서 해당 로직 처리 
	 */
	@RequestMapping(value = "/{job_type}/**", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Device Process Rest GET API")
	public BaseResponse deviceProcessRestGetApi(
			final HttpServletRequest request
			, @PathVariable("job_type") String jobType
			, @RequestParam Map<String,Object> paramMap) {
        String finalPath = this.getRequestFinalPath(request);
        DeviceProcessRestEvent event = new DeviceProcessRestEvent(Domain.currentDomainId(), jobType, finalPath, RequestMethod.GET, paramMap);
        return this.restEventPublisher(event);
	}

	/**
	 * 디바이스 관련 각 모듈에 특화된 REST PUT 서비스 
	 * DeviceProcessRestEvent 이벤트를 발생시켜 각 모듈에서 해당 로직 처리 
	 */
	@RequestMapping(value = "/{job_type}/**", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Device Process Rest PUT API")
	public BaseResponse deviceProcessRestPutApi(
			final HttpServletRequest request
			, @PathVariable("job_type") String jobType
			, @RequestParam Map<String,Object> paramMap
			, @RequestBody Map<String,Object> requestBody) {
        String finalPath = this.getRequestFinalPath(request);
        DeviceProcessRestEvent event = new DeviceProcessRestEvent(Domain.currentDomainId(), jobType, finalPath, RequestMethod.PUT, paramMap);
        event.setRequestPutBody(requestBody);
        return this.restEventPublisher(event);
	}

	/**
	 * 디바이스 관련 각 모듈에 특화된 REST POST 서비스 
	 * DeviceProcessRestEvent 이벤트를 발생시켜 각 모듈에서 해당 로직 처리 
	 */
	@RequestMapping(value = "/{job_type}/**", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse deviceProcessRestPostApi(
			final HttpServletRequest request
			, @PathVariable("job_type") String jobType
			, @RequestParam Map<String,Object> paramMap
			, @RequestBody List<Map<String,Object>> requestBody) {
        String finalPath = this.getRequestFinalPath(request);
        DeviceProcessRestEvent event = new DeviceProcessRestEvent(Domain.currentDomainId(), jobType, finalPath, RequestMethod.POST, paramMap);
        event.setRequestPostBody(requestBody);
        return this.restEventPublisher(event);
	}
	
	/**
	 * 디바이스 관련 이벤트 퍼블리셔 
	 * @param event
	 * @return
	 */
	private BaseResponse restEventPublisher(DeviceProcessRestEvent event) {
		BeanUtil.get(EventPublisher.class).publishEvent(event);
		return event.getReturnResult();
	}
	
	/**
	 * path variable request 정리  
	 * @param request
	 * @return
	 */
	private String getRequestFinalPath(HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);
        
        if(finalPath.startsWith("/") == false) finalPath = "/" + finalPath;
        
        return finalPath;
	}
			
}
