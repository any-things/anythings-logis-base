package xyz.anythings.base.service.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 수량 검수 모델 
 * 
 * @author shortstop
 */
public class Inspection {
	/**
	 * 주문 번호 
	 */
	private String orderId;
	/**
	 * 버킷 번호 
	 */
	private String bucketCd;
	/**
	 * 송장 번호 
	 */
	private String invoiceId;
	/**
	 * 거래처 코드 
	 */
	private String custCd;
	/**
	 * 거래처 명 
	 */
	private String custNm;
	/**
	 * 상태
	 */
	private String status;
	/**
	 * 내품 내역
	 */
	private List<InspectionItem> items;
	
	public Inspection() {
	}
	
	public Inspection(String orderId, String bucketCd, String invoiceId, String custCd, String custNm, String status) {
		this(orderId, bucketCd, invoiceId, custCd, custNm, status, null);
	}
	
	public Inspection(String orderId, String bucketCd, String invoiceId, String custCd, String custNm, String status, List<InspectionItem> items) {
		this.orderId = orderId;
		this.bucketCd = bucketCd;
		this.invoiceId = invoiceId;
		this.custCd = custCd;
		this.custCd = custNm;
		this.status = status;
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBucketCd() {
		return bucketCd;
	}

	public void setBucketCd(String bucketCd) {
		this.bucketCd = bucketCd;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<InspectionItem> getItems() {
		return items;
	}

	public void setItems(List<InspectionItem> items) {
		this.items = items;
	}
	
	public void addItem(String skuCd, String skuNm, String skuBarcd, Integer itemQty) {
		if(this.items == null) {
			this.items = new ArrayList<InspectionItem>();
		}
		
		this.items.add(new InspectionItem(skuCd, skuNm, skuBarcd, itemQty));
	}
	
}
