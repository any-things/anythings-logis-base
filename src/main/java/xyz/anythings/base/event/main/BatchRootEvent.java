package xyz.anythings.base.event.main;

import java.util.Map;

import xyz.anythings.sys.event.model.SysEvent;

/**
 * 작업 관련 Root 이벤트 
 * @author yang
 *
 */
public class BatchRootEvent extends SysEvent{

	/**
	 * 
	 * 
scope_type	범위 유형 : event
scope_id	범위 ID : event
category	설정 카테고리 : mps?
name	설정 키 : event.receive.type.receipt
                event.receive.type.batch
                
description	설정 설명 : 주문 수신 이벤트 처리 방법 
value	설정 값 : proc / dsrc
config	설정 메타 데이터(json) : { 'type' : 'proc' , 'procedure' : 'sp_batch_receive' }
                             { 'type' : 'dsrc' , 'sourceDsrc' : '', 'sourceTable' :'', 'sourceCol':'','rcvTable' : '' ,'rcvCol' : '' , 'completeQry' ?????} 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/** 메인 이벤트는 프로시저 타입에 이벤트 처리를 기본 지원 **/
	/** TODO : Procedure 에 대한 정보를 데이터로 관리 ?  **/ 
	/** 프로시저 변경만으로 재사용 가능한 방법은 ?? **/
	/** scope setting 활용 ?  **/ 
	/**
	 * 프로시저 명 
	 */
	protected String procedureName;
	/**
	 * 프로시저 파라미터 
	 */
	protected Map<String,?> procedureParams;
	
	/**
	 * 1 . BEFORE 
	 * 2 . MAIN
	 * 3 . AFTER
	 */
	protected short eventStep;
	
	
	public BatchRootEvent(short eventStep) {
		super();
		this.setEventStep(eventStep);
	}
	
	
	protected void setEventStep(short eventStep) {
		this.eventStep = eventStep;
	}
	
	protected short getEventStep() {
		return this.eventStep;
	}


	public String getProcedureName() {
		return procedureName;
	}


	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}


	public Map<String, ?> getProcedureParams() {
		return procedureParams;
	}


	public void setProcedureParams(Map<String, ?> procedureParams) {
		this.procedureParams = procedureParams;
	}
}
