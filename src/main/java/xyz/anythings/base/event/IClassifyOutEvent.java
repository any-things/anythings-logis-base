package xyz.anythings.base.event;

/**
 * 소분류 Out 이벤트
 * 
 * @author shortstop
 */
public interface IClassifyOutEvent extends IClassifyEvent {

	/**
	 * 분류 결과 리턴
	 * 
	 * @return
	 */
	public Object getResult();
	
	/**
	 * 분류 결과 설정
	 * 
	 * @param result
	 */
	public void setResult(Object result);

}
