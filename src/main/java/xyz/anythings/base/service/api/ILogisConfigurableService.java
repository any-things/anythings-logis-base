//package xyz.anythings.base.service.api;
//
//import java.util.List;
//
//import xyz.anythings.base.entity.BoxResult;
//import xyz.anythings.base.entity.JobProcess;
//import xyz.anythings.base.entity.Location;
//
///**
// * 고객사 별 설정에 따라서 처리해주는 공통 Configurable Service 인터페이스
// * 
// * @author shortstop
// */
//public interface ILogisConfigurableService {
//
//	/******************************************************************
//	 * 							1. 설정 사항 값 조회 
//	 ******************************************************************/
//	
//	/**
//	 * 사이트, 고객사 별 Box ID 매핑 시점
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getBoxIdMappingPoint(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 라벨 인쇄 방법
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getLabelPrintMethod(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 실적 보고 활성화 여부
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return true이면 실적을 상위 시스템에 보고
//	 */
//	public boolean isReportResultEnabled(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 박스 실적 보고 시점 리턴
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getBoxResultReportPoint(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 라벨 템플릿 조회 & 리턴
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getBarcodeLabelTemplate(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 거래명세서 템플릿 조회 & 리턴
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getPrintStatementTemplate(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 한 번에 인쇄할 송장 라벨 개수
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public int getBarcodeLabelCountAtOnce(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 Fullbox 이후 액션
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getFullboxAction(Long domainId, String comCd);
//	
//	/**
//	 * 사이트, 고객사 별 검수 이후 액션
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String getInspectionAction(Long domainId, String comCd);
//	
//	/******************************************************************
//	 * 						2. 트레이 I/F, 라벨, 명세서 인쇄 
//	 ******************************************************************/
//	
//	/**
//	 * 소터로 부터 Tray 정보를 접수
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object receiveSorterTray(Long domainId, Object ... parameters);
//	
//	/**
//	 * 소터로 Tray 처리 실적 정보 전송
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object sendSorterTrayResult(Long domainId, Object ... parameters);
//	
//	/**
//	 * 옵션에 따른 라벨 인쇄
//	 * 
//	 * @param box
//	 * @param printerIdOrName
//	 */
//	public void printLabelByOption(BoxResult box, String printerIdOrName);
//	
//	/**
//	 * 옵션에 따른 라벨 인쇄
//	 * 
//	 * @param box
//	 */
//	public void printLabelByOption(BoxResult box);
//	
//	/**
//	 * 상위 시스템에 라벨 인쇄 요청
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 */
//	public void requestPrintBarcodeLabel(Long domainId, String boxResultId);
//	
//	/**
//	 * 상위 시스템에 라벨 인쇄 요청
//	 * 
//	 * @param box
//	 */
//	public void requestPrintBarcodeLabel(BoxResult box);
//	
//	/**
//	 * 사이트, 고객사 별 DAS 송장 바코드 라벨 인쇄
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerName
//	 */
//	public void printBarcodeLabel(Long domainId, String boxResultId, String printerName);
//	
//	/**
//	 * 사이트, 고객사 별 라벨 인쇄
//	 * 
//	 * @param box
//	 * @param printerIdOrName
//	 */
//	public void printBarcodeLabel(BoxResult box, String printerIdOrName);
//	
//	/**
//	 * 사이트, 고객사 별 인쇄 명령으로 인쇄
//	 * 
//	 * @param domainid
//	 * @param command
//	 * @param printCount
//	 * @param printerIdOrName TODO Printer 엔티티 사용 가능한 방법 고민
//	 */
//	public void printBarcodeLabel(Long domainId, String command, int printCount, String printerIdOrName);
//	
//	/**
//	 * 거래명세서 인쇄
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerIdOrName
//	 */
//	public void printTradeStatment(Long domainId, String boxResultId, String printerIdOrName);
//	
//	/**
//	 * 거래명세서 인쇄
//	 * 
//	 * @param box
//	 * @param printerIdOrName
//	 */
//	public void printTradeStatment(BoxResult box, String printerIdOrName);
//	
//	/******************************************************************
//	 * 							3. 송장 발행 
//	 ******************************************************************/
//	
//	/**
//	 * 송장 번호 발행
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @return
//	 */
//	public String publishInvoiceId(Long domainId, String comCd);
//	
//	/******************************************************************
//	 * 							4. Fullbox, 검수 액션  
//	 ******************************************************************/
//	
//	/**
//	 * 사이트, 고객사 별 출고 검수 후 액션 처리
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerId
//	 */
//	public void inspectionAction(Long domainId, String boxResultId, String printerId);
//	
//	/**
//	 * 사이트, 고객사 별 DAS 출고 검수 후 액션 처리
//	 * 
//	 * @param box
//	 * @param printerId
//	 */
//	public void inspectionAction(BoxResult box, String printerId);
//	
//	/**
//	 * 사이트, 고객사 별 fullbox 후 액션 처리
//	 * 
//	 * @param domainId
//	 * @param boxResultId
//	 * @param printerId
//	 */
//	public void fullboxAction(Long domainId, String boxResultId, String printerId);
//	
//	/**
//	 * 사이트, 고객사 별 fullbox 후 액션 처리
//	 * 
//	 * @param box
//	 * @param printerId
//	 */
//	public void fullboxAction(BoxResult box, String printerId);
//	
//	/******************************************************************
//	 * 							5. 박싱 작업  
//	 ******************************************************************/
//	
//	/**
//	 * 박싱 시작
//	 * 
//	 * @param assortService
//	 * @param job
//	 * @param location
//	 * @return
//	 */
//	public BoxResult startBoxing(IAssortService assortService, JobProcess job, Location location);
//	
//	/**
//	 * 수량 조절 후 박싱 시작
//	 * 
//	 * @param assortService
//	 * @param job
//	 * @param location
//	 * @param boxQty
//	 * @param eaQty
//	 * @return
//	 */
//	public BoxResult startBoxingByModifyQty(IAssortService assortService, JobProcess job, Location location, Integer boxQty, Integer eaQty);
//	
//	/**
//	 * 박싱 완료
//	 * 
//	 * @param assortService
//	 * @param box
//	 * @param location
//	 * @return
//	 */
//	public void endBoxing(IAssortService assortService, BoxResult box, Location location);
//	
//	/**
//	 * 작업 배치에 대해서 박싱 작업이 안 된 모든 박스의 박싱을 완료한다.
//	 * 
//	 * @param assortService
//	 * @param domainId
//	 * @param batchId
//	 * @param regionCd
//	 * @param sideCd
//	 * @return
//	 */
//	public List<BoxResult> batchBoxing(IAssortService assortService, Long domainId, String batchId, String regionCd, String sideCd);
//	
//	/**
//	 * 박싱 취소
//	 * 
//	 * @param box
//	 * @return
//	 */
//	public BoxResult cancelBoxing(BoxResult box);
//	
//	/******************************************************************
//	 * 							6. 실적 전송  
//	 ******************************************************************/
//	/**
//	 * 사이트 별, 고객사 별 체크 포인트 별 박스 실적 전송 포인트 여부 조회
//	 * 
//	 * @param domainId
//	 * @param comCd
//	 * @param checkPoint
//	 * @return
//	 */
//	public boolean isBoxResultSendPoint(Long domainId, String comCd, String checkPoint);
//	
//	/**
//	 * 주문 라인별 실적 전송
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object sendPickResult(Long domainId, Object ... parameters);
//	
//	/**
//	 * 박스별 실적 전송
//	 * 
//	 * @param domainId
//	 * @param parameters
//	 * @return
//	 */
//	public Object sendBoxResult(Long domainId, Object ... parameters);
//	
//	/**
//	 * 피킹 확정 실적 후 처리
//	 * 
//	 * @param job
//	 * @param totalPickedQty
//	 * @return
//	 */
//	public Object handlePickData(JobProcess job, int totalPickedQty);
//	
//	/**
//	 * 피킹 실적 데이터 상위 시스템으로 전송
//	 *  
//	 * @param domainId
//	 * @param pickResults
//	 * @return
//	 */
//	public Object sendPickData(Long domainId, List<?> pickResults);
//	
//}
