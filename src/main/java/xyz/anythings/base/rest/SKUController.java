package xyz.anythings.base.rest;

import java.util.List;

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

import xyz.anythings.base.entity.SKU;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.system.service.AbstractRestService;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/sku")
@ServiceDesc(description = "SKU Service API")
public class SKUController extends AbstractRestService {

	@Override
	protected Class<?> entityClass() {
		return SKU.class;
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
	public SKU findOne(@PathVariable("id") String id) {
		return this.getOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/{id}/exist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Check exists By ID")
	public Boolean isExist(@PathVariable("id") String id) {
		return this.isExistOne(this.entityClass(), id);
	}
	
	@RequestMapping(value = "/check_import", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Check Before Import")
	public List<SKU> checkImport(@RequestBody List<SKU> list) {
		for (SKU item : list) {
			this.checkForImport(SKU.class, item);
		}
		
		return list;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiDesc(description = "Create")
	public SKU create(@RequestBody SKU input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public SKU update(@PathVariable("id") String id, @RequestBody SKU input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.getClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<SKU> list) {
		return this.cudMultipleData(this.entityClass(), list);
	}
	
	/*@RequestMapping(value = "/receive/ready/{com_cd}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Ready to Receive From Parent System")
	public Map<String, Object> readyToReceive(@PathVariable("com_cd") String comCd) {
		Long domainId = Domain.currentDomainId();
		
		Map<String, Object> retVal = ValueUtil.newMap("com_cd", comCd);
		IfSkuReceiver skuReceiver = this.receiverFactory.getSkuReceiver(domainId);
		IfSkuBarcodeReceiver skuBarcodeReceiver = this.receiverFactory.getSkuBarcodeReceiver(domainId);
		List<String> comCdList = ValueUtil.newStringList(comCd);
		
		int skuCount = (skuReceiver == null) ? 0 : skuReceiver.getReceiveCount(domainId, comCdList);
		int skuBcrCount = (skuBarcodeReceiver == null) ? 0 : skuBarcodeReceiver.getReceiveCount(domainId, comCdList);
		
		boolean skuWeightReceiveFlag = ValueUtil.toBoolean(SettingUtil.getValue(ConfigConstants.MPS_MASTER_SKU_WEIGHT_IF_ENABLED, MpsConstants.FALSE_STRING));
		if(skuWeightReceiveFlag) {
			IfSkuWeightReceiver skuWeightReceiver = this.receiverFactory.getSkuWeightReceiver(domainId);
			int weightCount = (skuWeightReceiver == null) ? 0 : skuWeightReceiver.getReceiveCount(domainId, comCdList);
			retVal.put("weight_count", weightCount);
		}
		
		retVal.put("sku_count", skuCount);
		retVal.put("barcd_count", skuBcrCount);
		return retVal;
	}
	
	@RequestMapping(value = "/receive/start/{com_cd}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Receive (Interface) From Parent System")
	public Map<String, Object> startToReceive(@PathVariable("com_cd") String comCd) {
		Long domainId = Domain.currentDomainId();
		List<String> comCdList = ValueUtil.newStringList(comCd);
		
		IfSkuReceiver skuReceiver = this.receiverFactory.getSkuReceiver(domainId);
		Object skuCount = skuReceiver.receiveData(domainId, comCdList);
		Map<String, Object> retVal = ValueUtil.newMap("result,sku_count", SysConstants.OK_STRING, skuCount);
		
		IfSkuBarcodeReceiver skuBarcodeReceiver = this.receiverFactory.getSkuBarcodeReceiver(domainId);
		if(skuBarcodeReceiver != null) {
			Object barcdCount = skuBarcodeReceiver.receiveData(domainId, comCdList);
			retVal.put("barcd_count", barcdCount);
		}
		
		boolean skuWeightReceiveFlag = ValueUtil.toBoolean(SettingUtil.getValue(ConfigConstants.MPS_MASTER_SKU_WEIGHT_IF_ENABLED, MpsConstants.FALSE_STRING));
		if(skuWeightReceiveFlag) {
			IfSkuWeightReceiver skuWeightReceiver = this.receiverFactory.getSkuWeightReceiver(domainId);
			if(skuWeightReceiver != null) {
				Object weightCount = skuWeightReceiver.receiveData(domainId, comCdList);
				retVal.put("weight_count", weightCount);
			}
		}
		
		return retVal;
	}*/

}