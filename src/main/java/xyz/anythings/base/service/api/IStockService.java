package xyz.anythings.base.service.api;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobBatch;
import xyz.anythings.base.entity.Location;
import xyz.anythings.base.entity.Region;
import xyz.anythings.base.entity.SKU;
import xyz.anythings.base.entity.Stock;
import xyz.anythings.base.entity.StockTaking;

/**
 * 재고 관련 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IStockService {

	/**
	 * 로케이션 재고 조회
	 *  
	 * @param locCd
	 * @return
	 */
	public Stock findStockByLocation(Long domainId, String locCd);
	/**
	 * 로케이션 재고 조회
	 *  
	 * @param locCd
	 * @return
	 */
	public Stock findStockByLocation(Long domainId, String locCd,String skuCd);
	
	/**
	 * 고정식 여부에 skuCd가 적치되어 있는 로케이션 조회 
	 * 
	 * @param domainId
	 * @param fixedFlag
	 * @param comCd
	 * @param skuCd
	 * @return
	 */
	public List<Location> searchSkuLocations(Long domainId, boolean fixedFlag, String comCd, String skuCd);
	
	/**
	 * 추천 로케이션 조회 
	 * 
	 * @param region
	 * @param sku
	 * @return
	 */
	public List<String> searchRecommendLocations(Region region, SKU sku);
	
	/**
	 * 주문 & 재고 정보를 조회 
	 * 
	 * @param batch
	 * @param regionCd
	 * @param locCd
	 * @param skuCd
	 * @return
	 */
	public Stock findOrderStock(JobBatch batch, String regionCd, String locCd, String skuCd);
	
	/**
	 * 재고 적치를 위한 상품 투입 수량 계산 
	 * 
	 * @param batch
	 * @param stock
	 * @return
	 */
	public int calcSkuInputQty(JobBatch batch, Stock stock);
	
	/**
	 * 재고 적치 
	 * 
	 * @param domainId
	 * @param locCd
	 * @param sku
	 * @param loadQty
	 * @return
	 */
	public Stock updateStock(Long domainId, String locCd, SKU sku, int loadQty);
	
	/**
	 * 재고 실사 시작
	 * 
	 * @param domainId
	 * @param regionCdList
	 */
	public void startStockTaking(Long domainId, List<String> regionCdList);
	
	/**
	 * 오늘 날짜의 센터, 호기별 재고 실사를 실행한다.
	 * 
	 * @param domainId
	 * @param today
	 * @param regionCd
	 */
	public void startStockTaking(Long domainId, String today, String regionCd);
	
	/**
	 * 재고 실사 종료 
	 * 
	 * @param domainId
	 * @param stockTakingId
	 */
	public void finishStockTaking(Long domainId, String stockTakingId);

	/**
	 * 재고 조정 처리 
	 * 
	 * @param domainId
	 * @param msgObj
	 */
	public void adjustStock(Long domainId, String stockTakingId, String mpiCd, int reqEaQty, int resEaQty);

	/**
	 * 오늘 날짜에 해당하는 센터 및 호기에 해당하는 가장 최신의 재고 실사 정보 조회 
	 * 
	 * @param domainId
	 * @param date
	 * @param regionCd
	 * @return
	 */
	public StockTaking getLatestStockTaking(Long domainId, String date, String regionCd);
	
	/**
	 * 주문에 대한 재고가 존재하는지 확인.
	 * 
	 * @param domainId
	 * @param orderId
	 * @return
	 */
	public boolean isOrderStockAvailable(Long domainId, String orderId);
	
	/**
	 * LED Bar 사용 여부 설정을 토글
	 * 
	 * @param domainId
	 * @param on
	 * @param regionCd
	 * @return
	 */
	public boolean toggleLedOnSetting(Long domainId, boolean on, String regionCd);
	
	/**
	 * 호기의 재고 부족 로케이션의 모든 LED 점등
	 *  
	 * @param domainId
	 * @param regionCd
	 * @return
	 */
	public int ledOnByShortage(Long domainId, String regionCd);
	
	/**
	 * 호기의 모든 LED 소등
	 * 
	 * @param domainId
	 * @param regionCd
	 * @return
	 */
	public int ledOffByRegion(Long domainId, String regionCd);
	
}
