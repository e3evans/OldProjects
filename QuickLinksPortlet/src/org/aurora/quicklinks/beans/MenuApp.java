package org.aurora.quicklinks.beans;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MenuApp implements Serializable {

	private static final long serialVersionUID = 2348136144495681544L;

	private Application app;
	private UserApplication userApp;
	private boolean subAppsAllAutoReg = true;

	public MenuApp(Application app) {
		this.app = app;
	}

	public Application getApp() {
		return this.app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public UserApplication getUserApp() {
		return this.userApp;
	}

	public void setUserApp(UserApplication userApp) {
		this.userApp = userApp;
	}

	public boolean isAlreadyRegistered() {
		return (this.userApp != null)
				&& (this.userApp.getActiveCd().equals("A"));
	}

	public boolean isDefaultApp() {
		return (this.userApp != null && this.userApp.getFlagDefault() != null && this.userApp
				.getFlagDefault().equals("true"));
	}

	public void setSubAppsAllAutoReg(boolean subAppsAllAutoReg) {
		this.subAppsAllAutoReg = subAppsAllAutoReg;
	}

	public boolean getSubAppsAllAutoReg() {
		return this.subAppsAllAutoReg;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Comparator used to sort by app name
	 */
	public final static Comparator<MenuApp> APP_COMPARATOR = new AppComparator();

	public static class AppComparator implements Comparator<MenuApp>,
			Serializable {

		private static final long serialVersionUID = 4783983219368967080L;

		public int compare(MenuApp a, MenuApp b) {
			return a.getApp().getAppName().toLowerCase()
					.compareTo(b.getApp().getAppName().toLowerCase());
		}
	}
}