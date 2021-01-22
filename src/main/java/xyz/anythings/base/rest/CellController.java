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

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.Cell;
import xyz.anythings.base.service.impl.LogisServiceDispatcher;
import xyz.anythings.gw.entity.Gateway;
import xyz.anythings.gw.entity.Indicator;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.anythings.sys.util.AnyValueUtil;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.sys.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/cells")
@ServiceDesc(description = "Cell Service API")
public class CellController extends AbstractRestService {

	/**
	 * 물류 서비스 디스패처
	 */
	@Autowired
	protected LogisServiceDispatcher serviceDispatcher;
	
	@Override
	protected Class<?> entityClass() {
		return Cell.class;
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
	public Cell findOne(@PathVariable("id") String id) {
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
	public Cell create(@RequestBody Cell input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public Cell update(@PathVariable("id") String id, @RequestBody Cell input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<Cell> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}
	
	@RequestMapping(value = "/change_indicator/{from_ind_cd}/{to_ind_cd}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Change Indicator")
	public Map<String, Object> changeIndicator(@PathVariable("from_ind_cd") String fromIndCd, @PathVariable("to_ind_cd") String toIndCd) {
		// 1. 도메인 ID 추출
		Long domainId = Domain.currentDomainId();
		
		// 2. To 표시기 코드 Validation 체크
		if(!AnyValueUtil.checkValidateByRegExpr("^[0-9]{6}$", toIndCd)) {
			return ValueUtil.newMap("success,result,msg", false, LogisConstants.NG_STRING, "지시기 코드 [" + toIndCd + "]가 유효하지 않습니다.");
		}
		
		// 3. 이전 표시기 조회
		Indicator fromIndicator = AnyEntityUtil.findEntityBy(domainId, false, Indicator.class, "*", "indCd", fromIndCd);
		if(fromIndicator == null) {
			return ValueUtil.newMap("success,result,msg", false, LogisConstants.NG_STRING, "지시기 [" + fromIndCd + "]가 존재하지 않습니다.");
		}
		
		// 4. 이전 표시기 코드로 셀 조회
		Cell cell = AnyEntityUtil.findEntityBy(domainId, false, Cell.class, "*", "indCd", fromIndCd);
		if(cell == null) {
			return ValueUtil.newMap("success,result,msg", false, LogisConstants.NG_STRING, "지시기에 로케이션이 매핑되어 있지 않아서 셀을 찾을 수 없습니다.");
		}
		
		// 5. 표시기의 게이트웨이 조회
		Gateway gw = AnyEntityUtil.findEntityBy(domainId, false, Gateway.class, "*", "gwCd", fromIndicator.getGwCd());
		
		// 6. 표시기 교체 API 호출
		this.serviceDispatcher.getIndicationService("DAS").changeIndicator(domainId, gw.getStageCd(), gw.getGwNm(), fromIndCd, toIndCd);

		// 7. 결과 리턴 
		return ValueUtil.newMap("success,result", true, SysConstants.OK_STRING);
	}

}