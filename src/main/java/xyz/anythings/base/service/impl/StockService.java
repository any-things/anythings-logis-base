package xyz.anythings.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.Cell;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;
import xyz.anythings.base.entity.Stocktaking;
import xyz.anythings.base.model.EquipBatchSet;
import xyz.anythings.base.query.store.StockQueryStore;
import xyz.anythings.base.service.api.IStockService;
import xyz.anythings.base.service.util.LogisServiceUtil;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.sys.entity.Domain;
import xyz.elidom.sys.util.ValueUtil;

/**
 * 재고 서비스 기본 구현
 * 
 * @author shortstop
 */
@Component
public class StockService extends AbstractLogisService implements IStockService {

	/**
	 * 재고 쿼리 스토어
	 */
	@Autowired
	private StockQueryStore stockQueryStore;
	
	@Override
	public Stock findStock(Long domainId, String cellCd, boolean exceptionWhenEmpty) {
		return AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, Stock.class, null, "domainId,cellCd", domainId, cellCd);
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, String comCd, String skuCd, boolean exceptionWhenEmpty) {
		return AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, Stock.class, null, "domainId,cellCd,comCd,skuCd", domainId, cellCd, comCd, skuCd);
	}

	@Override
	public Stock findOrCreateStock(Long domainId, String cellCd) {
		Stock stock = this.findStock(domainId, cellCd, false);
		if(stock == null) {
			stock = this.createStock(domainId, cellCd, null, null, null);
		}
		
		return stock;
	}
	
	@Override
	public Stock findOrCreateStock(Long domainId, String cellCd, String comCd, String skuCd) {
		Stock stock = this.findStock(domainId, cellCd, false);
		if(stock == null) {
			stock = this.createStock(domainId, cellCd, null, null, null);
		}
		
		return stock;
	}

	@Override
	public Stock createStock(Long domainId, String cellCd, String comCd, String skuCd, String skuNm) {
		Cell cell = AnyEntityUtil.findEntityBy(domainId, true, Cell.class, null, "domainId,cellCd", domainId, cellCd);
		Stock stock = new Stock();
		
		if(ValueUtil.isEmpty(skuNm) && ValueUtil.isNotEmpty(skuCd)) {
			SKU sku = AnyEntityUtil.findEntityBy(domainId, true, SKU.class, "sku_cd,sku_barcd,sku_nm", "comCd,skuCd", comCd, skuCd);
			stock.setSkuCd(skuCd);
			stock.setSkuNm(sku.getSkuNm());
			stock.setSkuBarcd(sku.getSkuBarcd());
		} else {
			stock.setSkuCd(skuCd);
			stock.setSkuNm(skuNm);			
		}
				
		stock.setCellCd(cellCd);
		stock.setEquipType(cell.getEquipType());
		stock.setEquipCd(cell.getEquipCd());
		stock.setComCd(comCd);
		stock.setActiveFlag(cell.getActiveFlag());
		stock.setLoadQty(0);
		stock.setAllocQty(0);
		stock.setStockQty(0);
		stock.setPickedQty(0);
		stock.setMinStockQty(0);
		stock.setMaxStockQty(0);
		stock.setLastTranCd(Stock.TRX_CREATE);
		this.queryManager.insert(stock);
		
		return stock;
	}

	@Override
	public Stock addStock(Stock stock, String tranCd, int addQty) {
		tranCd = ValueUtil.isEmpty(tranCd) ? Stock.TRX_IN : tranCd;
		stock.setLastTranCd(tranCd);
		stock.setLoadQty(ValueUtil.toInteger(stock.getLoadQty()) + addQty);
		stock.setStockQty(stock.getLoadQty() + stock.getAllocQty());
		this.queryManager.upsert(stock, "lastTranCd", "loadQty", "stockQty", "updaterId", "updatedAt");		
		return stock;
	}

	@Override
	public Stock removeStock(Stock stock, String tranCd, int removeQty) {
		tranCd = ValueUtil.isEmpty(tranCd) ? Stock.TRX_OUT : tranCd;
		stock.setLastTranCd(tranCd);
		stock.setLoadQty(ValueUtil.toInteger(stock.getLoadQty()) - removeQty);
		stock.setStockQty(stock.getLoadQty() + stock.getAllocQty());
		this.queryManager.upsert(stock, "lastTranCd", "loadQty", "stockQty", "updaterId", "updatedAt");		
		return stock;
	}
	
	@Override
	public Stock adjustStock(Long domainId, String tranCd, String cellCd, String comCd, String skuCd, int adjustQty) {
		Stock stock = this.findStock(domainId, cellCd, comCd, skuCd, false);
		if(stock == null) {
			stock = this.findStock(domainId, cellCd, true);
		}
		
		tranCd = ValueUtil.isEmpty(tranCd) ? Stock.TRX_ADJUST : tranCd;
		stock.setLastTranCd(tranCd);
		stock.setLoadQty(ValueUtil.toInteger(stock.getLoadQty()) + adjustQty);
		stock.setStockQty(stock.getLoadQty() + stock.getAllocQty());
		this.queryManager.update(stock, "lastTranCd", "loadQty", "stockQty", "updaterId", "updatedAt");		
		return stock;
	}
	
	@Override
	public Stock supplyStock(Long domainId, String cellCd, SKU sku, int loadQty) {
		Stock stock = this.findOrCreateStock(domainId, cellCd);
		
		if(ValueUtil.isEmpty(stock.getSkuCd()) || ValueUtil.isEqualIgnoreCase(stock.getSkuCd(), sku.getSkuCd())) {
			stock.setComCd(sku.getComCd());
			stock.setSkuCd(sku.getSkuCd());
			stock.setSkuNm(sku.getSkuNm());
		}
		
		stock.setLoadQty(ValueUtil.toInteger(stock.getLoadQty()) + loadQty);
		this.queryManager.update(stock, "loadQty", "updaterId", "updatedAt");
		return stock;
	}
	
	@Override
	public List<Stock> searchStocksBySku(Long domainId, String equipType, String equipCd, String comCd, String skuCd) {
		String sql = this.stockQueryStore.getSearchStocksQuery();
		return AnyEntityUtil.searchItems(domainId, false, Stock.class, sql, "domainId,equipType,equipCd,comCd,skuCd", domainId, equipType, equipCd, comCd, skuCd);
	}
	
	@Override
	public List<Stock> searchStocksBySku(Long domainId, String equipType, String equipCd, Boolean fixedFlag, String comCd, String skuCd) {
		String sql = this.stockQueryStore.getSearchStocksQuery();
		return AnyEntityUtil.searchItems(domainId, false, Stock.class, sql, "domainId,equipType,equipCd,comCd,skuCd", domainId, equipType, equipCd, comCd, skuCd);
	}

	@Override
	public List<Stock> searchRecommendCells(Long domainId, String equipType, String equipCd, String comCd, String skuCd, Boolean fixedFlag) {
		// 1. 조회 조건
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addSelect("cellCd");
		condition.addFilter("equipType", equipType);
		condition.addFilter("equipCd", equipCd);
		condition.addFilter("comCd", comCd);
		condition.addFilter("skuCd", skuCd);
		
		if(fixedFlag != null) {
			condition.addFilter("fixedFlag", fixedFlag);
		}
		
		return this.queryManager.selectList(Stock.class, condition);
	}
	
	@Override
	public Stock calcuateOrderStock(Stock stock) {
		// 1. 고정식인 경우 
		if(stock.getFixedFlag() != null && stock.getFixedFlag()) {
			stock.setStockQty(stock.getMaxStockQty() - stock.getLoadQty() - stock.getAllocQty());
			return stock;			
			
		// 2. 자유식인 경우 
		} else {
			EquipBatchSet equipBatchSet = LogisServiceUtil.findBatchByEquip(Domain.currentDomainId(), stock.getEquipType(), stock.getEquipCd());
			int supplementQty = this.calcSkuSupplementQty(equipBatchSet.getBatch(), stock);
			stock.setStockQty(supplementQty);
			return stock;
		}
	}

	@Override
	public int calcSkuSupplementQty(JobBatch batch, Stock stock) {
		String sql = this.stockQueryStore.getCalcSkuSupplementQtyQuery();
		Map<String, Object> params = ValueUtil.newMap("domainId,batchId,cellCd,comCd,skuCd", batch.getDomainId(), batch.getId(), stock.getCellCd(), stock.getComCd(), stock.getSkuCd());
		return this.queryManager.selectBySql(sql, params, Integer.class);
	}

	@Override
	public boolean toggleLedSettingForStock(Long domainId, boolean on, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int ledOnShortageStocks(Long domainId, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ledOffByEquip(Long domainId, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Stock adjustStock(Long domainId, String stocktakingId, String indCd, int fromQty, int toQty) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void startStocktaking(Long domainId, String equipType, List<String> equipCdList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startStocktaking(Long domainId, String today, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishStocktaking(Long domainId, String stocktakingId) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public Stocktaking findLatestStocktaking(Long domainId, String date, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeStockForPicking(Long domainId, String equipType, String equipCd, String cellCd, String comCd, String skuCd, int pickQty) {
		// 1. Lock 을 걸고 재고 조회 
		Stock stock = AnyEntityUtil.findEntityBy(domainId, true, true, Stock.class, null, "equipType,equipCd,cellCd,comCd,skuCd", equipType, equipCd, cellCd, comCd, skuCd);
		stock.setAllocQty(stock.getAllocQty() - pickQty);
		stock.setPickedQty(stock.getPickedQty() + pickQty);
		
		// 2. 재고 업데이트 
		this.queryManager.update(stock, "allocQty", "pickedQty", LogisConstants.ENTITY_FIELD_UPDATED_AT);		
	}

}
