package xyz.anythings.base.rest;

import java.util.ArrayList;
import java.util.List;

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
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;
import xyz.anythings.base.model.EquipBatchSet;
import xyz.anythings.base.service.api.ISkuSearchService;
import xyz.anythings.base.service.api.IStockService;
import xyz.anythings.base.service.util.LogisServiceUtil;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.elidom.dbist.dml.Page;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysMessageConstants;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.system.service.AbstractRestService;
import xyz.elidom.sys.util.MessageUtil;
import xyz.elidom.sys.util.ThrowUtil;
import xyz.elidom.util.ValueUtil;

@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/stocks")
@ServiceDesc(description = "Stock Service API")
public class StockController extends AbstractRestService {
	
	/**
	 * 재고 서비스
	 */
	@Autowired
	private IStockService stockService;
	/**
	 * 상품 조회 서비스
	 */
	@Autowired
	private ISkuSearchService skuSearchService;

	@Override
	protected Class<?> entityClass() {
		return Stock.class;
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
	public Stock findOne(@PathVariable("id") String id) {
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
	public Stock create(@RequestBody Stock input) {
		return this.createOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Update")
	public Stock update(@PathVariable("id") String id, @RequestBody Stock input) {
		return this.updateOne(input);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Delete")
	public void delete(@PathVariable("id") String id) {
		this.deleteOne(this.entityClass(), id);
	}

	@RequestMapping(value = "/update_multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Create, Update or Delete multiple at one time")
	public Boolean multipleUpdate(@RequestBody List<Stock> list) {
		for(Stock stock : list) {
			stock.setLastTranCd(Stock.TRX_UPDATE);
		}
		
		return this.cudMultipleData(this.entityClass(), list);
	}

	@RequestMapping(value = "/find_by_cell/{equip_type}/{equip_cd}/{cell_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "DPS Find Stock By Cell Code")
	public Stock findStock(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd, @PathVariable("cell_cd") String cellCd) {
		
		Stock stock = this.stockService.findOrCreateStock(Domain.currentDomainId(), cellCd);
		
		if(ValueUtil.isNotEqual(stock.getEquipType(), equipType) || ValueUtil.isNotEqual(stock.getEquipCd(), equipCd)) {
			// 현재 호기의 로케이션이 아닙니다. 
			throw ThrowUtil.newValidationErrorWithNoLog(true, "IS_NOT_LOCATION_OF_CURRENT_REGION");
		}
		
		return stock;
	}
	
	@RequestMapping(value = "/sku/search/{equip_type}/{equip_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search by SKU")
	public List<SKU> searchBySkuCd(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("sku_cd") String skuCd) {
		
		Long domainId = Domain.currentDomainId();
		// 1. 배치 진행 여부 확인 
		boolean runningBatchExist = LogisServiceUtil.isRunningBatchExist(domainId, equipType, equipCd);
		
		// 2. 배치 진행 상태에 따라 검색 가능한 대상 SKU가 달라짐.
		if(runningBatchExist) {
			// 2.1. 작업 진행 중일 때 배치에 포함된 상품 리스트 검색 
			EquipBatchSet equipBatch = LogisServiceUtil.checkRunningBatch(domainId, equipType, equipCd);
			List<SKU> skuList = this.skuSearchService.searchList(equipBatch.getBatch(), skuCd);
			
			if(ValueUtil.isEmpty(skuList)) {
				throw ThrowUtil.newValidationErrorWithNoLog("진행 중인 배치에 해당 상품 주문이 존재하지 않습니다.");
			} else {
				return skuList;
			}
		
		// 3. 배치가 진행 중이지 않은 경우 고정 로케이션에서 조회
		} else {
			// 3.1 상품 마스터에서 상품 검색
			String query = "select com_cd, sku_barcd, sku_cd, sku_nm, box_in_qty from sku where (sku_cd = :skuCd or sku_barcd = :skuCd)";
			List<SKU> skuList = this.queryManager.selectListBySql(query, ValueUtil.newMap("skuCd", skuCd), SKU.class, 0, 0);
			
			// 3.2
			if(ValueUtil.isEmpty(skuList)) {
				List<String> terms = ValueUtil.toList(MessageUtil.getTerm("terms.label.sku", "SKU"), skuCd);
				throw ThrowUtil.newValidationErrorWithNoLog(true, SysMessageConstants.NOT_FOUND, terms);
			}
			
			// 3.3 상품별 고정 로케이션 상품인지 판별
			List<SKU> retSkuList = new ArrayList<SKU>();
			for(SKU sku : skuList) {
				List<Stock> fixStocks = this.stockService.searchStocksBySku(domainId, equipType, null, true, sku.getComCd(), sku.getSkuCd());
				if(ValueUtil.isNotEmpty(fixStocks)) {
					retSkuList.add(sku);
				}
			}
			
			// 3.4 고정 로케이션에 해당 상품이 존재하지 않습니다.
			if(ValueUtil.isEmpty(retSkuList)) {
				throw ThrowUtil.newValidationErrorWithNoLog("고정 로케이션에 해당 상품 재고가 존재하지 않습니다.");
			}
			
			// 3.5 결과 리턴
			return skuList;
		}
	}
	
	@RequestMapping(value = "/search_by_sku/{equip_type}/{equip_cd}/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "DPS Search Stocks By SKU")
	public List<Stock> searchStocksBySku(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd) {
		
		List<Stock> stocks = this.stockService.searchStocksBySku(Domain.currentDomainId(), equipType, equipCd, comCd, skuCd);
		
		if(ValueUtil.isEmpty(stocks)) {
			throw ThrowUtil.newValidationErrorWithNoLog("해당 상품으로 재고를 찾을 수 없습니다.");
		} else {
			return stocks;
		}
	}
	
	@RequestMapping(value = "/recommend_cells/{equip_type}/{equip_cd}/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "DPS Search Recommendation Cells")
	public List<Stock> recommendCells(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd,
			@RequestParam(name = "fixed_flag", required = false) Boolean fixedFlag) {
		
		//return this.stockService.searchRecommendCells(Domain.currentDomainId(), equipType, equipCd, comCd, skuCd, fixedFlag);
		// TODO custom service로 연결
		return this.stockService.searchRecommendCells(Domain.currentDomainId(), equipType, null, comCd, skuCd, fixedFlag);
	}
	
	@RequestMapping(value = "/find_order_stock/{equip_type}/{equip_cd}/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "DPS Find Order Stock By SKU")
	public Stock findOrderStock(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd) {

		Long domainId = Domain.currentDomainId();
		// 1. 배치 진행 여부 확인 
		boolean runningBatchExist = LogisServiceUtil.isRunningBatchExist(domainId, equipType, equipCd);
		
		// 2. 배치 진행 중이면 보충 가능
		if(runningBatchExist) {
			EquipBatchSet equipBatchSet = LogisServiceUtil.findBatchByEquip(domainId, equipType, equipCd);
			JobBatch batch = equipBatchSet.getBatch();
			Stock stock = this.stockService.calculateSkuOrderStock(domainId, batch.getId(), equipType, null, comCd, skuCd);
			
			if(stock != null && stock.getOrderQty() > 0) {
				stock.setEquipType(equipType);
				stock.setEquipCd(equipCd);
				stock.setOrderQty(stock.getOrderQty() - stock.getAllocQty() - stock.getStockQty());
				
			} else {
				stock = new Stock();
				stock.setOrderQty(0);
				stock.setStockQty(0);
				stock.setInputQty(0);
			}
			
			return stock;
			
		// 3. 배치 진행 중이 아니면 고정 로케이션 보충만 가능
		} else {
			throw ThrowUtil.newValidationErrorWithNoLog("지금은 고정 셀 보충만 가능합니다. 고정 셀 상품을 선택해주세요.");
		}
	}
	
	@RequestMapping(value = "/calc_order_stock/{equip_type}/{equip_cd}/{cell_cd}/{com_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Calculate Order Stock By Cell and SKU")
	public Stock findOrderStock(
			@PathVariable("equip_type") String equipType,
			@PathVariable("equip_cd") String equipCd,
			@PathVariable("cell_cd") String cellCd,
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd,
			@RequestParam(name = "fixed_flag", required = false) Boolean fixedFlag) {
		
		Long domainId = Domain.currentDomainId();
		SKU sku = AnyEntityUtil.findEntityBy(domainId, true, SKU.class, "id,com_cd,sku_cd,sku_barcd,sku_nm", "comCd,skuCd", comCd, skuCd);
		Stock stock = this.stockService.findOrCreateStock(domainId, cellCd, sku);
		return this.stockService.calcuateOrderStock(stock);
	}
	
	@RequestMapping(value = "/load_stock/{rack_cd}/{cell_cd}/{com_cd}/{sku_cd}/{qty_unit}/{load_qty}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Load Stock")
	public Stock loadStock(
			@PathVariable("rack_cd") String rackCd,
			@PathVariable("cell_cd") String cellCd,
			@PathVariable("com_cd") String comCd,
			@PathVariable("sku_cd") String skuCd,
			@PathVariable("qty_unit") String qtyUnit,
			@PathVariable("load_qty") Integer loadQty) {
		
		// 1. Validation
		Long domainId = Domain.currentDomainId();
		
		// 2. SKU 조회
		SKU sku = AnyEntityUtil.findEntityBy(domainId, true, SKU.class, "id,box_in_qty,com_cd,sku_cd,sku_barcd,sku_nm", "comCd,skuCd", comCd, skuCd);

		// 3. 수량 단위가 박스 단위이면 박스 수량과 적치 수량을 곱해서 처리 
		if(ValueUtil.isEqualIgnoreCase("B", qtyUnit)) {
			loadQty = sku.getBoxInQty() * loadQty;
		}

		// 4. 재고 조회
		Stock stock = this.stockService.findOrCreateStock(domainId, cellCd, sku);
		
		// 5. 재고 보충
		stock = this.stockService.addStock(stock, Stock.TRX_IN, loadQty);

		// 6. 재고 리턴
		return stock;
	}
	
}