package xyz.anythings.base.service.api.sub;
//package xyz.anythings.base.service.api;
//
//import xyz.anythings.base.entity.BoxResult;
//
///**
// * 라벨 프린터, 일반 프린터 출력 서비스 API
// * 
// * @author shortstop
// */
//public interface IPrintingService {
//
//	/**
//	 * 박스 라벨 인쇄
//	 * 
//	 * @param box
//	 */
//	public void printBarcodeLabel(BoxResult box);
//	
//	/**
//	 * 박스 라벨 인쇄
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerIdOrName
//	 */
//	public void printBarcodeLabel(Long domainId, String boxResultId, String printerIdOrName);
//	
//	/**
//	 * 박스 라벨 인쇄
//	 * 
//	 * @param box
//	 * @param printerIdOrName
//	 */
//	public void printBarcodeLabel(BoxResult box, String printerIdOrName);
//	
//	/**
//	 * 인쇄 명령으로 라벨 인쇄
//	 * 
//	 * @param domainid
//	 * @param command
//	 * @param printCount
//	 * @param printerIdOrName
//	 */
//	public void printBarcodeLabel(Long domainId, String command, int printCount, String printerIdOrName);
//	
//	/**
//	 * 리포트 인쇄
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerIdOrName
//	 */
//	public void printReport(Long domainId, String boxResultId, String printerIdOrName);
//	
//	/**
//	 * 리포트 인쇄
//	 * 
//	 * @param box
//	 * @param printerIdOrName
//	 */
//	public void printReport(BoxResult box, String printerIdOrName);
//	
//	/**
//	 * 리포트 인쇄
//	 * 
//	 * @param box
//	 */
//	public void printReport(BoxResult box);
//	
//}
