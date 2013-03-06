package org.aurora.quicklinks.beans;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AppFormBean implements Serializable {

	private static final long serialVersionUID = -7098609643282995326L;

	private List<MenuApp> listMenuApp;

	public void setListMenuApp(List<MenuApp> listMenuApp) {
		this.listMenuApp = listMenuApp;
	}

	public List<MenuApp> getListMenuApp() {
		return listMenuApp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}