package xyz.anythings.base.service.model;

/**
 * 주문 가공 옵션 
 * 
 * @author shortstop
 */
public class OrderProcessOption {
	/**
	 * 분배 방식 - 물량 균등 분배 : 1
	 */
	public static final String ASSORT_METHOD_VOLUME_EQUAL = "1";
	/**
	 * 분배 방식 - 상품 분배 : 2
	 */
	public static final String ASSORT_METHOD_SKU = "2";
	/**
	 * 분배 방식 - 거래처 균등 분배 : 3
	 */
	public static final String ASSORT_METHOD_CUSTOMER_EQUAL = "3";
	/**
	 * 분배 방식 - 수량 분배 : 4
	 */
	public static final String ASSORT_METHOD_QUANTITY = "4";
	
	/**
	 * 호기 코드 
	 */
	private String regionCd;
	/**
	 * 주문 분배 방식 
	 */
	private String assortMethod;
	/**
	 * 주문 그룹 필드 명 
	 */
	private String orderGroupName;
	/**
	 * 주문 그룹 필드 값
	 */
	private String orderGroupValue;
	
	public OrderProcessOption() {
	}
	
	public OrderProcessOption(String regionCd, String assortMethod, String orderGroupName, String orderGroupValue) {
		this.regionCd = regionCd;
		this.assortMethod = assortMethod;
		this.orderGroupName = orderGroupName;
		this.orderGroupValue = orderGroupValue;
	}
	
	public String getRegionCd() {
		return regionCd;
	}
	
	public void setRegionCd(String regionCd) {
		this.regionCd = regionCd;
	}
	
	public String getAssortMethod() {
		return assortMethod;
	}
	
	public void setAssortMethod(String assortMethod) {
		this.assortMethod = assortMethod;
	}
	
	public String getOrderGroupName() {
		return orderGroupName;
	}
	
	public void setOrderGroupName(String orderGroupName) {
		this.orderGroupName = orderGroupName;
	}
	
	public String getOrderGroupValue() {
		return orderGroupValue;
	}
	
	public void setOrderGroupValue(String orderGroupValue) {
		this.orderGroupValue = orderGroupValue;
	}
	
}
