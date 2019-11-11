package xyz.anythings.base.service.util;

import xyz.anythings.base.entity.JobBatch;

/**
 * 작업 배치 범위 내 작업 설정 값 조회 유틸리티
 * 
 *  - job.cmm.sku.barcode.max.length				상품 바코드 최대 길이
 *  - job.cmm.sku.search.condition.fields			SKU 조회를 위한 코드 필드명 리스트
 *  - job.cmm.sku.search.select.fields			SKU 조회를 위한 조회 필드명 리스트
 *  - job.cmm.sku.skucd.validation.enabled		서버 사이드에서 상품 유효성 체크 여부
 *  - job.cmm.sku.weight.unit						상품 중량 정보를 g으로 사용할 지 kg으로 사용할 지 여부
 *  - job.cmm.server.validate.sku_cd.rule			서버 사이드에서 상품 유효성 체크를 위한 룰
 *  - job.cmm.server.validate.box_id.rule			서버 사이드에서 박스 ID 유효성 체크를 위한 룰
 *  - job.cmm.server.validate.cell_cd.rule		서버 사이드에서 로케이션 코드 유효성 체크를 위한 룰
 *  - job.cmm.server.validate.ind_cd.rule			서버 사이드에서 표시기 코드 유효성 체크를 위한 룰
 *  - job.cmm.server.validate.rack_cd.rule		서버 사이드에서 랙 코드 유효성 체크를 위한 룰
 *  - job.cmm.server.validate.invoice_no.rule		서버 사이드에서 송장번호 유효성 체크를 위한 룰
 *  - job.cmm.box.result.report.enabled			박스 실적 보고 여부 
 *  - job.cmm.box.result.report.point				박스 실적 전송 시점
 *  - job.cmm.box.cancel.enabled					박스 취소 기능 활성화 여부 
 *  - job.cmm.box.weight.enabled					박스 중량 관리 여부
 *  - job.cmm.box.out.class.field					주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명
 *  - job.cmm.box.action							박스 처리 후 액션
 *  - job.cmm.box.invoice-no.rule					옵션에 따라 송장 번호를 다르게 부여 
 *  - job.cmm.box.box_id.unique.scope				박스 ID 유일성 보장 범위
 *  - job.cmm.pick.cancel.enabled					확정 취소 기능 활성화 여부 
 *  - job.cmm.pick.result.report.enabled			확정 실적 보고 여부 
 *  - job.cmm.pick.cancel.status.enabled			표시기에서 분류 작업 취소시에 '취소' 상태로 관리할 지 여부
 *  - job.cmm.pick.include.cancelled.enabled		상품 투입시 취소된 상품을 조회할 지 여부
 *  - job.cmm.label.print.count					라벨 발행시 한 번에 동일 라벨을 몇 장 발행할 지 설정 
 *  - job.cmm.label.print.method					송장 라벨 발행 방법 
 *  - job.cmm.label.template						송장 라벨을 자체 출력시 송장 라벨 출력 템플릿 설정 MPS 매니저 > 개발자 > 커스텀 템플릿에 등록된 송장 라벨 명칭을 설정하면 됨
 *  - job.cmm.device.list							사용 디바이스 리스트
 *  - job.cmm.device.side.enabled					디바이스의 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
 *  - job.cmm.device.station.enabled				디바이스의 작업 영역 정보를 사용할 지 여부
 *  - job.cmm.inspection.enabled					출고 검수 활성화 여부
 *  - job.cmm.insepction.weight.enabled			중량 검수 활성화 여부
 *  - job.cmm.inspection.action					출고 검수 후 액션. 아래 출고 검수 활성화 시에만 의미가 있음.
 *  - job.cmm.input.work_scope					투입시 투입 범위
 *  - job.cmm.input.mode.single.enabled			단품 투입 활성화 여부
 *  - job.cmm.input.single.ind_on.mode			job.cmm.input.mode.single.enabled이 true인 경우에 단품 투입시 하나씩 표시기에 점등할 것인지 전체 표시기에 점등할 것인지 설정
 *  - job.cmm.input.mode.box.enabled				완박스 투입 활성화 여부
 *  - job.cmm.input.box.ind_on.mode				job.cmm.input.mode.box.enabled이 true인 경우에  완박스 투입시 하나씩 표시기에 점등할 것인지 전체 표시기에 점등할 것인지 설정
 *  - job.cmm.input.mode.bundle.enabled			번들 투입 활성화 여부
 *  - job.cmm.order.delete.when.order_cancel		주문 취소시 데이터 삭제 여부
 *  - job.cmm.assigned-cell.indicator.enabled		작업지시 시점에 표시기에 할당 셀 표시 활성화 여부
 *  - job.cmm.trade-statement.template			거래명세서 템플릿 이름을 설정
 *	
 * @author shortstop
 */
public class BatchJobConfigUtil {
	
	/**
	 * 작업 배치 설정 중에 최대 바코드 사이즈
	 * 
	 * @param batch
	 * @return
	 */
	public static int getMaxBarcodeSize(JobBatch batch) {
		// job.cmm.sku.barcode.max.length
		return 0;
	}
	
	/**
	 * 상품 조회를 위한 코드 필드명 리스트
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSkuSearchConditionFields(JobBatch batch) {
		// job.cmm.sku.search.condition.fields
		return null;
	}
	
	/**
	 * 상품 조회를 위한 상품 조회 필드명 리스트
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSkuSelectConditionFields(JobBatch batch) {
		// job.cmm.sku.search.select.fields
		return null;
	}
	
	/**
	 * 서버 사이드에서 상품 코드 유효성 체크 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isNeedCheckSkucdValidation(JobBatch batch) {
		// job.cmm.sku.skucd.validation.enabled
		return false;
	}
	
	/**
	 * 상품 중량 정보를 g으로 사용할 지 kg으로 사용할 지 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSkuWeightUnit(JobBatch batch) {
		// job.cmm.sku.weight.unit		
		return null;
	}

	/**
	 * 서버 사이드에서 상품 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSkuCdValidationRule(JobBatch batch) {
		// job.cmm.server.validate.sku_cd.rule		
		return null;
	}
	
	/**
	 * 서버 사이드에서 박스 ID 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getBoxIdValidationRule(JobBatch batch) {
		// job.cmm.server.validate.box_id.rule					
		return null;
	}
	
	/**
	 * 서버 사이드에서 셀 코드 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getCellCdValidationRule(JobBatch batch) {
		// job.cmm.server.validate.cell_cd.rule
		return null;
	}
	
	/**
	 * 서버 사이드에서 표시기 코드 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getIndCdValidationRule(JobBatch batch) {
		// job.cmm.server.validate.ind_cd.rule			
		return null;
	}
	
	/**
	 * 서버 사이드에서 랙 코드 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getRackCdValidationRule(JobBatch batch) {
		// 	job.cmm.server.validate.rack_cd.rule
		return null;
	}
	
	/**
	 * 서버 사이드에서 송장번호 유효성 체크를 위한 룰
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInvoiceNoValidationRule(JobBatch batch) {
		// job.cmm.server.validate.invoice_no.rule
		return null;
	}
	
	/**
	 * 
	job.cmm.box.action							박스 처리 후 액션
	job.cmm.box.invoice-no.rule					옵션에 따라 송장 번호를 다르게 부여 
	job.cmm.box.box_id.unique.scope				박스 ID 유일성 보장 범위
	 */
	
	/**
	 * 박스 실적 보고 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isBoxResultReportEnabled(JobBatch batch) {
		// job.cmm.box.result.report.enabled
		return false;
	}
	
	/**
	 * 박스 실적 전송 시점 - F: Fullbox 시, I: Inspection 시
	 * 
	 * @param batch
	 * @return
	 */
	public static String getBoxResultSendPoint(JobBatch batch) {
		// job.cmm.box.result.report.point		
		return null;
	}
	
	/**
	 * 박스 취소 기능 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isBoxResultCancelEnabled(JobBatch batch) {
		// job.cmm.box.cancel.enabled
		return false;
	}
	
	/**
	 * 박스 중량 측정 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isBoxWeightMeasureEnabled(JobBatch batch) {
		// job.cmm.box.weight.enabled	
		return false;
	}
	
	/**
	 * 주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명 - 기본은 class_cd
	 * 
	 * @param batch
	 * @return
	 */
	public static String getBoxOutClassField(JobBatch batch) {
		// job.cmm.box.out.class.field
		return null;
	}
		
	/**
	 * 박스 처리 후 액션 - P : 라벨 출력, T : 거래명세서 출력, N : 액션 없음 
	 * 
	 * @param batch
	 * @return
	 */
	public static String getBoxingAction(JobBatch batch) {
		// job.cmm.box.action
		return null;
	}
	
	/**
	 * 옵션에 따라 송장 번호를 다르게 부여 - BOX_ID : 박스 ID와 송장 번호 동일, BOX_ID_ONLY : 박스 ID, ... 
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInvoiceNoRule(JobBatch batch) {
		// job.cmm.box.invoice-no.rule					
		return null;
	}
	
	/**
	 * 박스 ID 유일성 보장 범위 - G : 도메인 전체 유일, D : 날자별 유일, B : 배치 내 유일
	 * 
	 * @param batch
	 * @return
	 */
	public static String getBoxIdUniqueScope(JobBatch batch) {
		// job.cmm.box.box_id.unique.scope				
		return null;
	}
	
	/**
	 * 확정 취소 기능 활성화 여부 
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isUndoPickedEnabled(JobBatch batch) {
		// job.cmm.pick.cancel.enabled
		return false;
	}
	
	/**
	 * 확정 실적 보고 여부 
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isPickResultEnabled(JobBatch batch) {
		// job.cmm.pick.result.report.enabled
		return false;
	}
	
	/**
	 * 표시기에서 분류 작업 취소시에 '취소' 상태로 관리할 지 여부 
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isPickCancelStatusEnabled(JobBatch batch) {
		// job.cmm.pick.cancel.status.enabled
		return false;
	}

	/**
	 * 상품 투입시 취소된 상품을 조회할 지 여부 
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isCanceledSkuInputEnabled(JobBatch batch) {
		// job.cmm.pick.include.cancelled.enabled
		return false;
	}

	/**
	 * 라벨 발행시 한 번에 동일 라벨을 몇 장 발행할 지 설정 
	 * 
	 * @param batch
	 * @return
	 */
	public static int getLabelPrintCountAtOnce(JobBatch batch) {
		// job.cmm.label.print.count
		return 0;
	}

	/**
	 * 송장 라벨 발행 방법 - S: 송장라벨 자체발행, I: 인터페이스로 발행, N: 발행안 함
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInvoiceIssueMethod(JobBatch batch) {
		// job.cmm.label.print.method
		return null;
	}

	/**
	 * 송장 라벨을 자체 출력시 송장 라벨 출력 템플릿 설정 MPS 매니저 > 개발자 > 커스텀 템플릿에 등록된 송장 라벨 명칭을 설정하면 됨
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInvoiceLabelTemplate(JobBatch batch) {
		// job.cmm.label.template						
		return null;
	}
	
	/**
	 * 사용 디바이스 리스트
	 * 
	 * @param batch
	 * @return
	 */
	public static String[] getDeviceList(JobBatch batch) {
		// job.cmm.device.list
		return null;
	}
	
	/**
	 * 디바이스의 작업 위치 (앞,뒤,앞/뒤,전체 등) 정보를 사용할 지 여부
	 * TODO 장비 설정에 있어야 할 듯 -> 여기서 뺄 지 결정
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isUseCellSideAtDevice(JobBatch batch) {
		// job.cmm.device.side.enabled
		return false;
	}

	/**
	 * 디바이스의 작업 영역 정보를 사용할 지 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isUseStationAtDevice(JobBatch batch) {
		// job.cmm.device.station.enabled
		return false;
	}
	
	/**
	 * 출고 검수 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isInspectionEnabled(JobBatch batch) {
		// job.cmm.inspection.enabled
		return false;
	}
	
	/**
	 * 중량 검수 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isWeightInspectionEnabled(JobBatch batch) {
		// job.cmm.insepction.weight.enabled
		return false;
	}

	/**
	 * 출고 검수 후 액션. 아래 출고 검수 활성화 시에만 의미가 있음.
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInspectionAction(JobBatch batch) {
		// job.cmm.inspection.action					
		return null;
	}

	/**
	 * 투입시 투입 범위
	 * 	- station : 작업 존 별 투입
	 * 	- rack : 호기별 투입
	 * 	- batch : 배치별 투입
	 * 	- batch_group : 배치 그룹별 투입
	 * 
	 * @param batch
	 * @return
	 */
	public static String getInputWorkScope(JobBatch batch) {
		// job.cmm.input.work_scope
		return null;
	}
	
	/**
	 * 단품 투입 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isSingleSkuInputEnabled(JobBatch batch) {
		// job.cmm.input.mode.single.enabled			
		return false;
	}
	
	/**
	 * 번들 투입 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isBundleInputEnabled(JobBatch batch) {
		// job.cmm.input.mode.bundle.enabled
		return false;
	}
	
	/**
	 * 완박스 투입 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isSingleBoxInputEnabled(JobBatch batch) {
		// job.cmm.input.mode.box.enabled
		return false;
	}
	
	/**
	 * job.cmm.input.mode.single.enabled이 true인 경우에 단품 투입시 하나씩 표시기에 점등할 것인지 전체 표시기에 점등할 것인지 설정
	 *  - single : 상품 하나 스캔시 하나 표시기 점등
	 *  - all : 상품 하나 스캔시 모든 셀의 표시기 점등
	 * 
	 * @param batch
	 * @return
	 */
	public static String getIndOnModeWhenSkuInput(JobBatch batch) {
		// job.cmm.input.single.ind_on.mode					
		return null;
	}
	
	/**
	 * job.cmm.input.mode.box.enabled이 true인 경우에  완박스 투입시 하나씩 표시기에 점등할 것인지 전체 표시기에 점등할 것인지 설정
	 *  - single : 상품 하나 스캔시 하나 표시기 점등
	 *  - all : 상품 하나 스캔시 모든 셀의 표시기 점등
	 * 
	 * @param batch
	 * @return
	 */
	public static String getIndOnModeWhenSingleBoxInput(JobBatch batch) {
		// job.cmm.input.box.ind_on.mode									
		return null;
	}
	
	/**
	 * 주문 취소시 데이터 삭제 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isDeleteWhenOrderCancel(JobBatch batch) {
		// job.cmm.order.delete.when.order_cancel											
		return false;
	}
	
	/**
	 * 작업지시 시점에 표시기에 할당 셀 표시 활성화 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isIndOnAssignedCellWhenInstruction(JobBatch batch) {
		// job.cmm.assigned-cell.indicator.enabled									
		return false;
	}

	/**
	 * 거래명세서 템플릿 이름을 설정
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean getTradeStatmentTemplate(JobBatch batch) {
		// job.cmm.trade-statement.template												
		return false;
	}
	
}
