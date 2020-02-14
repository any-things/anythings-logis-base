package xyz.anythings.base.service.api;

import java.util.List;

import xyz.anythings.base.entity.JobConfig;

/**
 * 작업자들이 사용하는 모바일 장비 요청을 처리하는 서비스 API
 * 
 *  1. 모바일 작업자 장비
 *  	1) KIOSK / 태블릿 / PDA 등 장비에서 사용할 설정 리스트 조회
 *  	2) 장비에 장비 설정 사항을 메시지로 전달 
 *  	3) 모바일 장비에 메시지 전송
 *  	4) 모바일 장비 업데이트 내역 보기
 *  	5) 모바일 장비 업데이트 메시지 전송
 *  
 * @author shortstop
 */
public interface IDeviceService {

	/**
	 * 1-1. KIOSK, 태블릿, PDA 등 작업자 장비 설정 정보 조회
	 * 
	 * @param domainId
	 * @param deviceType
	 * @param deviceId
	 * @return
	 */
	public List<JobConfig> searchDeviceSettings(Long domainId, String deviceType, String deviceId);
	
	/**
	 * 1-2. 장비에 장비 설정 사항을 메시지로 전달 
	 * 
	 * @param domainId
	 * @param deviceType
	 * @param deviceId
	 * @param deviceSettings
	 */
	public void sendDeviceSettings(Long domainId, String deviceType, String deviceId, List<JobConfig> deviceSettings);
	
	/**
	 * 1-3. 작업자 모바일 장비에 메시지 전송
	 * 
	 * @param domainId
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param notiType
	 * @param message
	 */
	public void sendMessageToDevice(Long domainId, String equipType, String equipCd, String stationCd, String notiType, String message);
	
	/**
	 * 1-3. 작업자 모바일 장비에 메시지 전송
	 * 
	 * @param domainId
	 * @param deviceType
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param notiType
	 * @param message
	 */
	public void sendMessageToDevice(Long domainId, String deviceType, String equipType, String equipCd, String stationCd, String notiType, String message);
		
	/**
	 * 1-4. 모바일 장비 업데이트 내역 보기
	 * 
	 * @param domainId
	 * @param deviceType
	 * @return
	 */
	public List<String> searchUpdateItems(Long domainId, String deviceType);
	
	/**
	 * 1-5. 모바일 장비 업데이트 메시지 전송
	 * 
	 * @param domainId
	 * @param deviceType
	 */
	public void sendDeviceUpdateMessage(Long domainId, String deviceType);
	
}
