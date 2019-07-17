package xyz.anythings.base.service.api;

import java.util.List;

import org.springframework.stereotype.Component;

import xyz.anythings.base.entity.BoxResult;
import xyz.anythings.base.service.model.InspectionByWeight;
import xyz.anythings.base.service.model.InspectionItem;

/**
 * 검수 서비스 API
 * 
 * @author shortstop
 */
@Component
public interface IInspectionService {
	
	/**
	 * 투입 박스 유형 (박스, 버킷)에 따라 박스 조회
	 * 
	 * @param domainId
	 * @param inputType
	 * @param boxId
	 * @return
	 */
	public BoxResult findBoxResultByInputType(Long domainId, String inputType, String boxId);

	/**
	 * 송장 ID로 검수 항목 조회
	 * 
	 * @param box
	 * @param weightInspection 중량 검수 여부
	 * @return
	 */
	public InspectionByWeight searchInspectionItems(BoxResult box, boolean weightInspection);
	
	/**
	 * 송장 ID로 검수 완료
	 * 
	 * @param box
	 * @param boxWeight 박스 무게
	 * @param printerId
	 * @return
	 */
	public BoxResult finishInspection(BoxResult box, Float boxWeight, String printerId);
	
	/**
	 * 송장 분할
	 * 
	 * @param box
	 * @param inspectionItems
	 * @param printerId
	 * @return
	 */
	public BoxResult splitBox(BoxResult box, List<InspectionItem> inspectionItems, String printerId);
	
	/**
	 * 박스 송장 라벨 발행
	 * 
	 * @param box
	 * @param printerId
	 * @return
	 */
	public void printInvoiceLabel(BoxResult box, String printerId);
	
}
