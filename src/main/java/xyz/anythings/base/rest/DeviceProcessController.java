package xyz.anythings.base.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import xyz.anythings.base.entity.SKU;
import xyz.elidom.orm.system.annotation.service.ApiDesc;
import xyz.elidom.orm.system.annotation.service.ServiceDesc;
import xyz.elidom.sys.SysConstants;
import xyz.elidom.sys.util.ValueUtil;

/**
 * 작업자 디바이스와의 인터페이스 API
 * 
 * @author shortstop
 */
@RestController
@Transactional
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/rest/device_process")
@ServiceDesc(description = "Device Process Controller API")
public class DeviceProcessController {

	/**
	 * 장비 업데이트 하라는 메시지를 장비 타입별로 publish
	 * 
	 * @param deviceType
	 * @return
	 */
	@RequestMapping(value = "/publish/device_update/{device_type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Publish device update message")
	public Map<String, Object> publishDeviceUpdate(@PathVariable("device_type") String deviceType) {
		return ValueUtil.newMap("result", SysConstants.OK_STRING);
	}
	
	/**
	 * 업데이트 버전의 릴리즈 노트를 조회
	 * 
	 * @param deviceType
	 * @return
	 */
	@RequestMapping(value = "/release_notes/{device_type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Release notes of device type")
	public List<String> releaseNotesOfDevice(@PathVariable("device_type") String deviceType) {
		List<String> releaseNotes = ValueUtil.toList("1. 디자인 테마 변경");
		releaseNotes.add("2. 메뉴 아이콘 변경");
		releaseNotes.add("3. 모든 엔티티에 대해서 단 건 조회, 리스트 조회, 페이지네이션, 마스터 디테일 구조의 디테일 리스트 조회 공통 유틸리티 추가");
		releaseNotes.add("4. Fixed : 디바이스 업데이트 시 오류 제거");
		return releaseNotes;
	}

	/**
	 * 장비 타입별로 전달할 메시지를 publish
	 * 
	 * @param deviceType
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/publish/message/{device_type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Publish device update message")
	public Map<String, Object> publishDeviceMessage(@PathVariable("device_type") String deviceType, @RequestBody String message) {
		return ValueUtil.newMap("result", SysConstants.OK_STRING);
	}
	
	/**
	 * 분류 처리를 위한 설비 유형, 설비 코드 및 상품 코드로 상품 조회
	 * 
	 * @param equipType
	 * @param equipCd
	 * @param skuCd
	 * @return
	 */
	@RequestMapping(value = "/search/sku/{equip_type}/{equip_cd}/{sku_cd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiDesc(description = "Search sku for cliassification")
	protected List<SKU> searchSkuForClassify(@PathVariable("equip_type") String equipType, @PathVariable("equip_cd") String equipCd, @PathVariable("sku_cd") String skuCd) {
		return null;
	}

}
