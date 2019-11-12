package xyz.anythings.base.service.util;

/**
 * 스테이지 범위 내 작업 설정 값 조회 유틸리티
 * 설정 항목 리스트
 * 
 * - parent.system.if.iftype					상위 시스템 인터페이스 유형 (datasource / dblink / if-table)
 * - parent.system.if.dblink.name				상위 시스템 I/F를 위한 DB Link 명
 * - parent.system.if.datasource.name			상위 시스템 데이터소스 명
 * - parent.system.if.company.table				상위 시스템 고객사 I/F 테이블 명
 * - parent.system.if.sku.table					상위 시스템 SKU I/F 테이블 명
 * - parent.system.if.shop.table				상위 시스템 매장 I/F 테이블 명
 * - parent.system.if.boxtype.table				상위 시스템 박스 유형 I/F 테이블 명 
 * - parent.system.if.order.table				상위 시스템 주문 I/F 테이블 명
 * - parent.system.if.pick-result.table			상위 시스템 피킹 실적 I/F 테이블 명
 * - parent.system.if.totalpicking.table		상위 시스템 토털 피킹 I/F 테이블 명
 * - parent.system.if.company.procedure			상위 시스템 고객사 I/F 프로시져 명
 * - parent.system.if.sku.procedure				상위 시스템 SKU I/F 프로시져 명
 * - parent.system.if.shop.procedure			상위 시스템 매장 I/F 프로시져 명
 * - parent.system.if.boxtype.procedure			상위 시스템 박스 유형 I/F 프로시져 명
 * - parent.system.if.order.procedure			상위 시스템 주문 I/F 프로시져 명
 * - parent.system.if.pick-result.procedure		상위 시스템 피킹 실적 I/F 프로시져 명
 * - parent.system.if.box-result.procedure		상위 시스템 박스 실적 I/F 프로시져 명
 * - parent.system.if.totalpicking.procedure	상위 시스템 토털 피킹 I/F 프로시져 명
 * 
 * - cmm.equip.status.report.ignore.flag		설비 이벤트 무시할 지 여부
 * - cmm.receive.logging.enabled				미들웨어 접수 메시지를 로깅할 지 여부
 * - cmm.ind.stock.adjustment.color				재고 실사시 버튼 색상
 * - cmm.ind.button.default.color				표시기 버튼 기본 색상
 * 
 * - cmm.sku.barcode.max.length					스테이지 공통 - 바코드 최대 길이
 * - cmm.sku.search.code.fields					스테이지 공통 - 상품 조회를 위한 코드 필드명 리스트
 * - cmm.box.out.class.field					스테이지 공통 - 주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명
 * - cmm.cell.mapping.target.field				스테이지 공통 - 기본 셀 매핑 대상 필드 명
 * - cmm.indicator.type  						스테이지 공통 - 표시기 유형 (통신 프로토콜 기준)
 * - cmm.order.delete.when.order_cancel			스테이지 공통 - 주문 취소시 데이터 삭제 여부
 * - cmm.order.ordergroup.field					스테이지 공통 - 주문 그룹 필드로 사용할 주문 테이블의 필드명
 * 
 * - das.sku.barcode.max.length					DAS 바코드 최대 길이
 * - das.sku.search.code.fields					DAS 상품 조회를 위한 코드 필드명 리스트
 * - das.box.out.class.field					DAS 주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명
 * - das.cell.mapping.target.field				DAS 기본 셀 매핑 대상 필드 명
 * - das.indicator.type  						DAS 표시기 유형 (통신 프로토콜 기준)
 * - das.order.delete.when.order_cancel			DAS 주문 취소시 데이터 삭제 여부
 * - das.order.ordergroup.field					DAS 주문 그룹 필드로 사용할 주문 테이블의 필드명
 * 
 * - rtn.sku.barcode.max.length					반품 바코드 최대 길이
 * - rtn.sku.search.code.fields					반품 상품 조회를 위한 코드 필드명 리스트
 * - rtn.box.out.class.field					반품 주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명
 * - rtn.cell.mapping.target.field				반품 기본 셀 매핑 대상 필드 명
 * - rtn.indicator.type  						반품 표시기 유형 (통신 프로토콜 기준)
 * - rtn.order.delete.when.order_cancel			반품 주문 취소시 데이터 삭제 여부
 * - rtn.order.ordergroup.field					반품 주문 그룹 필드로 사용할 주문 테이블의 필드명
 * 
 * - dps.sku.barcode.max.length					DPS 바코드 최대 길이
 * - dps.sku.search.code.fields					DPS 상품 조회를 위한 코드 필드명 리스트
 * - dps.box.out.class.field					DPS 주문 필드 중에 박스 처리시에 출고 분류 코드로 사용할 필드 명
 * - dps.cell.mapping.target.field				DPS 기본 셀 매핑 대상 필드 명
 * - dps.indicator.type  						DPS 표시기 유형 (통신 프로토콜 기준)
 * - dps.order.delete.when.order_cancel			DPS 주문 취소시 데이터 삭제 여부
 * - dps.order.ordergroup.field					DPS 주문 그룹 필드로 사용할 주문 테이블의 필드명
 * - dps.batch.split-by-rack.enabled			DPS 배치 분리 여부
 * - dps.sku.popula.rank.calc.days				DPS SKU 물량 Rank 선정 기준 데이터 범위 (일자)
 * - dps.station.wait-pool.count				DPS 작업 스테이션에 대기할 박스 최대 개수 (-1이면 무한대)
 * - dps.supple.recommend-cell.enabled			DPS 추천 로케이션 사용 여부
 * 
 * @author shortstop
 */
public class StageJobConfigUtil {
	
}
