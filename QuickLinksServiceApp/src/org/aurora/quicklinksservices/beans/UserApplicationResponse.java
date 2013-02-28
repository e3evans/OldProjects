package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserApplicationResponse implements Serializable {

	private static final long serialVersionUID = -3882633354007298236L;

	private Collection<UserAppResponseBean> userAppList;

	public Collection<UserAppResponseBean> getUserAppList() {
		return userAppList;
	}

	public void setUserAppList(Collection<UserAppResponseBean> userAppList) {
		this.userAppList = userAppList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}