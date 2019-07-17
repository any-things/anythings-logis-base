package xyz.anythings.base.rest;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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

import xyz.anythings.base.entity.Center;
import xyz.elidom.core.entity.Code;
import xyz.elidom.dbist.dml.Filter;
import xyz.elidom.dbist.dml.Order;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.OrmConstants;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysConfigConstants;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.sys.util.SettingUtil;
import xyz.elidom.sys.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/center")
@ServiceDesc(description = "Center Service API")
public class CenterController extends AbstractRestService {

	@Override
	protected Class<?> entityClass() {
		return Center.class;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search (Pagination) By Search Conditions")
	public Page<?> index(
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "limit", required = false) Integer limit,
			@RequestParam(name = "select", required = false) String select,
			@RequestParam(name = "sort", required = false) String sort,
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "belongs_to_domain", required = false) Boolean belongsToDomain) {

		// 1. 도메인 소속된 센터에 대한 표시를 하지 않도록 요청한 경우
		if(belongsToDomain == null || !belongsToDomain) {
			// 1.1 아래 특수 조건인 경우는 클라이언트 resource_code 컴포넌트로 인식하여 해당 도메인 소속 정보만을 보여준다.
			if(limit != null && limit == 10000 && select != null & ValueUtil.isEqual(select, "name,description")) {
				return this.domainCenters();
			// 1.2 일반 검색
			} else {
				return this.search(this.entityClass(), page, limit, select, sort, query);
			}
		// 2. 도메인 소속된 센터에 대한 표시를 하도록 요청한 경우 - Join Query
		} else {
			Filter[] filters = ValueUtil.isEmpty(query) ? null : this.jsonParser.parse(query, Filter[].class);
			String siteOnlyVal = this.parseDomainOnlyValue(filters);
			if(siteOnlyVal == null) siteOnlyVal = "all";
			return this.searchByPagination(siteOnlyVal, page, limit, select, sort, filters);
		}
	}
	
	/**
	 * 도메인 소속 센터 검색 조건인지 여부 리턴
	 * 
	 * @param filters
	 * @return
	 */
	private String parseDomainOnlyValue(Filter[] filters) {
		if(filters != null && filters.length > 0) {
			for(Filter filter : filters) {
				String name = filter.getName();
				Object value = filter.getValue();
				
				if(ValueUtil.isEqualIgnoreCase("belongs_to_site", name)) {
					return (value == null) ? "all" : ValueUtil.toString(value);
				}
			}
		}
		
		return null;
	}

	/**
	 * 검색을 위한 검색 필드, 조회 조건, 소팅 조건으로 도메인 소속된 센터 리스트 조회 쿼리를 완성하여 리턴
	 *
	 * @param siteOnlyVal
	 * @param page
	 * @param limit
	 * @param select
	 * @param sort
	 * @param query
	 * @return
	 */
	private Page<?> searchByPagination(String siteOnlyVal, Integer page, Integer limit, String select, String sort, Filter[] filters) {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		sql.add("select").add("c." + select.replaceAll(",", ",c.") + ",");
		
		if(ValueUtil.isEqualIgnoreCase("true", siteOnlyVal)) {
			sql.add("  dc.domain_id as belongs_domain_id")
			   .add("from")
			   .add("  tb_center c inner join (select * from tb_domain_center where domain_id = :domainId) dc on c.dc_cd = dc.dc_cd")
			   .add("where 1=1");
			
		} else if(ValueUtil.isEqualIgnoreCase("all", siteOnlyVal)) {
			sql.add("  dc.domain_id as belongs_domain_id")
			   .add("from")
			   .add("  tb_center c left outer join (select * from tb_domain_center where domain_id = :domainId) dc on c.dc_cd = dc.dc_cd")
			   .add("where 1=1");
			
		} else {
			sql.add("  null as belongs_domain_id")
			   .add("from")
			   .add("  tb_center c")
			   .add("where")
			   .add("  c.dc_cd not in (select dc_cd from tb_domain_center where domain_id = :domainId)");			
		}

		Map<String, Object> params = ValueUtil.newMap("domainId", Domain.currentDomainId());
		if(ValueUtil.isNotEmpty(filters)) {
			for(Filter filter : filters) {
				String name = filter.getName();
				
				if(ValueUtil.isEqualIgnoreCase("belongs_to_site", name)) {
					continue;
				}
				
				String op = filter.getOperator();
				Object val = filter.getValue();

				if(ValueUtil.isEqual(val, "true")) {
					val = true;
				} else if(ValueUtil.isEqual(val, "false")) {
					val = false;
				}

				if(ValueUtil.isEmpty(op) || ValueUtil.isEqualIgnoreCase(op, "eq") || ValueUtil.isEqualIgnoreCase(op, "=")) {
					sql.add("and c." + filter.getName() + " = :" + filter.getName());
					params.put(name, val);

				} else if(ValueUtil.isEqualIgnoreCase(op, "contains") || ValueUtil.isEqualIgnoreCase(op, "like")) {
					sql.add("and c." + filter.getName() + " like :" + filter.getName());
					params.put(name, "%" + val + "%");
				}
			}
		}

		if(ValueUtil.isNotEmpty(sort)) {
			sql.add("order by");
			Order[] orders = this.jsonParser.parse(sort, Order[].class);
			int idx = 0;

			for(Order order : orders) {
				if(idx > 0) { sql.add(OrmConstants.COMMA); }
				sql.add("c." + order.getField() + " " + (order.isAscending() ? "asc" : "desc"));
				idx++;
			}
		}

		page = (page == null) ? 1 : page;
		limit = (limit == null) ? ValueUtil.toInteger(SettingUtil.getValue(SysConfigConstants.SCREEN_PAGE_LIMIT, "50")) : limit;
		return this.queryManager.selectPageBySql(sql.toString(), params, Center.class, page, limit);
	}

	@RequestMapping(value = "/domain_centers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search Domain Center List")
	public Page<Code> domainCenters() {
		StringJoiner sql = new StringJoiner(SysConstants.LINE_SEPARATOR);
		sql.add("select")
		   .add("  c.id, c.dc_cd as name, c.dc_nm as description")
		   .add("from")
		   .add("  tb_center c inner join tb_domain_center dc on c.dc_cd = dc.dc_cd")
		   .add("where")
		   .add("  dc.domain_id = :domainId");

		Map<String, Object> params = ValueUtil.newMap("domainId", Domain.currentDomainId());
		return this.queryManager.selectPageBySql(sql.toString(), params, Code.class, 0, 0);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find one by ID")
	public Center findOne(@PathVariable("id") String id) {
		return this.getOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/{id}/exist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Check exists By ID")
	public Boolean isExist(@PathVariable("id") String id) {
		return this.isExistOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/check_import", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Check Before Import")
	public List<Center> checkImport(@RequestBody List<Center> list) {
		for (Center item : list) {
			this.checkForImport(Center.class, item);
		}

		return list;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiDesc(description = "Create")
	public Center create(@RequestBody Center input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public Center update(@PathVariable("id") String id, @RequestBody Center input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.getClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<Center> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}

	/*@RequestMapping(value = "/receive", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Receive (Interface) From WCS or WMS")
	public Map<String, Object> receiveFromWcs() {
		Long domainId = Domain.currentDomainId();
		Object count = this.receiverFactory.getCenterReceiver(domainId).receiveData(domainId);
		return ValueUtil.newMap("result,count", SysConstants.OK_STRING, count);
	}*/
}
