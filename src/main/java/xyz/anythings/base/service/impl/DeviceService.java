package xyz.anythings.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import xyz.anythings.base.LogisConstants;
import xyz.anythings.base.entity.DeviceConf;
import xyz.anythings.base.entity.DeviceProfile;
import xyz.anythings.base.entity.Kiosk;
import xyz.anythings.base.entity.PDA;
import xyz.anythings.base.entity.Tablet;
import xyz.anythings.base.event.IDeviceEvent;
import xyz.anythings.base.event.device.DeviceEvent;
import xyz.anythings.base.model.IDevice;
import xyz.anythings.base.service.api.IDeviceService;
import xyz.anythings.gw.GwConstants;
import xyz.anythings.gw.service.mq.MqSender;
import xyz.anythings.gw.service.mq.model.device.DeviceCommand;
import xyz.anythings.gw.service.mq.model.device.DeviceCommandFactory;
import xyz.anythings.gw.service.util.MwMessageUtil;
import xyz.anythings.sys.util.AnyOrmUtil;
import xyz.elidom.dbist.dml.Query;
import xyz.elidom.rabbitmq.message.MessageProperties;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.entity.Setting;
import xyz.elidom.util.ValueUtil;

/**
 * 모바일 장비 요청을 처리하는 서비스 API 기본 구현
 * 
 * @author shortstop
 */
@Component
public class DeviceService extends AbstractLogisService implements IDeviceService {

	/**
	 * 메시지 센더
	 */
	@Autowired
	private MqSender mqSender;
	
	@Override
	public List<DeviceConf> searchDeviceSettings(Long domainId, String stageCd, String deviceType, String deviceId) {
		
		Query condition = AnyOrmUtil.newConditionForExecution(domainId);
		condition.addFilter("stageCd", stageCd);
		Class<?> clazz = null;
		
		if(ValueUtil.isEqualIgnoreCase(deviceType, LogisConstants.DEVICE_KIOSK)) {
			condition.addFilter("kioskCd", deviceId);
			clazz = Kiosk.class;
			
		} else if(ValueUtil.isEqualIgnoreCase(deviceType, LogisConstants.DEVICE_TABLET)) {
			condition.addFilter("tabletCd", deviceId);
			clazz = Tablet.class;
			
		} else if(ValueUtil.isEqualIgnoreCase(deviceType, LogisConstants.DEVICE_PDA)) {
			condition.addFilter("pdaCd", deviceId);
			clazz = PDA.class;
		}
		
		IDevice device = (IDevice)this.queryManager.selectByCondition(clazz, condition);
		
		if(device == null) {
			return null;
			
		} else {
			condition = AnyOrmUtil.newConditionForExecution(domainId);
			condition.addFilter("stageCd", device.getStageCd());
			condition.addFilter("deviceType", deviceType);
			condition.addFilter("equipType", device.getEquipType());
			condition.addFilter("equipCd", device.getEquipCd());
			DeviceProfile deviceProfile = this.queryManager.selectByCondition(DeviceProfile.class, condition);
			
			if(deviceProfile == null) {
				condition.removeFilter("equipType");
				condition.removeFilter("equipCd");
				deviceProfile = this.queryManager.selectByCondition(DeviceProfile.class, condition);
				
				if(deviceProfile == null) {
					condition.removeFilter("deviceType");
					condition.addFilter("defaultFlag", true);
					deviceProfile = this.queryManager.selectByCondition(DeviceProfile.class, condition);
				}
			}
			
			condition = AnyOrmUtil.newConditionForExecution(domainId);
			condition.addSelect("name", "description", "value");
			condition.addFilter("deviceProfileId", deviceProfile.getId());
			List<DeviceConf> settings = this.queryManager.selectList(DeviceConf.class, condition);
			
			condition = AnyOrmUtil.newConditionForExecution(domainId);
			condition.addFilter("category", "device");
			List<Setting> deviceSettings = this.queryManager.selectList(Setting.class, condition);
			
			for(Setting deviceSetting : deviceSettings) {
				DeviceConf conf = ValueUtil.populate(deviceSetting, new DeviceConf());
				settings.add(conf);
			}
			
			return settings;
		}
	}

	@Override
	public void sendDeviceSettings(Long domainId, String deviceType, String deviceId, List<DeviceConf> deviceSettings) {
		DeviceEvent event = new DeviceEvent(domainId, DeviceCommand.COMMAND_SETTING, null, deviceType, deviceId, null);
		event.setSendData(deviceSettings);
		this.eventPublisher.publishEvent(event);
	}

	@Override
	public void sendMessageToDevice(Long domainId, String stageCd, String equipType, String equipCd, String stationCd, String sideCd, String jobType, String command, String message, Object sendData) {
		DeviceEvent event = new DeviceEvent(domainId, command, message, stageCd, equipType, equipCd, stationCd, sideCd, jobType);
		if(sendData != null) {
			event.setSendData(sendData);
		}
		
		this.eventPublisher.publishEvent(event);
	}

	@Override
	public void sendMessageToDevice(Long domainId, String deviceType, String deviceId, String jobType, String command, String message, Object sendData) {
		DeviceEvent event = new DeviceEvent(domainId, command, message, deviceType, deviceId, jobType);
		if(sendData != null) {
			event.setSendData(sendData);
		}
		
		this.eventPublisher.publishEvent(event);
	}

	@Override
	public List<String> searchUpdateItems(Long domainId, String deviceType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDeviceUpdateMessage(Long domainId, String deviceType) {
		List<String> updateItems = this.searchUpdateItems(domainId, deviceType);
		DeviceEvent event = new DeviceEvent(domainId, DeviceCommand.COMMAND_SETTING, null, deviceType, null, null);
		event.setSendData(updateItems);
		this.eventPublisher.publishEvent(event);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, classes = IDeviceEvent.class)	
	public void receiveDeviceEvent(IDeviceEvent event) {
		String vHost = this.mqSender.getVirtualHost(event.getDomainId());
		String destId = this.getDeviceDestId(vHost, event.getDeviceType(), event.getEquipType(), event.getEquipCd(), event.getStageCd(), event.getSideCd());
		MessageProperties props = MwMessageUtil.newMessageProp(event.getStageCd(), destId, false);
		DeviceCommand eqEvent = DeviceCommandFactory.createDeviceCommand(event.getDeviceType(), event.getEquipType(), event.getEquipCd(), event.getStationCd(), event.getSideCd(), event.getJobType(), event.getCommand(), props);
		this.mqSender.send(event.getStageCd(), vHost, props, eqEvent);
	}

	/**
	 * 목적지 ID 생성
	 * 
	 * @param virtualHost
	 * @param deviceType
	 * @param equipType
	 * @param equipCd
	 * @param stationCd
	 * @param sideCd
	 * @return
	 */
	private String getDeviceDestId(String virtualHost, String deviceType, String equipType, String equipCd, String stationCd, String sideCd) {
		StringBuffer destId = new StringBuffer();
		destId.append(virtualHost)
			  .append(SysConstants.SLASH)
			  .append(deviceType)
			  .append(SysConstants.SLASH)
			  .append(equipType)
			  .append(SysConstants.SLASH)
			  .append(equipCd);
		
		if(ValueUtil.isNotEmpty(stationCd)) {
			destId.append(SysConstants.SLASH).append(stationCd);
		} else {
			destId.append(SysConstants.SLASH).append(GwConstants.ALL_STRING);
		}
		
		if(ValueUtil.isNotEmpty(sideCd)) {
			destId.append(SysConstants.SLASH).append(sideCd);
		} else {
			destId.append(SysConstants.SLASH).append(GwConstants.ALL_STRING);
		}
		
		return destId.toString();
	}

}
