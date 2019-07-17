package xyz.anythings.base;

import java.util.List;

import xyz.anythings.base.entity.JobProcess;
import xyz.anythings.sys.AnyConstants;
import xyz.elidom.sys.util.SettingUtil;
import xyz.elidom.util.ValueUtil;

/**
 * 물류 마스터 모듈 상수 정의
 * 
 * @author shortstop
 */
public class LogisBaseConstants extends AnyConstants {

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
	 * MPS Job Type : DAS
	 */
	public static final String JOB_TYPE_DAS = "DAS";
	/**
	 * MPS Job Type : DAS 2차 분류 타입
	 */
	public static final String JOB_TYPE_DAS2 = "DAS2";
	/**
	 * MPS Job Type : DAS 2차 분류 싱글 슈트 타입
	 */
	public static final String JOB_TYPE_DAS3 = "DAS3";
	/**
	 * MPS Job Type : RTN
	 */
	public static final String JOB_TYPE_RTN = "RTN";
	/**
	 * MPS Job Type : RTN3 (무오더 반품)
	 */
	public static final String JOB_TYPE_RTN3 = "RTN3";
	/**
	 * MPS Job Type : DPS
	 */
	public static final String JOB_TYPE_DPS = "DPS";
	/**
	 * MPS Job Type : DPS 2차 분류 타입
	 */
	public static final String JOB_TYPE_DPS2 = "DPS2";
	/**
	 * MPS Job Type : QPS
	 */
	public static final String JOB_TYPE_QPS = "QPS";
	
	/**
	 * 작업 상태 - PICKING, FINISH
	 */
	public static final List<String> JOB_STATUS_PF = ValueUtil.newStringList(JobProcess.JOB_STATUS_PICKING, JobProcess.JOB_STATUS_FINISH);
	/**
	 * 작업 상태 - WAIT, INPUT, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIC = ValueUtil.newStringList(JobProcess.JOB_STATUS_WAIT, JobProcess.JOB_STATUS_INPUT, JobProcess.JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - WAIT, INPUT, PICKING, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIPC = ValueUtil.newStringList(JobProcess.JOB_STATUS_WAIT, JobProcess.JOB_STATUS_INPUT, JobProcess.JOB_STATUS_PICKING, JobProcess.JOB_STATUS_CANCEL);
	/**
	 * 작업 상태 - WAIT, INPUT, PICKING, FINISH, CANCEL
	 */
	public static final List<String> JOB_STATUS_WIPFC = ValueUtil.newStringList(JobProcess.JOB_STATUS_WAIT, JobProcess.JOB_STATUS_INPUT, JobProcess.JOB_STATUS_PICKING, JobProcess.JOB_STATUS_FINISH, JobProcess.JOB_STATUS_CANCEL);

	
	/**
	 * 로케이션 단위의 작업 박싱 완료 상태 : BOXED
	 */
	public static final String LOCATION_JOB_STATUS_BOXED = "BOXED";
	/**
	 * 로케이션 단위의 작업 완료 후 Fullbox가 필요한 상태 : ENDING
	 */
	public static final String LOCATION_JOB_STATUS_END = "ENDING";
	/**
	 * 로케이션 단위의 작업 최종 완료 상태 : ENDED
	 */
	public static final String LOCATION_JOB_STATUS_ENDED = "ENDED";
	/**
	 * 로케이션 단위의 작업 최종 완료, 완료 중 상태
	 */
	public static final List<String> LOCATION_JOB_STATUS_END_LIST = ValueUtil.newStringList(LOCATION_JOB_STATUS_BOXED, LOCATION_JOB_STATUS_END);
		
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
	 * 호기 사이드 코드 명 - REGION_SIDE
	 */
	public static final String CODE_NAME_REGION_SIDE = "REGION_SIDE";
	
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
	 * 작업 유형이 DPS2 작업 타입인지 체크
	 * 
	 * @param jobType
	 * @return
	 */
	public static boolean isDps2JobType(String jobType) {
		return ValueUtil.isEqualIgnoreCase(JOB_TYPE_DPS2, jobType);
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
		return isDpsJobType(jobType) || isQpsJobType(jobType) || isDps2JobType(jobType);
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
			if(ValueUtil.isNotEmpty(sideCd) && (ValueUtil.isEqualIgnoreCase(sideCd, AnyConstants.NULL_STRING) || ValueUtil.isEqualIgnoreCase(sideCd, SIDE_TOTAL) || ValueUtil.isEqualIgnoreCase(sideCd, AnyConstants.ALL_CAPITAL_STR))) {
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
		return ValueUtil.toBoolean(SettingUtil.getValue(domainId, LogisBaseConfigConstants.MPS_MOBILE_SIDE_CD_ENABLED, AnyConstants.FALSE_STRING));
	}
}
