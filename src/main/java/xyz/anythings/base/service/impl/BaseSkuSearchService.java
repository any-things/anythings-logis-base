//package xyz.anythings.base.service.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.StringJoiner;
//
//import org.springframework.stereotype.Component;
//
//import com.cj.mps.util.MpsSetting;
//
//import xyz.anythings.base.entity.JobBatch;
//import xyz.anythings.base.entity.JobInputSeq;
//import xyz.anythings.base.entity.SKU;
//import xyz.anythings.base.service.api.ISkuSearchService;
//import xyz.anythings.sys.AnyConstants;
//import xyz.anythings.sys.ConfigConstants;
//import xyz.anythings.sys.service.AbstractQueryService;
//import xyz.anythings.sys.util.AnyOrmUtil;
//import xyz.elidom.dbist.dml.Query;
//import xyz.elidom.sys.SysConstants;
//import xyz.elidom.sys.entity.Domain;
//import xyz.elidom.sys.util.SettingUtil;
//import xyz.elidom.sys.util.ThrowUtil;
//import xyz.elidom.util.FormatUtil;
//import xyz.elidom.util.ValueUtil;
//
///**
// * SKU 검색 / 조회 서비스 API 기본 구현
// * 
// * @author shortstop
// */
//@Component("baseSkuSearchService")
//public class BaseSkuSearchService extends AbstractQueryService implements ISkuSearchService {
//
//	@Override
//	public String[] getSkuFieldsToFind(Long domainId) {
//		return MpsSetting.getSkuFieldsToFind(domainId);
//	}
//	
//	@Override
//	public String validateSkuCd(Long domainId, String skuCd) {
//		skuCd = ValueUtil.isEmpty(skuCd) ? skuCd : skuCd.trim();
//		String maxLengthStr = SettingUtil.getValue(ConfigConstants.MPS_BARCODE_MAX_LENGTH);
//		
//		if(ValueUtil.isNotEmpty(maxLengthStr)) {
//			int maxLen = ValueUtil.toInteger(maxLengthStr);
//			skuCd = (skuCd.length() > maxLen) ? skuCd.substring(0, maxLen) : skuCd;
//		}
//		
//		return skuCd;
//	}
//	
//	@Override
//	public List<SKU> searchSkuListForPicking(Long domainId, String comCd, String batchId, String zoneCd, String skuCd) {
//		skuCd = this.validateSkuCd(domainId, skuCd);
//		String[] skuCodeFields = MpsSetting.getSkuFieldsToFind(domainId);
//		Map<String, Object> params = ValueUtil.newMap("domainId,comCd,batchId,zoneCd,status", domainId, comCd,batchId,zoneCd,JobInputSeq.INPUT_STATUS_RUNNING);
//
//		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
//		sql.add("SELECT")
//		   .add(" 	s.id, s.com_cd, s.sku_cd, s.sku_nm, nvl(s.sku_barcd, ' ') as sku_barcd, nvl(s.box_barcd, ' ') as box_barcd, s.box_in_qty, s.sku_wt, s.sku_real_wt")
//		   .add("FROM")
//		   .add("	TB_SKU S INNER JOIN ")
//		   .add("	(SELECT distinct com_cd, sku_cd FROM TB_IF_ORDER WHERE domain_id = :domainId and batch_id =:batchId and com_cd = :comCd and order_id in")
//		   .add("		(select order_id from tb_job_input_seq where domain_id = :domainId and com_cd = :comCd and batch_id = :batchId and zone_cd = :zoneCd and status =:status)")
//		   .add("	) O")
//		   .add("	ON S.COM_CD = O.COM_CD and S.SKU_CD = O.SKU_CD and S.COM_CD = :comCd")
//		   .add("WHERE (");
//
//		int idx = 0;
//		for(String skuCodeField : skuCodeFields) {
//			params.put(skuCodeField, skuCd);
//			sql.add((idx > 0 ? "or" : "") + "  		S." + FormatUtil.toUnderScore(skuCodeField) + " = :" + skuCodeField);
//			idx++;
//		}
//		sql.add(") ORDER BY COM_CD ASC, SKU_CD ASC");
//		return this.queryManager.selectListBySql(sql.toString(), params, SKU.class, 0, 0);
//	}
//	
//	@Override
//	public List<SKU> searchSkuListForMiddleAssorting(Long domainId, String batchGroupId, String skuCd) {
//		skuCd = this.validateSkuCd(domainId, skuCd);
//		String[] skuCodeFields = MpsSetting.getSkuFieldsToFind(domainId);
//		Map<String, Object> params = ValueUtil.newMap("domainId,batchGroupId,status", domainId, batchGroupId, JobBatch.STATUS_RUNNING);
//
//		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
//		sql.add("SELECT")
//		   .add(" 	s.id, s.com_cd, s.sku_cd, s.sku_nm, nvl(s.sku_barcd, ' ') as sku_barcd, nvl(s.box_barcd, ' ') as box_barcd, s.box_in_qty, s.sku_wt, s.sku_real_wt")
//		   .add("FROM")
//		   .add("	TB_SKU S INNER JOIN ")
//		   .add("	(SELECT distinct com_cd, sku_cd FROM TB_IF_ORDER WHERE domain_id = :domainId and batch_id in")
//		   .add("		(select id from tb_job_batch where domain_id = :domainId and batch_group_id = :batchGroupId and status = :status)")
//		   .add("	) O")
//		   .add("	ON S.COM_CD = O.COM_CD and S.SKU_CD = O.SKU_CD")
//		   .add("WHERE (");
//
//		int idx = 0;
//		for(String skuCodeField : skuCodeFields) {
//			params.put(skuCodeField, skuCd);
//			sql.add((idx > 0 ? "or" : "") + "  		S." + FormatUtil.toUnderScore(skuCodeField) + " = :" + skuCodeField);
//			idx++;
//		}
//		sql.add(") ORDER BY COM_CD ASC, SKU_CD ASC");
//		
//		return this.queryManager.selectListBySql(sql.toString(), params, SKU.class, 0, 0);
//	}
//	
//	@Override
//	public List<SKU> searchSkuList(String skuCd) {
//		// 1. skuCd 유효성 체크
//		Long domainId = Domain.currentDomainId();
//		skuCd = this.validateSkuCd(domainId, skuCd);
//		String[] skuCodeFields = MpsSetting.getSkuFieldsToFind(domainId);
//		
//		// 2. MPS에서 skuCd로 조회 
//		List<SKU> skuList = new ArrayList<SKU>();
//		Map<String, Object> params = ValueUtil.newMap("domainId", domainId);
//		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
//		sql.add("SELECT")
//		   .add(" 	s.id, s.com_cd, s.sku_cd, s.sku_nm, nvl(s.sku_barcd, ' ') as sku_barcd, nvl(s.box_barcd, ' ') as box_barcd, s.box_in_qty, s.sku_wt, s.sku_real_wt")
//		   .add("FROM")
//		   .add("	TB_SKU S")
//		   .add("WHERE (com_cd) in (")
//		   .add("		SELECT c.com_cd ")
//		   .add("         FROM tb_company c inner join tb_domain_company dc on c.com_cd = dc.com_cd")
//		   .add("		 WHERE dc.domain_id = :domainId)")
//		   .add("AND (");
//		
//		int idx = 0;
//		for(String skuCodeField : skuCodeFields) {
//			params.put(skuCodeField, skuCd);
//			sql.add((idx > 0 ? "or" : "") + "  		S." + FormatUtil.toUnderScore(skuCodeField) + " = :" + skuCodeField);
//			idx++;
//		}
//		sql.add(") ORDER BY COM_CD ASC, SKU_CD ASC");
//		
//		skuList =  this.queryManager.selectListBySql(sql.toString(), params, SKU.class, 0, 0);
//		
//		// 3. 결과가 없으면 메시지 표시 
//		if(ValueUtil.isEmpty(skuList)) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		// 4. 결과가 있으면 리턴
//		} else {		
//			return skuList;
//		}
//	}
//	
//	@Override
//	public List<SKU> searchSkuList(JobBatch batch, String skuCd) {
//		// 1. skuCd 유효성 체크
//		Long domainId = batch.getDomainId();
//		skuCd = this.validateSkuCd(domainId, skuCd);
//		// 2. MPS에서 skuCd로 조회 
//		List<SKU> skuList = this.searchByCode(batch, skuCd, false);
//		// 3. 결과가 없으면 메시지 표시 
//		if(ValueUtil.isEmpty(skuList)) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		// 4. 결과가 있으면 리턴
//		} else {		
//			return skuList;
//		}
//	}
//	
//	@Override
//	public List<SKU> searchByCode(JobBatch batch, String skuCd, boolean exceptionFlag) {
//		String[] skuCodeFields = MpsSetting.getSkuFieldsToFind(batch.getDomainId());
//		Map<String, Object> params = ValueUtil.newMap("domainId,batchId", batch.getDomainId(), batch.getId());
//
//		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
//		sql.add("SELECT")
//		   .add(" 	s.id, s.com_cd, s.sku_cd, s.sku_nm, nvl(s.sku_barcd, ' ') as sku_barcd, nvl(s.box_barcd, ' ') as box_barcd, s.box_in_qty, s.sku_wt, s.sku_real_wt")
//		   .add("FROM")
//		   .add("	TB_SKU S INNER JOIN ")
//		   .add("	(SELECT distinct com_cd, sku_cd FROM TB_IF_ORDER WHERE domain_id = :domainId and batch_id = :batchId) O")
//		   .add("	ON S.COM_CD = O.COM_CD and S.SKU_CD = O.SKU_CD")
//		   .add("WHERE (");
//
//		int idx = 0;
//		for(String skuCodeField : skuCodeFields) {
//			params.put(skuCodeField, skuCd);
//			sql.add((idx > 0 ? "or" : "") + "  		S." + FormatUtil.toUnderScore(skuCodeField) + " = :" + skuCodeField);
//			idx++;
//		}
//		sql.add(") ORDER BY COM_CD ASC, SKU_CD ASC");
//
//		List<SKU> skuList = this.queryManager.selectListBySql(sql.toString(), params, SKU.class, 0, 0);
//
//		if(ValueUtil.isEmpty(skuList) && exceptionFlag) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		}
//
//		return skuList;
//	}
//	
//	@Override
//	public List<SKU> searchByCode(Long domainId, String comCd, String skuCd, boolean exceptionFlag) {
//		String[] skuCodeFields = MpsSetting.getSkuFieldsToFind(domainId);
//		Map<String, Object> params = ValueUtil.isNotEmpty(comCd) ? ValueUtil.newMap("comCd", comCd) : new HashMap<String, Object>(1);
//
//		StringJoiner sql = new StringJoiner(AnyConstants.LINE_SEPARATOR);
//		sql.add("SELECT id, com_cd, sku_cd, sku_nm, nvl(sku_barcd, ' ') as sku_barcd, nvl(box_barcd, ' ') as box_barcd, box_in_qty, sku_wt, sku_real_wt")
//		   .add("FROM TB_SKU WHERE")
//		   .add("(");
//
//		int idx = 0;
//		for(String skuCodeField : skuCodeFields) {
//			params.put(skuCodeField, skuCd);
//			sql.add((idx > 0 ? "or" : "") + "  		" + FormatUtil.toUnderScore(skuCodeField) + " = :" + skuCodeField);
//			idx++;
//		}
//		
//		sql.add(")")
//	       .add("#if($comCd)")
//		   .add("AND COM_CD = :comCd")
//		   .add("#end")
//		   .add("ORDER BY SKU_CD ASC");
//
//		List<SKU> skuList = this.queryManager.selectListBySql(sql.toString(), params, SKU.class, 0, 0);
//
//		if(ValueUtil.isEmpty(skuList) && exceptionFlag) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		}
//
//		return skuList;
//	}
//
//	@Override
//	public SKU findByCode(String comCd, String skuCd, boolean exceptionFlag) {
//		Map<String, Object> condition = ValueUtil.newMap("comCd,skuCd", comCd, skuCd);
//		SKU sku = this.queryManager.selectByCondition(SKU.class, condition);
//
//		if(sku == null && exceptionFlag) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		}
//
//		return sku;
//	}
//
//	@Override
//	public SKU findByCode(String comCd, String skuCd, String skuBarcd, boolean exceptionFlag) {
//		return findSKU(exceptionFlag, "id,com_cd,sku_cd,sku_nm,sku_barcd,box_barcd,box_in_qty,sku_wt,sku_real_wt", "comCd,skuCd,skuBarcd", comCd, skuCd, skuBarcd);
//	}
//
//	@Override
//	public SKU findByBoxBarcd(String comCd, String boxBarcd, boolean exceptionFlag) {
//		return findSKU(exceptionFlag, "id,com_cd,sku_cd,sku_nm,sku_barcd,box_barcd,box_in_qty,sku_wt,sku_real_wt", "comCd,boxBarcd", comCd, boxBarcd);
//	}
//	
//	/**
//	 * 고객사 코드, 상품 코드로 상품 조회
//	 * 
//	 * @param comCd
//	 * @param skuCd
//	 * @param exceptionFlag
//	 * @return
//	 */
//	protected SKU getSkuForWeight(String comCd, String skuCd, boolean exceptionFlag) {
//		Query condition = AnyOrmUtil.newConditionForExecution(1, 1, "sku_wt", "sku_real_wt", "wt_unit");
//		condition.addFilter("comCd", comCd);
//		condition.addFilter("skuCd", skuCd);
//		SKU sku = this.queryManager.selectByCondition(SKU.class, condition);
//
//		if(sku == null && exceptionFlag) {
//			throw ThrowUtil.newValidationErrorWithNoLog("바코드[" + skuCd + "]로 상품을 찾을 수 없습니다");
//		}
//		
//		return sku;
//	}
//
//	@Override
//	public Float findSkuWeight(String comCd, String skuCd, boolean exceptionFlag) {
//		SKU sku = this.getSkuForWeight(comCd, skuCd, exceptionFlag);
//		return (sku == null || sku.getSkuWt() == null) ? 0.0f : sku.getSkuWt();
//	}
//
//	@Override
//	public Float findSkuWeight(String comCd, String skuCd, String toUnit, boolean exceptionFlag) {
//		SKU sku = this.getSkuForWeight(comCd, skuCd, exceptionFlag);
//		Float weight = (sku == null) ? 0.0f : sku.getSkuWt();
//
//		if(ValueUtil.isNotEmpty(weight) && weight > 0.0f) {
//			weight = this.convertWeightToUnit(weight, sku.getWtUnit(), MpsSetting.WEIGHT_UNIT_KG);
//		}
//
//		return weight;
//	}
//	
//	@Override
//	public Float findSkuRealWeight(String comCd, String skuCd, String toUnit, boolean exceptionFlag) {
//		SKU sku = this.getSkuForWeight(comCd, skuCd, exceptionFlag);
//		Float weight = (sku == null) ? 0.0f : sku.getSkuRealWt();
//
//		if(ValueUtil.isNotEmpty(weight) && weight > 0.0f) {
//			weight = this.convertWeightToUnit(weight, sku.getWtUnit(), toUnit);
//		}
//
//		return weight;		
//	}
//	
//	@Override
//	public SKU findSkuWtAndRealWt(String comCd, String skuCd, String toUnit, boolean exceptionFlag) {
//		SKU sku = this.getSkuForWeight(comCd, skuCd, exceptionFlag);
//		Float skuWt = sku.getSkuWt();
//		
//		if(ValueUtil.isNotEmpty(skuWt) && skuWt > 0.0f) {
//			skuWt = this.convertWeightToUnit(skuWt, sku.getWtUnit(), MpsSetting.WEIGHT_UNIT_KG);
//			sku.setSkuWt(skuWt);
//		}
//		
//		Float skuRealWt = sku.getSkuRealWt();
//		
//		if(ValueUtil.isNotEmpty(skuRealWt) && skuRealWt > 0.0f) {
//			skuRealWt = this.convertWeightToUnit(skuRealWt, sku.getWtUnit(), MpsSetting.WEIGHT_UNIT_KG);
//			sku.setSkuRealWt(skuRealWt);
//		}
//		
//		return sku;
//	}
//	
//	/**
//	 * 중량 단위 변환 (KG -> G, G -> KG)
//	 * 
//	 * @param skuWeight
//	 * @param skuWtUnit
//	 * @param toUnit
//	 * @return
//	 */
//	protected Float convertWeightToUnit(Float skuWeight, String skuWtUnit, String toUnit) {
//		if(ValueUtil.isNotEmpty(skuWeight) && skuWeight > 0.0f) {
//			if(!ValueUtil.isEqualIgnoreCase(skuWtUnit, toUnit)) {
//				// 1. KG -> G
//				if(ValueUtil.isEqualIgnoreCase(toUnit, MpsSetting.WEIGHT_UNIT_G)) {
//					skuWeight = (skuWeight * 1000.0f);
//				// 2. G -> KG
//				} else if(ValueUtil.isEqualIgnoreCase(toUnit, MpsSetting.WEIGHT_UNIT_KG)) {
//					skuWeight = (skuWeight / 1000.0f);
//				}
//			}
//		}
//
//		return skuWeight;
//	}
//	
//	@Override
//	public SKU findSKU(boolean exceptionFlag, String selectFields, String paramNames, Object ... paramValues) {
//		Query condition = AnyOrmUtil.newConditionForExecution();
//
//		if(ValueUtil.isEmpty(selectFields)) {
//			condition.addSelect(selectFields.split(SysConstants.COMMA));
//		}
//
// 		String[] keyArr = paramNames.split(SysConstants.COMMA);
//
//		if (keyArr.length != paramValues.length) {
//			throw new IllegalArgumentException("keys count and values count mismatch!");
//		}
//
//		for (int i = 0; i < keyArr.length; i++) {
//			if(keyArr[i] != null && paramValues[i] != null) {
//				condition.addFilter(keyArr[i], paramValues[i]);
//			}
//		}
//
//		SKU sku = this.queryManager.selectByCondition(SKU.class, condition);
//
//		if(sku == null && exceptionFlag) {
//			throw ThrowUtil.newValidationErrorWithNoLog("SKU를 찾을 수 없습니다");
//		}
//
//		return sku;
//	}
//}
