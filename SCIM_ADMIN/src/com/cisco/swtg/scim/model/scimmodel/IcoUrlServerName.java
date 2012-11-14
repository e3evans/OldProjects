package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ICO_URL_SERVER_NAME")
public class IcoUrlServerName implements Serializable {
	private static final long serialVersionUID = 62726879266208L;
	
	private int serverId;
	
	private String serverName;
	private String icoUrlDomain;
	private String icoUrlAccessibility;
	
	@Id
	@Column(name="SERVER_ID")
	public int getServerId() { return serverId; }
	public void setServerId(int serverId) { this.serverId = serverId; }
	
	@Column(name="SERVER_NAME")
	public String getServerName() { return serverName; }
	public void setServerName(String serverName) { this.serverName = serverName; }
	
	@Column(name="ICO_URL_DOMAIN")
	public String getIcoUrlDomain() { return icoUrlDomain; }
	public void setIcoUrlDomain(String icoUrlDomain) { this.icoUrlDomain = icoUrlDomain; }
	
	@Column(name="ICO_URL_ACCESSIBILITY")
	public String getIcoUrlAccessibility() { return icoUrlAccessibility; }
	public void setIcoUrlAccessibility(String icoUrlAccessibility) { this.icoUrlAccessibility = icoUrlAccessibility; }
	
}
