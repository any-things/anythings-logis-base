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
import xyz.anythings.base.query.store.StockQueryStore;
import xyz.anythings.base.service.api.IStockService;
import xyz.anythings.sys.util.AnyEntityUtil;
import xyz.elidom.sys.util.ValueUtil;

/**
 * 재고 서비스 기본 구현
 * 
 * @author shortstop
 */
@Component
public class StockService extends AbstractLogisService implements IStockService {

	@Autowired
	private StockQueryStore stockQueryStore;
	
	@Override
	public Stock findStock(Long domainId, String cellCd) {
		Stock stock = AnyEntityUtil.findEntityBy(domainId, false, Stock.class, null, "domainId,cellCd", domainId, cellCd);
		
		if(stock == null) {
			Cell cell = AnyEntityUtil.findEntityBy(domainId, true, Cell.class, null, "domainId,cellCd", domainId, cellCd);
			stock = new Stock();
			stock.setDomainId(domainId);
			stock.setCellCd(cellCd);
			stock.setEquipType(cell.getEquipType());
			stock.setEquipCd(cell.getEquipCd());
			stock.setActiveFlag(cell.getActiveFlag());
			stock.setLoadQty(0);
			stock.setAllocQty(0);
			stock.setStockQty(0);
			this.queryManager.insert(stock);
		}
		
		return stock;
	}
	
	@Override
	public Stock findStock(Long domainId, String cellCd, boolean exceptionWhenEmpty) {
		return AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, Stock.class, null, "domainId,cellCd", domainId, cellCd);
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, Integer binIndex) {
		return AnyEntityUtil.findEntityBy(domainId, false, Stock.class, null, "domainId,cellCd,binIndex", domainId, cellCd, binIndex);
	}
	
	@Override
	public Stock findStock(Long domainId, String cellCd, Integer binIndex, boolean exceptionWhenEmpty) {
		return AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, Stock.class, null, "domainId,cellCd,binIndex", domainId, cellCd, binIndex);
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, String skuCd) {
		return AnyEntityUtil.findEntityBy(domainId, false, Stock.class, null, "domainId,cellCd,skuCd", domainId, cellCd, skuCd);
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, String skuCd, boolean exceptionWhenEmpty) {
		return AnyEntityUtil.findEntityBy(domainId, exceptionWhenEmpty, Stock.class, null, "domainId,cellCd,skuCd", domainId, cellCd, skuCd);
	}

	@Override
	public List<Cell> searchCellsBySku(Long domainId, String stageCd, String equipType, String equipCd, boolean fixedFlag, String comCd, String skuCd) {
		String sql = this.stockQueryStore.getSearchCellsQuery();
		return AnyEntityUtil.searchItems(domainId, false, Cell.class, sql, "domainId,stageCd,equipType,equipCd,comCd,skuCd", domainId, stageCd, equipType, equipCd, comCd, skuCd);
	}
	
	@Override
	public List<Stock> searchStocksBySku(Long domainId, String stageCd, String equipType, String equipCd, boolean fixedFlag, String comCd, String skuCd) {
		String sql = this.stockQueryStore.getSearchStocksQuery();
		return AnyEntityUtil.searchItems(domainId, false, Stock.class, sql, "domainId,stageCd,equipType,equipCd,comCd,skuCd", domainId, stageCd, equipType, equipCd, comCd, skuCd);
	}

	@Override
	public List<String> searchRecommendCells(Long domainId, String equipType, String equipCd, String comCd, String skuCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calcSkuSupplementQty(JobBatch batch, Stock stock) {
		String sql = this.stockQueryStore.getCalcSkuSupplementQtyQuery();
		Map<String, Object> params = ValueUtil.newMap("domainId,batchId,cellCd,comCd,skuCd", batch.getDomainId(), batch.getId(), stock.getCellCd(), stock.getComCd(), stock.getSkuCd());
		return this.queryManager.selectBySql(sql, params, Integer.class);
	}

	@Override
	public Stock supplyStock(Long domainId, String cellCd, SKU sku, int loadQty) {
		Stock stock = this.findStock(domainId, cellCd);
		
		if(ValueUtil.isEmpty(stock.getSkuCd()) || ValueUtil.isEqualIgnoreCase(stock.getSkuCd(), sku.getSkuCd())) {
			stock.setComCd(sku.getComCd());
			stock.setSkuCd(sku.getSkuCd());
			stock.setSkuNm(sku.getSkuNm());
		}
		
		stock.setLoadQty(ValueUtil.toInteger(stock.getLoadQty()) + loadQty);
		this.queryManager.update(stock, "loadQty", "updaterId", "updatedAt");
		
		// TODO 재고 이력
		return stock;
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
	public void adjustStock(Long domainId, String stocktakingId, String indCd, int fromQty, int toQty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stocktaking findLatestStocktaking(Long domainId, String date, String equipType, String equipCd) {
		// TODO Auto-generated method stub
		return null;
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
	public void adjustStock(Long domainId, String trxCd, String equipType, String equipCd, String cellCd, String comCd, String skuCd, int addQty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStockForPicking(Long domainId, String equipType, String equipCd, String cellCd, String comCd, String skuCd, int pickQty) {
		// 2.1 Lock 을 걸고 재고 조회 TODO : BIN 인덱스 사용하는 경우 처리 
		Stock stock = AnyEntityUtil.findEntityBy(domainId, true, true, Stock.class, null, "equipType,equipCd,cellCd,comCd,skuCd", equipType, equipCd, cellCd, comCd, skuCd);
		stock.setAllocQty(stock.getAllocQty() - pickQty);
		stock.setPickedQty(stock.getPickedQty() + pickQty);
		
		// 2.2 재고 업데이트 
		this.queryManager.update(stock, "allocQty", "pickedQty", LogisConstants.ENTITY_FIELD_UPDATED_AT);		
	}

}
