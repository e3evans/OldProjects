package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TPT2J_USER_APP")
public class UserApp implements Serializable {

	private static final long serialVersionUID = 5649724987260236583L;

	public static final Integer NOTDISPLAYED = new Integer(0);
	private UserAppKey userAppKey;
	private App application;
	private Timestamp created;
	private Timestamp lastAccess;
	private Integer dispSeq;
	private String activeCd;

	// private User user;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "PT2J_USERID", nullable = false)),
			@AttributeOverride(name = "appId", column = @Column(name = "PT2J_APPID", nullable = false)),
			@AttributeOverride(name = "seqNo", column = @Column(name = "PT2J_SEQ_NO", nullable = false)) })
	public UserAppKey getUserAppKey() {
		return this.userAppKey;
	}

	public void setUserAppKey(UserAppKey userAppKey) {
		this.userAppKey = userAppKey;
	}

	@Column(name = "PT2J_CREATED", insertable = true, updatable = true)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Column(name = "PT2J_LAST_ACCESS", insertable = true, updatable = true)
	public Timestamp getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Column(name = "PT2J_DISP_SEQ", insertable = true, updatable = true)
	public Integer getDispSeq() {
		return this.dispSeq;
	}

	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}

	@Column(name = "PT2J_ACTIVE_CD", insertable = true, updatable = true)
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	public String getActiveCd() {
		return this.activeCd;
	}

	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}

	/*
	 * public boolean isActive() { return this.activeCd.equalsIgnoreCase("A"); }
	 */

	/*
	 * public Set getUserAppParams() { return this.userAppParams; }
	 * 
	 * public void setUserAppParams(Set userAppParams) { this.userAppParams =
	 * userAppParams; }
	 * 
	 * public void addUserAppParam(UserAppParam userAppParam) {
	 * userAppParam.setUserApp(this); this.userAppParams.add(userAppParam); }
	 * 
	 * public void removeUserAppParam(UserAppParam userAppParam) {
	 * this.userAppParams.remove(userAppParam); }
	 */
	/*
	 * public UserAppParam getUserAppParam(String paramName) { UserAppParam
	 * userAppParam = null; for (Iterator i = this.userAppParams.iterator();
	 * i.hasNext(); ) { UserAppParam tmpUserAppParam = (UserAppParam)i.next();
	 * if
	 * (tmpUserAppParam.getUserAppParamKey().getParamName().equals(paramName)) {
	 * userAppParam = tmpUserAppParam; } } return userAppParam; }
	 */

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "PT2J_APPID", updatable = false, insertable = false),
			@JoinColumn(name = "PT2J_SEQ_NO", updatable = false, insertable = false) })
	public App getApplication() {
		return this.application;
	}

	public void setApplication(App application) {
		this.application = application;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumns({ @JoinColumn(name = "PT2J_USERID", updatable = false,
	// insertable = false) })
	// public User getUser() {
	// return this.user;
	// }
	//
	// public void setUser(User user) {
	// this.user = user;
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}