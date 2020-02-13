package xyz.anythings.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.Cell;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;
import xyz.anythings.base.entity.Stocktaking;
import xyz.anythings.base.service.api.IStockService;
import xyz.anythings.sys.util.AnyEntityUtil;

/**
 * 재고 서비스 기본 구현
 * 
 * @author shortstop
 */
@Component
public class StockService extends AbstractLogisService implements IStockService {

	@Override
	public Stock findStock(Long domainId, String cellCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, Integer binIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock findStock(Long domainId, String cellCd, String skuCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock findStock(JobBatch batch, String cellCd, String skuCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> searchCellsBySku(Long domainId, boolean fixedFlag, String comCd, String skuCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchRecommendCells(String equipType, String equipCd, SKU sku) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calcSkuInputQty(JobBatch batch, Stock stock) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stock supplyStock(Long domainId, String cellCd, SKU sku, int loadQty) {
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
