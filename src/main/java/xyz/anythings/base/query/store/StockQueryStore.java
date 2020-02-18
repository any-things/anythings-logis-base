package xyz.anythings.base.query.store;

import org.springframework.stereotype.Component;

/**
 * 재고 관련 쿼리 스토어 
 * 
 * @author shortstop
 */
@Component
public class StockQueryStore extends LogisBaseQueryStore {

	/**
	 * 셀 검색 쿼리
	 * 
	 * @return
	 */
	public String getSearchCellsQuery() {
		return this.getQueryByPath("stock/SearchCells");
	}
	
	/**
	 * 재고 검색 쿼리
	 * 
	 * @return
	 */
	public String getSearchStocksQuery() {
		return this.getQueryByPath("stock/SearchStocks");
	}
	
	/**
	 * 재고 검색 쿼리
	 * 
	 * @return
	 */
	public String getCalcSkuSupplementQtyQuery() {
		return this.getQueryByPath("stock/CalcSkuSupplementQty");
	}

}
