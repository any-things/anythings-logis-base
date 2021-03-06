package xyz.anythings.base.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.PDA;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.anythings.sys.util.AnyValueUtil;
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
@RequestMapping("/rest/pdas")
@ServiceDesc(description = "PDA Service API")
public class PDAController extends AbstractRestService {

	@Override
	protected Class<?> entityClass() {
		return PDA.class;
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
	public PDA findOne(@PathVariable("id") String id) {
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
	public PDA create(@RequestBody PDA input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public PDA update(@PathVariable("id") String id, @RequestBody PDA input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<PDA> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}

	@RequestMapping(value = "/update/setting", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update setting")
	public PDA updateSetting(
			HttpServletRequest req, 
			HttpServletResponse res, 
			@RequestParam(name = "equip_type", required = true) String equipType,
			@RequestParam(name = "equip_cd", required = true) String equipCd,
			@RequestParam(name = "station_cd", required = true) String stationCd) {
		
		Long domainId = Domain.currentDomainId();
		String pdaIp = AnyValueUtil.getRemoteIp(req);
		String pdaCd = equipCd;
		
		if(!this.updatablePdaStatus(domainId, equipType, equipCd, stationCd, pdaIp)) {
			return null;
		}
		
		JobBatch batch = AnyEntityUtil.findEntityBy(domainId, false, JobBatch.class, "stage_cd,equip_nm", "equipType,equipCd,status", equipType, equipCd, JobBatch.STATUS_RUNNING);
		PDA pda = AnyEntityUtil.findEntityBy(domainId, false, PDA.class, "id,domain_id,stage_cd,pda_cd,pda_nm,equip_type,equip_cd,station_cd,status", "pdaIp", pdaIp);
		
		String pdaNm = (batch == null) ? pdaCd : batch.getEquipNm();
		if(ValueUtil.isNotEmpty(stationCd)) {
			pdaCd = pdaCd + SysConstants.DASH + stationCd;
			pdaNm = pdaNm + SysConstants.DASH + stationCd;
		}
		
		if(pda == null) {
			pda = new PDA();
		}
		
		if(batch != null) {
			pda.setStageCd(batch.getStageCd());
		}
		
		pda.setPdaCd(pdaCd);
		pda.setPdaNm(pdaNm);
		pda.setPdaIp(pdaIp);
		pda.setEquipType(equipType);
		pda.setEquipCd(equipCd);
		pda.setStationCd(stationCd);
		pda.setStatus(LogisConstants.EQUIP_STATUS_OK);
		this.queryManager.upsert(pda);
		return pda;
	}

	/**
	 * Pda 상태 정보 업데이트 가능 여부 체크
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param equipZone
	 * @param tabletIp
	 * @return
	 */
	private boolean updatablePdaStatus(Long domainId, String equipType, String equipCd, String equipZone, String tabletIp) {
		return !ValueUtil.isEqualIgnoreCase(tabletIp, "127.0.0.1");
	}

}