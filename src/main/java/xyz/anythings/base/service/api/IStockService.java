package xyz.anythings.base.service.api;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.Cell;
import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;
import xyz.anythings.base.entity.Stocktaking;

/**
 * 재고 관련 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IStockService {

	/**
	 * 셀 재고 조회
	 * 
	 * @param domainId
	 * @param cellCd
	 * @return
	 */
	public Stock findStock(Long domainId, String cellCd);
	
	/**
	 * 셀 재고 조회
	 * 
	 * @param domainId
	 * @param cellCd
	 * @param binIndex
	 * @return
	 */
	public Stock findStock(Long domainId, String cellCd, Integer binIndex);
	
	/**
	 * 셀 재고 조회
	 * 
	 * @param domainId
	 * @param locCd
	 * @param skuCd
	 * @return
	 */
	public Stock findStock(Long domainId, String cellCd, String skuCd);
	
	/**
	 * 재고 정보를 조회 
	 * 
	 * @param batch
	 * @param cellCd
	 * @param skuCd
	 * @return
	 */
	public Stock findStock(JobBatch batch, String cellCd, String skuCd);
	
	/**
	 * 고정식 여부에 skuCd가 적치되어 있는 로케이션 조회 
	 * 
	 * @param domainId
	 * @param fixedFlag
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public List<Cell> searchCellsBySku(Long domainId, boolean fixedFlag, String comCd, String skuCd);
	
	/**
	 * 추천 로케이션 조회 
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param sku
	 * @return
	 */
	public List<String> searchRecommendCells(String equipType, String equipCd, SKU sku);
	
	/**
	 * 재고 보충을 위한 상품 투입 수량 계산 
	 * 
	 * @param batch
	 * @param stock
	 * @return
	 */
	public int calcSkuInputQty(JobBatch batch, Stock stock);
	
	/**
	 * 재고 보충
	 * 
	 * @param domainId
	 * @param cellCd
	 * @param sku
	 * @param loadQty
	 * @return
	 */
	public Stock supplyStock(Long domainId, String cellCd, SKU sku, int loadQty);
	
	/**
	 * 재고 실사 시작
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCdList
	 */
	public void startStocktaking(Long domainId, String equipType, List<String> equipCdList);
	
	/**
	 * 오늘 날짜의 센터, 호기별 재고 실사를 실행한다.
	 * 
	 * @param domainId
	 * @param today
	 * @param equipType
	 * @param equipCd
	 */
	public void startStocktaking(Long domainId, String today, String equipType, String equipCd);
	
	/**
	 * 재고 실사 종료 
	 * 
	 * @param domainId
	 * @param stocktakingId
	 */
	public void finishStocktaking(Long domainId, String stocktakingId);

	/**
	 * 재고 실사 시 재고 조정 처리 
	 * 
	 * @param domainId
	 * @param stocktakingId
	 * @param indCd
	 * @param fromQty
	 * @param toQty
	 */
	public void adjustStock(Long domainId, String stocktakingId, String indCd, int fromQty, int toQty);

	/**
	 * 오늘 날짜에 해당하는 센터 및 호기에 해당하는 가장 최신의 재고 실사 정보 조회 
	 * 
	 * @param domainId
	 * @param date
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public Stocktaking findLatestStocktaking(Long domainId, String date, String equipType, String equipCd);
	
	/**
	 * LED Bar 사용 여부 설정을 토글
	 * 
	 * @param domainId
	 * @param on
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public boolean toggleLedSettingForStock(Long domainId, boolean on, String equipType, String equipCd);
	
	/**
	 * 호기의 재고 부족 셀의 모든 LED 점등
	 *  
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public int ledOnShortageStocks(Long domainId, String equipType, String equipCd);
	
	/**
	 * 호기의 모든 LED 소등
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @return
	 */
	public int ledOffByEquip(Long domainId, String equipType, String equipCd);
	
}
