package xyz.anythings.base.entity;

import xyz.elidom.dbist.annotation.Column;
import xyz.elidom.dbist.annotation.PrimaryKey;
import xyz.elidom.dbist.annotation.GenerationRule;
import xyz.elidom.dbist.annotation.Table;

@Table(name = "onetime_tokens", idStrategy = GenerationRule.UUID)
public class OnetimeToken extends xyz.elidom.orm.entity.basic.ElidomStampHook {
	/**
	 * SerialVersion UID
	 */
	private static final long serialVersionUID = 535633231735130373L;

	@PrimaryKey
	@Column (name = "id", nullable = false, length = 40)
	private String id;

	@Column (name = "requester_id", length = 32)
	private String requesterId;

	@Column (name = "user_id", nullable = false, length = 32)
	private String userId;

	@Column (name = "access_ip", length = 24)
	private String accessIp;

	@Column (name = "auth_token", length = 40)
	private String authToken;

	@Column (name = "expired_flag", length = 1)
	private Boolean expiredFlag;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Boolean getExpiredFlag() {
		return expiredFlag;
	}

	public void setExpiredFlag(Boolean expiredFlag) {
		this.expiredFlag = expiredFlag;
	}	
}
