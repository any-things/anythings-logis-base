package xyz.anythings.base;

import java.util.List;

import xyz.anythings.sys.AnyConstants;
import xyz.elidom.sys.util.SettingUtil;
import xyz.elidom.util.ValueUtil;

/**
 * 물류 마스터 모듈 상수 정의
 * 
 * @author shortstop
 */
public class LogisConstants extends AnyConstants {

	/**
	 * 사이드 F : 앞
	 */
	public static final String SIDE_FRONT = "F";	
	/**
	 * 사이드 R : 뒤
	 */
	public static final String SIDE_REAR = "R";
	/**
	 * 사이드 T : 전체
	 */
	public static final String SIDE_TOTAL = "T";

	/**
	 * MPS Job Type : DAS - 일반 DAS
	 */
	public static final String JOB_TYPE_DAS = "DAS";
	/**
	 * MPS Job Type : DAS2 - 소터와 연동하는 2차 분류 DAS
	 */
	public static final String JOB_TYPE_DAS2 = "DAS2";
	/**
	 * MPS Job Type : DAS3 - 단수/단포 DAS
	 */
	public static final String JOB_TYPE_DAS3 = "DAS3";
	/**
	 * MPS Job Type : RTN - 일반 반품 
	 */
	public static final String JOB_TYPE_RTN = "RTN";
	/**
	 * MPS Job Type : RTN2 - 소터와 연동하는 2차 분류 반품
	 */
	public static final String JOB_TYPE_RTN2 = "RTN2";
	/**
	 * MPS Job Type : RTN3 - 무오더 반품
	 */
	public static final String JOB_TYPE_RTN3 = "RTN3";
	/**
	 * MPS Job Type : DPS - 일반 DPS
	 */
	public static final String JOB_TYPE_DPS = "DPS";
	/**
	 * MPS Job Type : DPS2 - 2차 분류 타입 DPS
	 */
	public static final String JOB_TYPE_DPS2 = "DPS2";
	/**
	 * MPS Job Type : DPS3 - 단수/단포 DPS
	 */
	public static final String JOB_TYPE_DPS3 = "DPS3";
	/**
	 * MPS Job Type : QPS
	 */
	public static final String JOB_TYPE_QPS = "QPS";
	
	/**
	 * 공통 대기 상태 : Waiting
	 */
	public static final String COMMON_STATUS_WAIT = "W";
	/**
	 * 공통 완료 상태 : Completed
	 */
	public static final String COMMON_STATUS_FINISHED = "F";
	/**
	 * 공통 진행 상태 : Running
	 */
	public static final String COMMON_STATUS_RUNNING = "R";
	/**
	 * 공통 에러 상태 : Error
	 */
	public static final String COMMON_STATUS_ERROR = "E";
	/**
	 * 공통 취소 상태 : Canceled
	 */
	public static final String COMMON_STATUS_CANCEL = "C";
	
	/**
	 * 작업 대기 상태 : Waiting
	 */
	public static final String JOB_STATUS_WAIT = "W";
	/**
	 * 작업 투입 상태 : Input
	 */
	public static final String JOB_STATUS_INPUT = "I";	
	/**
	 * 작업 피킹 상태 : Picking
	 */
	public static final String JOB_STATUS_PICKING = "P";
	/**
	 * 작업 완료 상태 : Finished
	 */
	public static final String JOB_STATUS_FINISH = "F";
	/**
	 * 박싱 완료 상태 : Boxed
	 */
	public static final String JOB_STATUS_BOXED = "B";
	/**
	 * 검수 완료 상태 : Examinated
	 */
	public static final String JOB_STATUS_EXAMINATED = "E";	
	/**
	 * 실적 보고 완료 상태 : Reported
	 */
	public static final String JOB_STATUS_REPORTED = "R";
	/**
	 * 작업 취소 상태 : Canceled
	 */
	public static final String JOB_STATUS_CANCEL = "C";
	/**
	 * 주문 취소 상태 : Deleted
	 */
	public static final String JOB_STATUS_DELETED = "D";
	
	/**
	 * 작업 상태 - PICKING, FINISH
	 */
	public static final List<String> JOB_STATUS_PF = ValueUtil.newStringList(JOB_STATUS_PICKING, JOB_STATUS_FINISH);
	/**
	 * 작업 상태 - WAIT, INPUT, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIC = ValueUtil.newStringList(JOB_STATUS_WAIT, JOB_STATUS_INPUT, JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - WAIT, INPUT, PICKING, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIPC = ValueUtil.newStringList(JOB_STATUS_WAIT, JOB_STATUS_INPUT, JOB_STATUS_PICKING, JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - INPUT, PICKING, CANCEL
	 */
	public static final List<String> JOB_STATUS_IPC = ValueUtil.newStringList(JOB_STATUS_INPUT, JOB_STATUS_PICKING, JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - WAIT, INPUT, PICKING, FINISH, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIPFC = ValueUtil.newStringList(JOB_STATUS_WAIT, JOB_STATUS_INPUT, JOB_STATUS_PICKING, JOB_STATUS_FINISH, JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - FINISH, BOXED, EXAMINED, REPORTED
	 */
	public static final List<String> JOB_STATUS_FBER = ValueUtil.newStringList(JOB_STATUS_FINISH, JOB_STATUS_BOXED, JOB_STATUS_EXAMINATED, JOB_STATUS_REPORTED);
		
	/**
	 * 로케이션 단위의 작업 박싱 완료 상태 : BOXED
	 */
	public static final String CELL_JOB_STATUS_BOXED = "BOXED";
	/**
	 * 로케이션 단위의 작업 완료 후 Fullbox가 필요한 상태 : ENDING
	 */
	public static final String CELL_JOB_STATUS_END = "ENDING";
	/**
	 * 로케이션 단위의 작업 최종 완료 상태 : ENDED
	 */
	public static final String CELL_JOB_STATUS_ENDED = "ENDED";
	/**
	 * 로케이션 단위의 작업 최종 완료, 완료 중 상태
	 */
	public static final List<String> CELL_JOB_STATUS_END_LIST = ValueUtil.newStringList(CELL_JOB_STATUS_BOXED, CELL_JOB_STATUS_END);
		
	/**
	 * 색상 - RED
	 */
	public static final String COLOR_RED = "R";
	/**
	 * 색상 - GREEN
	 */
	public static final String COLOR_GREEN = "G";
	/**
	 * 색상 - BLUE
	 */
	public static final String COLOR_BLUE = "B";
	/**
	 * 색상 - YELLOW
	 */
	public static final String COLOR_YELLOW = "Y";
	/**
	 * 표시기 색상 리스트
	 */
	public static final List<String> MPI_COLOR_LIST = ValueUtil.newStringList(COLOR_RED, COLOR_GREEN, COLOR_BLUE, COLOR_YELLOW);
	
	/**
	 * 랙 사이드 코드 명 - RACK_SIDE
	 */
	public static final String CODE_NAME_RACK_SIDE = "RACK_SIDE";
	
	/**
	 * 설비 상태 정상
	 */
	public static final String EQUIP_STATUS_OK = "1";
	/**
	 * 설비 상태 고장
	 */
	public static final String EQUIP_STATUS_BREAK_DOWN = "2";

	
	/**
	 * 작업 유형이 DAS 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDasJobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DAS, jobType);
	}
	
	/**
	 * 작업 유형이 DAS 2차분류 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDas2JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DAS2, jobType);
	}
	
	/**
	 * 작업 유형이 DAS 2차 분류 단수/단포 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDas3JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DAS3, jobType);
	}
	
	/**
	 * 작업 유형이 반품 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isRtnJobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_RTN, jobType);
	}
	
	/**
	 * 작업 유형이 2차 분류 반품 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isRtn2JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_RTN2, jobType);
	}
	
	/**
	 * 작업 유형이 무오더 반품 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isRtn3JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_RTN3, jobType);
	}
	
	/**
	 * 작업 유형이 DPS 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDpsJobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DPS, jobType);
	}
	
	/**
	 * 작업 유형이 2차 분류 DPS 작업 타입인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDps2JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DPS2, jobType);
	}
	
	/**
	 * 작업 유형이 단수/단포 DPS 작업 타입인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDps3JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DPS3, jobType);
	}
	
	/**
	 * 작업 유형이 QPS 작업 타입인지 체크 
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isQpsJobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_QPS, jobType);
	}
	
	/**
	 * B2B 작업 타입인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isB2BJobType(String jobType) {
		return !isB2CJobType(jobType);
	}
	
	/**
	 * B2C 작업 타입인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isB2CJobType(String jobType) {
		return isDpsJobType(jobType) || isDps2JobType(jobType) || isDps3JobType(jobType) || isQpsJobType(jobType);
	}
	
	/**
	 * 무오더 작업 유형인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isNoOrderJobType(String jobType) {
		return isRtn3JobType(jobType);
	}
	
	/**
	 * 쿼리에서 sideCd가 ALL이라면 sideCd 조건은 없는 것과 같으므로 null로 바꾼다.
	 *
	 * @param domainId
	 * @param sideCd
	 * @return
	 */
	public static String checkSideCdForQuery(Long domainId, String sideCd) {
		if(isDeviceSideCdEnabled(domainId)) {
			if(ValueUtil.isNotEmpty(sideCd) && (ValueUtil.isEqualIgnoreCase(sideCd, AnyConstants.NULL_STRING) || ValueUtil.isEqualIgnoreCase(sideCd, SIDE_TOTAL) || ValueUtil.isEqualIgnoreCase(sideCd, AnyConstants.ALL_CAP_STRING))) {
				return null;
			} else if(ValueUtil.isEmpty(sideCd)) {
				return null;
			} else {
				return sideCd.toUpperCase();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 모바일 장비에서 SideCd 사용 여부
	 * 
	 * @param domainId
	 * @return
	 */
	public static boolean isDeviceSideCdEnabled(Long domainId) {
		return ValueUtil.toBoolean(SettingUtil.getValue(domainId, LogisConfigConstants.DEVICE_SIDE_CD_ENABLED, AnyConstants.FALSE_STRING));
	}
}
