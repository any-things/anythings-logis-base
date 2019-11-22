package xyz.anythings.base.rest;

import java.util.List;
import java.util.Map;
import xyz.elidom.dbist.dml.Filter;

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

import xyz.anythings.base.entity.DeviceProfile;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.anythings.base.entity.DeviceConf;

import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.ValueUtil;
import xyz.elidom.dbist.dml.Page;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/device_profiles")
@ServiceDesc(description = "DeviceProfile Service API")
public class DeviceProfileController extends AbstractRestService {

	@Override
	protected Class<?> entityClass() {
		return DeviceProfile.class;
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
	public DeviceProfile findOne(@PathVariable("id") String id) {
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
	public DeviceProfile create(@RequestBody DeviceProfile input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public DeviceProfile update(@PathVariable("id") String id, @RequestBody DeviceProfile input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<DeviceProfile> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}

	@RequestMapping(value = "/{id}/include_details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find One included all details by ID")
	public Map<String, Object> findDetails(@PathVariable("id") String id) {
		return this.findOneIncludedDetails(id);
	}

	@RequestMapping(value = "/{id}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search detail list by master ID")
	public List<DeviceConf> findDeviceConf(@PathVariable("id") String id) {
		xyz.elidom.dbist.dml.Query query = new xyz.elidom.dbist.dml.Query();
		query.addFilter(new Filter("deviceProfileId", id));
		return this.queryManager.selectList(DeviceConf.class, query);
	}

	@RequestMapping(value = "/{id}/items/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update, Delete multiple details at one time")
	public List<DeviceConf> updateDeviceConf(@PathVariable("id") String id, @RequestBody List<DeviceConf> list) {
		this.cudMultipleData(DeviceConf.class, list);
		return this.findDeviceConf(id);
	}
	
	@RequestMapping(value = "/configs/{device_type}/{stage_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search device config details by device & stage")
	public List<DeviceConf> searchConfigItems(@PathVariable("device_type") String deviceType, @PathVariable("stage_cd") String stageCd) {
		// TODO Redis Cache에서 조회하도록 수정
		xyz.elidom.dbist.dml.Query query = AnyOrmUtil.newConditionForExecution(Domain.currentDomainId());
		query.addSelect("id");
		query.addFilter(new Filter("stageCd", stageCd));
		query.addFilter(new Filter("defaultFlag", true));
		List<DeviceProfile> profiles = this.queryManager.selectList(DeviceProfile.class, query);
		
		if(ValueUtil.isNotEmpty(profiles)) {
			DeviceProfile profile = profiles.get(0);
			return this.findDeviceConf(profile.getId());
		} else {
			throw ThrowUtil.newDeviceConfigNotSet(stageCd);
		}
	}

}