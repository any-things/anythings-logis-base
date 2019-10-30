package xyz.anythings.base.model;

/**
 * 가장 기본적인 응답 모델
 * 
 * @author shortstop
 */
public class BaseResponse {

	/**
	 * 성공 여부
	 */
	private boolean success;
	/**
	 * 메시지 
	 */
	private String message;

	/**
	 * 생성자 1
	 */
	public BaseResponse() {
	}
	
	/**
	 * 생성자 2
	 * 
	 * @param success
	 * @param message
	 */
	public BaseResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
