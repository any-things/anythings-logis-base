package xyz.anythings.base.service.util;

import xyz.anythings.base.entity.JobBatch;

/**
 * 작업 배치 범위 내 표시기 설정 값 조회 유틸리티
 * 표시기 설정 리스트
 *  - ind.action.delay.before.on				표시기가 점등되기 전 지연되는 시간입니다. (100ms 단위)
 *  - ind.action.delay.cancel.button.off		표시기의 취소 버튼을 눌렀을 때 표시기가 소등되기까지의 지연 시간입니다. (100ms)
 *  - ind.action.send.off.ack.already.off		표시기가 이미 소등된 상태에서 소등 요청을 받았을 때 응답 메시지를 보낼 지 여부입니다.
 *  - ind.action.show.string.before.on			다음 작업을 점등하기 전에 표시될 문자열입니다.
 *  - ind.action.show.string.delay.before.on	점등 전에 문자를 표시할 시간입니다. (100ms 단위)
 *  - ind.action.status.report.interval			표시기의 상태 보고 주기입니다. (초 단위)
 *  - ind.job.color.rotation.seq				표시기 버튼 색상의 로테이션 순서입니다.
 *  - ind.job.color.stocktaking					재고 실사 작업에서 표시기 버튼의 기본 색상입니다.
 *  - ind.job.segment.roles.on					작업 점등 시 각 세그먼트가 나타낼 정보입니다 - 첫번째/두번째/세번째 세그먼트 역할 -> R(릴레이 순서)/B(Box)/P(PCS)
 *  - ind.show.segment1.mapping.role			표시 세그먼트의 첫번째 숫자와 매핑되는 역할 (T: 총 수량, R: 총 남은 수량, F: 총 처리한 수량, P: 방금 전 처리한 낱개 수량, B: 방금 전 처리한 박스 수량)
 *  - ind.show.segment2.mapping.role			표시 세그먼트의 두번째 숫자와 매핑되는 역할
 *  - ind.show.segment3.mapping.role			표시 세그먼트의 세번째 숫자와 매핑되는 역할
 *  - ind.show.relay.max.no						최대 릴레이 번호 
 *  - ind.show.button.blink.interval			표시기의 버튼이 깜빡이는 주기입니다. (100ms 단위)
 *  - ind.show.button.on.mode					표시기의 버튼이 점등되는 방식입니다. (B: 깜빡임, S: 항상 켜짐)
 *  - ind.show.fullbox.button.blink				Full Box 처리 후, 표시기가 Full Box 상태로 점등됐을 때 버튼이 깜빡일지 여부입니다.
 *  - ind.show.view-type						표시기 자체적으로 표시 형식을 변경하는 모드
 *  - ind.show.number.alignment					표시기 숫자의 정렬 방향입니다. (R / L)
 *  - ind.led.blink.interval					LED 바가 깜박이는 주기입니다. (100ms 단위)
 *  - ind.led.brightness						LED 바의 밝기입니다. (1~10)
 *  - ind.led.on.mode							LED 바가 점등되는 방식입니다. (B: 깜빡임, S: 항상 켜짐)
 *  - ind.led.use.enabled						LED 바를 사용할지 여부입니다.
 *  - ind.led.use.enabled.racks					LED 바를 사용할 호기 리스트 (콤마로 구분)
 *  - ind.buttons.enable						표시기 버튼 사용 여부
 *  - ind.latest.release.version				표시기 최신 버전 정보 설정
 *  - ind.gw.latest.release.version				Gateway 최신 버전 정보 설정
 * 
 * @author shortstop
 */
public class BatchIndConfigUtil {

	/**
	 * 표시기가 점등되기 전 지연되는 시간입니다. (100ms 단위)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getIndOnDelayTime(JobBatch batch) {
		// ind.action.delay.before.on				
		return 0;
	}

	/**
	 * 표시기의 취소 버튼을 눌렀을 때 표시기가 소등되기까지의 지연 시간입니다. (100ms)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getIndOnDelayTimeCancelPushed(JobBatch batch) {
		// ind.action.delay.cancel.button.off
		return 0;
	}

	/**
	 * 표시기가 이미 소등된 상태에서 소등 요청을 받았을 때 응답 메시지를 보낼 지 여부입니다.
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isNoackWhenAlreadyOffEnabled(JobBatch batch) {
		// ind.action.send.off.ack.already.off
		return false;
	}
	
	/**
	 * 다음 작업을 점등하기 전에 표시될 문자열입니다.
	 * 
	 * @param batch
	 * @return
	 */
	public static String getDisplayStringBeforeIndOn(JobBatch batch) {
		// ind.action.show.string.before.on
		return null;
	}

	/**
	 * 점등 전에 문자를 표시할 시간입니다. (100ms 단위)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getDisplayIntervalBeforeIndOn(JobBatch batch) {
		// ind.action.show.string.delay.before.on	
		return 0;
	}

	/**
	 * 표시기의 상태 보고 주기입니다. (초 단위)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getIndStateReportInterval(JobBatch batch) {
		// ind.action.status.report.interval
		return 0;
	}

	/**
	 * 표시기 버튼 색상의 로테이션 순서입니다.
	 * 
	 * @param batch
	 * @return
	 */
	public static String[] getIndButtonColorForRotation(JobBatch batch) {
		// ind.job.color.rotation.seq
		return null;
	}
	
	/**
	 * 재고 실사 작업에서 표시기 버튼의 기본 색상입니다.
	 * TODO - 스테이지 범위의 표시기 설정으로 이동 필요
	 * 
	 * @param batch
	 * @return
	 */
	public static String getIndColorForStocktaking(JobBatch batch) {
		// ind.job.color.stocktaking				
		return null;
	}
	
	/**
	 * 세그먼트 사용 개수
	 * 
	 * @param batch
	 * @return
	 */
	public static int getSegmentCount(JobBatch batch) {
		return getSegmentRoles(batch).length;
	}
	
	/**
	 * 작업 점등 시 각 세그먼트가 나타낼 정보입니다 - 첫번째/두번째/세번째 세그먼트 역할 -> R(릴레이 순서)/B(Box)/P(PCS)
	 * 
	 * @param batch
	 * @return
	 */
	public static String[] getSegmentRoles(JobBatch batch) {
		// ind.job.segment.roles.on								
		return null;
	}
	
	/**
	 * 표시 세그먼트의 첫 번째 숫자와 매핑되는 역할 (T: 총 수량, R: 총 남은 수량, F: 총 처리한 수량, P: 방금 전 처리한 낱개 수량, B: 방금 전 처리한 박스 수량)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSegment1RoleForDisplay(JobBatch batch) {
		// ind.show.segment1.mapping.role
		return null;
	}

	/**
	 * 표시 세그먼트의 두 번째 숫자와 매핑되는 역할 (T: 총 수량, R: 총 남은 수량, F: 총 처리한 수량, P: 방금 전 처리한 낱개 수량, B: 방금 전 처리한 박스 수량)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSegment2RoleForDisplay(JobBatch batch) {
		// ind.show.segment2.mapping.role
		return null;
	}

	/**
	 * 표시 세그먼트의 세 번째 숫자와 매핑되는 역할 (T: 총 수량, R: 총 남은 수량, F: 총 처리한 수량, P: 방금 전 처리한 낱개 수량, B: 방금 전 처리한 박스 수량)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getSegment3RoleForDisplay(JobBatch batch) {
		// ind.show.segment3.mapping.role
		return null;
	}
	
	/**
	 * 최대 릴레이 번호
	 * 
	 * @param batch
	 * @return
	 */
	public static String getMaxRelayNo(JobBatch batch) {
		// ind.show.relay.max.no
		return null;
	}
	
	/**
	 * 표시기의 버튼이 깜빡이는 주기입니다. (100ms 단위)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getButtonBlinkInterval(JobBatch batch) {
		// ind.show.button.blink.interval			
		return null;
	}
	
	/**
	 * 표시기의 버튼이 점등되는 방식입니다. (B: 깜빡임, S: 항상 켜짐)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getButtonOnMode(JobBatch batch) {
		// ind.show.button.on.mode					
		return null;
	}
	
	/**
	 * 표시기 Full Box 터치시 버튼 깜빡임 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isFullButtonBlink(JobBatch batch) {
		// ind.show.fullbox.button.blink
		return false;
	}
	
	/**
	 * 표시기 자체적으로 표시 형식을 변경하는 모드
	 * 
	 * @param batch
	 * @return
	 */
	public static String getShowViewType(JobBatch batch) {
		// ind.show.view-type
		return null;
	}
	
	/**
	 * 표시기 숫자의 정렬 방향입니다. (R / L)
	 * 
	 * @param batch
	 * @return
	 */
	public static String getShowNumberAlignment(JobBatch batch) {
		// ind.show.number.alignment
		return null;
	}
	
	/**
	 * LED 바가 깜박이는 주기입니다. (100ms 단위)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getLedBlinkInterval(JobBatch batch) {
		// ind.led.blink.interval
		return 0;
	}

	/**
	 * LED 바의 밝기입니다. (1~10)
	 * 
	 * @param batch
	 * @return
	 */
	public static int getLedBrightness(JobBatch batch) {
		// ind.led.brightness						
		return 0;
	}
	
	/**
	 * LED 바를 깜빡이게 할 지 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isLedBlink(JobBatch batch) {
		// ind.show.fullbox.button.blink
		return false;
	}
	
	/**
	 * LED 바를 사용할지 여부입니다.
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isUseLed(JobBatch batch) {
		// ind.led.use.enabled						
		return false;
	}

	/**
	 * LED 바를 사용할 호기 리스트 (콤마로 구분)
	 * 
	 * @param batch
	 * @return
	 */
	public static String[] getRackOfUsingLed(JobBatch batch) {
		// ind.led.use.enabled.racks										
		return null;
	}
		
	/**
	 * 표시기 버튼 사용 여부
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean isUseButton(JobBatch batch) {
		// ind.buttons.enable							
		return false;
	}
	
	/**
	 * 표시기 최신 버전 정보 설정
	 * 
	 * @param batch
	 * @return
	 */
	public static boolean getIndLatestReleaseVersion(JobBatch batch) {
		// ind.latest.release.version				
		return false;
	}
	
	/**
	 * Gateway 최신 버전 정보 설정
	 * 
	 * @param batch
	 * @return
	 */
	public static String getGwLatestReleaseVersion(JobBatch batch) {
		// ind.gw.latest.release.version					
		return null;
	}

}
