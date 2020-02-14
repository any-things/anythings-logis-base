package xyz.anythings.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.JobConfig;
import xyz.anythings.base.service.api.IDeviceService;

/**
 * 모바일 장비 요청을 처리하는 서비스 API 기본 구현
 * 
 * @author shortstop
 */
@Component
public class DeviceService extends AbstractLogisService implements IDeviceService {

	@Override
	public List<JobConfig> searchDeviceSettings(Long domainId, String deviceType, String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDeviceSettings(Long domainId, String deviceType, String deviceId, List<JobConfig> deviceSettings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageToDevice(Long domainId, String equipType, String equipCd, String stationCd, String notiType,
			String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageToDevice(Long domainId, String deviceType, String equipType, String equipCd,
			String stationCd, String notiType, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> searchUpdateItems(Long domainId, String deviceType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDeviceUpdateMessage(Long domainId, String deviceType) {
		// TODO Auto-generated method stub
		
	}

}
