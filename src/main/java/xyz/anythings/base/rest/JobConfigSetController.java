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

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.JobConfig;
import xyz.anythings.base.entity.JobConfigSet;
import xyz.anythings.base.service.impl.ConfigSetService;
import xyz.anythings.base.util.LogisEntityUtil;
import xyz.elidom.core.entity.CodeDetail;
import xyz.elidom.dbist.dml.Filter;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.system.service.AbstractRestService;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/job_config_set")
@ServiceDesc(description = "JobConfigSet Service API")
public class JobConfigSetController extends AbstractRestService {
	
	@Autowired
	private ConfigSetService configSetService;

	@Override
	protected Class<?> entityClass() {
		return JobConfigSet.class;
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
	public JobConfigSet findOne(@PathVariable("id") String id) {
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
	public JobConfigSet create(@RequestBody JobConfigSet input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public JobConfigSet update(@PathVariable("id") String id, @RequestBody JobConfigSet input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<JobConfigSet> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}

	@RequestMapping(value = "/{id}/include_details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Find One included all details by ID")
	public Map<String, Object> findDetails(@PathVariable("id") String id) {
		return this.findOneIncludedDetails(id);
	}

	@RequestMapping(value = "/{id}/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search detail list by master ID")
	public List<JobConfig> findJobConfig(@PathVariable("id") String id) {
		xyz.elidom.dbist.dml.Query query = new xyz.elidom.dbist.dml.Query();
		query.addFilter(new Filter("jobConfigSetId", id));
		query.addOrder("category", true);
		query.addOrder("name", true);
		return this.queryManager.selectList(JobConfig.class, query);
	}

	@RequestMapping(value = "/{id}/items/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update, Delete multiple details at one time")
	public List<JobConfig> updateJobConfig(@PathVariable("id") String id, @RequestBody List<JobConfig> list) {
		this.cudMultipleData(JobConfig.class, list);
		return this.findJobConfig(id);
	}
	
	@RequestMapping(value = "/{id}/copy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiDesc(description = "Copy template config set")
	public JobConfigSet copyConfigSet(@PathVariable("id") String templateId) {
		return this.configSetService.copyJobConfigSet(templateId);
	}
	
	@RequestMapping(value = "/build_config_set/{batch_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Build config set by batch id")
	public JobConfigSet buildBatchConfigSet(@PathVariable("batch_id") String batchiId) {
		JobBatch batch = LogisEntityUtil.findEntityById(true, JobBatch.class, batchiId);
		return this.configSetService.buildJobConfigSet(batch);
	}
	
	@RequestMapping(value = "/clear_config_set/{batch_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Clear config set by batch id")
	public JobConfigSet clearBatchConfigSet(@PathVariable("batch_id") String batchiId) {
		JobBatch batch = LogisEntityUtil.findEntityById(true, JobBatch.class, batchiId);
		return this.configSetService.buildJobConfigSet(batch);
	}
	
	@RequestMapping(value = "/config_value/{batch_id}/{config_key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Config key by batch id")
	public CodeDetail getConfigValue(@PathVariable("batch_id") String batchiId, @PathVariable("config_key") String configKey) {
		JobBatch batch = LogisEntityUtil.findEntityById(true, JobBatch.class, batchiId);
		String value = this.configSetService.getJobConfigValue(batch, configKey);
		// TODO key, value 오브젝트를 새로 정의
		CodeDetail item = new CodeDetail();
		item.setName(configKey);
		item.setDescription(value);
		return item;
	}

}